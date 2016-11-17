package part_1_basic

import support.HandsOnSuite

/**
* You define a class in Scala by using the `class` keyword.
* Like in Java, a class contain fields and methods (by default everything is public).
*/

  /**
  *   This class defined in Java
  *
  *     class User {
  *
  *       String name;
  *
  *       public User(String name) {
  *         this.name = name;
  *         System.out.println("User " + name + " created");
  *       }
  *
  *       public String getName() {
  *         return this.name;
  *       }
  *
  *       public void setName(String name) {
  *         this.name = name;
  *       }
  *
  *       public String sayHello() {
  *         return "Hello " + this.name;
  *       }
  *     }
  */

  /**
  *   The same in Scala
  *
  *     class User(var name: String) {
  *       println(s"User $name created")
  *
  *       def sayHello = s"Hello $name"
  *     }
  */

class e1_classes extends HandsOnSuite {

  // The '{}' block is not required if empty
  class ClassWithValParameter(val name: String)

  /**
  * For a val parameter, no setter is generated
  */
  exercice("a val parameter defines a getter") {
    val aClass = new ClassWithValParameter("name goes here")
    aClass.name should be(__)
  }

  class ClassWithVarParameter(var description: String)

  /**
  * For a var parameter, both a getter and a setter are generated
  */
  exercice("a var parameter defines a getter and a setter") {
    val aClass = new ClassWithVarParameter("description goes here")
    aClass.description should be(__)

    aClass.description = "new description"
    aClass.description should be(__)
  }

  /**
  * You can also define a private field
  */
  class ClassWithPrivateVarFields(private var name: String){
    override def toString: String = name
    def changeName(newname: String) = {
      name = newname
    }
  }

  exercice("private variable") {
    val aClass = new ClassWithPrivateVarFields("name")
    // NOTE: aClass.name is not accessible

    aClass.toString should be(__)

    aClass.changeName("newname")
    aClass.toString should be(__)
  }
}
