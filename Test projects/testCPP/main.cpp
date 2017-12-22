#include <iostream>
#include <vector>

using namespace std;

void f();
void print(vector<unsigned long long> n) {
    cout << n[0];
    for (int i = 1; i < n.size(); i++) {
        cout << "-" << n[i];
    }
    cout << endl;
}

bool check(vector<unsigned long long> n) {
    for (int i = 0; i < n.size(); i++)
        for (int j = i; j < n.size(); j++)
            if (n[i] == n[j] && i != j)
                return true;
    return false;
}

int main() {

    f();

    return 0;


}


void f() {
    unsigned long long n;
    cin >> n;
    vector<unsigned long long> num;
    num.push_back(n);
    int l = 0;
    while (n != 0) {
        l++;
        n /= 10;
    }

    int coef = 1;
    for (int i = 1; i < l; i++) {
        unsigned long long r = num[0] % (10 * coef);
        num[0] /= 10 * coef;
        num.push_back(r);
        if (check(num)) {
            num.pop_back();
            num[0] *= (10 * coef);
            num[0] += r;
            coef *= 10;
        } else
            coef = 1;
    }

    long long first = 1, last = num.size();
    while ((first != last) && (first != --last)) {
        unsigned long long t = num[first];
        num[first] = num[last];
        num[last] = t;
        ++first;
    }
    print(num);

}