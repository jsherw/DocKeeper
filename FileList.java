import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class FileList {
    String[] myFiles;
    String[] tags = {"Kitchen", "Living Room", "Dining Room", "Bedroom", "Bathroom", "Garage", "Other"};
    Map<String, String> fileAddresses;
    File masterList = new File("FileText.txt");
    FileWriter fw;
    BufferedWriter bw;
    PrintWriter out;
    BufferedReader br;

    public FileList() throws FileNotFoundException {
        myFiles = display();
    }

    /**
     * @author James Sherwood
     * Method: Add a new file address to master list.
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
     * Method: Create list of files to be displayed to the user.
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
     * Method: Permanently remove a file address from the masterlist.
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

    /**
     * @author James Sherwood
     * Method: Associate a searchable tag with a file name.
     * @param itemName name of file to be tagged
     * @param tag selected tag to be added
     * @throws IOException file not found
     */
    public void addTag(String itemName, String tag) throws IOException {
        File tempFile = new File("tempFile.csv");
        br = new BufferedReader(new FileReader("tagFile.csv"));
        boolean isNewTag = true;
        boolean isNewItem = false;
        boolean isExisting = false;
        String row;
        while((row = br.readLine()) != null){
            String[] currTags = row.split(",");
            if(currTags[0].equals(itemName)){ //if item exists on tag list
                isExisting = true;
                for (String t : currTags) {
                    if (tag.equals(t)) { //if the item has that tag already
                        isNewTag = false;
                        break;
                    }
                }
            }
                if(!isExisting){
                    isNewItem = true;
                    isNewTag = false;
                } else{
                    isNewItem = false;
                }
        }
        br.close();

        //add new tag to existing object
        if(isNewTag){
            br = new BufferedReader(new FileReader("tagFile.csv"));
            out = new PrintWriter(new FileWriter(tempFile));
            StringBuilder newLine = new StringBuilder();
            String curr;
            while(((curr = br.readLine()) != null)) {
                String[] line = curr.split(",");
                if(line[0].equals(itemName)){
                    for (String s : line) {
                        newLine.append(s).append(",");
                    }
                    continue;
                }
                out.write(curr + System.getProperty("line.separator"));
                out.write(newLine + tag + ",");
            }
            br.close();
            out.close();

            Path source = Paths.get(tempFile.getPath());
            Files.move(source, source.resolveSibling("tagFile.csv"), StandardCopyOption.REPLACE_EXISTING);
        }
        //add new item to the csv
        if(isNewItem){
            System.out.println("if(isNewItem");
            fw = new FileWriter("tagFile.csv", true);
            fw.write(System.getProperty("line.separator"));
            fw.write(itemName);
            fw.append(",");
            fw.append(tag);
            fw.append(",");
            fw.close();
        }
    }

    /**
     * @author James Sherwood
     * Method: Search the .csv file for addresses associated with a particular tag.
     * @param str tag to be searched for
     * @return Array of files matching tag
     * @throws IOException csv file not found.
     */
    String[] search(String str) throws IOException {
        br = new BufferedReader(new FileReader("tagFile.csv"));
        ArrayList<String> temp = new ArrayList<>();
        String row;
        while((row = br.readLine()) != null){
            String[] tags = row.split(",");
            for(String i: tags){
                if (i.equals(str)){
                    temp.add(tags[0]);
                }
            }
        }

        String[] matchedItems = new String[temp.size()];
        for (int i = 0; i < temp.size(); i++){
            matchedItems[i] = temp.get(i);
        }

        br.close();
        return matchedItems;
    }
}
