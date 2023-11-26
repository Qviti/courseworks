package coursework;

public class Task {
    private final int sensorId;
    private final int taskId;
    private final double complexity;

    public Task(int sensorId, int taskId, double complexity) {
        this.sensorId = sensorId;
        this.taskId = taskId;
        this.complexity = complexity;
    }

    public int getSensorId() {
        return sensorId;
    }

    public int getTaskId() {
        return taskId;
    }

    public double getComplexity() {
        return complexity;
    }
}