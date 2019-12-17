package ru.made.ex.four;

import java.util.ArrayList;

public class SimpleExecutionStatistics implements ExecutionStatistics {
    private ArrayList<Long> tasksExecutionTime;

    SimpleExecutionStatistics(ArrayList<Long> tasksExecutionTime) {
        this.tasksExecutionTime = tasksExecutionTime;
    }

    @Override
    public long getMinExecutionTimeInMs() {
        return tasksExecutionTime.stream().min(Long::compareTo).get();
    }

    @Override
    public long getMaxExecutionTimeInMs() {
        return tasksExecutionTime.stream().max(Long::compareTo).get();
    }

    @Override
    public double getAverageExecutionTimeInMs() {
        return tasksExecutionTime.stream().mapToLong((Long v) -> v).average().getAsDouble();
    }
}
