public class User {
//    private boolean have_registered;
    private int intid;
    private String id;
    private String name;//名
    private String surname;//姓
    private String email;
    private String pwd;
    private int job;//本科un1 SY2 ZY3 BY4 老师0

    private int num,clas,college,year;
    public int idgetjob(String id)
    {
        if(id.length()==5)
            return 0;
        else if(id.length()==8)
            return 1;
        else
        {
            if(id.charAt(0)=='S')
                return 2;
            else if(id.charAt(0)=='Z')
                return 3;
            else if(id.charAt(0)=='B')
                return 4;
        }
        return 0;///??????
    }

    public int id_int(String id)
    {
        if (id.length() == 5 || id.length() == 8)
            return Integer.parseInt(id);
        else
            return Integer.parseInt(id.substring(2));
    }

    public void getmessage(String id) { //id合法
        int year,college,clas,num;
        if (id.length() == 5)
        {
            this.num = this.intid = Integer.parseInt(id);
            this.job = 0;
        }
        else if (id.length() == 8)
        {
            int id2 = this.intid = Integer.parseInt(id);
            num = id2%1000;      id2/=1000;     this.num = num;
            clas=id2%10;        id2/=10;        this.clas = clas;
            college=id2%100;    id2/=100;       this.college = college;
            year=id2;                           this.year = year;
            this.job = 1;
        }
        else
        {
            String id3 = id.substring(2);
            int id2 = this.intid = Integer.parseInt(id3);
            num=id2%100;       id2/=100;        this.num = num;
            clas=id2%10;        id2/=10;        this.clas = clas;
            college=id2%100;    id2/=100;       this.college = college;
            year=id2;                           this.year = year;
            char c = id.charAt(0);
            if(c=='S') this.job = 2;
            else if(c=='Z') this.job = 3;
            else if(c=='B') this.job = 4;
        }
//        System.out.println(this.intid);
//        System.out.println(this.job);
//        System.out.println(this.year);
//        System.out.println(this.college);
//        System.out.println(this.clas);
//        System.out.println(this.num);
    }
    public boolean id_illegal(String id) {
        if(id.length()!=5 && id.length()!=8 && id.length()!=9)
            return true;

        //是否合法 eg:5位AAAAA 8位AAAABBBB

        getmessage(id);

        if (job == 0 && num >= 1 && num <= 99999)
            return false;
        else if (job == 1 && num>=1 && num<=999 && clas>=1 && clas<=6 && college>=1 && college<=43 && year>=17 && year<=22)
            return false;
        else if(job == 2 && num>=1 && clas>=1 && clas<=6 && college>=1 && college<=43 && year>=19 && year<=22)
            return false;
        else if(job == 3 && num>=1 && clas>=1 && clas<=6 && college>=1 && college<=43 && year>=19 && year<=22)
            return false;
        else if (job == 4 && num>=1 && clas>=1 && clas<=6 && college>=1 && college<=43 && year>=17 && year<=22)
            return false;
        return true;
    }
    public boolean id_haveregistered(String id) {//id一定合法
        int job = idgetjob(id);
        int intid = id_int(id);
        return Test.us.get(intid * 10 + job) != null;
    }

    public boolean name_illegal(String name,String surname) {
        return !name.matches("[A-Z][a-z]{0,19}+") || !surname.matches("[A-Z][a-z]{0,19}+");
    }
    public boolean email_illegal(String email) {
        return !email.matches("\\w+@\\w+(\\.\\w+)+");
    }

    public boolean pwd_illegal(String pwd) {
        return !pwd.matches("[a-zA-Z]\\w{7,15}+");
    }

    public boolean pwd_inconsistent(String pwd,String confirm_pwd) {
        return !pwd.equals(confirm_pwd);
    }

    public boolean pwd_wrong(int job, int id, String pwd) {
        return !Test.us.get(id * 10 + job).pwd.equals(pwd);
    }

    public void userRegister(String id, String name, String surname, String email, String pwd, String confirm_pwd) {

        if(Test.have_logged)
            System.out.println("already logged in");
        else if(id_illegal(id))
            System.out.println("user id illegal");
        else if(id_haveregistered(id))
            System.out.println("user id duplication");
        else if(name_illegal(name,surname))
            System.out.println("user name illegal");
        else if(email_illegal(email))
            System.out.println("email address illegal");
        else if(pwd_illegal(pwd))
            System.out.println("password illegal");
        else if(pwd_inconsistent(pwd, confirm_pwd))
            System.out.println("passwords inconsistent");
        else
        {
            this.id = id;
            this.name = name;
            this.surname = surname;
            this.email = email;
            this.pwd = pwd;
//            this.have_registered = true;
            Test.us.put(intid*10+job,this);
            System.out.println("register success");
//            System.out.println(this.job+" "+this.intid);
        }
    }

    public void userLogin(String id,String pwd) {
        getmessage(id);

        if(Test.have_logged)
            System.out.println("already logged in");
        else if(id_illegal(id))
            System.out.println("user id illegal");
        else if(!id_haveregistered(id))
            System.out.println("user id not exist");
        else if(pwd_wrong(job,intid,pwd))
            System.out.println("wrong password");
        else {
            Test.user_online.id = id;
            Test.user_online.intid = intid;
            Test.user_online.job = this.job;
            Test.user_online = Test.us.get(intid * 10 + job);
            if (job == 0) System.out.println("Hello Professor " + Test.user_online.surname + "~");
            else System.out.println("Hello " + Test.user_online.name + "~");
            Test.have_logged = true;
        }
    }
    public void userlogout()
    {
        if(!Test.have_logged)
            System.out.println("not logged in");
        else
        {
            Test.have_logged = false;
            System.out.println("Bye~");
        }
    }

    public void printInfo() {
        if(!Test.have_logged)
            System.out.println("login first");
        else
        {
            System.out.println("Name: "+Test.user_online.name+" "+Test.user_online.surname);
            System.out.println("ID: "+Test.user_online.id);
            System.out.print("Type: ");
            if(Test.user_online.job==0) System.out.println("Professor");
            else System.out.println("Student");
            System.out.println("Email: "+Test.user_online.email);
        }
    }

    public void printInfo(String id) {
        if(!Test.have_logged)
            System.out.println("login first");
        else if(Test.user_online.job!=0)
            System.out.println("permission denied");
        else if(id_illegal(id))
            System.out.println("user id illegal");
        else if(!id_haveregistered(id))
            System.out.println("user id not exist");
        else
        {
            int print_job = idgetjob(id);
            int print_id = id_int(id);
            User user_print;
            user_print = Test.us.get(print_id*10+print_job);
            System.out.println("Name: "+user_print.name+" "+user_print.surname);
            System.out.println("ID: "+user_print.id);
            System.out.print("Type: ");
            if(user_print.job==0) System.out.println("Professor");
            else System.out.println("Student");
            System.out.println("Email: "+user_print.email);
        }
    }
}
