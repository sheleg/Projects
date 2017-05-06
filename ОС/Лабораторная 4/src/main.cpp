#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
#include <string>
#include <windows.h>
#include <shlobj.h>

#define BEAR_DAY 0
#define BULL_DAY 1

using namespace std;

const char g_szClassName[] = "myWindowClass";
queue<int> inputDataCheck; 
vector<pair<int, int>> checkOutputData;

CRITICAL_SECTION inputDataCS;
CRITICAL_SECTION fileCS;
HANDLE openOutputStreamEvent;

/*Functions for the solution of a task*/
//_________________________
void easyTrading(string);
int getDescriptionOfDay(int, int, int, vector<int>, vector<double>);
double average(int, int, vector<double>);
//_________________________

LRESULT CALLBACK WndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam);
LRESULT CreateMainWindow(HINSTANCE);
string getPath();
void ThreadOfInputData(void*);
void ThreadOfSolvingProblem(void*);
void ThreadOfCheckOutputData(void*);
int checkData(ifstream&);


bool GetFolder(LPTSTR szPath)
{
	szPath[0] = 0;
	bool result = false;

	LPMALLOC pMalloc;
	if (SHGetMalloc(&pMalloc) == NOERROR) {
		BROWSEINFO bi;
		ZeroMemory(&bi, sizeof bi);
		bi.ulFlags = BIF_RETURNONLYFSDIRS;
		LPITEMIDLIST pidl = SHBrowseForFolder(&bi);
		if (pidl != NULL) {
			if (SHGetPathFromIDList(pidl, szPath))
				result = true;
			pMalloc->Free(pidl);
		}
		pMalloc->Release(); 
	}
	return result;
}

bool ChooseFolder(HWND hwnd, LPSTR lpszTitle, LPSTR lpBuffer) {
	BROWSEINFO bi;
	bool res = false;
	LPITEMIDLIST pidlBrowse;
	//ZeroMemory(&bi, sizeof(BROWSEINFO));
	bi.hwndOwner = hwnd;
	bi.pszDisplayName = (LPWSTR)lpBuffer;
	bi.lpszTitle = (LPCWSTR)lpszTitle;
	bi.ulFlags = BIF_RETURNONLYFSDIRS | BIF_DONTGOBELOWDOMAIN;
	pidlBrowse = SHBrowseForFolder(&bi);
	if (pidlBrowse != NULL) {
		if (SHGetPathFromIDList(pidlBrowse, (LPWSTR)lpBuffer))
			res = true;
	}
	return res;
}

int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance, LPSTR lpCmdLine, int nCmdShow) {

	
	//CreateMainWindow(hInstance);

	/*OPENFILENAME ofn = { 0 };
	char szDirect[260];
	char szFileName[260];
	ofn.lStructSize = sizeof(ofn);
	ofn.hwndOwner = NULL;
	ofn.lpstrFile =(LPWSTR) szDirect;
	*(ofn.lpstrFile) = 0;
	ofn.nMaxFile = sizeof(szDirect);
	ofn.lpstrFilter = NULL;
	ofn.nFilterIndex = 1;
	ofn.lpstrFileTitle = (LPWSTR)szFileName;
	*(ofn.lpstrFileTitle) = 0;
	ofn.nMaxFileTitle = sizeof(szFileName);
	ofn.lpstrInitialDir = NULL;
	ofn.Flags = OFN_EXPLORER;
	GetOpenFileName(&ofn);
	std::cout << szDirect;*/


		InitializeCriticalSection(&inputDataCS);
	InitializeCriticalSection(&fileCS);

	unsigned long threadsID = 0;
	string path = getPath() + "\\easy";
	checkOutputData.resize(1000, make_pair(-1, -1));

	openOutputStreamEvent = CreateEvent(NULL, true, false, L"Otput stream opened");
	HANDLE inputDataStream = CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)ThreadOfInputData, (void *)&path, 0, &threadsID);
	//Sleep(15);
	WaitForSingleObject(inputDataStream, INFINITE);
	HANDLE solvingProblemStream;
	HANDLE outputDataCheckStream;
	
	int q;
	string tempPath;
	char numstr[100];
	DWORD dwWaitResult;
	while (inputDataCheck.size() != 0) {
		EnterCriticalSection(&inputDataCS);
		q = inputDataCheck.front();
		inputDataCheck.pop();
		LeaveCriticalSection(&inputDataCS);

		tempPath = path + itoa(q, numstr, 10);
		solvingProblemStream = CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)ThreadOfSolvingProblem, (void *)&tempPath, 0, &threadsID);

		
		/*dwWaitResult = WaitForSingleObject(openOutputStreamEvent, 1);
		while (dwWaitResult != WAIT_OBJECT_0) {
			dwWaitResult = WaitForSingleObject(openOutputStreamEvent, 1);
		}*/
		WaitForSingleObject(openOutputStreamEvent, INFINITE);
		outputDataCheckStream = CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)ThreadOfCheckOutputData, (void *)&tempPath, 0, &threadsID);
		WaitForSingleObject(outputDataCheckStream, 10);
		//TODO: Сообщение об обработке данных одного файла или всех? Если одного, то сразу выводить на экран
	}

	CloseHandle(inputDataStream);
	
	return 0;
}



