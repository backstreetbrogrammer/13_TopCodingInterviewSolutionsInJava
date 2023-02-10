package com.backstreetbrogrammer.Q3_Deadlock;

public class Account {
    private static int counter;

    private double balance;
    private int id;

    public Account(final double balance) {
        this.balance = balance;
        id = ++counter;
    }

    private boolean transfer(final Account to, final double amount) {
        synchronized (this) {
            synchronized (to) {
                if (amount > balance) {
                    return false;
                }
                to.balance += amount;
                this.balance -= amount;
                System.out.println("Transfer successful!!");
                return true;
            }
        }
    }

    private boolean transferDeadlockFixed(final Account to, final double amount) {
        final var lock1 = (id < to.id) ? this : to;
        final var lock2 = (id < to.id) ? to : this;
        synchronized (lock1) {
            synchronized (lock2) {
                if (amount > balance) {
                    return false;
                }
                to.balance += amount;
                this.balance -= amount;
                System.out.println("Transfer successful!!");
                return true;
            }
        }
    }

    public static void transfer(final Account from, final Account to, final double amount) {
        new Thread(() -> from.transfer(to, amount)).start();
    }

    public static void transferDeadlockFixed(final Account from, final Account to, final double amount) {
        new Thread(() -> from.transferDeadlockFixed(to, amount)).start();
    }
}
