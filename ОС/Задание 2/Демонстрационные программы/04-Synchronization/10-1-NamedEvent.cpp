// Пример синхронизации потоков в разных процессах 
// с использованием именованного события

#include <windows.h>
#include <iostream.h>

HANDLE hInEvent;
CHAR lpEventName[]="InEventName";

int main()
{
	char c;

	hInEvent = OpenEvent(EVENT_MODIFY_STATE, FALSE, lpEventName);
	if (hInEvent == NULL)
	{
		cout << "Open event failed." << endl;
		cout << "Input any char to exit." << endl;
		cin >> c;
		return GetLastError();
	}

	cout << "Input any char: ";
	cin >> c;
		// устанавливаем событие о вводе символа	
	SetEvent(hInEvent);
		// закрываем дескриптор события в текущем процессе
	CloseHandle(hInEvent);

	cout << "Now input any char to exit from the process: ";
	cin >> c;

	return 0;
}
