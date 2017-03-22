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

    int N, element;

    fin >> N;

    int* canonicalForm = new int[N + 1];
    for (int i = 0; i <= N; ++i) {
        canonicalForm[i] = 0;
    }

    for (int i = 1; i <= N; ++i) {
        for (int j = 1; j <= N; ++j) {
            fin >> element;
            if (element == 1) {
                canonicalForm[j] = i;
            }
        }
    }

    for (int i = 1; i <= N; ++i) {
        fout << canonicalForm[i] << " ";
    }

    delete[] canonicalForm;

    fin.close();
    fout.close();
    return 0;
}