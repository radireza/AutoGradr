public class Student
{
    private String name;
    private String fileName;
    private boolean pass;
    
    public Student (String name, String fileName)
    {
        this.name = name;
        this.fileName = fileName;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getFileName()
    {
        return fileName;
    }
    
    public void setPass(boolean pass)
    {
        this.pass = pass;
    }
    
    public boolean getPass()
    {
        return pass;
    }
    
    public String toString()
    {
        return name + " " + fileName + " " + pass;
    }
}