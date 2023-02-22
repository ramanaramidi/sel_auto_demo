package rest.site;

import common.LoadPropertiesFiles;
import commons.constants.Constants;
import commons.constants.ApiConstants.SiteConstants;
import commons.objects.Site;
import testData.UserData;
import utility.helper.MiscHelpers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SiteRequestBuilder {

    static Properties userProperties;
//    static {
//        try {
//            userProperties = LoadPropertiesFiles.loadProperties(System.getProperty("user.dir") + "\\src\\test\\resources\\testData\\userData.properties");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public Map<String,String> getHeader() {
        Map<String,String> header = new HashMap<>();
        String authToken = UserData.getInstance();
        System.out.println(authToken);
        header.put("Authorization",authToken);
        header.put("Content-Type","application/json");
        return header;
    }

    public String createNewSiteRequest(Site site) {
        String baseRequest = SiteConstants.NEW_SITE_WITH_GEOLOCATION;
        baseRequest = baseRequest.replace("REPLACEWITHSITEID", site.siteId);
        baseRequest = baseRequest.replace("REPLACEWITHLATITUDE", Constants.VALID_LATITUDE);
        baseRequest = baseRequest.replace("REPLACEWITHLONGITUDE", Constants.VALID_LONGITUDE);
        baseRequest = baseRequest.replace("REPLACEWITHSITETYPE", site.siteType);
        baseRequest = baseRequest.replace("REPLACEWITHNAME", site.siteName);
        baseRequest = baseRequest.replace("REPLACEWITHBTSRAWLAND", site.siteBuildOutType);
        baseRequest = baseRequest.replace("REPLACEWITHADDRESS", site.siteAddress);
        baseRequest = baseRequest.replace("REPLACEWITHCITY", site.siteCity);
        return baseRequest.replace("REPLACEWITHRINGID", site.ringId);
    }

    public String createNewSiteWithoutGeoLocationRequest(Site site) {
        String baseRequest = SiteConstants.NEW_SITE_WITHOUT_GEOLOCATION;
        baseRequest = baseRequest.replace("REPLACEWITHSITEID", site.siteId);
        baseRequest = baseRequest.replace("REPLACEWITHSITETYPE", site.siteType);
        baseRequest = baseRequest.replace("REPLACEWITHNAME", site.siteName);
        baseRequest = baseRequest.replace("REPLACEWITHBTSRAWLAND", site.siteBuildOutType);
        baseRequest = baseRequest.replace("REPLACEWITHADDRESS", site.siteAddress);
        baseRequest = baseRequest.replace("REPLACEWITHCITY", site.siteCity);
        return baseRequest.replace("REPLACEWITHRINGID", site.ringId);
    }

    public String updateSiteToActivateForMacroRing(Site site){
        String baseRequest = SiteConstants.UPDATE_FOR_ACTIVE_SITE_STAGE_MACRO;
        baseRequest = baseRequest.replace("REPLACEWITHLANDLORDSITEID", site.preferredLandlordId);
        baseRequest = baseRequest.replace("REPLACEWITHSITECLASS",site.siteClass);
        return baseRequest;
    }
    public String updateSiteForFops(Site site) {
        String baseRequest = SiteConstants.UPDATE_FOR_FOPS_SITE;
        baseRequest = baseRequest.replace("REPLACEWITHSITECLASS", site.siteClass);
        return baseRequest;
    }

    public String updateSiteToActivateWithPrimary(Site site){
        String baseRequest = SiteConstants.UPDATE_FOR_ACTIVE_SITE_PRIMARY_BUILD;
        baseRequest = baseRequest.replace("REPLACEWITHLANDLORDSITEID", site.preferredLandlordId);
        baseRequest = baseRequest.replace("REPLACEWITHSITECLASS",site.siteClass);
        baseRequest = baseRequest.replace("REPLACEWITHBUILDSTATUS",site.siteBuildStatus);
        return baseRequest;
    }
}
