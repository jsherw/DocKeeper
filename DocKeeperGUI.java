import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

public class DocKeeperGUI extends JFrame {
    UserInfo u;
    String myFilePath = ".";
    JFrame myFrame;
    JPanel homePanel, loginPanel, aboutPanel, buttonPanel, filePanel, myCurrPanel;
    JButton homeBtn, loginBtn, aboutBtn, filesBtn, submitBtn, uploadBtn, deleteBtn, exportBtn, importBtn;
    JFileChooser fc = new JFileChooser(myFilePath);

    /**
     * @author James Sherwood
     * Structures the GUI frame
     */
    public DocKeeperGUI() throws IOException {
        //initialize frame
        myFrame = new JFrame("DocKeeper");
        myFrame.setSize(500,500);

        myFrame.setLayout(new GridBagLayout());
        myFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        GridBagConstraints c = new GridBagConstraints();
        //initialize panels
        homePanel = myHomePanel();
        aboutPanel = myAboutPanel();
        loginPanel = myLogInPanel();
        filePanel = myFilePanel();

        //initialize buttons
        homeBtn = new JButton("Home");
        homeBtn.addActionListener(e -> {
            try {
                changePanel(e);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        loginBtn = new JButton("Log In");
        loginBtn.addActionListener(e -> {
            try {
                changePanel(e);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        aboutBtn = new JButton("About");
        aboutBtn.addActionListener(e -> {
            try {
                changePanel(e);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        filesBtn = new JButton("Files");
        filesBtn.addActionListener(e -> {
            try {
                changePanel(e);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        //initialize button panel
        buttonPanel = new JPanel();
        buttonPanel.add(homeBtn);
        buttonPanel.add(loginBtn);
        buttonPanel.add(aboutBtn);

        buttonPanel.add(filesBtn);

        myCurrPanel = homePanel;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 2;
        myFrame.getContentPane().add(myCurrPanel, c);
        c.gridx = 0;
        c.gridy = 3;

        myFrame.getContentPane().add(buttonPanel, c);

        myFrame.pack();
        myFrame.setVisible(true);
    }

    /**
     * @author James Sherwood
     * @return  home panel
     * @throws IOException if image not found.
     */
    private JPanel myHomePanel() throws IOException {
        JPanel myPanel;
        JLabel picLabel;

        myPanel = new JPanel();
        myPanel.setLayout(new GridBagLayout());

        //home page image
        Image myImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("DocKeeper-logos.jpeg")));
        myImage = myImage.getScaledInstance(475, 400, java.awt.Image.SCALE_SMOOTH);
        ImageIcon myImageIcon = new ImageIcon(myImage);
        picLabel = new JLabel(myImageIcon);
        picLabel.setBounds(1,1,485,400);
        myPanel.add(picLabel);

        return myPanel;
    }


    /**
     * @author James Sherwood
     * @return login panel
     */
    private JPanel myLogInPanel(){
        final int PADDING = 30;
        JPanel myPanel, welcomePanel, formPanel, btnPanel;
        JLabel myUserName, myEmail, welcomeMessage;
        JTextField myNameText;
        JTextField emailField;

        //change welcome message based on log-in
        welcomePanel = new JPanel(new FlowLayout(FlowLayout.CENTER,PADDING,PADDING));
        if(u.getIsUser()){
            welcomeMessage = new JLabel("Welcome, " + u.getMyUserName());
        } else{
            welcomeMessage = new JLabel("Welcome. Please Login.");
        }
        welcomePanel.add(welcomeMessage);

        formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        myUserName = new JLabel("Name: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy =0;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0,20,0,0);
        formPanel.add(myUserName, c);

        myEmail = new JLabel("Email: ");
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy= 1;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0,20,0,0);
        formPanel.add(myEmail,c);

        myNameText = new JTextField(50);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.1;
        c.ipadx = 200;
        formPanel.add(myNameText, c);

        emailField = new JTextField(150);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0.001;
        c.ipadx = 200;
        formPanel.add(emailField,c);

        btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER,PADDING,PADDING));

        submitBtn = new JButton("Submit");
        submitBtn.addActionListener(e -> {
            String s1 = myNameText.getText();
            String s2 = String.valueOf(emailField.getText());
            try {
                u = new UserInfo(s1,s2);
                if(u.getIsUser()){
                    //reload page
                    changePanel(e);
                }
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            if (u.getIsAdmin()){
                uploadBtn.setEnabled(true);
                deleteBtn.setEnabled(true);
                exportBtn.setEnabled(true);
                importBtn.setEnabled(true);
            }
            myNameText.setText("");
            emailField.setText("");
        });
        btnPanel.add(submitBtn);

        myPanel = new JPanel(new BorderLayout());
        myPanel.setPreferredSize(new Dimension(400,400));

        //hide formPanel after user has signed in
        if(!u.getIsUser()){
            myPanel.add(welcomePanel,BorderLayout.NORTH);
            myPanel.add(formPanel, BorderLayout.CENTER);
            myPanel.add(btnPanel, BorderLayout.SOUTH);
        } else {
            myPanel.add(welcomePanel, BorderLayout.CENTER);
            myPanel.add(btnPanel, BorderLayout.SOUTH);
        }
        return myPanel;
    }

    /**
     * @author James Sherwood
     * @return about panel
     */
    private JPanel myAboutPanel(){
        JPanel versionPanel, registrationPanel, devPanel;
        JPanel mainPanel = new JPanel();
        JLabel versionLabel, groupLabel, devLabel, devName1, introLabel;

        versionPanel = new JPanel(new BorderLayout());

        //Change display when logged in.
        if(u != null && u.getMyUserName() != null){
            introLabel = new JLabel("This app is registered to: " + u.getMyUserName());
        } else{
            u = new UserInfo();
            introLabel = new JLabel("This app is not yet registered.");
        }

        versionLabel = new JLabel("Version Number: " + u.getVersionNum());
        versionPanel.add(introLabel, BorderLayout.NORTH);
        versionPanel.add(versionLabel, BorderLayout.CENTER);

        registrationPanel = new JPanel(new BorderLayout());
        groupLabel = new JLabel("This app provided by " + u.getGroup());
        registrationPanel.add(groupLabel, BorderLayout.LINE_START);


        devPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        devLabel = new JLabel("Developers: ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy =0;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0,20,0,0);
        devPanel.add(devLabel, c);

        devName1 = new JLabel("1. James Sherwood");
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0.1;
        c.ipadx = 200;
        devPanel.add(devName1, c);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(400,400));
        mainPanel.add(versionPanel, BorderLayout.NORTH);
        mainPanel.add(devPanel, BorderLayout.CENTER);
        mainPanel.add(registrationPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    /**
     * @author James Sherwood
     * @return filePanel
     * @throws FileNotFoundException for file masterList.
     */
    private JPanel myFilePanel() throws IOException {

        DefaultListModel<String> dlm = new DefaultListModel<>();
        FileList fl = new FileList();
        JList<String> fileDisplay = new JList<>(dlm);

        JPanel mainPanel = new JPanel(new GridBagLayout());

        //initialize List Model
        for(int i = 0; i < fl.display().length; i++){
            dlm.addElement(fl.myFiles[i]);
        }

        mainPanel.setPreferredSize(new Dimension(400,400));
        GridBagConstraints c = new GridBagConstraints();

        //Scrollpane to display file names.
        JScrollPane fileNames = new JScrollPane(fileDisplay);
        fileNames.setPreferredSize(new Dimension(200,200));
        c.gridx = 0;
        c.gridy = 1;
        mainPanel.add(fileNames,c);

        uploadBtn = new JButton("New File");
        uploadBtn.setEnabled(false);

        uploadBtn.addActionListener(e -> {
            var response = fc.showOpenDialog(null);
            if (response == JOptionPane.OK_OPTION){
                myFilePath = fc.getSelectedFile().getAbsolutePath();
                try {
                    fl.addToFileList(myFilePath);
                    dlm.addElement(String.valueOf(String.valueOf(Paths.get(myFilePath).getFileName())));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        deleteBtn = new JButton("Delete");
        deleteBtn.setEnabled(false);
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = fileDisplay.getSelectedIndex();
                String str = (String.valueOf(dlm.get(index)));
                try {
                    fl.remove(str);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                dlm.removeElementAt(index);
            }
        });

        exportBtn = new JButton("Export");
        exportBtn.setEnabled(false);
        exportBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    u.export();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        importBtn = new JButton("Import");
        importBtn.setEnabled(false);
        importBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    u.importFile();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JPanel fileBtnPanel = new JPanel();
        fileBtnPanel.add(uploadBtn);
        fileBtnPanel.add(deleteBtn);
        fileBtnPanel.add(exportBtn);
        fileBtnPanel.add(importBtn);
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy =GridBagConstraints.SOUTH;
        mainPanel.add(fileBtnPanel, c);

        return mainPanel;
    }

    /**
     * @author James Sherwood
     * Change display to user's selected panel.
     * @param e input from button press.
     */
      private void changePanel(ActionEvent e) throws FileNotFoundException {
        if(e.getSource() == homeBtn){
            constraints(homePanel);
        }else if(e.getSource() == loginBtn || e.getSource() == submitBtn){
            constraints(myLogInPanel());
        } else if(e.getSource() == filesBtn){
            constraints(filePanel);
        }
        else{
            constraints(aboutPanel);
        }
    }

    /**
     * @author James Sherwood
     * constraints used when changing panels.
     * @param homePanel default grid constraints.
     */
    private void constraints(JPanel homePanel) {
        myFrame.remove(myCurrPanel);
        myFrame.remove(buttonPanel);
        GridBagConstraints c = new GridBagConstraints();
        myCurrPanel = homePanel;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 2;
        myFrame.getContentPane().add(myCurrPanel, c);
        c.gridx = 0;
        c.gridy = 3;

        myFrame.getContentPane().add(buttonPanel, c);

        myFrame.revalidate();
        myFrame.repaint();
    }

    /**
     * @author James Sherwood
     * Driver program
     * @param args default
     */
    public static void main(String[] args) throws IOException {
        new DocKeeperGUI();
    }
}