
#include <windows.h>
#include <iostream.h>

void main()
{
	char c;
	HANDLE	hStdOut;	// ���������� ������������ ������
	COORD	coord;		// ��� ������� �������

		// ������ ���������� ������������ ������
	hStdOut=GetStdHandle(STD_OUTPUT_HANDLE);

	do
	{
		cout<<"Input new cursor position."<<endl;
		cout<<"X = ";
		cin>>coord.X;
		cout<<"Y = ";
		cin>>coord.Y;

			// ���������� ����� ������� �������
		if (!SetConsoleCursorPosition(hStdOut,coord))
		{
			cout<<"Set cursor position failed."<<endl;
			break;
		}

		cout<<"This is a new position."<<endl;

		cout<<"Input 'y' to continue: ";
		cin>>c;
	} while (c=='y');
		
}
