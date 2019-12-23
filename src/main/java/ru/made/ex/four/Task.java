package ru.made.ex.four;


class Task extends Thread {
    public volatile long executionTime = 0;
    private volatile boolean finished = false;
    private volatile boolean failed = false;
    private Runnable job;

    public Task(Runnable job) {
        this.job = job;
    }

    @Override
    public void run() {
        try {
            long start = System.currentTimeMillis();
            job.run();
            executionTime = System.currentTimeMillis() - start;
        } catch (Exception e) {
            failed = true;
        }
        finished = true;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isFailed() {
        return failed;
    }
}