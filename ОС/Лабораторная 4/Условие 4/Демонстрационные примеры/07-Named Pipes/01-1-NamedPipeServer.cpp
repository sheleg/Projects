// ������ �������� ������� ������������ ������.

#include <windows.h>
#include <iostream.h>

int main()
{
	char c;		// ��������� ������
	HANDLE hNamedPipe;
	
		// ������� ����������� ����� ��� ������
	hNamedPipe = CreateNamedPipe(
		"\\\\.\\pipe\\demo_pipe",	// ��� ������
		PIPE_ACCESS_INBOUND,		// ������ �� ������
		PIPE_TYPE_MESSAGE | PIPE_WAIT,	// ���������� �������� ���������
		1,		// ������������ ���������� ����������� ������ 
		0,		// ������ ��������� ������ �� ���������
		0,		// ������ �������� ������ �� ���������
		INFINITE,	// ������ ���� ����� ���������� �����
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
	
		// ���� ���� ������ �������� � �������
	cout << "The server is waiting for connection with a client." << endl;
	if(!ConnectNamedPipe(
			hNamedPipe,		// ���������� ������
			(LPOVERLAPPED)NULL	// ����� ����������
		))
	{
		cerr << "The connection failed." << endl
			<< "The last error code: " << GetLastError() << endl;
		CloseHandle(hNamedPipe);
		cout << "Press any char to finish the server: ";
		cin >> c;
		return (0);
	}
		// ������ ������ �� ������
	for (int i = 0; i < 10; i++)
	{
		int nData;
		DWORD dwBytesRead;
		if (!ReadFile(
				hNamedPipe,		// ���������� ������
				&nData,			// ����� ������ ��� ����� ������
				sizeof(nData),		// ����� �������� ����
				&dwBytesRead,		// ����� ����������� ����
				(LPOVERLAPPED)NULL	// �������� ������ ����������
			))
		{
			cerr << "Data reading from the named pipe failed." << endl
				<< "The last error code: " << GetLastError() << endl;
			CloseHandle(hNamedPipe);
			cout << "Press any char to finish the server: ";
			cin >> c;
			return (0);
		}
			// ������� ����������� ������ �� �������
		cout << "The number " << nData << " was read by the server" << endl;
	}
		// ��������� ���������� ������ 
	CloseHandle(hNamedPipe);
		// ��������� �������
	cout << "The data are read by the server."<<endl;
	cout << "Press any char to finish the server: ";
	cin >> c;
	return (1);
}
