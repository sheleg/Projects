#include <iostream>
#include <fstream>
#include <algorithm>
#include <limits.h>

using namespace std;

int *sequence;
int N = 0;

int getLowerThen(int *, int);
int getUpperThen(int *, int);
void reverseVector(int*, int);

int main() {
    ios_base::sync_with_stdio(false);
    ofstream fout("output.txt");
    ifstream fin("input.txt");
    fin >> N;

    sequence = new int[N];
    int* lastElementOfSubsequence = new int[N + 1];
    int* indexes = new int[N + 1];
    int* lastElementsOfReversedSubsequence = new int[N + 1];
    int* indexesReversed = new int[N + 1];
    int m = 0;

    for (int i = 0; i < N; i++) {
        fin >> sequence[i];
    }

    for (int i = 1; i <= N; ++i) {
        lastElementOfSubsequence[i] = INT_MAX;
    }
    lastElementOfSubsequence[0] = -1;

    for (int i = 0, j = 0; i < N; i++) {
        j = getLowerThen(lastElementOfSubsequence, i);

        indexes[i] = j;
        if ((sequence[i] < lastElementOfSubsequence[j]) && (lastElementOfSubsequence[j - 1] < sequence[i])) {
            lastElementOfSubsequence[j] = sequence[i];
        }
    }

    for (int i = 0; i < N; i++) {
        lastElementsOfReversedSubsequence[i] = -1;
    }
    lastElementsOfReversedSubsequence[N] = INT_MAX;


    lastElementsOfReversedSubsequence[N - 1] = sequence[N - 1];
    indexesReversed[0] = 0;
    for (int i = 1, j = 0; i < N; i++) {
        j = getUpperThen(lastElementsOfReversedSubsequence, N - i);

        lastElementsOfReversedSubsequence[j] = sequence[N - i];
        indexesReversed[i] = N - j;
        if (indexesReversed[i] > m)
            m = indexesReversed[i];
        else
            indexesReversed[i] = m;
    }

    int max = 1;
    for (int i = 0; i < N; i++) {
        if ((indexesReversed[N - i - 1] + indexes[i]) > max) {
            max = indexesReversed[N - i - 1] + indexes[i];
        }
    }

    fout << max;

    fin.close();
    fout.close();
    return 0;
}

int getLowerThen(int *searchArray, int element) {
    return (int) (lower_bound(searchArray, searchArray + N + 1, sequence[element]) - searchArray);
}

int getUpperThen(int *searchArray, int element) {
    return int(upper_bound(searchArray,
                           searchArray + N + 1, sequence[element]) - searchArray - 1);
}

void reverseVector(int* arrayToReverse, int size) {
    reverse(arrayToReverse, arrayToReverse + size);
}


