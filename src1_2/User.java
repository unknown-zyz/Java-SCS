
public class User {
    private int number;
    private String id;
    private String name;//名
    private String surname;//姓
    private String email;
    private String pwd;
    private int job;//本科un1 SY2 ZY3 BY4 老师0

    public String getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public int getJob() {
        return job;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setJob(int job) {
        this.job = job;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }


//    public String toString() {
//
//        return  "Name: " + name +surname+"\n"+
//                "number=" + number +
//                ", id='" + id + '\'' +
//                ", name='" + name + '\'' +
//                ", surname='" + surname + '\'' +
//                ", email='" + email + '\'' +
//                ", pwd='" + pwd + '\'' +
//                ", job=" + job +
//                '}';
//    }
}
