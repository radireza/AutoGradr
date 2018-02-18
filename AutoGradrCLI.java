import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import org.apache.commons.io.FileUtils;
import java.io.InputStreamReader;
import java.awt.Desktop;

//ALL HELP LEADS TO ERROR
public class AutoGradrCLI
{
    private String[] args;
    private Options options = new Options();
    private CommandLine cmd = null;
    private String className;

    public AutoGradrCLI(String[] args)
    {
        this.args = args;
        options.addOption("h", "help", false, "Shows help for this program.");
        options.addOption("f", "folder", true, "The folder containing all the student .java files");
        options.addOption("e", "expect", true, "The expected output of the program");
        options.addOption("r", "result", true, "The place where the spreadsheet will be placed in");
        options.addOption("t", "tester", true, "The tester class you will be using to determine if the students' programs are running correctly");
    }

    public void parse()
    {
        CommandLineParser parser = new BasicParser();
        try
        {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("h"))
            {
                help();
            }
            //remember to include help when options don't work out
            else if (cmd.hasOption("f") && cmd.hasOption("e") && cmd.hasOption("r") && cmd.hasOption("t"))
            {
                run();
            }
            else
            {
                help();
            }
        }
        catch (Exception e)
        {
            help();
        }
    }

    private void run()
    {
        //clears folder env
        //env is the virtual environment for grading
        try
        {
            Process proc = Runtime.getRuntime().exec("deleter.exe");
            proc.waitFor();
        }
        catch (Exception e)
        {
            EAG.fatal();
        }
        //initializes expected output
        ExpectedOutput expectedOutput = new ExpectedOutput(cmd.getOptionValue("e"));
        //starts student array storing filename, name, and pass/fail
        Student[] students = new Student[new File(cmd.getOptionValue("f")).listFiles().length];
        //loop through, matching student names with filenames
        File[] files = new File(cmd.getOptionValue("f")).listFiles();
        for (int i = 0; i < students.length; i++)
        {
            try
            {
                BufferedReader brTest = new BufferedReader(new FileReader(files[i]));
                String name = brTest.readLine();
                if (name.charAt(2) == '*')
                {
                    name = name.substring(3);
                }
                else
                {
                    name = name.substring(2);
                }
                students[i] = new Student(name, files[i].getName());
            }
            catch(Exception e)
            {
                EAG.error3();
            }
        }
        //checks the class name of the programs
        String className = "";
        try
        {
            BufferedReader brTest = new BufferedReader(new FileReader(cmd.getOptionValue("t")));
            className = brTest.readLine();
            if (className.charAt(2) == '*')
            {
                className = className.substring(3);
            }
            else
            {
                className = className.substring(2);
            }
        }
        catch (Exception e)
        {
            EAG.error4();
        }
        //copy and transfer to folders, compile, check output, set Pass to true or false
        for (int i = 0; i < students.length; i++)
        {
            File studentTemp = new File(cmd.getOptionValue("f") + "\\" + students[i].getFileName());
            File testerTemp = new File(cmd.getOptionValue("t"));
            File dest = new File("env");
            try
            {
                FileUtils.copyFileToDirectory(studentTemp, dest);
                FileUtils.copyFileToDirectory(testerTemp, dest);
            }   
            catch (Exception e)
            {
                EAG.fatal();
            }
            studentTemp = new File("env\\" + students[i].getFileName());
            File classTemp = new File("env\\" + className + ".java");
            studentTemp.renameTo(classTemp);
            try
            {
                Process proc = Runtime.getRuntime().exec("compiler.exe");
                proc.waitFor();
            }
            catch (Exception e)
            {
            }
            String output = "";
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "java -cp " + System.getProperty("user.dir") + "\\env\\ " + className + "Tester");
            builder.redirectErrorStream(true);
            try
            {
                Process p = builder.start();
                BufferedReader bR = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line = "";
                while ((line = bR.readLine()) != null)
                {
                    output = output + line + "\n";
                }
                bR.close();
            }
            catch(Exception e)
            {
                EAG.fatal();
            }
            if (output.equals(expectedOutput.getOutput()))
            {
                students[i].setPass(true);
            }
            else
            {
                students[i].setPass(false);
            }
            try
            {
                Process proc = Runtime.getRuntime().exec("deleter.exe");
                proc.waitFor();
            }
            catch (Exception e)
            {
                EAG.fatal();
            }
            //FOR TESTING PURPOSES
            //System.out.println(students[i].getName());
            //System.out.println(expectedOutput.getOutput());
            //System.out.println("NEXT");
            //System.out.println(output);
        }
        //makes the spreadsheet
        MakeOutput.complete(students, cmd.getOptionValue("r"), className);
        //attempts to open up the Excel sheet
        this.className = className;
    }
    
    public void showSpreadSheet()
    {
        try
        {
            File spreadsheet = new File (cmd.getOptionValue("r") + "\\resultsOf" + className + ".xls");
            Desktop.getDesktop().open(spreadsheet);
        }
        catch (Exception e)
        {
            EAG.fatal();
        }
    }

    private void help()
    {
        HelpFormatter formater = new HelpFormatter();
        formater.printHelp(" ", options);
        System.out.println("Remember to leave all student files as .java (Expected output does not need to be in .java) and please specify the result's directory.");
        System.out.println("Make sure the tester class follows the format className + Tester.");
        System.out.println("Also, please make sure the students only leave the first line with their name (in comments).");
        System.out.println("Lastly, remember to comment the classname (for the classes getting tested) in the first line of the tester.");
        System.out.println("Please note that this program ignores the difference betwee print and println");
        System.out.println("This project is made possible by Will Liu (BitsByWill), Anjee Feng, and Daniel Guerrero");
        System.out.println("This program is currently at version 1.0.0");
        System.exit(0);
    }
}