
#include <windows.h>
#include <iostream.h>

void main()
{
	char c;
	HANDLE	hStdout;	// ���������� ������������ ������
	WORD	wAttribute;	// ���� ���� � ������

	cout<<"In order to set text attributes, press any char:";
	cin>>c;

		// ������ ����������� ���������� ������
	hStdout=GetStdHandle(STD_OUTPUT_HANDLE);
	
		// ������������� ���� ���� �������, � ���� �������� �������  
    wAttribute=BACKGROUND_GREEN|BACKGROUND_INTENSITY|
		FOREGROUND_RED|FOREGROUND_INTENSITY;
		// ������������� ������ ���� ������ � ����
	if (!SetConsoleTextAttribute(hStdout,wAttribute))
		cout<<"Set console text attribute failed."<<endl;

	cout<<"The text attributes was changed."<<endl;
	
}
