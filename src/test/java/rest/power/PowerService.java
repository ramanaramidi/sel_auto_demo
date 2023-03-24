package rest.power;

import commons.constants.ApiConstants.PowerConstants;
import commons.objects.ApiRequest;
import commons.objects.ApiResponse;
import commons.objects.Cabinet;
import commons.objects.Site;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.helper.RestClientLib;

import java.util.HashMap;
import java.util.Map;

public class PowerService {

    Logger logReport = LoggerFactory.getLogger(PowerService.class);
    String baseUrl = "https://magentabuiltstg.t-mobile.com/api";

    public ApiResponse createNewCabinet(Site site, String vendor) {
        logReport.info(" Start of ----createNewCabinet");
        PowerRequestBuilder powerRequestBuilder = new PowerRequestBuilder();
        Map<String, String> headers = powerRequestBuilder.getHeader();
        String req = powerRequestBuilder.createNewCabinetRequest(site, vendor);
        System.out.println(req);
        ApiRequest requestDetails = new ApiRequest(
                baseUrl + PowerConstants.CREATE_CABINET_TRACKER_URL,
                null,
                headers,
                req
        );
        ApiResponse responseDetails = RestClientLib.post(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----createNewCabinet");
        return responseDetails;
    }

    public ApiResponse updateCabinetModel(Site site , String model) {
        logReport.info(" Start of ----updateCabinetModel");
        PowerRequestBuilder powerRequestBuilder = new PowerRequestBuilder();
        Map<String, String> params = new HashMap<>();
        params.put("TRACKOR_KEY", site.cabinet);
        Map<String, String> headers = powerRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(
                baseUrl + PowerConstants.CREATE_CABINET_TRACKER_URL,
                params,
                headers,
                powerRequestBuilder.updateCabinetModel(model)
        );
        ApiResponse responseDetails = RestClientLib.put(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----updateCabinetModel");
        return responseDetails;
    }

    public ApiResponse createNewCabinetEquipment(Cabinet cabinet) {
        logReport.info(" Start of ----createNewCabinetEquipment");
        PowerRequestBuilder powerRequestBuilder = new PowerRequestBuilder();
        Map<String, String> headers = powerRequestBuilder.getHeader();
        String req = powerRequestBuilder.createNewCabinetEquipment(cabinet);
        System.out.println(req);
        ApiRequest requestDetails = new ApiRequest(
                baseUrl + PowerConstants.CREATE_CABINET_EQUIPMENT_TRACKER_URL,
                null,
                headers,
                req
        );
        ApiResponse responseDetails = RestClientLib.post(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----createNewCabinetEquipment");
        return responseDetails;
    }

    public ApiResponse updateCabinetEquipmentStringAndManufacture(Cabinet cabinet) {
        logReport.info(" Start of ----updateCabinetEquipmentStringAndManufacture");
        PowerRequestBuilder powerRequestBuilder = new PowerRequestBuilder();
        Map<String, String> params = new HashMap<>();
        params.put("TRACKOR_KEY", cabinet.cabinetEquipmentId);
        Map<String, String> headers = powerRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(
                baseUrl + PowerConstants.CREATE_CABINET_EQUIPMENT_TRACKER_URL,
                params,
                headers,
                powerRequestBuilder.updateCabinetEquipmentStringAndManufacture(cabinet)
        );
        ApiResponse responseDetails = RestClientLib.put(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----updateCabinetEquipmentStringAndManufacture");
        return responseDetails;
    }

    public ApiResponse updateCabinetEquipmentModel(Cabinet cabinet) {
        logReport.info(" Start of ----updateCabinetEquipmentModel");
        PowerRequestBuilder powerRequestBuilder = new PowerRequestBuilder();
        Map<String, String> params = new HashMap<>();
        params.put("TRACKOR_KEY", cabinet.cabinetEquipmentId);
        Map<String, String> headers = powerRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(
                baseUrl + PowerConstants.CREATE_CABINET_EQUIPMENT_TRACKER_URL,
                params,
                headers,
                powerRequestBuilder.updateCabinetEquipmentModel(cabinet)
        );
        ApiResponse responseDetails = RestClientLib.put(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----updateCabinetEquipmentModel");
        return responseDetails;
    }

    public ApiResponse updateCabinetBatteryString(Cabinet cabinet , String count) {
        logReport.info(" Start of ----updateCabinetBatteryString");
        PowerRequestBuilder powerRequestBuilder = new PowerRequestBuilder();
        Map<String, String> params = new HashMap<>();
        params.put("TRACKOR_KEY", cabinet.cabinetId);
        Map<String, String> headers = powerRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(
                baseUrl + PowerConstants.CREATE_CABINET_TRACKER_URL,
                params,
                headers,
                powerRequestBuilder.updateCabinetBatteryString(count)
        );
        ApiResponse responseDetails = RestClientLib.put(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----updateCabinetBatteryString");
        return responseDetails;
    }
}
