// Пример вывода сообщения об ошибке в MessageBox.
// Внимание: не забудьте включить в проект
// файл 01-1-ErrorMessageBox.cpp .

#include <windows.h>

	// прототип функции вывода сообщения об ошибке в MessageBox
void ErrorMessageBox();

	// тест для функции вывода сообщения об ошибке на консоль
int main()
{
	HANDLE	hHandle=NULL;

		// неправильный вызов функции закрытия дескриптора
	if (!CloseHandle(hHandle))
		ErrorMessageBox();

	return 0;
}
