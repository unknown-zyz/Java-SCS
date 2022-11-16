import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.TreeMap;

public class CourseOption {
    static TreeMap<String, Course> course_all = new TreeMap<>();
    static int[] normal_year = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    static int[] leap_year = {0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    static final boolean SUBMIT_TASK = false;
    static final boolean ADD_ANSWER = true;

    static boolean is_LeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    static boolean courseId_illegal(String courseId) {
        if (!courseId.matches("C\\d{4}"))
            return true;
        int courseYear = Integer.parseInt(courseId.substring(1, 3));
        int courseNum = Integer.parseInt(courseId.substring(3, 5));
        return courseYear < 17 || courseYear > 22 || courseNum < 1 || courseNum > 99;
    }

    static boolean courseId_haveRegistered(String courseId) {
        return course_all.get(courseId) != null;
    }

    static boolean courseId_notExist(String courseId) {
        return !courseId_haveRegistered(courseId) || (!course_all.get(courseId).findAdminId(Option.user_online.getId()) && !course_all.get(courseId).findStudentId(Option.user_online.getId()));
    }

    static boolean courseName_illegal(String courseName) {
        return !courseName.matches("\\w{6,16}");
    }

    static boolean wareId_illegal(String wareId) {
        if (!wareId.matches("W\\d{6}"))
            return true;
        int courseNum = Integer.parseInt(wareId.substring(1, 5));
        int wareNum = Integer.parseInt(wareId.substring(5, 7));
        return courseNum != Option.course_select.getCourseNumber() || wareNum < 1;
    }

    static boolean wareId_haveRegistered(String wareId) {
        return Option.course_select.ware_course.get(wareId) != null;
    }

    static boolean wareName_illegal(String wareName) {
        return !(wareName.matches("\\w+\\.[a-zA-Z\\d]+") && wareName.length() >= 6 && wareName.length() <= 16);
    }

    static boolean taskId_illegal(String taskId) {
        if (!taskId.matches("T\\d{6}"))
            return true;
        int courseNum = Integer.parseInt(taskId.substring(1, 5));
        int taskNum = Integer.parseInt(taskId.substring(5, 7));
        return courseNum != Option.course_select.getCourseNumber() || taskNum < 1;
    }

    static boolean taskId_haveRegistered(String taskId) {
        return Option.course_select.task_course.get(taskId) != null;
    }

    static boolean taskName_illegal(String wareName) {
        return !(wareName.matches("\\w+\\.[a-zA-Z\\d]+") && wareName.length() >= 6 && wareName.length() <= 16);
    }

    static boolean time_illegal(String time) {
        if (!time.matches("\\d{4}-\\d{2}-\\d{2}-\\d{2}:\\d{2}:\\d{2}"))
            return true;
        int year = Integer.parseInt(time.substring(0, 4));
        int month = Integer.parseInt(time.substring(5, 7));
        int date = Integer.parseInt(time.substring(8, 10));
        int hour = Integer.parseInt(time.substring(11, 13));
        int minute = Integer.parseInt(time.substring(14, 16));
        int second = Integer.parseInt(time.substring(17, 19));
        if (year >= 1900 && year <= 9999 && month >= 1 && month <= 12 && hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59 && second >= 0 && second <= 59) {
            if (is_LeapYear(year))
                return !(date >= 1 && date <= leap_year[month]);
            else
                return !(date >= 1 && date <= normal_year[month]);
        } else
            return true;
    }

    static boolean taskTime_illegal(String startTime, String endTime) {
        return time_illegal(startTime) || time_illegal(endTime) || startTime.compareTo(endTime) >= 0;
    }

    public static void redirectOutput(String content, String targetStr, boolean append) throws Exception {
        File target = new File(targetStr);
        if (!target.exists()) target.createNewFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(target, append));
        bw.write(content.trim() + "\n");
        bw.close();
    }

    public static String floatFormat(double val) {
        if (val < 0) return "None";
        return String.format("%.1f", val);
    }

