# Java labs at ITMO University #

## Laboratory work #1

Write a Java program that performs the appropriate action options. The program must meet the following requirements:
* It should be packaged into an executable jar archive.
* The expression must be evaluated in accordance with the calculation of mathematical expressions (the order of actions must be observed, etc.).
* The program must use math functions from the Java standard library.
* The result of evaluating the expression to be output to standard output must be output in the specified format.

The program must be executed on the helios server.

Task:
1. Create a one-dimensional array m of type int. Fill it with odd numbers from 7 to 19 inclusive in ascending order.
2. Create a one-dimensional float array x. Fill it with 19 random numbers ranging from -11.0 to 12.0.
3. Create a 7x19 2D array u. Calculate its elements using the following formula (where x = x [j]):
    - if m[i] = 13, then u[i][j]=tan(ln(cos^2(x)));
    - if m[i] ∈ {7, 9, 11}, then u[i][j]=(((1/2⋅(1−x))^x−12)/0.25)^3;
    - for other values m[i]: u[i][j]=arcsin(sin(ln(4⋅|x|/5))).
4. Print the resulting array in the format with two decimal places.

## Laboratory work #2

Based on the base Pokemon class, write your own classes for the specified types of Pokemon. Each type of Pokémon must have one or two types and standard basic characteristics:
* health points (HP)
* attack
* defence
* special attack
* special defence
* speed

Pokémon classes must be inherited according to the Pokémon evolutionary chain. Based on the base classes PhysicalMove, SpecialMove and StatusMove, implement their own classes for the specified types of attacks. 
The attack must be of standard type, power and accuracy. Standard attack effects must be implemented. Assign attacks to each type of Pokémon in accordance with the option. The Pokémon level is chosen as the minimum required for all implemented attacks.

Using the Battle simulation class, create 2 Pokémon teams (each Pokémon must have a name) and start the battle.

