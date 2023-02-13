@echo off

rem LICENSE.txt
rem
rem Copyright (c) 2012-2022 Rafael Corchuelo.
rem
rem In keeping with the traditional purpose of furthering education and research, it is
rem the policy of the copyright owner to permit non-commercial use and redistribution of
rem this software. It has been tested carefully, but it is not guaranteed for any particular
rem purposes.  The copyright owner does not offer any warranties or representations, nor do
rem they accept any liabilities with respect to them.

if "%~1" == "" goto usage

set "PROJECT=%~1"
set "METADATA=.\.metadata\.plugins\org.eclipse.debug.core\.launches"

if not exist ".\Projects\%PROJECT%" (
	if not exist ".\Starters\%PROJECT%" (
		set "MISSING_ITEM=%PROJECT%"
		goto :error
	)
)

if not exist "%METADATA%" (
	set "MISSING_ITEM=%METADATA%"
	goto :error
)

rem echo %0 "%PROJECT%"

echo Writing "%PROJECT% (runner)" launcher
set "FILE=%METADATA%\%PROJECT% (runner).launch"
set "OPTIONS=--worker runner"
call :write_launcher

echo Writing "%PROJECT% (populator#initial)" launcher
set "FILE=%METADATA%\%PROJECT% (populator#initial).launch"
set "OPTIONS=--worker populator#initial"
call :write_launcher

echo Writing "%PROJECT% (populator#sample)" launcher
set "FILE=%METADATA%\%PROJECT% (populator#sample).launch"
set "OPTIONS=--worker populator#sample"
call :write_launcher

echo Writing "%PROJECT% (inquirer)" launcher
set "FILE=%METADATA%\%PROJECT% (inquirer).launch"
set "OPTIONS=--worker inquirer"
call :write_launcher

echo Writing "%PROJECT% (tester)" launcher
set "FILE=%METADATA%\%PROJECT% (tester).launch"
set "OPTIONS=--worker tester"
call :write_tester

goto :end

:write_launcher
	rem echo write_launcher "%PROJECT%" "%FILE%" "%OPTIONS%"
	(
		echo ^<?xml version="1.0" encoding="UTF-8" standalone="no"?^> 
		echo ^<launchConfiguration type="org.eclipse.jdt.launching.localJavaApplication"^> 
		echo ^ ^ ^ ^ ^<listAttribute key="org.eclipse.debug.core.MAPPED_RESOURCE_PATHS"^> 
		echo ^ ^ ^ ^ ^ ^ ^ ^ ^<listEntry value="/%PROJECT%/src/main/java/acme/Launcher.java"/^> 
		echo ^ ^ ^ ^ ^</listAttribute^> 
		echo ^ ^ ^ ^ ^<listAttribute key="org.eclipse.debug.core.MAPPED_RESOURCE_TYPES"^> 
		echo ^ ^ ^ ^ ^ ^ ^ ^ ^<listEntry value="1"/^> 
		echo ^ ^ ^ ^ ^</listAttribute^> 
		echo ^ ^ ^ ^ ^<listAttribute key="org.eclipse.debug.ui.favoriteGroups"^> 
		echo ^ ^ ^ ^ ^ ^ ^ ^ ^<listEntry value="org.eclipse.debug.ui.launchGroup.debug"/^> 
		echo ^ ^ ^ ^ ^ ^ ^ ^ ^<listEntry value="org.eclipse.debug.ui.launchGroup.run"/^> 
		echo ^ ^ ^ ^ ^</listAttribute^> 
		echo ^ ^ ^ ^ ^<booleanAttribute key="org.eclipse.jdt.launching.ATTR_USE_START_ON_FIRST_THREAD" value="true"/^> 
		echo ^ ^ ^ ^ ^<stringAttribute key="org.eclipse.jdt.launching.MAIN_TYPE" value="acme.Launcher"/^> 
		echo ^ ^ ^ ^ ^<stringAttribute key="org.eclipse.jdt.launching.PROGRAM_ARGUMENTS" value="--configuration development %OPTIONS%"/^> 
		echo ^ ^ ^ ^ ^<stringAttribute key="org.eclipse.jdt.launching.PROJECT_ATTR" value="%PROJECT%"/^> 
		echo ^ ^ ^ ^ ^<stringAttribute key="org.eclipse.jdt.launching.VM_ARGUMENTS" value="-ea -Xverify:none"/^> 
		echo ^</launchConfiguration^> 
	) > "%FILE%"

	goto :eof

