// ������ �������� ������� ������������ ������.

#include <windows.h>
#include <iostream.h>

int main()
{
	char c;		// ��������� ������
	HANDLE hNamedPipe;
	char pipeName[] = "\\\\.\\pipe\\demo_pipe";
	
		// ����������� � ����������� �������
	hNamedPipe = CreateFile(
		 pipeName,			// ��� ������
		 GENERIC_WRITE,			// ���������� � �����
		 FILE_SHARE_READ,		// ��������� ������ ������ � �����
		 (LPSECURITY_ATTRIBUTES) NULL,	// ������ �� ���������
		 OPEN_EXISTING,			// ��������� ������������ �����
		 0,				// �������� �� ���������
		(HANDLE)NULL			// �������������� ��������� ���
	);
	
		// ��������� ����� � �������
	if (hNamedPipe == INVALID_HANDLE_VALUE)
	{
		cerr << "Connection with the named pipe failed." << endl
			<< "The last error code: " << GetLastError() << endl;
		cout << "Press any char to finish the client: ";
		cin >> c;
		return (0);
	}
		// ����� � ����������� �����
	for (int i = 0; i < 10; i++)
	{
		DWORD dwBytesWritten;		
		if (!WriteFile(
				hNamedPipe,		// ���������� ������
				&i,			// ������
				sizeof(i),		// ������ ������
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
			// ������� ����� �� �������
		cout << "The number " << i << " is written to the named pipe." << endl;
		Sleep(1000);
	}
		// ��������� ���������� ������
	CloseHandle(hNamedPipe);
		// ��������� �������
	cout << "The data are written by the client." << endl
		<< "Press any char to finish the client: ";
	cin >> c;
	return (1);
}
