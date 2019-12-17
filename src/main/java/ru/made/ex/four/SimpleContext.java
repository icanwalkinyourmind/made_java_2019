package ru.made.ex.four;

import java.util.ArrayList;
import java.util.function.Function;


class Callback extends Thread {
    private Context context;
    private Runnable job;

    Callback(Context context, Runnable job) {
        super();
        this.context = context;
        this.job = job;
    }

    @Override
    public void run() {
        while (true) {
            if (!Thread.interrupted()) {
                if (context.isFinished()) {
                    job.run();
                    return;
                }
            } else {
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
    private ArrayList<Task> executingTasks;

    SimpleContext(ArrayList<Task> tasks) {
        executingTasks = tasks;
    }

    private int calcTasksWithCondition(Function<Task, Boolean> condition) {
        int numOfTasks = 0;
        for (Task t : executingTasks) {
            if (condition.apply(t)) numOfTasks += 1;
        }
        return numOfTasks;
    }

    @Override
    public int getCompletedTaskCount() {
        return calcTasksWithCondition((Task task) -> task.isFinished);
    }

    @Override
    public int getFailedTaskCount() {
        return calcTasksWithCondition((Task task) -> task.isFailed);
    }

    @Override
    public int getInterruptedTaskCount() {
        return calcTasksWithCondition(Thread::isInterrupted);
    }

    @Override
    public void interrupt() {
        for (Task task : executingTasks) {
            if (!task.isFinished & !task.isInterrupted()) {
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
        ArrayList<Long> finishedTasksExecutionTime = new ArrayList<>();
        for (Task task : executingTasks) {
            if (task.isFinished) {
                finishedTasksExecutionTime.add(task.executionTime);
            }
        }
        return new SimpleExecutionStatistics(finishedTasksExecutionTime);
    }

    @Override
    public void awaitTermination() {
        for (Task task : executingTasks) {
            if (!task.isFinished && !task.isInterrupted()) {
                try {
                    task.join();
                } catch (InterruptedException ignored) {
                }
            }
        }
    }
}