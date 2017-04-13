@echo off

set A_COMP_PATH=\\10.150.6.2\Сommon_stud\ShelegV\A

call :check_or_create_connection H: H:\bank.test %A_COMP_PATH%\bank\e
goto end


:check_or_create_connection
	set disk_name=%1
	set semaphore_path=%2
	set network_path=%3
	:main
	if exist %disk_name%\ (
		if exist %semaphore_path% (
			EXIT /b
		)
	)
	net use /delete %disk_name%
	if NOT exist %semaphore_path% (
		net use "%disk_name%" %network_path%
		goto main
	)
EXIT /b

:end
pause
