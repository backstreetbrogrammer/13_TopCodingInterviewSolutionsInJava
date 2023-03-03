package com.backstreetbrogrammer.scratch;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

public class JTimer implements JTimerI {

    private final PriorityQueue<JTask> minHeap = new PriorityQueue<>();
    private final Map<String, JTask> hashTable = new HashMap<>();

    private final Object lock = new Object();
    private final Thread dispatchThread = new Thread(() -> {
        if (!minHeap.isEmpty()) {
            JTask headOfHeapJTask;
            synchronized (lock) {
                headOfHeapJTask = minHeap.peek();
            }
            final var headOfHeapRunTime = headOfHeapJTask.getRunTime();
            final LocalDateTime now = LocalDateTime.now();
            if (headOfHeapRunTime != null) {
                if (now.isBefore(headOfHeapRunTime)) {
                    // let the dispatcher thread sleep
                    final var millis = ChronoUnit.MILLIS.between(now, headOfHeapRunTime);
                    try {
                        TimeUnit.MILLISECONDS.sleep(millis);
                    } catch (final InterruptedException e) {
                        // swallow the interrupt
                        System.out.println("Dispatcher thread interrupted");
                    }
                } else {
                    // its already past the schedule time - run the task
                    runTheTask(headOfHeapJTask);
                }
            }
        }
    });

    private void runTheTask(final JTask jTask) {
        if (!jTask.isRunning()) {
            jTask.start();
            jTask.join();
            synchronized (lock) {
                minHeap.remove(jTask);
                hashTable.remove(jTask.getThreadName());
            }
        }
    }

    @Override
    public void schedule(final String name,
                         final Runnable task,
                         final LocalDateTime runTime) {
        final JTask jTask = new JTask(runTime, task, name);
        synchronized (lock) {
            minHeap.add(jTask);
            hashTable.put(name, jTask);
        }

        // whenever there is addition - check the minHeap head
        dispatchThread.interrupt();
        dispatchThread.start();
    }

    @Override
    public void cancel(final String name) {
        synchronized (lock) {
            if (hashTable.containsKey(name)) {
                final var jTaskToRemove = hashTable.remove(name);
                jTaskToRemove.interrupt();
                jTaskToRemove.stop();
                dispatchThread.interrupt();
                minHeap.remove(jTaskToRemove);
            }
        }
    }

}
