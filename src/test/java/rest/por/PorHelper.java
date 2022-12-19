package rest.por;

import com.jayway.jsonpath.JsonPath;
import commons.objects.ApiResponse;
import commons.objects.Por;

import java.util.List;

public class PorHelper {

    public Por createAPor(Por por){
        PorService porService = new PorService();
        ApiResponse response = porService.createNewPor(por);
        if(!response.isError) {
            por.porId = JsonPath.read(response.responseBody, "TRACKOR_KEY");
            return por;
        }
        return null;
    }

    public Por updatePorWithQueueStatus(Por por){
        PorService porService = new PorService();
        ApiResponse response = porService.updatePorQueueStatus(por);
        if(response.responseCode==200){
            return por;
        }
        else
            return null;
    }

    public String getPorTrackerIdsAndDelete(Por por){
        PorService porService = new PorService();
        ApiResponse response = porService.getPorTrackerIdById(por.porId);
        if(response.responseCode == 200){
            List<Integer> trackerKeys = JsonPath.read(response.responseBody,"[*].TRACKOR_ID");
            for (Integer trackor : trackerKeys) {
                System.out.println("DELETING TRACKER ID:: " + trackor);
                porService.deletePorTracker(trackor.toString());
            }
            return "PASS";
        }
        return null;
    }

}
