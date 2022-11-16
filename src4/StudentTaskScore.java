public class StudentTaskScore {
    private final User student;

    private final String taskId;

    private final double score;

    public StudentTaskScore(User student, String taskId,double score) {
        this.student = student;
        this.taskId = taskId;
        this.score = score;
    }

    public User getStudent() {
        return student;
    }

    public double getScore() {
        return score;
    }

    @Override
    public String toString() {
        if(score==-1)
            return "[ID:"+student.getId()+"] "+
                "[Name:"+student.getSurname()+" "+student.getName()+"] "+
                "[Task_ID:"+taskId+"] "+
                "[Score:"+"None]";
        else
            return "[ID:"+student.getId()+"] "+
                    "[Name:"+student.getSurname()+" "+student.getName()+"] "+
                    "[Task_ID:"+taskId+"] "+
                    "[Score:"+CourseOption.floatFormat(score)+"]";
    }
}
