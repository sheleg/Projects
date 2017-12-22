import numpy as np


def cut(polyn):
    trim = np.trim_zeros(polyn, 'b')
    return trim if trim.size != 0 else np.array([0])


def plus(polynom1, polynom2, mod_polynom):
    bigger, lesser = (polynom1, polynom2) if len(polynom1) > len(polynom2) else (polynom2, polynom1)
    res = cut(bigger ^ np.pad(lesser, (0, len(bigger) - len(lesser)), 'constant'))
    return mod(res, mod_polynom)


def mult(polynom1, polynom2, mod_polynom):
    mult = np.zeros(len(polynom1) + len(polynom2) - 1, dtype=int)
    for i in range(0, len(polynom1)):
        if polynom1[i] == 1:
            for j in range(0, len(polynom2)):
                if polynom2[j] == 1:
                    mult[i + j] = mult[i + j] ^ 1

    return mod(cut(mult), mod_polynom)


def inverse_pol(pol, mod_polynom):
    prev = pol
    while True:
        next = mod(mult(pol, prev, mod_polynom), mod_polynom)
        if next.size == 1 and next[0] == 1:
            break
        prev = next
    return prev


def mod(divisible, divisor):
    d = divisible.copy()
    divisor_deg = len(divisor)
    ones_indices = np.array([i for i in range(0, divisor_deg) if divisor[i] == 1])

    current_deg = len(d) - divisor_deg
    while current_deg >= 0:
        new_in_divisible_indices = current_deg + ones_indices
        for idx in new_in_divisible_indices:
            d[idx] = d[idx] ^ 1

        d = cut(d)
        current_deg = len(d) - divisor_deg
    return d


def get_good_exponentiation(pol, deg):

    exp = np.zeros((len(pol) - 1) * deg + 1, dtype=int)
    for i in range(0, len(pol)):
        if pol[i] == 1:
            exp[i * deg] = 1
    return exp


def pow_pol(pol, degree, mod_polynom):
    binary = list('{0:0b}'.format(degree))
    len_binary = len(binary)

    two_degrees = [(len_binary - 1 - idx) for idx in range(0, len_binary) if binary[idx] == '1']

    mult_polynoms = [get_good_exponentiation(pol, 2 ** d) for d in two_degrees]

    res = mult_polynoms[0]
    for p in mult_polynoms[1:]:
        res = mult(res, p)

    return mod(res, mod_polynom)


def calculate(operation, polynom, param, mod_polynom):
    if operation == '+':
        return plus(polynom, param, mod_polynom)
    if operation == '*':
        return mult(polynom, param, mod_polynom)
    if operation == '/':
        return mod(polynom, param)
    if operation == '^':
        if param == -1:
            return inverse_pol(polynom, mod_polynom)
        else:
            return pow_pol(polynom, param, mod_polynom)
