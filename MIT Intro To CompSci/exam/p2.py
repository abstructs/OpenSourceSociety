def max_val(t): 
    """ t, tuple or list
        Each element of t is either an int, a tuple, or a list
        No tuple or list is empty
        Returns the maximum int in t or (recursively) in an element of t """ 
    def flatten(t):
        for i in t:
            if isinstance(i, (list, tuple)):
                for j in flatten(i):
                    yield j
            else:
                yield i

    return max(list(flatten(t)))