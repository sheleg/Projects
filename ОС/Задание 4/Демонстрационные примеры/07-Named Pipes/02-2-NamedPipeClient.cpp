// ������ �������� ������� ������������ ������.

#include <windows.h>
#include <iostream.h>

int main()
{
	char 	c;		// ��������� ������
	HANDLE 	hNamedPipe;
	char 	machineName[80];
	char 	pipeName[80];
	char 	lpszOutMessage[]="How do you do server?";	// ��������� �������
	DWORD 	dwBytesWritten;	// ��� ����� ���������� ����
	char 	lpszInMessage[80];	// ��� ��������� �� �������
	DWORD 	dwBytesRead;		// ��� ����� ����������� ����
	
		// ������ ��� ������ � ����, �� ������� �������� ������
	cout << "Enter a name of the server machine: ";
	cin >> machineName;
		// ����������� ��� ������ � ��� ������
	wsprintf(pipeName, "\\\\%s\\pipe\\demo_pipe",
		machineName);
	
		// ����������� � ����������� �������
	hNamedPipe = CreateFile(
		pipeName,				// ��� ������
		GENERIC_READ | GENERIC_WRITE,		// ������ � ���������� � �����
		FILE_SHARE_READ | FILE_SHARE_WRITE,	// ��������� ������ � ������ � �����
		(LPSECURITY_ATTRIBUTES) NULL,		// ������ �� ���������
		OPEN_EXISTING,				// ��������� ������������ �����
		FILE_ATTRIBUTE_NORMAL,			// �������� �� ���������
		(HANDLE)NULL				// �������������� ��������� ���
		);
	
		// ��������� ����� � �������
	if (hNamedPipe==INVALID_HANDLE_VALUE)
	{
		cerr << "Connection with the named pipe failed." << endl
			<< "The last error code: " << GetLastError() << endl;
		cout << "Press any char to finish the client: ";
		cin >> c;
		return (0);
	}
		// ����� � ����������� �����
	if (!WriteFile(
		hNamedPipe,		// ���������� ������
		lpszOutMessage,		// ������
		sizeof(lpszOutMessage),	// ������ ������
		&dwBytesWritten,	// ���������� ���������� ����
		(LPOVERLAPPED)NULL	// ���������� ������
		))
	{
			// ������ ������
		cerr << "Writing to the named pipe failed: " << endl
			<< "The last error code: " << GetLastError() << endl;
		cout << "Press any char to finish the client: ";
		cin >> c;
		CloseHandle(hNamedPipe);
		return (0);
	}
		// ������� ��������� ��������� �� �������
	cout << "The client has send the following message to a server: "
		<< endl << "\t" << lpszOutMessage << endl;
		// ������ �� ������������ ������
	if (!ReadFile(
		hNamedPipe,		// ���������� ������
		lpszInMessage,		// ������
		sizeof(lpszInMessage),	// ������ ������
		&dwBytesRead,		// ���������� ���������� ����
		(LPOVERLAPPED)NULL	// ���������� ������
		))
	{
			// ������ ������
		cerr << "Reading to the named pipe failed: " << endl
			<< "The last error code: " << GetLastError() << endl;
		cout << "Press any char to finish the client: ";
		cin >> c;
		CloseHandle(hNamedPipe);
		return (0);
	}
		// ������� ���������� ��������� �� �������
	cout << "The client has received the following message from a server: "
		<< endl << "\t" << lpszInMessage << endl;
		// ��������� ���������� ������
	CloseHandle(hNamedPipe);
		// ��������� �������
	cout << "Press any char to finish the client: ";
	cin >> c;
	return (1);
}
