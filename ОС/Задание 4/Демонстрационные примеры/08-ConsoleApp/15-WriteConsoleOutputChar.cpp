
#include <windows.h>
#include <iostream.h>

void main()
{
	HANDLE	hStdOut;// ���������� ������������ ������
	CHAR	c;		// ��� ������� ������� ����� ��������
	COORD	coord;	// ���������� ���� ����� ��������
	DWORD	dwWritten;	// ������� ������ �� ���������

		// �������� ����������� ����������� ������
	hStdOut=GetStdHandle(STD_OUTPUT_HANDLE);

		// ������ ������, ������� ����� ��������
	cout<<"Input a char to write: ";
	cin>>c;
		// ������������� ���������� ��� ������ �������
	cout<<"Input X coordinate to write: ";
	cin>>coord.X;
	cout<<"Input Y coordinate to write: ";
	cin>>coord.Y;

		// ����� ������ � ����� ������
	if (!WriteConsoleOutputCharacter(
			hStdOut,	// ���������� ������ ������
			&c,		// �������, ������� �������
			1,		// ����� ���� ������
			coord,	// ����������, ���� �������
			&dwWritten))	// ���������� ���������� ��������
		cout<<"Write console output character failed."<<endl;
}
