# Address Book Project Readme

## Introduction

The main purpose of this project is to connect to a MySQL database and either list all records, update one record
or search for a value.

## How to run
1. Use the .sql script provided with the source code to create the scheme that will contain the "addr" table with all the necessary fields
2. Configure the username and password fields to match the username and password of your MySQL connection (default values are "username" for username and "password" for password).


###### By .jar
1. Download the .jar file located in the out/artifacts/addressbook_jar folder
2. Run it through command line interface in the following format:

	java -jar addressbook.jar parameters
	
	Parameters that can be used are following:
	- list
	- add name="value" email="value" phone="value"
	- search field="value"
	
	Examples of usage are provided below:
	- java -jar addressbook.jar list
	- java -jar addressbook.jar add name="Someone" phone="385981234567"
	- java -jar addressbook.jar search email="test@test.com"
	
3. Important rules regarding the parameters:
	- list parameter should be used as is, without any other parameters
	
	- add parameter takes up to three aditional parameters corresponding to the fields in the database
	- any non-existing parameters provided with the add parameter will result in rejection of the command
	- Email values MUST be presented in a correct format (something@page.com); any deviation from this format
	will result in rejection of method
	- Phone number MUST be a number and contain 12 digits corresponding to necessary codes (country code, city code, the number);
	any deviation from this format will result in rejection of method
	
	- search parameter takes only one additional parameter corresponding to any of the fields in the database
	- any non-existing parameters provided with the search parameter will result in rejection of the command
	
###### By Maven
1. Clone the repository
2. Run the command line interface and change directory to the project folder (addressbook)
3. The project needs to be built by using the following command: mvn package
4. Run the project with mvn exec:java -Dexec.args="list"
   Expected output should be similar to this example:
	![output example](https://imgur.com/oe39WZ3.png)
5. To run tests use:
	- mvn test to perform all of the tests
	- mvn test -Dtest=ArgumentCasesTest to perform tests regarding ArgumentCases class

## Notes

This project was developed using IntelliJ. Maven and JUnit were used for testing. The project was tested on Windows 10
with Java version 1.8.
