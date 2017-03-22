#include <iostream>
#include <queue>
#include <fstream>


using namespace std;

int main() {
    ifstream fin("input.txt");
    ofstream fout("output.txt");

    int N;

    fin >> N;


    bool *used = new bool[N];
    for (int i = 0; i < N; ++i) {
        used[i] = false;
    }

    int *index = new int[N] {};

    int **graph = new int *[N];
    for (int i = 0; i < N; ++i) {
        graph[i] = new int[N]{};
    }

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            fin >> graph[i][j];
        }
    }

//    for (int i = 0; i < N; ++i) {
//        for (int j = 0; j < N; ++j) {
//            cout << graph[i][j] << "  ";
//        }
//        cout << endl;
//    }

    int count = 1;

    for (int i = 0; i < N; i++) {
        queue<int> queue;
        int currentVertex = i;
        queue.push(currentVertex);

        if (!used[currentVertex]) {
            used[currentVertex] = true;
            index[currentVertex] = count++;
        }

        while (!queue.empty()) {
            int v = queue.front();
            queue.pop();
            for (int j = 0; j < N; j++) {
                if (graph[v][j] == 1) {
                    if (!used[j]) {
                        used[j] = true;
                        queue.push(j);
                        index[j] = count++;
                    }
                }
            }
        }
    }

    for (int i = 0; i < N; i++) {
        if (index[i] == 0) {
            index[i] = count++;
        }
        fout << index[i] << " ";
//        cout << index[i] << " ";
    }

    fin.close();
    fout.close();
    return 0;
}
//
//0 1 1 0 0 0 0
//0 0 0 0 1 0 0
//0 1 0 1 1 0 0
//0 1 0 0 1 0 0
//0 0 0 0 0 1 0
//0 0 0 1 0 0 0
//0 0 0 0 0 1 0
