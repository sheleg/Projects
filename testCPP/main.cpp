#include <iostream>
#include <fstream>
#include <cmath>
#include <vector>

using namespace std;
vector<long long> v;

bool newBinomialHeap(long long& num) {
    if (num < 0)
        return false;
    if (num == 0)
        return false;
    long long ans = (long long)log2(num);
    //cout << ans << endl;
    if (ans < 0) {
        //cout << "1" << endl;
        return false;
    }

//    cout << (int)2.6 << endl;

    v.push_back(ans);
    long long temp = (long long)pow(2, ans);
    num -= temp;
    return true;
}

int main() {
    ifstream fin("input.txt");
    long long mainNum;
    fin >> mainNum;
    fin.close();


    while (newBinomialHeap(mainNum));

    ofstream fout("output.txt");
    int size = v.size();
    if (size == 0) {
        fout << "-1";
        return 0;
    }
    for (int i = 0; i < size; i++) {
        fout << v.back() << endl;
        v.pop_back();
    }

    fout.close();

    return 0;
}