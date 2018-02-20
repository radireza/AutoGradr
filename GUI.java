import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JWindow;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.io.File;

public class GUI
{
    public static void run()
    {
        JWindow window = new JWindow();
        ImageIcon icon = new ImageIcon("TheAutoGradrLogo.png");
        window.getContentPane().add(new JLabel("", icon, SwingConstants.CENTER));
        window.setBounds(600, 300, 140, 130);
        window.setVisible(true);
        try 
        {
            Thread.sleep(3000);
        } 
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }
        window.setVisible(false);
        String display = "Remember to leave all input files as .java (Expected output does not need to be in .java)\n\nAlso, please make sure the students only leave the first line with their name.\n\nPlease make sure to comment the class name on the first line of the tester.\n\n\nThis project is made possible by Will Liu (BitsByWill), Anjee Feng, and Daniel Guerrero\nThis program is currently at version 1.0.0";
        JOptionPane.showMessageDialog(null,display, "AUTOGRADR", JOptionPane.INFORMATION_MESSAGE);
        JFileChooser folderBrowser = new JFileChooser();
        folderBrowser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        JFileChooser expectedResultsBrowser = new JFileChooser();
        JFileChooser resultsBrowser = new JFileChooser();
        resultsBrowser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        JFileChooser testerBrowser = new JFileChooser();
        JButton button = new JButton("Grade! :)");
        File folder = null;
        File expectedResults = null;
        File results = null;
        File tester = null;
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(new JLabel("Student Folder:"));
        panel.add(folderBrowser);
        panel.add(new JLabel("\n"));
        panel.add(new JLabel("Expected Results:"));
        panel.add(expectedResultsBrowser);
        panel.add(new JLabel("\n"));
        panel.add(new JLabel("Results:"));
        panel.add(resultsBrowser);
        panel.add(new JLabel("\n"));
        panel.add(new JLabel("Tester:"));
        panel.add(testerBrowser);
        panel.add(new JLabel("\n"));
        panel.add(new JLabel("\n"));
        Object [] options = {"Grade! :)", "Don't Grade :("};
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension( 500, 500 ));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);        
        icon = new ImageIcon("TheAutoGradrLogo.png");
        int n = JOptionPane.showOptionDialog(null, scrollPane, "AUTOGRADR", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
        if(n == 0)
        {
            folder = folderBrowser.getSelectedFile();
            expectedResults = expectedResultsBrowser.getSelectedFile();
            results = resultsBrowser.getSelectedFile();
            tester = testerBrowser.getSelectedFile();
            String[] temp = {"-f", folderBrowser.getSelectedFile().toString(), "-e", expectedResultsBrowser.getSelectedFile().toString(), "-r", resultsBrowser.getSelectedFile().toString(), "-t", testerBrowser.getSelectedFile().toString()};
            AutoGradrCLI run = new AutoGradrCLI(temp);
            run.parse();
            options[0] = "Yes please :)";
            options [1] = "No, thanks";
            n = JOptionPane.showOptionDialog(null, "Do you want to display the excel sheet?", "AUTOGRADR", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
            if(n==0){
                run.showSpreadSheet();
            }
            JOptionPane.showMessageDialog(null, "Thanks for using this program!", "AUTOGRADR", JOptionPane.ERROR_MESSAGE, icon);
        }
    }
}
