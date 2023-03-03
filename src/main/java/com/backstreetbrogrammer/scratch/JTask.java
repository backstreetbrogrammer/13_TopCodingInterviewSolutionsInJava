package com.backstreetbrogrammer.scratch;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class JTask implements Comparable<JTask> {

    private final LocalDateTime runTime;
    private final Thread workerThread;

    private final AtomicBoolean running = new AtomicBoolean(false);
    private final AtomicBoolean stopped = new AtomicBoolean(true);

    public JTask(final LocalDateTime runTime, final Runnable runnableTask, final String threadName) {
        this.runTime = runTime;
        this.workerThread = new Thread(() -> {
            running.set(true);
            stopped.set(false);
            while (running.get()) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1L);
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Thread was interrupted, Failed to complete operation");
                }
                runnableTask.run(); // this is the actual run method for task submitted
            }
            stopped.set(true);
        }, threadName);
    }

    public LocalDateTime getRunTime() {
        return runTime;
    }

    public String getThreadName() {
        return workerThread.getName();
    }

    public void interrupt() {
        running.set(false);
        workerThread.interrupt();
    }

    public boolean isRunning() {
        return running.get();
    }

    public boolean isStopped() {
        return stopped.get();
    }

    public void start() {
        workerThread.start();
    }

    public void join() {
        try {
            workerThread.join();
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        running.set(false);
    }

    @Override
    public int compareTo(final JTask o) {
        return runTime.compareTo(o.runTime);
    }
}
