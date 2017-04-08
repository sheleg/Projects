// ������ ������� WriteConsoleOutputAttribute

#include <windows.h>
#include <iostream.h>

void main()
{
	HANDLE	hStdOut;// ���������� ������������ ������
	WORD wBuf[80];	// ������ ���������
	CHAR cBuf[80];	// ������ ��������
	CHAR c;			// ��� �������, ������� ����� ��������
	int		n;		// ���������� ��������, ������� ����� ��������
	DWORD	dwWritten;	// ������� ������ �� ����� ����
	CONSOLE_SCREEN_BUFFER_INFO csbi;// ��� ���������� ������ ������


		// �������� ����������� ����������� ������
	hStdOut=GetStdHandle(STD_OUTPUT_HANDLE);

		// ������ ������, ������� ����� ��������
	cout<<"Input a char to write: ";
	cin>>c;
	cout<<"Input a number of chars (1-80): ";
	cin>>n;

		// ��������� ������ �������� � ���������
	for (int i=0; i<n; i++)
	{
		cBuf[i]=c;
		wBuf[i]=BACKGROUND_BLUE|BACKGROUND_INTENSITY|
		FOREGROUND_RED|FOREGROUND_GREEN|FOREGROUND_INTENSITY;
	}

		// �������� ������� ������� �������
	if (!GetConsoleScreenBufferInfo(hStdOut,&csbi))
		cout<<"Console screen buffer info failed."<<endl;

		// ����� ������ � ����� ������
	if (!WriteConsoleOutputCharacter(
			hStdOut,	// ���������� ������ ������
			cBuf,		// �������, ������� �������
			n,			// ���������� ��������
			csbi.dwCursorPosition,	// ����������, ���� �������
			&dwWritten))	// ���������� ���������� ��������
		cout<<"Write console output character failed."<<endl;

		// ����� �������� � ����� ������
	if (!WriteConsoleOutputAttribute(
			hStdOut,
			wBuf,
			n,
			csbi.dwCursorPosition,
			&dwWritten))
		cout<<"Write console output attribute failed."<<endl;

		// ��������� ������, ����� ���� ����� ��� ������
	cout<<endl;
}
