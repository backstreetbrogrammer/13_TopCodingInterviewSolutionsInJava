# Top Coding Interview Solutions In Java

> Collection of most popular coding interview problems and solutions in Java

## Table of contents

1. Sort and Merge using multithreading
    - using `Thread` / `Runnable`
    - using `CompletableFuture`
2. Random Sampling
    - using `Collections.shuffle()`
    - using `Collections.swap()`
    - online sampling on streaming data
3. Deadlock
    - demonstrate deadlock issue
    - resolve deadlock issue
4. Implement a `Stack` in Java
    - without using Java collections
    - using Java collections
    - bounded concurrent stack using locks
    - concurrent stack using CAS (Atomic classes)
5. Odd-Even printer using multithreading
6. Implement UNIX `tail` command in Java
    - tail on a static file
    - tail on a running file (appended in real-time)
7. False sharing
    - demonstrate false sharing
    - resolve false sharing
8. Implement Read-Write Lock
    - Read Reentrant
    - Write Reentrant
    - Read to Write Reentrant
    - Write to Read Reentrant
    - Fully Reentrant
9. Implement UNIX `find` command in Java

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

---

### Problem 6: Implement UNIX `tail` command in Java

UNIX `tail` command displays the last part of a file on the unix or linux server. Even if the file is being updated
currently, `tail` command will display the latest data appended to the file in real-time. Thus, `tail` command is very
useful in monitoring a running application logs.

Write a program to implement the same `tail` command like method in Java. Assume that tail method takes two arguments -
a filename, and the number of lines, starting from the last line, that are to be printed.

#### Follow up

Implement the `tail` method for a **running** log file (appended in real-time).

---

### Problem 7: False Sharing

In computer science, false sharing is a performance-degrading usage pattern that can arise in systems with distributed,
coherent caches at the size of the smallest resource block managed by the caching mechanism.

False sharing in Java occurs when two threads running on two different CPUs write to two different variables which
happen to be stored within the same CPU cache line. When the first thread modifies one of the variables - the whole CPU
cache line is invalidated in the CPU caches of the other CPU where the other thread is running. This means, that the
other CPUs need to reload the content of the invalidated cache line - even if they don't really need the variable that
was modified within that cache line.

![False Sharing](FalseSharing.PNG)

Write a program to demonstrate false sharing in Java.

#### Follow up

Modify the program to resolve false sharing and improve performance. (Measure the performance)

---

### Problem 8: Implement Read-Write Lock

Suppose there is an `Object obj`, which is **read from** and **written by** many threads.

We need to ensure that no thread may access `obj` for reading or writing while another thread is writing to `obj`.

If no thread is writing to `obj` and no threads have **requested write access**, then multiple threads can **read**
the `obj` at the same time.

Implement the synchronization mechanism which adheres to the above read-write conditions.

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

---

### Problem 9: Implement UNIX `find` command in Java


