package coursework;

import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RobotController {
	// maximum sizes of task and result queues
    private static final int MAX_TASK_QUEUE_SIZE = 10;
    private static final int MAX_RESULT_QUEUE_SIZE = 10;
    // number of sensor threads to create (any number works)
    private static final int NUMBER_OF_SENSORS = 3; 

    // for holding tasks and analysis results
    private BlockingQueue<Task> taskQueue;
    private BlockingQueue<AnalysisResult> resultQueue;
    // executors to manage sensor, analyzer, actuator threads
    private ExecutorService sensorExec;
    private ExecutorService analyzerExec;
    private ExecutorService actuatorExec;
    // global task ID generator (necessary if there's more than 1 sensor)
    private AtomicInteger taskIdGen = new AtomicInteger(1);

    public RobotController() {
    	// initialize queues
        taskQueue = new ArrayBlockingQueue<>(MAX_TASK_QUEUE_SIZE);
        resultQueue = new ArrayBlockingQueue<>(MAX_RESULT_QUEUE_SIZE);
        // and executors
        sensorExec = Executors.newFixedThreadPool(NUMBER_OF_SENSORS);
        analyzerExec = Executors.newSingleThreadExecutor();
        actuatorExec = Executors.newSingleThreadExecutor();
    }

    public void start(double initialPosition, double lambda) {
    	// start sensors with unique IDs 
        for (int i = 1; i <= NUMBER_OF_SENSORS; i++) {
            sensorExec.submit(new Sensor(i, taskQueue, taskIdGen, lambda));
        }
    	// start analyzer and actuator
        analyzerExec.submit(new Analyzer(taskQueue, resultQueue));
        actuatorExec.submit(new Actuator(resultQueue, initialPosition));
    }

    public static void main(String[] args) {
    	// handles user input for robot starting position and lambda
        try (Scanner scanner = new Scanner(System.in)) {
            double initialPos = -1.0; // out of range initial position so it keeps asking user for the correct position

            while (initialPos < 0.0 || initialPos > 1.0) {
                System.out.print("Enter the initial position of the robot (between 0.0 and 1.0): ");
                initialPos = scanner.nextDouble();

                if (initialPos < 0.0 || initialPos > 1.0) {
                    System.out.println("Invalid position. Please enter a value between 0.0 and 1.0.");
                }
            }

            System.out.print("Enter the lambda value for the Poisson distribution: ");
            double lambda = scanner.nextDouble();

            RobotController controller = new RobotController();
            controller.start(initialPos, lambda);
        }
    }
}