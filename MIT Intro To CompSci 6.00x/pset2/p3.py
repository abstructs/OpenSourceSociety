# Same problem as p2.py but in O(log(N)) complexity using bisection search
"""
Write a program that uses these bounds and bisection search 
(for more info check out the Wikipedia page on bisection search) 
to find the smallest monthly payment to the cent (no more multiples 
of $10) such that we can pay off the debt within a year. Try it 
out with large inputs, and notice how fast it is (try the same large 
inputs in your solution to Problem 2 to compare!). Produce the same 
return value as you did in Problem 2.
"""

def payDebtFixed(principal, annual_rate, n = 11, fixed_payment = 10):
    if n == 0:
        return principal - fixed_payment
    monthly_rate = (annual_rate / 12)
    unpaid = principal - fixed_payment
    new_principal = unpaid + (monthly_rate * unpaid)
    
    return payDebtFixed(new_principal, annual_rate, n - 1, fixed_payment)
    
def findFixedPayment(principal, annual_rate):
    owed = principal
    monthly_rate = (annual_rate / 12)
    interest = (principal * monthly_rate)
    
    high_payment = (principal * (pow((1.0 + monthly_rate), 12))) / 12.0
    low_payment = principal / 12.0
    epsilon = 0.01 
    fixed_payment = 0
    
    while True:
        fixed_payment = (low_payment + high_payment) / 2
        owed = float(format(payDebtFixed(principal, annual_rate, 11, fixed_payment), '.2f'))
        if owed > epsilon:
            low_payment = fixed_payment
        elif owed < epsilon:
            high_payment = fixed_payment
        elif owed == epsilon:
            break
    return float(format(fixed_payment, '.2f'))

balance = 10000
annualInterestRate = .12
 
print("Lowest Payment: " + str(findFixedPayment(balance, annualInterestRate)))

    
    