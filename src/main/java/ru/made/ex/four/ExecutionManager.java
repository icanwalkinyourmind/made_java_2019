package ru.made.ex.four;

public interface ExecutionManager {
    Context execute(Runnable... tasks);
}

