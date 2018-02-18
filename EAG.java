import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
//errorautogradr

public class EAG
{
    private static ImageIcon main = new ImageIcon("small.png");
    
    public static void error1()
    {
        JOptionPane.showMessageDialog(null, "Expected Results File Does Not Exist", "AUTOGRADR: FATAL ERROR", JOptionPane.ERROR_MESSAGE, main);
        System.exit(0);
    }
    
    public static void error2()
    {
        JOptionPane.showMessageDialog(null, "Unable to Write to Results Directory", "AUTOGRADR: FATAL ERROR", JOptionPane.ERROR_MESSAGE, main);
        System.exit(0);
    }
    
    public static void fatal()
    {
        JOptionPane.showMessageDialog(null, "Unknown Program Corruption Error", "AUTOGRADR: FATAL ERROR", JOptionPane.ERROR_MESSAGE, main);
        System.exit(0);
    }
    
    public static void error3()
    {
        JOptionPane.showMessageDialog(null, "Student Directory/Files has Errors", "AUTOGRADR: FATAL ERROR", JOptionPane.ERROR_MESSAGE, main);
        System.exit(0);
    }
    
    public static void error4()
    {
        JOptionPane.showMessageDialog(null, "Error with Tester Class", "AUTOGRADR: FATAL ERROR", JOptionPane.ERROR_MESSAGE, main);
        System.exit(0);
    }
}