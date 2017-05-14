// Процесс создается функцией CreateProcess.
// Параметры и атрибуты консоли берутся
// из структуры STARTUPINFO.

#include <iostream.h>

int main()
{
	char c;

	cout<<"I am created."<<endl;
	cout<<"Press any char to finish: ";
	cin>>c;
	
	return 0;
}
