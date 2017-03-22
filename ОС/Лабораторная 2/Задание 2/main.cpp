#include <windows.h>
#include <stdio.h>
#include <iostream>
#include <fstream>
#include <string>
#include <ctime>
#include <vector>

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
            vertices[i] = new Point();
        }

        vertices[0]->x = getRandomNumber(0, 25);
        vertices[0]->y = getRandomNumber(25, 65);

        vertices[1]->x = getRandomNumber(35, 65);
        vertices[1]->y = getRandomNumber(65, 100);

        vertices[2]->x = getRandomNumber(75, 100);
        vertices[2]->y = getRandomNumber(25, 65);

        vertices[3]->x = getRandomNumber(50, 75);
        vertices[3]->y = getRandomNumber(0, 25);

        vertices[4]->x = getRandomNumber(25, 50);
        vertices[4]->y = getRandomNumber(0, vertices[3]->y);

        checkPoint.x = getRandomNumber(0, 100);
        checkPoint.y = getRandomNumber(0, 100);
    }

    ~Pentagon() {
        for (int i = 0; i < 5; ++i) {
            delete[]vertices[i];
        }
        delete[]vertices;
    }

    void print() {
        for (int i = 0; i < 5; i++) {
            std::cout << vertices[i][0].x << " " << vertices[i][0].y << std::endl;
        }
    }

    friend std::ostream &operator<<(std::ostream &os, const Pentagon &pentagon) {
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
        // for (size_t i = 0; i < 1000; i++) {
        //     /* code */
        // }
        //

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

    int getRandomNumber(int min, int max) {
        return rand() % (max - min + 1) + min;
    }
};

int ThreadCount = 0;
int TaskCount = 0;
int currentTask = 0;

int correctDecision = 0;
int errorInDecision = 0;
int noSolution = 0;

double MIN_TIME = INT_MAX;
double MAX_TIME = INT_MIN;

int *threadSolution;
float *threadTime;

unsigned long uThrID;

string inputFile = "input.txt";
string outputFile = "output.txt";

vector<Pentagon *> parametrs;
vector<int> answers;

CRITICAL_SECTION cs;
HANDLE hThr;

void Thread(void *pParams);
float checkPoint(Pentagon *pentagon, int number);
vector<Pentagon *> getRandomParametr();
Pentagon *createPentagon();

int main() {
    srand(time(NULL));

    std::ofstream fout(outputFile, std::ios::trunc);
    cout << "Input count of threads: ";
    cin >> ThreadCount;
    cout << "Input count of tasks:";
    cin >> TaskCount;

    parametrs = getRandomParametr();
    answers.resize(TaskCount, 0);
    threadSolution = new int[ThreadCount];
    threadTime = new float[ThreadCount];
    for (int i = 0; i < ThreadCount; i++) {
        threadSolution[i] = 0;
        threadTime[i] = 0;
    }

    InitializeCriticalSection(&cs);
    HANDLE *arrayThread = new HANDLE[ThreadCount];
    unsigned long *threadID = new unsigned long[ThreadCount]{};

    for (int i = 0; i < ThreadCount; i++) {
        arrayThread[i] = CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE) Thread, (void *) &i, 0, &threadID[i]);
        if (NULL == arrayThread[i]) {
            std::cout << "Error in create thread № " << i << "\n";
        }
    }
    WaitForMultipleObjects(ThreadCount, arrayThread, TRUE, INFINITE);

    for (int i = 0; i < ThreadCount; i++) {
        CloseHandle(arrayThread[i]);
    }

    LARGE_INTEGER StartingTime, EndingTime, ElapsedMicroseconds;
    LARGE_INTEGER Frequency;

    QueryPerformanceFrequency(&Frequency);
    QueryPerformanceCounter(&StartingTime);

    for (int i = 0; i < TaskCount; i++) { // вывод параметры - решение
        fout << *parametrs[i] << "   Answer: " << answers[i] << endl << endl;
    }
    fout << endl;

    QueryPerformanceCounter(&EndingTime);
    ElapsedMicroseconds.QuadPart = EndingTime.QuadPart - StartingTime.QuadPart;
    ElapsedMicroseconds.QuadPart *= 1000000;
    ElapsedMicroseconds.QuadPart /= Frequency.QuadPart;

    cout << "\nTime taken to write data to a file (nanoseconds): " << ElapsedMicroseconds.QuadPart << endl;

    cout << correctDecision << " tasks successfully solved\n";
    cout << errorInDecision << " tasks isn't solved due to an error in the calculation process\n";
    cout << noSolution << " tasks don't have a solution\n\n";
    for (int i = 0; i < ThreadCount; i++) {//вывод поток - кол-во задач
        cout << "Thread " << i + 1 << " solved " << threadSolution[i] << " tasks\n";
    }

    cout << "\nMinimum time to resolve: " << MIN_TIME << endl;
    cout << "Maximum time to resolve: " << MAX_TIME << endl << endl;

    for (int i = 0; i < ThreadCount; i++) {
        cout << "Thread " << i + 1 << " spent " << threadTime[i] << " milliseconds solving " << threadSolution[i]
             << " tasks" << endl;
    }

    delete[] threadID;
    delete[] arrayThread;
    delete[] threadSolution;
    delete[] threadTime;
    fout.close();

    return 0;
}


