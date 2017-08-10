;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname space-invaders) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/universe)
(require 2htdp/image)

;; Space Invaders


;; Constants:

(define WIDTH  300)
(define HEIGHT 500)

(define INVADER-X-SPEED 1.5)  ;speeds (not velocities) in pixels per tick
(define INVADER-Y-SPEED -1.5)
(define TANK-SPEED 2)
(define MISSILE-SPEED 10)
(define SPEED (/ 1 120)) ; speed of game
(define HIT-RANGE 10)

(define INVADE-RATE 55)

(define BACKGROUND (empty-scene WIDTH HEIGHT))

(define EMPTY (rectangle WIDTH HEIGHT "outline" "white"))

(define INVADER
  (overlay/xy (ellipse 10 15 "outline" "blue")              ;cockpit cover
              -5 6
              (ellipse 20 10 "solid"   "blue")))            ;saucer

(define TANK
  (overlay/xy (overlay (ellipse 28 8 "solid" "black")       ;tread center
                       (ellipse 30 10 "solid" "green"))     ;tread outline
              5 -14
              (above (rectangle 5 10 "solid" "black")       ;gun
                     (rectangle 20 10 "solid" "black"))))   ;main body

(define TANK-HEIGHT/2 (/ (image-height TANK) 2))
(define TANK-HEIGHT (image-height TANK))
(define MISSILE (ellipse 5 15 "solid" "red"))

;; Data Definitions:

(define-struct game (invaders missiles tank))
;; Game is (make-game  (listof Invader) (listof Missile) Tank)
;; interp. the current state of a space invaders game
;;         with the current invaders, missiles and tank position

;; Game constants defined below Missile data definition

#;
(define (fn-for-game s)
  (... (fn-for-loinvader (game-invaders s))
       (fn-for-lom (game-missiles s))
       (fn-for-tank (game-tank s))))


(define-struct tank (x dir))
;; Tank is (make-tank Number Integer[-1, 1])
;; interp. the tank location is x, HEIGHT - TANK-HEIGHT in screen coordinates
;;         the tank moves TANK-SPEED pixels per clock tick left if dir -1, right if dir 1

(define T0 (make-tank (/ WIDTH 2) 1))   ;center going right
(define T1 (make-tank 50 1))            ;going right
(define T2 (make-tank 50 -1))           ;going left

#;
(define (fn-for-tank t)
  (... (tank-x t) (tank-dir t)))



(define-struct invader (x y dx))
;; Invader is (make-invader Number Number Number)
;; interp. the invader is at (x, y) in screen coordinates
;;         the invader along x by dx pixels per clock tick

(define I1 (make-invader 150 100 12))           ;not landed, moving right
(define I2 (make-invader 150 HEIGHT -10))       ;exactly landed, moving left
(define I3 (make-invader 150 (+ HEIGHT 10) 10)) ;> landed, moving right


#;
(define (fn-for-invader invader)
  (... (invader-x invader) (invader-y invader) (invader-dx invader)))

;; ListOfInvader is one of:
;; - empty
;; - Invader
;; interp. a list of invaders

#;
(define (fn-for-loi loi)
  (cond [(empty? loi) (...)]                    ;BASE CASE
        [else (... (invader-x  (first loi))     ;Invader's X value at a position
                   (invader-y  (first loi))     ;Invader's Y value at a position
                   (invader-dx (first loi))
                   (fn-for-loi (rest loi)))]))  ;NATURAL RECURSION

;; Template rules used:
;;  - one of: 2 cases
;;  - atomic distinct: empty
;;  - compound: (cons Invader ListOfInvader)
;;  - self-reference: (rest loi) is ListOfInvader

(define-struct missile (x y))
;; Missile is (make-missile Number Number)
;; interp. the missile's location is x y in screen coordinates

(define M1 (make-missile 150 300))                               ;not hit U1
(define M2 (make-missile (invader-x I1) (+ (invader-y I1) 10)))  ;exactly hit U1
(define M3 (make-missile (invader-x I1) (+ (invader-y I1)  5)))  ;> hit U1

