package ru.made.ex.four;

public interface ExecutionStatistics {
    long getMinExecutionTimeInMs(); // минимальное время выполнения среди тасков в миллисекундах
    long getMaxExecutionTimeInMs(); // максимальное время выполнения среди тасков в миллисекундах
    double getAverageExecutionTimeInMs(); //среднее арифметическое время выполнения тасков в миллисекундах.
}
