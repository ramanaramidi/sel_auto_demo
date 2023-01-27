package commons.objects;

import commons.constants.Constants;
import utility.helper.MiscHelpers;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Site {
    public String siteId;
    public String longitude;
    public String latitude;
    public String ringId;
    public String siteType;
    public String siteClass;
    public String siteName;
    public String siteBuildOutType;
    public String siteBuildStatus;
    public String siteAddress;
    public String siteCity;
    public String siteStatus;
    public String preferredLandlordId;
    public Integer trackerId;

    public Site(String ringId, String buildStatus, String status){
        this.siteId = "SAU"+MiscHelpers.getRandomString(5,true).toUpperCase();
        this.longitude = Constants.VALID_LONGITUDE;
        this.latitude = Constants.VALID_LATITUDE;
        this.ringId = ringId;
        this.siteType = getRandomSiteType();
        this.siteClass = getRandomSiteClass(this.siteType);
        this.siteName = getSiteName();
        this.siteBuildOutType = getRandomBuildOutType();
        this.siteBuildStatus = buildStatus;
        this.siteAddress = getRandomSiteAddress();
        this.siteCity = getRandomSiteCity();
        this.siteStatus = status;
        this.preferredLandlordId = getRandomPreferredLandlord();
    }

    public Site(String ringId, String buildStatus, String siteType, String siteClass, String siteBuildOutType ,String status){
        this.siteId = "SAU"+MiscHelpers.getRandomString(5,true).toUpperCase();
        this.longitude = Constants.VALID_LONGITUDE;
        this.latitude = Constants.VALID_LATITUDE;
        this.ringId = ringId;
        this.siteType = siteType;
        this.siteClass = siteClass;
        this.siteName = getSiteName();
        this.siteBuildOutType = siteBuildOutType;
        this.siteBuildStatus = buildStatus;
        this.siteAddress = getRandomSiteAddress();
        this.siteCity = getRandomSiteCity();
        this.siteStatus = status;
        this.preferredLandlordId = getRandomPreferredLandlord();
    }

    public  String getSiteName(){
        return "Test"+MiscHelpers.getRandomString(3,false);
    }

    public String getRandomSiteClass(String siteType){
        List<String> siteClassListStructureNonBuild = Arrays.asList(
                "Bell Tower",
                "Billboard",
                "Cactus",
                "Catenary Structure",
                "Clock Tower",
                "Flag Pole");
        List<String> siteClassListBuild = Arrays.asList(
                "3G Repeater",
                "Building Other",
                "In Building",
                "Non TMO Repeater",
                "Repeater",
                "Roof Top Mount");
        List<String> siteClassListDas = Arrays.asList(
                "In-Building BTS",
                "In-Building DAS",
                "In-Building HUB",
                "Outdoor BTS",
                "Outdoor HUB",
                "External DAS");
        List<String> siteClassListNonCell = Arrays.asList(
                "Non Cell Site");
        List<String> siteClassListRouter = Arrays.asList(
                "Aggregate");
        Random r = new Random();
        switch (siteType){
            case "Structure Non Building":return siteClassListStructureNonBuild.get(r.nextInt(siteClassListStructureNonBuild.size()));
            case "Building":return siteClassListBuild.get(r.nextInt(siteClassListBuild.size()));
            case "Engineering DAS":return siteClassListDas.get(r.nextInt(siteClassListDas.size()));
            case "Non Cell Site":return siteClassListNonCell.get(r.nextInt(siteClassListNonCell.size()));
            case "Router":return siteClassListRouter.get(r.nextInt(siteClassListRouter.size()));
            default:return "Test Site";
        }



    }

    public String getRandomSiteType(){
        Random r = new Random();
        List<String> siteTypeList = Arrays.asList(
                "Structure Non Building",
                "Building"
//                "Engineering DAS",
//                "Non Cell Site",
//                "Router",
//                "Test Site"
        );
        return siteTypeList.get(r.nextInt(siteTypeList.size()));
    }

    public String getRandomBuildStatus(){
        Random r = new Random();
        List<String> buildStatusTypeList = Arrays.asList(
                "Candidate",
                "Primary",
                "Final Build",
                "Alt 1",
                "Rejected");
        return buildStatusTypeList.get(r.nextInt(buildStatusTypeList.size()));
    }
    public String getRandomBuildOutType(){
        Random r = new Random();
        List<String> buildOutTypeList = Arrays.asList(
//                "Leased",
//                "Raw Land",
                "Build To Suit");
//                "Temp Install");
        return buildOutTypeList.get(r.nextInt(buildOutTypeList.size()));
    }
    public String getRandomPreferredLandlord(){
        Random r = new Random();
        List<String> preferredLandlordList = Arrays.asList(
                "1 - ATC SLMA - 203708",
                "1 - ATC SLMA - 204030");
        return preferredLandlordList.get(r.nextInt(preferredLandlordList.size()));
    }

    public String getRandomSiteCity(){
        Random r = new Random();
        List<String> cityList = Arrays.asList(
                "Alexander City",
                "Andalusia",
                "Anniston",
                "Athens",
                "Atmore",
                "Auburn",
                "Bessemer",
                "Birmingham",
                "Chickasaw",
                "Clanton",
                "Cullman",
                "Decatur");
        return cityList.get(r.nextInt(cityList.size()));
    }
    public String getRandomSiteAddress(){
        Random r = new Random();
        List<String> addressList = Arrays.asList(
                "42 W Megan St, Gilbert, AZ, 85233",
                "346 Mason Mill Rd, Pike Road, AL, 36064",
                "46955 Highway 25, Vincent, AL, 35178",
                "1517 Tramon Ave, North Pole, AK, 99705",
                "1958 N Old Wire Rd, Fayetteville, AR, 72703",
                "2367 N Marks Mill Ln, Fayetteville, AR, 72703",
                "2925 Meadowlark Ave, Fort Collins, CO, 80526",
                "103 Silver Rd, Black Hawk, CO, 80422",
                "15 Cannon St, Norwalk, CT, 06851",
                "124 Grumman Ave, Norwalk, CT, 06851",
                "2625 Majestic Dr, Wilmington, DE, 19810",
                "7029 Willow Grove Rd, Camden Wyoming, DE, 19934",
                "6230 Westport Ln, Naples, FL, 34116",
                "404 Little Gem Ct LOT 21, Niceville, FL, 32578",
                "1485 Hardin Ave, College Park, GA, 30337",
                "6200 Queensdale Dr, Douglasville, GA, 30135",
                "87-2422 Hawaii Belt Rd, Captain Cook, HI, 96704",
                "31 Luana Way, Hilo, HI, 96720",
                "93 Lone Cedar Ln, Clark Fork, ID, 83811",
                "5244 N Nashville Ave, Chicago, IL, 60656",
                "1932 N Burling St, Chicago, IL, 60614",
                "2831 Slatestone Ct, Evansville, IN, 47712",
                "25515 County Road 28, Goshen, IN, 46526",
                "11 Country Club Ct, Le Claire, IA, 52753",
                "3373 Field Sike Dr, Bettendorf, IA, 52722",
                "16640 Nall Ave, Stilwell, KS, 66085",
                "14513 N 145th St, Basehor, KS, 66007",
                "8705 Creighton Ct, Hurstbourne, KY, 40222",
                "478 W 2nd St, Maysville, KY, 41056");
        return addressList.get(r.nextInt(addressList.size()));
    }
}