#;
(define (fn-for-missile m)
  (... (missile-x m) (missile-y m)))

;; ListOfMissile is one of:
;; - empty
;; - Missile
;; interp. a list of Missiles shot from the tank

#;
(define (fn-for-lom lom)
  (cond [(empty? lom) (...)]                   ;BASE CASE
        [else (... (missile-x (first lom))     ;Missile X
                   (missile-y (first lom))     ;Missile Y  
                   (fn-for-lom (rest lom)))])) ;NATURAL RECURSION

;; Template rules used:
;;  - one of: 2 cases
;;  - atomic distinct: empty
;;  - compound: (cons Missile ListOfMissile)
;;  - self-reference: (rest lom) is ListOfMissile

(define G0 (make-game empty empty T0))
(define G1 (make-game empty empty T1))
(define G2 (make-game (list I1) (list M1) T1))
(define G3 (make-game (list I1 I2) (list M1 M2) T1))

;; =================
;; Functions:

;; Game -> Game
;; start the world with (main (make-game (cons (make-invader 150 0 2) empty) (cons (make-missile 150 HEIGHT) empty) (make-tank 150 1)))
;; 
(define (main g)
  (big-bang g                           ; Game
            (on-tick   next-game SPEED) ; Game -> Game
            (to-draw   render-game)     ; Game -> Image
            (on-key    handle-key)      ; Game KeyEvent -> Game
            (stop-when handle-stop)))      ; Game -> Boolean

;; ListOfInvader -> Boolean
;; If invader hits the ground returns true

(check-expect (invader-hit-ground? empty) false)
(check-expect (invader-hit-ground? (cons (make-invader 150 150 2) empty)) false)
(check-expect (invader-hit-ground? (cons (make-invader 150 250 2) (cons (make-invader 150 150 2) empty))) false)

(check-expect (invader-hit-ground? (cons (make-invader 150 HEIGHT 2) empty)) true)
(check-expect (invader-hit-ground? (cons (make-invader 150 250 2) (cons (make-invader 150 HEIGHT 2) empty))) true)

;; (define (invader-hit-ground? loi) false) ;stub

;; <Template from ListOfInvader>

(define (invader-hit-ground? loi)
  (cond [(empty? loi) false]
        [(<= HEIGHT (invader-y (first loi))) true]
        [else (invader-hit-ground? (rest loi))]))

;; Game -> Boolean
;; Determines if an invaders hit the ground

;; (define (handle-stop g) false) ;stub
;; <Template>
#;
(define (fn-for-stop g)
  ...g)

(define (handle-stop g)
  (invader-hit-ground? (game-invaders g)))

;; Game -> Game
;; produce the next game state

;; Tank Tests
(check-expect (next-game G0) (make-game empty empty (make-tank (+ (/ WIDTH 2) TANK-SPEED) 1))) ;; Tank moving right
(check-expect (next-game (make-game empty empty (make-tank (/ WIDTH 2) -1))) (make-game empty empty (make-tank (- (/ WIDTH 2) TANK-SPEED) -1))) ; Tank moving left
(check-expect (next-game (make-game empty empty (make-tank 0 -1))) (next-game (make-game empty empty (make-tank 0 0)))) ;; Tank on left corner
(check-expect (next-game (make-game empty empty (make-tank WIDTH 1))) (next-game (make-game empty empty (make-tank WIDTH 0)))) ;; Tank on right corner

;; Projectile Tests
(check-expect (next-game (make-game empty
                                    (cons (make-missile 150 (/ HEIGHT 2)) empty)
                                    empty))
              (make-game empty
                         (cons (make-missile 150 (- (/ HEIGHT 2) MISSILE-SPEED)) empty)
                         empty)) ; moving up

(check-expect (next-game (make-game empty
                                    (cons (make-missile 150 0) empty)
                                    empty))
              (make-game empty
                         empty
                         empty)) ; hit top

