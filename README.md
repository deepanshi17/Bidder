# Bidder Auction Program

Bidder is an online application that hosts an auction server and handles customer clients. Customers can access the server and bid on several items. As customers place bids, the server updates its database and notifies all the customers of the next highest bid. Items are marked sold to the highest bidder when the set price is reached or when time runs out. 

## For Users

These instructions will get you a copy of the project up and running on your local machine and explain the various features of the project. 

### Prerequisites

Downloading this project requires that Gson be available to your build path. Gson jar downloads are available from Maven Central.

To run the program, download the executable jar files and run them. If they don't run by double-clicking the files, try this command from the command line:

The easiest way to run the program is by using an integrated development environment, such as Eclipse. 

```
java -jar <executable_jar>
```

### Features

The server is capable of handling multiple customers using multiple threads. The client sees a start-up window which gives him the options to sign up or log in. The client is identified with a name, username, and password. Passwords are encrypted in the server database to ensure security. Customers can view their bid history, place bids on open auctions, and search for items by item ID using a search bar. Customers can also turn on their sound to enjoy the soothing music of George Gershwin while they bid. The server only accepts valid bids, and customers are informed via a pop-up window if their bid is invalid. Customers are also sent other various updates from the server, such as an invalid log in, an item being sold, or a processing error. Customers can use the log out button to exit the program gracefully. Additionally, each item has a set minimum price. There is a special feature that allows customers to immediately buy an item by clicking "Buy it Now". Items have unique descriptions that are visible to customers. Customers are able to see all their bids across previous sessions. The Observable interface is used to notify customers of changes in the server. 

![Start up screen](https://github.com/EE422C/final-project-sp-2020-deepanshi17/blob/master/StartUp.png?raw=true)

![Log in](https://github.com/EE422C/final-project-sp-2020-deepanshi17/blob/master/Login.png?raw=true)

## For Programmers

This program can be easily deployed and tested by starting the server and then adding multiple clients across different machines. The program is machine independent, and the clients and server communicate through the use of Sockets. 

### Testing

The most common program failure occurs when the server is unable to connect to the port. For example,

```
java.net.BindException: Address already in use (Bind failed)
```
Test the port connection using a command line with the command:

```
lsof -i :4242
```

This command will list all the processes running on the port. If there is some kind of error or failure, kill the processes using the command:

```
kill -9 [PID]
```
This will clean the port of any unwanted processes. Then run the server again. This will fix most outlying issues. 

### Coding Style

It is important to stick to good java coding style in order to avoid any syntactical issues. The source file name consists of the case-sensitive name of the top-level class it contains (of which there is exactly one), plus the .java extension. Source files are encoded in UTF-8. Aside from the line terminator sequence, the ASCII horizontal space character (0x20) is the only whitespace character that appears anywhere in a source file. This is especially pertinent if you are trying to edit the background music. Make sure the file name does not contain any whitespace or special characters. 

```
ex. RhapsodyInBlue.mp3
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management


## Version Control

The project was developed on GitHub.

## References

* [Cryptography](http://www.appsdeveloperblog.com/encrypt-user-password-example-java/)

* [Networking](https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html)

* [JavaFX](https://www.javaworld.com/article/3057072/exploring-javafxs-application-class.html)
