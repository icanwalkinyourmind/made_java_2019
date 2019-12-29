package ru.made.ex.four;

import java.util.List;

public class SimpleExecutionStatistics implements ExecutionStatistics {
    private List<Long> tasksExecutionTime;

    SimpleExecutionStatistics(List<Long> tasksExecutionTime) {
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
