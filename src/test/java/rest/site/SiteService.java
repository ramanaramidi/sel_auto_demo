package rest.site;

import commons.constants.ApiConstants.SiteConstants;
import commons.objects.ApiRequest;
import commons.objects.ApiResponse;
import commons.objects.Site;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.helper.RestClientLib;

import java.util.HashMap;
import java.util.Map;

public class SiteService {

    Logger logReport = LoggerFactory.getLogger(SiteService.class);
    String baseUrl = "https://magentabuiltstg.t-mobile.com/api";


    public ApiResponse createNewSite(Site site){
        logReport.info(" Start of ----createNewSite");
        SiteRequestBuilder siteRequestBuilder = new SiteRequestBuilder();
        Map<String,String> headers = siteRequestBuilder.getHeader();
        String req = siteRequestBuilder.createNewSiteRequest(site);
        System.out.println(req);
        ApiRequest requestDetails = new ApiRequest(baseUrl+SiteConstants.CREATE_SITE_TRACKER_URL,null,headers, req);
        ApiResponse responseDetails = RestClientLib.post(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----createNewSite");
        return responseDetails;
    }

    public ApiResponse createNewSiteWithoutGeoLocation(Site site){
        logReport.info(" Start of ----createNewSiteWithoutGeoLocation");
        SiteRequestBuilder siteRequestBuilder = new SiteRequestBuilder();
        Map<String,String> headers = siteRequestBuilder.getHeader();
        String req = siteRequestBuilder.createNewSiteWithoutGeoLocationRequest(site);
        System.out.println(req);
        ApiRequest requestDetails = new ApiRequest(baseUrl+SiteConstants.CREATE_SITE_TRACKER_URL,null,headers, req);
        ApiResponse responseDetails = RestClientLib.post(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----createNewSiteWithoutGeoLocation");
        return responseDetails;
    }

    public ApiResponse updateRingToActivateForMacroRing(Site site){
        logReport.info(" Start of ----updateRingToActivateForMacroRing");
        SiteRequestBuilder siteRequestBuilder = new SiteRequestBuilder();
        Map<String,String> params = new HashMap<>();
        params.put("TRACKOR_KEY",site.siteId);
        Map<String,String> headers = siteRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+ SiteConstants.CREATE_SITE_TRACKER_URL,params,headers, siteRequestBuilder.updateSiteToActivateForMacroRing(site));
        ApiResponse responseDetails = RestClientLib.put(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----updateRingToActivateForMacroRing");
        return responseDetails;
    }

    public ApiResponse updateSiteToActivateWithPrimary(Site site){
        logReport.info(" Start of ----updateSiteToActivateWithPrimary");
        SiteRequestBuilder siteRequestBuilder = new SiteRequestBuilder();
        Map<String,String> params = new HashMap<>();
        params.put("TRACKOR_KEY",site.siteId);
        Map<String,String> headers = siteRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+ SiteConstants.CREATE_SITE_TRACKER_URL,params,headers, siteRequestBuilder.updateSiteToActivateWithPrimary(site));
        ApiResponse responseDetails = RestClientLib.put(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----updateSiteToActivateWithPrimary");
        return responseDetails;
    }
    public ApiResponse updateSiteForFops(Site site) {
        logReport.info(" Start of ----updateSiteForFops");
        SiteRequestBuilder siteRequestBuilder = new SiteRequestBuilder();
        Map<String, String> params = new HashMap<>();
        params.put("TRACKOR_KEY", site.siteId);
        Map<String, String> headers = siteRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(
                baseUrl + SiteConstants.CREATE_SITE_TRACKER_URL,
                params,
                headers,
                siteRequestBuilder.updateSiteForFops(site)
        );
        ApiResponse responseDetails = RestClientLib.put(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----updateSiteForFops");
        return responseDetails;
    }

    public ApiResponse getSiteTrackerDetailsBySiteIdAndCustomFields(String siteId,String fields){
        logReport.info(" Start of ----getSiteTrackerDetailsBySiteIdAndCustomFields");
        SiteRequestBuilder siteRequestBuilder = new SiteRequestBuilder();
        Map<String,String> params = new HashMap<>();
        params.put("fields",fields);
        params.put("TRACKOR_KEY",siteId);
        Map<String,String> headers = siteRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+ SiteConstants.CREATE_SITE_TRACKER_URL,params,headers, null);
        ApiResponse responseDetails = RestClientLib.get(requestDetails);
        System.out.println("API RESPONSE:: " + responseDetails.responseBody);
        logReport.info(" End of ----getSiteTrackerDetailsBySiteIdAndCustomFields");
        return responseDetails;
    }
}
