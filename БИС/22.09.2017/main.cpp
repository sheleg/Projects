#include <io.h>
#include <fcntl.h>
#include <iostream>
#include <windows.h>
#include <iomanip>
using namespace std;


int main()
{
	_setmode(_fileno(stdout), _O_U8TEXT);
	for (int i = 0; i < 256; i++)
	{
		char smb = i;
		if (i % 16 == 0)
			wcout << "\n";
		if (i >= 7 && i <= 15){
			if (i == 7)
			{
				wcout << " " << L"\u2022" << "-" << setw(2) << hex << 7;
				wcout << " " << L"\u25D8" << "-" << setw(2) << hex << 8;
				wcout << " " << L"\u25CB" << "-" << setw(2) << hex << 9;
				wcout << " " << L"\u25D9" << "-" << setw(2) << hex << 10;
				wcout << " " << L"\u2642" << "-" << setw(2) << hex << 11;
				wcout << " " << L"\u2640" << "-" << setw(2) << hex << 12;
				wcout << " " << L"\u266A" << "-" << setw(2) << hex << 13;
				wcout << " " << L"\u266B" << "-" << setw(2) << hex << 14;
				wcout << " " << L"\u263C" << "-" << setw(2) << hex << 15;
			}
		}
		else
			wcout << " " << smb << "-" << setw(2) << hex << i;
	}
	wcout << endl;

    return 0;
}

