@ECHO OFF
::
:: Licensed to the Apache Software Foundation (ASF) under one
:: or more contributor license agreements.  See the NOTICE file
:: distributed with this work for additional information
:: regarding copyright ownership.  The ASF licenses this file
:: to you under the Apache License, Version 2.0 (the
:: "License"); you may not use this file except in compliance
:: with the License.  You may obtain a copy of the License at
::
::   http://www.apache.org/licenses/LICENSE-2.0
::
:: Unless required by applicable law or agreed to in writing,
:: software distributed under the License is distributed on an
:: "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
:: KIND, either express or implied.  See the License for the
:: specific language governing permissions and limitations
:: under the License.
::
::
::   Runs the default clinical pipeline on files in the input directory specified by -i {directory}
::   Writes .xmi files to the output directory specified by --xmiOut {directory}
::   Uses UMLS credentials specified by --user {username} --pass {password}
::   Can also use a custom dictionary with -l {dictionaryConfigFile}
::
::
:: Requires JAVA JDK 1.8+
::

@REM The setenv script sets up the environment needed by cTAKES.
@call %~sdp0\setenv.bat

cd %CTAKES_HOME%

java -cp "%CLASS_PATH%" -Xms512M -Xmx3g %PIPE_RUNNER% -p %FAST_PIPER% %*

cd %CURRENT_DIR%

:end
