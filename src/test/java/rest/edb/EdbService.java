package rest.edb;

import commons.constants.ApiConstants.EdbConstants;
import commons.objects.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.helper.RestClientLib;

import java.util.Map;

public class EdbService {

    Logger logReport = LoggerFactory.getLogger(EdbService.class);
    String baseUrl = "https://magentabuiltstg.t-mobile.com/api";

    public ApiResponse updateExistingSector(Sector sector){
        logReport.info(" Start of ----updateExistingSector");
        EdbRequestBuilder edbRequestBuilder = new EdbRequestBuilder();
        Map<String,String> headers = edbRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+ EdbConstants.EDB_CONSUMER_TRACKER_URL,null,headers, edbRequestBuilder.updateExistingSectorEDB(sector));
        ApiResponse responseDetails = RestClientLib.post(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----updateExistingSector");
        return responseDetails;
    }

    public ApiResponse addToExistingSectorEDB(Sector sector, Users user){
        logReport.info(" Start of ----addToExistingSectorEDB");
        EdbRequestBuilder edbRequestBuilder = new EdbRequestBuilder();
        Map<String,String> headers = edbRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+ EdbConstants.EDB_CONSUMER_TRACKER_URL,null,headers, edbRequestBuilder.addToExistingSectorEDB(sector,user));
        ApiResponse responseDetails = RestClientLib.post(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----addToExistingSectorEDB");
        return responseDetails;
    }

    public ApiResponse removeFromExistingSectorEDB(Sector sector, Users user){
        logReport.info(" Start of ----removeFromExistingSectorEDB");
        EdbRequestBuilder edbRequestBuilder = new EdbRequestBuilder();
        Map<String,String> headers = edbRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+ EdbConstants.EDB_CONSUMER_TRACKER_URL,null,headers, edbRequestBuilder.removeFromExistingSectorEDB(sector,user));
        ApiResponse responseDetails = RestClientLib.post(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----removeFromExistingSectorEDB");
        return responseDetails;
    }

    public ApiResponse createNewRingEDB(Ring ring, Users user){
        logReport.info(" Start of ----createNewRingEDB");
        EdbRequestBuilder edbRequestBuilder = new EdbRequestBuilder();
        Map<String,String> headers = edbRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+ EdbConstants.EDB_CONSUMER_TRACKER_URL,null,headers, edbRequestBuilder.createNewRingEDB(ring,user));
        ApiResponse responseDetails = RestClientLib.post(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----createNewRingEDB");
        return responseDetails;
    }

    public ApiResponse updateRingLatLongEDB(Ring ring, Users user){
        logReport.info(" Start of ----updateRingLatLongEDB");
        EdbRequestBuilder edbRequestBuilder = new EdbRequestBuilder();
        Map<String,String> headers = edbRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+ EdbConstants.EDB_CONSUMER_TRACKER_URL,null,headers, edbRequestBuilder.updateRingLatLongEDB(ring,user));
        ApiResponse responseDetails = RestClientLib.post(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----updateRingLatLongEDB");
        return responseDetails;
    }

    public ApiResponse createNewSectorEDB(Sector sector, Users user){
        logReport.info(" Start of ----createNewSectorEDB");
        EdbRequestBuilder edbRequestBuilder = new EdbRequestBuilder();
        Map<String,String> headers = edbRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+ EdbConstants.EDB_CONSUMER_TRACKER_URL,null,headers, edbRequestBuilder.createNewSectorEDB(sector,user));
        ApiResponse responseDetails = RestClientLib.post(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----createNewSectorEDB");
        return responseDetails;
    }

    public ApiResponse createNewSiteEDB(Site site, Users user){
        logReport.info(" Start of ----createNewSiteEDB");
        EdbRequestBuilder edbRequestBuilder = new EdbRequestBuilder();
        Map<String,String> headers = edbRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+ EdbConstants.EDB_CONSUMER_TRACKER_URL,null,headers, edbRequestBuilder.createNewSiteEDB(site,user));
        ApiResponse responseDetails = RestClientLib.post(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----createNewSiteEDB");
        return responseDetails;
    }

    public ApiResponse moveExistingSiteEDB(Site site, Users user){
        logReport.info(" Start of ----moveExistingSiteEDB");
        EdbRequestBuilder edbRequestBuilder = new EdbRequestBuilder();
        Map<String,String> headers = edbRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+ EdbConstants.EDB_CONSUMER_TRACKER_URL,null,headers, edbRequestBuilder.moveExistingSiteEDB(site,user));
        ApiResponse responseDetails = RestClientLib.post(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----moveExistingSiteEDB");
        return responseDetails;
    }

    public ApiResponse createElementEventSiteEDB(Site site, Users user){
        logReport.info(" Start of ----moveExistingSiteEDB");
        EdbRequestBuilder edbRequestBuilder = new EdbRequestBuilder();
        Map<String,String> headers = edbRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+ EdbConstants.EDB_CONSUMER_TRACKER_URL,null,headers, edbRequestBuilder.createElementEventSiteEDB(site,user));
        ApiResponse responseDetails = RestClientLib.post(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----moveExistingSiteEDB");
        return responseDetails;
    }

    public ApiResponse createElementEventSectorEDB(Site site, Users user){
        logReport.info(" Start of ----moveExistingSiteEDB");
        EdbRequestBuilder edbRequestBuilder = new EdbRequestBuilder();
        Map<String,String> headers = edbRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+ EdbConstants.EDB_CONSUMER_TRACKER_URL,null,headers, edbRequestBuilder.createElementEventSectorEDB(site,user));
        ApiResponse responseDetails = RestClientLib.post(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----moveExistingSiteEDB");
        return responseDetails;
    }

    public ApiResponse createPierMessageEDB(Site site, Users user){
        logReport.info(" Start of ----moveExistingSiteEDB");
        EdbRequestBuilder edbRequestBuilder = new EdbRequestBuilder();
        Map<String,String> headers = edbRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+ EdbConstants.EDB_CONSUMER_TRACKER_URL,null,headers, edbRequestBuilder.createPierMessageEDB(site,user));
        ApiResponse responseDetails = RestClientLib.post(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----moveExistingSiteEDB");
        return responseDetails;
    }
}
