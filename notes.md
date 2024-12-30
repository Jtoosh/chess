# Computer Science 240: Advanced Software Construction

## Pre-Class studying

### Java Basics

I worked on the Java W3 Schools exercises during the break, heres some bits I learned:

- Java is more syntax strict, like C++. Strings need to be in "", '' is for characters. Semi-colons after every line, case-sensitive, etc.
- `println` adds a newline char after the string, `print` does not.
- Comments are `//` for single line, `/* */` for multi-line.
- Variables must be typed, but don't need to be declared with a var keyword like JS.
- Multiple variables can be assigned in one line, like `int x = 5, y = 6, z = 50;`. They can also all be assigned the same value, with assignment operators in between each variable.
- Type casting is done with `()` around the type, like `(int) myDouble;`. When moving from a smaller primitive type to a larger one, it is called a Widening Cast, and is done automatically. When moving from a larger type to a smaller one, it is called a Narrowing Cast, and must be done manually.
- For review, the primitive types in Jave are: byte, short, int, long, float, double, char, boolean.
- Operators are similar/same as in C++.
- In Java, the length of a string is a method of the string (`.length()`), not a property or a separate method. Finding the index of the first occurrence of a char (or array element for that matter) is done with `.indexOf()`. When using `.indexOf()`, on a string, the char you are searching for is placed in "", not ''.
- Apart from the normal way with the `+` operator, Strings can be concatenated with the `.concat()` method.
- Escape characters are handled like normal, with a `\` before the character.

### Git Review

- `git branch` - without an argument lists the branches, with an argument creates a new branch with that name.
- `git checkout` - switches branches, can also be used to create a new branch with the `-b` flag.
- `git diff` - when passed with two versions, by providing the commit hashes, shows the differences between the two versions.
- Add the `-a` flag to `git commit` to commit all changes, without needing to add them first. `git commit -am "message"`. This is a shortcut for `git add .` and `git commit -m "message"`.

### Chapter 1 Reading: Fundamental Programming Structures

- Java has a few main reasons for its use: **Portability**, **Object-Orientation**, **Garbage Collection**, **Maturity**, **Rich Libraries**, and **Large Community**.
- Here is a note from the book about compiling and running a Java program: The `javac` compiler is invoked with the name of a file, with slashes separating the path segments, and an extension .java. The `java` virtual machine launcher is invoked with the name of a class, with dots separating the package segments, and no extension.
- Java's architecture of creating intermediate byte codes is a type of compromise between the greater speed but less portability of natively compiled machine code (binary), and the greater portability but slower speed of interpreted code.
- `System.out` is actually an instance of the `PrintStream` class, whose methods include, `print`, `println`, and others.
- Remember, instance methods operate on actual objects of a class, while static methods operate independent from any instances of a class. This good for utility methods, or for declaring a "singleton" which is allocated to a class, not an instance of a class.
- Most classes in Java need to be constructed, which is done using the `new` operator. The `new` operator creates a new object of a class, and returns a reference to that object. For example, `new Random();` constructs a new `Random` object. Some objects are ready to use without constructing them, like `System.out` or `String`.
- An alternative way to produce an instance of a class is called the *factory method*. An example is `RandomGenerator generator = RandomGenerator.getDefault();`. This is a static method that returns an instance of the class. (This is also the preferred way to make a random number generator, as the `Random` class is more of a legacy class).
- `jshell` is a read-evaluate-print-loop (REPL) tool that allows you to test Java code without needing to compile it. It is useful for testing small code snippets, and for learning Java. It is run by simply typing the command `jshell` in the terminal. It is exited by typing `/exit`.
- Java was designed to be a "write once, run anywhere" language. This is part of the reason why programs are compiled into byte codes that are run on the Java Virtual Machine (JVM). This allows Java programs to run on any machine that has a JVM installed.
- > Side note: Hexadecimal number literals have the prefix of `0x`, and binary literals have the prefix of `0b`. This is why memory addresses start is `0x`, because they are hexadecimal literals.
- Some special floating-point values are `Double.POSITIVE_INFINITY`, `Double.NEGATIVE_INFINITY`, and `Double.NaN`. **NOTE** that each `NaN` value is considered unequal to itself, so `Double.NaN == Double.NaN` will return `false`. To check if a value is `NaN`, use the `Double.isNaN()` method.
- Special `char` literals include `'\n'` for newline, `'\t'` for tab, `'\b'` for backspace, and `'\r'` for carriage return.
- Java is a strongly typed langauge. Each variable declared must have a type. Sometimes the type can be inferred by the compiler, so instead of a type, the keyword `var` can be used. For example, `var x = 5;` is the same as `int x = 5;`.
- `String` objects are immutable in Java. To mutate a string throughout a program, there is the `StringBuilder` class, which includes familiar methods like `.append()`. `StringBuilder` can easily be converted to a `String` with the `.toString()` method.
- Strings have plenty of useful methods, reference the reading or Java docs for more info. Some of the more useful ones include: `.length()`, `.charAt()`, `.substring()`, `startsWith()`, `split()`, `replace()`, and others.
- When using the string literal method for declaring a string, which is the `String = "hello"` method, the JRE stores the string in a special memory area called the "string constant pool". Java will internally check if the string already exists in the pool, and if it does, it will not create a new string, but instead return a reference to the existing string. By contrast, using the `new` operator will always create a new string object on the heap.
- Arrays can be instantiated as empty, with a size, or with values. For example, `int[] primes = new int[10];` creates an array of 10 integers, named `primes`, all initialized to 0. `int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};` creates an array of 10 integers, with the values specified. The second way of initializing an array uses what is called the "list initializer syntax", which uses the {} braces. Arrays are accessed using normal indexing.
