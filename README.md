# Java labs at ITMO University #

## Laboratory work #1, variant 312923

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
