// ������������� �������, ������������� �
// ������ ���������, � �������������� ��������

#include <windows.h>
#include <iostream.h>

int main()
{
	HANDLE	hMutex;
	int		i,j;
	char	c;

		// ��������� �������
	hMutex = OpenMutex(SYNCHRONIZE, FALSE, "DemoMutex");
	if (hMutex == NULL)
	{
		cout << "Open mutex failed." << endl;
		cout << "Input any char to exit." << endl;
		cin >> c;
		return GetLastError();
	}

	for (j = 10; j < 20; j++)
	{
			// ����������� �������
		WaitForSingleObject(hMutex, INFINITE);
		for (i = 0; i < 10; i++)
		{
			cout << j << " ";
			cout.flush();
			Sleep(5);
		}
		cout << endl;
			// ����������� �������
		ReleaseMutex(hMutex);
	}
		// ��������� ���������� �������
	CloseHandle(hMutex);
		
	return 0;
}
