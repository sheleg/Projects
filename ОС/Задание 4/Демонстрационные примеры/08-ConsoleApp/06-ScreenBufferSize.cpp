
#include <windows.h>
#include <iostream.h>

void main()
{
	COORD coord;	// ��� ������� ������ ������
	HANDLE	hStdOut;	// ���������� ������������ ������

		// ������ ���������� ������������ ������
	hStdOut=GetStdHandle(STD_OUTPUT_HANDLE);

		// ������ ����� ������ ������ ������
	cout<<"Enter new screen buffer size."<<endl;
	cout<<"A number of columns: ";
	cin>>coord.X;
	cout<<"A number of rows: ";
	cin>>coord.Y;
		// ������������� ����� ������ ������ ������
	SetConsoleScreenBufferSize(hStdOut,coord);
}
