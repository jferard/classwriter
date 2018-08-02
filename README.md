ClassWriter - A minimal Java bytecode writer. Creates classes, methods, interfaces...

Copyright (C) 2018 J. Férard <https://github.com/jferard>

# Warning
This project if currently on hold, since I'm totally overbooked.

I will focus, when possible, on a "smart decompiler": display the source bytecode as a Java array with comments.
Here is an example of exepected output fro the constant pool:

    /* CONSTANT POOL */
                    0x00, 0x10, // number of entries: 15
    /*   #1 */ ConstantTags.UTF8, 0x00, 0x04, 'C', 'o', 'd', 'e', // u"Code"
    /*   #2 */ ConstantTags.UTF8, 0x00, 0x0D, 'H', 'e', 'l', 'l', 'o', ',', ' ', 'W', 'o', 'r', 'l',
                    'd', '!', // u"Hello, World!"
    /*   #3 */ ConstantTags.STRING, 0x00, 0x02, // #2 -> "Hello, World!"
    /*   #4 */ ConstantTags.UTF8, 0x00, 0x10, 'j', 'a', 'v', 'a', '/', 'l', 'a', 'n', 'g', '/', 'S',
                    'y', 's', 't', 'e', 'm', // u"java/lang/System"
    /*   #5 */ ConstantTags.CLASS, 0x00, 0x04, // #4 -> "java.lang.System"
    /*   #6 */ ConstantTags.UTF8, 0x00, 0x03, 'o', 'u', 't', // u"out"
    /*   #7 */ ConstantTags.UTF8, 0x00, 0x15, 'L', 'j', 'a', 'v', 'a', '/', 'i', 'o', '/', 'P', 'r',
                    'i', 'n', 't', 'S', 't', 'r', 'e', 'a', 'm', ';', // u"Ljava/io/PrintStream;"
    /*   #8 */ ConstantTags.NAMEANDTYPE, 0x00, 0x06, 0x00, 0x07, // #6:#7 -> out:Ljava.io.PrintStream;
    /*   #9 */ ConstantTags.FIELDREF, 0x00, 0x05, 0x00, 0x08,
                    // #5~#8 -> java.lang.System~out:Ljava.io.PrintStream;
    /*  #10 */ ConstantTags.UTF8, 0x00, 0x13, 'j', 'a', 'v', 'a', '/', 'i', 'o', '/', 'P', 'r', 'i',
                    'n', 't', 'S', 't', 'r', 'e', 'a', 'm', // u"java/io/PrintStream"
    /*  #11 */ ConstantTags.CLASS, 0x00, 0x0A, // #10 -> "java.io.PrintStream"
    /*  #12 */ ConstantTags.UTF8, 0x00, 0x07, 'p', 'r', 'i', 'n', 't', 'l', 'n', // u"println"
    /*  #13 */ ConstantTags.UTF8, 0x00, 0x15, '(', 'L', 'j', 'a', 'v', 'a', '/', 'l', 'a', 'n', 'g',
                    '/', 'S', 't', 'r', 'i', 'n', 'g', ';', ')', 'V', // u"(Ljava/lang/String;)V"
    /*  #14 */ ConstantTags.NAMEANDTYPE, 0x00, 0x0C, 0x00, 0x0D,
                    // #12:#13 -> println:(Ljava.lang.String;)V
    /*  #15 */ ConstantTags.METHODREF, 0x00, 0x0B, 0x00, 0x0E,
                    // #11~#14 -> java.io.PrintStream~println:(Ljava.lang.String;)V


# Example
## Source file

    package com.github.jferard.classwriter;

    public class HelloWorld {
        public static final void main(final String[] args) {
            System.out.println("Hello, world!");
        }
    }

## Class file
The command:

    $ javap -v -p -s -sysinfo -constants HelloWorld.class

