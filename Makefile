all:
	javac -classpath "/usr/share/java/mysql-connector-java-8.0.15.jar:." FundoImobiliario.java

run:
	clear
	java -classpath "/usr/share/java/mysql-connector-java-8.0.15.jar:." FundoImobiliario

clean:
	rm -r *.class