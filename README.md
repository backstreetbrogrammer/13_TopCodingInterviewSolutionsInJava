# Top Coding Interview Solutions In Java

> Collection of most popular coding interview problems and solutions in Java

## Table of contents

1. [Sort and Merge using multithreading](https://github.com/backstreetbrogrammer/13_TopCodingInterviewSolutionsInJava#problem-1-sort-and-merge-using-multithreading)
    - using `Thread` / `Runnable`
    - using `CompletableFuture`
2. [Random Sampling](https://github.com/backstreetbrogrammer/13_TopCodingInterviewSolutionsInJava#problem-2-random-sampling)
    - using `Collections.shuffle()`
    - using `Collections.swap()`
    - online sampling on streaming data
3. [Deadlock](https://github.com/backstreetbrogrammer/13_TopCodingInterviewSolutionsInJava#problem-3-deadlock)
    - demonstrate deadlock issue
    - resolve deadlock issue
4. [Implement a `Stack` in Java](https://github.com/backstreetbrogrammer/13_TopCodingInterviewSolutionsInJava#problem-4-stack)
    - without using Java collections
    - using Java collections
    - bounded concurrent stack using locks
    - concurrent stack using CAS (Atomic classes)
5. [Odd-Even printer using multithreading](https://github.com/backstreetbrogrammer/13_TopCodingInterviewSolutionsInJava#problem-4-stack)
6. [Implement UNIX `tail` command in Java](https://github.com/backstreetbrogrammer/13_TopCodingInterviewSolutionsInJava#problem-6-implement-unix-tail-command-in-java)
    - tail on a static file
    - tail on a running file (appended in real-time)
7. [False sharing](https://github.com/backstreetbrogrammer/13_TopCodingInterviewSolutionsInJava#problem-7-false-sharing)
    - demonstrate false sharing
    - resolve false sharing
8. [Implement Read-Write Lock](https://github.com/backstreetbrogrammer/13_TopCodingInterviewSolutionsInJava#problem-8-implement-read-write-lock)
    - Read Reentrant
    - Write Reentrant
    - Read to Write Reentrant
    - Write to Read Reentrant
    - Fully Reentrant
9. [Implement UNIX `find` command in Java](https://github.com/backstreetbrogrammer/13_TopCodingInterviewSolutionsInJava#problem-9-implement-unix-find-command-in-java)
10. [Design LRU cache](https://github.com/backstreetbrogrammer/13_TopCodingInterviewSolutionsInJava#problem-10-design-lru-cache)

### Youtube

![Top Java Coding Problems](TopJavaCodingProblems.png)

[Top Coding Interview Solutions In Java playlist](https://www.youtube.com/playlist?list=PLQDzPczdXrTiEt7nlXb66dgiAjXhhUAUU)

- [01 - Top Java Coding Problems - Tutorial Series](https://youtu.be/uUdkLShAvkY)

---

### Problem 1: Sort and Merge using multithreading

Given an array of N size with random integers. Write a multithreaded program that performs the following operations on
this array:

- Thread 1 sorts the even numbers
- Thread 2 sorts the odd numbers
- Thread 3 merge the results with even numbers in the top part of the array

#### Example:

We have an array [2, 29, 3, 0, 11, 8, 32, 94, 9, 1, 7] of 11 elements.

- Thread 1 results [0, 2, 8, 32, 94]
- Thread 2 results [1, 3, 7, 9, 11, 29]
- Thread 3 results [0, 2, 8, 32, 94, 1, 3, 7, 9, 11, 29]

#### Follow up

Use `CompletableFuture` to solve the same

#### Youtube

- [02 - Sort and Merge using multithreading](https://youtu.be/oR8qfx3Gops)
- [03 - Sort and Merge using multithreading - SOLUTION - Code Demo 1](https://youtu.be/yG5ksLYtINk)
- [04 - Sort and Merge using multithreading - SOLUTION - Code Demo 2](https://youtu.be/1g1Jy4lZShM)
- [05 - Sort and Merge using multithreading - SOLUTION - Code Demo 3](https://youtu.be/nAl1nnQSR7Q)
- [06 - Sort and Merge using CompletableFuture - SOLUTION - Code Demo 4](https://youtu.be/1Vj_-EDQOxU)

---

### Problem 2: Random Sampling

You are working in a software product company and the software product is being used by multiple clients. Whenever a new
feature is added to the product, company needs to select a **random subset of its clients** to roll out the new feature
in. This is needed as the company wants to see the effect of the new feature without taking the chance of alienating all
its users if the rollout is unsuccessful.

Implement an algorithm that takes as input an array of distinct elements and a size, and returns a subset of the given
size of the array elements. All subsets should be equally likely.

Hint: Use `Collections.shuffle()` method

#### Example:

We have a list of integers: 1 to 10 and we need a sublist of sample size of 4 integers. Every run should give different
sublist of 4 integers as sample with equal likeliness.

#### Follow up 1

Use `Collections.swap()` method

#### Follow up 2

Online sampling on streaming data.

**Network packet sniffer** is designed such that to provide a uniform sample of packets for a network session.

Write a program that takes as input a size `k` and read packets, continuously maintaining a uniform random sample of
size `k` of the read packets.

#### Youtube

- [07 - Random Sampling - PROBLEM STATEMENT](https://youtu.be/SjEHO2qWy7g)
- [08 - Random Sampling - SOLUTION - Code Demo 1](https://youtu.be/sFlR7Dnzp6g)
- [09 - Random Sampling - SOLUTION - Using Collections.shuffle() method](https://youtu.be/aoAeKm4_Eks)
- [10 - Random Sampling - SOLUTION - Using Collections.swap() method](https://youtu.be/jkhO8uCBs1U)
- [11 - Random Online Sampling - SOLUTION - Code Demo 1](https://youtu.be/Z_W7k-bYbqI)
- [12 - Random Online Sampling - SOLUTION - Code Demo 2](https://youtu.be/fvZr3PASqnI)
- [13 - Random Online Sampling - SOLUTION - Code Demo 3](https://youtu.be/728Pb3RbdAI)

---

### Problem 3: Deadlock

Suppose there are 2 threads T1 and T2 which need to acquire locks L1 and L2. If T1 first acquires L1 and then T2
acquires L2, they end up waiting on each other forever.

Write a program to demonstrate deadlock issue.

#### Follow up

Modify the program to resolve the deadlock issue.

#### Youtube

- [14 - Deadlock - PROBLEM STATEMENT](https://youtu.be/ZSA9di_lixM)
- [15 - Deadlock - SOLUTION - Code Demo 1](https://youtu.be/IPjFzRoJ4A8)
- [16 - Deadlock - SOLUTION - Code Demo 2](https://youtu.be/rwnX3uyrD0Y)

---

### Problem 4: Stack

A stack is a linear data structure that follows the LIFO - **Last-In, First-Out** principle. That means the objects can
be inserted or removed only at one end of it, also called a **top**. Last object inserted will be the first object to
get.

![Stack](Stack.PNG)

Implement a stack in Java without using any standard Java Collections.

Following 3 methods should be implemented from the `StackI` interface.

```java
public interface StackI<T> {

    T pop();

    void push(T item);

    T peek();

}
```

#### Follow up 1

Implement a stack in Java using Java collections.

#### Follow up 2

Implement a bounded concurrent stack in Java using locks.

#### Follow up 3

Implement a concurrent stack in Java using CAS (Atomic classes in Java).

This solution is based on [Treiber stack algorithm](https://en.wikipedia.org/wiki/Treiber_stack)

#### Youtube

- [17 - Stack - PROBLEM STATEMENT](https://youtu.be/GDeqvqme0YU)
- [18 - Stack custom implementation - SOLUTION - Code Demo](https://youtu.be/4LRcaIEA6Tg)
- [19 - Stack custom implementation - SOLUTION - Unit Test Code Demo](https://youtu.be/YiB3XGTclwo)
- [20 - Java Stack Follow Up 1 - SOLUTION - Unit Test Code Demo](https://youtu.be/85wSAvotfHY)
- [21 - Stack implementation using Locks - Follow Up 2 - SOLUTION - Code Demo 1](https://youtu.be/f14SPQfaASE)
- [22 - Stack implementation using Locks - Follow Up 2 - SOLUTION - Code Demo 2](https://youtu.be/WXsv-4YNErw)
- [23 - Stack implementation using Locks - Follow Up 2 - SOLUTION - Unit Test Code Demo 1](https://youtu.be/WX4WXJLw0nU)
- [24 - Stack implementation using Locks - Follow Up 2 - SOLUTION - Unit Test Code Demo 2](https://youtu.be/1gC7th9CFoU)
- [25 - Stack implementation using CAS - Follow Up 3 - SOLUTION - Code Demo 1](https://youtu.be/fEjY-ewwr9U)
- [26 - Stack implementation using CAS - Follow Up 3 - SOLUTION - Code Demo 2](https://youtu.be/Cb-2NDY_BLE)
- [27 - Stack implementation using CAS - Follow Up 3 - SOLUTION - Unit Test Code Demo](https://youtu.be/zLrZKsvymQY)

---

### Problem 5: Print odd and even numbers by 2 threads

Write Java code in which the 2 threads, running concurrently, print the numbers from 1 to 100 **in order**.

- Thread 1 prints odd numbers from 1 to 100
- Thread 2 prints even numbers from 1 to 100

Optionally, name the 2 threads as **OddThread** and **EvenThread** and print the thread name while printing the numbers.

Sample output:

```
i=1, [OddThread]
i=2, [EvenThread]
i=3, [OddThread]
i=4, [EvenThread]
i=5, [OddThread]
...
...
i=96, [EvenThread]
i=97, [OddThread]
i=98, [EvenThread]
i=99, [OddThread]
i=100, [EvenThread]
```

#### Youtube

- [28 - Odd Even Printer Using Multithreading - PROBLEM STATEMENT](https://youtu.be/tS4G7U-FxmU)
- [29 - Odd Even Printer Using Multithreading - SOLUTION - Code Demo 1](https://youtu.be/avl3BTLAXyo)
- [30 - Odd Even Printer Using Multithreading - SOLUTION - Code Demo 2](https://youtu.be/8RQmdOSCCak)
- [31 - Odd Even Printer Using Multithreading - SOLUTION - Code Demo 3](https://youtu.be/TbUeV4sq_GY)

---

### Problem 6: Implement UNIX `tail` command in Java

UNIX `tail` command displays the last part of a file on the unix or linux server. Even if the file is being updated
currently, `tail` command will display the latest data appended to the file in real-time. Thus, `tail` command is very
useful in monitoring a running application logs.

Write a program to implement the same `tail` command like method in Java. Assume that tail method takes two arguments -
a filename, and the number of lines, starting from the last line, that are to be printed.

#### Follow up

Implement the `tail` method for a **running** log file (appended in real-time).

#### Youtube

- [32 - Implement UNIX tail command in Java - PROBLEM STATEMENT](https://youtu.be/9NCo8Lr-1IE)
- [33 - Implement UNIX tail command in Java - SOLUTION - Code Demo 1](https://youtu.be/yDDnpWQjNpY)
- [34 - Implement UNIX tail command in Java - SOLUTION - Code Demo 2](https://youtu.be/I9KPISd2LsQ)
- [35 - Implement UNIX tail command in Java - SOLUTION - Code Demo 3](https://youtu.be/LRucuskHIVQ)
- [36 - Implement UNIX tail command in Java - SOLUTION - Code Demo 4](https://youtu.be/iUfZN4GD668)
- [37 - Implement UNIX tail command in Java - SOLUTION - Code Demo 5](https://youtu.be/ixn7qUWKplU)
- [38 - Implement UNIX tail command in Java - SOLUTION - Code Demo 6](https://youtu.be/HIKMZX3rEGQ)

---

### Problem 7: False Sharing

In computer science, false sharing is a performance-degrading usage pattern that can arise in systems with distributed,
coherent caches at the size of the smallest resource block managed by the caching mechanism.

False sharing in Java occurs when two threads running on two different CPUs write to two different variables which
happen to be stored within the same CPU cache line. When the first thread modifies one of the variables - the whole CPU
cache line is invalidated in the CPU caches of the other CPU where the other thread is running. This means, that the
other CPUs need to reload the content of the invalidated cache line - even if they don't really need the variable that
was modified within that cache line.

In simpler words, CPU cache is organized in lines of data. Each line can hold 8 longs - 64 bytes. When a visible
variable is modified in L1 or L2 cache, all the line is marked "dirty" for the other caches. A read on a dirty line
triggers a refresh of this line -> which is to flush the data from main memory.

![False Sharing](FalseSharing.PNG)

Write a program to demonstrate false sharing in Java.

#### Follow up

Modify the program to resolve false sharing and improve performance. (Measure the performance)

#### Youtube

- [39 - False Sharing - PROBLEM STATEMENT](https://youtu.be/MVz4qfZ0bzM)
- [40 - Demonstrate False Sharing - SOLUTION - Code Demo 1](https://youtu.be/zgVUMP6dO10)
- [41 - Demonstrate False Sharing - SOLUTION - Code Demo 2](https://youtu.be/q8F9-sWe_es)
- [42 - Resolve False Sharing - SOLUTION - Code Demo 3](https://youtu.be/5a5K5ELQXJQ)

---

### Problem 8: Implement Read-Write Lock

Suppose there is an `Object obj`, which is **read from** and **written by** many threads.

We need to ensure that no thread may access `obj` for reading or writing while another thread is writing to `obj`.

If no thread is writing to `obj` and no threads have **requested write access**, then multiple threads can **read**
the `obj` at the same time.

Implement the synchronization mechanism which adheres to the above read-write conditions.

#### Re-entrance

With a reentrant lock / locking mechanism, the attempt to acquire the same lock will succeed, and will increment an
internal counter belonging to the lock. The lock will only be released when the current holder of the lock has released
it and counter is set to zero.

It means we can do something like this on a **single** thread:

- Acquire a lock on "foo"
- Do something
- Acquire a lock on "foo". Note that we haven't released the lock that we previously acquired.
- ...
- Release lock on "foo"
- ...
- Release lock on "foo"

Here's an example in Java using primitive object locks / monitors which are reentrant:

```
final Object lock = new Object();
...
synchronized (lock) {
    ...
    doSomething(lock, ...)
    ...
}

public void doSomething(Object lock, ...) {
    synchronized (lock) {
        ...
    }
}
```

#### Follow up 1 - Read re-entrance

A thread is granted **read re-entrance** if it can get read access (no writers or write requests), or if it already has
read access (regardless of **write requests**).

#### Follow up 2 - Write re-entrance

Write re-entrance is granted only if the thread has already got write access.

#### Follow up 3 - Read to Write re-entrance

A thread that have read access to also obtain write access. For this to be allowed the thread must be the only reader.

#### Follow up 4 - Write to Read re-entrance

A thread that has got write access needs read-access too. A writer should always be granted read access if requested. If
a thread has got write access, no other threads can have read nor write access.

#### Follow up 5 - Fully Reentrant ReadWriteLock

Combine all the above for fully re-entrant ReadWriteLock.

#### Youtube

- [43 - Implement Read-Write Lock - PROBLEM STATEMENT](https://youtu.be/bwHID3mQ5HY)
- [44 - Implement Read Write Lock - SOLUTION - Code Demo](https://youtu.be/SGtzx3jL1Uo)
- [45 - Implement Read Write Lock - UNIT TEST CASE - Code Demo1](https://youtu.be/h8t6kJ1-PtM)
- [46 - Implement Read Write Lock - UNIT TEST CASE - Code Demo2](https://youtu.be/JYx56cI67Qs)
- [47 - Implement Read Write Lock - READ REENTRANT](https://youtu.be/E9K5kfQCJd0)
- [48 - Implement Read Write Lock - READ REENTRANT - Code Demo](https://youtu.be/G-6hBnBEOho)
- [49 - Implement Read Write Lock - READ REENTRANT - Unit Test Code Demo](https://youtu.be/Fp4BtHLaSVk)

---

### Problem 9: Implement UNIX `find` command in Java

UNIX `find` command locates files based on some user-specified criteria and either prints the pathname of each matched
object or, if another action is requested, performs that action on each matched object.

It initiates a search from a desired starting location and then recursively traverses the nodes (directories) of a
hierarchical structure (typically a tree). `find` can traverse and search through different file systems of partitions
belonging to one or more storage devices mounted under the starting directory.

The possible search criteria include a **pattern** to match against the **filename**. By default, `find` returns a list
of all files below the current working directory, although users can limit the search to any desired maximum number of
levels under the starting directory.

Write a program to implement the same `find` command like method in Java. Assume that `find` method takes two arguments

- a directory name as String, and
- the file name pattern as String, that are to be printed

```
Usage: java JFind <directory> <filename-pattern>
```

---

### Problem 10: Design LRU cache

In computing, a `cache` is a hardware or software component that stores data so that future requests for that data can
be served faster; the data stored in a cache might be the result of an earlier computation or a copy of data stored
elsewhere.

A `cache hit` occurs when the requested data can be found in a cache, while a `cache miss` occurs when it cannot.

Cache **hits** are served by reading data from the cache, which is faster than recomputing a result or reading from a
slower data store; thus, the more requests that can be served from the cache, the faster the system performs.

To be cost-effective and to enable efficient use of data, caches must be relatively small.

The **Least Recently Used** (`LRU`) cache is a cache eviction algorithm that organizes elements in order of use. In LRU,
as the name suggests, the element that hasn't been used for the longest time will be evicted from the cache.

Design an LRU cache using Java provided API / collections - `LinkedHashMap`.

#### Follow up

Design an LRU cache which uses classic data structures and is also thread safe.

**LRU cache algorithm**

1. **Inserting key,value pair** `put(K,V)`:
    - Create a new `linked list` **node** with `key`, `value` and insert into `head` of `linked list`.
    - Insert `key -> node` mapping into `hash table`.

2. **Get value by key** `get(K)`:
    - Lookup **node** in `hash table` and return node `value`.
    - Then update **most recently used** item by moving the node to **front** (`head`) of `linked list`.
    - `Hash table` does **NOT** need to be updated.

3. **Finding least recently used**:
    - **Least recently used** item will be found at the `end` of the `linked list`.

4. **Eviction when cache is full**:
    - Remove `tail` of `linked list`.
    - Get `key` from `linked list` **node** and remove `key` from `hash table`.

