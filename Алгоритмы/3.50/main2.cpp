//#include<iostream>
//#include <stdio.h>
//#include <math.h>
//#include<conio.h>
//using namespace std;
//int getMid(int s, int e)
//{
//    return s + (e - s) / 2;
//}
//
//int getSumUtil(int *st, int ss, int se, int qs, int qe, int index)
//{
//    if (qs <= ss && qe >= se)
//        return st[index];
//    if (se < qs || ss > qe)
//        return 0;
//    int mid = getMid(ss, se);
//    return getSumUtil(st, ss, mid, qs, qe, 2 * index + 1) + getSumUtil(st, mid + 1, se, qs, qe, 2 * index + 2);
//}
//void updateValueUtil(int *st, int ss, int se, int i, int diff, int index)
//{
//    if (i < ss || i > se)
//        return;
//    st[index] = st[index] + diff;
//    if (se != ss)
//    {
//        int mid = getMid(ss, se);
//        updateValueUtil(st, ss, mid, i, diff, 2 * index + 1);
//        updateValueUtil(st, mid+1, se, i, diff, 2 * index + 2);
//    }
//}
//void updateValue(int arr[], int *st, int n, int i, int new_val)
//{
//    if (i < 0 || i > n-1)
//    {
//        cout<<"Invalid Input";
//        return;
//    }
//    int diff = new_val - arr[i];
//    arr[i] = new_val;
//    updateValueUtil(st, 0, n - 1, i, diff, 0);
//}
//int getSum(int *st, int n, int qs, int qe)
//{
//    if (qs < 0 || qe > n-1 || qs > qe)
//    {
//        cout<<"Invalid Input"<<endl;
//        return -1;
//    }
//    return getSumUtil(st, 0, n - 1, qs, qe, 0);
//}
//int constructSTUtil(int arr[], int ss, int se, int *st, int si)
//{
//    if (ss == se)
//    {
//        st[si] = arr[ss];
//        return arr[ss];
//    }
//    int mid = getMid(ss, se);
//    st[si] =  constructSTUtil(arr, ss, mid, st, si*2+1) + constructSTUtil(arr, mid + 1, se, st, si*2+2);
//    return st[si];
//}
//
//int *constructST(int arr[], int n)
//{
//    int x = (int)(ceil(log2(n)));
//    int max_size = 2 * (int)pow(2, x) - 1;
//    int *st = new int[max_size];
//    constructSTUtil(arr, 0, n - 1, st, 0);
//    return st;
//}
//
//int main()
//{
//    int arr[] = {1, 3, 5, 7, 9, 11};
//    int n = sizeof(arr) / sizeof(arr[0]);
//    int *st = constructST(arr, n);
//    while (fin >> command) {
//        switch (command) {
//            case 1: {
//                long long index, value;
//                fin >> index;
//                fin >> value;
//                updateValue(treeOfSegmentMin, 'n', 1, 0, N - 1, index, value);
//                updateValue(treeOfSegmentMax, 'x', 1, 0, N - 1, index, value);
//                updateValue(treeOfSegmentSum, 's', 1, 0, N - 1, index, value);
//                arrayOfTree[index] = value;
////                for (int i = 0; i < arrayOfTree.size(); ++i) {
////                    fout << arrayOfTree[i] << "  ";
////                }
////                fout << "\n\n";
//            }
//                break;
//            case 2: {
//                long long startIndex, endIndex, value;
//                fin >> startIndex;
//                fin >> endIndex;
//                fin >> value;
////                addToSegment(1, 0, N - 1, startIndex, endIndex, value);
//                for (long long i = startIndex; i <= endIndex; ++i) {
//                    arrayOfTree[i] = value;
//                }
//                treeBuild(treeOfSegmentMin, 'n', 1, 0, N - 1);
//                treeBuild(treeOfSegmentMax, 'x', 1, 0, N - 1);
//                treeBuild(treeOfSegmentSum, 's', 1, 0, N - 1);
////                for (int i = 0; i < treeOfSegmentSum.size(); ++i) {
////                    fout << treeOfSegmentSum[i] << "  ";
////                }
////                fout << endl << endl;
//            }
//                break;
//            case 3: {
//                long long startIndex, endIndex;
//                fin >> startIndex;
//                fin >> endIndex;
//                fout << sumOfSegment(treeOfSegmentSum, 1, 0, N - 1, startIndex, endIndex) << endl;
////                for (int i = 0; i < arrayOfTree.size(); ++i) {
////                    fout << arrayOfTree[i] << "  ";
////                }
////                fout << endl << endl;
//            }
//                break;
//            case 4: {
//                long long startIndex, endIndex;
//                fin >> startIndex;
//                fin >> endIndex;
//                fout << minimumOfSegment(treeOfSegmentMin, 1, 0, N - 1, startIndex, endIndex) << endl;
////                for (int i = 0; i < arrayOfTree.size(); ++i) {
////                    fout << arrayOfTree[i] << "  ";
////                }
////                fout << endl << endl;
//
//            }
//                break;
//            case 5: {
//                long long startIndex, endIndex;
//                fin >> startIndex;
//                fin >> endIndex;
//                fout << maximumOfSegment(treeOfSegmentMax, 1, 0, N - 1, startIndex, endIndex) << endl;
////                for (int i = 0; i < arrayOfTree.size(); ++i) {
////                    fout << arrayOfTree[i] << "  ";
////                }
////                fout << endl << endl;
//            }
//                break;
//            case 0: {
//                return 0;
//            }
//            default:
//                break;
//        }
//    }
//    cout<<"Sum of values in given range:"<<getSum(st, n, 1, 3)<<endl;
//    updateValue(arr, st, n, 1, 10);
//    cout<<"Updated sum of values in given range:"<<getSum(st, n, 1, 3);
//    getch();
//}