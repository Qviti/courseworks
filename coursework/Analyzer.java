package coursework;

import java.util.concurrent.BlockingQueue;

public class Analyzer implements Runnable {
    private final BlockingQueue<Task> taskQueue;
    private final BlockingQueue<AnalysisResult> resultQueue;

    public Analyzer(BlockingQueue<Task> taskQueue, BlockingQueue<AnalysisResult> resultQueue) {
        this.taskQueue = taskQueue;
        this.resultQueue = resultQueue;
    }

    public void run() {
        while (true) {
            try {
                // take task from task queue
                Task task = taskQueue.take();

                // analyze
                AnalysisResult result = analyzeTask(task);

                // place result into result queue
                resultQueue.put(result);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Analyzer interrupted.");
                break;
            }
        }
    }

    private AnalysisResult analyzeTask(Task task) throws InterruptedException {
        // calculate result based on complexity
        double complexity = task.getComplexity();
        double result = Math.sqrt(1.0 / complexity);

        // simulate analysis time
        Thread.sleep((long) (complexity * 1000));

        return new AnalysisResult(task.getSensorId(), task.getTaskId(), complexity, result);
    }
}