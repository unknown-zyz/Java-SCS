import java.util.HashMap;
import java.util.TreeMap;

public class User {
    private final String id;
    private final String name;//名
    private final String surname;//姓
    private final String email;
    private final String pwd;
    boolean role_admin;//!!!
    private int job;//本科un1 SY2 ZY3 BY4 老师0

    HashMap<Course,VirtualMachine> vm_student = new HashMap<>();
    TreeMap<String, Course> course_admin = new TreeMap<>();
    TreeMap<String, Course> course_learn = new TreeMap<>();

    public int getCourse_AdminSize() {
        return course_admin.size();
    }

    public void printCourse_Admin() {
        for (String key : course_admin.keySet()) {
            Course course_print = course_admin.get(key);
            System.out.print("[ID:" + course_print.getCourseId() + "] ");
            System.out.print("[Name:" + course_print.getCourseName() + "] ");
            System.out.print("[TeacherNum:" + course_print.getTeacherSize() + "] ");
            System.out.print("[AssistantNum:" + course_print.getAssistantSize() + "] ");
            System.out.println("[StudentNum:" + course_print.getStudentSize() + "] ");
        }
    }
    public VirtualMachine getVM(Course course)
    {
        return vm_student.get(course);
    }

    public String getId() {
        return id;
    }

    public int getJob() {
        return job;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setJob(int job) {
        this.job = job;
    }

    public boolean hasAdmin() {          //未判断当前为助教还是学生
        return course_admin.size() > 0;
    }

    public User(String id, String name, String surname, String email, String pwd) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.pwd = pwd;

    }

    public String toString() {
        return "Name: " + name + " " + surname + "\n" +
                "ID: " + id + '\n' +
                "Type: " + ((job == 0) ? "Professor" : "Student") + '\n' +
                "Email: " + email;
    }
}
