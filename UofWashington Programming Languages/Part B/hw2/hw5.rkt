;; Programming Languages, Homework 5

#lang racket
(provide (all-defined-out)) ;; so we can put tests in a second file

;; definition of structures for MUPL programs - Do NOT change
(struct var  (string) #:transparent)  ;; a variable, e.g., (var "foo")
(struct int  (num)    #:transparent)  ;; a constant number, e.g., (int 17)
(struct add  (e1 e2)  #:transparent)  ;; add two expressions
(struct ifgreater (e1 e2 e3 e4)    #:transparent) ;; if e1 > e2 then e3 else e4
(struct fun  (nameopt formal body) #:transparent) ;; a recursive(?) 1-argument function
(struct call (funexp actual)       #:transparent) ;; function call
(struct mlet (var e body) #:transparent) ;; a local binding (let var = e in body) 
(struct apair (e1 e2)     #:transparent) ;; make a new pair
(struct fst  (e)    #:transparent) ;; get first part of a pair
(struct snd  (e)    #:transparent) ;; get second part of a pair
(struct aunit ()    #:transparent) ;; unit value -- good for ending a list
(struct isaunit (e) #:transparent) ;; evaluate to 1 if e is unit else 0

;; a closure is not in "source" programs but /is/ a MUPL value; it is what functions evaluate to
(struct closure (env fun) #:transparent) 

;; Problem 1

;; CHANGE (put your solutions here)

;; listof(a) -> mupllistof(a)
;; turn a racket list to a mupl list
(define (racketlist->mupllist rl)
  (cond [(null? rl) (aunit)]
        [(number? (car rl)) (apair (int (car rl)) (racketlist->mupllist (cdr rl)))]
        [(string? (car rl)) (apair (var (car rl)) (racketlist->mupllist (cdr rl)))]
        [#t (apair (car rl) (racketlist->mupllist (cdr rl)))]))

;; mupllistof(a) -> listof(a)
;; turn a racket list to a mupl list
(define (mupllist->racketlist ml)
  (cond [(aunit? ml) null]
        [(int? (apair-e1 ml)) (cons (apair-e1 ml) (mupllist->racketlist (apair-e2 ml)))]
        [(string? (apair-e1 ml)) (cons (apair-e1 ml) (mupllist->racketlist (apair-e2 ml)))]
        [#t (cons (apair-e1 ml) (mupllist->racketlist (apair-e2 ml)))]))

;; Problem 2

;; lookup a variable in an environment
;; Do NOT change this function
(define (envlookup env str)
  (cond [(null? env) (error "unbound variable during evaluation" str)]
        [(equal? (car (car env)) str) (cdr (car env))]
        [#t (envlookup (cdr env) str)]))

;; Do NOT change the two cases given to you.  
;; DO add more cases for other kinds of MUPL expressions.
;; We will test eval-under-env by calling it directly even though
;; "in real life" it would be a helper function of eval-exp.
(define (eval-under-env e env)
  (cond [(var? e) 
         (envlookup env (var-string e))]
        [(add? e) 
         (let ([v1 (eval-under-env (add-e1 e) env)]
               [v2 (eval-under-env (add-e2 e) env)])
           (if (and (int? v1)
                    (int? v2))
               (int (+ (int-num v1) 
                       (int-num v2)))
               (error "MUPL addition applied to non-number")))]
        ;; CHANGE add more cases here
        [(int? e) e]
        [(ifgreater? e)
         (let ([v1 (eval-under-env (ifgreater-e1 e) env)]
               [v2 (eval-under-env (ifgreater-e2 e) env)])
           (if (and (int? v1) (int? v2))
               (if (> (int-num v1) (int-num v2))
                   (eval-under-env (ifgreater-e3 e) env)
                   (eval-under-env (ifgreater-e4 e) env))
               (error "Cannot perform greater than with non-ints.")))]
        [(mlet? e)
         (letrec ([new_env (cons (cons (mlet-var e) (eval-under-env (mlet-e e) env)) env)]
                  [v1 (eval-under-env (mlet-body e) new_env)])
           v1)]
        [(call? e) (if (closure? (call-funexp e))
                       (let ([env-with-fun (cons (cons (fun-nameopt (closure-fun (call-funexp e))) (closure-fun (call-funexp e))) (closure-env (call-funexp e)))]
                             [arguments (cons (fun-formal (closure-fun (call-funexp e))) (call-actual e))])
                         (if (fun-nameopt (closure-fun (call-funexp e)))
                             (eval-under-env (fun-body (closure-fun (call-funexp e))) (cons arguments env-with-fun))
                             (eval-under-env (fun-body (closure-fun (call-funexp e))) (cons arguments (closure-env (call-funexp e))))))
                       (error "First argument must be a closure"))]
        [(apair? e) (let ([v1 (eval-under-env (apair-e1 e) env)]
                          [v2 (eval-under-env (apair-e2 e) env)])
                      (apair v1 v2))]
        [(snd? e) (if (apair? (snd-e e)) (apair-e2 (snd-e e)) (error "Argument should be an apair"))]
        [(fst? e) (if (apair? (fst-e e)) (apair-e1 (fst-e e)) (error "Argument should be an apair"))]
        [(isaunit? e) (if (aunit? e) (int 1) (int 0))]
        [#t (error (format "bad MUPL expression: ~v" e))]))

;; Do NOT change
(define (eval-exp e)
  (eval-under-env e null))
        
;; Problem 3

(define (ifaunit e1 e2 e3) (if (aunit? e1) e2 e3))

(define (mlet* lstlst e2)
  (define (aux lstlst acc)
    (cond [(null? lstlst) (eval-under-env e2 acc)]
          [#t (aux (cdr lstlst) (cons (first lstlst) acc))]))
  (aux lstlst null))
         

(define (ifeq e1 e2 e3 e4)
  (if (and (int? e1) (int? e2))
      (if (= (int-num e1) (int-num e2)) e3 e4)
      (error "Cannot do equal with two non-ints!")))

;; Problem 4

(define mupl-map (closure null
                          (fun "map" (list (cons "mlst" (apair (int 1) (aunit))))
                               (cond [(aunit? (var "mlst")) (aunit)]
                                     [#t "hi"]))))

(define mupl-mapAddN 
  (mlet "map" mupl-map
        "CHANGE (notice map is now in MUPL scope)"))

;; Challenge Problem

(struct fun-challenge (nameopt formal body freevars) #:transparent) ;; a recursive(?) 1-argument function

;; We will test this function directly, so it must do
;; as described in the assignment
(define (compute-free-vars e) "CHANGE")

;; Do NOT share code with eval-under-env because that will make
;; auto-grading and peer assessment more difficult, so
;; copy most of your interpreter here and make minor changes
(define (eval-under-env-c e env) "CHANGE")

;; Do NOT change this
(define (eval-exp-c e)
  (eval-under-env-c (compute-free-vars e) null))
