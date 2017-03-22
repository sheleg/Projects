#include <iostream>
#include <vector>
#include <cmath>

int main() {
    int size = 5;

    std::vector<std::vector<long double> > matrix = {{7,   2.5, 2,   1.5, 1,   1},
                                           {2.5, 8,   2.5, 2,   1.5, 1},
                                           {2,   2.5, 9,   2.5, 2,   1},
                                           {1.5, 2,   2.5, 10,  2.5, 1},
                                           {1,   1.5, 2,   2.5, 11,  1}
    };

    int lambda = 5;
    for (int k = 0; k < size; ++k) {
        matrix[k][k] += lambda;
    }

    for (int i = 0; i < size; ++i) {
        for (int j = 0; j < size + 1; ++j) {
            std::cout.flags(std::ios::left);
            std::cout.width(5);
            std::cout << matrix[i][j] << "   ";
        }
        std::cout << std::endl;
    }

    std::cout << "Введите точность вычислений:";
    long double eps;
    std::cin >> eps;

    std::vector<long double> previous(size, 0.0);

    while (true) {
        std::vector<long double> current(size);
        for (int i = 0; i < size; i++) {
            current[i] = matrix[i][size];
            for (int j = 0; j < size; j++) {
                if (j < i) {
                    current[i] -= matrix[i][j] * current[j];
                }

                if (j > i) {
                    current[i] -= matrix[i][j] * previous[j];
                }
            }
            current[i] /= matrix[i][i];
        }

        long double error = 0.0;

        for (int i = 0; i < size; i++) {
            error += abs(current[i] - previous[i]);
        }

        if (error < eps) {
            break;
        }

        previous = current;
    }

    for (int i = 0; i < size; i++) {
        std::cout << previous[i] << " ";
    }

    return 0;
}