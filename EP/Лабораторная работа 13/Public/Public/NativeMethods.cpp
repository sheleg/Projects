#include <stdio.h>
#include "NativeMethods.h"
#include <Windows.h>

HKEY hKey;
DWORD dwDisposition;

JNIEXPORT void JNICALL Java_NativeMethods_openKey
  (JNIEnv *env, jclass, jstring jopenKey)
{
	const char* openKey = env->GetStringUTFChars(jopenKey, JNI_FALSE);
	if (RegOpenKeyEx(HKEY_CURRENT_USER, openKey, 0, KEY_ALL_ACCESS, &hKey)==ERROR_SUCCESS)
	{
		printf("\nThe subkey was successfully opened.\n");
	//	return true;
	}
	else
	{
		printf("\nError opening the desired subkey (permissions?).\n");
	//	return false;
	}
}

JNIEXPORT void JNICALL Java_NativeMethods_createKey
  (JNIEnv *env, jclass, jstring jcreateKey)
{
	bool flag;
	const char* createKey = env->GetStringUTFChars(jcreateKey, JNI_FALSE);
	RegCreateKeyEx(HKEY_CURRENT_USER, createKey, 0, NULL, 0, 0, NULL, &hKey, &dwDisposition);
	if (dwDisposition != REG_CREATED_NEW_KEY && dwDisposition != REG_OPENED_EXISTING_KEY)
	{
		printf("\nError creating the desired subkey (permissions?).\n");
	//	flag = false;
	}
	else
	{
		printf("\nThe subkey was successfully created.\n");
	//	flag = true;
	}
	RegCloseKey(hKey);
	//return flag;
}

JNIEXPORT void JNICALL Java_NativeMethods_closeKey
  (JNIEnv *, jclass)
{
	if(RegCloseKey(hKey)==ERROR_SUCCESS)
	{
		dwDisposition = 0;
		printf("\nThe subkey was successfully closed.\n");
	//	return true;
	}
	//return false;
}

JNIEXPORT void JNICALL Java_NativeMethods_deleteValue
  (JNIEnv *env, jclass, jstring deleteVal)
{
	const char* deleteValue = env->GetStringUTFChars(deleteVal, JNI_FALSE);
	if (RegDeleteValue(hKey, TEXT(deleteValue))==ERROR_SUCCESS)
		printf("\nString Value value successfully removed.\n");
	else 
		printf("\nError removing the specified value (permissions?).\n");
}

JNIEXPORT void JNICALL Java_NativeMethods_setValue
  (JNIEnv *env, jclass, jstring jplace, jstring jdata)
{
	const char* place = env->GetStringUTFChars(jplace, JNI_FALSE);
	const char* data = env->GetStringUTFChars(jdata, JNI_FALSE);
	if (RegSetValueEx(hKey, place, NULL, REG_SZ, reinterpret_cast<const byte*>(data), sizeof(data))==ERROR_SUCCESS)
		printf("\nThe value of the key was set successfully.\n");
	else
		printf("\nError setting the value of the key.\n");
}