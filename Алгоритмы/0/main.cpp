#include <iostream>
#include <fstream>
#include <unordered_set>

using namespace std;

int main() {
    ifstream fin("input.txt");
    ofstream fout("output.txt");

    long long sum = 0, element;
    unordered_set<long long> setElements;

    while (fin >> element) {
        setElements.insert(element);
    }

    for (unordered_set<long long>::iterator iter = setElements.begin(); iter != setElements.end(); iter++) {
        sum += *iter;
    }

    fout << sum;

    fin.close();
    fout.close();
    return 0;
}