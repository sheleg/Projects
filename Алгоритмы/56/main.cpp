#include <iostream>
#include <fstream>
#include <algorithm>
#include <vector>

using namespace std;

//vector<long long> vector;
//vector<long long> elementsOfHeap;

bool isHeap(long long* elementsOfHeap, int size);

int main()
{
    ifstream fin("input.txt");
    ofstream fout("output.txt");

    long long element = -1;
    int size = 0, l = 1;

    fin >> size;

    long long* elementsOfHeap = new long long [size + 1]{};

    while (fin >> element) {
        elementsOfHeap[l] = element;
        l++;
    }

    if (isHeap(elementsOfHeap, size)) {
        fout << "Yes";
    } else {
        fout << "No";
    }

    delete[]elementsOfHeap;
    fin.close();
    fout.close();
    return 0;
}

bool isHeap(long long* elementsOfHeap, int size) {

    for(int parent = 1, childLeft, childRight; ; parent++) {
        childLeft = 2 * parent;
        childRight = 2 * parent + 1;
        if (childRight > size) {
            if (childLeft <= size) {
                if (elementsOfHeap[childLeft] < elementsOfHeap[parent])
                    return false;
            }
            break;
        }

        if (elementsOfHeap[childLeft] < elementsOfHeap[parent]
            || elementsOfHeap[childRight] < elementsOfHeap[parent]) {
            return false;
        }
    }
    return true;
}