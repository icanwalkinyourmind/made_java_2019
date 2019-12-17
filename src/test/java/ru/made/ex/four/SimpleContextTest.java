package ru.made.ex.four;


import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SimpleContextTest {
    public static Context setupContext(Runnable doSomething) {
        ArrayList<Runnable> jobs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final int j = i;
            jobs.add(doSomething);
        }
        ArrayList<Task> tasks = new ArrayList<>();
        for (Runnable job : jobs) {
            Task task = new Task(job);
            tasks.add(task);
            task.start();
        }
        return new SimpleContext(tasks);
    }

    @Test
    public void getCompletedTaskCount() {
        Context context = setupContext(() -> {
            System.out.print("");
        });
        context.awaitTermination();
        assertEquals(10, context.getCompletedTaskCount());
    }

    @Test
    public void getFailedTaskCount() {
        Context context = setupContext(() -> {
            throw new java.lang.RuntimeException();
        });
        context.awaitTermination();
        assertEquals(10, context.getCompletedTaskCount());
    }

    @Test
    public void testInterruption() {
        Context context = setupContext(() -> {
            System.out.println("");
        });
        context.interrupt();
        assertTrue(context.getInterruptedTaskCount() > 0);
    }

    @Test
    public void isFinished() {
        Context context = setupContext(() -> {
            System.out.print("");
        });
        context.awaitTermination();
        assertTrue(context.isFinished());
    }

    @Test
    public void onFinish() {
        final ArrayList<Integer> list = new ArrayList<>();
        Context context = setupContext(() -> {
        });
        context.onFinish(() -> list.add(1));
        context.awaitTermination();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
        assertEquals(1, list.size());
    }
}
