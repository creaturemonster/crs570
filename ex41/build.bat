REM  Compile and deploy
REM
SET crs570.home=..
SET classpath=%crs570.home%\lib\servlet-api.jar;%crs570.home%\lib\annotations-api.jar
SET deploy.home=WebContent\WEB-INF\classes
SET source.home=src
javac -deprecation -d %deploy.home% %source.home%\rain\*.java
