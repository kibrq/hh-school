def cache_decorator(func):
    d = {}
    def inner(*args, **kwargs):
        if args not in d:
            d[args] = func(*args, **kwargs)
        return d[args]
    return inner

