// Несинхронизированные потоки

#include <windows.h>
#include <iostream.h>

volatile int a[10];

DWORD WINAPI thread(LPVOID)
{
	int i;
	
	for (i = 0; i < 10; i++)
	{
		a[i] = i + 1;
		Sleep(17);
	}
		
	return 0;
}

int main()
{
	int i;
	HANDLE	hThread;
	DWORD	IDThread;
 
	cout << "An initial state of the array: ";
	for (i = 0; i < 10; i++)
		cout << a[i] <<' ';
	cout << endl;
		
		// создаем поток, который готовит элементы массива
	hThread = CreateThread(NULL, 0, thread, NULL, 0, &IDThread);
	if (hThread == NULL)
		return GetLastError();

		// поток main выводит элементы массива 
	cout << "A modified state of the array: ";
	for (i = 0; i < 10; i++)
	{
		cout << a[i] << ' ' << flush;
		Sleep(17);
	}
	cout << endl;

	CloseHandle(hThread);

	return 0;
}
