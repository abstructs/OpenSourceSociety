def printPyramid(n):  
    for i in range(1, n + 1):
        for j in range(n - i):
            print(" ", end="")
        for j in range(i):
            print("#", end="")
        print("  ", end="")
        
        # for i in range(1, n + 1):
        for j in range(i):
            print("#", end="")
        print("")
def get_int():
    while True:
        print("Please enter a number between 0 and 23: ", end="")
        num = int(input())
        if num > 0 and num <= 23:
            return num
    
    
def main():
    i = get_int()
    
    printPyramid(i)
    print("")
if __name__ == "__main__":
    main()