package coursework;

public class AnalysisResult {
    private final int sensorId;
    private final int taskId;
    private final double complexity;
    private final double result;
    
    // analysisResult constructor
    public AnalysisResult(int sensorId, int taskId, double complexity, double result) {
        this.sensorId = sensorId;
        this.taskId = taskId;
        this.complexity = complexity;
        this.result = result;
    }
    // returns task id
    public int getTaskId() {
        return taskId;
    }
    // returns sensor id
    public int getSensorId() {
    	return sensorId;
    }
    // returns complexity
    public double getComplexity() {
        return complexity;
    }
    // returns result
    public double getResult() {
        return result;
    }
    // converts the whole thing into a string format 
    public String toString() {
        return "AnalysisResult{" + "taskId=" + taskId + ", complexity=" + complexity + ", result=" + result + '}';
    }
}