(check-expect (next-game (make-game empty
                                    (cons (make-missile 150 (/ HEIGHT 2)) (cons (make-missile 200 (/ HEIGHT 2)) empty))
                                    empty))
              (make-game empty
                         (cons (make-missile 150 (- (/ HEIGHT 2) MISSILE-SPEED)) (cons (make-missile 200 (- (/ HEIGHT 2) MISSILE-SPEED)) empty))
                         empty)) ; two moving up

(check-expect (next-game (make-game empty
                                    (cons (make-missile 200 (/ HEIGHT 2)) (cons (make-missile 150 0) empty))
                                    empty))
              (make-game empty
                         (cons (make-missile 200 (- (/ HEIGHT 2) MISSILE-SPEED)) empty)
                         empty)) ; moving up, one hit top

;; Invader Tests
(check-expect (next-game (make-game (cons (make-invader 150 100 12) empty)
                                    empty
                                    empty))
              (make-game (cons (make-invader (+ 150 INVADER-X-SPEED) (- 100 INVADER-Y-SPEED) 12) empty)
                         empty
                         empty)) ; moving down-right

(check-expect (next-game (make-game (cons (make-invader 150 100 -12) empty)
                                    empty
                                    empty))
              (make-game (cons (make-invader (- 150 INVADER-X-SPEED) (- 100 INVADER-Y-SPEED) -12) empty)
                         empty
                         empty)) ; moving down-left

(check-expect (next-game (make-game (cons (make-invader 0 100 -12) empty)
                                    empty
                                    empty))
              (make-game (cons (make-invader (+ 0 INVADER-X-SPEED) (- 100 INVADER-Y-SPEED) 12) empty)
                         empty
                         empty)) ; bounce off left wall

(check-expect (next-game (make-game (cons (make-invader WIDTH 100 12) empty)
                                    empty
                                    empty))
              (make-game (cons (make-invader (- WIDTH INVADER-X-SPEED) (- 100 INVADER-Y-SPEED) -12) empty)
                         empty
                         empty)) ; bounce right right wall

(check-expect (next-game (make-game (cons (make-invader 0 100 -12) (cons (make-invader 150 300 12) empty))
                                    empty
                                    empty))
              (make-game (cons (make-invader (+ 0 INVADER-X-SPEED) (- 100 INVADER-Y-SPEED) 12) (cons (make-invader (+ 150 INVADER-X-SPEED) (- 300 INVADER-Y-SPEED) 12) empty))
                         empty
                         empty)) ; list bounce off left wall

(check-expect (next-game (make-game (cons (make-invader WIDTH 100 12) (cons (make-invader 150 300 -12) empty))
                                    empty
                                    empty))
              (make-game (cons (make-invader (- WIDTH INVADER-X-SPEED) (- 100 INVADER-Y-SPEED) -12) (cons (make-invader (- 150 INVADER-X-SPEED) (- 300 INVADER-Y-SPEED) -12) empty))
                         empty
                         empty)) ; list bounce right right wall


;; (define (next-game g) g) ; stub

;; <Template from Game>

(define (next-game g)
  (make-game (next-invader (game-invaders g) (game-missiles g) (random 100))
             (next-missile (game-missiles g) (game-invaders g))
             (next-tank (game-tank g))))

;; Tank -> Tank
;; Move tank in the proper direction

(check-expect (next-tank empty) empty)
(check-expect (next-tank (make-tank (/ WIDTH 2) 1)) (make-tank (+ (/ WIDTH 2) TANK-SPEED) 1))   ;; Move right
(check-expect (next-tank (make-tank (/ WIDTH 2) -1)) (make-tank (- (/ WIDTH 2) TANK-SPEED) -1)) ;; Move left

(check-expect (next-tank (make-tank WIDTH 1))(make-tank WIDTH 0)) ;; Right corner
(check-expect (next-tank (make-tank 0 -1))(make-tank 0 0))        ;; Left corner

;; (define (next-tank t) t) ;stub

;; <Template from Tank>