    static void addCourse(String courseId, String courseName) {
        if (Option.user_online == null)
            System.out.println("not logged in");
        else if (Option.user_online.getJob() != 0)
            System.out.println("permission denied");
        else if (courseId_illegal(courseId))
            System.out.println("course id illegal");
        else if (courseId_haveRegistered(courseId))
            System.out.println("course id duplication");
        else if (courseName_illegal(courseName))
            System.out.println("course name illegal");
        else {
            Course course = new Course(courseId, courseName);
//course_teacher添加移到构造函数
            course_all.put(course.getCourseId(), course);
            Option.user_online.course_admin.put(course.getCourseId(), course);
            System.out.println("add course success");
        }
    }

    static void removeCourse(String courseId) {
        if (Option.user_online == null)
            System.out.println("not logged in");
        else if (Option.user_online.getJob() != 0)
            System.out.println("permission denied");
        else if (courseId_illegal(courseId))
            System.out.println("course id illegal");
        else if (!(courseId_haveRegistered(courseId) && course_all.get(courseId).findTeacherId(Option.user_online.getId())))
            System.out.println("course id not exist");
        else {
            course_all.remove(courseId);
            for (String key : Option.user_all.keySet()) {
                User user_remove = Option.user_all.get(key);
                user_remove.course_admin.remove(courseId);
                user_remove.course_learn.remove(courseId);
            }
//            Option.course_select = null;
            System.out.println("remove course success");
        }
    }

    static void listCourse() {
        boolean flag = false;
        if (Option.user_online == null) {
            System.out.println("not logged in");
            return;
        } else if (Option.user_online.role_admin) {
            for (String key : course_all.keySet()) {
                Course course_print = course_all.get(key);
                if (course_print.findAdminId(Option.user_online.getId())) {
                    System.out.println(course_print);
                    flag = true;
                }
            }
        } else {
            for (String key : course_all.keySet()) {
                Course course_print = course_all.get(key);
                if (course_print.findStudentId(Option.user_online.getId())) {
                    System.out.println(course_print);
                    flag = true;
                }
            }
        }
        if (!flag)
            System.out.println("course not exist");
    }

    static void selectCourse(String courseId) {
        if (Option.user_online == null)
            System.out.println("not logged in");
        else if (courseId_illegal(courseId))
            System.out.println("course id illegal");
        else if (courseId_notExist(courseId))
            System.out.println("course id not exist");
        else {
            Option.course_select = course_all.get(courseId);
            System.out.println("select course success");
        }
    }

    static void addAdmin(String[] adminId) {
        if (Option.user_online == null)
            System.out.println("not logged in");
        else if (Option.user_online.getJob() != 0)
            System.out.println("permission denied");
        else if (Option.course_select == null)
            System.out.println("no course selected");
        else {
            for (String id : adminId) {
                if (Option.id_illegal(id)) {
                    System.out.println("user id illegal");
                    return;
                }
                if (!Option.id_haveRegistered(id)) {
                    System.out.println("user id not exist");
                    return;
                }
            }
            for (String id : adminId) {
                User user_admin = Option.user_all.get(id);
                if (id.length() == 5)
                    Option.course_select.teacher.put(id, user_admin);
                else
                    Option.course_select.assistant.put(id, user_admin);

                user_admin.course_admin.put(Option.course_select.getCourseId(), Option.course_select);
            }
            System.out.println("add admin success");
        }
    }

    static void removeAdmin(String adminId) {
        if (Option.user_online == null)
            System.out.println("not logged in");
        else if (Option.user_online.getJob() != 0)
            System.out.println("permission denied");
        else if (Option.course_select == null)
            System.out.println("no course selected");
        else if (Option.id_illegal(adminId))
            System.out.println("user id illegal");
        else if (!(Option.id_haveRegistered(adminId) && Option.course_select.findAdminId(adminId)))
            System.out.println("user id not exist");
        else {
            if (adminId.length() == 5)
                Option.course_select.teacher.remove(adminId);
            else
                Option.course_select.assistant.remove(adminId);
            User user_admin = Option.user_all.get(adminId);
            user_admin.course_admin.remove(Option.course_select.getCourseId(), Option.course_select);
            System.out.println("remove admin success");
        }
    }

