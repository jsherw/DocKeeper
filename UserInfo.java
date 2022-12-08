import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class UserInfo implements Serializable{
    private final double versionNum = 0.2;
    private final String group = "Team Gamma";
    private String myUserName;
    private String myEmail;
    private boolean isAdmin = false;
    private boolean isUser = false;

    public UserInfo(){
        this.myUserName = null;
        this.myEmail = null;
    }
    public UserInfo(String name, String email ) throws FileNotFoundException {
        setMyUserName(name);
        setMyEmail(email);

        isUser = verifyUser();
    }

    /**
     * @author James Sherwood
     * Method: Check for valid user identity, and appropriate level of access in the program.
     * @return true if username & password match
     * @throws FileNotFoundException Unknown file location
     */
    private boolean verifyUser() throws FileNotFoundException {
        File f = new File("SavedUsers.txt");
        Scanner scnr = new Scanner(f);

        while(scnr.hasNext()){
            String currLine = scnr.nextLine();
            //check for admin-level access. Admin names are stored with a prepended '*' character.
            if(currLine.equals("*" + getMyUserName() + getMyEmail())  ) {
                setIsAdmin();
                scnr.close();
                return true;
                //check for user-level access.
            } else if(currLine.equals(getMyUserName() + getMyEmail())){
                scnr.close();
                return true;
            }
        }
        scnr.close();
        return false;
    }

    /**
     * @author James Sherwood
     * Method: Create new text file "export" and copy contents of SavedUsers.txt
     * @throws IOException FileNotFound or Unavailable for action
     */
    public void export() throws IOException {

        File export = new File("export.txt");
        BufferedReader br = new BufferedReader(new FileReader("SavedUsers.txt"));
        PrintWriter out = new PrintWriter(new FileWriter(export));
        String curr;

        while(((curr = br.readLine()) != null)) {
            out.write(curr + System.getProperty("line.separator"));
        }

        out.close();
        br.close();

        //creates new text file called "export".
    }

    /**
     * @author James Sherwood
     * Method: Replace SavedUsers with export.txt
     * @throws IOException
     */
    public void importFile() throws IOException {
        File f = new File("export.txt");
        if(f.exists() && !f.isDirectory()){
            Path source = Paths.get(f.getPath());
            Files.move(source, source.resolveSibling("SavedUsers.txt"), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public double getVersionNum() {return versionNum;}
    public void setMyUserName(String myUserName) {
        this.myUserName = myUserName;
    }
    public String getMyUserName() {
        return myUserName;
    }
    public void setMyEmail(String myEmail) {
        this.myEmail = myEmail;
    }
    public String getMyEmail(){return this.myEmail;}
    public void setIsAdmin(){
        isAdmin = true;
    }
    public boolean getIsAdmin(){
        return this.isAdmin;
    }
    public boolean getIsUser(){
        return this.isUser;
    }
    public String getGroup(){return this.group;}
}
