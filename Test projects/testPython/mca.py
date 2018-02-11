import numpy as np
from matplotlib import pyplot as plt
from sympy import *

s = 4.0
n_count = 20

a = 1
b = 2

err_step = float(b - a) / n_count

x_plot = np.arange(a, b, 0.01)


def phi0(x):
    return x ** 2 - 2 * x + 1


def phi1(x):
    return (1 - x) ** 2 * (2 - x)


def phi2(x):
    return (1 - x) ** 3 * (2 - x)


def get_global_err(fun):
    global_err = 0
    for i in np.arange(1, n_count):
        curr_x = a + i * err_step
        sol = solution(curr_x)
        approx_sol = fun(curr_x)
        curr_err = abs(sol - approx_sol)
        global_err = max(global_err, curr_err)
    return global_err


def get_plot(fun, name):
    sol_plot = plt.plot(x_plot, solution(x_plot), label='solution')
    approx_plot = plt.plot(x_plot, fun(x_plot), label=name)
    plt.setp(sol_plot, color='b')
    plt.setp(approx_plot, color='g')

    plt.legend()
    plt.show()
    pass


def solution(x):
    return (x ** 2 - 1.0) * (3.0 * s * (x ** 2 - 4.0) + 8.0) / 24.0


h = 1.0 / (n_count)


def ritz():
    def p(x):
        return 1.0 / x

    def q(x):
        return 0

    def f(x):
        return -s * x

    x_sym = Symbol("x_sym")
    integr_limits = (x_sym, a, b)

    a_matr = np.zeros((2, 2))
    a_matr[0, 0] = Integral(
        p(x_sym) * diff(phi1(x_sym), x_sym) * diff(phi1(x_sym), x_sym) + q(x_sym) * phi1(x_sym) * phi1(x_sym),
        integr_limits)
    a_matr[0, 1] = a_matr[1, 0] = Integral(
        p(x_sym) * diff(phi1(x_sym), x_sym) * diff(phi2(x_sym), x_sym) + q(x_sym) * phi1(x_sym) * phi2(x_sym),
        integr_limits)
    a_matr[1, 1] = Integral(
        p(x_sym) * diff(phi2(x_sym), x_sym) * diff(phi2(x_sym), x_sym) + q(x_sym) * phi2(x_sym) * phi2(x_sym),
        integr_limits)

    b_vec = np.zeros((2, 1))
    b_vec[0, 0] = Integral(
        f(x_sym) * phi1(x_sym) - p(x_sym) * diff(phi1(x_sym), x_sym) * diff(phi0(x_sym), x_sym) - q(x_sym) * phi1(
            x_sym) * phi0(x_sym),
        integr_limits)
    b_vec[1, 0] = Integral(
        f(x_sym) * phi2(x_sym) - p(x_sym) * diff(phi2(x_sym), x_sym) * diff(phi0(x_sym), x_sym) - q(x_sym) * phi2(
            x_sym) * phi0(x_sym),
        integr_limits)

    c_vec = np.linalg.solve(a_matr, b_vec)
    c1, c2 = c_vec[0][0], c_vec[1][0]

    def ritz_solution(x):
        return phi0(x) + c1 * phi1(x) + c2 * phi2(x)

    global_err = get_global_err(ritz_solution)

    print('A = ', a_matr)
    print ('b = ', b_vec)
    print('c = ', c_vec)
    print('ritz global err = ', global_err)

    get_plot(ritz_solution, 'ritz')
    pass


def galerckin():
    def p(x):
        return -1.0 / x

    def q(x):
        return 0

    def f(x):
        return -s * x ** 2

    x = Symbol('x')
    A_matr = np.zeros((2, 2))

    global b
    A_matr[0, 0] = Integral((diff(phi1(x), x, b) + p(x) * diff(phi1(x), x) + q(x) * phi1(x)) * phi1(x), (x, a, b))
    A_matr[0, 1] = Integral((diff(phi2(x), x, b) + p(x) * diff(phi2(x), x) + q(x) * phi2(x)) * phi1(x), (x, a, b))
    A_matr[1, 0] = Integral((diff(phi1(x), x, b) + p(x) * diff(phi1(x), x) + q(x) * phi1(x)) * phi2(x), (x, a, b))
    A_matr[1, 1] = Integral((diff(phi2(x), x, b) + p(x) * diff(phi2(x), x) + q(x) * phi2(x)) * phi2(x), (x, a, b))

    b_vec = np.zeros((2, 1))
    b_vec[0, 0] = -Integral(((diff(phi0(x), x, b) + p(x) * diff(phi0(x), x) + q(x) * phi0(x) + f(x)) * phi1(x)),
                            (x, a, b))
    b_vec[1, 0] = -Integral(((diff(phi0(x), x, b) + p(x) * diff(phi0(x), x) + q(x) * phi0(x) + f(x)) * phi2(x)),
                            (x, a, b))
    c = np.linalg.solve(A_matr, b_vec)

    c1, c2 = c[0][0], c[1][0]

    def galerckin_solution(x):
        return phi0(x) + c1 * phi1(x) + c2 * phi2(x)

    global_err = get_global_err(galerckin_solution)
    print('galerckin global err = ', global_err)

    get_plot(galerckin_solution, 'galerckin')
    pass


def shooting_method():
    def deriv(x, u):
        return 1.0 / x * u + s * x ** 2

    def mrk(N, h, start, x):
        corr = np.zeros(2 * N + 1)
        corr[0] = start
        for i in range(0, 2 * N):
            x0 = x[i]
            y0 = corr[i]
            k1 = deriv(x0, y0)
            k2 = deriv(x0 + h / 2.0, y0 + h * k1 / 2.0)
            k3 = deriv(x0 + h / 2.0, y0 + h * k2 / 2.0)
            k4 = deriv(x0 + h, y0 + h * k3)
            corr[i + 1] = (y0 + (k1 + 2.0 * k2 + 2.0 * k3 + k4) * h / 6.0)
        dercorr = np.zeros(N + 1)
        dercorr[0] = 0

        i = 0

        for j in range(0, N):
            y0 = dercorr[j]
            k1 = corr[i]
            k2 = corr[i + 1]
            k3 = corr[i + 1]
            k4 = corr[i + 2]
            dercorr[j + 1] = (y0 + (k1 + 2.0 * k2 + 2.0 * k3 + k4) * 2 * h / 6.0)
            i = i + 2

        return dercorr

    N = 21
    a = 1
    b = 2
    alpha = -2
    delta = -2
    h = (b - a) / (1 + 2 * N)

    x = np.arange(a, b, h)
    shot1 = mrk(N, h, alpha, x)
    shot2 = mrk(N, h, alpha + delta, x)
    shift_alpha = (1 - shot1[N]) / (shot2[N] - shot1[N]) * delta
    shot3 = mrk(N, h, alpha + shift_alpha, x)

    x = np.arange(a, b, h * 2)
    plt.plot(x, shot1, label='shoot1')
    plt.plot(x, shot2, label='shoot2')
    plt.plot(x, shot3, label='shoot3')

    plt.plot(x, solution(x), label='solution')
    plt.legend()
    plt.show()
    print('shooting global error = ', (max(abs(solution(x) - shot3))))

    pass


if __name__ == '__main__':
    # ritz()
    # galerckin()
    shooting_method()