    static void listAdmin() {
        if (Option.user_online == null)
            System.out.println("not logged in");
        else if (Option.course_select == null)
            System.out.println("no course selected");
        else if (Option.user_online.role_admin)
            Option.course_select.printAdmin_admin();
        else
            Option.course_select.printAdmin_student();
    }

    static void changeRole() {///???
        if (Option.user_online == null)
            System.out.println("not logged in");
        else if (!(Option.user_online.hasAdmin() && Option.user_online.getJob() > 0))
            System.out.println("permission denied");
        else if (Option.user_online.role_admin) {
            System.out.println("change into Student success");
            Option.user_online.role_admin = false;
            Option.course_select = null;
        } else {
            System.out.println("change into Assistant success");
            Option.user_online.role_admin = true;
            Option.course_select = null;
        }
    }

    static void addWare(String wareId, String warePath) {
        File wareFile = new File(warePath);
        String wareName = wareFile.getName();
        if (Option.user_online == null)
            System.out.println("not logged in");
        else if (!Option.user_online.role_admin)
            System.out.println("permission denied");
        else if (Option.course_select == null)
            System.out.println("no course selected");
        else if (wareId_illegal(wareId))
            System.out.println("ware id illegal");
        else if (wareName_illegal(wareName))
            System.out.println("ware name illegal");
        else if (!wareFile.exists())
            System.out.println("ware file does not exist");
        else {
            try {
                Path sourcePath = Path.of(warePath);
                Path destinationPath = Path.of("./data/" + Option.course_select.getCourseId() + "/wares/" + wareId + "_" + wareName);
                Path destinationParentPath = destinationPath.getParent();
                Ware oldWare = Option.course_select.ware_course.get(wareId);
                if (oldWare != null) {
                    Files.delete(oldWare.getPath());
                    Option.course_select.ware_course.remove(wareId);
                }
                Files.createDirectories(destinationParentPath);
                Files.copy(sourcePath, destinationPath);
                Ware ware = new Ware(wareId, wareName, destinationPath);
                Option.course_select.ware_course.put(wareId, ware);
                System.out.println("add ware success");
            } catch (Exception e) {
                System.out.println("ware file operation failed");
            }
        }
    }

    static void removeWare(String wareId) {
        if (Option.user_online == null)
            System.out.println("not logged in");
        else if (!Option.user_online.role_admin)
            System.out.println("permission denied");
        else if (Option.course_select == null)
            System.out.println("no course selected");
        else if (!(wareId_haveRegistered(wareId) && Option.course_select.findWareId(wareId)))
            System.out.println("ware not found");
        else {
            try {
                String wareName = Option.course_select.ware_course.get(wareId).getWareName();
                Path destinationPath = Paths.get("./data/" + Option.course_select.getCourseId() + "/wares/" + wareId + "_" + wareName);
                Files.delete(destinationPath);
                Option.course_select.ware_course.remove(wareId);
                System.out.println("remove ware success");
            } catch (IOException e) {
                System.out.println("delete file failed");
            }
        }
    }

    static void listWare() {
        if (Option.user_online == null)
            System.out.println("not logged in");
        else if (Option.course_select == null)
            System.out.println("no course selected");
        else if (Option.course_select.ware_course.size() == 0)
            System.out.println("total 0 ware");
        else
            Option.course_select.printWare();
    }