void Thread(void *pParams) {
    SetThreadPriority(GetCurrentThread(), THREAD_PRIORITY_BELOW_NORMAL);
    int number = *(int *) pParams - 1;
    Pentagon *pentagon;
    float checkTime = 0;
    int currentTaskThread = 0;

    EnterCriticalSection(&cs);
    currentTaskThread = currentTask++;
    LeaveCriticalSection(&cs);

    while (currentTaskThread < TaskCount) {
        pentagon = parametrs[currentTaskThread];
        checkTime = checkPoint(pentagon, currentTaskThread);
        threadTime[number] += checkTime;
        ++threadSolution[number];

        EnterCriticalSection(&cs);
        currentTaskThread = currentTask++;
        LeaveCriticalSection(&cs);
    }
}

float checkPoint(Pentagon *pentagon, int numberInArray) {
    float threadTime;

    LARGE_INTEGER StartingTime, EndingTime, ElapsedMicroseconds;
    LARGE_INTEGER Frequency;

    QueryPerformanceFrequency(&Frequency);
    QueryPerformanceCounter(&StartingTime);

    int pointBelongPentagon = pentagon->checkPointBelong();

    switch (pointBelongPentagon) {
        case 0: { //Задача решена, точка не принадлежит
            EnterCriticalSection(&cs);
            answers[numberInArray] = 0;
            correctDecision++;
            LeaveCriticalSection(&cs);
        }
            break;
        case 1: { //Задача решена, точка принадлежит
            EnterCriticalSection(&cs);
            answers[numberInArray] = 1;
            correctDecision++;
            LeaveCriticalSection(&cs);
        }
            break;
        case -1: { //Ошибка во время выполнения
            EnterCriticalSection(&cs);
            errorInDecision++;
            LeaveCriticalSection(&cs);
        }
            break;
        case -2: { //Нет решения
            EnterCriticalSection(&cs);
            answers[numberInArray] = 0;
            noSolution++;
            LeaveCriticalSection(&cs);
        }
            break;
    }

    QueryPerformanceCounter(&EndingTime);
    ElapsedMicroseconds.QuadPart = EndingTime.QuadPart - StartingTime.QuadPart;
    ElapsedMicroseconds.QuadPart *= 1000;
    ElapsedMicroseconds.QuadPart /= Frequency.QuadPart;

    threadTime = ElapsedMicroseconds.QuadPart;

    if (threadTime > MAX_TIME) {
        EnterCriticalSection(&cs);
        MAX_TIME = threadTime;
        LeaveCriticalSection(&cs);
    }
    if (threadTime < MIN_TIME) {
        EnterCriticalSection(&cs);
        MIN_TIME = threadTime;
        LeaveCriticalSection(&cs);
    }

    return threadTime;
}

vector<Pentagon *> getRandomParametr() {
    vector<Pentagon*> vectPentagon(TaskCount);
    for (int i = 0; i < TaskCount; i++) {
        vectPentagon[i] = createPentagon();
    }
    return vectPentagon;
}

Pentagon *createPentagon() {
    Pentagon *pentagon = new Pentagon();
    return pentagon;
}
