import java.util.Arrays;
import java.util.Scanner;

public class Test {

    static Scanner in = new Scanner(System.in);
    public static void main(String[] args) throws Exception{
        //int cnt=0;
        label:
        while(true){
            String oldCommand = in.nextLine();
            oldCommand = oldCommand.trim();
            String[] command = oldCommand.split("\\s+");

//            cnt++;
//            if(cnt==50)
//                System.out.println("command50"+command);

            switch (command[0])
            {
//                case "test":
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
                    Option.userLogout();
                    break;
                case "addCourse":
                    if(command.length!=3)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    CourseOption.addCourse(command[1],command[2]);
                    break;
                case "removeCourse":
                    if(command.length!=2)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    CourseOption.removeCourse(command[1]);
                    break;
                case "listCourse":
                    if(command.length!=1)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    CourseOption.listCourse();
                    break;
                case "selectCourse":
                    if(command.length!=2)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    CourseOption.selectCourse(command[1]);
                    break;
                case "addAdmin":
                    if(command.length==1)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    String[] admin = Arrays.copyOfRange(command,1,command.length);
                    CourseOption.addAdmin(admin);
                    break;
                case "removeAdmin":
                    if(command.length!=2)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    CourseOption.removeAdmin(command[1]);
                    break;
                case "listAdmin":
                    if(command.length!=1)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    CourseOption.listAdmin();
                    break;
                case "changeRole":
                    if(command.length!=1)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    CourseOption.changeRole();
                    break;
                case "addWare":
                    if(command.length!=3)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    CourseOption.addWare(command[1],command[2]);
                    break;
                case "removeWare":
                    if(command.length!=2)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    CourseOption.removeWare(command[1]);
                    break;
                case "listWare":
                    if(command.length!=1)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    CourseOption.listWare();
                    break;
                case "addTask":
                    if(command.length!=5)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    CourseOption.addTask(command[1],command[2],command[3],command[4]);
                    break;
                case "removeTask":
                    if(command.length!=2)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    CourseOption.removeTask(command[1]);
                    break;
                case "listTask":
                    if(command.length!=1)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    CourseOption.listTask();
                    break;
                case "addStudent":
                    if(command.length==1)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    String[] student = Arrays.copyOfRange(command,1,command.length);
                    CourseOption.addStudent(student);
                    break;
                case "removeStudent":
                    if(command.length!=2)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    CourseOption.removeStudent(command[1]);
                    break;
                case "listStudent":
                    if(command.length!=1)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    CourseOption.listStudent();
                    break;
                case "downloadFile":
                    if(command.length==1)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    String[] argv1 = Arrays.copyOfRange(command,1,command.length);
                    CourseOption.downloadFile(argv1);
                    break;
                case "openFile":
                    String[] argv2 = Arrays.copyOfRange(command,1,command.length);
                    CourseOption.openFile(argv2);
                    break;
                case "submitTask":
                    CourseOption.submitTask(command);
                    break;
                case "addAnswer":
                    CourseOption.addAnswer(command);
                    break;
                case "queryScore":
                    if(!(command.length==1 || command.length==2 || command.length==3))
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    CourseOption.queryScore(command);
                    break;
                case "requestVM":
                    if(command.length!=2)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    VMOption.requestVM(command[1]);
                    break;
                case "startVM":
                    if(command.length!=1)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    VMOption.startVM();
                    break;
                case "clearVM":
                    if(command.length!=2)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    VMOption.clearVM(command[1]);
                    break;
                case "logVM":
                    if(command.length!=1)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    VMOption.logVM();
                    break;
                case "uploadVM":
                    if(command.length!=2)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    VMOption.uploadVM(command[1]);
                    break;
                case "downloadVM":
                    if(command.length!=2)
                    {
                        System.out.println("arguments illegal");
                        break;
                    }
                    VMOption.downloadVM(command[1]);
                    break;
                case "":
                    break;
                default:
                    System.out.println("command '"+command[0]+"' not found");
            }
        }
    }
}