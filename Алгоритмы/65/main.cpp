#include <iostream>
#include <fstream>
#include <vector>

using namespace std;

int main() {
    ifstream fin("input.txt");
    ofstream fout("output.txt");

    if (!fin) {
        cout << "No stream";
        return 0;
    }

    int N, M, pairFirst, pairSecond;

    fin >> N;
    fin >> M;
    vector<int>* adjacencyList = new vector<int>[N];

    while (fin >> pairFirst) {
        fin >> pairSecond;
        adjacencyList[pairFirst - 1].push_back(pairSecond);
        adjacencyList[pairSecond - 1].push_back(pairFirst);
    }

    for (int i = 0; i < N; ++i) {
        fout << adjacencyList[i].size() << " ";
        if (adjacencyList[i].size() != 0) {
            for (int j = 0; j < adjacencyList[i].size(); ++j) {
                fout << adjacencyList[i][j] << " ";
            }
        }
        fout << endl;
    }

    delete[] adjacencyList;
    fin.close();
    fout.close();
    return 0;
}