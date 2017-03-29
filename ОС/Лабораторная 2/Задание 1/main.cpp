#include <windows.h>
#include <stdio.h>
#include <iostream>
#include <fstream>
#include <string>
#include <ctime>

using namespace std;

struct Point {
    double x;
    double y;

    Point() {}

    Point(double newX, double newY) {
        x = newX;
        y = newY;
    }
};

struct Pentagon {
    Point **vertices;
    Point checkPoint;

    Pentagon() {
        vertices = new Point*[5];
        for (int i = 0; i < 5; ++i) {
            vertices[i] = new Point(0, 0);
        }
    }

    ~Pentagon() {
        for (int i = 0; i < 5; ++i) {
            delete[]vertices[i];
        }
        delete[]vertices;
    }

    friend ostream &operator<<(ostream &os, const Pentagon &pentagon) {
        os << "Pentagon:  ";
        for (int i = 0; i < 5; i++) {
            os << "(" << pentagon.vertices[i][0].x << ", " << pentagon.vertices[i][0].y << ")  ";
        }

        os << endl << "Point: (" << pentagon.checkPoint.x << ", " << pentagon.checkPoint.y << ")  ";
        return os;
    }

    friend istream &operator >>(istream &os, Pentagon &pentagon) {

        for (int i = 0; i < 5; i++) {
            os >> pentagon.vertices[i][0].x >> pentagon.vertices[i][0].y;;
        }
        os >> pentagon.checkPoint.x >> pentagon.checkPoint.y;

        return os;
    }

    int checkPointBelong() {
        bool b = false;
        int n = 5;
        Sleep(10);
        for (int i = 0; i < n - 1; i++) { //луч направляем вдоль оси х
            if (checkPoint.x < vertices[i]->x
                && checkPoint.y < vertices[i]->y
                && checkPoint.y > vertices[i + 1]->y
                || checkPoint.x < vertices[i]->x
                   && checkPoint.y > vertices[i]->y
                   && checkPoint.y < vertices[i + 1]->y)
                b = !b;//если пересечет многоуолник нечетное число раз - тру

            if (checkPoint.x == vertices[i]->x
                && checkPoint.y == vertices[i]->y)
                return 1;//если точка в вершине

            if (vertices[i]->y != vertices[i + 1]->y) {
                if (((checkPoint.x < vertices[i]->x
                      && checkPoint.x > vertices[i + 1]->x)
                     || (checkPoint.x > vertices[i]->x
                         && checkPoint.x < vertices[i + 1]->x))
                    && (checkPoint.x * (vertices[i]->x - vertices[i + 1]->x)
                        / (vertices[i]->y - vertices[i + 1]->y) == checkPoint.y))
                    return 1;
            }//если точка лежит на ребре

            if ((vertices[i]->y == vertices[i + 1]->y)
                && (checkPoint.y == vertices[i]->y)
                && ((checkPoint.x < vertices[i]->x
                     && checkPoint.x > vertices[i + 1]->x)
                    || (checkPoint.x > vertices[i]->x
                        && checkPoint.x < vertices[i + 1]->x)))
                return 1;//если точка лежит на ребре

            if ((vertices[i]->y == vertices[i + 1]->y)
                && (checkPoint.y == vertices[i]->y))
                b = !b;//если ребро принадлежит лучу
        }

        if (checkPoint.x < vertices[0]->x
            && checkPoint.y < vertices[0]->y
            && checkPoint.y > vertices[n - 1]->y
            || checkPoint.x < vertices[0]->x
               && checkPoint.y > vertices[0]->y
               && checkPoint.y < vertices[n - 1]->y)
            b = !b;//пересечение с последним ребром

        if ((vertices[0]->y == vertices[n - 1]->y)
            && (vertices[0]->y == checkPoint.y))
            b = !b;//если ребро принадлежит лучу

        if (checkPoint.x == vertices[n - 1]->x
            && checkPoint.y == vertices[n - 1]->y)
            return 1;//если точка в вершине

        if (b == true) {
            return 1;
        }
        else {
            return 0;
        }
    }
};

float startTime = 0;
string inputFile = "input.txt";
string outputFile = "output.txt";
unsigned long uThrID;

HANDLE hThr;

void Thread(void* pParams);
void checkPoint(string inFile, string outFile);

int main() {
    hThr = CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)Thread, NULL, 0, &uThrID);
    WaitForSingleObject(hThr, INFINITE);
    cout << "\nThread spent " << startTime << " milliseconds solving task\n";
    return 0;
}

void Thread(void* pParams) {
    SetThreadPriority(GetCurrentThread(), THREAD_PRIORITY_BELOW_NORMAL);
    checkPoint(inputFile, outputFile);
}

void checkPoint(string inFile, string outFile) {
    startTime = 0;

    LARGE_INTEGER StartingTime, EndingTime, ElapsedMicroseconds;
    LARGE_INTEGER Frequency;

    QueryPerformanceFrequency(&Frequency);
    QueryPerformanceCounter(&StartingTime);

    int N = 5;

    bool pointBelong = false;

    Point* pointArray = new Point[N];
    Point vertex;

    try {
    std::ifstream fin(inFile);
    std::ofstream fout(outFile);

    if (!fin) {
        throw "Input file not found\n";
    }
    if (!fout) {
        throw "Output file not found\n";
    }

    Pentagon* pentagon = new Pentagon();
    fin >> *pentagon;

    if (pentagon->checkPointBelong() == 1) {
        fout << 1;
    }
    else {
        fout << 0;
    }


    QueryPerformanceCounter(&EndingTime);
    ElapsedMicroseconds.QuadPart = EndingTime.QuadPart - StartingTime.QuadPart;
    ElapsedMicroseconds.QuadPart *= 1000;
    ElapsedMicroseconds.QuadPart /= Frequency.QuadPart;

    startTime = ElapsedMicroseconds.QuadPart - startTime;

    fin.close();
    fout.close();
}
catch (const char* err) {
    cout << err << endl;
    return;
}
}
