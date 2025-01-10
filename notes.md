# Computer Science 240: Advanced Software Construction

## Pre-Class studying and Reading Notes

### Java Basics

I worked on the Java W3 Schools exercises during the break, heres some bits I learned:

- Java is more syntax strict, like C++. Strings need to be in "", '' is for characters. Semi-colons after every line, case-sensitive, etc.
- `println` adds a newline char after the string, `print` does not.
- Comments are `//` for single line, `/* */` for multi-line.
- Variables must be typed, but don't need to be declared with a var keyword like JS.
- Multiple variables can be assigned in one line, like `int x = 5, y = 6, z = 50;`. They can also all be assigned the same value, with assignment operators in between each variable.
- Type casting is done with `()` around the type, like `int myNum = (int) "0";`. When moving from a smaller primitive type to a larger one, it is called a Widening Cast, and is done automatically. When moving from a larger type to a smaller one, it is called a Narrowing Cast, and must be done manually.
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
- `System.out` is actually an instance of the `PrintStream` class, whose methods include, `print`, `println`, and others.
- Remember, instance methods operate on actual objects of a class, while static methods operate independent from any instances of a class. This good for utility methods, or for declaring a "singleton" which is allocated to a class, not an instance of a class.
- Most classes in Java need to be constructed, which is done using the `new` operator. The `new` operator creates a new object of a class, and returns a reference to that object. For example, `new Random();` constructs a new `Random` object. Some objects are ready to use without constructing them, like `System.out` or `String`.
- An alternative way to produce an instance of a class is called the *factory method*. An example is `RandomGenerator generator = RandomGenerator.getDefault();`. This is a static method that returns an instance of the class. (This is also the preferred way to make a random number generator, as the `Random` class is more of a legacy class).
- `jshell` is a read-evaluate-print-loop (REPL) tool that allows you to test Java code without needing to compile it. It is useful for testing small code snippets, and for learning Java. It is run by simply typing the command `jshell` in the terminal. It is exited by typing `/exit`.
- > Side note: Hexadecimal number literals have the prefix of `0x`, and binary literals have the prefix of `0b`. This is why memory addresses start is `0x`, because they are hexadecimal literals.
- Some special floating-point values are `Double.POSITIVE_INFINITY`, `Double.NEGATIVE_INFINITY`, and `Double.NaN`. **NOTE** that each `NaN` value is considered unequal to itself, so `Double.NaN == Double.NaN` will return `false`. To check if a value is `NaN`, use the `Double.isNaN()` method.
- Special `char` literals include `'\n'` for newline, `'\t'` for tab, `'\b'` for backspace, and `'\r'` for carriage return.
- Java is a strongly typed langauge. Each variable declared must have a type. Sometimes the type can be inferred by the compiler, so instead of a type, the keyword `var` can be used. For example, `var x = 5;` is the same as `int x = 5;`.
- `String` objects are immutable in Java. To mutate a string throughout a program, there is the `StringBuilder` class, which includes familiar methods like `.append()`. `StringBuilder` can easily be converted to a `String` with the `.toString()` method.
- Strings have plenty of useful methods, reference the reading or Java docs for more info. Some of the more useful ones include: `.length()`, `.charAt()`, `.substring()`, `startsWith()`, `split()`, `replace()`, and others.
- The static method `main`, which is the entry point for the program, takes in the `String[] args` parameter. This is an array of strings that are passed in as command line arguments. Accessing command line arguments is done by simply indexing the `args` array.
- Packages are a concept that are fairly unique to Java. A package is a way to encapsulate code. The name of a package should match the directory structure of a project. All of the code in the same package has access to each other, because all of that code is in the `package scope.` Code that is available outside of the package is in the `public scope`. The `public` keyword is used to declare a class or method as public. If a class is not declared as public, it is only available to other classes in the same package.
- The `import` keyword is used to import classes from other packages. This is done at the top of the file, before the class declaration
- When using 3rd party libraries, or utilizing code external to the current class, the `java` command needs to be given the classpath value, which is given with the `-cp` flag in the command. When specifying mulitiple classpaths in one command, separate each path with a `:` on Linux/Unix systems, and `,` on Windows systems.
- 2 critical classes, particularly for I/O, are the `File` class and `Scanner` class. Both are useful classes with methods that perform essential functions. A `Scanner` is an object that implements an interator, and has a method, `hasNext()` to return if a next object exists, and `.next()` to yield that next object.

