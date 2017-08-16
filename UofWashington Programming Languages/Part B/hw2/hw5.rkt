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
(struct ifeq (e1 e2 e3 e4) #:transparent) ;; check if two ints are equal
(struct ifaunit (e1 e2 e3) #:transparent) ;; check if something evaluates to a unit
;; a closure is not in "source" programs but /is/ a MUPL value; it is what functions evaluate to
(struct mlet* (lstlst e2) #:transparent)
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
        [(call? e) (letrec ([call-fn (eval-under-env (call-funexp e) env)]
                            [closr (eval-under-env call-fn env)])
                     (if (closure? closr)
                         (letrec ([fun (closure-fun closr)]
                                  [args (cons (fun-formal fun) (eval-under-env (call-actual e) env))]
                                  [new_env (if (fun-nameopt fun)
                                               (append (list (cons (fun-nameopt fun) fun) args) (closure-env closr))
                                               (cons args (closure-env closr)))])
                           (eval-under-env (fun-body (closure-fun closr)) new_env))
                         (error "Could not evaluate to a closure.")))]
        [(apair? e) (let ([v1 (eval-under-env (apair-e1 e) env)]
                          [v2 (eval-under-env (apair-e2 e) env)])
                      (apair v1 v2))]
        [(closure? e) e]
        [(aunit? e) e]
        [(fun? e) (closure env e)]
        [(ifaunit? e) (let ([v1 (eval-under-env (ifaunit-e1 e) env)])
                        (if (aunit? v1)
                            (ifaunit-e2 e)
                            (ifaunit-e3 e)))]
        [(fst? e) (let ([v1 (eval-under-env (fst-e e) env)])
                    (if (apair? v1) (apair-e1 v1) (error "Argument should be an apair")))]
        [(snd? e) (let ([v1 (eval-under-env (snd-e e) env)])
                    (if (apair? v1) (apair-e2 v1) (error "Argument should be an apair")))]
        [(isaunit? e) (if (aunit? (eval-under-env (isaunit-e e) env)) (int 1) (int 0))]
        [(mlet*? e) (mlet-fun (mlet*-lstlst e) (mlet*-e2 e) env)]
        [(ifeq? e) (let ([v1 (eval-under-env (ifeq-e1 e) env)]
                         [v2 (eval-under-env (ifeq-e2 e) env)])
                     (if (and (int? v1) (int? v2))
                         (if (= (int-num v1) (int-num v2))
                             (eval-under-env (ifeq-e3 e) env)
                             (eval-under-env (ifeq-e4 e) env))
                         (error "Cannot do equal with two non-ints!")))]
        [#t (error (format "bad MUPL expression: ~v" e))]))

;; Do NOT change
(define (eval-exp e)
  (eval-under-env e null))
        
;; Problem 3

(define (mlet-fun lstlst e2 env)
  (define (aux lstlst acc)
    (cond [(null? lstlst) (eval-under-env e2 acc)]
          [#t (aux (cdr lstlst) (cons (cons (car (first lstlst)) (eval-under-env (cdr (first lstlst)) acc)) acc))]))
  (aux lstlst env))

;; Problem 4

(define mupl-map (fun "map" "fun"
                               (fun "aux" "lst"
                                    (ifeq (int 1) (isaunit (var "lst"))
                                          (aunit)
                                          (apair (call (var "fun") (fst (var "lst"))) (call (var "aux") (snd (var "lst"))))))))

;(apair (call (var "fun") (fst (var "lst")))
                                          
                                          
(define mupl-mapAddN
  (fun #f "i"
       (mlet "map" mupl-map
             (call (var "map") (fun "add" "x" (add (var "x") (var "i")))))))
  
  ;"CHANGE (notice map is now in MUPL scope)"))

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
