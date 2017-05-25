import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel {

    static void createWindow() {
        JFrame frame = new JFrame();
        JButton createFolders = new JButton("create folder");
        JButton createScheduleTXT = new JButton("create schedule");

        frame.getContentPane().add(createFolders);
        frame.getContentPane().add(createScheduleTXT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(480,480);
    }

    class CreateFolderListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {

        }
    }
    class CreateSceduleTXTListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {

        }
    }


}