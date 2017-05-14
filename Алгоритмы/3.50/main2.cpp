#include <iostream>
#include <fstream>
#include <algorithm>
#include <vector>

using namespace std;

void updateValue(int, int, int, int, int);

void treeBuild(int, int, int);

int minimumOfSegment(int, int, int, int, int);

vector<int> treeOfSegmentMin;
vector<int> arrayOfTree;

int N = 0;



int main() {
    ifstream fin("input.txt");
    ofstream fout("output.txt");

    fin >> N;

    arrayOfTree.resize(N, 0);

    treeOfSegmentMin.resize((unsigned long) (4 * N), 0);

    int command = 0;
    while (fin >> command) {
        switch (command) {
            case 1: {
                int index, value;
                fin >> index;
                fin >> value;
                updateValue(1, 0, N - 1, index, value);
                arrayOfTree[index] = value;
            }
                break;
            case 2: {
                int startIndex, endIndex, value;
                fin >> startIndex;
                fin >> endIndex;
                fin >> value;
                for (int i = startIndex; i <= endIndex; ++i) {
                    arrayOfTree[i] += value;
                }
                addToSegment();
            }
                break;
            case 3: {
                int startIndex, endIndex;
                fin >> startIndex;
                fin >> endIndex;
                fout << sumOfSegment(1, 0, N - 1, startIndex, endIndex) << endl;
            }
                break;
            case 4: {
                int startIndex, endIndex;
                fin >> startIndex;
                fin >> endIndex;
                fout << minimumOfSegment(1, 0, N - 1, startIndex, endIndex) << endl;
            }
                break;
            case 5: {
                int startIndex, endIndex;
                fin >> startIndex;
                fin >> endIndex;
                fout << maximumOfSegment(1, 0, N - 1, startIndex, endIndex) << endl;
            }
                break;
            case 0: {
                return 0;
            }
            default:
                break;
        }
    }
    return 0;
}

void treeBuild(int numberOfVertex, int currentStartIndex, int currentEndIndex) {
    if (currentStartIndex == currentEndIndex) {
        treeOfSegmentMin[numberOfVertex] = arrayOfTree[currentStartIndex];
    } else {
        int tempSegment = (currentStartIndex + currentEndIndex) / 2;
        treeBuild(numberOfVertex * 2, currentStartIndex, tempSegment);
        treeBuild(numberOfVertex * 2 + 1, tempSegment + 1, currentEndIndex);

        treeOfSegmentMin[numberOfVertex] = min(treeOfSegmentMin[numberOfVertex * 2],
                                               treeOfSegmentMin[numberOfVertex * 2 + 1]);
    }
}

void updateValue(int numberOfVertex, int currentStartIndex,
                 int currentEndIndex, int positionToUpdate, int valueToUpdate) {

    if (currentStartIndex == currentEndIndex) {
        treeOfSegmentMin[numberOfVertex] = valueToUpdate;
    } else {
        int tempSegment = (currentStartIndex + currentEndIndex) / 2;

        if (positionToUpdate <= tempSegment)
            updateValue(numberOfVertex * 2, currentStartIndex, tempSegment, positionToUpdate, valueToUpdate);
        else
            updateValue(numberOfVertex * 2 + 1, tempSegment + 1, currentEndIndex, positionToUpdate, valueToUpdate);

        treeOfSegmentMin[numberOfVertex] = min(treeOfSegmentMin[numberOfVertex * 2],
                                               treeOfSegmentMin[numberOfVertex * 2 + 1]);
    }
}


int minimumOfSegment(int numberOfVertex, int currentStartIndex,
                     int currentEndIndex, int necessaryStartIndex, int necessaryEndIndex) {

    if (necessaryStartIndex == currentStartIndex
        && necessaryEndIndex == currentEndIndex)
        return treeOfSegmentMin[numberOfVertex];

    if (currentEndIndex < necessaryStartIndex || necessaryEndIndex < currentStartIndex) {
        return numeric_limits<int>::max();
    }

    int tempSegment = (currentStartIndex + currentEndIndex) / 2;

    return min(
            minimumOfSegment(numberOfVertex * 2, currentStartIndex, tempSegment,
                             necessaryStartIndex, min(necessaryEndIndex, tempSegment)),
            minimumOfSegment(numberOfVertex * 2 + 1, tempSegment + 1, currentEndIndex,
                             max(necessaryStartIndex, tempSegment + 1), necessaryEndIndex));
}


