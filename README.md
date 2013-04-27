myOrm
=====

Requirements

1. Java 1.6 
2. Maven2 for building and running
3. MySQL server (tested on 5.1) 

Database Setup

1. Create a database under mysql named as 'notifier' .

	CREATE DATABASE ORM;
	
2. Now Create a table named as employee.

CREATE  TABLE `notifier`.`employee` (
  `idemployee` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NULL ,
  `designation` VARCHAR(45) NULL ,
  PRIMARY KEY (`idemployee`) );

Application Setup

1. find the db.properties under src/main/resource and make proper entry for database.
2. Now Execution, Since its a maven project we ll execute it by maven commands ( I am using maven2)
3. Go to project directory execute, one by one
	 > mvn clean
	 > mvn compile ( Compiles project )
	 > mvn exec:java -Dexec.mainClass="com.akhi.orm.common.Main" (Runs the main class)
4. For importing into eclipse
	> mvn eclipse:clean
	> Then import a maven project in eclipse (Assuming you have maven plgin in eclipse) 

	 


