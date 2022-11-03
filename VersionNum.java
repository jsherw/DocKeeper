/**
 * @author James Sherwood
 * Object class to hold the program's version number
 */
public class VersionNum {
    private final double versionNum = 0.1;
    private final String group = "Team Gamma";
    private String userName;
    private String userEmail;

    public VersionNum(){
    }
    public VersionNum(String name, String email ){
        setUserName(name);
        setUserEmail(email);
    }

    public double getVersionNum() {return versionNum;}
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getUserEmail(){return this.userEmail;}
    public String getGroup(){return this.group;}
}
