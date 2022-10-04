import java.util.TreeMap;

public class Course {
    private final String courseId;
    private final int courseNumber;
    private final String courseName;
    TreeMap<String, User> teacher = new TreeMap<>();
    TreeMap<String, User> assistant = new TreeMap<>();
    TreeMap<String, User> student = new TreeMap<>();

    TreeMap<String, Ware> ware_course = new TreeMap<>();

    TreeMap<String, Task> task_course = new TreeMap<>();

    public Course(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseNumber = Integer.parseInt(courseId.substring(1));
        this.teacher.put(Option.user_online.getId(), Option.user_online);
    }

    public String getCourseId() {
        return courseId;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getTeacherSize() {
        return teacher.size();
    }

    public int getAssistantSize() {
        return assistant.size();
    }

    public int getStudentSize() {
        return student.size();
    }

    public boolean findTeacherId(String id) {
        return teacher.get(id) != null;
    }

    public boolean findAdminId(String id) {
        return teacher.get(id) != null || assistant.get(id) != null;
    }

    public boolean findStudentId(String id) {
        return student.get(id) != null;
    }

    public void printAdmin() {
        for (String key : teacher.keySet()) {
            User user_print = teacher.get(key);
            System.out.print("[ID:" + user_print.getId() + "] ");
            System.out.print("[Name:" + user_print.getSurname() +" "+ user_print.getName() + "] ");
            System.out.print("[Type:Professor] ");
            System.out.println("[Email:" + user_print.getEmail() + "] ");
        }
        for (String key : assistant.keySet()) {
            User user_print = assistant.get(key);
            System.out.print("[ID:" + user_print.getId() + "] ");
            System.out.print("[Name:" + user_print.getSurname() + " "+user_print.getName() + "] ");
            System.out.print("[Type:Assistant] ");
            System.out.println("[Email:" + user_print.getEmail() + "] ");
        }
    }

    public void printStudent() {
        for (String key : student.keySet()) {
            User user_print = student.get(key);
            System.out.print("[ID:" + user_print.getId() + "] ");
            System.out.print("[Name:" + user_print.getSurname() +" "+ user_print.getName() + "] ");
            System.out.println("[Email:" + user_print.getEmail() + "] ");
        }
    }

    public boolean findWareId(String id) {
        return ware_course.get(id) != null;
    }

    public void printWare() {
        for (String key : ware_course.keySet()) {
            Ware ware_print = ware_course.get(key);
            System.out.print("[ID:" + ware_print.getWareId() + "] ");
            System.out.println("[Name:" + ware_print.getWareName() + "] ");
        }
    }

    public boolean findTaskId(String id) {
        return task_course.get(id) != null;
    }

    public void printTask() {
        for (String key : task_course.keySet()) {
            Task task_print = task_course.get(key);
            System.out.print("[ID:" + task_print.getTaskId() + "] ");
            System.out.print("[Name:" + task_print.getTaskName() + "] ");
            System.out.print("[ReceiveNum:" + task_print.getReceiveNum() + "] ");
            System.out.print("[StartTime:" + task_print.getStartTime() + "] ");
            System.out.println("[EndTime:" + task_print.getEndTime() + "] ");
        }
    }
}
