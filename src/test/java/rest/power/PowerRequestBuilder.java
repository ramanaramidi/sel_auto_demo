package rest.power;

import commons.constants.ApiConstants.PowerConstants;
import commons.objects.Site;
import testData.UserData;

import java.util.HashMap;
import java.util.Map;

public class PowerRequestBuilder {

    public Map<String, String> getHeader() {
        Map<String, String> header = new HashMap<>();
        String authToken = UserData.getInstance();
        System.out.println(authToken);
        header.put("Authorization", authToken);
        header.put("Content-Type", "application/json");
        return header;
    }

    public String createNewCabinetRequest(Site site, String vendor) {
        String baseRequest = PowerConstants.CREATE_CABINETS;
        baseRequest = baseRequest.replace("REPLACEWITHSITEID", site.siteId);
        baseRequest =
                baseRequest.replace("REPLACEWITHVENDOR", vendor);
        return baseRequest;
    }

    public String updateCabinetModel(String model) {
        String baseRequest = PowerConstants.UPDATE_FOR_CABINET_MODEL;
        baseRequest =
                baseRequest.replace(
                        "REPLACEWITHMODEL",
                        model
                );
        return baseRequest;
    }
}
