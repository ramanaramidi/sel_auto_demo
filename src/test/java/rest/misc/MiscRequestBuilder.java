package rest.misc;

import testData.UserData;

import java.util.HashMap;
import java.util.Map;

public class MiscRequestBuilder {

    public Map<String,String> getHeader() {
        Map<String,String> header = new HashMap<>();
        String authToken = UserData.getInstance();
        System.out.println(authToken);
        header.put("Authorization",authToken);
        header.put("Content-Type","application/json");
        return header;
    }
}
