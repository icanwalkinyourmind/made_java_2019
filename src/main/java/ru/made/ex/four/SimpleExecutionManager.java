package ru.made.ex.four;

import java.util.ArrayList;

public class SimpleExecutionManager implements ExecutionManager {
    @Override
    public Context execute(Runnable... jobs) {
        ArrayList<Task> tasks = new ArrayList<>();
        for (Runnable job : jobs) {
            Task task = new Task(job);
            tasks.add(task);
            task.start();
        }
        return new SimpleContext(tasks);
    }
}
