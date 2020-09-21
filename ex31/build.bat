REM  Compile and deploy
REM
SET tomcat.home=C:\tomcat
SET classpath=%tomcat.home%\lib\annotations-api.jar;%tomcat.home%\lib\servlet-api.jar
SET deploy.home=WebContent\WEB-INF\classes
SET source.home=src
javac -deprecation -d %deploy.home% %source.home%\rain\*.java
