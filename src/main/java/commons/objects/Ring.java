package commons.objects;

import commons.constants.Constants;
import utility.helper.MiscHelpers;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Ring {
    public String market;
    public String longitude;
    public String latitude;
    public String ringStatus;
    public String ringId;
    public String ringIdDescription;
    public String ringSubMarket;
    public String ringRadius;
    public String radCentre;
    public Integer trackerId;

    public Ring(String market, String longitude, String latitude, String ringStatus, String ringId, String ringIdDescription, String ringSubMarket, String ringRadius, String radCentre) {
        this.market = market;
        this.longitude = longitude;
        this.latitude = latitude;
        this.ringStatus = ringStatus;
        this.ringId = ringId;
        this.ringIdDescription = ringIdDescription;
        this.ringSubMarket = ringSubMarket;
        this.ringRadius = ringRadius;
        this.radCentre = radCentre;
    }


    public Ring(String market, String longitude, String latitude, String ringStatus, String ringId) {
        this.market = market;
        this.longitude = longitude;
        this.latitude = latitude;
        this.ringStatus = ringStatus;
        this.ringId = ringId;
        this.ringIdDescription = null;
        this.ringSubMarket = null;
        this.ringRadius = null;
        this.radCentre = null;
    }

    public Ring(String ringId) {
        this.market = null;
        this.longitude = null;
        this.latitude = null;
        this.ringStatus = null;
        this.ringId = ringId;
        this.ringIdDescription = null;
        this.ringSubMarket = null;
        this.ringRadius = null;
        this.radCentre = null;
    }

    public Ring(String ringStatus, String ringId) {
        this.market = getRandomMarket();
        this.longitude = Constants.VALID_LONGITUDE;
        this.latitude = Constants.VALID_LATITUDE;
        this.ringStatus = ringStatus;
        this.ringId = ringId;
        this.ringIdDescription = getRandomIdDescription();
        this.ringSubMarket = "Non-BTS Engineering Sites";
        this.ringRadius = MiscHelpers.getRandomNumber(2);
        this.radCentre = MiscHelpers.getRandomNumber(2);
    }

    public Ring(String ringStatus, String ringId, String desc) {
        this.market = "SAN FRANCISCO";
        this.longitude = Constants.VALID_LONGITUDE;
        this.latitude = Constants.VALID_LATITUDE;
        this.ringStatus = ringStatus;
        this.ringId = ringId;
        this.ringIdDescription = desc;
        this.ringSubMarket = "Non-BTS Engineering Sites";
        this.ringRadius = MiscHelpers.getRandomNumber(2);
        this.radCentre = MiscHelpers.getRandomNumber(2);
    }

    public String getRandomMarket(){
        Random r = new Random();
        List<String> marketList = Arrays.asList(
                "ALASKA",
                "ARKANSAS",
                "ATLANTA",
                "CLEVELAND",
                "COLUMBUS",
                "CONNECTICUT",
                "DAKOTAS");
        return marketList.get(r.nextInt(marketList.size()));
    }

    public String getRandomIdDescription(){
        Random r = new Random();
        List<String> idDescriptionList = Arrays.asList(
                "Macro",
                "Midcell",
                "TMO Repeater",
                "Non TMO Repeater",
                "Outdoor Repeater",
                "Indoor DAS",
                "Indoor DAS Hotel",
                "TEST",
                "Retail Pico");
        return idDescriptionList.get(r.nextInt(idDescriptionList.size()));
    }
}
