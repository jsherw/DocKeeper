import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class FileList  {
    String[] myFiles;
    Map<String, String> fileAddresses;
    File masterList = new File("FileText.txt");
    Path filePath = masterList.toPath();
    FileWriter fw;
    BufferedWriter bw;
    PrintWriter out;
    BufferedReader br;

    public FileList() throws FileNotFoundException {
        myFiles = display();
    }

    /**
     * @author James Sherwood
     * @param fileAddress   location of file to add to the list
     * @throws IOException list can't be found.
     */
    void addToFileList(String fileAddress) throws IOException {
        try{
            fw = new FileWriter(masterList, true);
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);
            out.println("\n" + fileAddress);
        }
        catch( IOException e ){
            // File writing failed
        }
        finally{
            try{
                if( out != null ){
                    out.close();
                }
                if( bw != null ){
                    bw.close();
                }
                if( fw != null ){
                    fw.close();
                }
            }
            catch( IOException e ){
                // Unknown exception
            }
        }
    }

    /**
     * @author James Sherwood
     * @return Array of file names.
     * @throws FileNotFoundException masterList not found.
     */
    public String[] display() throws FileNotFoundException {
        Set<String> myFileKeys = new HashSet<>();
        fileAddresses = new HashMap<>();
        Scanner scnr = new Scanner(masterList);
        String myFileAddress;
        Path myFileName;

        while(scnr.hasNextLine()){
            myFileAddress = scnr.nextLine();
            myFileName = Paths.get(myFileAddress).getFileName();
            fileAddresses.put(String.valueOf(myFileName), myFileAddress);
            myFileKeys.add(String.valueOf(myFileName));
        }
        scnr.close();
        return myFileKeys.toArray(new String[0]);
    }

    /**
     * @author James Sherwood
     * @param file name of file
     * @throws IOException FnF
     */
    public void remove(String file) throws IOException {
        String target = fileAddresses.get(file);
        File tempFile = new File("tempFile.txt");
        br = new BufferedReader(new FileReader(masterList));
        out = new PrintWriter(new FileWriter(tempFile));
        String curr;

        //create new text file without deleted filepath
        while(((curr = br.readLine()) != null)) {
            String line = curr.trim();
            if(line.equals(target)){
                continue;
            }
            out.write(curr + System.getProperty("line.separator"));
        }
        out.close();
        br.close();

        //Rename temp.txt to FileText and replace existing file
        Path source = Paths.get(tempFile.getPath());
        Files.move(source, source.resolveSibling("FileText.txt"), StandardCopyOption.REPLACE_EXISTING);
    }

    public String getAddress(String str){
       return fileAddresses.get(str);
    }

    String findFile(String str){
        /*
        Search for particular files according a set criteria.
         */
        return null;
    }
}
