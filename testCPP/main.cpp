// #include <windows.h>
#include <iostream>
#include <fstream>
#include <vector>

#define BEAR_DAY 0
#define BULL_DAY 1

using namespace std;

vector<double> prices;
vector<int> daysDescriprions;
int n = 0, m = 0, k = 0;

void easyTrading();
int getDescriptionOfDay(int i);
double average(int, int);

int main() {
    //TODO: Что с названиями тестов
    
    easyTrading();
    return 0;
}

void easyTrading() {
    //TODO: Проверки!
    ifstream fin("easy.in");
    ofstream fout("easy.out");

    fin >> m;
    fin >> n;
    fin >> k;

    prices.resize(k + 1, 0);
    daysDescriprions.resize(k + 1, -1);

    if (m > n || n > k || m > k) {
        cout << "Wrong data!";
        return;
    }

    prices[0] = -1;
    for (int i = 1; i < k + 1; ++i) {
        fin >> prices[i];
    }


    for (int i = 0; i < k; ++i) {
        daysDescriprions[i] = getDescriptionOfDay(i);
    }

    for (int i = 1; i < k; ++i) {
        if (daysDescriprions[i] == BULL_DAY) {
            fout << "BUY ON DAY " << i << endl;
            while (daysDescriprions[++i] != BEAR_DAY) {}
            fout << "SELL ON DAY " << i << endl;
            prices.clear();
            return;
        }
    }
    return;
}

int getDescriptionOfDay(int i) {
    if (i < n) {
        return -2;
    }

    if (average(i, n) < average(i, m) && (i == n || daysDescriprions[i - 1] == BEAR_DAY)) {
        return BULL_DAY;
    }

    if (average(i, n) > average(i, m) && (i == n || daysDescriprions[i - 1] == BULL_DAY)) {
        return BEAR_DAY;
    }

}

double average(int day, int count) {
    double tempPrice = 0;
    for (int i = 0; i < count; ++i) {
        tempPrice += prices[day - i];
    }
    return tempPrice / count;
}