(define (next-tank t)
  (cond [(empty? t) empty]
        [(and (<= (tank-x t) 0) (= (tank-dir t) -1)) (make-tank 0 0)]
        [(and (>= (tank-x t) WIDTH) (= (tank-dir t) 1)) (make-tank WIDTH 0)]
        [(= (tank-dir t) 1) (make-tank (+ (tank-x t) TANK-SPEED) (tank-dir t))]
        [(= (tank-dir t) -1) (make-tank (- (tank-x t) TANK-SPEED) (tank-dir t))]
        [else (make-tank (tank-x t) (tank-dir t))]))

;; ListOfMissile ListOfInvader -> ListOfMissile
;; progress missiles, if it hits an invader it's removed from the state

(check-expect (next-missile empty empty) empty)
(check-expect (next-missile (cons (make-missile 150 300) empty) empty)
              (cons (make-missile 150 (- 300 MISSILE-SPEED)) empty)) ; progress

(check-expect (next-missile (cons (make-missile 150 0) empty) empty) empty); one hits top

(check-expect (next-missile (cons (make-missile 150 0) (cons (make-missile 180 150) empty)) empty)
              (cons (make-missile 180 (- 150 MISSILE-SPEED)) empty)) ;list one hits top

(check-expect (next-missile (cons (make-missile 150 170) (cons (make-missile 180 150) empty)) empty)
              (cons (make-missile 150 (- 170 MISSILE-SPEED)) (cons (make-missile 180 (- 150 MISSILE-SPEED)) empty))) ;list, both progress up                 

(check-expect (next-missile (cons (make-missile 150 170) (cons (make-missile 180 150) empty))
                            (cons (make-invader 140 180 2) empty))
              (cons (make-missile 180 (- 150 MISSILE-SPEED)) empty)) ;list, both progress up, collision

(check-expect (next-missile (cons (make-missile 150 170) (cons (make-missile 180 150) empty))
                            (cons (make-invader 140 180 2) (cons (make-invader 190 140 -2) empty)))
              empty) ;all collide

;(define (next-missile lom) empty) ;stub

;; Template from ListOfMissile

(define (next-missile lom loi)
  (cond [(empty? lom) empty]
        [(missile-hit? (first lom) loi) (next-missile (rest lom) loi)]
        [(<= (missile-y (first lom)) 0) (next-missile (rest lom) loi)]
        [else (cons (make-missile (missile-x (first lom))
                                  (- (missile-y (first lom)) MISSILE-SPEED))
                    (next-missile (rest lom) loi))]))

;; Missile ListOfInvader -> Boolean
;; Determines if the missile made contact with an invader
(check-expect (missile-hit? (make-missile 150 100) empty) false)
(check-expect (missile-hit? (make-missile 150 150)
                            (cons (make-invader 160 160 1) empty)) true)
(check-expect (missile-hit? (make-missile 130 130)
                            (cons (make-invader 160 160 1) empty)) false)
(check-expect (missile-hit? (make-missile 150 150)
                            (cons (make-invader 140 150 12) (cons (make-invader 200 200 -2) empty))) true)

(check-expect (missile-hit? (make-missile 200 300)
                            (cons (make-invader 140 150 12) (cons (make-invader 200 200 -2) empty))) false)

;; (define (missile-hit? m loi) false) ;stub

;; <Template from ListOfInvader>

(define (missile-hit? m loi)
  (cond [(empty? loi) false]
        [(and
          (and (<= (abs (- (missile-x m)(invader-x (first loi)))) HIT-RANGE)
               (>= (abs (- (missile-x m)(invader-x (first loi)))) 0)) ;checks if difference between missile and invader is between hit range and 0
          (and (<= (abs (- (missile-y m)(invader-y (first loi)))) HIT-RANGE)
               (>= (abs (- (missile-y m)(invader-y (first loi)))) 0)))
         true]
        [else (missile-hit? m (rest loi))]))

