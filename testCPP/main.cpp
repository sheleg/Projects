#include <iostream>
#include <fstream>
#include <algorithm>
#include <vector>

using namespace std;

void updateValue(int, int, int, int, int);

void treeBuild(int, int, int);

int minimumOfSegment(int, int, int, int, int);

vector<int> treeOfSegmentMin;
int N = 0;



int main() {
    ifstream fin("input.txt");
    ofstream fout("output.txt");

    fin >> N;
    treeOfSegmentMin.resize((unsigned long) (4 * N), 0);

    return 0;
}

void updateValue(int numberOfVertex, int currentStartIndex,
                 int currentEndIndex, int positionToUpdate, int valueToUpdate) {

    if (currentStartIndex == currentEndIndex) {
        treeOfSegmentMin[numberOfVertex] = min(treeOfSegmentMin[positionToUpdate], valueToUpdate);
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


