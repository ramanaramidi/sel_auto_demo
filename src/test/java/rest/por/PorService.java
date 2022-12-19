package rest.por;

import commons.constants.ApiConstants.PorConstants;
import commons.objects.ApiRequest;
import commons.objects.ApiResponse;
import commons.objects.Por;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.helper.RestClientLib;

import java.util.HashMap;
import java.util.Map;

public class PorService {

    Logger logReport = LoggerFactory.getLogger(PorService.class);
    String baseUrl = "https://magentabuiltstg.t-mobile.com/api";


    public ApiResponse createNewPor(Por por){
        logReport.info(" Start of ----createNewPor");
        PorRequestBuilder porRequestBuilder = new PorRequestBuilder();
        Map<String,String> headers = porRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+ PorConstants.CREATE_POR_TRACKER_URL,null,headers, porRequestBuilder.createNewPorRequest(por));
        ApiResponse responseDetails = RestClientLib.post(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----createNewPor");
        return responseDetails;
    }

    public ApiResponse updatePorQueueStatus(Por por){
        logReport.info(" Start of ----updatePorQueueStatus");
        PorRequestBuilder porRequestBuilder = new PorRequestBuilder();
        Map<String,String> params = new HashMap<>();
        params.put("TRACKOR_KEY",por.porId);
        Map<String,String> headers = porRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+ PorConstants.CREATE_POR_TRACKER_URL,params,headers, porRequestBuilder.updatePor(por));
        ApiResponse responseDetails = RestClientLib.put(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----updatePorQueueStatus");
        return responseDetails;
    }

    public ApiResponse getPorTrackerIdById(String id){
        logReport.info(" Start of ----getPorTrackerIdById");
        PorRequestBuilder porRequestBuilder = new PorRequestBuilder();
        Map<String,String> headers = porRequestBuilder.getHeader();
        Map<String,String> params = new HashMap<>();
        params.put("fields","XITOR_KEY");
        params.put("TRACKOR_KEY",id);
        ApiRequest requestDetails = new ApiRequest(baseUrl+ PorConstants.CREATE_POR_TRACKER_URL,params,headers, null);
        ApiResponse responseDetails = RestClientLib.get(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----getPorTrackerIdById");
        return responseDetails;
    }

    public ApiResponse deletePorTracker(String trackerId){
        logReport.info(" Start of ----deletePorTracker");
        PorRequestBuilder porRequestBuilder = new PorRequestBuilder();
        Map<String,String> headers = porRequestBuilder.getHeader();
        Map<String,String> params = new HashMap<>();
        params.put("trackor_type","PORTracker");
        params.put("trackor_id",trackerId);
        ApiRequest requestDetails = new ApiRequest(baseUrl+ PorConstants.CREATE_POR_TRACKER_URL,params,headers, null);
        ApiResponse responseDetails = RestClientLib.delete(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----deletePorTracker");
        return responseDetails;
    }

//    public ApiResponse updatePorToGenerateProject(){
//
//    }

}
