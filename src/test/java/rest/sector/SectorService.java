package rest.sector;

import commons.constants.ApiConstants.SectorConstants;
import commons.objects.ApiRequest;
import commons.objects.ApiResponse;
import commons.objects.Sector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.helper.RestClientLib;

import java.util.HashMap;
import java.util.Map;

public class SectorService {

    Logger logReport = LoggerFactory.getLogger(SectorService.class);
    String baseUrl = "https://magentabuiltstg.t-mobile.com/api";


    public ApiResponse createNewSector(Sector sector){
        logReport.info(" Start of ----createNewSector");
        SectorRequestBuilder sectorRequestBuilder = new SectorRequestBuilder();
        Map<String,String> headers = sectorRequestBuilder.getHeader();
        String req = sectorRequestBuilder.createNewSector(sector);
        System.out.println(req);
        ApiRequest requestDetails = new ApiRequest(baseUrl+ SectorConstants.CREATE_SECTOR_TRACKER_URL,null,headers, req);
        ApiResponse responseDetails = RestClientLib.post(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----createNewSector");
        return responseDetails;
    }

    public ApiResponse updateSectorWithPlannedSwitchValues(Sector sector){
        logReport.info(" Start of ----updateSectorWithPlannedSwitchValues");
        SectorRequestBuilder sectorRequestBuilder = new SectorRequestBuilder();
        Map<String,String> params = new HashMap<>();
        params.put("TRACKOR_KEY",sector.sectorId);
        Map<String,String> headers = sectorRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+ SectorConstants.CREATE_SECTOR_TRACKER_URL,params,headers, sectorRequestBuilder.updateSectorWithPlannedSwitchDetails(sector));
        ApiResponse responseDetails = RestClientLib.put(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----updateSectorWithPlannedSwitchValues");
        return responseDetails;
    }

    public ApiResponse updateSectorWithPlannedAMFAndTACValues(Sector sector){
        logReport.info(" Start of ----updateSectorWithPlannedAMFAndTACValues");
        SectorRequestBuilder sectorRequestBuilder = new SectorRequestBuilder();
        Map<String,String> params = new HashMap<>();
        params.put("TRACKOR_KEY",sector.sectorId);
        Map<String,String> headers = sectorRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+ SectorConstants.CREATE_SECTOR_TRACKER_URL,params,headers, sectorRequestBuilder.updateSectorWithPlannedAMFAndTACDetails(sector));
        ApiResponse responseDetails = RestClientLib.put(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----updateSectorWithPlannedAMFAndTACValues");
        return responseDetails;
    }

    public ApiResponse updateSectorWithPlannedAMFValues(Sector sector){
        logReport.info(" Start of ----updateSectorWithPlannedAMFValues");
        SectorRequestBuilder sectorRequestBuilder = new SectorRequestBuilder();
        Map<String,String> params = new HashMap<>();
        params.put("TRACKOR_KEY",sector.sectorId);
        Map<String,String> headers = sectorRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+ SectorConstants.CREATE_SECTOR_TRACKER_URL,params,headers, sectorRequestBuilder.updateSectorWithPlannedAMFDetails(sector));
        ApiResponse responseDetails = RestClientLib.put(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----updateSectorWithPlannedAMFValues");
        return responseDetails;
    }

    public ApiResponse createNewNode(Sector sector){
        logReport.info(" Start of ----createNewNode");
        SectorRequestBuilder sectorRequestBuilder = new SectorRequestBuilder();
        Map<String,String> headers = sectorRequestBuilder.getHeader();
        String req = sectorRequestBuilder.createNewNodeFromSliped(sector);
        System.out.println(req);
        ApiRequest requestDetails = new ApiRequest(baseUrl+ SectorConstants.CREATE_NODE_TRACKER_URL,null,headers, req);
        ApiResponse responseDetails = RestClientLib.post(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----createNewNode");
        return responseDetails;
    }

    public ApiResponse updateSectorWithNode(Sector sector){
        logReport.info(" Start of ----updateSectorWithNode");
        SectorRequestBuilder sectorRequestBuilder = new SectorRequestBuilder();
        Map<String,String> params = new HashMap<>();
        params.put("TRACKOR_KEY",sector.sectorId);
        Map<String,String> headers = sectorRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+ SectorConstants.CREATE_SECTOR_TRACKER_URL,params,headers, sectorRequestBuilder.updateSectorWithNode(sector));
        ApiResponse responseDetails = RestClientLib.put(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----updateSectorWithNode");
        return responseDetails;
    }
}