;; ListOfInvader ListOfMissiles Integer -> ListOfInvader
;; progress invaders or delete them from game state if they hit a missile, spawns one if random number divded by invade rate = 0
(check-expect (next-invader empty empty 1) empty)
(check-expect (next-invader (cons (make-invader 200 200 2) empty) empty 7)
              (cons (make-invader (+ 200 INVADER-X-SPEED) (- 200 INVADER-Y-SPEED) 2) empty))

(check-expect (next-invader (cons (make-invader 200 200 -2) empty) empty 7)
              (cons (make-invader (- 200 INVADER-X-SPEED) (- 200 INVADER-Y-SPEED) -2) empty))

(check-expect (next-invader (cons (make-invader 0 200 -2) empty) empty 7)
              (cons (make-invader (+ 0 INVADER-X-SPEED) (- 200 INVADER-Y-SPEED) 2) empty)) ; hits left wall

(check-expect (next-invader (cons (make-invader WIDTH 200 2) empty) empty 7)
              (cons (make-invader (- WIDTH INVADER-X-SPEED) (- 200 INVADER-Y-SPEED) -2) empty)) ; hits right wall

(check-expect (next-invader (cons (make-invader 250 200 -2) (cons (make-invader 200 200 2) empty)) empty 7)
              (cons (make-invader (- 250 INVADER-X-SPEED) (- 200 INVADER-Y-SPEED) -2)
                    (cons (make-invader (+ 200 INVADER-X-SPEED) (- 200 INVADER-Y-SPEED) 2) empty))) ; two moving

(check-expect (next-invader (cons (make-invader 250 200 -2) (cons (make-invader 200 200 2) empty))
                            (cons (make-missile 240 190) empty) 7)
              (cons (make-invader (+ 200 INVADER-X-SPEED) (- 200 INVADER-Y-SPEED) 2) empty)) ; two moving, one gets hit


(check-expect (next-invader (cons (make-invader 250 200 -2) (cons (make-invader 200 200 2) empty))
                            (cons (make-missile 240 190) (cons (make-missile 190 190) empty)) 7)
              empty) ; two moving, one gets hit


;; (define (next-invader loi) empty) ;stub

;; <Template from ListOfInvader>

(define (next-invader loi lom r)
  (cond [(empty? loi) (if (= (modulo r INVADE-RATE) 0)
                          (if (= (+ (random 2) 1) 1)
                              (cons (make-invader (random WIDTH) 0 -2) empty)
                              (cons (make-invader (random WIDTH) 0 2) empty))
                          empty)]
        [(invader-hit? (first loi) lom) (next-invader (rest loi) lom r)]
        [(<= (invader-x (first loi)) 0)
         (cons (make-invader (+ (invader-x (first loi)) INVADER-X-SPEED)
                             (- (invader-y (first loi)) INVADER-Y-SPEED)
                             (* (invader-dx (first loi)) -1))
               (next-invader (rest loi) lom r))]
        [(>= (invader-x (first loi)) WIDTH)
         (cons (make-invader (- (invader-x (first loi)) INVADER-X-SPEED)
                             (- (invader-y (first loi)) INVADER-Y-SPEED)
                             (* (invader-dx (first loi)) -1))
               (next-invader (rest loi) lom r))]
        [(> (invader-dx (first loi)) 0)
         (cons (make-invader (+ (invader-x (first loi)) INVADER-X-SPEED)
                             (- (invader-y (first loi)) INVADER-Y-SPEED)
                             (invader-dx (first loi)))
               (next-invader (rest loi) lom r))]
        [(< (invader-dx (first loi)) 0)
         (cons (make-invader (- (invader-x (first loi)) INVADER-X-SPEED)
                             (- (invader-y (first loi)) INVADER-Y-SPEED)
                             (invader-dx (first loi)))
               (next-invader (rest loi) lom r))]))

;; Invader ListOfMissile -> Boolean
;; Determines if an invader got hit by a missile, if it has, destroy the invader

(check-expect (invader-hit? (make-invader 200 200 -2) empty) false)

(check-expect (invader-hit? (make-invader 200 200 -2)
                            (cons (make-missile 190 190) empty)) true)
