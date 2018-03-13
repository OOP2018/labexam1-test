## Test Code for Labexam1 (2018)

This contains test code for Labexam1.  It can be added to your 
existing git project as a [submodule][git-submodule].  Please follow the instructions to avoid messing up your existing labexam1 project.

**Requires:** JUnit 4.x library and Java 8.

## How to Use

This code can be added *inside* an existing Git repository as a *submodule*.  The submodule code won't be part of your repo, which means your repo and the submodule can be updated separately.  

Suppose your project is in a directory named `labexam1-fatalaijon`.  Then do this:

1. Make sure your project *does not* have a directory named `test`.  If you do, either move it or use a different name for the "test" code from the submodule.

1. Add submodule from git@github.com:OOP2018/labexam1-test.git **into a new directory named test**
   ```shell
   cmd> cd workspace/labexam1-fatalaijon
   cmd> git submodule add git@github.com:OOP2018/labexam1-test.git test
   Cloning into 'test'...
   ...
   Receiving object: 100% (9/9), 6.7KiB
   ```
2. Check the status.  You'll notice 2 new files: `.gitmodules` and `test`.
   ```shell
   cmd> git status
   On branch master

   Changes to be committed:
       new file:   .gitmodules
       new file:   test
   ```
3. In your preferred IDE, open your `labexam1-yourname` project and make 2 changes:
    * Add JUnit 4 library to the project.
    * Add `test` as a new source directory.
    * In Eclipse, you **right-click** on Project and choose *Build Path* -> *Configure Build Path* -> click *Libraries* tab, *Add Library* -> JUnit 4.  Then select the *Source* tab, click *[Add Folder...]* and choose the "test" directory.
4. Run the `test/labexam1/TestRunner` class to run all tests.

## The Tests

* `test/labexam1/*Test.java` - JUnit tests of your classes.  See comments in the code for explanation of what the tests do.
* `test/labexam1/TestRunner.java` - Main class. Does 3 things:
    1. Runs JUnit tests for student classes
    2. Invokes Receipt.printReceipt to print receipt for a sample sale.  The Receipt should contain:
        * List of items sorted by product Id.  No duplicate items.
        * Item data is neatly aligned in columns.
        * Receipt includes a **readable** date.
        * Receipt includes total price of the sale (even if the total is wrong).
    3. Invokes Main.main to make a sale, using a redirected InputStream.
* Javadoc in LineItem: extra points for writing a descriptive class Javadoc comment in LineItem, including \@author with your name.


## References

[git-submodule]: https://git-scm.com/book/en/v2/Git-Tools-Submodules
