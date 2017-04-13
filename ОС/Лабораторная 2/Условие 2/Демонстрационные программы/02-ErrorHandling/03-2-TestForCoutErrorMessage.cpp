// Пример вывода сообщения об ошибке на консоль.
// Внимание: не забудьте включить в проект 
// файл 02-1-CoutErrorMessage.cpp.

#include <windows.h>
#include <iostream.h>

	// прототип функции для вывода сообщения об ошибке на консоль
void CoutErrorMessage();

	// тест для функции вывода сообщения об ошибке на консоль
int main()
{
	HANDLE	hHandle=NULL;

		// неправильный вызов функции закрытия дескриптора
	if (!CloseHandle(hHandle))
		CoutErrorMessage();

	return 0;
}
