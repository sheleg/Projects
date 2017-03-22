#include <iostream>
#include <fstream>
#include <vector>


using namespace std;

int main() {
    ifstream fin("input.txt");
    ofstream fout("output.txt");

    if (!fin) {
        return 0;
    }

    int N, M, pairFirst, pairSecond;

    fin >> N;
    fin >> M;

    int **adjacencyMatrix = new int *[N + 1];
    for (int i = 0; i <= N; ++i) {
        adjacencyMatrix[i] = new int[N + 1];
    }

    for (int i = 0; i <= N; ++i) {
        for (int j = 0; j <= N; ++j) {
            adjacencyMatrix[i][j] = 0;
        }
    }

    while (fin >> pairFirst) {
        fin >> pairSecond;
        adjacencyMatrix[pairFirst][pairSecond] = 1;
        adjacencyMatrix[pairSecond][pairFirst] = 1;
    }

    for (int i = 1; i <= N; ++i) {
        for (int j = 1; j <= N; ++j) {
            fout << adjacencyMatrix[i][j] << "  ";
        }
        fout << endl;
    }

    for (int i = 0; i <= N; ++i) {
        delete[] adjacencyMatrix[i];
    }
    delete[] adjacencyMatrix;

    fin.close();
    fout.close();
    return 0;
}