REM  Compile and deploy
REM
SET crs570.home=C:\crs570
SET classpath=%crs570.home%\lib\servlet-api.jar;%crs570.home%\lib\activation.jar;%crs570.home%\lib\javax.mail.jar
SET deploy.home=%crs570.home%\final_app\WebContent\WEB-INF\classes
SET source.home=%crs570.home%\final_app\src
rem javac -deprecation -d %deploy.home% %source.home%\rain\*.java --add-modules=java.activation,java.xml.ws.annotation
"C:\Program Files (x86)\ojdkbuild\java-1.8.0-openjdk-1.8.0.212-1\bin\javac" -cp %classpath% -deprecation -d %deploy.home% %source.home%\rain\*.java
