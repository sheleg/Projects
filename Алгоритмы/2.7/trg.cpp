#include <iostream>
#include <fstream>
#include <algorithm>
#include <vector>

using namespace std;

int main()
{
    ofstream fon("output.txt");
    ifstream fin("input.txt");
    int size = 0;
    fin >> size;
    if (size == 1)
    {
        fon << 1;
        return 0;
    }
    int* arraySeq = new int[size];
    for (int i = 0; i < size; i++)
    {
        fin >> arraySeq[i];
    }
    vector<int> v(size+1);
    v[0] = -1000000000;
    for (int i = 1; i <=size; ++i)
    {
        v[i] = 1000000000;
    }
    vector<int> r(size+1);
    for (int i = 0; i<size; i++)
    {

        int j = int(lower_bound(v.begin(), v.end(), arraySeq[i]) - v.begin());
        r[i] = j;
        if ((arraySeq[i] < v[j])&&(v[j - 1] < arraySeq[i]))
        {

            v[j] = arraySeq[i];

        }
    }
    vector<int> z(size+1);
    z[size] = 1000000000;
    for (int i = 0; i < size; i++)
    {
        z[i] = -1000000000;
    }
    reverse(arraySeq + 0, arraySeq + size);
    vector<int> y(size + 1);
    int m = 0;
    z[size - 1] = arraySeq[0];
    y[0] = 0;
    for (int i = 1; i < size; i++)
    {
        int j = int(upper_bound(z.begin(), z.end(), arraySeq[i-1]) - z.begin()-1);

        if ((arraySeq[i-1] > z[j]) && (z[j + 1] > arraySeq[i-1]))
        {
            z[j] = arraySeq[i-1];
            y[i] = size - j;
            if (y[i] > m)
                m = y[i];
            else
                y[i] = m;
        }
        else
        {
            //j--;
            z[j] = arraySeq[i-1];
            y[i] = size - j;
                if (y[i] > m)
                m = y[i];
            else
                y[i] = m;
        }
    }

    reverse(y.begin(), y.end());
    int max = 1;
    for (int i = 0; i < size; i++)
    {
        if ((y[i+1] + r[i])>max)
        {
            max = y[i+1] + r[i];
        }
    }
    fon << max;
    fin.close();
    fon.close();
    return 0;
}