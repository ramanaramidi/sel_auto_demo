package rest.edb;

import commons.constants.ApiConstants.EdbConstants;
import commons.objects.Ring;
import commons.objects.Sector;
import commons.objects.Site;
import commons.objects.Users;
import testData.UserData;

import java.util.HashMap;
import java.util.Map;

public class EdbRequestBuilder {

    public Map<String,String> getHeader() {
        Map<String,String> header = new HashMap<>();
        String authToken = UserData.getInstance();
        System.out.println(authToken);
        header.put("Authorization",authToken);
        header.put("Content-Type","application/json");
        return header;
    }

    public String updateExistingSectorEDB(Sector sector) {
        String baseRequest = EdbConstants.EDB_C_UPDATE_EXISTING_SECTOR;
        baseRequest = baseRequest.replace("REPLACEWITHSECTORID", sector.sectorId);
        baseRequest = baseRequest.replace("REPLACEWITHSITEID", sector.siteId);
        return baseRequest;
    }

    public String addToExistingSectorEDB(Sector sector, Users user) {
        String baseRequest = EdbConstants.EDB_C_ADDED_EXISTING_SECTOR;
        baseRequest = baseRequest.replace("REPLACEWITHSECTORID", sector.sectorId);
        baseRequest = baseRequest.replace("REPLACEWITHSITEID", sector.siteId);
        baseRequest = baseRequest.replace("REPLACEWITHUSER", user.getNtCode());
        return baseRequest;
    }

    public String removeFromExistingSectorEDB(Sector sector, Users user) {
        String baseRequest = EdbConstants.EDB_C_REMOVE_SECTOR;
        baseRequest = baseRequest.replace("REPLACEWITHSECTORID", sector.sectorId);
        baseRequest = baseRequest.replace("REPLACEWITHSITEID", sector.siteId);
        baseRequest = baseRequest.replace("REPLACEWITHUSER", user.getNtCode());
        return baseRequest;
    }

    public String createNewRingEDB(Ring ring, Users user) {
        String baseRequest = EdbConstants.EDB_C_CREATE_RING_ASSET;
        baseRequest = baseRequest.replace("REPLACEWITHRINGID", ring.ringId);
        baseRequest = baseRequest.replace("REPLACEWITHUSER", user.getNtCode());
        return baseRequest;
    }

    public String updateRingLatLongEDB(Ring ring, Users user) {
        String baseRequest = EdbConstants.EDB_C_UPDATE_RING_LATLONG_ASSET;
        baseRequest = baseRequest.replace("REPLACEWITHRINGID", ring.ringId);
        baseRequest = baseRequest.replace("REPLACEWITHLAT", ring.latitude);
        baseRequest = baseRequest.replace("REPLACEWITHLONG", ring.longitude);
        baseRequest = baseRequest.replace("REPLACEWITHUSER", user.getNtCode());
        return baseRequest;
    }

    public String createNewSectorEDB(Sector sector, Users user) {
        String baseRequest = EdbConstants.EDB_C_CREATE_SECTOR_ASSET;
        baseRequest = baseRequest.replace("REPLACEWITHSECTORID", sector.sectorId);
        baseRequest = baseRequest.replace("REPLACEWITHSITEID", sector.siteId);
        baseRequest = baseRequest.replace("REPLACEWITHUSER", user.getNtCode());
        return baseRequest;
    }

    public String createNewSiteEDB(Site site, Users user) {
        String baseRequest = EdbConstants.EDB_C_CREATE_SITE_ASSET;
        baseRequest = baseRequest.replace("REPLACEWITHSITEID", site.siteId);
        baseRequest = baseRequest.replace("REPLACEWITHUSER", user.getNtCode());
        return baseRequest;
    }

    public String moveExistingSiteEDB(Site site, Users user) {
        String baseRequest = EdbConstants.EDB_C_MOVE_SITE_ASSET;
        baseRequest = baseRequest.replace("REPLACEWITHSITEID", site.siteId);
        baseRequest = baseRequest.replace("REPLACEWITHUSER", user.getNtCode());
        return baseRequest;
    }

    public String createElementEventSiteEDB(Site site, Users user) {
        String baseRequest = EdbConstants.EDB_C_ELEMENT_EVENT_SITE;
        baseRequest = baseRequest.replace("REPLACEWITHSITEID", site.siteId);
        baseRequest = baseRequest.replace("REPLACEWITHUSER", user.getNtCode());
        return baseRequest;
    }

    public String createElementEventSectorEDB(Site site, Users user) {
        String baseRequest = EdbConstants.EDB_C_ELEMENT_EVENT_SECTOR;
        baseRequest = baseRequest.replace("REPLACEWITHSITEID", site.siteId);
        baseRequest = baseRequest.replace("REPLACEWITHUSER", user.getNtCode());
        return baseRequest;
    }

    public String createPierMessageEDB(Site site, Users user) {
        String baseRequest = EdbConstants.EDB_C_PIER_MESSAGES;
        baseRequest = baseRequest.replace("REPLACEWITHSITEID", site.siteId);
        baseRequest = baseRequest.replace("REPLACEWITHUSER", user.getNtCode());
        return baseRequest;
    }
}