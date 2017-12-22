from polynoms_operations import calculate

import numpy as np

polynom_path = 'polynom.txt'
in_path = 'input.txt'
out_path = 'output.txt'


def get_calc(s):
    polyn = np.array([int(si) for si in s[0]])
    if s[1] == '^':
        param = int(s[2])
    else:
        param = np.array([int(si) for si in s[2]])

    return [s[1], polyn, param]


def main():
    with open(polynom_path, 'r') as plnm_file:
        irred = np.array([int(s) for s in plnm_file.readline()])

    with open(in_path, 'r') as in_file:
        strings = [line.split() for line in in_file.readlines()]
        calculations = [get_calc(s) for s in strings]

    for calc in calculations:
        res = calculate(*calc, irred)
        print('{} {} {} = {}'.format(calc[1], calc[0], calc[2], res))

    pass


if __name__ == '__main__':
    main()
