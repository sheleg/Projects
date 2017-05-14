
#include <windows.h>
#include <iostream.h>

void main()
{
	char c;
	HANDLE	hStdout;	// дескриптор стандартного вывода
	DWORD	dwLength;	// количество заполняемых клеток
	DWORD	dwWritten;	// для колическтва заполенных клеток
	COORD	coord;		// координаты первой клетки
	CONSOLE_SCREEN_BUFFER_INFO csbi;	// для параметров буфера экрана

		// читаем стандартный дескриптор вывода
	hStdout=GetStdHandle(STD_OUTPUT_HANDLE);
		// читаем параметры выходного буфера
	if (!GetConsoleScreenBufferInfo(hStdout,&csbi))
		cout<<"Console screen buffer info failed."<<endl;

		// вычисляем размер буфера экрана в символах
	dwLength=csbi.dwSize.X*csbi.dwSize.Y;
	coord.X=0;	// начинаем заполнять буфер с первой клетки
	coord.Y=0;      	

	cout<<"Input any char to fill screen buffer: ";
	cin>>c;
		// заполняем буфер экрана символом, который ввели
	if (!FillConsoleOutputCharacter(
			hStdout,	// стандартный дескриптор вывода
			c,			// символ заполнения 
			dwLength,	// длина буфера в символах
			coord,		// индекс первой клетки
			&dwWritten))// количество заполненных клеток
		cout<<"Fill screen buffer failed."<<endl;

	cout<<"In order to clear screen buffer, press any char: ";
	cin>>c;
		// очищаем буфер экрана пробелами
	if (!FillConsoleOutputCharacter(
			hStdout,	// стандартный дескриптор вывода
			' ',			// символ заполнения 
			dwLength,	// длина буфера в символах
			coord,		// индекс первой клетки
			&dwWritten))// количество заполненных клеток
		cout<<"Fill screen buffer failed."<<endl;
}
