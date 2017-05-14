// ������ �������� ������� ������������ ������.
// ������ ��������� ��������� �� ������� �
// �������� ��� ��������� � �����.
// ��������: � ���� ������ ��� ������ � ��������� ���� ����
// �� ���������� ������ ������ ���� �������� � ��� �� ������
// � �������, ��� � �� ������.

#include <windows.h>
#include <iostream.h>

int main()
{
	char 	c;			// ���������
	HANDLE 	hNamedPipe;
	char 	lpszInMessage[80];		// ��� ��������� �� �������
	DWORD 	dwBytesRead;		// ��� ����� ����������� ����
	char 	lpszOutMessage[]="The server has received a message."; // �������� ���������
	DWORD 	dwBytesWrite;		// ��� ����� ���������� ����
	
		// ������� ����������� ����� ��� ������
	hNamedPipe = CreateNamedPipe(
		"\\\\.\\pipe\\demo_pipe",	// ��� ������
		PIPE_ACCESS_DUPLEX,		// ������ �� ������ � ����� � �����
		PIPE_TYPE_MESSAGE | PIPE_WAIT,	// ���������� �������� ���������
		1,		// ������������ ���������� ����������� ������ 
		0,		// ������ ��������� ������ �� ���������
		0,		// ������ �������� ������ �� ���������
		INFINITE,	// ������ ���� ����� 500 ��
		(LPSECURITY_ATTRIBUTES)NULL	// ������ �� ���������
	);
		// ��������� �� �������� ��������
	if (hNamedPipe == INVALID_HANDLE_VALUE)
	{
		cerr << "Creation of the named pipe failed." << endl
			<< "The last error code: " << GetLastError() << endl;
		cout << "Press any char to finish server: ";
		cin >> c;
		return (0);
	}
	
		// ����, ���� ������ �������� � �������
	cout << "The server is waiting for connection with a client." << endl;
	if(!ConnectNamedPipe(
			hNamedPipe,		// ���������� ������
			(LPOVERLAPPED)NULL	// ����� ����������
		))
	{
		cerr << "The connection failed." << endl
			<< "The last error code: "<<GetLastError() << endl;
		CloseHandle(hNamedPipe);
		cout << "Press any char to finish the server: ";
		cin >> c;
		return (0);
	}
		// ������ ��������� �� �������
	if (!ReadFile(
			hNamedPipe,			// ���������� ������
			lpszInMessage,			// ����� ������ ��� ����� ������
			sizeof(lpszInMessage),		// ����� �������� ����
			&dwBytesRead,			// ����� ����������� ����
			(LPOVERLAPPED)NULL		// �������� ������ ����������
		))
	{
		cerr << "Data reading from the named pipe failed." << endl
			<< "The last error code: "<< GetLastError() << endl;
		CloseHandle(hNamedPipe);
		cout << "Press any char to finish the server: ";
		cin >> c;
		return (0);
	}
		// ������� ���������� �� ������� ��������� �� �������
	cout << "The server has received the following message from a client: "
		<< endl << "\t" << lpszInMessage << endl;
		// �������� �������
	if (!WriteFile(
			hNamedPipe,			// ���������� ������
			lpszOutMessage,			// ����� ������ ��� ������ ������
			sizeof(lpszOutMessage),		// ����� ������������ ����
			&dwBytesWrite,			// ����� ���������� ����
			(LPOVERLAPPED)NULL		// �������� ������ ����������
		))
	{
		cerr << "Data writing to the named pipe failed." << endl
			<< "The last error code: " << GetLastError() << endl;
		CloseHandle(hNamedPipe);
		cout << "Press any char to finish the server: ";
		cin >> c;
		return (0);
	}
		// ������� ��������� ������� ��������� �� �������
	cout << "The server send the following message to a client: "
		<< endl << "\t" << lpszOutMessage << endl;
		// ��������� ���������� ������ 
	CloseHandle(hNamedPipe);
		// ��������� �������
	cout << "Press any char to finish the server: ";
	cin >> c;
	return (1);
}
