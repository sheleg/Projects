#include <iostream>
#include <fstream>
#include <algorithm>

using namespace std;

int main()
{
    string str;
    ifstream fin("input.txt");
    ofstream fout("output.txt");

    if (!fin) {
        return 0;
    }

    fin >> str;

    str.erase(unique(str.begin(), str.end()), str.end());

    unsigned long N = str.size();

    long long **matrix = new long long*[N];
    for(int i = 0; i < N; i++)
        matrix[i] = new long long[N] {};

    for(int i = 0; i < N; i++ )
        matrix[i][i] = 1;

    for(int i = 1; i < N; i++ ) {
        for(int j = i; j < N; j++) {
            long long minimum = min(matrix[j-i][j-1], matrix[j-i+1][j]);

            if(str[j - i] == str[j])
                matrix[j - i][j] = minimum;
            else
                matrix[j - i][j] = minimum + 1;

            for(int y = j - i + 1; y <= j; y++)
                if(matrix[y][j] + matrix[j - i][y - 1] < matrix[j - i][j])
                    matrix[j - i][j] = matrix[y][j] + matrix[j - i][y - 1];
        }
    }

    fout<<matrix[0][N - 1];

    return 0;
}