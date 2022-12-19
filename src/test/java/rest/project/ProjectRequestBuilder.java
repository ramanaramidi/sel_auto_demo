package rest.project;

import common.LoadPropertiesFiles;
import testData.UserData;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ProjectRequestBuilder {

    static Properties userProperties;
//    static {
//        try {
//            userProperties = LoadPropertiesFiles.loadProperties(System.getProperty("user.dir") + "\\src\\test\\resources\\testData\\userData.properties");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public Map<String,String> getHeader() {
        Map<String,String> header = new HashMap<>();
        String authToken = UserData.getInstance();
        System.out.println(authToken);
        header.put("Authorization",authToken);
        header.put("Content-Type","application/json");
        return header;
    }

}
