(* Coursera Programming Languages, Homework 3, Provided Code *)

exception NoAnswer

datatype pattern = Wildcard
		 | Variable of string
		 | UnitP
		 | ConstP of int
		 | TupleP of pattern list
		 | ConstructorP of string * pattern

datatype valu = Const of int
	      | Unit
	      | Tuple of valu list
	      | Constructor of string * valu

fun g f1 f2 p =
    let 
	val r = g f1 f2 
    in
	case p of
	    Wildcard          => f1 ()
	  | Variable x        => f2 x
	  | TupleP ps         => List.foldl (fn (p,i) => (r p) + i) 0 ps
	  | ConstructorP(_,p) => r p
	  | _                 => 0
    end

(**** for the challenge problem only ****)

datatype typ = Anything
	     | UnitT
	     | IntT
	     | TupleT of typ list
	     | Datatype of string

(**** you can put all your code here ****)

(* listOf(String) -> listOf(String) *)
(* Produce a string list with only strings that start with a capital *)
fun only_capitals(str_list) =
  (List.filter (fn str => Char.isUpper(String.sub(str, 0))) str_list)

(* 1 *)
(* listOf(String) -> String *)
(* Produce longest string in list *) 
fun longest_string1 (str_list) =
  (foldl (fn (x, y) => if String.size(x) > String.size(y)
		       then x else y) "" str_list)
      
(* 2 *)
(* listOf(String) -> String *)
(* Produce longest string in list *) 
fun longest_string2 (str_list) =
  (foldl (fn (x, y) => if String.size(x) >= String.size(y)
		       then x else y) "" str_list)

(* 4 *)
fun longest_string_helper (some_fun) =
  if some_fun(2, 1)
  then longest_string1
  else longest_string2
	   
val longest_string3 = longest_string_helper(fn (x,y) => x > y);
      
val longest_string4 = longest_string_helper(fn (x,y) => y >= x);

(* 5 *)
(* Return the longest string in the list with a capital as the first letter  *)
val longest_capitalized = longest_string1 o only_capitals;

(* 6 *)
(* String -> String *)
(* Return the string in reversed order *)
val rev_string = String.implode o List.rev o String.explode;

(* 7 *)
(* (A -> B Option) -> listOf(A) -> B *)
fun first_answer (f) =
  fn (lst) => case lst of
		  [] => raise NoAnswer
		 |x::xs  => if isSome(f(x))
			    then x
			    else first_answer(f) xs

(* 8 *)
fun all_answers(f) =
  fn (lst) => let
      fun a_answers (l, acc) =
	case l of
	    [] => SOME acc
	   |x::xs => if isSome(f x) then a_answers(xs, x :: acc) else NONE
  in
      a_answers(lst, [])
  end
	 
(* 9 *)
(* Count wildcards in a list *)
fun count_wildcards (p) =
  g ((fn ()=> 1) (fn ()=>0) p)
