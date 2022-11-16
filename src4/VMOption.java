import java.io.*;
import java.nio.file.Path;

public class VMOption {
    static void requestVM(String type) {
        if (Option.user_online == null)
            System.out.println("not log in");
        else if (Option.user_online.role_admin)
            System.out.println("not a student");
        else if (Option.course_select == null)
            System.out.println("not select course");
        else {
            VirtualMachine vm = Option.course_select.getFactory().createVM(type);
            Option.user_online.vm_student.put(Option.course_select, vm);
            System.out.println("requestVM success");
        }
    }

    static void startVM() {
        VirtualMachine vm = Option.user_online.getVM(Option.course_select);
        if (Option.user_online == null)
            System.out.println("not log in");
        else if (Option.user_online.role_admin)
            System.out.println("not a student");
        else if (Option.course_select == null)
            System.out.println("not select course");
        else if (vm == null)
            System.out.println("no VM");
        else {
            System.out.println("welcome to " + vm.getClass().getSimpleName());
            String str;
            while (!(str = Test.in.nextLine()).matches("EOF")) {
                vm.command.add(str);
            }
            System.out.println("quit " + vm.getClass().getSimpleName());
        }
    }

    static void clearVM(String str) {
        int number = Integer.parseInt(str);
        if (Option.user_online == null)
            System.out.println("not log in");
        else if (!Option.user_online.role_admin)
            System.out.println("not a teacher");
        else if (Option.course_select == null)
            System.out.println("not select course");
        else {
            VirtualMachine vm = Option.course_select.VMs.get(number - 1);
            vm.command.clear();
            System.out.println("clear " + vm.getClass().getSimpleName() + " success");
        }
    }

    static void logVM() {
        VirtualMachine vm = Option.user_online.getVM(Option.course_select);
        if (Option.user_online == null)
            System.out.println("not log in");
        else if (Option.user_online.role_admin)
            System.out.println("not a student");
        else if (Option.course_select == null)
            System.out.println("not select course");
        else if (vm == null || vm.command.size() == 0)
            System.out.println("no log");
        else {
            for (String i : vm.command)
                System.out.println(i);
        }
    }

    public static void ser(File f, Object o) throws Exception {
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }
        if (!f.exists()) {
            f.createNewFile();
        }
        OutputStream ops = new FileOutputStream(f);
        ObjectOutputStream oos = new ObjectOutputStream(ops);
        oos.writeObject(o);
        oos.close();
        ops.close();
    }

    public static void dser(File f) throws Exception {
        InputStream in = new FileInputStream(f);
        ObjectInputStream ois = new ObjectInputStream(in);
        Object obj = ois.readObject();
        VirtualMachine vm = (VirtualMachine) obj;
        Option.user_online.vm_student.put(Option.course_select, vm);
        Option.course_select.VMs.add(vm);
        ois.close();
        in.close();
    }

    static void uploadVM(String path) throws Exception {
        if (Option.user_online == null)
            System.out.println("not log in");
        else if (Option.user_online.role_admin)
            System.out.println("not a student");
        else if (Option.course_select == null)
            System.out.println("not select course");
        else {
            File f = new File(path);
            VirtualMachine vm = Option.user_online.getVM(Option.course_select);
            ser(f, vm);
            System.out.println("uploadVM success");
        }
    }

    static void downloadVM(String path) throws Exception {
        if (Option.user_online == null)
            System.out.println("not log in");
        else if (Option.user_online.role_admin)
            System.out.println("not a student");
        else if (Option.course_select == null)
            System.out.println("not select course");
        else {
            File f = new File(path);
            dser(f);
            System.out.println("downloadVM success");
        }
    }
}
