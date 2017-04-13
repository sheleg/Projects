// Пример завершения процесса функцией ExitProcess

#include <windows.h>
#include <iostream.h>

volatile UINT count;
volatile char c;

void thread()
{
	for ( ; ; )
	{
		count++;
		Sleep(100);
	}
}

int main()
{
	HANDLE 	hThread;
	DWORD	IDThread;

	hThread = CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)thread, NULL, 0, &IDThread);
	if (hThread == NULL)
		return GetLastError();

	for ( ; ; )
	{		
		cout << "Input 'y' to display the count or 'e' to exit: ";
		cin >> (char)c;
		if (c == 'y')
			cout << "count = " << count << endl;
		if (c == 'e')
			ExitProcess(1);
	}
}
