#!/bin/bash
javac -cp GUI:lib/fontawesomefx-commons-9.1.2.jar:lib/UndecoratorBis.jar:lib/hibernate-commons-annotations-5.0.1.Final.jar:lib/annotations-java8.jar:lib/jfoenix-9.0.0.jar:lib/mysql.jar -d GUI $(find src -name "*.java")
java -cp GUI:lib/fontawesomefx-commons-9.1.2.jar:lib/UndecoratorBis.jar:lib/hibernate-commons-annotations-5.0.1.Final.jar:lib/annotations-java8.jar:lib/jfoenix-9.0.0.jar:lib/mysql.jar br.com.pokemon.gui.start.Main