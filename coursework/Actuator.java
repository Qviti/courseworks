package coursework;

import java.util.concurrent.BlockingQueue;

public class Actuator implements Runnable {
    private final BlockingQueue<AnalysisResult> resultQueue;
    private double pos; 				//position. self-explanatory
    @SuppressWarnings("unused")
	private int dir = 1; 				// direction. 1 = moving right, -1 = moving left

    public Actuator(BlockingQueue<AnalysisResult> resultQueue, double initialPosition) {
        this.resultQueue = resultQueue;
        this.pos = initialPosition;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                AnalysisResult result = resultQueue.take();

                // process analysis result
                double oldPos = pos;						//oldPos = old position
                double movement = result.getResult();
                
                // apply movement and bounce (if applicable)
                pos = iCouldBeFishingRightNow(pos, movement);

                // output the info (and sensor id)
                System.out.printf("Robot moving. Sensor id %d, Task id %d, task complexity %.2f, result %.2f, old position: %.2f, new position: %.2f%n",
                                  result.getSensorId(), result.getTaskId(), result.getComplexity(), result.getResult(), oldPos, pos);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Actuator interrupted.");
            }
        }
    }

    private double iCouldBeFishingRightNow(double position, double movement) {
        position += movement;
        while (position < 0 || position > 1) {
            if (position > 1) {
                position = 2 - position; // reflect the position back into the range
                dir = -1;
            } else if (position < 0) {
                position = -position; // reflect the position back into the range
                dir = 1;
            }
        }
        return position;
    }
}