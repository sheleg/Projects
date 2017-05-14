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

		// открываем семафор с полным доступом для совместимости с Win98
	hSemaphore = OpenSemaphore(SEMAPHORE_ALL_ACCESS, FALSE, lpSemaphoreName);
	if (hSemaphore == NULL)
	{
		cout << "Open semaphore failed." << endl;
		cout << "Input any char to exit: ";
		cin >> c;
		return GetLastError();
	}
		// открываем существующий файл для ввода
	ifstream inFile("C:\\data.tmp");
	if (!inFile)
	{
		cout << "Open file failed." << endl;
		cout << "Input any char to exit." << endl;
		cin >> c;
		return (1);
	}
		// читаем данные из файла и выводим их на консоль
	for (int i = 0; i <10; i++)
	{
		int j;
			// ждем записи данных в файл
		WaitForSingleObject(hSemaphore, INFINITE);
			// читаем данные из файла
		inFile >> j;
			// выводим прочитанный элемент на консоль
		cout << j << ' ';
	}
	cout << endl;
		// закрываем дескриптор семафора в текущем процессе
	CloseHandle(hSemaphore);

	cout << "The file is read." << endl;
	cout << "Input any char to exit from the process: ";
	cin >> c;

	return 0;
}