    static void addTask(String taskId, String taskPath, String startTime, String endTime) {
        File taskFile = new File(taskPath);
        String taskName = taskFile.getName();
        if (Option.user_online == null)
            System.out.println("not logged in");
        else if (!Option.user_online.role_admin)
            System.out.println("permission denied");
        else if (Option.course_select == null)
            System.out.println("no course selected");
        else if (taskId_illegal(taskId))
            System.out.println("task id illegal");
        else if (taskName_illegal(taskName))
            System.out.println("task name illegal");
        else if (taskTime_illegal(startTime, endTime))
            System.out.println("task time illegal");
        else if (!taskFile.exists())
            System.out.println("task file not found");
        else {
            try {
                Path sourcePath = Path.of(taskPath);
                Path destinationPath = Path.of("./data/" + Option.course_select.getCourseId() + "/tasks/" + taskId + "/" + taskName);
                Path destinationParentPath = destinationPath.getParent();
                Task oldTask = Option.course_select.task_course.get(taskId);
                if (oldTask != null) {
                    Files.delete(oldTask.getPath());
                    Option.course_select.task_course.remove(taskId);
                }
                Files.createDirectories(destinationParentPath);
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                Task task = new Task(taskId, taskName, startTime, endTime, destinationPath);
                Option.course_select.task_course.put(task.getTaskId(), task);
                System.out.println("add task success");
            } catch (Exception e) {

                System.out.println("file operation failed" + " " + e);
            }
        }
    }

    static void removeTask(String taskId) {
        if (Option.user_online == null)
            System.out.println("not logged in");
        else if (!Option.user_online.role_admin)
            System.out.println("permission denied");
        else if (Option.course_select == null)
            System.out.println("no course selected");
//        else if (taskId_illegal(taskId))
//            System.out.println("task id illegal");
        else if (!(taskId_haveRegistered(taskId) && Option.course_select.findTaskId(taskId)))
            System.out.println("task not found");
        else {
            try {
                String taskName = Option.course_select.task_course.get(taskId).getTaskName();
                Path destinationPath = Paths.get("./data/" + Option.course_select.getCourseId() + "/tasks/" + taskId + "/" + taskName);
                Files.delete(destinationPath);
                Option.course_select.task_course.remove(taskId);
                System.out.println("remove task success");
            } catch (IOException e) {
                System.out.println("delete file failed");
            }
        }
    }

    static void listTask() {
        if (Option.user_online == null)
            System.out.println("not logged in");
        else if (Option.course_select == null)
            System.out.println("no course selected");
        else if (Option.course_select.task_course.size() == 0)
            System.out.println("total 0 task");
        else if (Option.user_online.role_admin)
            Option.course_select.printTask_Admin();
        else
            Option.course_select.printTask_Student();

    }

    static void addStudent(String[] studentId) {
        if (Option.user_online == null)
            System.out.println("not logged in");
        else if (!Option.user_online.role_admin)
            System.out.println("permission denied");
        else if (Option.course_select == null)
            System.out.println("no course selected");
        else {
            for (String id : studentId) {
                if (Option.id_illegal(id)) {
                    System.out.println("user id illegal");
                    return;
                }
                if (!Option.id_haveRegistered(id)) {
                    System.out.println("user id not exist");
                    return;
                }
                if (Option.toJob(id) == 0) {
                    System.out.println("I'm professor rather than student!");
                    return;
                }
            }
            for (String id : studentId) {
                User user_student = Option.user_all.get(id);
                Option.course_select.student.put(id, user_student);
                user_student.course_learn.put(Option.course_select.getCourseId(), Option.course_select);
            }
            System.out.println("add student success");
        }
    }

    static void removeStudent(String studentId) {
        if (Option.user_online == null)
            System.out.println("not logged in");
        else if (!Option.user_online.role_admin)
            System.out.println("permission denied");
        else if (Option.course_select == null)
            System.out.println("no course selected");
        else if (Option.id_illegal(studentId))
            System.out.println("user id illegal");
        else if (!(Option.id_haveRegistered(studentId) && Option.course_select.findStudentId(studentId)))
            System.out.println("user id not exist");
        else {
            Option.course_select.student.remove(studentId);
            User user_student = Option.user_all.get(studentId);
            user_student.course_learn.remove(Option.course_select.getCourseId(), Option.course_select);
            System.out.println("remove student success");
        }
    }

    static void listStudent() {
        if (Option.user_online == null)
            System.out.println("not logged in");
        else if (!Option.user_online.role_admin)
            System.out.println("permission denied");
        else if (Option.course_select == null)
            System.out.println("no course selected");
        else
            Option.course_select.printStudent();
    }

