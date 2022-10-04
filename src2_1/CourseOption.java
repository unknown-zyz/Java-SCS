import java.util.TreeMap;

public class CourseOption {
    static TreeMap<String, Course> course_all = new TreeMap<>();

    static int[] normal_year = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    static int[] leap_year = {0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

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
            Option.user_online.course_admin.remove(courseId);
            System.out.println("remove course success");
        }
    }

    static void listCourse() {
        if (Option.user_online == null)
            System.out.println("not logged in");
        else if (Option.user_online.getJob() != 0)
            System.out.println("permission denied");
        else if (Option.user_online.getCourse_AdminSize() == 0)
            System.out.println("course not exist");
        else
            Option.user_online.printCourse_Admin();
    }

    static void selectCourse(String courseId) {
        if (Option.user_online == null)
            System.out.println("not logged in");
        else if (!Option.user_online.isAdmin())
            System.out.println("permission denied");
        else if (courseId_illegal(courseId))
            System.out.println("course id illegal");
        else if (!(courseId_haveRegistered(courseId) && course_all.get(courseId).findAdminId(Option.user_online.getId())))
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
        else if (!Option.user_online.isAdmin())
            System.out.println("permission denied");
        else if (Option.course_select == null)
            System.out.println("no course selected");
        else
            Option.course_select.printAdmin();
    }

    static void changeRole() {
        if (Option.user_online == null)
            System.out.println("not logged in");
        else if (!(Option.user_online.isAdmin() && Option.user_online.getJob() > 0))
            System.out.println("permission denied");
        else if (Option.role_student) {
            System.out.println("change into Assistant success");
            Option.role_student = false;
        } else {
            System.out.println("change into Student success");
            Option.role_student = true;
        }
    }

    static void addWare(String wareId, String wareName) {
        if (Option.user_online == null)
            System.out.println("not logged in");
        else if (Option.user_online.getJob() != 0)
            System.out.println("permission denied");
        else if (Option.course_select == null)
            System.out.println("no course selected");
        else if (wareId_illegal(wareId))
            System.out.println("ware id illegal");
        else if (wareId_haveRegistered(wareId))
            System.out.println("ware id duplication");
        else if (wareName_illegal(wareName))
            System.out.println("ware name illegal");
        else {
            Ware ware = new Ware(wareId, wareName);
            Option.course_select.ware_course.put(ware.getWareId(), ware);
            System.out.println("add ware success");
        }
    }

    static void removeWare(String wareId) {
        if (Option.user_online == null)
            System.out.println("not logged in");
        else if (Option.user_online.getJob() != 0)
            System.out.println("permission denied");
        else if (Option.course_select == null)
            System.out.println("no course selected");
        else if (wareId_illegal(wareId))
            System.out.println("ware id illegal");
        else if (!(wareId_haveRegistered(wareId) && Option.course_select.findWareId(wareId)))
            System.out.println("ware id not exist");
        else {
            Option.course_select.ware_course.remove(wareId);
            System.out.println("remove ware success");
        }
    }

    static void listWare() {
        if (Option.user_online == null)
            System.out.println("not logged in");
        else if (!Option.user_online.isAdmin())
            System.out.println("permission denied");
        else if (Option.course_select == null)
            System.out.println("no course selected");
        else
            Option.course_select.printWare();
    }

    static void addTask(String taskId, String taskName, String startTime, String endTime) {
        if (Option.user_online == null)
            System.out.println("not logged in");
        else if (!Option.user_online.isAdmin())
            System.out.println("permission denied");
        else if (Option.course_select == null)
            System.out.println("no course selected");
        else if (taskId_illegal(taskId))
            System.out.println("task id illegal");
        else if (taskId_haveRegistered(taskId))
            System.out.println("task id duplication");
        else if (taskName_illegal(taskName))
            System.out.println("task name illegal");
        else if (taskTime_illegal(startTime, endTime))
            System.out.println("task time illegal");
        else {
            Task task = new Task(taskId, taskName, startTime, endTime);
            Option.course_select.task_course.put(task.getTaskId(), task);
            System.out.println("add task success");
        }
    }

    static void removeTask(String taskId) {
        if (Option.user_online == null)
            System.out.println("not logged in");
        else if (!Option.user_online.isAdmin())
            System.out.println("permission denied");
        else if (Option.course_select == null)
            System.out.println("no course selected");
        else if (taskId_illegal(taskId))
            System.out.println("task id illegal");
        else if (!(taskId_haveRegistered(taskId) && Option.course_select.findTaskId(taskId)))
            System.out.println("task id not exist");
        else {
            Option.course_select.task_course.remove(taskId);
            System.out.println("remove task success");
        }
    }

    static void listTask() {
        if (Option.user_online == null)
            System.out.println("not logged in");
        else if (!Option.user_online.isAdmin())
            System.out.println("permission denied");
        else if (Option.course_select == null)
            System.out.println("no course selected");
        else
            Option.course_select.printTask();
    }

    static void addStudent(String[] studentId) {
        if (Option.user_online == null)
            System.out.println("not logged in");
        else if (!Option.user_online.isAdmin())
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
        else if (!Option.user_online.isAdmin())
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
        else if (!Option.user_online.isAdmin())
            System.out.println("permission denied");
        else if (Option.course_select == null)
            System.out.println("no course selected");
        else
            Option.course_select.printStudent();
    }
}
