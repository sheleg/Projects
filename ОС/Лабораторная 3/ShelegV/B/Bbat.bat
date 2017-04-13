@echo off
chcp 1251>nul
set B_COMP_PATH=\\10.150.6.2\Common_stud\ShelegV\B
set path1=%B_COMP_PATH%\old
set path2=%B_COMP_PATH%\new
set path3=%B_COMP_PATH%\newest
set last_path=%B_COMP_PATH%\new\last_verion
set prelast_path=%B_COMP_PATH%\old\prelast_version

call :check_or_create_folders
call :initialize_version_control
call :make_copies
goto :eof


:check_or_create_folders
	if not exist %path1% (md %path1%)
	if not exist %path1%\C\"My Documents" (md %path1%\C\"My Documents")
	if not exist %path1%\E\"Subo" (
		md %path1%\E\"Subo"\"Data"
		md %path1%\E\"Subo"\"Archive"
	)
	if not exist %path1%\E\"Subo"\"Data" (md %path1%\E\"Subo"\"Data")
	if not exist %path1%\E\"Subo"\"Archive" (md %path1%\E\"Subo"\"Archive")	
	if not exist %path1%\E\"Zao" (md %path1%\E\"Zao")

	if not exist %path2% (md %path2%)
	if not exist %path2%\C\"My Documents" (md %path2%\C\"My Documents")
	if not exist %path2%\D (md %path2%\D)

	if not exist %path3% (md %path3%)
	if not exist %path3%\C\"My Documents" (md %path3%\C\"My Documents")
exit /b

:initialize_version_control
	if exist %last_path% (
		if exist %prelast_path% (
			del %prelast_path% /s /q >nul
			xcopy %last_path% %prelast_path% /e /q /i /c /r >nul
			del %last_path% /s /q >nul
		)
		
		if not exist %prelast_path% (
			md %prelast_path%
			xcopy %last_path% %prelast_path% /e /q /i /c /r >nul
			del %last_path% /s /q >nul
		)
	)
	
	if not exist %last_path% (
		md %last_path%
	)
exit /b

:make_copies
	call :make_copy %path1%\C\"My Documents" %last_path%\old\C\"My Documents" 1
	call :make_copy %path1%\E\"Subo"\"Data" %last_path%\old\E\"Subo"\"Data" 0
	call :make_copy %path1%\E\"Subo"\"Archive" %last_path%\old\C\E\"Subo"\"Archive" 0
	call :make_copy %path1%\E\"Zao" %last_path%\old\C\E\"Zao" 1
	call :make_copy %path2%\C\"My Documents" %last_path%\new\C\"My Documents" 0
	call :make_copy %path2%\D %last_path%\new\D 0
	call :make_copy %path3%\C\"My Documents" %last_path%\newest\C\"My Documents" 1
exit /b

:make_copy	 
	set c_from=%1
	set c_to=%2
	set with_subdirectories=%3
	if %with_subdirectories%==1 (
		xcopy %c_from% %c_to% /e /q /i /c /r >nul
	) else if %with_subdirectories%==0 (
		xcopy %c_from% %c_to% /q /i /c /r >nul
	)
exit /b