Stop-Process -Name "postgres";
(Get-ChildItem -Path $env:USERPROFILE\AppData\Local\Temp\).Fullname -match "postgresql-embed-*" | Remove-Item -Recurse;
