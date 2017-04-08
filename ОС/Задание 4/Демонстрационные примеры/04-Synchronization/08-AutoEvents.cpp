// ������ ������������� ������� ��� ������
// ������� � �������������� �������

#include <windows.h>
#include <iostream.h>

volatile int n;
HANDLE hOutEvent, hAddEvent;

DWORD WINAPI thread(LPVOID)
{
	int i;
	
	for (i = 0; i < 10; i++)
	{
		n++;
		if (i == 4)
		{
			SetEvent(hOutEvent);
			WaitForSingleObject(hAddEvent, INFINITE);
		}
	}
		
	return 0;
}

int main()
{
	HANDLE	hThread;
	DWORD	IDThread;

	cout << "An initial value of n = " << n << endl;

		// ������� ������� � �������������� �������
	hOutEvent = CreateEvent(NULL, FALSE, FALSE, NULL);
	if (hOutEvent == NULL)
		return GetLastError();
	hAddEvent = CreateEvent(NULL, FALSE, FALSE, NULL);
	if (hAddEvent == NULL)
		return GetLastError();

		// ������� ����� ������� thread
	hThread = CreateThread(NULL, 0, thread, NULL, 0, &IDThread);
	if (hThread == NULL)
		return GetLastError();

		// ���� ���� ����� thread �������� �������� ������
	WaitForSingleObject(hOutEvent, INFINITE);
		// ������� �������� ����������
	cout << "An intermediate value of n = " << n << endl;
		// ��������� ������ �������� ������ thread
	SetEvent(hAddEvent);

	WaitForSingleObject(hThread, INFINITE);
	CloseHandle(hThread);

	CloseHandle(hOutEvent);
	CloseHandle(hAddEvent);

	cout << "A final value of n = " << n << endl;

	return 0;
}