Key things to understand:

1. *Ways Java differs from C++*
2. **Java installation**
3. **How Java is both portable and fast:** - Java's architecture of creating intermediate byte codes is a type of compromise between the greater speed but less portability of natively compiled machine code (binary), and the greater portability but slower speed of interpreted code. - Java was designed to be a "write once, run anywhere" language. This is part of the reason why programs are compiled into byte codes that are run on the Java Virtual Machine (JVM). This allows Java programs to run on any machine that has a JVM installed.
4. **How to compile and run Java code:** - Here is a note from the book about compiling and running a Java program: The `javac` compiler is invoked with the name of a file, with slashes separating the path segments, and an extension .java. The `java` virtual machine launcher is invoked with the name of a class, with dots separating the package segments, and no extension.
5. **The primitive data type available in Java:** byte, short, int, long (these 4 are the int types), float, double (these 2 are the decimal types), char, boolean.
6. **What's the difference in string literal String construction and construction with `new` operator:** - When using the string literal method for declaring a string, which is the `String = "hello"` method, the JRE stores the string in a special memory area called the "string constant pool". Java will internally check if the string already exists in the pool, and if it does, it will not create a new string, but instead return a reference to the existing string. By contrast, using the `new` operator will always create a new string object on the heap.
7. **How to declare, create, and initialize arrays:** - Arrays can be instantiated as empty with a size, or with values. For example, `int[] primes = new int[10];` creates an array of 10 integers, named `primes`, all initialized to 0. `int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};` creates an array of 10 integers, with the values specified. This second way of initializing an array uses what is called the "list initializer syntax", which uses the {} braces. Arrays are accessed using normal indexing.
8. **How to find the length of an array:** The Array class has a .length() method, that takes no parameters.
9. **How to create and access a nested array**:
10. **How to specify command-line arguments in IntelliJ**: Set a Run Configuration, and enter the arguments in the indicated field.
11. **The relationship between Packages, Imports and the CLASSPATH environment variable:**
12. **How to use a Scanner to read a text file of words, separated by whitespace:** Here's some sample code to show this:

```java
public class ScannerExample {
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length == 1) {
            var file = new File(args[0]);
            if (file.exists()) {
                var scanner = new Scanner(file);
                while (scanner.hasNext()) {
                    var text = scanner.next();
                    System.out.println(text);
                }
            }
        }
    }
}
```

### Chapter 2 Reading: Classes and Objects

- Classes are the main code construct for Java. In Java, the variables of a class are called the `fields` and the functions of a class are called the `methods`.
- One way of thinking about *Encapsulation* is only exposing code on a need-to-know basis. This is done by using private fields, and **getters and setters**. Common practice is to make all fields private, and then create public methods to access and return the field values, and modify the field values as needed. Sometimes this will seem redundant, but the value becomes apparent when the field values or program behaviors become more complex. Sometimes the getters and setters are as simple as this:

```java
public class Duck{
    private String name;
    private int age;
    private String color;

    public getName(){
        return name;
    }

    public setName(String name){
        this.name = name;
    }
}
```

- When creating my own classes, it is generally a good idea to *overwrite* a few of the built-in Java methods: `equals()`, `hashCode()`, and `toString()`. This is because the default implementations of these methods generally do not have the desired functionality. When overriding a built-in Java method, the method is prefixed with the `@Override` annotation on the line above. Overriding these methods is a good practice, because it ensures that hash codes are generated with all the fields being considered, and equality is being determined by all the fields as well. Overriding the `toString()` method is especially helpful for debugging.

