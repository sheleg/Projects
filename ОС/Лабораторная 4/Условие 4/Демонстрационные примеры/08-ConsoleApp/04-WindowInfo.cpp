
#include <windows.h>
#include <iostream.h>

void main()
{
	char c;
	HANDLE	hStdOut;	// дескриптор стандартного вывода
	COORD coord;	// координаты окна
	SMALL_RECT sr;	// прямоугольник окна

		// читаем дескриптор стандартного вывода
	hStdOut=GetStdHandle(STD_OUTPUT_HANDLE);

		// читаем наибольший размер окна
	coord=GetLargestConsoleWindowSize(hStdOut);
		// выводим наибольшие размеры окна на консоль
	cout<<"The largest console window size: "<<endl;
	cout<<" X = "<<coord.X<<endl;
	cout<<" Y = "<<coord.Y<<endl<<endl;

	cout<<"Set new window rectangle in characters."<<endl<<endl;
		// устанавливаем прямоугольник окна в символах
	cout<<"Input left coordinate (0-79): ";
	cin>>sr.Left;
	cout<<"Input top coordinate (0-24): ";
	cin>>sr.Top;
	cout<<"Input right coordinate (0-79): ";
	cin>>sr.Right;
	cout<<"Input bottom coordinate (0-24): ";
	cin>>sr.Bottom;
		// устанавливаем новое окно
	if (!SetConsoleWindowInfo(hStdOut,TRUE,&sr))
		cout<<"Set console window info failed."<<endl;

	cout<<endl<<"Press any char to finish: ";
	cin>>c;

}
