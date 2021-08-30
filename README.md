# Java labs at ITMO University #

## Laboratory work #1

Write a Java program that performs the appropriate action options. The program must meet the following requirements:

1. It should be packaged into an executable jar archive.
2. The expression must be evaluated in accordance with the calculation of mathematical expressions (the order of actions must be observed, etc.).
3. The program must use math functions from the Java standard library.
4. The result of evaluating the expression to be output to standard output must be output in the specified format.

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

1. health points (HP)
2. attack
3. defence
4. special attack
5. special defence
6. speed

Pokémon classes must be inherited according to the Pokémon evolutionary chain. Based on the base classes PhysicalMove, SpecialMove and StatusMove, implement their own classes for the specified types of attacks. 
The attack must be of standard type, power and accuracy. Standard attack effects must be implemented. Assign attacks to each type of Pokémon in accordance with the option. The Pokémon level is chosen as the minimum required for all implemented attacks.
Using the Battle simulation class, create 2 Pokémon teams (each Pokémon must have a name) and start the battle.