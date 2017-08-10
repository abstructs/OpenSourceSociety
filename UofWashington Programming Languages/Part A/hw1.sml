(* This buffer is for text that is not saved, and for Lisp evaluation.
   To create a file, visit it with C-x C-f and enter text in its buffer.

;; Date Date -> Boolean
;; Returns true of first date comes before second
 *)

fun is_older (d1 : (int * int * int), d2 : (int * int * int)) =
  if #1 d1 = #1 d2
  then
      if #2 d1 = #2 d2
      then #3 d1 < #3 d2
      else #2 d1 < #2 d2
  else #1 d1 < #1 d2

fun number_in_month (l : (int * int * int) list, m : int) =
  if null l
  then 0
  else if #2(hd l) = m
  then 1 + number_in_month(tl l, m)
  else 0 + number_in_month(tl l, m)

(* (listOf Dates) (listOf Months) -> Boolean *)
(* Returns true if a month in the months list is in date*)
(* ASSUMES: date isn't  null *)
fun date_in_months(date : (int * int * int), months : int list) =
  if null months
  then false
  else if #2 date = hd months
  then true
  else date_in_months(date, tl months)
		     
fun number_in_months (dates : (int * int * int) list, months : int list) =
  if null dates orelse null months
  then 0
  else if null months
  then 0
  else
      let
	  val sum_months = number_in_months(tl dates, months)
      in
	  if date_in_months(hd dates, months)   
	  then 1 + sum_months
	  else 0 + sum_months
      end

fun dates_in_month(dates : (int * int * int) list, m : int) =
  if null dates
  then []
  else if #2 (hd dates) = m
  then hd dates :: dates_in_month(tl dates, m)
  else dates_in_month(tl dates, m)

fun dates_in_months(dates : (int * int * int) list, months : int list) =
  if null dates
  then []
  else if date_in_months(hd dates, months)
  then hd dates :: dates_in_months(tl dates, months)
  else dates_in_months(tl dates, months)

fun get_nth(los : string list, n : int) =
  if n = 1
  then hd los
  else get_nth(tl los, n - 1)
	      
fun date_to_string( date : (int * int * int)) =
  let
      val dates = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"]
      val year = Int.toString (#1 date)
      val month = get_nth(dates, #2 date)
      val day = Int.toString (#3 date)
  in
      month ^ " " ^ day ^ ", " ^ year
  end

fun number_before_reaching_sum(sum : int, lst : int list) =
  let
      fun before_sum(s : int, l : int list, n : int) =
	if null l
	then 0
	else if s <= 0
	then n + 1
	else if s - hd l > 0 
	then before_sum(s - hd l, tl l, n+1)
	else
	    n
  in
      before_sum(sum, lst, 0)
  end
      
fun what_month(day : int) =
  let
      val months = [31,28,31,30,31,30,31,31,30,31,30,31]
      val month = number_before_reaching_sum(day, months)
  in
      month + 1
  end

fun month_range(day1 : int, day2 : int) =
  if day1 > day2
  then []
  else what_month day1 :: month_range(day1 + 1, day2)

fun oldest(dates : (int * int * int) list) =
  if null dates
  then NONE
  else
      let
	  fun find_oldest(dates : (int * int * int) list) =
	    if null (tl dates)
	    then hd dates
	    else
		let val tail_ans = find_oldest(tl dates)
		in
		    if is_older(hd dates, tail_ans)
		    then hd dates
		    else tail_ans
		end
      in
	  SOME(find_oldest(dates))
      end

fun is_in_list(n : int, l : int list) =
  if null l
  then false
  else if hd l = n
  then true
  else is_in_list(n, tl l)
	  
fun remove_duplicates(l : int list) =
  if null l
  then []
  else if is_in_list(hd l, tl l)
  then remove_duplicates(tl l)
  else hd l :: remove_duplicates(tl l)

fun number_in_months_challenge(dates : (int * int * int) list, m : int list) =
  number_in_months(dates, remove_duplicates m)

fun dates_in_months_challenge(dates : (int * int * int) list, m : int list) =
  dates_in_months(dates, remove_duplicates m)
		 
fun reasonable_date(date : (int * int * int)) =
  let
      val months = [31,28,31,30,31,30,31,31,30,31,30,31]
      fun is_leap_year(date : (int * int * int)) =
	  (#1 date mod 400) = 0 orelse (#1 date mod 4) = 0 andalso not((#2 date mod 100) = 0)

      fun int_get_nth(loi : int list, n : int) =
	if n = 1
	then hd loi
	else int_get_nth(tl loi, n - 1)
  in
      if #1 date > 0 andalso (#2 date > 0 andalso #2 date <= 12) andalso #3 date > 0
      then
	  if #2 date = 2 andalso is_leap_year date
	  then #3 date <= 29
	  else #3 date <= int_get_nth(months, #2 date)
      else false
  end
