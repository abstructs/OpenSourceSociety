(* Dan Grossman, Coursera PL, HW2 Provided Code *)

(* if you use this function to compare two strings (returns true if the same
   string), then you avoid several of the functions in problem 1 having
   polymorphic types that may be confusing *)
fun same_string(s1 : string, s2 : string) =
    s1 = s2

(* put your solutions for problem 1 here *)
	     
(* 1. a*)
(* String listOf(String) -> listOf(Option) *)					      
fun all_except_option(str, str_list) =
  let
      fun remove_str(str, str_list) =
	case str_list of
	    [] => []
	   |x::xs  => if same_string(x, str)
		      then xs
		      else x :: remove_str(str, xs)
					  
      val reduced_list = remove_str(str, str_list);
  in
      if reduced_list = str_list
      then NONE
      else SOME reduced_list
  end
      
(* 1. b *)
(* String listOf(listOf(String)) -> listOf(listOf(String)) *)
fun get_substitutions1(str_list_list, str) =
  case str_list_list of
      [] => []
     |x::xs => let val filtered_list = all_except_option(str, x);
	       in
		   case filtered_list of
		       SOME x => x @ get_substitutions1(xs, str)
		      |NONE => get_substitutions1(xs, str) 
	       end
(* 1. c *)
(* String listOf(listOf(String)) -> listOf(listOf(String)) *)		   
fun get_substitutions2(str_list_list, str) =
  let
      fun get_subs(str_list_list, str, acc_list) =
	case str_list_list of
	    [] => acc_list
	   |x::xs => let val filtered_list = all_except_option(str, x);
		     in
			 case filtered_list of
			     SOME x => if x = []
				       then get_subs(xs, str, []) 
				       else get_subs(xs, str, x :: acc_list)
			    |NONE => get_subs(xs, str, acc_list) 
		     end
  in
      get_subs(str_list_list, str, [])
  end

      
(* 1. d *)
(* listOf(listOf(String)) Full-Name -> listOf(String) *)
(* Full-Name is each of: First:String, Middle:String, Last:String *)
fun similar_names(str_list_list, name) =
  let
      fun sub_list(str_list) =
	case str_list of
	    [] => []
	   |x::xs => case name of
			 {first,middle,last} => {first=x, middle=middle, last=last} :: sub_list(xs)
							
      fun sub_list_list(str_list_list, acc) =
	case str_list_list of
	    [] => acc
	   |x::xs => sub_list_list(xs, sub_list(x) @ acc)

      val filtered_list = get_substitutions2(str_list_list, (case name of {first,middle,last}=>first))
  in
       name :: sub_list_list(filtered_list, [])
  end	
      
(* you may assume that Num is always used with values 2, 3, ..., 10
   though it will not really come up *)
datatype suit = Clubs | Diamonds | Hearts | Spades
datatype rank = Jack | Queen | King | Ace | Num of int 
type card = suit * rank

datatype color = Red | Black
datatype move = Discard of card | Draw 

exception IllegalMove

	      

(* put your solutions for problem 2 here *)

(* 2. a *)
(* Card -> Color *)
fun card_color(card) =
  case card of
      (suit, rank) => if suit = Clubs orelse suit = Spades
		      then Black
		      else Red
			       
(* 2. b *)
(* Card -> Rank *)
fun card_value(card) =
  case card of
      (suit, Num i) => i
     |(suit, rank) => if rank = Ace
		      then 11
		      else 10

(* 2. c *)
(* listOf(Card) Card Exception -> listOf(Card) *)
fun remove_card(card_list, card_to_remove, e) =
  let
      fun filter_card(card_list, card_to_remove) =
	case card_list of
	    [] => []
	   |card::xs => if card = card_to_remove
			then filter_card(xs, card_to_remove)
			else card :: filter_card(xs, card_to_remove)

      val filtered_list = filter_card(card_list, card_to_remove);
  in
      if filtered_list = card_list
      then raise e
      else filtered_list
  end

(* 2. d *)
(* listOf(Card) -> Boolean *)
fun all_same_color(card_list) =
  let
      fun is_color(card_list, color) =
	case card_list of
	    [] => true
	   |x::xs => card_color(x) = color andalso is_color(xs, color)

      val first_card_color = (case card_list of
				  card::xs => card_color(card))
  in
      is_color(card_list, first_card_color)
  end
				 
(* 2. e *)
(* listOf(Card) -> Number *)
fun sum_cards(card_list) =
  case card_list of
      [] => 0
     |card::rest => card_value(card) + sum_cards(rest)

(* 2. f *)
(* listOf(Card) Number -> Number *)
fun score(card_list, goal) =
  let
      fun get_prelim_score(card_list, goal) =
	let
	    val card_sum = sum_cards(card_list);
	in
	    if card_sum > goal
	    then (card_sum - goal) * 3
	    else goal - card_sum
	end
	    
      val prelim_score = get_prelim_score(card_list, goal);
  in
      if all_same_color(card_list)
      then prelim_score div 2
      else prelim_score
  end
      
(* 2. g *)
(* listOf(Card) listOf(Move) Number -> Number *)
fun officiate(card_list, move_list, goal) =
  let
      fun play_game(held_cards, card_deck, move_list, goal) =
	case move_list of
	    [] => score(held_cards, goal)
	   |Discard(card)::rest_moves  => play_game(
					     remove_card(held_cards, card, IllegalMove),
					     card_deck,
					     rest_moves,
					     goal)
	   |Draw::rest_moves => (case card_deck of
				     [] => score(held_cards, goal)
				    |deck_card::rest_deck  => if sum_cards(deck_card::held_cards) > goal
							      then score(deck_card::held_cards, goal)
							      else play_game(deck_card::held_cards,
									     rest_deck,
									     rest_moves,
									     goal))
							    
  in
      play_game([], card_list, move_list, goal)
  end
