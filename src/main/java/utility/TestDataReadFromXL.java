package utility;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class TestDataReadFromXL {



    public static HSSFSheet readExcel(String filePath,String fileName,String sheetName) throws IOException {
        //Create a object of File class to open xlsx file
        File file =    new File(filePath+"\\"+fileName);
        //Create an object of FileInputStream class to read excel file
        FileInputStream inputStream = new FileInputStream(file);
        HSSFWorkbook workbook = null;
        //Find the file extension by spliting file name in substring and getting only extension name
        String fileExtensionName = fileName.substring(fileName.indexOf("."));
        //Check condition if the file is xlsx file
        if(fileExtensionName.equals(".xlsx")){
            //If it is xlsx file then create object of XSSFWorkbook class
           // workbook = new XSSFWorkbook(inputStream);
        }
        //Check condition if the file is xls file
        else {
            //If it is xls file then create object of XSSFWorkbook class
            workbook = new HSSFWorkbook(inputStream);
        }
        //Read sheet inside the workbook by its name
        HSSFSheet hSSFSheet = workbook.getSheet(sheetName);
        //Find number of rows in excel file
        return hSSFSheet;

    }

}






