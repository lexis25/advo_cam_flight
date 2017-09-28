import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel {


     void createWindow() {
        JFrame frame = new JFrame();
        JButton createFolders = new JButton("create folder");
        createFolders.setSize(100,50);
        createFolders.addActionListener(new CreateFolderListener());

        JButton createScheduleTXT = new JButton("create schedule");
        createScheduleTXT.setSize(100,50);
        createScheduleTXT.addActionListener(new CreateSceduleTXTListener());

        frame.getContentPane().add(BorderLayout.EAST,createFolders);
        frame.getContentPane().add(BorderLayout.WEST,createScheduleTXT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setVisible(true);
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