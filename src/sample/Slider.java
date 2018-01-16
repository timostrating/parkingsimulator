import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;


//package sample;

public class Slider extends JPanel {
    static final int SPEED_MIN = 0;
    static final int SPEED_MAX = 10;
    static final int SPEED_INIT = 1;

        public Slider() {
            //JPanel topPanel = new JPanel(new BorderLayout());
            //topPanel.add(new JSlider());

            JPanel bottomPanel = new JPanel(new GridLayout(2, 2));
            bottomPanel.add(new JSlider(JSlider.HORIZONTAL, SPEED_MIN, SPEED_MAX, SPEED_INIT));
            bottomPanel.add(new JPanel());

            setLayout(new GridLayout(1, 2));
            //add(topPanel);
            add(bottomPanel);
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable(){
                public void run() {
                    JOptionPane.showMessageDialog(null, new Slider());
                }
            });
        }
}

