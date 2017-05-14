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

		// ��������� ������� � ������ �������� ��� ������������� � Win98
	hSemaphore = OpenSemaphore(SEMAPHORE_ALL_ACCESS, FALSE, lpSemaphoreName);
	if (hSemaphore == NULL)
	{
		cout << "Open semaphore failed." << endl;
		cout << "Input any char to exit: ";
		cin >> c;
		return GetLastError();
	}
		// ��������� ������������ ���� ��� �����
	ifstream inFile("C:\\data.tmp");
	if (!inFile)
	{
		cout << "Open file failed." << endl;
		cout << "Input any char to exit." << endl;
		cin >> c;
		return (1);
	}
		// ������ ������ �� ����� � ������� �� �� �������
	for (int i = 0; i <10; i++)
	{
		int j;
			// ���� ������ ������ � ����
		WaitForSingleObject(hSemaphore, INFINITE);
			// ������ ������ �� �����
		inFile >> j;
			// ������� ����������� ������� �� �������
		cout << j << ' ';
	}
	cout << endl;
		// ��������� ���������� �������� � ������� ��������
	CloseHandle(hSemaphore);

	cout << "The file is read." << endl;
	cout << "Input any char to exit from the process: ";
	cin >> c;

	return 0;
}
