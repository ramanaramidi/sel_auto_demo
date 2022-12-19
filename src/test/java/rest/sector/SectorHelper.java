package rest.sector;

import commons.objects.ApiResponse;
import commons.objects.Sector;

public class SectorHelper {

    public Sector createNewSector(Sector sector){
        SectorService sectorService = new SectorService();
        ApiResponse response = sectorService.createNewSector(sector);
        if(!response.isError) {
            return sector;
        }
        return null;
    }

    public Sector updateSectorWithPlannedValues(Sector sector){
        SectorService sectorService = new SectorService();
        ApiResponse response = sectorService.updateSectorWithPlannedSwitchValues(sector);
        if(!response.isError) {
            response = sectorService.updateSectorWithPlannedAMFAndTACValues(sector);
            if(!response.isError)
                return sector;
        }
        return null;
    }
    public Sector updateSectorWithAMFPlannedValues(Sector sector){
        SectorService sectorService = new SectorService();
        ApiResponse response = sectorService.updateSectorWithPlannedSwitchValues(sector);
        if(!response.isError) {
            response = sectorService.updateSectorWithPlannedAMFValues(sector);
            if(!response.isError)
                return sector;
        }
        return null;
    }

    public Sector createNodeAndAssignToSector(Sector sector){
        SectorService sectorService = new SectorService();
        ApiResponse response = sectorService.createNewNode(sector);
        if(!response.isError) {
            response = sectorService.updateSectorWithNode(sector);
            if(!response.isError)
                return sector;
        }
        return null;
    }
}
