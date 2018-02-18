public class Main{
    public static void main (String [] args){
        if (args.length == 0)
        {
            GUI.run();
        }
        else
        {
            AutoGradrCLI run = new AutoGradrCLI(args);
            run.parse();
        }
    }
}