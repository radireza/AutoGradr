import java.io.FileReader;
import java.io.BufferedReader;

public class ExpectedOutput
{
    private String output;
    
    public ExpectedOutput(String file)
    {
        output = readIt(file);
    }
    
    public String getOutput()
    {
        return output;
    }
    
    private String readIt(String file)
    {
        String originalString = "";
        boolean y = false;
        String line = null;
        do 
        {
            try
            {
                FileReader read = new FileReader(file);
                BufferedReader bR = new BufferedReader(read);
                while ((line = bR.readLine()) != null)
                {
                    originalString = originalString + line + "\n";
                }
                bR.close();
                y = true;
            }
            catch (Exception e)
            {
                y = true;
                EAG.error1();
            }
        } while (y != true);
        return originalString;
    }
}