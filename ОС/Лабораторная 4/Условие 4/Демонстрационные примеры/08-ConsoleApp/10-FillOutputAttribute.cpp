
#include <windows.h>
#include <iostream.h>

void main()
{
	char c;
	HANDLE	hStdout;	// ���������� ������������ ������
	WORD	wAttribute;	// ���� ���� � ������
	DWORD	dwLength;	// ���������� ����������� ������
	DWORD	dwWritten;	// ��� ����������� ���������� ������
	COORD	coord;		// ���������� ������ ������
	CONSOLE_SCREEN_BUFFER_INFO csbi;	// ��� ���������� ������ ������

	cout<<"In order to fill console attributes, press any char:";
	cin>>c;
		// ������ ����������� ���������� ������
	hStdout=GetStdHandle(STD_OUTPUT_HANDLE);
		// ������ ��������� ��������� ������
	if (!GetConsoleScreenBufferInfo(hStdout,&csbi))
		cout<<"Console screen buffer info failed."<<endl;

		// ��������� ������ ������ ������ � ��������
	dwLength=csbi.dwSize.X*csbi.dwSize.Y;
	coord.X=0;	// �������� ��������� ����� � ������ ������
	coord.Y=0;      	

		// ������������� ���� ���� �������, � ���� �������� ������  
    wAttribute=BACKGROUND_BLUE|BACKGROUND_INTENSITY|
		FOREGROUND_RED|FOREGROUND_GREEN|FOREGROUND_INTENSITY;
		// ��������� ����� ����������
	if (!FillConsoleOutputAttribute(
			hStdout,	// ����������� ���������� ������
			wAttribute,	// ���� ���� � ������ 
			dwLength,	// ����� ������ � ��������
			coord,		// ������ ������ ������
			&dwWritten))// ���������� ����������� ������
		cout<<"Fill console attribute failed."<<endl;

	cout<<"The fill attributes was changed."<<endl;
}
