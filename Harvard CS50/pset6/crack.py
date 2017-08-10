import crypt
from hmac import compare_digest as compare_hash
from itertools import permutations

def password(hashed):
    alphabet = "abcdefghijklmnopqrstuvwxyz" + "abcdefghijklmnopqrstuvwxyz".upper()
    
    for a in alphabet:
        if compare_hash(hashed, crypt.crypt(a, hashed)):
            return a
            
    for a in alphabet:   
        for b in alphabet:
            if compare_hash(hashed, crypt.crypt(a + b, hashed)):
                return a + b
    
    for a in alphabet:   
        for b in alphabet:
            for c in alphabet:
                if compare_hash(hashed, crypt.crypt(a + b + c, hashed)):
                    return a + b + c
                    
    for a in alphabet:   
        for b in alphabet:
            for c in alphabet:
                for d in alphabet:
                    if compare_hash(hashed, crypt.crypt(a + b + c + d, hashed)):
                        return a + b + c + d

def main():
    hashed = input("Enter a hash: ")
    print(password(hashed))

if __name__ == "__main__":
    main()