package rest.edb;

import com.jayway.jsonpath.JsonPath;
import commons.objects.*;


public class EdbHelper {

    public String createNewEdbCEntryForUpdatingExistingSector(Sector sector){
        EdbService edbService = new EdbService();
        ApiResponse response = edbService.updateExistingSector(sector);
        if(!response.isError) {
            return JsonPath.read(response.responseBody, "TRACKOR_KEY");
        }
        return null;
    }

    public String addToExistingSectorEDB(Sector sector, Users users){
        EdbService edbService = new EdbService();
        ApiResponse response = edbService.addToExistingSectorEDB(sector,users);
        if(!response.isError) {
            return JsonPath.read(response.responseBody, "TRACKOR_KEY");
        }
        return null;
    }

    public String removeFromExistingSectorEDB(Sector sector, Users users){
        EdbService edbService = new EdbService();
        ApiResponse response = edbService.removeFromExistingSectorEDB(sector,users);
        if(!response.isError) {
            return JsonPath.read(response.responseBody, "TRACKOR_KEY");
        }
        return null;
    }

    public String createNewRingEDB(Ring ring, Users users){
        EdbService edbService = new EdbService();
        ApiResponse response = edbService.createNewRingEDB(ring,users);
        if(!response.isError) {
            return JsonPath.read(response.responseBody, "TRACKOR_KEY");
        }
        return null;
    }

    public String updateRingLatLongEDB(Ring ring, Users users){
        EdbService edbService = new EdbService();
        ApiResponse response = edbService.createNewRingEDB(ring,users);
        if(!response.isError) {
            return JsonPath.read(response.responseBody, "TRACKOR_KEY");
        }
        return null;
    }

    public String createNewSectorEDB(Sector sector, Users users){
        EdbService edbService = new EdbService();
        ApiResponse response = edbService.createNewSectorEDB(sector,users);
        if(!response.isError) {
            return JsonPath.read(response.responseBody, "TRACKOR_KEY");
        }
        return null;
    }

    public String createNewSiteEDB(Site site, Users users){
        EdbService edbService = new EdbService();
        ApiResponse response = edbService.createNewSiteEDB(site,users);
        if(!response.isError) {
            return JsonPath.read(response.responseBody, "TRACKOR_KEY");
        }
        return null;
    }

    public String moveExistingSiteEDB(Site site, Users users){
        EdbService edbService = new EdbService();
        ApiResponse response = edbService.moveExistingSiteEDB(site,users);
        if(!response.isError) {
            return JsonPath.read(response.responseBody, "TRACKOR_KEY");
        }
        return null;
    }

    public String createElementEventSiteEDB(Site site, Users users){
        EdbService edbService = new EdbService();
        ApiResponse response = edbService.createElementEventSiteEDB(site,users);
        if(!response.isError) {
            return JsonPath.read(response.responseBody, "TRACKOR_KEY");
        }
        return null;
    }

    public String createElementEventSectorEDB(Site site, Users users){
        EdbService edbService = new EdbService();
        ApiResponse response = edbService.createElementEventSectorEDB(site,users);
        if(!response.isError) {
            return JsonPath.read(response.responseBody, "TRACKOR_KEY");
        }
        return null;
    }

    public String createPierMessageEDB(Site site, Users users){
        EdbService edbService = new EdbService();
        ApiResponse response = edbService.createPierMessageEDB(site,users);
        if(!response.isError) {
            return JsonPath.read(response.responseBody, "TRACKOR_KEY");
        }
        return null;
    }
}
