// ������ ������ ������������������ �������.
// �������� ������ � Windows 2000.

#include <windows.h>
#include <iostream.h>

CRITICAL_SECTION cs;

DWORD WINAPI thread(LPVOID)
{
	int i,j;
	
	for (j = 0; j < 10; j++)
	{
			// ������� ����� � ����������� ������
		TryEnterCriticalSection (&cs);
		for (i = 0; i < 10; i++)
		{
			cout << j << " ";
			cout.flush();
		}
		cout << endl;
			// ����� �� ����������� ������
		LeaveCriticalSection(&cs);
	}
		
	return 0;
}

int main()
{
	int i,j;
	HANDLE	hThread;
	DWORD	IDThread;
		// �������������� ����������� ������
	InitializeCriticalSection(&cs);

	hThread=CreateThread(NULL, 0, thread, NULL, 0, &IDThread);
	if (hThread == NULL)
		return GetLastError();

		// ������ ����������������, ������� ������
		// ������ �������� ������ ���������� �����
	for (j = 10; j < 20; j++)
	{
			// ������� ����� � ����������� ������
		TryEnterCriticalSection(&cs);
		for (i = 0; i < 10; i++)
		{
			cout << j << " ";
			cout.flush();
		}
		cout << endl;
			// ����� �� ����������� ������
		LeaveCriticalSection(&cs);
	}
		// ������� ����������� ������
	DeleteCriticalSection(&cs);
		// ���� ���������� ������ ������ thread
	WaitForSingleObject(hThread, INFINITE);

	return 0;
}
