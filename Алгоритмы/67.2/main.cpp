#include <iostream>
#include <fstream>
#include <vector>
#include <queue>

using namespace std;
int ID = 1;
vector<vector<int>> matrix;
vector<bool> used;
vector<int> indexVector;

void DFS(int v) {
    used[v] = true;
    if (indexVector[v]==0)
        indexVector[v] = ID++;
    for (int i = 0; i < used.size();i++) {
        if (matrix[v][i] && !used[i]) {
            DFS(i);
        }
    }
}

int main() {
    std::ifstream fin("input.txt");
    std::ofstream fout("output.txt");
    int size;
    fin >> size;

    int val;

    used.resize(size, false);
    indexVector.resize(size, 0);

    for (int i = 0; i < size; i++) {
        vector <int> temp;
        for (int j = 0; j < size; j++) {
            fin >> val;
            temp.push_back(val);
        }
        matrix.push_back(temp);
    }
    fin.close();
    for (int i = 0; i < size;i++) {
        DFS(i);
    }

    for (int i : indexVector)
        fout << i << " ";
    fout.close();
    return 0;
}