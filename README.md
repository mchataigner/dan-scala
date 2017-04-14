Dans s'cas là.
==============
Authors
-----------
* Ludwine Probst [@nivdul](https://twitter.com/nivdul)
* Mathieu Chataigner [@mchataigner](https://twitter.com/mchataigner)
* Jonathan Winandy [@ahoy_jon](https://twitter.com/ahoy_jon)
* Jean Helou [@jeanhelou](https://twitter.com/jeanhelou)

Requirements
------------
What you will need :
* Internet connexion
* Java Development Kit 8 or more recent
* A terminal
* A text editor or a java IDE with scala plugin
* [OPT] Scala

Launch Hands-on
------------

You can launch this handson via
* ```./handson``` on linux/mac
* ```handson.bat``` on windows

These scripts launch SBT (scala build tool) in the background preconfigured with custom commands to play the hands-on.
The main command is:

    > go

This command launch first test and watch file changes. Each time you modify and save a file, it replays current test.
As soon as a test succeed, it goes to the next one.

It will pause at the first wrong answer or code whole (**`__`** or **`???`**) that you will replace by correct values.

All code in src/main is the machinery for this handson. It uses advanced scala concepts: implicit, macro. You should not worry about this code.

One important thing is the **`anchor`** method. It's a custom method we added to the workshop (it's not standard scala library). It will help you debug your program by inspecting the content of some value/code expression.

Plan:
-----

* Part 1: basic scala concepts
* Part 2: collection manipulation
* Part 3: functional programming
* Part 4: advanced collections
* Part 5: write a small http server
* Part 6: write a small spark job
* Part 7: write a small scalding job

Hints
-----

### [ScalaDoc](http://www.scala-lang.org/api/current/#package)

Please read the **[ScalaDoc](http://www.scala-lang.org/api/current/#package)**. You will find many answers to questions you might have.

### REPL

Do not hesitate to use the **[Scala REPL](http://docs.scala-lang.org/overviews/repl/overview.html)** (or **[Ammonite REPL](http://www.lihaoyi.com/Ammonite/#Ammonite-REPL)** same but with colors) or **[Scala worksheet](https://confluence.jetbrains.com/display/IntelliJIDEA/Working+with+Scala+Worksheet)** (in your IDE) to try stuff and have real time feedback.

### Code Generated

If you installed Scala on your machine, you will be able to compile simple code:

toto.scala:
```scala
case class Toto(name: String)
```

with scalac

```
$ scalac toto.scala
```

and then you can inspect what gets generated with javap:

```
$ javap toto.scala
```

```java
Compiled from "toto.scala"
public class Toto {
  public java.lang.String name();
  public Toto(java.lang.String);
}
```

Inspired by
-----------

* [Scala koans](http://www.scalakoans.org/)
* [Scala Coursera](https://www.coursera.org/specializations/scala)

Going further
-------------

This handson is a first introduction to scala. There are many libraries available. One good pointer for more functional programming style is [Typelevel](http://typelevel.org/). It's an open source community of projects focused on pure functional programming in Scala.

Another entry point to other libraries is [Awesome Scala](https://github.com/lauris/awesome-scala)
