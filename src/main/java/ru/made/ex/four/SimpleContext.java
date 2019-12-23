package ru.made.ex.four;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


class Callback extends Thread {
    private Context context;
    private Runnable job;

    Callback(Context context, Runnable job) {
        this.context = context;
        this.job = job;
    }

    @Override
    public void run() {
        while (true) {
            if (Thread.interrupted()) {
                return;
            }
            if (context.isFinished()) {
                job.run();
                return;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}


public class SimpleContext implements Context {
    private List<Task> executingTasks;

    SimpleContext(List<Task> tasks) {
        executingTasks = tasks;
    }

    private int calcTasksWithCondition(Predicate<Task> condition) {
        int numOfTasks = 0;
        for (Task t : executingTasks) {
            if (condition.test(t)) numOfTasks += 1;
        }
        return numOfTasks;
    }

    @Override
    public int getCompletedTaskCount() {
        return calcTasksWithCondition(Task::isFinished);
    }

    @Override
    public int getFailedTaskCount() {
        return calcTasksWithCondition(Task::isFailed);
    }

    @Override
    public int getInterruptedTaskCount() {
        return calcTasksWithCondition(Thread::isInterrupted);
    }

    @Override
    public void interrupt() {
        for (Task task : executingTasks) {
            if (!task.isFailed() & !task.isInterrupted()) {
                task.interrupt();
            }
        }
    }

    @Override
    public boolean isFinished() {
        return getCompletedTaskCount() + getInterruptedTaskCount() == executingTasks.size();
    }

    @Override
    public void onFinish(Runnable callback) {
        Callback cb = new Callback(this, callback);
        cb.start();
    }

    @Override
    public ExecutionStatistics getStatistics() {
        return new SimpleExecutionStatistics(
                executingTasks.stream()
                        .filter(Task::isFinished)
                        .map(task -> task.executionTime)
                        .collect(Collectors.toList()));
    }

    @Override
    public void awaitTermination() {
        for (Task task : executingTasks) {
            if (!task.isFinished() && !task.isInterrupted()) {
                try {
                    task.join();
                } catch (InterruptedException ignored) {
                }
            }
        }
    }
}