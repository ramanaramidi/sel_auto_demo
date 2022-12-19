package utility;

import java.io.*;


/**
 * Created by tahmmed1 on 12/12/2016.
 */
public class TextDataWriterReader {


    public static void logCurrentSession(String sessionName,String SessionStartTime,String jsonFileName) throws IOException {

        String logFile = System.getProperty("user.dir") + "\\src\\main\\resources\\SessionLog.txt";
        Writer output;
        output = new BufferedWriter(new FileWriter(logFile, false));
        output.write(sessionName);
        output.append("\n"+SessionStartTime);
        output.append("\n"+jsonFileName);
        output.close();
    }



    public static String[] getSessionLog() {

        // The name of the file to open.
        String fileName = System.getProperty("user.dir") + "\\src\\main\\resources\\SessionLog.txt";
        // This will reference one line at a time
        String line = null;
        String []logData = new String[5];

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);
            int i=0;
            while((line = bufferedReader.readLine()) != null) {

                logData[i] = line;
                i++;

            }

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
        }
        return logData;
    }



    public static void main(String [] args) throws IOException {

        // The name of the file to open.
        logCurrentSession("test1","test2","");
        String [] mydata = getSessionLog();

        for (int i = 0; i < mydata.length; i++)
            System.out.println( mydata[i] );
    }




}