package utility;
/**
 * Created by tahmmed1 on 5/18/2017.
 */

import common.LoadPropertiesFiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Properties;

public class JdbcSQLServerConnection {

    public static Connection getSqlConnection() throws IOException {
        Connection conn = null;
        String propertiesFile = System.getProperty("user.dir")+"\\src\\main\\resources\\appMonitoring.properties";
        Properties prop = LoadPropertiesFiles.loadProperties(propertiesFile);

        String dbName = prop.getProperty("AppDB.Name"); //"AppMonitoring";
        String serverport=prop.getProperty("AppDB.Port");
        String url = prop.getProperty("AppDB.Url");
        Statement stmt = null;
        Statement stmt2 = null;
        ResultSet result = null;
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String databaseUserName = prop.getProperty("AppDB.UserName");
        String databasePassword = prop.getProperty("AppDB.Password");
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, databaseUserName, databasePassword);
            System.out.println("DB Connected");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }



    // Method for Retrieving for Test Application Test Data.

    public static String[]  getApplicationFeatures(Connection remoteConn, int FeatureID)
    {
        Statement stmt = null;
        ResultSet result = null;
        String qRes [] = new String[6];
        try
        {
            String SQL = "select application_name,component_name,component_display_name,feature_name,feature_display_name,feature_weight from ref_app_feature where feature_id="+FeatureID+" and active_flag=1";
            stmt = remoteConn.createStatement();
            result = stmt.executeQuery(SQL);
            try {
                while (result.next()) {
                    qRes[0] = result.getString(1);
                    qRes[1] = result.getString(2);
                    qRes[2] = result.getString(3);
                    qRes[3] = result.getString(4);
                    qRes[4] = result.getString(5);
                    qRes[5] = result.getString(6);
                }

            } finally {
                result.close();
            }

        }
        catch(SQLException ex)
        {
            System.out.println("SQL Error");
        }
        return qRes;
    }


    public static boolean insertMonirotingLogToMsSql(Connection remoteConn,Hashtable testResultData)
    {

        int len;
        String query;
        PreparedStatement pstmt;

        try
        {
            File file = new File(testResultData.get("screenshot_uri").toString());


            query = ("insert into monitoring_detail ([session_name], [test_node], [test_id], [test_name], [application_name],[component_name], [component_display_name], [feature_name], [feature_display_name], [test_result_url], [test_Status], [test_message], [screenshot_uri], [test_start_time], [test_end_time], [test_duration_ms],[session_start_time], [session_end_time], [session_duration_ms], [feature_weight], [PerformanceThreshold], [created_by]) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pstmt = getSqlConnection().prepareStatement(query);
            pstmt.setString(1,testResultData.get("session_name").toString());
            pstmt.setString(2,testResultData.get("test_node").toString());
            pstmt.setString(3, testResultData.get("test_id").toString());
            pstmt.setString(4,testResultData.get("test_name").toString());
            pstmt.setString(5,testResultData.get("application_name").toString());
            pstmt.setString(6,testResultData.get("component_name").toString());
            pstmt.setString(7,testResultData.get("component_display_name").toString());
            pstmt.setString(8,testResultData.get("feature_name").toString());
            pstmt.setString(9,testResultData.get("feature_display_name").toString());
            pstmt.setString(10,testResultData.get("test_result_url").toString());
            pstmt.setInt(11, (Integer) testResultData.get("test_Status"));
            pstmt.setString(12,testResultData.get("test_message").toString());
            if(file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                len = (int) file.length();
                pstmt.setBinaryStream(13, fis, len);
            }
            else
            {
                pstmt.setBinaryStream(13,null);
            }
            pstmt.setString(14,testResultData.get("test_start_time").toString());
            pstmt.setString(15,testResultData.get("test_end_time").toString());
            pstmt.setString(16,  testResultData.get("test_duration_ms").toString());
            pstmt.setString(17,  testResultData.get("session_start_time").toString());
            pstmt.setString(18,testResultData.get("session_end_time").toString());
            pstmt.setString(19,  testResultData.get("session_duration_ms").toString());
            pstmt.setString(20, testResultData.get("feature_weight").toString());
            pstmt.setString(21, testResultData.get("PerformanceThreshold").toString());
            pstmt.setString(22,testResultData.get("created_by").toString());
            // Method used to insert a stream of bytes
            //   pstmt.setBinaryStream(3, fis, len);
            pstmt.executeUpdate();
            return true;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }


        // INSERT INTO [monitoring_detail] ([session_name], [test_node], [test_id], [test_name], [application_name], [component_name], [component_display_name], [feature_name], [feature_display_name], [test_result_url], [test_Status], [test_message], [screenshot_uri], [test_start_time], [test_end_time], [test_duration_ms], [session_start_time], [session_end_time], [session_duration_ms], [feature_weight], [created_by]) VALUES ('PIER-05-18-2017-11:00','testnode1','1000','PIERTESTCASE1','PIER','Ticket','Ticket Module','Create','Create Ticket','http://jenkinsserver/PIER/PIER-05-18-2017-11:00','1','Successful', null,'2017-05-11 00:00:00.013','2017-05-11 00:00:02.013','2000','2017-05-11 00:00:00.013', null, null,'100', 'app_monitoring_user')
/*
        try
        {
            Statement stmt = null;
            String SQL = "INSERT INTO [monitoring_detail] ([session_name], [test_node], [test_id], [test_name], [application_name],[component_name], [component_display_name], [feature_name], [feature_display_name], [test_result_url], [test_Status], [test_message], [screenshot_uri], [test_start_time], [test_end_time], [test_duration_ms],[session_start_time], [session_end_time], [session_duration_ms], [feature_weight], [created_by])" +
                    " VALUES ('"+testResultData.get("session_name")+"','"+testResultData.get("test_node")+"','"+testResultData.get("test_id")+"','"+testResultData.get("test_name")+"','"+testResultData.get("application_name")+"','"+testResultData.get("component_name")+"','"+testResultData.get("component_display_name")+"','"+testResultData.get("feature_name")+"','"+testResultData.get("feature_display_name")+"','"+testResultData.get("test_result_url")+"','"+testResultData.get("test_Status")+"','"+testResultData.get("test_message")+"','"+testResultData.get("screenshot_uri")+"','"+testResultData.get("test_start_time")+"','"+testResultData.get("test_end_time")+"','"+testResultData.get("test_duration_ms")+"','"+testResultData.get("session_start_time")+"','"+testResultData.get("session_end_time")+"','"+testResultData.get("session_duration_ms")+"', '"+testResultData.get("feature_weight")+"','"+testResultData.get("created_by")+"')";
            stmt = remoteConn.createStatement();
            stmt.executeQuery(SQL);
            return true;
        }
        catch (Exception ex)
        {
           // System.out.print("**************************************SQL ERROR ***********************************************************");
            System.out.print(ex.toString());

            return false;
        }
        */

    }



    // This Method for Testing the scripts.

    public static void main(String [] arg) throws IOException {
     /*   Connection conn = null;
        String propertiesFile = System.getProperty("user.dir")+"\\appMonitoring.properties";
        Properties prop = LoadPropertiesFiles.loadProperties(propertiesFile);

        String dbName = prop.getProperty("AppDB.Name"); //"AppMonitoring";
        String serverport=prop.getProperty("AppDB.Port");
        String url = prop.getProperty("AppDB.Url");
        Statement stmt = null;
        Statement stmt2 = null;
        ResultSet result = null;
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String databaseUserName = prop.getProperty("AppDB.UserName");
        String databasePassword = prop.getProperty("AppDB.Password");
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, databaseUserName, databasePassword);
            //ResultSet rs = null;
            String SQL = "select application_name,component_name,component_display_name,feature_name,feature_weight from ref_app_feature where feature_id=3 and active_flag=1";
            stmt = conn.createStatement();
            result = stmt.executeQuery(SQL);

           try {
                while (result.next()) {
                    String surname = result.getString(3);
                   // Timestamp timeReg = rs.getTimestamp(5);
                    System.out.println(surname);
                }
            } finally {
               result.close();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        */

       String [] gotData = getApplicationFeatures(getSqlConnection(),3);

        System.out.println(Arrays.toString(gotData));



    }
}