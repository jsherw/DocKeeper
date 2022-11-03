import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Iteration1 extends JFrame {
    VersionNum myVersion;
    JFrame myFrame;
    JPanel homePanel, profPanel, aboutPanel, buttonPanel, myCurrPanel;
    JButton myHomeBtn,myProfBtn, myAboutBtn;

    /**
     * @author James Sherwood
     * Structures the GUI frame
     */
    public Iteration1() throws IOException {
        //initialize frame
        myFrame = new JFrame();
        myFrame.setSize(500,500);
        myFrame.setLayout(new BorderLayout());
        myFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //initialize panels and set layout
        homePanel = myHomePanel();
        homePanel.setLayout(null);
        profPanel = myProfilePanel();
        profPanel.setLayout(null);
        aboutPanel = myAboutPanel();
        aboutPanel.setLayout(null);

        //initialize buttons
        myHomeBtn = new JButton("Home");
        myHomeBtn.setBounds(50, 350, 100, 20);
        myHomeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePanel(e);
            }
        });
        myProfBtn = new JButton("Profile");
        myProfBtn.setBounds(170, 350, 100, 20);
        myProfBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePanel(e);
            }
        });
        myAboutBtn = new JButton("About");
        myAboutBtn.setBounds(290, 350, 100, 20);
        myAboutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePanel(e);
            }
        });

        //initialize button panel
        buttonPanel = new JPanel();
        buttonPanel.add(myHomeBtn);
        buttonPanel.add(myProfBtn);
        buttonPanel.add(myAboutBtn);

        myCurrPanel = homePanel;
        myFrame.getContentPane().add(myCurrPanel, BorderLayout.CENTER);
        myFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        myFrame.setVisible(true);

    }

    /**
     * @author James Sherwood
     * Change display to user's selected panel.
     * @param e input from button press.
     */
      private void changePanel(ActionEvent e) {
        if(e.getSource() == myHomeBtn){
            myFrame.remove(myCurrPanel);
            myFrame.remove(buttonPanel);
            myCurrPanel = homePanel;
            myFrame.add(myCurrPanel, BorderLayout.CENTER);
            myFrame.add(buttonPanel, BorderLayout.SOUTH);
            myFrame.revalidate();
            myFrame.repaint();

        } else if(e.getSource() == myProfBtn){
            myFrame.remove(myCurrPanel);
            myFrame.remove(buttonPanel);
            myCurrPanel = profPanel;
            myFrame.add(myCurrPanel, BorderLayout.CENTER);
            myFrame.add(buttonPanel, BorderLayout.SOUTH);
            myFrame.revalidate();
            myFrame.repaint();
        } else{
            myFrame.remove(myCurrPanel);
            myFrame.remove(buttonPanel);
            myCurrPanel = myAboutPanel();
            myCurrPanel.setLayout(null);
            myFrame.add(myCurrPanel, BorderLayout.CENTER);
            myFrame.add(buttonPanel, BorderLayout.SOUTH);
            myFrame.revalidate();
            myFrame.repaint();
        }
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

        Image myImage = ImageIO.read(getClass().getResource("DocKeeper-logos.jpeg"));
        myImage = myImage.getScaledInstance(475, 400, java.awt.Image.SCALE_SMOOTH);
        ImageIcon myImageIcon = new ImageIcon(myImage);
        picLabel = new JLabel(myImageIcon);
        picLabel.setBounds(1,1,485,400);
        myPanel.add(picLabel);
        return myPanel;
    }

    /**
     * @author James Sherwood
     * @return profile panel
     */
    private JPanel myProfilePanel(){
        JPanel myPanel;
        JLabel myUserName, myEmail;
        JButton myButton;
        JTextField myNameText, myEmailText;

        myPanel = new JPanel();

        myUserName = new JLabel("Name: ");
        myUserName.setBounds(150, 150, 60, 20);
        myNameText = new JTextField();
        myNameText.setBounds(190,150,140,20);

        myEmail= new JLabel("Email: ");
        myEmail.setBounds(150,200,60,20);
        myEmailText = new JTextField();
        myEmailText.setBounds(190,200,140,20);

        myButton = new JButton("Submit");
        myButton.setBounds(200, 240, 100, 20);
        myButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s1 = myNameText.getText();
                String s2 = myEmailText.getText();
                myVersion = new VersionNum(s1,s2);
                myNameText.setText("");
                myEmailText.setText("");
            }
        });

        myPanel.add(myUserName);
        myPanel.add(myNameText);
        myPanel.add(myEmail);
        myPanel.add(myEmailText);
        myPanel.add(myButton);

        return myPanel;
    }

    /**
     * @author James Sherwood
     * @return about panel
     */
    private JPanel myAboutPanel(){

        JPanel myPanel;
        JLabel myVersionLabel, myGroupLabel, myDevLabel, devName1, myIntro;

        myPanel = new JPanel();
        if(myVersion != null && myVersion.getUserName() != null){
            myIntro = new JLabel("This app is registered to: " + myVersion.getUserName());
            myIntro.setBounds(40,140,300,20);
        } else{
            myVersion = new VersionNum();
            myIntro = new JLabel("This app is not yet registered.");
            myIntro.setBounds(40,140,200,20);

        }
        myVersionLabel = new JLabel("Version Number: " + myVersion.getVersionNum());
        myVersionLabel.setBounds(180, 120, 200,20);
        myGroupLabel = new JLabel("This app provided by " + myVersion.getGroup());
        myGroupLabel.setBounds(40, 280, 220, 20);

        myDevLabel = new JLabel("Developers: ");
        myDevLabel.setBounds(40, 300, 140, 20);
        devName1 = new JLabel("1. James Sherwood");
        devName1.setBounds(120, 320, 140, 20);

        myPanel.add(myIntro);
        myPanel.add(myVersionLabel);
        myPanel.add(myGroupLabel);
        myPanel.add(myDevLabel);
        myPanel.add(devName1);

        return myPanel;
    }

    /**
     * @author James Sherwood
     * Driver program
     * @param args
     */
    public static void main(String[] args) throws IOException {
        new Iteration1();
    }
}