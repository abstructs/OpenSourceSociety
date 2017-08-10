import cs50

def get_card_type(card_number):
    str_card = str(card_number)
    card = ""
    
    if len(str_card) == 15:
        card = "AMERICAN EXPRESS"
    elif len(str_card) == 16 and str_card[0] != "4":
        card = "MASTER CARD"
    elif len(str_card) == 13 or len(str_card) == 16:
        card = "VISA"
    else:
        card = "INVALID"
        
    return card
    
def is_valid_card(card_number):
    card_str = str(card_number)
    
    # get odd and even digits
    odd_digits = []
    even_digits = []
    for i in range(len(card_str)):
        if i % 2 == 0:
            odd_digits.append(int(card_str[i]))
        else:
            even_digits.append(int(card_str[i]) * 2)
    sum_arr = []   
    for i in even_digits:
        for j in str(i):
            sum_arr.append(int(j))
            
    print(str(sum(sum_arr) + sum(odd_digits)))
    
def main():
    print("Number: ", end="")
    
    while True:
        credit_card = cs50.get_int()
        
        if type(credit_card) is int:
            break
        print("Retry: ", end="")
    
    print(get_card_type(credit_card))
    

    
    
    
if __name__ == "__main__":
    main()