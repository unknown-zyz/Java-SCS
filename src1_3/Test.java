import java.util.HashMap;
import java.util.Scanner;

public class Test {

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
                    Option.userRegister(command[1],command[2],command[3],command[4],command[5],command[6]);
                    break;
                case "login":
                    if(command.length!=3)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    Option.userLogin(command[1],command[2]);
                    break;
                case "printInfo":
                    if(command.length!=1 && command.length!=2)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    if(command.length==1)
                        Option.printInfo();
                    else
                        Option.printInfo(command[1]);
                    break;
                case "logout":
                    if(command.length!=1)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    Option.userlogout();
                    break;
                case "":
                    break;
                default:
                    System.out.println("command '"+command[0]+"' not found");
            }
        }
    }
}