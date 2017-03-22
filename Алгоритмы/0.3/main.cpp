/*
#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>

using namespace std;

int main() {
    int check = 0, size, N;

    ifstream fin("input.txt");
    ofstream fout("output.txt");

    fin >> N;

    vector<int> arrayOfSizes;

    fin >> size;
    arrayOfSizes.push_back(size);
    while (fin >> size) {
        if (check % 2 == 0) {
            arrayOfSizes.push_back(size);
        }
        check++;
    }

    long long **matrix = new long long *[N];
    for (int i = 0; i < arrayOfSizes.size(); ++i) {
        matrix[i] = new long long[N]{};
    }

    for (int i = 0; i < N; i++) {
        matrix[i][i] = 0;
    }

    for (int l = 1; l < N; l++) {
        for (int i = 0; i < N - l; i++) { // rows
            int j = i + l; // columns
            matrix[i][j] = LONG_LONG_MAX;
            for (int k = i; k < j; k++) {
                matrix[i][j] = std::min(matrix[i][j],
                                   matrix[i][k] + matrix[k + 1][j] +
                                   arrayOfSizes[i] * arrayOfSizes[k + 1] * arrayOfSizes[j + 1]);
            }
        }
    }

//    for (int d = 1; d < N; ++d) {
//        for (int i = 0; i < N - d; ++i) {
//            int j = i + d;
//            matrix[i][j] = INT_MAX;
//
//            for (int k = 0; k < 2; ++k) {
//                int q =
//            }
//        }
//    }

    for (long long i = 0; i < N; ++i) {
        for (long long j = 0; j < N; ++j) {
            cout << matrix[i][j] << " ";
        }
        cout << endl;
    }

    fout << matrix[0][N - 1];

    return 0;
}*/

#include <iostream>
#include <fstream>
#include <vector>
#include <limits>

using namespace std;

int main() {
    int check = 0, size, N;

    ifstream fin("input.txt");
    ofstream fout("output.txt");

    fin >> N;

    vector<int> arrayOfSizes;

    fin >> size;
    arrayOfSizes.push_back(size);
    while (fin >> size) {
        if (check % 2 == 0) {
            arrayOfSizes.push_back(size);
        }
        check++;
    }

    long long **matrix = new long long* [N + 1];
    for (int i = 0; i < arrayOfSizes.size(); ++i) {
        matrix[i] = new long long[N + 1]{};
    }

    for (int i = 1; i <= N; i++) {
        matrix[i][i] = 0;
    }

    for (int l = 2; l <= N; l++) {
        for (int i = 1; i <= N - l + 1; i++) {
            int j = i + l - 1;
            matrix[i][j] = numeric_limits< long long >::max();
            for (int k = i; k < j; k++) {
                matrix[i][j] = min(matrix[i][j],
                                    matrix[i][k] + matrix[k + 1][j] +
                                            arrayOfSizes[i - 1] * arrayOfSizes[k] * arrayOfSizes[j]);
            }
        }
    }

//    for (int l = 1; l < N; l++) {
//        for (int i = 0; i < N - l; i++) { // rows
//            int j = i + l; // columns
//            matrix[i][j] = LONG_LONG_MAX;
//            for (int k = i; k < j; k++) {
//                matrix[i][j] = std::min(matrix[i][j],
//                                        matrix[i][k] + matrix[k + 1][j] +
//                                        arrayOfSizes[i] * arrayOfSizes[k + 1] * arrayOfSizes[j + 1]);
//            }
//        }
//    }

//    for (int i = 0; i <= N; ++i) {
//        for (int j = 0; j <= N; ++j) {
//            cout << matrix[i][j] << " ";
//        }
//        cout << endl;
//    }

    fout << matrix[1][N];

    fin.close();
    fout.close();
    return 0;
}