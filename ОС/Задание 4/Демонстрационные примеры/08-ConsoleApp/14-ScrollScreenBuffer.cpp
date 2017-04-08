// пример использовани€ функций ReadFile и WriteFile
// отключаем режимы ENABLE_LINE_INPUT и ENABLE_ECHO_INPUT

#include <windows.h>
 
HANDLE hStdout, hStdin;

// функци€ перехода на новую строку в буфере экрана
void GoToNewLine(void)
{
	CONSOLE_SCREEN_BUFFER_INFO csbi;
	SMALL_RECT srScroll;
	SMALL_RECT srClip;
	COORD coord;
	CHAR_INFO ci;

		// читаем информацию о буфере экрана
	if (!GetConsoleScreenBufferInfo(hStdout, &csbi)) 
		MessageBox(
			NULL, 
			"Get console screen buffer info failed",
			"Win32 API error",
			MB_OK | MB_ICONINFORMATION
		);
		
		// переходим на первый столбец
	csbi.dwCursorPosition.X=0; 
		// если это не последн€€ строка,
	if ((csbi.dwCursorPosition.Y+1)<csbi.dwSize.Y)
		// то переводим курсор на следующую строку 
		csbi.dwCursorPosition.Y+=1;
		// иначе, прокручиваем буфер экрана
	else
	{
			// координаты пр€моугольника, который прокручиваем
		srScroll.Left=0;
		srScroll.Top=1;
		srScroll.Right=csbi.dwSize.X;
		srScroll.Bottom=csbi.dwSize.Y;
			// координаты пр€моугольника буфера экрана 
		srClip.Left=0;
		srClip.Top=0;
		srClip.Right=csbi.dwSize.X;
		srClip.Bottom=csbi.dwSize.Y;
			// устанавливаем новые координаты 
			// левого угла пр€моугольника srScroll
		coord.X=0;
		coord.Y=0;
			// устанавливаем атрибуты и символ заполнитель
			// дл€ последней строки
		ci.Attributes=csbi.wAttributes;
		ci.Char.AsciiChar=' ';
			// прокручиваем пр€моугольник srScroll
		if (!ScrollConsoleScreenBuffer(
				hStdout,	// дескриптор стандартного вывода
				&srScroll,	// прокручиваемый пр€моугольник
				&srClip,	// буфер экрана
				coord,		// начало буфера экрана
				&ci))		// атрибуты и символ заполнитель 
			MessageBox(
				NULL, 
				"Set console window info failed",
				"Win32 API error",
				MB_OK | MB_ICONINFORMATION
			);
	}
		// теперь устанавливаем курсор	
	if (!SetConsoleCursorPosition(hStdout, csbi.dwCursorPosition))
			MessageBox(
				NULL, 
				"Set console cursor position failed",
				"Win32 API error",
				MB_OK | MB_ICONINFORMATION
			);
} 
 
int main(void) 
{
	LPSTR lpszPrompt1="Input 'q' to exit from read cycle.\n"; 
	CHAR  c; 
	DWORD cRead, cWritten;
	DWORD dwOldMode, dwNewMode; 

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

		// читаем режимы, установленные по умолчанию
    if (!GetConsoleMode(hStdin, &dwOldMode))
		MessageBox(
			NULL, 
			"Get console mode failed",
			"Win32 API error",
			MB_OK | MB_ICONINFORMATION
		);

		// отключаем режиы ENABLE_LINE_INPUT и ENABLE_ECHO_INPUT
    dwNewMode=dwOldMode & ~(ENABLE_LINE_INPUT | ENABLE_ECHO_INPUT); 
		// устанавливаем новый режим
    if (!SetConsoleMode(hStdin, dwNewMode))
		MessageBox(
			NULL, 
			"Set console mode failed",
			"Win32 API error",
			MB_OK | MB_ICONINFORMATION
		);
		
		// выводим сообщение о том, как выйти из цикла чтени€ 
	if (!WriteFile( 
			hStdout,		// дескриптор стандартного вывода 
			lpszPrompt1,	// строка, которую выводим 
			lstrlen(lpszPrompt1),	// длина строки 
			&cWritten,		// количество записанных байт 
			NULL))			// синхроный вывод
		return (0);	// ошибка вывода

		// цикл чтени€
	while (TRUE)
	{ 
		if (!ReadFile(hStdin, &c, 1, &cRead, NULL))
			return (0);

		if (c=='\r')
			GoToNewLine();

		else
			if (!WriteFile(hStdout, &c, cRead, &cWritten, NULL))
				return (0);
		
		if (c=='q')
		{
			GoToNewLine();
			break;
		}
	} 

	return (1);
}