    static void downloadFile(String[] argv) {
        int argc = argv.length;
        String fileId ;
        if (argv[argc - 1].matches(">{1,2}")) {
            System.out.println("please input the path to redirect the file");
            return;
        } else if (argc < 2) {
            System.out.println("arguments illegal");
            return;
        } else if (argc == 2) {
            if (argv[0].matches(">{1,2}")) {
                try {
                    redirectOutput("arguments illegal", argv[1], argv[0].equals(">>"));
                } catch (Exception e) {
                    System.out.println("file operation failed");
                }
                return;
            }
            fileId = argv[1];
        } else if (argc == 3) {
            if (!argv[1].matches(">{1,2}")) {
                System.out.println("arguments illegal");
                return;
            }
            fileId = argv[0];
        } else if (argc == 4) {
            if (argv[2].matches(">{1,2}") && argv[0].equals(argv[3])) {
                System.out.println("input file is output file");
                return;
            } else if (!argv[2].matches(">{1,2}")) {
                System.out.println("arguments illegal");
                return;
            }
            fileId = argv[1];
        } else {
            try {
                redirectOutput("arguments illegal", argv[argc - 1], argv[argc - 2].equals(">>"));
            } catch (Exception e) {
                System.out.println("file operation failed");
            }
            return;
        }

        String content = null;
        if (Option.user_online == null) content = "not logged in";
        else if (Option.course_select == null) content = "no course selected";
        Doc doc = null;
        if (Option.course_select != null) {
            doc = Option.course_select.ware_course.get(fileId);
            if (doc == null) doc = Option.course_select.task_course.get(fileId);
        }
        if ((argc == 2 || argc == 4) && doc != null) {
            try {
                Path sourcePath = doc.getPath();
                Path destinationPath = Path.of(argv[0]);
                if (destinationPath.getParent() != null) Files.createDirectories(destinationPath.getParent());
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                System.out.println("file operation failed");
                return;
            }
        }
        try {
            if (content == null) {
                if (doc == null) content = "file not found";
                else content = new String(Files.readAllBytes(doc.getPath()));
            }
            if (argc > 2) {
                redirectOutput(content, argv[argc - 1], argv[argc - 2].equals(">>"));
            } else {
                System.out.println(content.trim());
            }
        } catch (Exception e) {
            System.out.println("file operation failed");
        }
    }

    static void openFile(String[] argv) {
        int argc = argv.length;
        if (argc == 0) {
            System.out.println("please input the path to open the file");
            return;
        }
        if (argc < 3 && argv[argc - 1].equals("<")) {
            System.out.println("please input the path to redirect the file");
            return;
        }
        if (argc > 3 || (argc > 1 && !argv[argc - 2].equals("<"))) {
            System.out.println("arguments illegal");
            return;
        }
        try {
            String path = argc == 2 ? argv[1] : argv[0];
            String content = new String(Files.readAllBytes(Path.of(path)));
            System.out.println(content.trim());
        } catch (Exception e) {
            System.out.println("file open failed");
        }
    }

