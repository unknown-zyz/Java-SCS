import java.nio.file.Path;
import java.util.*;

import static java.lang.Math.abs;

public class Task extends Doc{
    private final String taskId;

    private final String taskName;

    private final String startTime;

    private final String endTime;

    private final Path path;

    private Path answer;
    private final TreeMap<String,Homework> homework_task = new TreeMap<>();
    public String getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public Path getPath() {
        return path;
    }

    public Task(String taskId, String taskName, String startTime, String endTime, Path path) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.path = path;
    }

    public Homework findHomework(String studentId)
    {
        return homework_task.get(studentId);
    }

    public int getHomeworkSize()
    {
        return homework_task.size();
    }

    public Path getAnswer() {return answer;}

    public void setAnswer(Path answer) throws Exception{
        this.answer = answer;
        for (String ID: homework_task.keySet()) {
            homework_task.get(ID).judge(answer);
        }
    }
    public void submitHomework(String studentId, Path path) throws Exception{
        homework_task.put(studentId, new Homework(path));
        if (answer != null)
            homework_task.get(studentId).judge(answer);
    }

    Comparator<StudentTaskScore> comparator = new Comparator<StudentTaskScore>() {
        @Override
        public int compare(StudentTaskScore o1, StudentTaskScore o2) {
            if (abs(o1.getScore() - o2.getScore()) < 1e-7) {
                return o1.getStudent().getId().compareTo(o2.getStudent().getId());
            }
            if (o1.getScore() > o2.getScore()) return -1;
            return 1;
        }
    };

    public ArrayList<StudentTaskScore> queryScore(User student) {
       ArrayList<StudentTaskScore> studentTaskScores = new ArrayList<>();
        if (student != null) {
            Homework hw =homework_task.get(student.getId());
            if (hw == null) return studentTaskScores;
            studentTaskScores.add(new StudentTaskScore(student, taskId, hw.getHighestScore()));
            return studentTaskScores;
        }
        for (String studentID: homework_task.keySet()) {
            Homework hw = homework_task.get(studentID);
            studentTaskScores.add(new StudentTaskScore(Option.course_select.student.get(studentID), taskId, hw.getHighestScore()));
        }
        Collections.sort(studentTaskScores, comparator);
        return studentTaskScores;
    }
}
