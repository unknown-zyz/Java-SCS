import java.util.HashMap;

public class Option {

    static HashMap<String, User> user_all = new HashMap<>();
    static User user_online;
    static Course course_select;


    static int toJob(String id) {
        if (id.length() == 5)
            return 0;
        else if (id.length() == 8)
            return 1;
        else {
            if (id.charAt(0) == 'S')
                return 2;
            else if (id.charAt(0) == 'Z')
                return 3;
            else if (id.charAt(0) == 'B')
                return 4;
        }
        return 0;///??????
    }

    static int toNum(String id) {
        switch (id.length()) {
            case 5:
                return Integer.parseInt(id);
            case 8:
                return Integer.parseInt(id.substring(5, 8));
            case 9:
                return Integer.parseInt(id.substring(7, 9));
            default:
                return 0;
        }
    }

    static int toClas(String id) {
        switch (id.length()) {
            case 8:
                return (Integer.parseInt(id.substring(4, 5)));
            case 9:
                return (Integer.parseInt(id.substring(6, 7)));
            default:
                return 0;
        }
    }

    static int toCollege(String id) {
        switch (id.length()) {
            case 8:
                return (Integer.parseInt(id.substring(2, 4)));
            case 9:
                return (Integer.parseInt(id.substring(4, 6)));
            default:
                return 0;
        }
    }

    static int toYear(String id) {
        switch (id.length()) {
            case 8:
                return Integer.parseInt(id.substring(0, 2));
            case 9:
                return Integer.parseInt(id.substring(2, 4));
            default:
                return 0;
        }
    }

    static boolean id_illegal(String id) {
        if (!(id.matches("\\d{5}") || id.matches("\\d{8}") || id.matches("[SZB]Y\\d{7}")))
            return true;
        int job = toJob(id);
        int num = toNum(id);
        int clas = toClas(id);
        int college = toCollege(id);
        int year = toYear(id);
        return !( (job == 0 && num >= 1 && num <= 99999)
                || (job == 1 && num >= 1 && num <= 999 && clas >= 1 && clas <= 6 && college >= 1 && college <= 43 && year >= 17 && year <= 22)
                || (job == 2 && num >= 1 && clas >= 1 && clas <= 6 && college >= 1 && college <= 43 && year >= 19 && year <= 22)
                || (job == 3 && num >= 1 && clas >= 1 && clas <= 6 && college >= 1 && college <= 43 && year >= 19 && year <= 22)
                || (job == 4 && num >= 1 && clas >= 1 && clas <= 6 && college >= 1 && college <= 43 && year >= 17 && year <= 22) );
    }

    static boolean id_haveRegistered(String id) {
        return user_all.get(id) != null;
    }

    static boolean name_illegal(String name, String surname) {
        return !name.matches("[A-Z][a-z]{0,19}+") || !surname.matches("[A-Z][a-z]{0,19}+");
    }

    static boolean email_illegal(String email) {
        return !email.matches("\\w+@\\w+(\\.\\w+)+");
    }

    static boolean pwd_illegal(String pwd) {
        return !pwd.matches("[a-zA-Z]\\w{7,15}+");
    }

    static boolean pwd_inconsistent(String pwd, String confirm_pwd) {
        return !pwd.equals(confirm_pwd);
    }

    static boolean pwd_wrong(String id, String pwd) {
        return !user_all.get(id).getPwd().equals(pwd);
    }

    static void userRegister(String id, String name, String surname, String email, String pwd, String confirm_pwd) {

        if (user_online != null)
            java.lang.System.out.println("already logged in");
        else if (id_illegal(id))
            java.lang.System.out.println("user id illegal");
        else if (id_haveRegistered(id))
            java.lang.System.out.println("user id duplication");
        else if (name_illegal(name, surname))
            java.lang.System.out.println("user name illegal");
        else if (email_illegal(email))
            java.lang.System.out.println("email address illegal");
        else if (pwd_illegal(pwd))
            java.lang.System.out.println("password illegal");
        else if (pwd_inconsistent(pwd, confirm_pwd))
            java.lang.System.out.println("passwords inconsistent");
        else {
            User user1 = new User(id, name, surname, email, pwd);
//            user1.setNumber(toNumber(id));
            user1.setJob(toJob(id));
            user_all.put(user1.getId(), user1);
            System.out.println("register success");
        }
    }

    static void userLogin(String id, String pwd) {
        int job = toJob(id);
        if (user_online != null)
            System.out.println("already logged in");
        else if (id_illegal(id))
            System.out.println("user id illegal");
        else if (!id_haveRegistered(id))
            System.out.println("user id not exist");
        else if (pwd_wrong(id, pwd))
            System.out.println("wrong password");
        else {
            user_online = user_all.get(id);
            //!!!!
            user_online.role_admin = (job == 0);
            if (job == 0) System.out.println("Hello Professor " + user_online.getSurname() + "~");
            else System.out.println("Hello " + user_online.getName() + "~");
        }
    }

    static void userLogout() {
        if (user_online == null)
            System.out.println("not logged in");
        else {
            System.out.println("Bye~");
            user_online = null;
            course_select = null;
        }
    }

    static void printInfo() {
        if (user_online == null)
            System.out.println("login first");
        else
            System.out.println(user_online);
    }

    static void printInfo(String id) {
        if (user_online == null)
            System.out.println("login first");
        else if (user_online.getJob() != 0)
            System.out.println("permission denied");
        else if (id_illegal(id))
            System.out.println("user id illegal");
        else if (!id_haveRegistered(id))
            System.out.println("user id not exist");
        else {
            User user_print = user_all.get(id);
            System.out.println(user_print);
        }
    }
}

