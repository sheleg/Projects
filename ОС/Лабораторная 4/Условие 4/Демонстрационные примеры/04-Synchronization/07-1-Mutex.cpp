// —инхронизаци€ потоков, выполн€ющихс€ в
// разных процессах, с использованинм мьютекса

#include <windows.h>
#include <iostream.h>

int main()
{
	HANDLE	hMutex;
	int		i,j;
	char	c;

		// открываем мьютекс
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
			// захватываем мьютекс
		WaitForSingleObject(hMutex, INFINITE);
		for (i = 0; i < 10; i++)
		{
			cout << j << " ";
			cout.flush();
			Sleep(5);
		}
		cout << endl;
			// освобождаем мьютекс
		ReleaseMutex(hMutex);
	}
		// закрываем дескриптор объекта
	CloseHandle(hMutex);
		
	return 0;
}
