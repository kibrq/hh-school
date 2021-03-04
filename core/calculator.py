from decorators import cache_decorator
import operator as op


def tooperation(operation):
    if operation == '+':
        return op.add
    if operation == '-':
        return op.sub
    if operation == '/':
        return op.truediv
    if operation == '*':
        return op.mul
    if operation == '**':
        return op.pow
    raise ValueError('Unsupported operation: ' + operation)


@cache_decorator
def calculator(a, b, operation):
    return tooperation(operation)(a, b)


if __name__ == '__main__':
    while True:
        try:
            a = int(input('Введите число: '))
            b = int(input('Введите число: '))
            operation = input('Введите операцию: ')

            print('Результат: ', calculator(a, b, operation))
        except ValueError as v:
            print(v)
