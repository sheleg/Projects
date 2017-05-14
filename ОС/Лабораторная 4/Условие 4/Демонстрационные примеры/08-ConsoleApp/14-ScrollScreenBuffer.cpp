// ������ ������������� ������� ReadFile � WriteFile
// ��������� ������ ENABLE_LINE_INPUT � ENABLE_ECHO_INPUT

#include <windows.h>
 
HANDLE hStdout, hStdin;

// ������� �������� �� ����� ������ � ������ ������
void GoToNewLine(void)
{
	CONSOLE_SCREEN_BUFFER_INFO csbi;
	SMALL_RECT srScroll;
	SMALL_RECT srClip;
	COORD coord;
	CHAR_INFO ci;

		// ������ ���������� � ������ ������
	if (!GetConsoleScreenBufferInfo(hStdout, &csbi)) 
		MessageBox(
			NULL, 
			"Get console screen buffer info failed",
			"Win32 API error",
			MB_OK | MB_ICONINFORMATION
		);
		
		// ��������� �� ������ �������
	csbi.dwCursorPosition.X=0; 
		// ���� ��� �� ��������� ������,
	if ((csbi.dwCursorPosition.Y+1)<csbi.dwSize.Y)
		// �� ��������� ������ �� ��������� ������ 
		csbi.dwCursorPosition.Y+=1;
		// �����, ������������ ����� ������
	else
	{
			// ���������� ��������������, ������� ������������
		srScroll.Left=0;
		srScroll.Top=1;
		srScroll.Right=csbi.dwSize.X;
		srScroll.Bottom=csbi.dwSize.Y;
			// ���������� �������������� ������ ������ 
		srClip.Left=0;
		srClip.Top=0;
		srClip.Right=csbi.dwSize.X;
		srClip.Bottom=csbi.dwSize.Y;
			// ������������� ����� ���������� 
			// ������ ���� �������������� srScroll
		coord.X=0;
		coord.Y=0;
			// ������������� �������� � ������ �����������
			// ��� ��������� ������
		ci.Attributes=csbi.wAttributes;
		ci.Char.AsciiChar=' ';
			// ������������ ������������� srScroll
		if (!ScrollConsoleScreenBuffer(
				hStdout,	// ���������� ������������ ������
				&srScroll,	// �������������� �������������
				&srClip,	// ����� ������
				coord,		// ������ ������ ������
				&ci))		// �������� � ������ ����������� 
			MessageBox(
				NULL, 
				"Set console window info failed",
				"Win32 API error",
				MB_OK | MB_ICONINFORMATION
			);
	}
		// ������ ������������� ������	
	if (!SetConsoleCursorPosition(hStdout, csbi.dwCursorPosition))
			MessageBox(
				NULL, 
				"Set console cursor position failed",
				"Win32 API error",
				MB_OK | MB_ICONINFORMATION
			);
} 
 
int main(void) 
{
	LPSTR lpszPrompt1="Input 'q' to exit from read cycle.\n"; 
	CHAR  c; 
	DWORD cRead, cWritten;
	DWORD dwOldMode, dwNewMode; 

		// ������ ����������� ������������ ����� � ������ 
	hStdin=GetStdHandle(STD_INPUT_HANDLE); 
	hStdout=GetStdHandle(STD_OUTPUT_HANDLE); 
	if (hStdin==INVALID_HANDLE_VALUE || hStdout==INVALID_HANDLE_VALUE) 
		MessageBox(
			NULL, 
			"Get standard handle failed",
			"Win32 API error",
			MB_OK | MB_ICONINFORMATION
		);

		// ������ ������, ������������� �� ���������
    if (!GetConsoleMode(hStdin, &dwOldMode))
		MessageBox(
			NULL, 
			"Get console mode failed",
			"Win32 API error",
			MB_OK | MB_ICONINFORMATION
		);

		// ��������� ����� ENABLE_LINE_INPUT � ENABLE_ECHO_INPUT
    dwNewMode=dwOldMode & ~(ENABLE_LINE_INPUT | ENABLE_ECHO_INPUT); 
		// ������������� ����� �����
    if (!SetConsoleMode(hStdin, dwNewMode))
		MessageBox(
			NULL, 
			"Set console mode failed",
			"Win32 API error",
			MB_OK | MB_ICONINFORMATION
		);
		
		// ������� ��������� � ���, ��� ����� �� ����� ������ 
	if (!WriteFile( 
			hStdout,		// ���������� ������������ ������ 
			lpszPrompt1,	// ������, ������� ������� 
			lstrlen(lpszPrompt1),	// ����� ������ 
			&cWritten,		// ���������� ���������� ���� 
			NULL))			// ��������� �����
		return (0);	// ������ ������

		// ���� ������
	while (TRUE)
	{ 
		if (!ReadFile(hStdin, &c, 1, &cRead, NULL))
			return (0);

		if (c=='\r')
			GoToNewLine();

		else
			if (!WriteFile(hStdout, &c, cRead, &cWritten, NULL))
				return (0);
		
		if (c=='q')
		{
			GoToNewLine();
			break;
		}
	} 

	return (1);
}
