package commons.constants.ApiConstants;

public class SiteConstants {
    public static final String CREATE_SITE_TRACKER_URL = "/v3/trackor_types/SiteTracker/trackors";

    public static final String NEW_SITE_WITH_GEOLOCATION = "{\n" +
            "  \"fields\": {\n" +
            "    \"TRACKOR_KEY\": \"REPLACEWITHSITEID\",\n" +
            "    \"S_SITE_LATITUDE\": \"REPLACEWITHLATITUDE\",\n" +
            "    \"S_SITE_LONGITUDE\": \"REPLACEWITHLONGITUDE\",\n" +
            "    \"S_SITE_TYPE\": \"REPLACEWITHSITETYPE\",\n" +
            "    \"S_SITE_NAME\": \"REPLACEWITHNAME\",\n" +
            "    \"S_BTS_RAW_LAND\": \"REPLACEWITHBTSRAWLAND\",\n" +
            "    \"S_ADDRESS\": \"REPLACEWITHADDRESS\",\n" +
            "    \"S_CITY\": \"REPLACEWITHCITY\"\n" +
            "  },\n" +
            "  \"parents\": [\n" +
            "      {\n" +
            "          \"trackor_type\": \"RingTracker\",\n" +
            "          \"filter\": {\n" +
            "               \"TRACKOR_KEY\": \"REPLACEWITHRINGID\"\n" +
            "          }\n" +
            "      }\n" +
            "      ]\n" +
            "}";

    public static final String UPDATE_FOR_ACTIVE_SITE_STAGE_MACRO = "{\n" +
            "  \"fields\": {\n" +
            "    \"S_SITE_CLASS\":\"REPLACEWITHSITECLASS\"\n" +
            "  }\n" +
            "}";

    public static final String UPDATE_FOR_ACTIVE_SITE_PRIMARY_BUILD = "{\n" +
            "  \"fields\": {\n" +
            "    \"S_SITE_CLASS\":\"REPLACEWITHSITECLASS\",\n" +
            "    \"S_BUILD_STATUS\":\"REPLACEWITHBUILDSTATUS\"\n" +
            "  }\n" +
            "}";

    public static final String NEW_SITE_WITHOUT_GEOLOCATION = "{\n" +
            "  \"fields\": {\n" +
            "    \"TRACKOR_KEY\": \"REPLACEWITHSITEID\",\n" +
            "    \"S_SITE_TYPE\": \"REPLACEWITHSITETYPE\",\n" +
            "    \"S_SITE_NAME\": \"REPLACEWITHNAME\",\n" +
            "    \"S_BTS_RAW_LAND\": \"REPLACEWITHBTSRAWLAND\",\n" +
            "    \"S_ADDRESS\": \"REPLACEWITHADDRESS\",\n" +
            "    \"S_CITY\": \"REPLACEWITHCITY\"\n" +
            "  },\n" +
            "  \"parents\": [\n" +
            "      {\n" +
            "          \"trackor_type\": \"RingTracker\",\n" +
            "          \"filter\": {\n" +
            "               \"TRACKOR_KEY\": \"REPLACEWITHRINGID\"\n" +
            "          }\n" +
            "      }\n" +
            "      ]\n" +
            "}";
}
