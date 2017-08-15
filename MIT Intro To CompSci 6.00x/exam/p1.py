def prod(lst, acc):
    if not lst:
        return acc
    else:
        return prod(lst[1:], acc + int(lst[0]))

def sum_digits(s):
    """ assumes s a string
        Returns an int that is the sum of all of the digits in s.
          If there are no digits in s it raises a ValueError exception. """
    nums = list(filter(lambda x: x.isdigit(), s))
    if not nums:
        raise ValueError
    else:
        return prod(nums, 0)
