
#include <windows.h>
#include <iostream.h>

void main()
{
	char c;
	char	ConsoleTitleBuffer[80];	// ��������� �� ����� � ����������
	DWORD	dwBufferSize=80;		// ������ ������ ��� ���������
	DWORD	dwTitleSize;			// ����� ���������

		// ������ ��������� �������
	dwTitleSize=GetConsoleTitle(ConsoleTitleBuffer,dwBufferSize);
		// ������� �� ������� ���������
	cout<<"Title length = "<<dwTitleSize<<endl;
	cout<<"The window title = "<<ConsoleTitleBuffer<<endl;

	cout<<"Input new title: ";
	cin.getline(ConsoleTitleBuffer,80);
		// ������������� ����� ��������� �������
	if (!SetConsoleTitle(ConsoleTitleBuffer))
		cout<<"Set console title failed."<<endl;

	cout<<"The title was changed."<<endl;
	
	cout<<"Press any char to exit: ";
	cin>>c;
}
