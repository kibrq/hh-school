import random as r


def gen(N):
    for _ in range(N):
        yield r.randint(1, 100)


N = 100
expr = (r.randint(1, 100) for _ in range(N))
