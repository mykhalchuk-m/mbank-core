@echo OFF
call build.cmd
@echo ------------------------------------------------------------------------
@echo STARTED DEPLOYMENT OF MBANK-CORE
@echo ------------------------------------------------------------------------
call mvn jboss:hard-deploy
echo Exit Code = %ERRORLEVEL%
if not "%ERRORLEVEL%" == "0" exit /b
@echo ------------------------------------------------------------------------
@echo FINISHED DEPLOYMENT OF MBANK-CORE
@echo ------------------------------------------------------------------------
@echo ON