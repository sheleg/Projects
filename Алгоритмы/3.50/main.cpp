#include <iostream>
#include <fstream>
#include <algorithm>
#include <vector>

using namespace std;

void treeBuild(long long, long long, long long);
void updateValue(long long, long long, long long, long long, long long);
void addToSegment(long long, long long, long long, long long, long long, long long);
long long sumOfSegment(long long, long long, long long, long long, long long);
long long minimumOfSegment(long long, long long, long long, long long, long long);
long long maximumOfSegment(long long, long long, long long, long long, long long);


vector<long long> treeOfSegment;
vector<long long> arrayOfTree;


long long getValue (int v, int tl, int tr, int pos) {
    if (tl == tr)
        return treeOfSegment[v];
    int tm = (tl + tr) / 2;
    if (pos <= tm)
        return treeOfSegment[v] + getValue(v * 2, tl, tm, pos);
    else
        return treeOfSegment[v] + getValue(v * 2 + 1, tm + 1, tr, pos);
}

int main() {
    ifstream fin("input.txt");
    ofstream fout ("output.txt");

    long long N = 0;
    fin >> N;

    arrayOfTree.resize(N, 0);
    treeOfSegment.resize((unsigned long) (4 * N), 0);

    int command = 0;
    while (fin >> command) {
        switch (command) {
            case 1: {
                long long index, value;
                fin >> index;
                fin >> value;
                updateValue(1, 0, N - 1, index, value);
                arrayOfTree[index] = value;
                for (int i = 0; i < arrayOfTree.size(); ++i) {
                    fout << arrayOfTree[i] << "  ";
                }
                fout << "\n\n";
            }break;
            case 2: {
                long long startIndex, endIndex, value;
                fin >> startIndex;
                fin >> endIndex;
                fin >> value;
//                addToSegment(1, 0, N - 1, startIndex, endIndex, value);
                for (long long i = startIndex; i <= endIndex; ++i) {
                    arrayOfTree[i] = value;
                }
                treeBuild(1, 0, N - 1);
                for (int i = 0; i < treeOfSegment.size(); ++i) {
                    fout << treeOfSegment[i] << "  ";
                }
                fout << endl << endl;
            }break;
            case 3: {
                long long startIndex, endIndex;
                fin >> startIndex;
                fin >> endIndex;
                fout << sumOfSegment(1, 0, N - 1, startIndex, endIndex) << endl;
                for (int i = 0; i < arrayOfTree.size(); ++i) {
                    fout << arrayOfTree[i] << "  ";
                }
                fout << endl << endl;
            }break;
            case 4: {
                long long startIndex, endIndex;
                fin >> startIndex;
                fin >> endIndex;
                fout << minimumOfSegment(1, 0, N - 1, startIndex, endIndex) << endl;
                for (int i = 0; i < arrayOfTree.size(); ++i) {
                    fout << arrayOfTree[i] << "  ";
                }
                fout << endl << endl;

            }break;
            case 5: {
                long long startIndex, endIndex;
                fin >> startIndex;
                fin >> endIndex;
                fout << maximumOfSegment(1, 0, N - 1, startIndex, endIndex) << endl;
                for (int i = 0; i < arrayOfTree.size(); ++i) {
                    fout << arrayOfTree[i] << "  ";
                }
                fout << endl << endl;

            }break;
            case 0: {
                return 0;
            }
            default:break;
        }
    }
    return 0;
}

void treeBuild(long long numberOfVertex, long long currentStartIndex, long long currentEndIndex) {

    if (currentStartIndex == currentEndIndex)
        return;
    if (currentEndIndex - currentStartIndex == 1)
        treeOfSegment[numberOfVertex] = arrayOfTree[currentStartIndex];
    else {
        long long tempSegment = (currentStartIndex + currentEndIndex) / 2;
        treeBuild(2 * numberOfVertex, currentStartIndex, tempSegment);
        treeBuild(2 * numberOfVertex + 1, tempSegment, currentEndIndex);
        treeOfSegment[numberOfVertex] = treeOfSegment[2 * numberOfVertex] + treeOfSegment[2 * numberOfVertex + 1];
    }
}

