package com.backstreetbrogrammer.Q8_Timer;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class JTimer {

    private final String name;
    private final Runnable task;

    private final PriorityQueue<JTask> minHeap = new PriorityQueue<>();
    private final Map<String, JTask> hashTable = new HashMap<>();

    private final Object lock = new Object();
    private final Thread dispatchThread = new Thread(() -> {
        final LocalDateTime now = LocalDateTime.now();
        minHeap.forEach(jTask -> {
            if ((jTask != null)
                    && (jTask.getRunTime() != null)
                    && (jTask.getRunTime().isAfter(now) || jTask.getRunTime().isEqual(now))) {
                jTask.getWorkerThread().start();
                synchronized (lock) {
                    minHeap.remove(jTask);
                }
            }
        });
    });

    public JTimer(final String name, final Runnable task, final LocalDateTime runTime) {
        this.name = name;
        this.task = task;
        final JTask jTask = new JTask(runTime, new Thread(task));
        synchronized (lock) {
            minHeap.add(jTask);
            hashTable.put(name, jTask);
        }

        dispatchThread.start();
    }

    public void schedule(final String name,
                         final Runnable task,
                         final LocalDateTime runTime) {
        //TODO
    }


}
