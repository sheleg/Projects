import numpy as np

size = 2

def get_input(path):
    arr = []
    with open(path) as f:
        # s = f.read()
        arr = [ch.strip() for ch in f.readlines()]#TODO: формат многочлена неприводимого

    if len(arr) == 3:
        if len(arr[0]) < len(arr[1]):
            arr[0] = "0" * (len(arr[1]) - len(arr[0])) + arr[0]
        if len(arr[0]) > len(arr[1]):
            arr[1] = "0" * (len(arr[0]) - len(arr[1])) + arr[1]
    return arr


def get_polynom(path):
    with open(path) as f:
        # s = f.read()
        arr = [int(ch) for ch in f.readline().strip()]

    return arr


def plus(polynom1, polynom2):
    result = ""
    for i in range(0, len(polynom1)):
        # print(type(int(polynom1[0])))
        # print(int(polynom1[0]))
        result += str(np.mod(int(polynom1[i]) + int(polynom2[i]), size))
    print('result = ', result)
    pass


def increase(polynom1, polynom2):
    pass


def division(polynom1, polynom2):
    pass


def inverse(polynom):
    pass


def degree(polynom, degree):
    pass


def operation(arr):
    if len(arr) == 2:
        if arr[1] == "^-1":
            inverse(arr[0])
        else:
            degree(arr[0], arr[1][1:])
    else:
        if arr[2] == "+":
            plus(arr[0], arr[1])
        elif arr[2] == "*":
            increase(arr[0], arr[1])
        elif arr[2] == "/":
            division(arr[0], arr[1])


if __name__ == '__main__':
    print(get_polynom("polynom.txt"))
    arr = get_input("input.txt")
    print(arr)
    operation(arr)