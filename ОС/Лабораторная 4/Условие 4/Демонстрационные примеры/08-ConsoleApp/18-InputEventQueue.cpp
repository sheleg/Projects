// пример обработки входного буфера консоли
// программа работает в Windows 98
// для работы программы в Windows 2000 нужно
// щёлкнуть левой кнопкой мыши по пиктограмме
// консольного приложения или правой кнопкой мыши
// по заголовку консоли, потом выбрать в меню
// пункт свойства и снять флаг выделение мышью

#include <windows.h>
#include <iostream.h>

HANDLE hStdIn, hStdOut;	// дескрипторы стандартного ввода и вывода 
DWORD dwOldConsoleMode, dwNewConsoleMode;	// для режимов консоли
BOOL bRead=TRUE;	// для цикла обработки событий 

// функция обработки сообщений от клавиатуры
VOID KeyEventProc(KEY_EVENT_RECORD kir)
{
	cout<<"\tKey event record:"<<endl;
			// просто выводим на консоль содержимое записи
	cout<<"Value of bKeyDown = "<<kir.bKeyDown<<endl;
	cout<<"Value of wRepeatCount = "<<kir.wRepeatCount<<endl;
	cout<<"Value of wVirtualKeyCode = "<<kir.wVirtualKeyCode<<endl;
	cout<<"Value of wVirtualScanCode = "<<kir.wVirtualScanCode<<endl;
	cout<<"Value of uChar.AsciiChar = "<<kir.uChar.AsciiChar<<endl;
	cout<<"Value of dwControlKeyState = "<<kir.dwControlKeyState<<endl;

		// если ввели букву 'q', то выходим из цикла обработки событий
	if (kir.uChar.AsciiChar=='q')
		bRead=FALSE;
}

// функция обработки сообщений от мыши
VOID MouseEventProc(MOUSE_EVENT_RECORD mer)
{
	cout<<"\tMouse event record:"<<endl;
		// просто выводим на консоль содержимое записи
	cout<<"Value of dwMousePosition.X = "<<mer.dwMousePosition.X<<endl;
	cout<<"Value of dwMousePosition.Y = "<<mer.dwMousePosition.Y<<endl;
	cout<<"Value of dwButtonState = "<<mer.dwButtonState<<endl;
	cout<<"Value of dwControlKeyState = "<<mer.dwControlKeyState<<endl;
	cout<<"Value of dwEventFlags = "<<mer.dwEventFlags<<endl;
}
	
VOID ResizeEventProc(WINDOW_BUFFER_SIZE_RECORD wbsr)
{
		// изменяем размеры буфера вывода
	SetConsoleScreenBufferSize(hStdOut,wbsr.dwSize);
}
	

DWORD main(VOID) 
{
			// получить дескрипторы стандартного ввода и вывода	
    hStdIn=GetStdHandle(STD_INPUT_HANDLE); 
    if (hStdIn==INVALID_HANDLE_VALUE)
		cout<<"Get standard input handle failed."<<endl;
	hStdOut=GetStdHandle(STD_OUTPUT_HANDLE);
    if (hStdOut==INVALID_HANDLE_VALUE)
		cout<<"Get standard output handle failed."<<endl;

		// сохранить текущий режим консоли
    if (!GetConsoleMode(hStdIn,&dwOldConsoleMode)) 
		cout<<"Get console mode failed."<<endl;
	
		// установить новый режим консоли	
    dwNewConsoleMode=ENABLE_WINDOW_INPUT|ENABLE_MOUSE_INPUT; 
    if (!SetConsoleMode(hStdIn, dwNewConsoleMode))
		cout<<"Set console mode failed."<<endl;

	cout<<"Begin input event queue processing."<<endl;
	cout<<"Input 'q' to quit."<<endl<<endl;
		// цикл обработки входных событий
	while (bRead) 
	{
		INPUT_RECORD ir;// входная запись	 
		DWORD cNumRead;	// для числа прочитанных записей

			// Wait for the event
		WaitForSingleObject(hStdIn,INFINITE);

			// читаем запись ввода		
		if (!ReadConsoleInput( 
				hStdIn,	// input buffer handle 
				&ir,	// buffer to read into 
				1,		// size of read buffer 
				&cNumRead))	// number of records read
		{
			cout<<"Read console input failed."<<endl;
			break;
		}

			// Dispatch the events to the appropriate handler
		switch(ir.EventType)
		{
			case KEY_EVENT: // keyboard input 
				KeyEventProc(ir.Event.KeyEvent); 
				break; 

			case MOUSE_EVENT: // mouse input
				MouseEventProc(ir.Event.MouseEvent);
				break;

			case WINDOW_BUFFER_SIZE_EVENT: // scrn buf. resizing
				ResizeEventProc(
					ir.Event.WindowBufferSizeEvent);
				break; 

			case FOCUS_EVENT:  // disregard focus events
				break;

			case MENU_EVENT:   // disregard menu events
				break;

			default:
				cout<<"Unknown event type."; 
				break; 
		}
    }
	
		// восстанавливаем старый режим консоли	
    if (!SetConsoleMode(hStdIn, dwOldConsoleMode))
		cout<<"Set console mode failed."<<endl;
	
    return 0; 
} 
