package rest.ring;

import com.jayway.jsonpath.JsonPath;
import commons.constants.Constants;
import commons.enums.SeedingDataTypeEnum;
import commons.objects.ApiResponse;
import commons.objects.Ring;
import commons.objects.Site;
import commons.objects.Trackers;
import testData.DataCollector;

public class RingHelper {

    public Ring createNewRing(boolean isForceSeeding){
        if(!isForceSeeding){
            Trackers trackerData = DataCollector.getTrackerData(SeedingDataTypeEnum.NewRing);
            if(trackerData!=null){
                return new Ring(trackerData.getTrackerId());
            }
            else {
                return createANewRing();
            }
        }
        else {
            return createANewRing();
        }
    }

    public Ring createNewRingWithOutGeoLocation(){
        RingService ringService = new RingService();
        ApiResponse response = ringService.createNewRingWithOutGeoLocation();
        return new Ring(JsonPath.read(response.responseBody,"TRACKOR_KEY"));
    }

    public Ring updateRingStatus(Ring ring){
        RingService ringService = new RingService();
        ApiResponse response = ringService.updateRingStatusByRingId(ring);
        if(!response.isError){
            return ring;
        }
        else
            return null;
    }

    public Ring createAnActiveRing(Ring ring){
        RingService ringService = new RingService();
        ApiResponse response = ringService.createNewRingForActivation(ring);
        if(!response.isError){
            ring.ringId = JsonPath.read(response.responseBody,"TRACKOR_KEY");
            ring.trackerId = JsonPath.read(response.responseBody,"TRACKOR_ID");
            ApiResponse activeRingResponse = ringService.updateRingSubMarketByRingId(ring);
            if(!activeRingResponse.isError)
                return ring;
            else
                return null;
        }
        return null;
    }

    private Ring createANewRing(){
        RingService ringService = new RingService();
        ApiResponse response = ringService.createNewRing();
        if(!response.isError){
            Ring ring = new Ring("TEST - LAB MARKET",
                    Constants.VALID_LONGITUDE,
                    Constants.VALID_LATITUDE,
                    "New Ring",
                    JsonPath.read(response.responseBody,"TRACKOR_KEY"));
            return ring;
        }
        else
            return null;
    }

}