The base classes and the battle simulator are in the jar-archive (Pokemon.jar). Documentation in javadoc format is here (https://se.ifmo.ru/~tony/doc/).

Pokémon:
![image](https://user-images.githubusercontent.com/75897943/131360592-519851b7-3400-4ada-8fec-157168f557f4.png)

## Laboratory work #3

Write a program that implements the object model according to the following description of the domain.

Description of the subject area:

The faint strokes of the oars were heard closer and closer. A small bell rang. Moomin-mom rushed to the ramp. She saw how the Moomin-troll dropped the oar into the water, turning the boat around. In confusion, he tried to row the second oar, but the boat circled in one place. In the stern sat a small, thin Hemuliha with a kind face and shouting something, but what exactly - no one could understand.

The program must meet the following requirements:
* The modified model must comply with the SOLID principles.
* The program must contain at least two interfaces and one abstract class.
* The developed classes must override the equals (), toString () and hashCode () methods.
* The program must contain at least one enumerated type (enum).

## Laboratory work #4

Refine the program from lab # 3 by updating the object model implementation in accordance with the new version of the domain description.

Description of the subject area:

The performance became more and more fun. Little by little, the entire audience moved onto the stage and took over the plot in the play, eating their own admission, which was placed on the dining room table in the living room.
Moomin-mom, freed from the skirts that burdened her, ran back and forth, delivering cups of coffee.
The orchestra began to play the Hemul march.
Moomin-dad was beaming, rejoicing at the great success, and Misa was as happy as at the dress rehearsal.
Suddenly Moomin-mom froze in the middle of the stage and dropped a cup of coffee on the floor.
And everything was silent around.
The faint strokes of the oars were heard closer and closer. A small bell rang. Moomin-mom rushed to the ramp. She saw how the Moomin-troll dropped the oar into the water, turning the boat around. In confusion, he tried to row the second oar, but the boat circled in one place. In the stern sat a small, thin Hemuliha with a kind face and shouting something, but what exactly - no one could understand.
Moomin-mom did not know what her son had done, but she had no doubt that she would approve of his act.
The audience was a little surprised at first, but then they realized that the play was going on. Leaving the coffee cups, they sat down on the ramp to watch the play continue.
The audience applauded.
Hemul turned around and glared at Snusmumrik.
Hemul did not say a word. He slowly approached, spreading his huge paws wide to grab Snusmumrik by the hair. Here he is getting closer and closer, here he rushes forward, and then ...

The program must meet the following requirements:
* The program must implement 2 of its own exception classes (checked and unchecked), as well as the exception handling of these classes.
* The use of local, anonymous and nested classes (static and non-static) must be added to the program.

## Laboratory work #5

Implement a console application that implements interactive management of a collection of objects. The collection must store objects of the Movie class, which is described below.

The developed program must meet the following requirements:
* A class whose collection of instances is managed by a program must implement default sorting.
* All requirements for class fields (specified in the form of comments) must be met.
* For storage, you must use a collection of type java.util.Stack.
* When the application starts, the collection should be automatically filled with values from the file.
* The filename must be passed to the program using command line argument.
* Data must be stored in a file in csv format.
* Reading data from a file must be implemented using the java.io.BufferedInputStream class.
* Writing data to a file must be implemented using the java.io.BufferedOutputStream class.
* All classes in the program must be documented in javadoc format.
* The program must work correctly with incorrect data (user input errors, lack of access rights to the file, etc.).

In interactive mode, the program must support the execution of the following commands:
* *help*: display help for available commands
* *info*: print information about the collection (type, date of initialization, number of elements, etc.) to the standard output stream
* *show*: print to standard output all elements of the collection in string representation
* *add {element}*: add new item to collection
* *update id {element}*: update the value of the collection element whose id is equal to the given
* *remove_by_id id*: remove an item from the collection by its id
* *clear*: clear collection
* *save*: save collection to file
* *execute_script file_name*: read and execute the script from the specified file. The script contains commands in the same form in which the user enters them interactively.
* *exit*: end the program (without saving to file)
* *remove_at index*: remove the element at the given position of the collection (index)
* *remove_first*: remove the first item from the collection
* *remove_greater {element}*: remove all items from the collection that are greater than the specified one
* *count_greater_than_mpaa_rating mpaaRating*: print the number of elements, the value of the mpaaRating field of which is greater than the given one
* *filter_starts_with_name name*: display elements whose name field value begins with a given substring
* *filter_less_than_oscars_count oscarsCount*: display elements whose oscarsCount field value is less than the specified one

Command input format:
* All command arguments that are standard data types (primitive types, wrapper classes, String, date storage classes) must be entered on the same line as the command name.
* All composite data types (class objects stored in a collection) must be entered one field per line.
* When entering composite data types, the user should be shown an input prompt containing the field name (for example, "Enter date of birth:")
* If the field is an enum, then the name of one of its constants is entered (in this case, the list of constants must be previously displayed).
* In case of incorrect user input (a string is entered that is not the name of a constant in the enum; a string is entered instead of a number; the entered number is not included in the specified limits, etc.) an error message should be displayed and the field should be prompted to re-enter the field.
* Use an empty string to enter null values.
* Fields with the comment "The value of this field should be generated automatically" should not be entered manually by the user when adding.

Description of the classes stored in the collection:
```java
public class Movie 
{
    private long id; // should be > 0, unique, generated automatically
    private String name; // can't be null or empty
    private Coordinates coordinates; // can't be null
    private java.util.Date creationDate; // can't be null, should be generated automatically
    private Long oscarsCount; // must be > 0, can't be null
    private int goldenPalmCount; // must be > 0
    private MovieGenre genre; // can't be null
    private MpaaRating mpaaRating; // can't be null
    private Person screenwriter;
}
public class Coordinates 
{
    private Long x; // can't be null, max value: 414
    private Float y; // can't be null, max value: 211
}
public class Person 
{
    private String name; // can't be null or empty
    private int height; // value should be > 0
    private long weight; // value should be > 0
}
public enum MovieGenre 
{
    WESTERN,
    COMEDY,
    MUSICAL,
    ADVENTURE,
    THRILLER;
}
public enum MpaaRating 
{
    G,
    PG,
    PG_13,
    R;
}
```

## Laboratory work #6

Divide the program from laboratory work #5 into client and server modules. The server module must execute commands for managing the collection. The client module must interactively read commands, send them for execution to the server, and output the results of execution.

The following requirements must be met:
* Collection object processing operations must be implemented using the Stream API using lambda expressions.
* Objects between client and server must be serialized.
* Objects in the collection passed to the client should be sorted by default.
* The client must correctly handle temporary server unavailability.
* The exchange of data between the client and the server must be carried out using the TCP protocol.
* To exchange data on the server, you must use a network channel.
* To exchange data on the client, you must use I / O streams.
* Network links must be used in non-blocking mode.

Responsibilities of the server application:
* Working with a file that stores a collection.
* Managing a collection of objects.
* Assigning automatically generated fields to objects in a collection.
* Waiting for connections and requests from the client.
* Processing received requests (commands).
* Saving the collection to a file when the application exits.
* Saving a collection to a file when executing a special command available only to the server (the client cannot send such a command).

The server application should consist of the following modules (implemented as one or more classes):
* The module for accepting connections.
* Request reader.
* The module for processing received commands.
* Module for sending responses to the client.

The server must be running in single threaded mode.

Responsibilities of the client application:
* Reading commands from the console.
* Validation of input data.
* Serialization of the entered command and its arguments.
* Sending the received command and its arguments to the server.
* Processing a response from the server (outputting the result of command execution to the console).
* The save command must be removed from the client application.
* The exit command terminates the client application.

## Laboratory work #7

Modify the program from laboratory work #6 as follows:
* Organize collection storage in a relational DBMS (PostgreSQL). Remove storage of the collection in a file.
* Use the database facility (sequence) to generate the id field.
* Update the state of the collection in memory only when the object is successfully added to the database
* All data retrieval commands must work with the collection in memory, not in the database
* Organize the possibility of registration and authorization of users. The user has the option to specify a password.
* Store passwords hashed using the SHA-384 algorithm
* Prevent unauthorized users from executing commands.
* When storing objects, store information about the user who created this object.
* Users should be able to view all objects in the collection, but only those that belong to them can be modified.
* To identify the user, send a username and password with each request.

It is necessary to implement multithreaded request processing:
* For multi-threaded reading of requests, use the creation of a new thread (java.lang.Thread)
* For multi-threaded processing of the received request, use the creation of a new thread (java.lang.Thread)
* For multithreading sending a response, use creating a new thread (java.lang.Thread)
* To synchronize access to collection use read / write sync with java.util.concurrent.locks.ReentrantLock
