package rest.sector;

import commons.constants.ApiConstants.SectorConstants;
import commons.constants.Constants;
import commons.objects.Sector;
import commons.objects.Site;
import testData.UserData;

import java.util.HashMap;
import java.util.Map;

public class SectorRequestBuilder {

    public Map<String,String> getHeader() {
        Map<String,String> header = new HashMap<>();
        String authToken = UserData.getInstance();
        System.out.println(authToken);
        header.put("Authorization",authToken);
        header.put("Content-Type","application/json");
        return header;
    }

    public String createNewSector(Sector sector) {
        String baseRequest = SectorConstants.NEW_SECTOR;
        baseRequest = baseRequest.replace("REPLACEWITHSECTORID", sector.sectorId);
        baseRequest = baseRequest.replace("REPLACEWITHLATITUDE", Constants.VALID_LATITUDE);
        baseRequest = baseRequest.replace("REPLACEWITHLONGITUDE", Constants.VALID_LONGITUDE);
        baseRequest = baseRequest.replace("REPLACEWITHTRACKERKEY", sector.siteId);
        return baseRequest;
    }

    public String updateSectorWithPlannedSwitchDetails(Sector sector){
        String baseRequest = SectorConstants.PLANNED_VALUES_SECTOR_SWITCH;
        baseRequest = baseRequest.replace("REPLACEWITHCELLID", sector.plannedCellId);
        baseRequest = baseRequest.replace("REPLACEWITHCOVERAGETYPE", sector.coverageType);
        baseRequest = baseRequest.replace("REPLACEWITHVENDOR", sector.plannedVendor);
        baseRequest = baseRequest.replace("REPLACEWITHSWITCH", sector.plannedSwitch);
        return baseRequest;
    }

    public String updateSectorWithPlannedAMFAndTACDetails(Sector sector){
        String baseRequest = SectorConstants.PLANNED_VALUES_SECTOR_AMF_TAC;
        baseRequest = baseRequest.replace("REPLACEWITHAMF", sector.plannedMME);
        baseRequest = baseRequest.replace("REPLACEWITHTAC", sector.plannedLacTac);
        return baseRequest;
    }
    public String updateSectorWithPlannedAMFDetails(Sector sector){
        String baseRequest = SectorConstants.PLANNED_VALUES_SECTOR_AMF;
        baseRequest = baseRequest.replace("REPLACEWITHAMF", sector.plannedMME);
        return baseRequest;
    }

    public String createNewNodeFromSliped(Sector sector){
        String baseRequest = SectorConstants.SET_NODE_VALUES_SLIPED;
        baseRequest = baseRequest.replace("REPLACEWITHNODEID", sector.nodeId);
        baseRequest = baseRequest.replace("REPLACEWITHNODECLASS", sector.nodeClass);
        baseRequest = baseRequest.replace("REPLACEWITHINITIALID", sector.nodeInitialID);
        baseRequest = baseRequest.replace("REPLACEWITHNODECABINET", sector.nodeCabinet);
        baseRequest = baseRequest.replace("REPLACEWITHSITE", sector.siteId);
        return baseRequest;
    }

    public String updateSectorWithNode(Sector sector){
        String baseRequest ="";
        if(sector.nodeClass.equals("gNodeB"))
            baseRequest = SectorConstants.UPDATE_SECTOR_WITH_GNODE;
        else
            baseRequest = SectorConstants.UPDATE_SECTOR_WITH_ENODE;
        baseRequest = baseRequest.replace("REPLACEWITHNODEID", sector.nodeId);
        return baseRequest;
    }
}
