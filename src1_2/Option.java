
public class Option {
    static int tojob(String id) {
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

    static int tonumber(String id) {
        if (id.length() == 5 || id.length() == 8)
            return Integer.parseInt(id);
        else
            return Integer.parseInt(id.substring(2));
    }

    static int tonum(String id) {
        switch (id.length()) {
            case 5:
                return Integer.parseInt(id);
            case 8:
                return Integer.parseInt(id) % 1000;
            case 9:
                return Integer.parseInt(id.substring(2)) % 100;
            default:
                return 0;
        }
    }

    static int toclas(String id) {
        switch (id.length()) {
            case 8:
                return (Integer.parseInt(id) % 10000) / 1000;
            case 9:
                return (Integer.parseInt(id.substring(2)) % 1000) / 100;
            default:
                return 0;
        }
    }

    static int tocollege(String id) {
        switch (id.length()) {
            case 8:
                return (Integer.parseInt(id) % 1000000) / 10000;
            case 9:
                return (Integer.parseInt(id.substring(2)) % 100000) / 1000;
            default:
                return 0;
        }
    }

    static int toyear(String id) {
        switch (id.length()) {
            case 8:
                return Integer.parseInt(id) / 1000000;
            case 9:
                return Integer.parseInt(id.substring(2)) / 100000;
            default:
                return 0;
        }
    }

    static boolean id_illegal(String id) {
        //是否合法 eg:5位AAAAA 8位AAAABBBB
        if (!(id.matches("\\d{5}") || id.matches("\\d{8}") || id.matches("[SZB]Y\\d{7}")))
            return true;
        int job = tojob(id);
        int num = tonum(id);
        int clas = toclas(id);
        int college = tocollege(id);
        int year = toyear(id);
        if (job == 0 && num >= 1 && num <= 99999)
            return false;
        else if (job == 1 && num >= 1 && num <= 999 && clas >= 1 && clas <= 6 && college >= 1 && college <= 43 && year >= 17 && year <= 22)
            return false;
        else if (job == 2 && num >= 1 && clas >= 1 && clas <= 6 && college >= 1 && college <= 43 && year >= 19 && year <= 22)
            return false;
        else if (job == 3 && num >= 1 && clas >= 1 && clas <= 6 && college >= 1 && college <= 43 && year >= 19 && year <= 22)
            return false;
        else if (job == 4 && num >= 1 && clas >= 1 && clas <= 6 && college >= 1 && college <= 43 && year >= 17 && year <= 22)
            return false;
        else
            return true;
    }

    static boolean id_haveregistered(String id) {//id一定合法
        int job = tojob(id);
        int number = tonumber(id);
        return Test.us.get(number * 10 + job) != null;
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

    static boolean pwd_wrong(int job, int id, String pwd) {
        return !Test.us.get(id * 10 + job).getPwd().equals(pwd);
    }

    static void userRegister(String id, String name, String surname, String email, String pwd, String confirm_pwd) {
        User user1 = new User();

        if (Test.have_logged)
            java.lang.System.out.println("already logged in");
        else if (id_illegal(id))
            java.lang.System.out.println("user id illegal");
        else if (id_haveregistered(id))
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
            user1.setId(id);
            user1.setNumber(tonumber(id));
            user1.setJob(tojob(id));
            user1.setName(name);
            user1.setSurname(surname);
            user1.setEmail(email);
            user1.setPwd(pwd);

//            this.have_registered = true;
            Test.us.put(user1.getNumber() * 10 + user1.getJob(), user1);
            System.out.println("register success");
//            System.out.println(this.job+" "+this.intid);
        }
    }

    static void userLogin(String id,String pwd) {
        int job = tojob(id);
        int number = tonumber(id);

        if(Test.have_logged)
            System.out.println("already logged in");
        else if(id_illegal(id))
            System.out.println("user id illegal");
        else if(!id_haveregistered(id))
            System.out.println("user id not exist");
        else if(pwd_wrong(job,number,pwd))
            System.out.println("wrong password");
        else {
            Test.user_online.setId(id);
            Test.user_online.setNumber(number);
            Test.user_online.setJob(job);
            Test.user_online = Test.us.get(number * 10 + job);
            if (job == 0) System.out.println("Hello Professor " + Test.user_online.getSurname() + "~");
            else System.out.println("Hello " + Test.user_online.getName() + "~");
            Test.have_logged = true;
        }
    }

    static void userlogout() {
        if(!Test.have_logged)
            System.out.println("not logged in");
        else
        {
            Test.have_logged = false;
            System.out.println("Bye~");
        }
    }

    static void printInfo() {
        if(!Test.have_logged)
            System.out.println("login first");
        else
        {
            System.out.println("Name: "+Test.user_online.getName()+" "+Test.user_online.getSurname());
            System.out.println("ID: "+Test.user_online.getId());
            System.out.print("Type: ");
            if(Test.user_online.getJob()==0) System.out.println("Professor");
            else System.out.println("Student");
            System.out.println("Email: "+Test.user_online.getEmail());
        }
    }

    static void printInfo(String id) {
        if(!Test.have_logged)
            System.out.println("login first");
        else if(Test.user_online.getJob()!=0)
            System.out.println("permission denied");
        else if(id_illegal(id))
            System.out.println("user id illegal");
        else if(!id_haveregistered(id))
            System.out.println("user id not exist");
        else
        {
            int print_job = tojob(id);
            int print_number = tonumber(id);
            User user_print = Test.us.get(print_number*10+print_job);

//            System.out.println(user_print);
            System.out.println("Name: "+user_print.getName()+" "+user_print.getSurname());
//            System.out.println("ID: "+id);
//            System.out.println("ID: "+print_number+id+user_print.getId());
            System.out.println("ID: "+user_print.getId());
            System.out.print("Type: ");
            if(print_job==0) System.out.println("Professor");
            else System.out.println("Student");
            System.out.println("Email: "+user_print.getEmail());

        }
    }
}