Gives:

    Classfile /home/jferard/prog/java/HelloWorld.class
      Last modified 5 août 2018; size 458 bytes
      MD5 checksum 5febe6dff46a7696d228117c324e3a66
      Compiled from "HelloWorld.java"
    public class com.github.jferard.classwriter.HelloWorld
      minor version: 0
      major version: 52
      flags: ACC_PUBLIC, ACC_SUPER
    Constant writablePool:
       #1 = Methodref          #6.#15         // java/lang/Object."<init>":()V
       #2 = Fieldref           #16.#17        // java/lang/System.out:Ljava/io/PrintStream;
       #3 = String             #18            // Hello, world!
       #4 = Methodref          #19.#20        // java/io/PrintStream.println:(Ljava/lang/String;)V
       #5 = Class              #21            // com/github/jferard/classwriter/HelloWorld
       #6 = Class              #22            // java/lang/Object
       #7 = Utf8               <init>
       #8 = Utf8               ()V
       #9 = Utf8               Code
      #10 = Utf8               LineNumberTable
      #11 = Utf8               main
      #12 = Utf8               ([Ljava/lang/String;)V
      #13 = Utf8               SourceFile
      #14 = Utf8               HelloWorld.java
      #15 = NameAndType        #7:#8          // "<init>":()V
      #16 = Class              #23            // java/lang/System
      #17 = NameAndType        #24:#25        // out:Ljava/io/PrintStream;
      #18 = Utf8               Hello, world!
      #19 = Class              #26            // java/io/PrintStream
      #20 = NameAndType        #27:#28        // println:(Ljava/lang/String;)V
      #21 = Utf8               com/github/jferard/classwriter/HelloWorld
      #22 = Utf8               java/lang/Object
      #23 = Utf8               java/lang/System
      #24 = Utf8               out
      #25 = Utf8               Ljava/io/PrintStream;
      #26 = Utf8               java/io/PrintStream
      #27 = Utf8               println
      #28 = Utf8               (Ljava/lang/String;)V
    {
      public com.github.jferard.classwriter.HelloWorld();
        elementName: ()V
        flags: ACC_PUBLIC
        Code:
          stackItems=1, locals=1, args_size=1
             0: aload_0
             1: invokespecial #1                  // Method java/lang/Object."<init>":()V
             4: return
          LineNumberTable:
            line 3: 0

      public static final void main(java.lang.String[]);
        elementName: ([Ljava/lang/String;)V
        flags: ACC_PUBLIC, ACC_STATIC, ACC_FINAL
        Code:
          stackItems=2, locals=1, args_size=1
             0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
             3: ldc           #3                  // String Hello, world!
             5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
             8: return
          LineNumberTable:
            line 5: 0
            line 6: 8
    }
    SourceFile: "HelloWorld.java"

## ClassWriter

      ClassWriter classWriter = ClassWriter.builder("HelloWorld").package("com.github.jferard.classwriter")
        .access(Access.PUBLIC)
        .init(
          MethodWriter.builder("<init>")
            .access(Access.PUBLIC)
            .code(
              CodeWriter.builder()
                .aload_0()
                .invokespecial(new MethodRef("java.lang.Object", "<init>":()V)
                .return().build()
            )
         )
        .method(
          MethodWriter.builder("main")
            .access(Access.builder().public().static().final().build())
            .params("args", "String[]")
            .code(
              CodeWriter.builder()
                .getstatic(new FieldRef("java.lang.Sytem", "out"))
                .ldc("Hello, World!")
                .invokevirtual(new MethodRef("java/io/PrintStream", "println:(Ljava/lang/String;)V")
                .return().build()
            )
          ).build()).build();
      classWriter.write(".");

# Objects

* class
* access tags
* encodedAnnotations
* methods
* fields
* methodRefs (for code)

## How is code processed?
There is a preprocess to compute:
* the offset each subroutine (a subroutine is a piece of code itself) for `jsr` instructions
* the offset of each label for `goto` instructions.

(Note: labels are mock instructions used by classwriter to use goto without knowing the offset at first).

### The "wide" issue

The `goto` instruction has a "wide" version, `goto_w`, as well as the `jsr` instruction.

Algorithm:

#### First step
* preprocess every instruction and add the size to the current offset
* if it is a `goto(l)` or a `if_xxx(l)` instruction, store the offset in a `offsetsByGoto` map (label -> offset list).
* if it is a `jsr(sr)` instruction, store the offset in a `offsetsByJsr` map (subroutine -> offset list).
* if it is a mock `label(l)`, store the offset in a `offsetByLabel` map (label -> offset).
* compute the sizes of the main code and of every subroutine with the "thin" versions (`goto` and `jsr` have a size of 3)
* then store offsets in a `offsetBySubroutine` map (subroutine -> offset)

#### Second step
* check every entry of `offsetByLabel`.
* if a label `l` has an offset `o >= 2**16` (= `MAX_SHORT + 1`), for every offset `o'` in `offsetsByGoto(l)` add 2 to offsets that are > `o'` in every map (including `offsetsByGoto`).  

* do the same for `offsetBySubroutine`
