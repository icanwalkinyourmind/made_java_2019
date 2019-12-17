package ru.made.ex.four;


class Task extends Thread {
    public volatile long executionTime = 0;
    public volatile boolean isFinished = false;
    public volatile boolean isFailed = false;
    private Runnable job;

    public Task(Runnable job) {
        super();
        this.job = job;
    }

    @Override
    public void run() {
        try {
            long start = System.currentTimeMillis();
            job.run();
            executionTime = System.currentTimeMillis() - start;
        } catch (Exception e) {
            isFailed = true;
        }
        isFinished = true;
    }
}