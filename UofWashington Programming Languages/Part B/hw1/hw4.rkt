
#lang racket

(provide (all-defined-out)) ;; so we can put tests in a second file

;; put your code below


;; ( #1 )
;; Natural Natural Natural -> listOf(Natural)
;; take a low, high and stride and produce a list of naturals
;;   between low and high seperated by stride

(define (sequence l h s)
  (cond [(> l h ) null]
        [#t (cons l (sequence (+ l s) h s))]))

;; ( #2 )
;; listof(String) String -> listof(String)
;; Add string to each string in the list

(define (string-append-map los s)
  (cond [(null? los) null]
        [#t (cons (string-append (car los) s) (string-append-map (cdr los) s))]))

;; ( #3 )
;; listOf(A) Natural -> A
;; Returns the nth item in the list, throws an error if list is empty or n is negative

(define (list-nth-mod loa n)
  (define (get-nth loa n)
    (cond [(null? loa) null]
          [(= n 0) (car loa)]
          [#t (get-nth (cdr loa) (- n 1))]))
  (cond [(< n 0) (error "list-nth-mod: negative number")]
        [(null? loa) (error "list-nth-mod: empty list")]
        [#t (get-nth loa n)]))

;; ( #4 )
;; streamof(A) Number -> listof(A)
;; Takes a stream and a number and produces the first n elements in the stream

(define (stream-for-n-steps stream n)
  (cond [(= 0 n) null]
        [(cons (car (stream)) (stream-for-n-steps (cdr (stream)) (- n 1)))]))

;; ( #5 )
;; () -> streamof(Number) 
;; Produce a stream of numbers excluding numbers divisible by 5
(define (funny-number-stream)
         (define (streamer x) (lambda () (cons (if (= 0 (modulo (+ x 1) 5)) (- 0 (+ 1 x)) (+ x 1)) (streamer (+ x 1)))))
  ((streamer 0)))

;; ( #6 )
;; () -> streamof(Dog)
;; Produce a stream that alternates images of dogs and dan

(define (dan-then-dog)
  (define (doggo x) (lambda () (cons (if (even? x) "dan.jpg" "dog.jpg") (doggo (+ x 1)))))
  ((doggo 0)))

;; ( #7 )
;; streamof(A) -> streamof(pairof(Number, A))
;; add zero for ever element in a stream
(define (stream-add-zero s)
  (lambda () (cons (cons 0 (car (s)))  (stream-add-zero (cdr (s))))))

;; ( #8 )
;; listof(X) listof(Y) -> streamof(pairof(X, Y))
;; cycle through two lists infinitely
(define (cycle-lists xs ys)
  (define (cycle xl yl)
    (cond [(null? xl) (cycle xs yl)]
          [(null? yl) (cycle xl ys)]
          [#t  (lambda () (cons (cons (car xl) (car yl)) (cycle (cdr xl) (cdr yl))))]))
  (cycle xs ys))

;; ( #9 )
;; Value Vector -> Vector
;; Searches through a vector list to find Value, returns #f if value isn't found
(define (vector-assoc v vec)
  (define (vector-helper n)
    (cond [(or (< n 0) (null? (vector-ref vec n))) #f]
          [(pair? (vector-ref vec n)) (if (equal? v (car (vector-ref vec n))) (vector-ref vec n) (vector-helper (- n 1)))]
          [#t (vector-helper (- n 1))]))
  (vector-helper (- (vector-length vec) 1)))

;; ( #10 )
;; listof(X) Number -> f(Value) -> X
;; Searches through a list and returns a function that caches the result and returns the value
(define (cached-assoc xs n)
  (define (cache vec-len)
    (letrec ([memo (make-vector vec-len)]
             [pos 0])
      ((lambda ()
         (let ([rslt (vector-assoc n memo)])
           (if rslt
               rslt
               (begin
                 (vector-set! memo (modulo pos vec-len) (assoc n xs))
                 (set! pos (+ pos 1))
                 (vector-assoc n memo))))))))
  cache)

;; ( #11 )
;; Macro!
;; Implementation of a while loop
(define-syntax while-less
  (syntax-rules (while do)
    [(while-less e1 do e2)
     (letrec ([aux (lambda (x y)
                     (cond [(>= y x) #t]
                           [#t (aux x e2)]))])
       (aux e1 e2))]))