package com.backstreetbrogrammer.scratch;

import java.time.LocalDateTime;

public interface JTimerI {

    void schedule(String name, Runnable task, LocalDateTime runTime);

    void cancel(String name);

}
