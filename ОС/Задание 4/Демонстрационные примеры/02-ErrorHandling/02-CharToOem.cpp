// ѕример перекодировки русских букв дл€ вывода на консоль,
// использу€ функцию CharToOem.
// ќбратна€ перекодировка выполн€еитс€ функцией OemToChar.
// OEM=original equipment manufacturer

#include <windows.h>
#include <iostream.h>

int main()
{

	char big[] = "јЅ¬√ƒ≈®∆«»… ЋћЌќѕ–—“”‘’÷„ЎўЏџ№Ёёя";
	char sml[] = "абвгдеЄжзийклмнопрстуфхцчшщъыьэю€";

	CharToOem(big,big);
	CharToOem(sml,sml);

	cout << big << endl;
	cout << sml << endl;

	return 0;
}
