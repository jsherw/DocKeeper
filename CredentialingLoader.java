/**
 * @author Uladzimir Hanevich
 */
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;

public class CredentialingLoader {
    private static boolean isAdmin = false;
    private static boolean isUser = false;

    /** The delimiter used in text files. */
    private static final String FILE_DELIMITER = ";";

    /** File location variable. */
    private static final String FILE_LOCATION = "savedUsers.txt";

    /**
     * A private constructor, to prevent external instantiation.
     */
    private CredentialingLoader() {

    }

    /**
     * Attempts to match a user name and password with the information stored in the
     * credentialing system.
     *
     * @author Uladzimir Hanevich
     * @param theUsername the user name to attempt
     * @param thePassword the password to attempt
     * @return result containing the error message if login is not successful, empty if login is OK.
     */
    public static String login(final String theUsername, final String thePassword) {
        String result = "";
        try (Scanner input = new Scanner(Paths.get(FILE_LOCATION))) {
            while (input.hasNextLine()) {
                final String lineAsString = input.nextLine();
                final String credentials = theUsername + FILE_DELIMITER + thePassword;

                System.out.println("lineAsString value (from file): " + lineAsString);
                System.out.println("credentials from input: " + credentials);

                if (lineAsString.equals("*" + credentials)) {
                    System.out.println("You are logged!");
                    isAdmin = true;
//                   new AppGUI(false);  // in loginFrame loginWorker method
                    break;
                } else if(lineAsString.equals(credentials)){
                    System.out.println("You are logged in!");
                    isUser = true;
                    break;
                }
                else if (!checkIfUserExists(theUsername)){
                    result = "User does not exist!";
                    break;

                }
                else {
                    System.out.println("!!! Wrong password!!!!!");
                    result = "Wrong password!";
                }
            }
        } // no file
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

//FIXME
    /**
     * Attempts to add a new user to the credentials file. Note, this method does not perform
     * validation. Any and all validation must be performed before calling this method.
     *
     * @author Uladzimir Hanevich
     * @param theUsername the user name to add
     * @param thePassword the password associated to the user name
     * @return empty String if written, an error message otherwise
     * @throws IllegalArgumentException when any of the arguments are non-null but "empty"
     */
    public static String register(final String theUsername,
                                  final String thePassword) {
        String result = "";
        if ("".equals(theUsername) || "".equals(thePassword)) {
            result = "All paramaters must contain non-empty values"; //FIXME dosn't show up
            throw new IllegalArgumentException("All paramaters must contain non-empty values");
        }

        if (checkIfUserExists(theUsername)) {
            result = "Unfortunately that user name is already taken.";  // if exists show a message
        } else {                                                       // else if NOT exists, then write credentials in the file
            try (PrintWriter printWriter = new PrintWriter(new FileWriter(FILE_LOCATION, true))) {
                printWriter.append(theUsername + FILE_DELIMITER + thePassword.toString() + "\n");
                System.out.println("You are registered!");
//               printWriter.append(System.lineSeparator());
                // Body of this else does:
                // If user doesn't exist, then create this new user and put credentials in the file.
            } catch (final IOException ioException) {
                ioException.printStackTrace();
                result = ioException.getMessage();
            }
        }
        return result;
    }


    /**
     * Attempts to find the username stored in the credentialing system.
     *
     * @author Uladzimir Hanevich
     * @param theUsername the username to look for
     * @return true if the username already exists, false otherwise
     */
    private static boolean checkIfUserExists(final String theUsername) {
        boolean found = false;
        try (Scanner input = new Scanner(Paths.get(FILE_LOCATION))) {
            while (input.hasNextLine() && !found) {
                final String lineAsString = input.nextLine();
                if (!lineAsString.startsWith("#")) {
                    found = theUsername.equalsIgnoreCase(lineAsString.split(FILE_DELIMITER) [0]); // [0] means "read the first element"

                    // ****  Testing   ****
//                   System.out.print("This is " + found);
//                   System.out.print(" because " + theUsername);
//                   System.out.println(" doesn't match " + lineAsString.split(FILE_DELIMITER) [0]);
                }
            }
        }
        catch (final IOException e) {
            e.printStackTrace();
        }

        return found;
    }
    public static boolean getIsAdmin(){
        return isAdmin;
    }
    public static boolean getIsUser(){
        return isUser;
    }

}
