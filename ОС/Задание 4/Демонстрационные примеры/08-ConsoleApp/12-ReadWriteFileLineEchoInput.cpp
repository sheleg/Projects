// пример использования функций ReadFile и WriteFile
// по умолчанию включены режимы:
// ENABLE_LINE_INPUT, ENABLE_ECHO_INPUT,ENABLE_PROCESSED_INPUT

#include <windows.h>
 
HANDLE hStdout, hStdin; 
 
int main(void) 
{
	LPSTR lpszPrompt1="Input 'q' to exit.\n"; 
	LPSTR lpszPrompt2="Input string and press Enter:\n";
	CHAR  chBuffer[80]; 
	DWORD cRead, cWritten;

		// читаем дескрипторы стандартного ввода и вывода 
	hStdin=GetStdHandle(STD_INPUT_HANDLE); 
	hStdout=GetStdHandle(STD_OUTPUT_HANDLE); 
	if (hStdin==INVALID_HANDLE_VALUE || hStdout==INVALID_HANDLE_VALUE) 
		MessageBox(
			NULL, 
			"Get standard handle failed",
			"Win32 API error",
			MB_OK | MB_ICONINFORMATION
		);

		// по умолчанию установлены режимы ввода: ENABLE_LINE_INPUT,
		// ENABLE_ECHO_INPUT, ENABLE_PROCESSED_INPUT 

		// выводим сообщение о том, как выйти из цикла чтения
	if (!WriteFile( 
			hStdout,		// дескриптор стандартного вывода 
			lpszPrompt1,	// строка, которую выводим 
			lstrlen(lpszPrompt1),	// длина строки 
			&cWritten,		// количество записанных байт 
			NULL))			// синхроный вывод
		return (0);	// ошибка записи
		
		// цикл чтения
	while (TRUE) 
	{
			// выводим сообщение о вводе строки
		if (!WriteFile( 
				hStdout,		// дескриптор стандартного вывода 
				lpszPrompt2,	// строка, которую выводим 
				lstrlen(lpszPrompt2),	// длина строки 
				&cWritten,		// количество записанных байт 
				NULL))			// синхроный вывод
			return (0);	// ошибка записи

			// вводим строку с клавиатуры и дублируем её на экран
		if (!ReadFile( 
				hStdin,		// дескриптор стандартного ввода 
				chBuffer,	// буфер для чтения 
				80,			// длина буфера 
				&cRead,		// количество прочитанных байт 
				NULL))		// синхронный ввод 
			return (0);	// ошибка чтения

			// выход из цикла
		if (chBuffer[0]=='q') break;
	}

	return (1);
}
