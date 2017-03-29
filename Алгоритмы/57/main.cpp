#include <iostream>
#include <fstream>
#include <vector>

#define EMPTY -1

using namespace std;

vector<long long> hashTable;

long long hashCode(long long, int, long long, long long, long long);
bool hashCodeInsert(long long int, long long int i);

int main() {
    int i;
    long long m = 0;
    long long c = 0;
    long long N = 0;
    long long tempNumber = 0;

    ifstream fin("input.txt");
    ofstream fout("output.txt");

    fin >> m;
    fin >> c;
    fin >> N;

    hashTable.resize(m, EMPTY);

    while (fin >> tempNumber) {
        i = 0;
        while (!hashCodeInsert(tempNumber, hashCode(tempNumber, i, m, c, N))) {
            i++;
        }
    }

    for (int i = 0; i < m; ++i) {
        fout << hashTable[i] << " ";
    }

    return 0;
}

long long hashCode(long long x, int attempt, long long m, long long c, long long N) {
    return (((x % m) + c * attempt) % m);
}

bool hashCodeInsert(long long int data, long long int i) {
    if (hashTable[i] == EMPTY || hashTable[i] == data) {
        hashTable[i] = data;
        return true;
    }
    return false;
}