**Things to Understand:**

- What object references are and why we need them
    > When a class is instantiated using the `new` operator, a pointer reference to the object is returned. This reference is stored in the variable that the develolper passes in. To reference an object inside of itself, the `this` keyword is used. Generally, when referencing an objects fields or methods, Java will assume that the reference is to the current object without the `this` keyword, unless there is a naming conflict.
- The differences between static methods and variables and instance methods and variables
    > Static methods and variables are associated with and operate on the class itself, not on a specific instance of the class. Instance methods and variables are associated with and operate on a specific instance of the class. Static methods and variables are declared with the `static` keyword, while instance methods and variables are not.
- How constructors work in Java
    > Constructors are generated by writing a method with the same name as the class name. Then, the constructor will be called whenever the class is instantiated with the `new` operator. Constructors can be overloaded, meaning that multiple constructors can be written with different parameters. The constructor that is called is determined by the parameters passed in when the class is instantiated. Another common use of constructors is to make a "copy constructor", which takes in an object of the same class and copies all of its fields to a new object.
- What constructor the compiler writes (and when it doesn't write one)
    > When no constructor is written, the compiler will write a default constructor that takes no parameters, and does nothing. If a constructor is written, the compiler will not write a default constructor.
- What code the compiler adds to some constructors and why
- What the 'this' reference is used for
- When the 'this' reference is required and when it is optional
    > Addressing the two preceeding bullet points, the `this` keyword is used to reference an object inside of itself. It is required when there is a naming conflict between a parameter and a field of the class. It is optional when there is no naming conflict, because Java can infer that the reference is to the current object.
- How to use the 'this' reference to call another constructor in the same class
    > The `this` keyword can be used to call another constructor in the same class. This is done by using the `this` keyword like a method call, typing `this()` and passing in the parameters for the corresponding constructor. This process, of calling another constructor in the same class, is called constructor chaining.
- What an enum is and how to implement one
    > An `enum` is a special type of class. It represents a group of constants, so the values are immutable. An `enum` is declared with the `enum` keyword, and the values are declared in a list, separated by commas. An example of an `enum` is:

    ```java
    enum Level{
        LOW,
        MEDIUM,
        HIGH
    }
    ```

    > The values of an `enum` can be accessed with dot notation. To access the values of the `Level` enum, you would use `Level.LOW`, `Level.MEDIUM`, and `Level.HIGH`. `enums` are often used with `switch` statements, and have a `values()` method that returns an array of the values of the `enum`. It is most useful to use an `enum` when you are working with a set of values that you know will remain constant.

- The standard order of elements in a Java class
    > Here is an answer provided by GitHub Copilot:
    In the context of the notes in the file, "The standard order of elements in a Java class" refers to the conventional sequence in which different parts of a Java class are typically organized. This order helps in maintaining readability and consistency across Java codebases. The standard order is generally as follows:
    >
    > 1. **Package declaration**: Specifies the package name.
    > 2. **Import statements**: Lists all the required imports.
    > 3. **Class or interface declaration**: Starts with the class or interface declaration.
    > 4. **Static variables (static fields)**: Declares static fields.
    > 5. **Instance variables (non-static fields)**: Declares instance variables.
    > 6. **Constructors**: Includes all constructors.
    > 7. **Methods**:
        - Static methods
        - Instance methods
        - Getter and setter methods
        - Other methods
    > 8. **Inner classes or interfaces**: Defines any inner classes or interfaces.
    >
    >
    > This order helps in locating different parts of a class quickly and understanding the structure of a Java class more easily.

### Java Records

In object-oriented programming, it is fairly common to create a class just for representing a collection of data. These instances of data field collections are referred to as `data objects`, and are fairly common in Java. Ideally, a data object should have the following characteristics:

- Is immutable
- Has getters for all fields
- Overrides the `equals`, `hashCode`, and `toString` methods to match the fields of the object

This can be done with a standard class, but it adds to the amount of boilerplate code that is needed. Because of how common data objects are, Java introduced the `record` keyword, that can create something functionally equivalent, but much, much more concise. To create a record, use the following syntax:`record Person(String name, int age) {}` Record that are created this way will meet the three characteristics mentioned above, as they are immutable, have getters for all fields, and override the `equals`, `hashCode`, and `toString` methods to factor in all of the fields of the record.

### Chapter 3 Reading: Interfaces

Polymorphism
: A blanket term in computer science for taking one object and morphing it to fit in to many contexts. In Java, this is accomplished primarily through *inheritance*, *interfaces*, and *abstract classes*.

The central idea around an `interface`, is being able to define a class, and what its methods do, without defining how they do it. It is defining functionality without defining implementation.

An interface defines methods that a class implements using a normal method signature, including the method's access scope, return type, name, and parameters. Curly braces and the implementation are not added. When a class implements, or uses, and interface, the class declaration uses the keyword `implements` such as `class MyClass implements MyInterface`. The class needs to declare and implement the methods defined in the interface. So, in the "implementing class" is where the functionality of the methods are actually defined. A class that implements an interface can implement other methods than the ones defined in the interface as well. If any of the methods defined in the interface are not implemented in the class, the class must be declared as `abstract`.

When writing the implementation for the methods defined in the interface, some programmers like to put the `@Override` annotation before the method in the implementing class. This is not technically necessary, it will function fine without, but it means to add a bit of clarity to the code.

An interface has no instantiations. Objects are only instances of classes, not interfaces. But, variables can be declared as an interface type. When this is done, that variable can have a value that is of any of the subtypes of that interface. This is a significant way to create polymorphism in Java. So, if a variable is declared as having the type `Collection` the variable can be assigned a value of any class that implements the `Collection` interface, such as `ArrayList`, `LinkedList`, or `HashSet`.

There are built-in JDK interfaces, that certain classes implement, and the developer can create new classes that implement built-in interfaces too. A developer can also write entirely new interfaces. This is done similar to writing a class, but with `interface` as the key word, instead of `class`. Here is a sample:

```java
public interface CharIterator {
    /** Returns true if there is another character to iterate. */
    boolean hasNext();

    /** Returns the next character. */
    char next();
}
```

### Chapter 4 Reading: Inheritance and Extending Classes

When a class "inherits" from another, the inheriting class is called the `subclass` and the class that gives things to be inherited is the `superclass`. A subclass is able to access and use code from a super class as if it was written in the subclass itself. In Java, the keyword for defining a class as a subclass of another is the `extends` keyword. An example:

```java
public static class Dog extends Pet{

}
```

In this example, the subclass, Dog, is able to access all of the code for the `Pet` class, and then define its own code. This perspective makes the `extends` keyword make sense, because the class `Dog` is taking all of the functionality of `Pet`, and then **extending** past that with whatever members are defined in `Dog`. **Note:** using the `extends` keyword is (to my current knowledge) the ***only*** way to make one class a subclass of another. (I don't know if some of the built-in JDK classes have pre-made inheritance relationships, I'll have to find that out)

`Abstract Classes` are a type of hybrid between a standard class and a hybrid. If an implementing class only implements some of the methods defined in the interface, or if it defines additional methods without providing their implementation, then that class is an `abstract` class. Both the class needs to have a signature of `abstract` and the methods that are left unimplemented. Then, similar to implementing classes of interfaces, a subclass of an abstract class is what will implement the abstract methods.

Similar to interfaces, any variable whose declared data type is that of an abstract class, can hold a value of any of its implementing subclasses.

`instanceof` is an operator in Java that returns `true` if the LHS is an instance of the data type/class passed on the RHS (including if the LHS `extends` or `implements` the class on the RHS), and `false` otherwise.

Lastly, the keyword `final` can be applied to method signatures or field declarations. If a method is declared as `final`, then it cannot be overridden by subclasses. If a field variable is declared as `final` then its value become immutable. `final` methods and fields can still be called and accessed, respectively, just not changed.

## Class Notes

