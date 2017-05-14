
#include <windows.h>
#include <iostream.h>

void main()
{
	char c;
	HANDLE	hStdout;	// дескриптор стандартного вывода
	WORD	wAttribute;	// цвет фона и текста

	cout<<"In order to set text attributes, press any char:";
	cin>>c;

		// читаем стандартный дескриптор вывода
	hStdout=GetStdHandle(STD_OUTPUT_HANDLE);
	
		// устанавливаем цвет фона зеленым, а цвет символов красным  
    wAttribute=BACKGROUND_GREEN|BACKGROUND_INTENSITY|
		FOREGROUND_RED|FOREGROUND_INTENSITY;
		// устанавливаем другой цвет текста и фона
	if (!SetConsoleTextAttribute(hStdout,wAttribute))
		cout<<"Set console text attribute failed."<<endl;

	cout<<"The text attributes was changed."<<endl;
	
}
