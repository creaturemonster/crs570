REM  Compile and deploy
REM
SET crs570.home=..
SET classpath=%crs570.home%\lib\activation.jar;%crs570.home%\lib\javax.mail.jar
SET deploy.home=WebContent\WEB-INF\classes
SET source.home=src
javac -deprecation -d %deploy.home% %source.home%\rain\*.java
