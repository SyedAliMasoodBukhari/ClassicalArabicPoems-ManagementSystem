@echo off

REM Set the installation directory for XAMPP
set XAMPP_INSTALL_DIR=C:\xampp
set CLASSICAL_ARABIC_POEMS_DIR=C:\Program Files (x86)\ClassicalArabicPoems

REM Check if the C:\xampp folder is not empty
dir "%XAMPP_INSTALL_DIR%" | find "0 File(s)" >nul && (
    REM If the folder is empty, display a message and exit
    echo The folder C:\xampp is empty. No need to uninstall.
    goto :EOF
) || (
    REM If the folder is not empty, proceed with the uninstallation

    REM Stop Apache and MySQL before uninstalling
    echo Stopping Apache and MySQL...
    "%XAMPP_INSTALL_DIR%\xampp_stop.exe"

    REM Wait for services to stop (adjust the sleep time as needed)
    timeout /nobreak /t 5

    REM Uninstall XAMPP
    echo Uninstalling XAMPP...
    "%XAMPP_INSTALL_DIR%\uninstall.exe"

    REM Wait for the uninstallation to complete
    timeout /nobreak /t 5

    REM Check if ClassicalArabicPoems folder exists before running uninstallation
    if exist "%CLASSICAL_ARABIC_POEMS_DIR%" (
        echo Uninstalling ClassicalArabicPoems...
        start /wait "%CLASSICAL_ARABIC_POEMS_DIR%\Uninstal.exe"
    ) else (
        echo ClassicalArabicPoems folder not found. Manually uninstall the Program.
    )

    REM Display completion message
    echo Uninstallation completed successfully.
)

REM Pause to keep the command prompt window open (remove if not needed)
pause
