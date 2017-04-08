// �������������������� ������, ������������� � ������ ���������

#include <windows.h>
#include <iostream.h>

int main()
{
	char	lpszAppName[] = "C:\\ConsoleProcess.exe";
	STARTUPINFO	si;
	PROCESS_INFORMATION	pi;
	char	c;

	ZeroMemory(&si, sizeof(STARTUPINFO));
	si.cb = sizeof(STARTUPINFO);

		// ������� ����� ���������� �������
	if (!CreateProcess(lpszAppName, NULL, NULL, NULL, FALSE,
			NULL, NULL, NULL, &si, &pi))
	{
		cout << "The new process is not created." << endl;
		cout << "Exit any char to exit." << endl;
		cin >> c;
		return GetLastError();
	}

		// ������� �� ����� ������
	for (int j = 0; j < 10; j++)
	{
		for (int i = 0; i < 10; i++)
		{
			cout << j << " ";
			cout.flush();
			Sleep(10);
		}
		cout << endl;
	}
		// ���� ���� �������� ������� �������� ������
	WaitForSingleObject(pi.hProcess, INFINITE);
		// ��������� ����������� ��������� �������� � ������� ��������
	CloseHandle(pi.hThread);
	CloseHandle(pi.hProcess);

	return 1;
}
