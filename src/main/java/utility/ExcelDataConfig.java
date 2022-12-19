package utility;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by TAhmmed1 on 12/25/2016.
 */
public class ExcelDataConfig {

    Workbook wb = null;
    Sheet sheet1;
    public ExcelDataConfig(String fileNamePass)
    {
        try
        {
            // Read All Files from DataTestFolder
            String fileName ="";

            File folder = new File("DataTest\\");
            File[] listOfFiles = folder.listFiles();

            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    // System.out.println("File " + listOfFiles[i].getName());
                    if(listOfFiles[i].getName().startsWith(fileNamePass)) {

                        fileName = listOfFiles[i].getName();
                        // System.out.println(listOfFiles[i].getName());
                        break;

                    }

                }
            }


            File src = new File("DataTest\\"+fileName);
            FileInputStream fis = new FileInputStream(src);

            String fileExtensionName = fileName.substring(fileName.indexOf("."));

            if(fileExtensionName.equals(".xlsx")){

                //If it is xlsx file then create object of XSSFWorkbook class
                wb = new XSSFWorkbook(fis);

            }

            //Check condition if the file is xls file

            else if(fileExtensionName.equals(".xls")){

                //If it is xls file then create object of XSSFWorkbook class
                wb = new HSSFWorkbook(fis);

            }

        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    public String getdata(int sheetNumber, int row, int column)
    {
        sheet1=wb.getSheetAt(sheetNumber);
        String data = sheet1.getRow(row).getCell(column).getStringCellValue();
        return data;
    }
    public int getRowCount(int sheetIndex)
    {
       int row = wb.getSheetAt(sheetIndex).getLastRowNum();
       row = row+1;
       return row;
    }
}
