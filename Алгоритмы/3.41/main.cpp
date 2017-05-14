#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>

using namespace std;

struct Command {
    int number;

    int x;
    int y;
    int z;

    bool operator < (const Command& command) const
    {
        return (x < command.x);
    }

    bool operator > (const Command& command) const
    {
        return (x > command.x);
    }

    friend ostream &operator<<(ostream &os, const Command& command) {
        os << "Command number " << command.number << ": "
           << command.x << " " << command.y << " " << command.z;
        return os;
    }
};

vector<int> treeOfSegmentMin;

void updateValue(int numberOfVertex, int currentStartIndex,
                 int currentEndIndex, int positionToUpdate, int valueToUpdate);
int minimumOfSegment(int numberOfVertex, int currentStartIndex,
                     int currentEndIndex, int necessaryStartIndex, int necessaryEndIndex);


int main() {
    ifstream fin("input.txt");
    ofstream fout("output.txt");

    long long N = 0;
    fin >> N;
    vector<Command> commands;

    treeOfSegmentMin.resize((unsigned long) (4 * N * 3), numeric_limits<int>::max());

    for (int i = 0; i < N; ++i) {
        Command command;
        command.number = i + 1;
        fin >> command.x;
        fin >> command.y;
        fin >> command.z;
        commands.push_back(command);
    }

    sort(commands.begin(), commands.end());
    long long count = N;

//    updateValue(1, 0, N * 3 - 1, 14, 3);
//    cout << minimumOfSegment(1, 0, N * 3 - 1, 0, 14) << " " << 3 << endl;
//    updateValue(1, 0, N * 3 - 1, 5, 1);
//    cout << minimumOfSegment(1, 0,  N * 3 - 1, 0, 5) << " " << 1 << endl;
//    updateValue(1, 0,  N * 3 - 1, 11, 2);
//    cout << minimumOfSegment(1, 0,  N * 3 - 1, 0, 11) << " " << 2 << endl;
//    updateValue(1, 0,  N * 3 - 1, 13, 9);
//    cout << minimumOfSegment(1, 0, N * 3 - 1, 0, 13) << " " << 9 << endl;
//    updateValue(1, 0,  N * 3 - 1, 12, 10);
//    cout << minimumOfSegment(1, 0,  N * 3 - 1, 0, 12) << " " << 10 << endl;
//
//    for (auto i = treeOfSegmentMin.begin(); i != treeOfSegmentMin.end(); i++) {
//        cout << *i << "  ";
//    }

    for (int i = 0; i < N; ++i) {
        updateValue(1, 0, N * 3 - 1, commands[i].y - 1, commands[i].z);
        if (minimumOfSegment(1, 0, N * 3 - 1, 0, commands[i].y - 1) < commands[i].z) {
            count--;
        }
    }

    fout << count;

    fin.close();
    fout.close();
    return 0;
}

void updateValue(int numberOfVertex, int currentStartIndex,
                 int currentEndIndex, int positionToUpdate, int valueToUpdate) {

    if (currentStartIndex == currentEndIndex) {
//        treeOfSegmentMin[numberOfVertex] = min(treeOfSegmentMin[positionToUpdate], valueToUpdate);
        treeOfSegmentMin[numberOfVertex] = min(treeOfSegmentMin[numberOfVertex], valueToUpdate);
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