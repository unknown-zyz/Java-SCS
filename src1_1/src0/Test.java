package MySCS_1;

import javax.jws.soap.SOAPBinding;
import java.util.Scanner;

public class Test {
    /*
    static boolean[] un_registered = new boolean[25500000];//[17,22] [01,43] [1,6] [001,999]
    static boolean[] sy_registered = new boolean[2550000];//[19,22] [01,43] [1,6] [01,99]
    static boolean[] zy_registered = new boolean[2550000];//[19,22] [01,43] [1,6] [01,99]
    static boolean[] by_registered = new boolean[2550000];//[17,22] [01,43] [1,6] [01,99]
    static boolean[] te_registered = new boolean[100050];//[00001,99999]
    static String[] un_name = new String[25500000];
    static String[] sy_name = new String[25500000];
    static String[] zy_name = new String[25500000];
    static String[] by_name = new String[25500000];
    static String[] te_name = new String[100050];
    static String[] un_surname = new String[25500000];
    static String[] sy_surname = new String[25500000];
    static String[] zy_surname = new String[25500000];
    static String[] by_surname = new String[25500000];
    static String[] te_surname = new String[100050];
    static String[] un_email = new String[25500000];
    static String[] sy_email= new String[25500000];
    static String[] zy_email = new String[25500000];
    static String[] by_email = new String[25500000];
    static String[] te_email = new String[100050];
    static String[] un_pwd = new String[25500000];
    static String[] sy_pwd = new String[25500000];
    static String[] zy_pwd = new String[25500000];
    static String[] by_pwd = new String[25500000];
    static String[] te_pwd = new String[100050];
     */
    static User[] un = new User[25500000];
    static User[] sy = new User[2550000];
    static User[] zy = new User[2550000];
    static User[] by = new User[2550000];
    static User[] te = new User[100050];
    static boolean have_logged=false;

    static User user_online = new User();
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        for(int i=0;i< un.length;i++)
        {
            User user0 = new User();
            un[i] = user0;
        }
        for(int i=0;i< sy.length;i++)
        {
            User user0 = new User();
            sy[i] = user0;
        }
        for(int i=0;i< zy.length;i++)
        {
            User user0 = new User();
            zy[i] = user0;
        }
        for(int i=0;i< by.length;i++)
        {
            User user0 = new User();
            by[i] = user0;
        }
        for(int i=0;i< te.length;i++)
        {
            User user0 = new User();
            te[i] = user0;
        }
        label:
        while(true){
            String old_command = in.nextLine();
            old_command = old_command.trim();
            String[] command = old_command.split("\\s+");

//             System.out.println(command.length);
//             for (String s : command) {
////                 System.out.print(1);
//                 System.out.print(s);
////                 System.out.print(2);
//             }
            User user1 = new User();
            switch (command[0])
            {
                case "QUIT":
                    System.out.println("----- Good Bye! -----");
                    break label;
                case "register":
                    if(command.length!=7)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    user1.userRegister(command[1],command[2],command[3],command[4],command[5],command[6]);
                    break;
                case "login":
                    if(command.length!=3)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                   user1.userLogin(command[1],command[2]);
                    break;
                case "printInfo"://两种
                    if(command.length!=1 && command.length!=2)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    if(command.length==1)
                        user1.printInfo();
                    else
                        user1.printInfo(command[1]);
                    break;
                case "logout":
                    if(command.length!=1)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    user1.userlogout();
                    break;
                case "":
                    break;
                default:
                    System.out.println("command '"+command[0]+"' not found");
            }
        }
    }
}
