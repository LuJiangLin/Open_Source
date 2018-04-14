@echo off
title 获取本机局域网IP及MAC地址 Mr.薛
echo =================================
echo 获取本机局域网IP及MAC地址 Mr.薛
echo =================================
echo=
echo=
echo       ◢████████████◣　　　　　　　 
echo 　　　██████████████　　　　　　　 
echo 　　　██　　　◥██◤　　　██　　　　　　　 
echo 　◢███　　　　◥◤　　　　██◣　　　　　　 
echo 　▊▎██◣　　　　　　　　◢█▊▊　　　　　　 
echo 　▊▎██◤　　●　　●　　◥█▊▊　　　　　 
echo 　▊　██　　　　　　　　　　█▊▊　　　　　　 
echo 　◥▇██　▊　　　　　　▊　█▇◤　　　　　　 
echo 　　　██　◥▆▄▄▄▄▆◤　█▊　　　◢▇▇◣ 
echo ◢██◥◥▆▅▄▂▂▂▂▄▅▆███◣　▊◢　█ 
echo █╳　　　　　　　　　　　　　　　╳█　◥◤◢◤ 
echo ◥█◣　　　˙　　　　　˙　　　◢█◤　　◢◤　 
echo 　　▊　　　　　　　　　　　　　▊　　　　█　　 
echo 　　▊　　　　　　　　　　　　　▊　　　◢◤　　 
echo 　　▊　　　　　　⊕　　　　　　█▇▇▇◤　　 
echo 　◢█▇▆▆▆▅▅▅▅▆▆▆▇█◣　　　　　　 
echo 　▊　▂　▊　　　　　　▊　▂　
echo=
echo=


:::::获取本机局域网IP地址
ver|findstr "5.1" >nul && (
    set "m=ipconfig^|findstr /i "ip address""
)|| (
    set "m=ipconfig^|findstr /i "ipv4""
)
for /f "tokens=14* delims=: " %%1 in ('%m%')do echo 本机局域网IP地址是 %%2
:::::获取本机MAC地址
for /f "tokens=2 delims=:" %%a in ('ipconfig /all^|find "物理地址"') do (
echo 本机MAC物理地址是%%a
pause>nul
)
pause