    static void addFile(String[] argv, boolean append) {
        int argc = argv.length;
        if (argv[argc - 1].equals("<")) {
            System.out.println("please input the path to redirect the file");
            return;
        }
        if ((argc == 3 && argv[1].equals("<")) || argc > 5 || argc < 3 || (argc > 3 && !argv[argc - 2].equals("<"))) {
            System.out.println("arguments illegal");
            return;
        }
        if (argc == 5) argc = 3;
        if (Option.user_online == null) {
            System.out.println("not logged in");
            return;
        }
        if (append == SUBMIT_TASK && Option.user_online.role_admin) {
            System.out.println("operation not allowed");
            return;
        }
        if (append == ADD_ANSWER && !Option.user_online.role_admin) {
            System.out.println("permission denied");
            return;
        }
        if (Option.course_select == null) {
            System.out.println("no course selected");
            return;
        }
        String taskID = (argc == 3 ? argv[2] : argv[1]);
        Task task = Option.course_select.task_course.get(taskID);
        if (task == null) {
            System.out.println("task not found");
            return;
        }
        try {
            Path sourcePath = Path.of(argc == 3 ? argv[1] : argv[3]);
            if (append == SUBMIT_TASK) {
                Homework oldHomework = task.findHomework(Option.user_online.getId());
                if (oldHomework != null) {
                    System.out.println("task already exists, do you want to overwrite it? (y/n)");
                    String option = Test.in.nextLine();
                    if (!option.equals("y") && !option.equals("Y")) {   //"\n""
                        System.out.println("submit canceled");
                        return;
                    }
                    Files.delete(oldHomework.getPath());
                }
                Path destinationPath = Path.of("./data/" + Option.course_select.getCourseId() + "/tasks/" + taskID + "/" + Option.user_online.getId() + ".task");
                if (oldHomework == null && destinationPath.getParent() != null)
                    Files.createDirectories(destinationPath.getParent());
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                task.submitHomework(Option.user_online.getId(), destinationPath);
                System.out.println("submit success");
                System.out.println("your score is: " + floatFormat(task.findHomework(Option.user_online.getId()).getLastScore()));
            } else {
                Path destinationPath = Path.of("./data/" + Option.course_select.getCourseId() + "/answers/" + taskID + ".ans");
                if (task.getAnswer() != null) Files.delete(destinationPath);
                else if (destinationPath.getParent() != null) Files.createDirectories(destinationPath.getParent());
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                task.setAnswer(destinationPath);
                System.out.println("add answer success");
            }
        } catch (Exception e) {
            System.out.println("file operation failed");
        }
    }

    static void submitTask(String[] str) {
        addFile(str, SUBMIT_TASK);
    }

    static void addAnswer(String[] str) {
        addFile(str, ADD_ANSWER);
    }

    static void queryScore(String[] str) {
        int argc = str.length;
        if (Option.user_online == null)
            System.out.println("not logged in");
        else if (Option.course_select == null)
            System.out.println("no course selected");
        else if (!Option.user_online.role_admin && argc == 3)
            System.out.println("permission denied");
        else if ((argc == 3 && !Option.course_select.findTaskId(str[1])) || (argc == 2 && str[1].charAt(0) == 'T' && !Option.course_select.findTaskId(str[1])))
            System.out.println("task not found");
        else if ((argc == 3 && !Option.course_select.findStudentId(str[2])) || (argc == 2 && str[1].charAt(0) != 'T' && !Option.course_select.findStudentId(str[1])))
            System.out.println("student not found");
        else {
            User student_select = null;
            ArrayList<Task> task_select = new ArrayList<>();
            for (int i = 1; i < argc; ++i) {
                if (task_select.size() == 0 && Option.course_select.findTaskId(str[i]))
                    task_select.add(Option.course_select.task_course.get(str[i]));
                if (student_select == null && Option.course_select.findStudentId(str[i]))
                    student_select = Option.course_select.student.get(str[i]);
            }
            if (student_select == null && !Option.user_online.role_admin)
                student_select = Option.user_online;
            if (task_select.size() == 0) {
                TreeMap<String, Task> tasks = Option.course_select.task_course;
                for (String taskID : tasks.keySet()) task_select.add(tasks.get(taskID));
            }

            ArrayList<StudentTaskScore> all_studentTaskScores = new ArrayList<>();
            for (Task task : task_select) {
                ArrayList<StudentTaskScore> studentTaskScores = task.queryScore(student_select);
                all_studentTaskScores.addAll(studentTaskScores);
            }
            if (all_studentTaskScores.size() == 0) System.out.println("total 0 result");
            else if (all_studentTaskScores.size() == 1) System.out.println("total 1 result");
            else System.out.printf("total %d results\n", all_studentTaskScores.size());
            for (int i = 0; i < all_studentTaskScores.size(); i++) {
                System.out.print("[" + (i + 1) + "] ");
                System.out.println(all_studentTaskScores.get(i));
            }

        }
    }
}
