// ������ ������������� ������� ReadFile � WriteFile
// �� ��������� �������� ������:
// ENABLE_LINE_INPUT, ENABLE_ECHO_INPUT,ENABLE_PROCESSED_INPUT

#include <windows.h>
 
HANDLE hStdout, hStdin; 
 
int main(void) 
{
	LPSTR lpszPrompt1="Input 'q' to exit.\n"; 
	LPSTR lpszPrompt2="Input string and press Enter:\n";
	CHAR  chBuffer[80]; 
	DWORD cRead, cWritten;

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

		// �� ��������� ����������� ������ �����: ENABLE_LINE_INPUT,
		// ENABLE_ECHO_INPUT, ENABLE_PROCESSED_INPUT 

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
			// ������� ��������� � ����� ������
		if (!WriteFile( 
				hStdout,		// ���������� ������������ ������ 
				lpszPrompt2,	// ������, ������� ������� 
				lstrlen(lpszPrompt2),	// ����� ������ 
				&cWritten,		// ���������� ���������� ���� 
				NULL))			// ��������� �����
			return (0);	// ������ ������

			// ������ ������ � ���������� � ��������� � �� �����
		if (!ReadFile( 
				hStdin,		// ���������� ������������ ����� 
				chBuffer,	// ����� ��� ������ 
				80,			// ����� ������ 
				&cRead,		// ���������� ����������� ���� 
				NULL))		// ���������� ���� 
			return (0);	// ������ ������

			// ����� �� �����
		if (chBuffer[0]=='q') break;
	}

	return (1);
}