(check-expect (invader-hit? (make-invader 250 250 2)
                            (cons (make-missile 190 190) empty)) false)

(check-expect (invader-hit? (make-invader 200 200 -2)
                            (cons (make-missile 150 140) (cons (make-missile 190 190) empty))) true)
(check-expect (invader-hit? (make-invader 200 200 -2)
                            (cons (make-missile 150 140) (cons (make-missile 50 190) empty))) false)

;; (define (invader-hit? i lom) false) ;stub

;; <Template from ListOfMissile>

(define (invader-hit? i lom)
  (cond [(empty? lom) false]
        [(and
          (and (<= (abs (- (missile-x (first lom))(invader-x i))) HIT-RANGE)
               (>= (abs (- (missile-x (first lom))(invader-x i))) 0))         ;checks if difference between missile and invader is between hit range and 0
          (and (<= (abs (- (missile-y (first lom))(invader-y i))) HIT-RANGE)
               (>= (abs (- (missile-y (first lom))(invader-y i))) 0)))
         true]
        [else (invader-hit? i (rest lom))]))

;; Game -> Image
;; render gamestate into an image

(check-expect (render-game (make-game empty empty empty)) (overlay EMPTY EMPTY EMPTY BACKGROUND))

(check-expect (render-game (make-game empty empty (make-tank 280 1))) (overlay EMPTY EMPTY (place-image TANK 280 (- HEIGHT TANK-HEIGHT/2) BACKGROUND)))
(check-expect (render-game (make-game (cons (make-invader (/ WIDTH 2) (/ HEIGHT 2) 2) empty) empty empty)) (overlay (place-image INVADER (/ WIDTH 2) (/ HEIGHT 2) EMPTY) EMPTY EMPTY BACKGROUND))
(check-expect (render-game (make-game empty (cons (make-missile (/ WIDTH 2) (/ HEIGHT 2)) empty) empty)) (overlay EMPTY (place-image MISSILE (/ WIDTH 2) (/ HEIGHT 2) EMPTY) BACKGROUND))

(check-expect (render-game (make-game (cons (make-invader 150 100 2) empty)
                                      (cons (make-missile (/ WIDTH 2) (/ HEIGHT 2)) empty)
                                      (make-tank 280 1)))
              (place-image INVADER 150 100
                           (overlay (place-image MISSILE (/ WIDTH 2) (/ HEIGHT 2) EMPTY)
                                    (place-image TANK 280 (- HEIGHT TANK-HEIGHT/2) EMPTY)
                                    BACKGROUND)))

(check-expect (render-game (make-game (cons (make-invader 120 100 -2) (cons (make-invader 200 140 2) empty))
                                      (cons (make-missile (/ WIDTH 2) (/ HEIGHT 2)) (cons (make-missile 150 90) empty))
                                      (make-tank 240 2)))
              (overlay (place-image INVADER 120 100 (place-image INVADER 200 140 EMPTY))
                       (place-image MISSILE (/ WIDTH 2) (/ HEIGHT 2) (place-image MISSILE 150 90 EMPTY))
                       (place-image TANK 240 (- HEIGHT TANK-HEIGHT/2) EMPTY)
                       BACKGROUND))


;(define (render-game g) BACKGROUND) ;stub

;; <Template from game>

(define (render-game g)
  (overlay (render-invaders (game-invaders g))
           (render-missiles (game-missiles g))
           (render-tank (game-tank g))
           BACKGROUND))
           

;; ListOfInvaders -> Image
;; Takes a list of invaders and renders them

(check-expect (render-invaders empty) EMPTY)
(check-expect (render-invaders (cons (make-invader 150 150 2) empty)) (place-image INVADER 150 150 EMPTY))
(check-expect (render-invaders (cons (make-invader 150 150 -1) (cons (make-invader 250 200 2) empty))) (place-image INVADER 150 150 (place-image INVADER 250 200 EMPTY)))

;; (define (render-invaders loi) EMPTY) ;stub

;; <Template from ListOfInvader>