:write_tester
	rem echo write_tester "%PROJECT%" "%FILE%" "%OPTIONS%"
	(
		echo ^<?xml version="1.0" encoding="UTF-8" standalone="no"?^>
		echo ^<launchConfiguration type="org.eclipse.jdt.junit.launchconfig"^>
		echo ^ ^ ^ ^ ^<listAttribute key="org.eclipse.debug.core.MAPPED_RESOURCE_PATHS"^>
		echo ^ ^ ^ ^ ^ ^ ^ ^ ^<listEntry value="/%PROJECT%/src/test/java"/^>
		echo ^ ^ ^ ^ ^</listAttribute^>
		echo ^ ^ ^ ^ ^<listAttribute key="org.eclipse.debug.core.MAPPED_RESOURCE_TYPES"^>
		echo ^ ^ ^ ^ ^ ^ ^ ^ ^<listEntry value="2"/^>
		echo ^ ^ ^ ^ ^</listAttribute^>
		echo ^ ^ ^ ^ ^<listAttribute key="org.eclipse.debug.ui.favoriteGroups"^>
		echo ^ ^ ^ ^ ^ ^ ^ ^ ^<listEntry value="org.eclipse.debug.ui.launchGroup.debug"/^>
		echo ^ ^ ^ ^ ^ ^ ^ ^ ^<listEntry value="org.eclipse.debug.ui.launchGroup.run"/^>
		echo ^ ^ ^ ^ ^</listAttribute^>
		echo ^ ^ ^ ^ ^<stringAttribute key="org.eclipse.jdt.junit.CONTAINER" value="=%PROJECT%/src\/test\/java=/test=/true=/=/optional=/true=/=/maven.pomderived=/true=/"/^>
		echo ^ ^ ^ ^ ^<booleanAttribute key="org.eclipse.jdt.junit.KEEPRUNNING_ATTR" value="false"/^>
		echo ^ ^ ^ ^ ^<stringAttribute key="org.eclipse.jdt.junit.TESTNAME" value=""/^>
		echo ^ ^ ^ ^ ^<stringAttribute key="org.eclipse.jdt.junit.TEST_KIND" value="org.eclipse.jdt.junit.loader.junit5"/^>
		echo ^ ^ ^ ^ ^<stringAttribute key="org.eclipse.jdt.launching.CLASSPATH_PROVIDER" value="org.eclipse.m2e.launchconfig.classpathProvider"/^>
		echo ^ ^ ^ ^ ^<stringAttribute key="org.eclipse.jdt.launching.MAIN_TYPE" value=""/^>
		echo ^ ^ ^ ^ ^<stringAttribute key="org.eclipse.jdt.launching.PROJECT_ATTR" value="%PROJECT%"/^>
		echo ^ ^ ^ ^ ^<stringAttribute key="org.eclipse.jdt.launching.SOURCE_PATH_PROVIDER" value="org.eclipse.m2e.launchconfig.sourcepathProvider"/^>
		echo ^ ^ ^ ^ ^<stringAttribute key="org.eclipse.jdt.launching.VM_ARGUMENTS" value="-ea -Xverify:none"/^>
		echo ^</launchConfiguration^>
 	) > "%FILE%"

	goto :eof

:usage
	echo Usage: %0 project-name
	echo.
	echo This utility creates an Eclipse launcher for your project.  Restart Eclipse, import
	echo your project and you'll have new launch configurations to run or debug it.
	echo.
	goto :end

:error
	echo Error: coudn't find "%MISSING_ITEM%"
	echo.
	goto :end


:end
	pause