// ������ ������������� ������� � ������  
// ��������� � �������������� ��������

#include <windows.h>
#include <iostream.h>
#include <fstream.h>

HANDLE	hSemaphore;
CHAR	lpSemaphoreName[] = "DataCount";

int main()
{
	char c;
	char szAppName[] = "C:\\ConsoleProcess.exe";
	STARTUPINFO si;
	PROCESS_INFORMATION pi;
	long count;

		// ������� �������, ���������� ����� � ����
	hSemaphore = CreateSemaphore(NULL, 0, 10, lpSemaphoreName);
	if (hSemaphore == NULL)
	{
		cout << "Create semaphore failed." << endl;
		cout << "Input any char to exit." << endl;
		cin >> c;
		return GetLastError();
	}
		// ������� ���� ��� ������
	ofstream outFile("C:\\data.tmp");
	if (!outFile)
	{
		cout << "Open file failed." << endl;
		cout << "Input any char to exit." << endl;
		cin >> c;
		return (1);
	}
		// ��������� �������, ������� ������� ������ �� �����
	ZeroMemory(&si, sizeof(STARTUPINFO));
	si.cb = sizeof(STARTUPINFO);
	if (!CreateProcess(szAppName, NULL, NULL, NULL, FALSE,
			CREATE_NEW_CONSOLE, NULL, NULL, &si, &pi))
	{
		cout << "Create process failed." << endl;
		cout << "Input any char to exit." << endl;
		cin >> c;
		return GetLastError();
	}

		// ���������� ������ � ����
	for (int i = 0; i <10; i++)
	{
		outFile << i << ' ';
		outFile.flush();
			// ��������, ��� �������� ���� ������� � ���� 
		ReleaseSemaphore(hSemaphore, 1, &count);
	}

	cout << "Data are written to the file." << endl;
		// ���� ���� ���������� ������� ��������� ������
	WaitForSingleObject(pi.hProcess, INFINITE);
		// ��������� ����������� ����� ��������
	CloseHandle(pi.hProcess);
	CloseHandle(pi.hThread);
		// ��������� ���������� �������� � ������� ��������
	CloseHandle(hSemaphore);

	return 0;
}
