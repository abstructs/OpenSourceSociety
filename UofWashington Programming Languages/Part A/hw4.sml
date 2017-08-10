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
fun first_answer f lst =
  case lst of
      [] => raise NoAnswer
     |x::xs  => case f x of 
		    SOME v => v
		   |NONE => first_answer(f) xs

(* 8 *)
fun all_answers f lst =
  let
      fun a_answers (l, acc) =
	case l of
	    [] => SOME acc
	   |x::xs => case f x of
			 NONE => NONE
			|SOME v => a_answers(xs, acc @ v)
  in
      a_answers(lst, [])
  end
		  
(* 9 *)
(* Count wildcards in a list *)
fun count_wildcards (p) =
  g (fn () => 1) (fn (x) => 0) p
      
fun count_wild_and_variable_lengths p =
  g (fn () => 1) (fn (x)=> String.size(x)) p

fun count_some_var (str,  p) =
  g (fn () => 0) (fn (x) => if str = x then 1 else 0) p

(* 10 *)
fun check_pat (p) =
  let
      fun all_strings pattern =
	case pattern of
	    Variable x => [x] 
	   |TupleP pt => foldl (fn (x,y) => all_strings(x) @ y) [] pt
	   |_  => []
		      
      fun is_unique (lst : string list) =
	case lst of
	    [] => true
	  | x::xs => not(List.exists (fn y => x = y) xs) andalso is_unique(xs)
  in 
      is_unique(all_strings(p))
  end
      
(* 11 *)
fun match pair =
  case pair of
      (_, Wildcard) => SOME []
    | (v, Variable s) => SOME [(s, v)]
    |(Unit, UnitP)  => SOME []
    |(Const i, ConstP j) => if i = j then SOME [] else NONE
    |(Tuple v, TupleP p) => if List.length(v) = List.length(p)
			    then all_answers match (ListPair.zip(v, p))
			    else NONE
    |(Constructor(s1,v), ConstructorP(s2, p)) => if s1 = s2 then match (v, p) else NONE
    |(_, _) => NONE
	       
      
(* 12 *)
fun first_match value plst =
  SOME (first_answer (fn x => match(value, x)) plst) handle NoAnswer => NONE
      
  
