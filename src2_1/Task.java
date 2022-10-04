public class Task {
    private final String taskId;

    private final String taskName;

    private int receiveNum;

    private final String startTime;

    private final String endTime;

    public String getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public int getReceiveNum() {
        return receiveNum;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public Task(String taskId, String taskName, String startTime, String endTime) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
