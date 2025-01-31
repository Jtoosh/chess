# Computer Science 240: Advanced Software Construction

## Pre-Class studying

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
- Apart from the normal way with the `+` operator, Strings can be concatenated with the `.concat()` method. And because strings are immutable, concatenation does not alter any existing strings, but creates a new one.
- Escape characters are handled like normal, with a `\` before the character.

### Git Review

- `git branch` - without an argument lists the branches, with an argument creates a new branch with that name.
- `git checkout` - switches branches, can also be used to create a new branch with the `-b` flag.
- `git diff` - when passed with two versions, by providing the commit hashes, shows the differences between the two versions.
- Add the `-a` flag to `git commit` to commit all changes, without needing to add them first. `git commit -am "message"`. This is a shortcut for `git add .` and `git commit -m "message"`.

## Reading Notes from "Core Java for the Impatient" 3rd Edition

### Chapter 1 Reading: Fundamental Programming Structures

- Java has a few main reasons for its use: **Portability**, **Object-Orientation**, **Garbage Collection**, **Maturity**, **Rich Libraries**, and **Large Community**.
- Remember, instance methods operate on actual objects of a class, while static methods operate independent from any instances of a class. This good for utility methods, or for declaring a "singleton" which is allocated to a class, not an instance of a class.
- Most classes in Java need to be constructed, which is done using the `new` operator. The `new` operator creates a new object of a class, and returns a reference to that object. For example, `new Random();` constructs a new `Random` object. Some objects are ready to use without constructing them, like `System.out` or `String`.
- An alternative way to produce an instance of a class is called the _factory method_. An example is `RandomGenerator generator = RandomGenerator.getDefault();`. This is a static method that returns an instance of the class. (This is also the preferred way to make a random number generator, as the `Random` class is more of a legacy class).
- `jshell` is a read-evaluate-print-loop (REPL) tool that allows you to test Java code without needing to compile it. It is useful for testing small code snippets, and for learning Java. It is run by simply typing the command `jshell` in the terminal. It is exited by typing `/exit`.
- > Side note: Hexadecimal number literals have the prefix of `0x`, and binary literals have the prefix of `0b`. This is why memory addresses start is `0x`, because they are hexadecimal literals.
- Some special floating-point values are `Double.POSITIVE_INFINITY`, `Double.NEGATIVE_INFINITY`, and `Double.NaN`. **NOTE** that each `NaN` value is considered unequal to itself, so `Double.NaN == Double.NaN` will return `false`. To check if a value is `NaN`, use the `Double.isNaN()` method.
- Special `char` literals include `'\n'` for newline, `'\t'` for tab, `'\b'` for backspace, and `'\r'` for carriage return.
- Java is a strongly typed langauge. Each variable declared must have a type. Sometimes the type can be inferred by the compiler, so instead of a type, the keyword `var` can be used. For example, `var x = 5;` is the same as `int x = 5;`.

**Strings:**

- `String` objects are immutable in Java. To mutate a string throughout a program, there is the `StringBuilder` class, which includes familiar methods like `.append()`. `StringBuilder` can easily be converted to a `String` with the `.toString()` method.
- Because of string immutability, concatenating strings with the `+` operator can be really inefficient, so try to use `StringBuilder` when possible.
- Strings have plenty of useful methods, reference the reading or Java docs for more info. Some of the more useful ones include: `.length()`, `.charAt()`, `.substring()`, `startsWith()`, `split()`, `replace()`, and others.
- The static method `main`, which is the entry point for the program, takes in the `String[] args` parameter. This is an array of strings that are passed in as command line arguments. Accessing command line arguments is done by simply indexing the `args` array.

**Arrays:**

- Declaring, creating, and initializing an array are different. _Declaring_ happens with this syntax: `int [] intArray`. This does not create an array, but a reference that can point to one. An array is created with the `new` operator, like this: `int [] intArray = new int[10];`. The array now exists in memory, but there are no values. The array is initialized with values like this: `int [] intArray = {1, 2, 3, 4, 5};` (list initializer syntax) or after the creation of the array, `intArray[0] = 1;`.
- If values inside of an array are primitive, the values are stored directly in the array (directly in the allocated memory). If the values are objects, the values of the array are references to the objects, not the objects themselves.
- Multi-dimensional arrays are created by nesting the `[]` brackets. For example, `int [][] intArray = new int[3][3];` creates a 3x3 2D array. The first bracket is the row, the second is the column. The same syntactical rules apply for declaration, creation, and initialization of multi-dimensional arrays.
- In Java, mult-dimensional arrays don't have to me matrices, or square in dimensions. They can have uneven lengths of rows and columns, referred to as a "ragged array". This behavior does not exist in C and C++.

**Packages, Imports, Classpath:**

- Packages are a concept that are fairly unique to Java. A package is a way to encapsulate code, specifically classes, into logical groups. The name of a package should match the directory structure of a project. All of the code in the same package has access to each other, because all of that code is in the `package scope.` Code that is available outside of the package is in the `public scope`. The `public` keyword is used to declare a class or method as public. If a class is not declared as public, it is only available to other classes in the same package.
- Packages can have sub-packages separated by dots (`java.util.date`).
- The package name becomes part of the class name when you place a class in a package. For example, a class named `Date` in the package `java.util` would be referred to as `java.util.Date`. To refer to a packaged class, you must use the fully qualified name, with the package name and its sub-packages, OR use the `import` keyword.
- The `import` keyword is used to import classes from other packages. This is done at the top of the file, before the class declaration. This provides a shorthand for referencing packaged classes, allowing reference using just the class name.
- The wildcard `*` imports all of the classes in the package, but it is not recursive, meaning it doesn't import classes from sub-packages.
- The `java.lang` package is automatically imported, so classes in this package do not need to be imported, and don't need to be referenced with their fully qualified name.
- When using 3rd party libraries, or utilizing code external to the current class, the `java` command needs to be given the classpath value, which is given with the `-cp` flag in the command. When specifying mulitiple classpaths in one command, separate each path with a `:` on Linux/Unix systems, and `,` on Windows systems.
- CLASSPATH is an environment vairable that lists all of the directories that contian .class files, package base directories, or other resources the application needs to access.
- IDEs like IntelliJ and Eclipse manage the classpath for you.

**File I/O:**

- 2 critical classes, particularly for I/O, are the `File` class and `Scanner` class. Both are useful classes with methods that perform essential functions. A `Scanner` is an object that implements an interator, and has a method, `hasNext()` to return if a next object exists, and `.next()` to yield that next object.
- `System.out` is actually an instance of the `PrintStream` class, whose methods include, `print`, `println`, and others.
- `printf()` uses the same syntax and format characters as C and C++.

#### Key things to understand

1. _Ways Java differs from C++_
2. **Java installation**
3. **How Java is both portable and fast:** - Java's architecture of creating intermediate byte codes is a type of compromise between the greater speed but less portability of natively compiled machine code (binary), and the greater portability but slower speed of interpreted code. - Java was designed to be a "write once, run anywhere" language. This is part of the reason why programs are compiled into byte codes that are run on the Java Virtual Machine (JVM). This allows Java programs to run on any machine that has a JVM installed.
4. **How to compile and run Java code:** - Here is a note from the book about compiling and running a Java program: The `javac` compiler is invoked with the name of a file, with slashes separating the path segments, and an extension .java. The `java` virtual machine launcher is invoked with the name of a class, with dots separating the package segments, and no extension.
5. **The primitive data type available in Java:** byte, short, int, long (these 4 are the int types), float, double (these 2 are the decimal types), char, boolean.
6. **What's the difference in string literal String construction and construction with `new` operator:** - When using the string literal method for declaring a string, which is the `String = "hello"` method, the JRE stores the string in a special memory area called the "string intern table". Java will internally check if the string already exists in the pool, and if it does, it will not create a new string, but instead return a reference to the existing string. By contrast, using the `new` operator will always create a new string object on the heap.
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
- One way of thinking about _Encapsulation_ is only exposing code on a need-to-know basis. This is done by using private fields, and **getters and setters**. Common practice is to make all fields private, and then create public methods to access and return the field values, and modify the field values as needed. Sometimes this will seem redundant, but the value becomes apparent when the field values or program behaviors become more complex. Sometimes the getters and setters are as simple as this:

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

- When creating my own classes, it is generally a good idea to _overwrite_ a few of the built-in Java methods: `equals()`, `hashCode()`, and `toString()`. This is because the default implementations of these methods generally do not have the desired functionality. When overriding a built-in Java method, the method is prefixed with the `@Override` annotation on the line above. Overriding these methods is a good practice, because it ensures that hash codes are generated with all the fields being considered, and equality is being determined by all the fields as well. Overriding the `toString()` method is especially helpful for debugging.

**Things to Understand:**

- **What object references are and why we need them**
  > When a class is instantiated using the `new` operator, a pointer reference to the object is returned. This reference is stored in the variable that the develolper passes in. To reference an object inside of itself, the `this` keyword is used. Generally, when referencing an objects fields or methods, Java will assume that the reference is to the current object without the `this` keyword, unless there is a naming conflict.
- **The differences between static methods and variables and instance methods and variables**
  > Static methods and variables are associated with and operate on the class itself, not on a specific instance of the class. Instance methods and variables are associated with and operate on a specific instance of the class. Static methods and variables are declared with the `static` keyword, while instance methods and variables are not.
- **How constructors work in Java**
  > Constructors are generated by writing a method with the same name as the class name. Then, the constructor will be called whenever the class is instantiated with the `new` operator. Constructors can be overloaded, meaning that multiple constructors can be written with different parameters. The constructor that is called is determined by the parameters passed in when the class is instantiated. Another common use of constructors is to make a "copy constructor", which takes in an object of the same class and copies all of its fields to a new object.
- **What constructor the compiler writes (and when it doesn't write one)**
  > When no constructor is written, the compiler will write a default constructor that takes no parameters, and does nothing. If a constructor is written, the compiler will not write a default constructor.
- **What code the compiler adds to some constructors and why**
- **What the 'this' reference is used for**
- **When the 'this' reference is required and when it is optional**
  > Addressing the two preceeding bullet points, the `this` keyword is used to reference an object inside of itself. It is required when there is a naming conflict between a parameter and a field of the class. It is optional when there is no naming conflict, because Java can infer that the reference is to the current object.
- **How to use the 'this' reference to call another constructor in the same class**
  > The `this` keyword can be used to call another constructor in the same class. This is done by using the `this` keyword like a method call, typing `this()` and passing in the parameters for the corresponding constructor. This process, of calling another constructor in the same class, is called constructor chaining.
- **What an enum is and how to implement one**

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
  > In the context of the notes in the file, "The standard order of elements in a Java class" refers to the conventional sequence in which different parts of a Java class are typically organized. This order helps in maintaining readability and consistency across Java codebases. The standard order is generally as follows:
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
  > This order helps in locating different parts of a class quickly and understanding the structure of a Java class more easily.

### Java Records

In object-oriented programming, it is fairly common to create a class just for representing a collection of data. These instances of data field collections are referred to as `data objects`, and are fairly common in Java. Ideally, a data object should have the following characteristics:

- Is immutable
- Has getters for all fields
- Overrides the `equals`, `hashCode`, and `toString` methods to match the fields of the object

This can be done with a standard class, but it adds to the amount of boilerplate code that is needed. Because of how common data objects are, Java introduced the `record` keyword, that can create something functionally equivalent, but much, much more concise. To create a record, use the following syntax:`record Person(String name, int age) {}` Record that are created this way will meet the three characteristics mentioned above, as they are immutable, have getters for all fields, and override the `equals`, `hashCode`, and `toString` methods to factor in all of the fields of the record.

### Chapter 3 Reading: Interfaces

Polymorphism
: A blanket term in computer science for taking one object and morphing it to fit in to many contexts. In Java, this is accomplished primarily through _inheritance_, _interfaces_, and _abstract classes_.

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

Later on, Interfaces were updated to allow the use of `static`, `default`, and `private` methods. This allows for methods to be actually defined within an interface.

### Chapter 4 Reading: Inheritance and Extending Classes

When a class "inherits" from another, the inheriting class is called the `subclass` and the class that gives things to be inherited is the `superclass`. A subclass is able to access and use code from a super class as if it was written in the subclass itself. In Java, the keyword for defining a class as a subclass of another is the `extends` keyword. An example:

```java
public static class Dog extends Pet{

}
```

In this example, the subclass, Dog, is able to access all of the code for the `Pet` class, and then define its own code. This perspective makes the `extends` keyword make sense, because the class `Dog` is taking all of the functionality of `Pet`, and then **extending** past that with whatever members are defined in `Dog`. **Note:** using the `extends` keyword is (to my current knowledge) the **_only_** way to make one class a subclass of another. (I don't know if some of the built-in JDK classes have pre-made inheritance relationships, I'll have to find that out)

`Abstract Classes` are a type of hybrid between a standard class and a hybrid. If an implementing class only implements some of the methods defined in the interface, or if it defines additional methods without providing their implementation, then that class is an `abstract` class. Both the class needs to have a signature of `abstract` and the methods that are left unimplemented. Then, similar to implementing classes of interfaces, a subclass of an abstract class is what will implement the abstract methods.

Similar to interfaces, any variable whose declared data type is that of an abstract class, can hold a value of any of its implementing subclasses.

`instanceof` is an operator in Java that returns `true` if the LHS is an instance of the data type/class passed on the RHS (including if the LHS `extends` or `implements` the class on the RHS), and `false` otherwise.

Lastly, the keyword `final` can be applied to method signatures or field declarations. If a method is declared as `final`, then it cannot be overridden by subclasses. If a field variable is declared as `final` then its value become immutable. `final` methods and fields can still be called and accessed, respectively, just not changed.

### Chapter 5 Reading: Exceptions

Normally, errors are thrown to the caller of a method, so errors must move up the call stack, and generally only involve returning an error code. This is not the most effective way to handle errors. Instead, throwing exceptions allows specific _handlers_ to take over control when an exception is thrown.

Exception in Java is a subclass of `Throwable` objects. Here is the hierarchy of exceptions in Java:
![Java Exception Hierarchy](https://learning.oreilly.com/api/v2/epubs/urn:orm:book:9780135404522/files/html/images/exception-hierarchy.jpg).

> A note about the `Runtime Exception` class. Really, all exceptions are thrown at runtime, but the objects of the `RuntimeException` class are not checked by the compiler. So `Runtime Exception` means that they are first _detected_ at runtime, makings its subclasses _unchecked_ exceptions. All other exception types are checked by the compiler, thus their designation as _checked_ exceptions.

Any method that has the potential to throw a **checked** exception must have it declared in the method signature, after the method name.

To catch an exception, set up a `try` block. If an exception is thrown while any of the code in the `try` block executes, then control will be passed to the handler that is defined in the `catch` block. If no exception is thrown, then the `catch` block is skipped. The `finally` block is always executed, regardless of exception throwing.

Often times a exception being thrown will prevent resources from being correctly deleted or closed from memory, which presents a clear issue. To avoid this, there is the "try-with-resources" statement. The syntax is the same as a try block, but before the curly braces `{}` any resources that need to be closed are declared, separated by semi-colons if there are morn than one. Once an exception is thrown **or** the try block executes successfully, the resources are closed.

Example of these three:

```java
public void tryWithResources() throws IOException {
    // Close is automatically called at the end of the try block
    try (FileInputStream input = new FileInputStream("test.txt")) {
        System.out.println(input.read());
    }
    catch (FileNotFoundException e) {
        // Handle the exception
        System.out.println("An exception was thrown with this message " + e.getMessage());
    }
    finally{
        // Do something else
        System.out.println("Finally block");
    }
}
```

Things can get a bit trickier if the `close` method for one of the resources is what actually throws an exception. If just the close method exception gets thrown, then in it thrown to the caller. If another exception is thrown, and then the close method, which is executing because of the prior exception, throws an exception, the close method exception can safely be ignored for now. When this second case happens, the first exception gets rethrown, and the second exception is added as a _suppressed_ exception. These can be accessed with an exception object's `getSuppressed()` method.

`finally` blocks are another way to clean up resources after a try block is executed or an exception is thrown. Because the resources that can be passed to a `try-with-resources` block are limited to those of the class `AutoClosable`, the `finally` block is still necessary for other types of resources. Two important notes about `finally` blocks are 1, to avoid putting return statements in `finally` blocks, and 2, to avoid throwing exceptions in `finally` blocks. Because a `finally` block is always executed, a return statement in a `try` block will not be executed until the `finally` block is executed, so that return statement will be lost to the one in the `finally` block. Throwing an exception in a `finally` block will override any other exceptions that are thrown, and the exception thrown in the `finally` block will be the one that is caught, and the other exceptions will be inaccessible, as the suppression mechanism only works with try-with-resources blocks.

Generally, just avoid any code that will alter the control flow in a `finally` block.

Exceptions can be rethrown using the `throw` keyword. As of now, I don't quite understand the why behind rethrowing, so I'll need to pay attention to that in lecture. From what I do understand, one purpose of rethrowing is to **chain** exceptions, which allows you to change the class of an exception to provide more meaningful information to the caller and/or stack trace.

Some classes of exceptions have a method to get the cause to then display it, generally `.getCause()`. For those that don't have a cause as a constructor parameter, the `.initCause()` method can be used to set the cause of the exception (the argument passed to `.initCause()` must be a `Throwable` object, generally it is the exception that caused the current exception).

When an exception is never caught, a _stack trace_ is given. This is a list of all the methods that were called up to the point of the exception being thrown. Most exception classes also have a `.printStackTrace()` method that will print the stack trace to the console. Along this vein, the Object class has useful methods that can throw exceptions, such as `.requireNonNull()`. If you see that this method is the latest on a stack trace before an exception, you can much more easily detect the bug. It also can take a message string as an optional 2nd parameter. Some alternatives to these methods can be useful too, such as `.requireNotNullElse()`. which allow the developer to supply an alternative value to be used if the first value is `null`.

### Chapter 7 Reading: Collections

- The `Collection` interface is the built-in method for implementing some common data structures in Java.
- Here is a chart that shows the inheritance of various types:
    ![Collection inheritance chart](https://learning.oreilly.com/api/v2/epubs/urn:orm:book:9780135404522/files/html/images/collection-interfaces.jpg)
- [This doc](https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html) shows the methods in the Collection interface.
- A useful method that the book mentions:
    > the method `Collections.nCopies(n, o)` returns a `List` object with `n` copies of the object `o`. That object “cheats” in that it doesn’t actually store `n` copies but, when you ask about any one of them, returns `o`.
- This is an example of one of the many useful methods of the `Collections` utility class, whose methods operate on any `Collection` object. [Here](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html) is a link to the doc for this class.

**Lists:**

- `List` is a subinterface of `SequencedCollection`, a subinterface of `Collection`. It has some useful methods for getting and setting the first and last elements of a list, returning the list in reversed order, and other useful methods. [Here](https://docs.oracle.com/javase/8/docs/api/java/util/List.html) is the doc for the List methods.
- 2 main implementing classes `ArrayList` (resizable array, faster at random access, slower adding non-terminal values) and `LinkedList` (doubly-linked list, slower at random access, faster at adding non-terminal values).

**Iterators:**

- Every implementing class of the `Collection` interface provides a way to iterate through its values, because, referring to the chart above, the `Collection` interface is a subtype of the `Iterable` interface, which defines this method. The method is simply called `iterator()` and returns an iterator for that type.
- Like typical in most languages, 2 of the core methods for Java iterators are `hasNext()` which returns a boolean, and `next()` which advances the iterator.
- Whenever a "for-each" loop is used, `for (String element : collection)` What is actually being done is an iterator is being used under the hood to iterate until the end of the collection is reached.
- The `remove()` method of the Iterator interface removes the _previously visited_ element, not the one currently being pointed to. This is something to keep in mind. For more details, [here](https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html) is the doc for the Iterator interface.
- To do a conditional removal of an element, the collection method `removeIf()` is much easier than iterating through and removing. Part of this is due to the nature of iterators, in that the corresponding collection can't be mutated in between iterator instatiations, otherwise a `ConcurrentModificationException` is thrown. These are thrown to make sure that the iterator that you instantiate doesn't become invalid.

**Sets:**

- Sets are useful for when order doesn't matter, and you simply need to know if a value is an element of the set or not.
- Sets have no index, no concept of "this value is 'at' this location". That is where an iterator would be needed.
- The 2 built-in sets are `HashSet` and `TreeSet`. `HashSet` is generally more efficient, as long as you have a good hash function. `TreeSet` implements a binary search tree,and thus is better for when you want some sorting and medium ability to access values. `TreeSet` implements `SequencedSet`, `SortedSet`, and `NavigableSet` interfaces.
- There is also `LinkedHashSet`, which is a hash table combined with a linked list.

**Maps:**

- Maps are a very common data structure, that store associations between keys and values, equivalent of dictionaries in Python. Each association of key-value is called an _entry_. There are 4 main types of Maps in Java:
  1. A TreeMap traverses entries in the sort order of the keys.
  2. A HashMap hashes the keys, which is efficient but traversal visits the entries in an unpredictable order.
  3. A LinkedHashMap traverses entries in in insertion order.
  4. A ConcurrentHashMap allows safe concurrent updates
-

## Class Notes

### Lecture: Java Fundamentals and Phase 0

A brief history of Java:

- A guy left Sun Microsystems because he didn't like it there. The boss, Scott Mcneely, went to some of his senior engineers and asked them to try an find the new hot thing in tech. James Gosling was one of the engineers, and their project led to creating Oak, a language designed for communication between devices. This was all between 1991 and 1993.
- Mosiac and the web launched, and around 1994, the Green team (the group from Sun) realized they accidentally made a language that was perfect for the web. They changed the name to Java since they couldn't trademark Oak, and made the HotJava Browser, allowing dynamic content which was a big deal at the time.
- Netscape announced in 1995 that they would support Java, and that is kind of where it took off.
- Oracle then bought Sun in 2010, and also gaining Java in the process.

**Overview of Java:**
Similar syntax to C++, but different _semantics_

Java and C++ differences:

- Java has built-in garbage collection, C++ does not.
- Java uses references instead of pointers (pointers are evil according to Dr Wilkerson).
- Data types are the same size in Java, in C++ they can vary based on hardware.
- Built-in boolean type in Java, not in C++.
- In C and C++, code needs to be compiled and linked, while classes are dynamically linked at runtime in Java.
- Java is hybrid compiled/interpreted, C++ is compiled.

Compiled vs Interpreted code:

- Compiled code is less portable, but faster.
  ![Compiled](https://keep.google.com/u/0/media/v2/1Yr4fp7ENpb0A6lZiiUzDZ7UXipMP0lN4h0YPO-MZDFdM7mnY38pAf-F_XWct2AU/19Hh9hs7kezn98dnFbb9s3Pxt6Vc54hM2jiOMriSDMe8gBKd4UlNmUmt3lQlinA?accept=image%2Fgif%2Cimage%2Fjpeg%2Cimage%2Fjpg%2Cimage%2Fpng%2Caudio%2Faac&sz=915)
- Interpreted code is more portable, but slower.
- Java is a hybrid of the two, with the code being compiled by the same compiler, regardless of platform, and the same Java Byte Code is run on any hardware, and then each platform has its own JVM that interprets the Byte Code.

Java compiles in a unique way that offers some advantages. It uses JIT compilation, or Just In Time compilation. This means that the Java Byte Code is compiled to machine code at runtime, and then that machine code is run. This allows the JVM to optimize the code for the specific hardware that it is running on.

The JVM is also a Hotspot VM, meaning it can dynamically recompile at runtime, while other compilers can't do that.

### Lecture: Java Fundamentals cont. and Classes and Objects

The reason that using a `PieceMovesCalc` interface, with a set of implementing classes, works slightly better has to do with serializing and deserializing the ChessPiece objects. (**serializing** and **deserializing** is the process of converting an Object into a storage type, like JSON, and then converting it back into an Object). Because ChessPiece is what will be serialized and stored, the move calculation logic doesn't necessarily need to be stored. So, if it doesn't need to be stored it is better to leave it out, so that serialization and deserialization is a bit simpler. So, making an interface that `ChessPiece.pieceMoves()` will call, and then having the implementing classes have the logic for the moves, this will leave the move logic out of the serialization process.

- Not all classes have main methods, and in fact most don't, as they are meant to specifically only be accessed from other classes.
- Primitive data types, refer to [Ch 1 Reading](./notes.md#key-things-to-understand)
- For a lot of the other content in lecture today, reference [Java Basics Section](./notes.md#java-basics)

### Lecture: Classes and Objects continued

Although subclassing seems intuitive with `ChessPiece`, because there is already a way to determine the piece type, with the ChessPiece field `type`, subclassing would give a redundant way to determine the piece type, which is a code smell. And the reason to not put all of the behavior in switch cases in `ChessPiece` is the **Single Responsibility Principle**. This is a software engineering principle that dictates that each class handles one responsibility only. It does one job, and does it very well.

**From Dr. Wilkerson's code:** he makes a new `PieceMovesCalc`, and the value is assigned to a switch statement that switches on the piece type, to get the corresponding `PieceMovesCalc` type, and then after the switch statement, the `.pieceMoves()` method is called on the instantiated `PieceMovesCalc` object.

Remember that references and objects are different, and only the `new` operator creates objects, references are created with the `Date dt` syntax.

### Lecture: Programming Exam, Record, and Exceptions

**Programming Exam:**

- "I know students that have failed the programming exam the first time and gone on to MIT." - Dr. Wilkerson emphasizing that failing the programming exam does not mean you are stupid and not capable.
- The main takeaway from Dr. Wilkerson's spiel about the programming exam is that spending adequate time preparing is the key to passing. I'm going to do the whole 4 hours at least once as practice, even though it'll be time consuming.

**Records:**

- Because they are their own class, need to be in their own file, even though it is just the one line of code.
- Contrary to the Java convention, the automatic getters generated for records simply use the name of the field for the method name, such as `.name()`, rather than `.getName()`.
- Records are immutable, but can have methods defined within them that go beyond the automatically generated ones. If you want to mutate a record, you'd have to create an entirely new record.

**Exceptions and Exception Handling:**

- Errors are exceptions, but sometimes an exception is not an error. Exceptions are simply abnormal behavior in a class.
- What the readings and the slide say about exceptions in Java holds for the majority of languages, with a few nuances here and there.
- In Java, something unique to it is the Handle or Declare rule. This is, when a checked exception is found in compilation, the JVM forces a handler (try-catch block) to be made, or the method to be declared (in the signature) as throwing that checked exception. If one of these is not done, the JVM will not allow the program to compile.
- Dr. Wilkerson's advice is to not write handlers for _unchecked_ exceptions, because these types of exceptions are generally fixable bugs. Writing a handler for these unchecked exceptions can hide the bugs from the programmer and keep them from improving the program.
- "Swallowing" an exception means writing a handler for an exception, but then not actually implementing handling behavior for it.
- Always create Exception instances on the same line that you throw them. Don't create an exception and then throw it later.

### Lecture: Collections and Copying objects

The "collections API" refers to a group of classes and interfaces, mostly those shown in the chart from chapter 7 of the book.

- The Collection API is found in the `java.util` package.
- An important detail is that a `Collection` **cannot** store primitives, only objects. There are wrapper classes for primitives, like the `Integer` class, that can be used to store something functionally equivalent to primitive values.
- **Tip from Dr. Wilkerson:** Make declarations and references as abstract as possible, using an interface or abstract class. By "hiding" the specific type that you implement later, you can change it more easily later. He mentioned a common theme in Computer Science of "what you hide, you can change".

Some interfaces not mentioned in the book:
**Queue:**

- This interface is designed for holding elements prior to processing.
- Methods include `add(value)` to add to the end of the queue, `peek()` to view the first item but not remove it, and `remove()` to pop out the first item in the queue.
- [Javadoc](https://docs.oracle.com/javase/8/docs/api/java/util/Queue.html)
- Implementing classes:
  - ArrayDeque (fifo, resizable array impl)
  - LinkedList (fifo, linked list implementation)
  - PriorityQueue (priority queue, binary heap impl)

**Deque:**

- This interface extends on a queue by allowing for insertion and removal from front and back.
- Methods include `addFirst()`, `addLast()`, `peekFirst()`, `peekLast()`, `removeFirst()`, `removeLast()`
- [Javadoc](https://docs.oracle.com/javase/8/docs/api/java/util/Deque.html)
- Implementing classes
  - ArrayDeque (resizable array implementation)
  - LinkedList (linked list implementation)

The **stack** interface in Java is deprecated, because it does not work very well. Stacks are still a very useful data structure, and can be successfully implemented using a deque, using the corresponding peek, remove, and add methods to enforce a LIFO insertion behavior.

**Brief recap of hashing:** Take an object, and using an hashing algorithm, create a unique integer for that object, called a hash code. This hash code is modulus divided by the length of the list storing items, and that result if the index for that item. If there is a hashcode collision, each element of the hash table (the list being inserted into) can be a list, containing all of the items that have a hashcode that results in that index.

`Object.equals()` compares identity, or memory address for equality by default. This needs to be overridden to compare equality by value instead.
An object in a collection implemented with hash tables also uses identity by default for the Object.hashCode method.
**Rule**: If `equals` is based on identity (memory address), then `hashCode` should be too. If `equals` is based on value, then `hashCode` should be too, and both methods should use the same fields in their calculations.

**Copying Objects**
It is common to need to make a copy of exisitng objects in programming. This is how "Undo" capabilities work in many programs. An example for this class is when determining if the King is in Checkmate. Dr. Wilkerson recommends copying the board and trying a move when doing this.

Two main ways to copy an object:

1. Shallow copy: Simply creates a new reference to the original object and its values (Not recommended usually). Example: new reference to same linked list nodes.
2. Deep copy: Creates an entirely new object with separate identity, but the values are made equal to those of the original. Example: makes new Linked List with new nodes, that have same values as original.

Immutable objects don't need to be copied, can be referenced without worry.

2 main methods for making copying capabilities in classes: **Copy constructors** (all languages), and a **`clone` method** (Java specific).

- Copy constructors entail making a constructor that taking in an instance of the same class as a parameter.
- Making a `clone` method involve implementing the `Cloneable` interface, and overriding/using the `clone` method. ([Cloneable javadoc](https://docs.oracle.com/javase/8/docs/api/java/lang/Cloneable.html))

### Lecture: Inner Classes, Phase 1, and Design Principles

**Inner Classes:**
An inner class is a class that is defined inside of another class. Sometimes they are also referred to as nested classes. Inner classes are useful for situations in which different classes need different implementations of an iterface. An example is the `Iterator` interface, and its implementation in different `Collection` types. The implementation for an `Iterator` in a `List` is different for that of a `Set`, and even each distinct type of `Set` will need a different implementation of `Iterator`. A good way to do this is by making inner implementing classes of `Iterator` where needed.

**_Principle_**: Declare fields and variables as close as you can to where you use them.

There are different types of inner classes. Here are the fundamental ones that we covered:

1. **Static Inner Class:** A static inner class is a nested class that cannot access any of the outer class's members. Anything from the outer class that the inner class will handle must be passed to it. This is the simplest inner class type to understand.
2. **Inner Class (Non-static):** A standard inner class is identical to a static inner class, only it _is_ able to access the outer class's members. This reduces the amount of data that needs to be passed.
3. **Local Inner Class:** This type of inner class takes the above mentioned principle even further; it declares an inner class inside of the method that uses it. This makes the inner classes a bit easier to find and improves some code readability. It also allows the inner class to use local variables of the method, and in some cases allows for no constructor to be used. However, because the lifetime of the inner class's objects are usually longer than the lifetime of the local variables, there is a protective restrction, which is that a local inner class can only use local variables that are _final_ or _effecively final_, which means that they are not mutated if they aren't declared as final.
4. **Anonymous Inner Class:** This type takes this proximity principle as far as possible with inner classes: anonymous inner classes are defined within an expression, often within a return expression, of their needed method. This provides the same benefits as the other types, namely access to local variables and class members, but it is declared even closer to where it is used.

Keep in mind that Inner classes were originally developed and added to Java for event handling, so that is where they really shine. They certainly have other uses, but that is their primary one.

## Project Notes

### Phase 0 Notes

- As I worked on this phase, I realized I had to pause fairly frequently and think about how to design the classes and methods. I've had to do that in past classes, but not as much and not as early. I think this is a good part of this class being a stepping stone for me.
- In Java, the equivalent of dictionaries are `Maps`. `Map` in Java is an interface though, but its most common implementing class is `HashMap`. The methods used are a bit different than other languages, but still fairly intuitive. `put()` is to add, `get()` is to access, and `remove()` is to delete. All of these three take in a key as parameters. There is also the `.clear()` method and the `.size()` method. For iterating through a `HashMap`, use `.keyset()` to access the keys, and `values()` to access the values.
- I talked to the TA Michael for a little bit, asking him about my challenge with the HashMap's equals method in comparing 2 `ChessBoard` objects. I learned a few things:
  - A map would work fine for the ChessBoard, but it doesn't work great with the library that we use later in the project to serialize and store the ChessBoard objects in a database. This reminded me that when designing data structures, I need to keep a wider perspective, and remember all of the ways they will be utilized, as that can affect which design choice will be best.
  - Tuples don't exist in Java the way that they do in Python. Python is one of the only languages where that is a built-in data structure, so just keep that in mind.
  - The `equals` method of a HashMap works by executing the `equals` method of all of the keys and values in the map. So, if the keys and/or values have `equals` methods that need to be overridden to be more accurate, failing to do so can cause the `equals` method of the map to return inaccurate results.
  - Storing the pieces of a ChessBoard in a 2D array may work better, so I'm making a branch and trying that.
- Array syntax is a bit different than I'm used to, coming off of Python and Javascript. Remember the syntax is `type[] name = {value, value}` or `type[] name = new type[size]` to initialize as empty. To make it multidimensional, just add another set of brackets, like `type[][] name = new type[size][size]`.
- Right now I am trying to figure out how to make a 2D ArrayList to store all of the valid moves, and keep the loop fairly abstract, to avoid repeating code. **As of right now, I think I could try using the ternary operator in the `.add()` method call.**
- Java ternary operator is similar to JS and C++, `condition ? valueIfTrue : valueIfFalse`.
- The method that I had was confusing and a bit too nested. Michael's advice to find the furthest possible square in each direction eventually led me in the right direction. I tried to do recursion like he told me at first, but then figured out how to do it with iteration more effectively. I found the Single Responsibility principle to hold true in this case, as making 4 different methods for each of the directions that a piece could move, rather than one with far greater complexity, was much easier, even with the shared code between the 4. _Maybe this indicates a balance/tension between the DRY principle and Single responsibility principle._
- One thing that I had to be conscientious of in both the Rook and Bishop Move calculator classes was converting between base 0 for accessing the 2D matrix of chess pieces on the board, and the base 1 for accessing the position of pieces/creating ChessMoves. Later on I will investigate if there is an effective way to do this, or maybe ask Dr. Wilkerson.
- I should consider: _How could I have used records in my implementation of Phase 0?_
- Something that helped with abstraction was learning this technique: When the body of a loop is the same and the only thing that differs is direction of iteration in 2 different uses, abstract the loop body into a method, and pass in the iterator variable as a parameter. Then just have the loop body be calling that abstracted method.

**Phase 0 Retrospective**
So, I finished up Phase 0 on the due date, and got 100%. I ran the code quality check as well, just out of curiousity, and got 70%, which I'm very satisfied with, since we haven't lectured on that yet, and I'm not graded on it for this phase. The details said I did well with _Naming_, _Code Decompisition_, and _Package Structure*, but need to improve on _Code readability_. That checks out, to be honest. Some of the conditional statements that I used are pretty unpleasant to the eye.

I want to start this new practice that I just thought of, which is documenting/journalling a short retrospective of my experience with the project. I think I'll outline the biggest obstacle(s) and how I overcame them, and also what helped me out during the project.

**Biggest Obstacle:** I would say that my biggest obstacle was evaluating/designing how to store the Chessboard data, and get started with the movement rule implementation. Because I've never done projects like this, with this much free rein, I felt a bit lost at first. Eventually **the readings and Dr. Wilkerson's commenets in lecture about design suggestions helped me out**, but I struggled to think of some ideas on my own. In retrospect, using a 2D matrix for a chess board seems obvious, but I should give myself some slack, since this is my first significant experience with software construction.

**Biggest Helps**: _The spec, lectures on the project phases, and overview videos_ were the most helpful. I think that this was because of this: I am at the point where some of the lower-level tasks I can handle just fine, but the higher level things like design choices, such as for storing the board data, or traversing the board for movement rules, I am just beginning with. The aforementioned resources proved helpful because they targetted these things: design suggestions and tips. I'll keep this in mind going forward.

The **Javadoc from Oracle** also proved pretty helpful and comprehensive. Something else that helped was "lurking", per se, in **the Slack channel**, and reading up on threads from TAs and fellow students who had similar ideas, thoughts, and questions as me.

### Phase 1 Notes


