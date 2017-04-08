
#include <windows.h>
#include <iostream.h>

void main()
{
	char c;
	HANDLE	hStdOut;	// ���������� ������������ ������
	COORD coord;	// ���������� ����
	SMALL_RECT sr;	// ������������� ����

		// ������ ���������� ������������ ������
	hStdOut=GetStdHandle(STD_OUTPUT_HANDLE);

		// ������ ���������� ������ ����
	coord=GetLargestConsoleWindowSize(hStdOut);
		// ������� ���������� ������� ���� �� �������
	cout<<"The largest console window size: "<<endl;
	cout<<" X = "<<coord.X<<endl;
	cout<<" Y = "<<coord.Y<<endl<<endl;

	cout<<"Set new window rectangle in characters."<<endl<<endl;
		// ������������� ������������� ���� � ��������
	cout<<"Input left coordinate (0-79): ";
	cin>>sr.Left;
	cout<<"Input top coordinate (0-24): ";
	cin>>sr.Top;
	cout<<"Input right coordinate (0-79): ";
	cin>>sr.Right;
	cout<<"Input bottom coordinate (0-24): ";
	cin>>sr.Bottom;
		// ������������� ����� ����
	if (!SetConsoleWindowInfo(hStdOut,TRUE,&sr))
		cout<<"Set console window info failed."<<endl;

	cout<<endl<<"Press any char to finish: ";
	cin>>c;

}
