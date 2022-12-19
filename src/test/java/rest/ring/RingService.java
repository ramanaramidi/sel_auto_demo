package rest.ring;

import commons.constants.ApiConstants.RingConstants;
import commons.objects.ApiRequest;
import commons.objects.ApiResponse;
import commons.objects.Ring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.helper.RestClientLib;

import java.util.HashMap;
import java.util.Map;

public class RingService {

    Logger logReport = LoggerFactory.getLogger(RingService.class);
    String baseUrl = "https://magentabuiltstg.t-mobile.com/api";


    public ApiResponse createNewRing(){
        logReport.info(" Start of ----createNewRing");
        RingRequestBuilder ringRequestBuilder = new RingRequestBuilder();
        Map<String,String> headers = ringRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+RingConstants.CREATE_RING_TRACKER_URL,null,headers, ringRequestBuilder.createNewRingRequest());
        ApiResponse responseDetails = RestClientLib.post(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----createNewRing");
        return responseDetails;
    }

    public ApiResponse createNewRingWithOutGeoLocation(){
        logReport.info(" Start of ----createNewRingWithOutGeoLocation");
        RingRequestBuilder ringRequestBuilder = new RingRequestBuilder();
        Map<String,String> headers = ringRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+RingConstants.CREATE_RING_TRACKER_URL,null,headers, ringRequestBuilder.createNewRingWithoutGeoLocationRequest());
        ApiResponse responseDetails = RestClientLib.post(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----createNewRingWithOutGeoLocation");
        return responseDetails;
    }

    public ApiResponse updateRingStatusByRingId(Ring ring){
        logReport.info(" Start of ----updateRingStatusByRingId");
        RingRequestBuilder ringRequestBuilder = new RingRequestBuilder();
        Map<String,String> params = new HashMap<>();
        params.put("TRACKOR_KEY",ring.ringId);
        Map<String,String> headers = ringRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+RingConstants.CREATE_RING_TRACKER_URL,params,headers, ringRequestBuilder.updateRingStatus(ring));
        ApiResponse responseDetails = RestClientLib.put(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----updateRingStatusByRingId");
        return responseDetails;
    }

    public ApiResponse createNewRingForActivation(Ring ring){
        logReport.info(" Start of ----createNewRingForActivation");
        RingRequestBuilder ringRequestBuilder = new RingRequestBuilder();
        Map<String,String> headers = ringRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+RingConstants.CREATE_RING_TRACKER_URL,null,headers, ringRequestBuilder.createActiveRingInitiationRequest(ring));
        ApiResponse responseDetails = RestClientLib.post(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----createNewRingForActivation");
        return responseDetails;
    }

    public ApiResponse updateRingSubMarketByRingId(Ring ring){
        logReport.info(" Start of ----updateRingSubMarketByRingId");
        RingRequestBuilder ringRequestBuilder = new RingRequestBuilder();
        Map<String,String> params = new HashMap<>();
        params.put("TRACKOR_KEY",ring.ringId);
        Map<String,String> headers = ringRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+RingConstants.CREATE_RING_TRACKER_URL,params,headers, ringRequestBuilder.updateRingSubMarketToActivate(ring));
        ApiResponse responseDetails = RestClientLib.put(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----updateRingSubMarketByRingId");
        return responseDetails;
    }
}
