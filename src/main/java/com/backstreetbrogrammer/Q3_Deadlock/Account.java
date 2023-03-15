package com.backstreetbrogrammer.Q3_Deadlock;

public class Account {
    private static int counter;

    private final int id;
    private double balance;

    public Account(final double balance) {
        this.balance = balance;
        id = ++counter;
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                ", id=" + id +
                '}';
    }

    private void printAcquire(final Thread thread, final Account lock) {
        System.out.printf("%s acquired lock: %s%n", thread, lock);
    }

    private void printRelease(final Thread thread, final Account lock) {
        System.out.printf("%s released lock: %s%n", thread, lock);
    }

    private void transfer(final Account to, final double amount) {
        synchronized (this) {
            printAcquire(Thread.currentThread(), this);
            synchronized (to) {
                printAcquire(Thread.currentThread(), to);
                if (amount > balance) {
                    System.err.println("Transfer NOT successful");
                    return;
                }
                to.balance += amount;
                this.balance -= amount;
                System.out.println("Transfer successful!!");
                printRelease(Thread.currentThread(), to);
            }
            printRelease(Thread.currentThread(), this);
        }
    }

    private void transferDeadlockFixed(final Account to, final double amount) {
        final var lock1 = (id < to.id) ? this : to;
        final var lock2 = (id < to.id) ? to : this;
        synchronized (lock1) {
            printAcquire(Thread.currentThread(), lock1);
            synchronized (lock2) {
                printAcquire(Thread.currentThread(), lock2);
                if (amount > balance) {
                    System.err.println("Transfer NOT successful");
                    return;
                }
                to.balance += amount;
                this.balance -= amount;
                System.out.println("Transfer successful!!");
                printRelease(Thread.currentThread(), lock2);
            }
            printRelease(Thread.currentThread(), lock1);
        }
    }

    public static void transfer(final Account from, final Account to, final double amount) {
        new Thread(() -> from.transfer(to, amount)).start();
    }

    public static void transferDeadlockFixed(final Account from, final Account to, final double amount) {
        new Thread(() -> from.transferDeadlockFixed(to, amount)).start();
    }
}
