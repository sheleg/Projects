// Пример процесса сервера именованного канала.
// Сервер принимает сообщение от клиента и посылает ему
// сообщение в ответ.
// В этом случае для работы в локальной сети вход на
// клиентскую машину может быть выполнен с любым именем и паролем.

#include <windows.h>
#include <iostream.h>

int main()
{
	char 	c;	// служебный символ
	SECURITY_ATTRIBUTES sa;	// атрибуты защиты
	SECURITY_DESCRIPTOR sd;	// дескриптор защиты
	HANDLE 	hNamedPipe;
	char 	lpszInMessage[80];	// для сообщения от клиента
	DWORD 	dwBytesRead;		// для числа прочитанных байт
	char 	lpszOutMessage[] = "The server has received a message."; // обратное сообщение
	DWORD 	dwBytesWrite;		// для числа записанных байт

		// инициализация атрибутов защиты
	sa.nLength = sizeof(sa);
	sa.bInheritHandle = FALSE;	// дескриптор канала ненаследуемый
		// инициализируем дескриптор защиты
	InitializeSecurityDescriptor(&sd,SECURITY_DESCRIPTOR_REVISION);
		// устанавливаем атрибуты защиты, разрешая доступ всем пользователям
	SetSecurityDescriptorDacl(&sd, TRUE, NULL, FALSE);
	sa.lpSecurityDescriptor = &sd;
		// создаем именованный канал для чтения
	hNamedPipe = CreateNamedPipe(
		"\\\\.\\pipe\\demo_pipe",	// имя канала
		PIPE_ACCESS_DUPLEX,		// читаем из канала и пишем в канал
		PIPE_TYPE_MESSAGE | PIPE_WAIT,	// синхронная передача сообщений
		1,		// максимальное количество экземпляров канала 
		0,		// размер выходного буфера по умолчанию
		0,		// размер входного буфера по умолчанию
		INFINITE,	// клиент ждет связь 500 мс
		&sa		// доступ для всех пользователей
	);
		// проверяем на успешное создание
	if (hNamedPipe == INVALID_HANDLE_VALUE)
	{
		cerr << "Creation of the named pipe failed." << endl
			<< "The last error code: " << GetLastError() << endl;
		cout << "Press any char to finish server: ";
		cin >> c;
		return (0);
	}
	
		// ждем пока клиент свяжется с каналом
	cout << "The server is waiting for connection with a client." << endl;
	if(!ConnectNamedPipe(
			hNamedPipe,		// дескриптор канала
			(LPOVERLAPPED)NULL	// связь синхронная
		))
	{
		cerr << "The connection failed." << endl
			<< "The last error code: " << GetLastError() << endl;
		CloseHandle(hNamedPipe);
		cout << "Press any char to finish the server: ";
		cin >> c;
		return (0);
	}
		// читаем сообщение от клиента
	if (!ReadFile(
			hNamedPipe,			// дескриптор канала
			lpszInMessage,			// адрес буфера для ввода данных
			sizeof(lpszInMessage),		// число читаемых байт
			&dwBytesRead,			// число прочитанных байт
			(LPOVERLAPPED)NULL		// передача данных синхронная
		))
	{
		cerr << "Data reading from the named pipe failed." << endl
			<< "The last error code: " << GetLastError() << endl;
		CloseHandle(hNamedPipe);
		cout << "Press any char to finish the server: ";
		cin >> c;
		return (0);
	}
		// выводим полученное от клиента сообщение на консоль
	cout << "The server has receivrd the following message from a client: "
		<< endl << "\t" << lpszInMessage << endl;
		// отвечаем клиенту
	if (!WriteFile(
			hNamedPipe,			// дескриптор канала
			lpszOutMessage,			// адрес буфера для вывода данных
			sizeof(lpszOutMessage),		// число записываемых байт
			&dwBytesWrite,			// число записанных байт
			(LPOVERLAPPED)NULL		// передача данных синхронная
		))
	{
		cerr << "Data writing to the named pipe failed." << endl
			<< "The last error code: " << GetLastError() << endl;
		CloseHandle(hNamedPipe);
		cout << "Press any char to finish the server: ";
		cin >> c;
		return (0);
	}
		// выводим посланное клиенту сообщение на консоль
	cout << "The server send the following message to a client: "
		<< endl << "\t" << lpszOutMessage << endl;
		// закрываем дескриптор канала 
	CloseHandle(hNamedPipe);
		// завершаем процесс
	cout << "Press any char to finish the server: ";
	cin >> c;
	return (1);
}