(define (render-invaders loi)
  (cond [(empty? loi) EMPTY]
        [else (place-image INVADER
                           (invader-x  (first loi))
                           (invader-y  (first loi))
                           (render-invaders (rest loi)))]))

;; ListOfMissiles -> Image
;; Takes a list of missiles and renders them

(check-expect (render-missiles empty) EMPTY)
(check-expect (render-missiles (cons (make-missile 150 150) empty))
              (place-image MISSILE 150 150 EMPTY))
(check-expect (render-missiles (cons (make-missile 150 150) (cons (make-missile 200 200) empty)))
              (place-image MISSILE 150 150 (place-image MISSILE 200 200 EMPTY)))

;; (define (render-missiles lom) BACKGROUND) ;stub

;; <Template from ListOfMissile>

(define (render-missiles lom)
  (cond [(empty? lom) EMPTY]
        [else (place-image MISSILE
                           (missile-x (first lom))
                           (missile-y (first lom))
                           (render-missiles (rest lom)))]))

;; Tank -> Image
;; Renders tank at a fixed height

(check-expect (render-tank empty) EMPTY)
(check-expect (render-tank (make-tank 200 -1)) (place-image TANK 200 (- HEIGHT TANK-HEIGHT/2) EMPTY))

;; (define (render-tank tank) EMPTY)

;; <Template from Tank>

(define (render-tank t)
  (if (empty? t)
      EMPTY
      (place-image TANK (tank-x t) (- HEIGHT TANK-HEIGHT/2) EMPTY)))

;; Game KeyEvent -> Game
;; handles key input to during the game

(check-expect (handle-key (make-game empty empty (make-tank 150 2)) " ") (make-game empty (cons (make-missile 150 (- HEIGHT TANK-HEIGHT)) empty) (make-tank 150 2)))
(check-expect (handle-key (make-game empty (cons (make-missile 250 150) empty) (make-tank 150 2)) " ")
              (make-game empty (cons (make-missile 150 (- HEIGHT TANK-HEIGHT)) (cons (make-missile 250 150) empty)) (make-tank 150 2)))
(check-expect (handle-key (make-game empty (cons (make-missile 250 150) (cons (make-missile 140 230) empty)) (make-tank 150 2)) " ")
              (make-game empty (cons (make-missile 150 (- HEIGHT TANK-HEIGHT)) (cons (make-missile 250 150) (cons (make-missile 140 230) empty))) (make-tank 150 2)))

(check-expect (handle-key (make-game empty empty (make-tank 150 2)) "up") (make-game empty empty (make-tank 150 2)))
(check-expect (handle-key (make-game empty (cons (make-missile 250 150) empty) (make-tank 150 2)) "down")
              (make-game empty (cons (make-missile 250 150) empty) (make-tank 150 2)))
(check-expect (handle-key (make-game empty (cons (make-missile 250 150) (cons (make-missile 140 230) empty)) (make-tank 150 1)) "left")
              (make-game empty (cons (make-missile 250 150) (cons (make-missile 140 230) empty)) (make-tank 150 -1)))
(check-expect (handle-key (make-game empty (cons (make-missile 250 150) (cons (make-missile 140 230) empty)) (make-tank 150 -1)) "right")
              (make-game empty (cons (make-missile 250 150) (cons (make-missile 140 230) empty)) (make-tank 150 1)))

;; (define (handle-key g ke) (make-game empty empty empty)) ;stub

;; <Template from KeyHandlers>

(define (handle-key g ke)
  (cond [(key=? ke " ") (make-game (game-invaders g) (cons (make-missile (tank-x (game-tank g)) (- HEIGHT TANK-HEIGHT)) (game-missiles g)) (game-tank g))]
        [(key=? ke "left") (make-game (game-invaders g) (game-missiles g) (make-tank (tank-x (game-tank g)) -1))]
        [(key=? ke "right") (make-game (game-invaders g) (game-missiles g) (make-tank (tank-x (game-tank g)) 1))]
        [else 
         g]))