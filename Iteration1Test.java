import org.junit.Test;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import static org.junit.Assert.*;

public class Iteration1Test {
    Iteration1 testFrame;
    Image testImage;
    ImageIcon testIcon;
    JPanel testPanel;
    JLabel testLabel;
    JButton testBtn;

    /**
     * @author James Sherwood
     * @throws IOException image not found
     */
    @Test
    public void panelTest() throws IOException {
        testFrame = new Iteration1();
        testFrame.setSize(500,500);
        testFrame.setLayout(new BorderLayout());
        testFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        testPanel = new JPanel();
        testPanel.setLayout(null);
        testFrame.add(testPanel);

        testFrame.setVisible(true);
        assertTrue(testPanel.isVisible());
    }

    /**
     * @author James Sherwood
     * @throws IOException image not found
     */
    @Test
    public void imageTest() throws IOException{
        testFrame = new Iteration1();
        testFrame.setSize(500,500);
        testFrame.setLayout(new BorderLayout());
        testFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        testPanel = new JPanel();
        testPanel.setLayout(null);

        testImage = ImageIO.read(getClass().getResource("DocKeeper-logos.jpeg"));
        testImage = testImage.getScaledInstance(475, 400, java.awt.Image.SCALE_SMOOTH);
        testIcon = new ImageIcon(testImage);

        testLabel = new JLabel(testIcon);
        testPanel.add(testLabel);
        testFrame.add(testPanel);
        testFrame.setVisible(true);

        assertTrue(testLabel.isValid());
    }

    /**
     * @author James Sherwood
     * @throws IOException image not found
     */
    @Test
    public void btnTest() throws IOException {
        final int[] counter = {0};
        testFrame = new Iteration1();
        testFrame.setSize(500,500);
        testFrame.setLayout(new BorderLayout());
        testFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        testPanel = new JPanel();
        testPanel.setLayout(null);

        testBtn = new JButton();
        testBtn.setBounds(10,10,10,10);
        testBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                counter[0] = 1 + counter[0];
            }
        });
        testFrame.setVisible(true);
        testBtn.doClick();

        assertEquals(1, counter[0]);
    }
}