void updateValue(long long numberOfVertex, long long currentStartIndex,
                 long long currentEndIndex, long long positionToUpdate, long long valueToUpdate) {

    if (currentStartIndex == currentEndIndex)
        treeOfSegment[numberOfVertex] = valueToUpdate;
    else {
        long long tempSegment = (currentStartIndex + currentEndIndex) / 2;

        if (positionToUpdate <= tempSegment)
            updateValue(numberOfVertex * 2, currentStartIndex, tempSegment, positionToUpdate, valueToUpdate);
        else
            updateValue(numberOfVertex * 2 + 1, tempSegment + 1, currentEndIndex, positionToUpdate, valueToUpdate);

        treeOfSegment[numberOfVertex] = treeOfSegment[numberOfVertex * 2] + treeOfSegment[numberOfVertex * 2 + 1];
    }
}


void addToSegment(long long numberOfVertex, long long currentStartIndex, long long currentEndIndex,
                  long long necessaryStartIndex, long long necessaryEndIndex, long long valueToAdd) {

    if (necessaryStartIndex > necessaryEndIndex)
        return;
    if (necessaryStartIndex == currentStartIndex && currentEndIndex == necessaryEndIndex)
        treeOfSegment[numberOfVertex] += valueToAdd;
    else {
        long long tempSegment = (currentStartIndex + currentEndIndex) / 2;
        addToSegment(numberOfVertex * 2, currentStartIndex, tempSegment,
                     necessaryStartIndex, min(necessaryEndIndex,tempSegment), valueToAdd);
        addToSegment(numberOfVertex * 2 + 1, tempSegment + 1, currentEndIndex,
                     max(necessaryStartIndex,tempSegment+1), necessaryEndIndex, valueToAdd);
    }
}


long long sumOfSegment(long long numberOfVertex, long long currentStartIndex,
                       long long currentEndIndex, long long necessaryStartIndex, long long necessaryEndIndex) {
    if (necessaryStartIndex > necessaryEndIndex)
        return 0;

    if (necessaryStartIndex == currentStartIndex
        && necessaryEndIndex == currentEndIndex)
        return treeOfSegment[numberOfVertex];

    long long tempSegment = (currentStartIndex + currentEndIndex) / 2;

    return sumOfSegment(numberOfVertex * 2, currentStartIndex, tempSegment,
                        necessaryStartIndex, min(necessaryEndIndex,tempSegment))
           + sumOfSegment(numberOfVertex * 2 + 1, tempSegment + 1, currentEndIndex,
                          max(necessaryStartIndex, tempSegment + 1), necessaryEndIndex);
}


long long minimumOfSegment(long long numberOfVertex, long long currentStartIndex,
                           long long currentEndIndex, long long necessaryStartIndex, long long necessaryEndIndex) {

    if (necessaryStartIndex == currentStartIndex
        && necessaryEndIndex == currentEndIndex)
        return treeOfSegment[numberOfVertex];

    if (currentEndIndex < necessaryStartIndex || necessaryEndIndex < currentStartIndex) {
        return INT_MAX;
    }

    long long tempSegment = (currentStartIndex + currentEndIndex) / 2;

    return min(
            minimumOfSegment(numberOfVertex * 2, currentStartIndex, tempSegment,
                             necessaryStartIndex, min(necessaryEndIndex,tempSegment)),
            minimumOfSegment(numberOfVertex * 2 + 1, tempSegment + 1, currentEndIndex,
                             max(necessaryStartIndex,tempSegment + 1), necessaryEndIndex));
}


long long maximumOfSegment(long long numberOfVertex, long long currentStartIndex,
                           long long currentEndIndex, long long necessaryStartIndex, long long necessaryEndIndex) {

    if (necessaryStartIndex == currentStartIndex
        && necessaryEndIndex == currentEndIndex)
        return treeOfSegment[numberOfVertex];

    if (currentEndIndex < necessaryStartIndex || necessaryEndIndex < currentStartIndex) {
        return INT_MIN;
    }

    long long tempSegment = (currentStartIndex + currentEndIndex) / 2;

    return max(
            maximumOfSegment(numberOfVertex * 2, currentStartIndex, tempSegment,
                             necessaryStartIndex, min(necessaryEndIndex,tempSegment)),
            maximumOfSegment(numberOfVertex * 2 + 1, tempSegment + 1, currentEndIndex,
                             max(necessaryStartIndex,tempSegment + 1), necessaryEndIndex));
}


