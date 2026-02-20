@echo off
chcp 65001 >nul
color 0A
title Quan Ly Du An va Nhan Vien - ABC GROUP

cls
echo ============================================
echo    HE THONG QUAN LY DU AN VA NHAN VIEN
echo              ABC GROUP
echo ============================================
echo.
echo [*] Dang compile du an...
echo.

javac -encoding UTF-8 -cp "lib\mssql-jdbc-12.10.0.jre11.jar;src" -d build src\BUS\*.java src\DAO\*.java src\DTO\*.java src\GUI\*.java

if %errorlevel% equ 0 (
    echo [V] Compile thanh cong!
    echo.
    echo [*] Khoi dong he thong...
    echo.
    timeout /t 2 /nobreak >nul
    cd build
    java -cp ".;..\lib\mssql-jdbc-12.10.0.jre11.jar" GUI.DangNhap
    cd ..
) else (
    echo [X] Compile that bai! 
    echo     Vui long kiem tra loi va thu lai.
    echo.
    pause
)
