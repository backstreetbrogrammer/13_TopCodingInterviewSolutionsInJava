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
4. Implement a Stack in Java
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

Write a program that takes as input a size `k` and read packets, continuously maintaining a uniform random sample osf
size `k` of the read packets.

---

### Problem 3: Deadlock

Suppose there are 2 threads T1 and T2 which need to acquire locks L1 and L2. If T1 first acquires L1 and then T2
acquires L2, they end up waiting on each other forever.

Write a program to demonstrate deadlock issue.

#### Follow up

Modify the program to resolve the deadlock issue.

---

### Problem 4: Stack

A stack is a linear data structure that follows the LIFO - **Last-In, First-Out** principle. That means the objects can
be inserted or removed only at one end of it, also called a **top**. Last object inserted will be the first object to
get.

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

---

### Problem 5: Print odd and even numbers by 2 threads

Write Java code in which the 2 threads, running concurrently, print the numbers from 1 to 100 **in order**.

- Thread 1 prints odd numbers from 1 to 100
- Thread 2 prints even numbers from 1 to 100

---

### Problem 6: Implement UNIX tail command in Java

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