LRESULT CALLBACK WndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam) {

	switch (message) {
	case WM_CLOSE:
		PostQuitMessage(0);
		break;
	default:
		return DefWindowProc(hWnd, message, wParam, lParam);
	}
	return 0;

}

LRESULT CreateMainWindow(HINSTANCE hInstance) {
	MSG msg = { 0 };
	WNDCLASS wc = { 0 };
	wc.lpfnWndProc = WndProc;
	wc.hInstance = hInstance;
	wc.hbrBackground = (HBRUSH)(COLOR_BACKGROUND);
	wc.lpszClassName = L"minwindowsapp";
	if (!RegisterClass(&wc))
		return 1;

	if (!CreateWindow(wc.lpszClassName,
		L"Minimal Windows Application",
		WS_OVERLAPPEDWINDOW | WS_VISIBLE,
		0, 0, 640, 480, 0, 0, hInstance, NULL))
		return 2;

	while (GetMessage(&msg, NULL, 0, 0) > 0)
		DispatchMessage(&msg);

	return 0;
}

string getPath() {
	string path = "C:\\Users\\Vlada\\Documents\\Visual Studio 2017\\Projects\\OS4\\Tests";
	return path;
}

//_______________________________
void easyTrading(string path) {
	vector<double> prices(10000, 0);
	vector<int> daysDescriptions(10000, 0);
	int n = 0, m = 0, k = 0;
	EnterCriticalSection(&fileCS);
	ifstream fin(path + ".in");

	fin >> m;
	fin >> n;
	fin >> k;
	LeaveCriticalSection(&fileCS);

	prices.resize(k + 1, 0);
	daysDescriptions.resize(k + 1, -1);

	prices[0] = -1;
	for (int i = 1; i < k + 1; ++i) {
		EnterCriticalSection(&fileCS);
		fin >> prices[i];
		LeaveCriticalSection(&fileCS);
	}
	EnterCriticalSection(&fileCS);
	fin.close();
	LeaveCriticalSection(&fileCS);

	for (int i = 0; i < k; ++i) {
		daysDescriptions[i] = getDescriptionOfDay(i, n, m, daysDescriptions, prices);
	}

	string str = "";
	ofstream fout(path + ".out", ios::trunc);
	if (!fout) {
		MessageBox(NULL, L"OUTPUT STREAM", L"ERROR!", MB_ICONEXCLAMATION | MB_OK);
	}
	
	fout << "Hello";

	SetEvent(openOutputStreamEvent);
	Sleep(5);
	ResetEvent(openOutputStreamEvent);

	for (int i = 1; i < k; ++i) {
		if (daysDescriptions[i] == BULL_DAY) {
			//EnterCriticalSection(&fileCS);
			fout << "BUY ON DAY " << i << endl;
			//LeaveCriticalSection(&fileCS);
			while (daysDescriptions[++i] != BEAR_DAY) {}
			//EnterCriticalSection(&fileCS);
			fout << "SELL ON DAY " << i << endl;
			//LeaveCriticalSection(&fileCS);
			prices.clear();
			/*MessageBox(NULL, (LPCWSTR)str.c_str(), L"Error!",
				MB_ICONEXCLAMATION | MB_OK);*/
			return;
		}
	}
	return;
}

