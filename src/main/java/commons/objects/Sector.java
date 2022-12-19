package commons.objects;

import commons.constants.Constants;
import utility.helper.MiscHelpers;

public class Sector {

    public String siteId;
    public String longitude;
    public String latitude;
    public String sectorId;
    public String plannedCellId;
    public String coverageType;
    public String plannedVendor;
    public String plannedSwitch;
    public String plannedMME;
    public String plannedLacTac;
    public String nodeId;
    public String nodeClass;
    public String nodeCabinet;
    public String nodeInitialID;


    public Sector(String siteId,String sectorId){
        String randomFour = MiscHelpers.getRandomNumber(4);
        this.siteId = siteId;
        this.longitude = Constants.VALID_LONGITUDE;
        this.latitude = Constants.VALID_LATITUDE;
        this.sectorId = sectorId;
        this.plannedCellId = MiscHelpers.getRandomNumber(2);
        this.coverageType = "Indoor";
        this.plannedVendor = "Commscope";
        this.plannedSwitch = "WSCRCAKE";
        this.plannedMME = "West Pool 2";
        this.plannedLacTac = "3699200";
        this.nodeId = randomFour+"_"+siteId+"_"+siteId;
        this.nodeInitialID = randomFour;
        this.nodeCabinet = siteId;
        this.nodeClass = "gNodeB";  //default value and will have to be changed based sector technology
    }
}
