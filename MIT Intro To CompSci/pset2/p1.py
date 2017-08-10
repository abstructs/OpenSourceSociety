

def payDebt(principal, annual_rate, min_month_rate):
    """ 
    Write a program to calculate the credit card balance after one year if 
    a person only pays the minimum monthly payment required by the credit 
    card company each month. 
    """

    monthly_rate = annual_rate / 12
    new_principal = principal
    
    for i in range(12):
        min_payment = new_principal * min_month_rate
        unpaid = new_principal - min_payment
        interest = monthly_rate * unpaid
        new_principal = unpaid + interest
    return float(format(new_principal, '.2f'))
        

balance = 10000
annualInterestRate = .12
monthlyPaymentRate = annualInterestRate / 12

print(str(payDebt(balance, annualInterestRate, monthlyPaymentRate)))