int getDescriptionOfDay(int i, int n, int m, vector<int> daysDescriptions, vector<double> prices) {
	if (i < n) {
		return -2;
	}

	if (average(i, n, prices) < average(i, m, prices) && (i == n || daysDescriptions[i - 1] == BEAR_DAY)) {
		return BULL_DAY;
	}

	if (average(i, n, prices) > average(i, m, prices) && (i == n || daysDescriptions[i - 1] == BULL_DAY)) {
		return BEAR_DAY;
	}
	return -2;
}

double average(int day, int count, vector<double> prices) {
	double tempPrice = 0;
	for (int i = 0; i < count; ++i) {
		tempPrice += prices[day - i];
	}
	return tempPrice / count;
}
//_______________________________


void ThreadOfInputData(void* params) {
	string path = *(string*)params;
	ifstream fin;

	int i = 1;
	int check;
	char numstr[100];
	string pathString;
	while (true) {
		pathString = path + itoa(i++, numstr, 10) + ".in";
		fin.open(pathString.c_str());

		if (fin.is_open()) {
			check = checkData(fin);
			if (check == 1) {
				//checkOutputData[i - 1] = make_pair(1, 0);
				EnterCriticalSection(&inputDataCS);
				inputDataCheck.push(i - 1);
				LeaveCriticalSection(&inputDataCS);
				//SetEvent(correctDataEvent);
				//Sleep(2);
			}
			else {
				//checkOutputData[i - 1] = make_pair(0, 0);
			}
			fin.close();			
		}
		else {
			fin.close();			 
			return;
		}
	}
}

int checkData(ifstream& fin) {
	int n, m, k;

	fin >> m;
	fin >> n;
	fin >> k;
	fin.ignore();

	if (m > n || n > 100 || m > 100 || m < 0 || n < 0) {
		fin.close();
		return -1;
	}

	if (n > k || m > k || k > 10000) {
		fin.close();
		return -1;
	}

	string tempString;
	double tempDouble = 0;
	for (int i = 0; i < k; i++) {
		getline(fin, tempString);
		if (atof(&tempString[0]) == 0.0) {
			fin.close();
			return -1;
		}
	}

	if (getline(fin, tempString)) {
		fin.close();
		return -1;
	}

	fin.close();
	return 1;
}


void ThreadOfSolvingProblem(void* params) {
	string path = *(string*)params;
	easyTrading(path);
}


void ThreadOfCheckOutputData(void* params) {
	string path = *(string*)params;
	int n = path[path.size() - 1] - 30;
	EnterCriticalSection(&fileCS);
	ifstream fin(path + "_reference.out");
	LeaveCriticalSection(&fileCS);

	string referenceData = "", fileData = "";
	string tempData = "";
	EnterCriticalSection(&fileCS);
	while (fin >> tempData) {
		referenceData += tempData;
	}
	fin.close();
	LeaveCriticalSection(&fileCS);

	EnterCriticalSection(&fileCS);
	fin.open(path + ".out");
	while (fin >> tempData) {
		fileData += tempData;
	}
	fin.close();
	LeaveCriticalSection(&fileCS);

	if (fileData.compare(referenceData)) {
		checkOutputData[n].second = 1;
	}
}