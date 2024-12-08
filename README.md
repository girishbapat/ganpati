# ganpati
Ganapati sales and inventory

create directory
on D:/temp
go to command prompt and execute below command
cd /d d:\temp
1. Make sure you dont have any application running on port 8090
with below command
netstat -ano | find "8090"
Assume it returns as below
  TCP    0.0.0.0:8090           0.0.0.0:0              LISTENING       2968
  TCP    [::]:8090              [::]:0                 LISTENING       2968
  TCP    [2001:0:348b:fb58:1898:1ffe:3f57:d488]:59027  [2001:0:348b:fb58:1898:1ffe:3f57:d488]:8090  TIME_WAIT       0
that means proces id is 2968
if you want to kill that process forcefully execute below command
taskkill /F /pid <process id>
in this case
taskkill /F /pid 2968
it will return as below
SUCCESS: The process with PID 2968 has been terminated.

if you re execute above command it will give error like below
ERROR: The process "2968" not found.

2. Make sure you dont have db files below d:\temp
like 
snehee.mv.db
or
snehee*.trace
if yes delete those

3. Now execute command from command prompt

cd /d d:\temp
java -version
It should return any version more than 17 like below:
openjdk version "17.0.10" 2024-01-16
OpenJDK Runtime Environment Temurin-17.0.10+7 (build 17.0.10+7)
OpenJDK 64-Bit Server VM Temurin-17.0.10+7 (build 17.0.10+7, mixed mode, sharing)

If it does not then you need to install JRE 17+

4. Now execute actual application with below command from same command prompt
cd /d d:\temp
java -jar ganpati.jar

Wait for couple of minutes till it logs some messages and if everything goes well it should show messages on command prompt similar to below
1. tomcat port 8090
2. context path '/ganpati-application' and'
3. Started GanpatiApplication in xx.yyy seconds

Sample output
2024-12-08T19:10:59.684+05:30 INFO 36924 --- [ganpati-application] [  restartedMain]
o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8090 (http) with context path '/ganpati-application'
2024-12-08T19:10:59.699+05:30 INFO 36924 --- [ganpati-application] [  restartedMain]
com.snehee.ganpati.GanpatiApplication    : Started GanpatiApplication in 7.162 seconds (process running for 7.941)

If you get above messages your application started successfully
Now check on d:\temp you will find database file crated as:
snehee.mv.db

5. if you want to stop application type CTRL+c to stop application. For any reason it does not stop or it hangs etc. you can forcefully stop application by following step 1.

6. To access application, open Chrome or your favorite browser and open below url:
http://localhost:8090/ganpati-application/swagger-ui/index.html

