package rest.ring;

import common.LoadPropertiesFiles;
import commons.constants.Constants;
import commons.constants.ApiConstants.RingConstants;
import commons.objects.Ring;
import testData.UserData;
import utility.helper.MiscHelpers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class RingRequestBuilder {

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

    public String createNewRingRequest() {
        String baseRequest = RingConstants.NEW_RING_WITH_GEOLOCATION;
        baseRequest = baseRequest.replace("REPLACEWITHLONGITUDE", Constants.VALID_LONGITUDE);
        baseRequest = baseRequest.replace("REPLACEWITHLATITUDE", Constants.VALID_LATITUDE);
        baseRequest = baseRequest.replace("REPLACEWITHRAD", MiscHelpers.getRandomNumber(2));
        return baseRequest.replace("REPLACEWITHRINGCODE", "AU" + MiscHelpers.getRandomString(5, true).toUpperCase());
    }

    public String createNewRingWithoutGeoLocationRequest() {
        String baseRequest = RingConstants.NEW_RING_WITHOUT_GEOLOCATION;
        return baseRequest.replace("REPLACEWITHRINGCODE", "AU" + MiscHelpers.getRandomString(5, true).toUpperCase());
    }

    //Will not work for Active status
    public String updateRingStatus(Ring ring) {
        String baseRequest = RingConstants.UPDATE_RING_STATUS;
        return baseRequest.replace("REPLACEWITHSTATUS", ring.ringStatus);
    }

    public String createActiveRingInitiationRequest(Ring ring){
        String baseRequest = RingConstants.ACTIVE_RING_STAGE_INITIATION;
        baseRequest = baseRequest.replace("REPLACEWITHLONGITUDE", Constants.VALID_LONGITUDE);
        baseRequest = baseRequest.replace("REPLACEWITHLATITUDE", Constants.VALID_LATITUDE);
        baseRequest = baseRequest.replace("REPLACEWITHDESCRIPTION", ring.ringIdDescription);
        baseRequest = baseRequest.replace("REPLACEWITHRADIUS", ring.ringRadius);
        baseRequest = baseRequest.replace("REPLACEWITHDESIREDRADCENTRE", ring.radCentre);
        return baseRequest.replace("REPLACEWITHRINGCODE", ring.ringId);
    }

    public String updateRingSubMarketToActivate(Ring ring) {
        String baseRequest = RingConstants.UPDATE_FOR_ACTIVE_RING_STAGE_COMPLETION;
        return baseRequest.replace("REPLACEWITHSUBMARKET", ring.ringSubMarket);
    }

}
