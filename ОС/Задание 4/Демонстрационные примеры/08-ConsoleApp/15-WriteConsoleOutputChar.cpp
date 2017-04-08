
#include <windows.h>
#include <iostream.h>

void main()
{
	HANDLE	hStdOut;// дескриптор стандартного вывода
	CHAR	c;		// для символа который будем выводить
	COORD	coord;	// координаты куда будем выводить
	DWORD	dwWritten;	// сколько вывели на самомделе

		// получаем стандартные дескрипторы вывода
	hStdOut=GetStdHandle(STD_OUTPUT_HANDLE);

		// вводим символ, который будем выводить
	cout<<"Input a char to write: ";
	cin>>c;
		// устанавливаем координаты для вывода символа
	cout<<"Input X coordinate to write: ";
	cin>>coord.X;
	cout<<"Input Y coordinate to write: ";
	cin>>coord.Y;

		// пишем символ в буфер экрана
	if (!WriteConsoleOutputCharacter(
			hStdOut,	// дескриптор буфера экрана
			&c,		// символы, которые выводим
			1,		// всего один символ
			coord,	// координаты, куда выводим
			&dwWritten))	// количество выведенных символов
		cout<<"Write console output character failed."<<endl;
}
