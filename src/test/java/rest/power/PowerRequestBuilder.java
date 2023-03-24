package rest.power;

import commons.constants.ApiConstants.PowerConstants;
import commons.objects.Cabinet;
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

    public String createNewCabinetEquipment(Cabinet cabinet) {
        String baseRequest = PowerConstants.CREATE_CABINET_EQUIPMENT;
        baseRequest = baseRequest.replace("REPLACEWITHCABINETEQUIPMENTID", cabinet.cabinetEquipmentId);
        baseRequest =
                baseRequest.replace("REPLACEWITHCLASSID", cabinet.type);
        baseRequest =
                baseRequest.replace("REPLACEWITHCABINET", cabinet.cabinetId);
        return baseRequest;
    }

    public String updateCabinetEquipmentStringAndManufacture(Cabinet cabinet) {
        String baseRequest = PowerConstants.UPDATE_FOR_CABINET_EQUIPMENT_STRING;
        baseRequest =
                baseRequest.replace(
                        "REPLACEWITHSTRING",
                        cabinet.string
                );

        baseRequest =
                baseRequest.replace(
                        "REPLACEWITHMANUFACTURE",
                        cabinet.manufacture
                );
        return baseRequest;
    }

    public String updateCabinetEquipmentModel(Cabinet cabinet) {
        String baseRequest = PowerConstants.UPDATE_FOR_CABINET_EQUIPMENT_MODEL;
        baseRequest =
                baseRequest.replace(
                        "REPLACEWITHMODEL",
                        cabinet.model
                );
        return baseRequest;
    }

    public String updateCabinetBatteryString(String model) {
        String baseRequest = PowerConstants.UPDATE_FOR_CABINET_BATTERY;
        baseRequest =
                baseRequest.replace(
                        "REPLACEWITHBATTERYSTRING",
                        model
                );
        return baseRequest;
    }
}
