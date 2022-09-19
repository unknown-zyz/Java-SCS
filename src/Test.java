import java.util.HashMap;
import java.util.Scanner;

public class Test {

    static HashMap<Integer,User> us = new HashMap<Integer,User>();
    static boolean have_logged = false;
    static User user_online = new User();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        label:
        while(true){
            String old_command = in.nextLine();
            old_command = old_command.trim();
            String[] command = old_command.split("\\s+");
//             System.out.println(command.length);
//             for (String s : command) {
//                 System.out.print(s);
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
                case "printInfo":
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