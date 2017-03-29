#include <iostream>
#include <fstream>
#include <vector>

using namespace std;


int MaxN = 1000000;

vector<int> A(MaxN);//elements from file
vector<int> Q(MaxN + 1);
vector<int> B(MaxN + 1);
vector<int> C(MaxN + 1);

long long Out, N, Max;

//read data

int AddFirst(int K) {
    int L = 0, R = N;

    while (R - L > 1) {
        if (Q[(L + R) >> 1] > K)
            R = (L + R) >> 1;
        else L = (L + R) >> 1;
        if (K <= Q[L]) {
            Q[L] = K;
            AddFirst(L);
        } else if (K <= Q[R]) {
            Q[R] = K;
            AddFirst(R);
        } else AddFirst(-1);
    }
}

int AddSecond(int K) {
    int L = 0, R = N;

    while (R - L > 1) {
        if (Q[(L + R) >> 1] <= K)
            R = (L + R) >> 1;
        else
            L = (L + R) >> 1;
        if (K >= Q[R]) {
            if (Q[R] == $80808080)
                AddSecond(0);
            else AddSecond(Max);
            if (R > Max)
                Max = R;
            Q[R] = K;
        } else if (K >= Q[L]) {
            if (Q[L] == $80808080)
                AddSecond(0);
            else AddSecond(Max);
            if (L > Max)
                Max = L;
            Q[L] = K;
        } else AddSecond(-1);
    }
}

int main() {
    ifstream fin("input.txt");
    fin >> N;
    for (int i = 0; i < N; ++i) {
        fin >> A[i];
    }
    Q[0] = -$7F7F7F7F;
    for (int i = 0; i < N; i++) {
        B[i] = AddFirst(A[i]);
//            FillChar(Q, Sizeof(Q), $80);
        Q[0] = $7F7F7F7F;
    }
    for (int i = N; i >= 0; i--) {
        C[i] = AddSecond(A[i]);
    }
    for (int i = 0; i < N; i++) {
        if ((B[i] + C[i] > Out) && (C[i] != 0))
            Out = B[i] + C[i];
    }
    cout << Out;
    return 0;
}








