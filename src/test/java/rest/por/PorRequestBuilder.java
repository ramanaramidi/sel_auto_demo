package rest.por;

import common.LoadPropertiesFiles;
import commons.constants.ApiConstants.PorConstants;
import commons.constants.Constants;
import commons.objects.Por;
import commons.objects.Site;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import testData.UserData;
import utility.helper.MiscHelpers;

public class PorRequestBuilder {

    static Properties userProperties;

    // static {
    // try {
    // userProperties = LoadPropertiesFiles.loadProperties(System.getProperty("user.dir") +
    // "\\src\\test\\resources\\testData\\userData.properties");
    // } catch (IOException e) {
    // throw new RuntimeException(e);
    // }
    // }

    public Map<String, String> getHeader() {
        Map<String, String> header = new HashMap<>();
        String authToken = UserData.getInstance();
        System.out.println(authToken);
        header.put("Authorization", authToken);
        header.put("Content-Type", "application/json");
        return header;
    }

    public String createNewPorRequest(Por por) {
        String baseRequest = PorConstants.NEW_POR;
        baseRequest =
                baseRequest.replace("REPLACEWITHPROGRAMNAME", por.programName);
        baseRequest = baseRequest.replace("REPLACEWITHPLANSTATUS", por.planStatus);
        baseRequest = baseRequest.replace("REPLACEWITHOBJECTIVE", por.objective);
        return baseRequest.replace("REPLACEWITHRINGID", por.ringID);
    }

    public String updatePor(Por por) {
        String baseRequest = PorConstants.UPDATE_POR_QUEUE_STATUS;
        baseRequest =
                baseRequest.replace("REPLACEWITHPORQUEUESTATUS", por.queueStatus);
        return baseRequest;
    }

    public String updatePorHcDate() {
        String baseRequest = PorConstants.UPDATE_POR_HC_DATE_STATUS;
        baseRequest =
                baseRequest.replace("REPLACEWITHPORQUEUESTATUS", MiscHelpers.specificFutureDateTime("yyyy-MM-dd", 10));
        return baseRequest;
    }

    public String updatePorBundel(String por) {
        String baseRequest = PorConstants.UPDATE_POR_UNBUNDEL;
        baseRequest =
                baseRequest.replace("REPLACEWITHBUNDEL", por);
        return baseRequest;
    }
}
