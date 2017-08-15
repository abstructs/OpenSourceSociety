"""
Now write a program that calculates the minimum fixed monthly 
payment needed in order pay off a credit card balance within 12 months.
By a fixed monthly payment, we mean a single number which does not change 
each month, but instead is a constant amount that will be paid each month.
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
    fixed_payment = 10
    while True:
        owed = payDebtFixed(principal, annual_rate, 11, fixed_payment)
        if owed <= 0:
            break
        fixed_payment += 10
    return fixed_payment
    
balance = 10000
annualInterestRate = .12

print("Lowest Payment: " + str(findFixedPayment(balance, annualInterestRate)))

    
    