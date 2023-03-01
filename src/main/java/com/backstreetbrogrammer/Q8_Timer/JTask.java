package com.backstreetbrogrammer.Q8_Timer;

import java.time.LocalDateTime;
import java.util.Comparator;

public class JTask implements Comparator<JTask> {

    private final LocalDateTime runTime;
    private final Thread workerThread;

    public JTask(final LocalDateTime runTime, final Thread workerThread) {
        this.runTime = runTime;
        this.workerThread = workerThread;
    }

    public LocalDateTime getRunTime() {
        return runTime;
    }

    public Thread getWorkerThread() {
        return workerThread;
    }

    @Override
    public int compare(final JTask jt1, final JTask jt2) {
        return jt1.runTime.compareTo(jt2.runTime);
    }
}
