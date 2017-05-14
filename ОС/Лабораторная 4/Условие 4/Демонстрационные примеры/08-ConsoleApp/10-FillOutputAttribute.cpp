
#include <windows.h>
#include <iostream.h>

void main()
{
	char c;
	HANDLE	hStdout;	// дескриптор стандартного вывода
	WORD	wAttribute;	// цвет фона и текста
	DWORD	dwLength;	// количество заполняемых клеток
	DWORD	dwWritten;	// для колическтва заполенных клеток
	COORD	coord;		// координаты первой клетки
	CONSOLE_SCREEN_BUFFER_INFO csbi;	// для параметров буфера экрана

	cout<<"In order to fill console attributes, press any char:";
	cin>>c;
		// читаем стандартный дескриптор вывода
	hStdout=GetStdHandle(STD_OUTPUT_HANDLE);
		// читаем параметры выходного буфера
	if (!GetConsoleScreenBufferInfo(hStdout,&csbi))
		cout<<"Console screen buffer info failed."<<endl;

		// вычисляем размер буфера экрана в символах
	dwLength=csbi.dwSize.X*csbi.dwSize.Y;
	coord.X=0;	// начинаем заполнять буфер с первой клетки
	coord.Y=0;      	

		// устанавливаем цвет фона голубым, а цвет символов желтым  
    wAttribute=BACKGROUND_BLUE|BACKGROUND_INTENSITY|
		FOREGROUND_RED|FOREGROUND_GREEN|FOREGROUND_INTENSITY;
		// заполняем буфер атрибутами
	if (!FillConsoleOutputAttribute(
			hStdout,	// стандартный дескриптор вывода
			wAttribute,	// цвет фона и текста 
			dwLength,	// длина буфера в символах
			coord,		// индекс первой клетки
			&dwWritten))// количество заполненных клеток
		cout<<"Fill console attribute failed."<<endl;

	cout<<"The fill attributes was changed."<<endl;
}
