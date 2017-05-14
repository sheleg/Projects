// Пример синхронизации потоков в разных  
// процессах с использованием семафора

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

		// создаем семафор, отмечающий вывод в файл
	hSemaphore = CreateSemaphore(NULL, 0, 10, lpSemaphoreName);
	if (hSemaphore == NULL)
	{
		cout << "Create semaphore failed." << endl;
		cout << "Input any char to exit." << endl;
		cin >> c;
		return GetLastError();
	}
		// создаем файл для вывода
	ofstream outFile("C:\\data.tmp");
	if (!outFile)
	{
		cout << "Open file failed." << endl;
		cout << "Input any char to exit." << endl;
		cin >> c;
		return (1);
	}
		// запускаем процесс, который выводит данные из файла
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

		// записываем данные в файл
	for (int i = 0; i <10; i++)
	{
		outFile << i << ' ';
		outFile.flush();
			// отмечаем, что записали один элемент в файл 
		ReleaseSemaphore(hSemaphore, 1, &count);
	}

	cout << "Data are written to the file." << endl;
		// ждем пока запущенный процесс прочитает данные
	WaitForSingleObject(pi.hProcess, INFINITE);
		// закрываем дескрипторы этого процесса
	CloseHandle(pi.hProcess);
	CloseHandle(pi.hThread);
		// закрываем дескриптор семафора в текущем процессе
	CloseHandle(hSemaphore);

	return 0;
}
