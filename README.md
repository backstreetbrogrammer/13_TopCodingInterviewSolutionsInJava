# Top Coding Interview Solutions In Java

> Collection of most popular coding interview problems and solutions in Java

---

### Problem 1: Sort and Merge using multithreading

Given an array of N size with random integers. Write a multithreaded program that performs the following operations on
this array:

Thread 1 sorts the even numbers

Thread 2 sorts the odd numbers

Thread 3 merge the results with even numbers in the top part of the array

#### Example:

Assume we have an array [2, 29, 3, 0, 11, 8, 32, 94, 9, 1, 7] of 11 elements.

Thread 1 results [0, 2, 8, 32, 94]

Thread 2 results [1, 3, 7, 9, 11, 29]

Thread 3 results [0, 2, 8, 32, 94, 1, 3, 7, 9, 11, 29]

#### Follow up

Use `CompletableFutures` to solve the same.

---

