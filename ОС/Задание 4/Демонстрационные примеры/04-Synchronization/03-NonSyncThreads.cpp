// ������ ������ �������������������� �������

#include <windows.h>
#include <iostream.h>

DWORD WINAPI thread(LPVOID)
{
	int i,j;
	
	for (j = 0; j < 10; j++)
	{
		for (i = 0; i < 10; i++)
		{
			cout << j << " ";
			cout.flush();
			Sleep(20);
		}
		cout << endl;
	}
		
	return 0;
}

int main()
{
	int i,j;
	HANDLE	hThread;
	DWORD	IDThread;

	hThread=CreateThread(NULL, 0, thread, NULL, 0, &IDThread);
	if (hThread == NULL)
		return GetLastError();

		// ��� ��� ������ ������������������, 
		// �� ��������� ������ ��������������
	for (j = 10; j < 20; j++)
	{
		for (i = 0; i < 10; i++)
		{
			cout << j << " ";
			cout.flush();
			Sleep(20);
		}
		cout << endl;
	}
		// ���� ���� ����� thread �������� ���� ������
	WaitForSingleObject(hThread, INFINITE);

	return 0;
}
