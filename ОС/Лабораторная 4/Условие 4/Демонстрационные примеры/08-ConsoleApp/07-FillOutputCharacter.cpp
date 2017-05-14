
#include <windows.h>
#include <iostream.h>

void main()
{
	char c;
	HANDLE	hStdout;	// ���������� ������������ ������
	DWORD	dwLength;	// ���������� ����������� ������
	DWORD	dwWritten;	// ��� ����������� ���������� ������
	COORD	coord;		// ���������� ������ ������
	CONSOLE_SCREEN_BUFFER_INFO csbi;	// ��� ���������� ������ ������

		// ������ ����������� ���������� ������
	hStdout=GetStdHandle(STD_OUTPUT_HANDLE);
		// ������ ��������� ��������� ������
	if (!GetConsoleScreenBufferInfo(hStdout,&csbi))
		cout<<"Console screen buffer info failed."<<endl;

		// ��������� ������ ������ ������ � ��������
	dwLength=csbi.dwSize.X*csbi.dwSize.Y;
	coord.X=0;	// �������� ��������� ����� � ������ ������
	coord.Y=0;      	

	cout<<"Input any char to fill screen buffer: ";
	cin>>c;
		// ��������� ����� ������ ��������, ������� �����
	if (!FillConsoleOutputCharacter(
			hStdout,	// ����������� ���������� ������
			c,			// ������ ���������� 
			dwLength,	// ����� ������ � ��������
			coord,		// ������ ������ ������
			&dwWritten))// ���������� ����������� ������
		cout<<"Fill screen buffer failed."<<endl;

	cout<<"In order to clear screen buffer, press any char: ";
	cin>>c;
		// ������� ����� ������ ���������
	if (!FillConsoleOutputCharacter(
			hStdout,	// ����������� ���������� ������
			' ',			// ������ ���������� 
			dwLength,	// ����� ������ � ��������
			coord,		// ������ ������ ������
			&dwWritten))// ���������� ����������� ������
		cout<<"Fill screen buffer failed."<<endl;
}
