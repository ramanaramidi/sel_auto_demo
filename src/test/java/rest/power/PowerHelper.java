package rest.power;

import com.jayway.jsonpath.JsonPath;
import commons.objects.ApiResponse;
import commons.objects.Site;

public class PowerHelper {

    public Site createNewCabinet(Site site, String vendor) {
        PowerService powerService = new PowerService();
        ApiResponse response = powerService.createNewCabinet(site, vendor);
        if (!response.isError) {
            site.cabinet = JsonPath.read(response.responseBody, "TRACKOR_KEY");
            return site;
        }
        return null;
    }

    public Site updateCabinetModel(Site site,String model) {
        PowerService powerService = new PowerService();
        ApiResponse response = powerService.updateCabinetModel(site,model);
        if (!response.isError) return site;
        return null;
    }
}
