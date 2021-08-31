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
* *
