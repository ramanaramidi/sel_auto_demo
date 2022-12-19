package commons.constants.ApiConstants;

public class RingConstants {

    public static final String CREATE_RING_TRACKER_URL = "/v3/trackor_types/RingTracker/trackors"; //same URL to be used with Put for update
    public static final String NEW_RING_WITHOUT_GEOLOCATION = "{\n" +
            "  \"fields\": {\n" +
            "    \"TRACKOR_KEY\": \"REPLACEWITHRINGCODE\"\n" +
            "  },\n" +
            "  \"parents\": [\n" +
            "      {\n" +
            "          \"trackor_type\": \"MarketTracker\",\n" +
            "          \"filter\": {\n" +
            "               \"TRACKOR_KEY\": \"TEST - LAB MARKET\"\n" +
            "          }\n" +
            "      }\n" +
            "      ]\n" +
            "}";

    public static final String UPDATE_RING_STATUS = "{\n" +
            "  \"fields\": {\n" +
            "    \"R_RING_STATUS\": \"REPLACEWITHSTATUS\"\n" +
            "  }\n" +
            "}";

    public static final String NEW_RING_WITH_GEOLOCATION = "{\n" +
            "  \"fields\": {\n" +
            "    \"TRACKOR_KEY\": \"REPLACEWITHRINGCODE\",\n" +
            "    \"R_RING_LONGITUDE\": \"REPLACEWITHLONGITUDE\",\n" +
            "    \"R_DESIRED_RAD_CENTER\": \"REPLACEWITHRAD\",\n" +
            "    \"R_RING_LATITUDE\": \"REPLACEWITHLATITUDE\"\n" +
            "  },\n" +
            "  \"parents\": [\n" +
            "      {\n" +
            "          \"trackor_type\": \"MarketTracker\",\n" +
            "          \"filter\": {\n" +
            "               \"TRACKOR_KEY\": \"TEST - LAB MARKET\"\n" +
            "          }\n" +
            "      }\n" +
            "      ]\n" +
            "}";
    public static final String ACTIVE_RING_STAGE_INITIATION = "{\n" +
            "  \"fields\": {\n" +
            "    \"TRACKOR_KEY\": \"REPLACEWITHRINGCODE\",\n" +
            "    \"R_RING_LONGITUDE\": \"REPLACEWITHLONGITUDE\",\n" +
            "    \"R_RING_LATITUDE\": \"REPLACEWITHLATITUDE\",\n" +
            "    \"R_RING_ID_DESCRIPTION\":\"REPLACEWITHDESCRIPTION\",\n" +
            "    \"R_RING_RADIUS\":\"REPLACEWITHRADIUS\",\n" +
            "    \"R_DESIRED_RAD_CENTER\":\"REPLACEWITHDESIREDRADCENTRE\"\n" +
            "      },\n" +
            "  \"parents\": [\n" +
            "      {\n" +
            "          \"trackor_type\": \"MarketTracker\",\n" +
            "          \"filter\": {\n" +
            "               \"TRACKOR_KEY\": \"TEST - LAB MARKET\"\n" +
            "          }\n" +
            "      }\n" +
            "      ]\n" +
            "}";

    public static final String UPDATE_FOR_ACTIVE_RING_STAGE_COMPLETION = "{\n" +
            "  \"fields\": {\n" +
            "    \"R_SUBMARKET\": \"REPLACEWITHSUBMARKET\"\n" +
            "  }\n" +
            "}";
    public static final String DEAD_RING = "AU00KR4";
}
