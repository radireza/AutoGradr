import java.io.FileOutputStream;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;

public class MakeOutput
{
    public static void complete(Student[] students, String filename, String className)
    {
        try
        {
            filename = filename + "\\resultsOf" + className + ".xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Results of " + className);
            HSSFRow rowhead = sheet.createRow((short)0);
            rowhead.createCell((short)0).setCellValue("Name");
            rowhead.createCell((short)1).setCellValue("File");
            rowhead.createCell((short)2).setCellValue("Pass/Fail");
            //alphabetize
            for (int i = 0; i < students.length; i++)
            {
                for (int j = 0; j < i; j++)
                {
                    if (students[i].getName().compareTo(students[j].getName()) > 0)
                    {
                        Student temp = students[j];
                        students[j] = students[i];
                        students[j] = temp;
                    }
                }
            }
            //excel
            for (int i = 0; i < students.length; i++)
            {
                HSSFRow row = sheet.createRow((short)(i + 1));
                row.createCell((short)0).setCellValue(students[i].getName());
                row.createCell((short)1).setCellValue(students[i].getFileName());
                if (students[i].getPass())
                {
                    row.createCell((short)2).setCellValue("PASS");
                }
                else
                {
                    row.createCell((short)2).setCellValue("FAIL");
                }
            }
            FileOutputStream fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();
        }
        catch (Exception e)
        {
            EAG.error2();
        }
    }
}