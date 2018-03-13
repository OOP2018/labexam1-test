## Test Code for Labexam1 (2018)

This contains test code for Labexam1.  You can add it to your 
existing git project as a [submodule][git-submodule] using directory "test".
A [submodule][git-submodule] lets you separate parts of a code into different Git repositories, so you can maintain the parts separately.  Please follow the instructions carefully to avoid messing up your labexam1 project.

**Requires:** JUnit 4.x library and Java 8.

## How to Use

Add this test code *inside* your existing Git repository for "labexam1" as a *submodule*.  The submodule classes are a separate repo inside your repository.  When you get done, your project will look like this:
```
labexam1-yourname/
   .gitignore
   bin/            - compiler output (may be "build" or "out")
   src/            - your source code
   src/labexam1/   - source package labexam1, contains your classes
   test/           - Git Submodule for labexam1-test repo
   test/labeam1/   - test package for JUnit test classes (part of submodule)
```

Suppose your project is in a directory named `labexam1-yourname`.  Then do this:

1. Make sure your project **does not** have a directory named `test`.  If you do, either rename it or delete it.

2. Add this repository (labexam1-test) to your project into a **new directory named test** **inside your project directory**.  Use a command window for this. 
   ```shell
   cmd> cd workspace/labexam1-yourname
   cmd> git submodule add  https://github.com/OOP2018/labexam1-test.git  test
   Cloning into 'test'...
   ...
   Receiving objects: 100% (9/9), 6.7KiB
   ```
3. Check the repo status.  You'll notice 2 new files: `.gitmodules` and `test`.
   ```shell
   cmd> git status
   On branch master

   Changes to be committed:
       new file:   .gitmodules
       new file:   test
   ```
   You don't have to "add" or commit these.  Just use the test code in your IDE.
4. In your preferred IDE, open your `labexam1-yourname` project and make 2 changes:
    * Add JUnit 4 library to the project.
    * Add `test` as a new source directory.
    * In Eclipse, you **right-click** on the project and choose *Build Path* -> *Configure Build Path* -> click *Libraries* tab -> *Add Library* -> JUnit 4.  Then select the *Source* tab, click *Add Folder...* button, and select the `test` directory. Now you should have 2 source folders: `src` and `test`.
5. Run the `test/labexam1/TestRunner` class to run all tests.

## The Tests

* `test/labexam1/*Test.java` - JUnit tests of your classes.  Comments in the code explain what the tests do.
* `test/labexam1/TestRunner.java` - Main class, does 3 things:
    1. Runs JUnit tests of your classes
    2. Invokes Receipt.printReceipt(sale) to print receipt for a sample sale.  The Receipt should contain:
        * List of items sorted by product Id.  No duplicate items.
        * Item data is neatly aligned in columns.
        * Receipt includes a **readable** date.
        * Receipt includes total price of the sale (even if the total is wrong).
    3. Invokes Main.main() to perform a sale, using a redirected InputStream.
* Javadoc in LineItem: extra points for writing a descriptive class Javadoc comment in LineItem, including \@author with your name.


[git-submodule]: https://git-scm.com/book/en/v2/Git-Tools-Submodules
