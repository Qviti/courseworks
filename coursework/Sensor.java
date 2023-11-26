package coursework;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;

public class Sensor implements Runnable {
	// id for sensors
    private final int sensorId;
    // queue for storing tasks
    private final BlockingQueue<Task> taskQueue;
    // for generating unique task ids
    private final AtomicInteger taskIdGenerator;
    // lambda for poisson delay
    private final double lambda;
    // RNG for poisson and complexity
    private final Random random = new Random();
    //constructor for sensor
    public Sensor(int sensorId, BlockingQueue<Task> taskQueue, AtomicInteger taskIdGenerator, double lambda) {
        this.sensorId = sensorId;
        this.taskQueue = taskQueue;
        this.taskIdGenerator = taskIdGenerator;
        this.lambda = lambda;
    }
    // main run method for sensor
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                int taskId = taskIdGenerator.getAndIncrement();
                double complexity = 0.1 + random.nextDouble() * 0.4;
                Task task = new Task(sensorId, taskId, complexity);
                taskQueue.put(task);
                
                //sleep based on poisson
                long delay = getPoissonDelay();
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Sensor " + sensorId + " interrupted.");
            }
        }
    }

    // calculate delay based on poisson distribution
    private long getPoissonDelay() {
        double p = 1.0;
        double L = Math.exp(-lambda);
        int k = 0;

        do {
            k++;
            p *= random.nextDouble(); 				
        } while (p > L);

        return (k - 1) * 1000L; 					// convert into time delay
    }
}