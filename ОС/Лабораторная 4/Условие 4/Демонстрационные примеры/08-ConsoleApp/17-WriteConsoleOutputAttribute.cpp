// вызова функции WriteConsoleOutputAttribute

#include <windows.h>
#include <iostream.h>

void main()
{
	HANDLE	hStdOut;// дескриптор стандартного вывода
	WORD wBuf[80];	// массив атрибутов
	CHAR cBuf[80];	// массив символов
	CHAR c;			// для символа, который будем выводить
	int		n;		// количество символов, которые будем выводить
	DWORD	dwWritten;	// сколько вывели на самом деле
	CONSOLE_SCREEN_BUFFER_INFO csbi;// для параметров буфера экрана


		// получаем стандартные дескрипторы вывода
	hStdOut=GetStdHandle(STD_OUTPUT_HANDLE);

		// вводим символ, который будем выводить
	cout<<"Input a char to write: ";
	cin>>c;
	cout<<"Input a number of chars (1-80): ";
	cin>>n;

		// заполняем массив символов и атрибутов
	for (int i=0; i<n; i++)
	{
		cBuf[i]=c;
		wBuf[i]=BACKGROUND_BLUE|BACKGROUND_INTENSITY|
		FOREGROUND_RED|FOREGROUND_GREEN|FOREGROUND_INTENSITY;
	}

		// получаем текущую позицию курсора
	if (!GetConsoleScreenBufferInfo(hStdOut,&csbi))
		cout<<"Console screen buffer info failed."<<endl;

		// пишем символ в буфер экрана
	if (!WriteConsoleOutputCharacter(
			hStdOut,	// дескриптор буфера экрана
			cBuf,		// символы, которые выводим
			n,			// количество символов
			csbi.dwCursorPosition,	// координаты, куда выводим
			&dwWritten))	// количество выведенных символов
		cout<<"Write console output character failed."<<endl;

		// пишем атрибуты в буфер экрана
	if (!WriteConsoleOutputAttribute(
			hStdOut,
			wBuf,
			n,
			csbi.dwCursorPosition,
			&dwWritten))
		cout<<"Write console output attribute failed."<<endl;

		// пропустим строку, чтобы было видно что вывели
	cout<<endl;
}
