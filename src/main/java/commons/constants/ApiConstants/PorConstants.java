package commons.constants.ApiConstants;

public class PorConstants {

    public static final String CREATE_POR_TRACKER_URL =
            "/v3/trackor_types/PORTracker/trackors"; // same URL to be used
    // with Put for update

    public static final String NEW_POR =
            "{\n" +
                    "  \"fields\": {\n" +
                    "    \"POR_PROGRAM_NAME\": \"REPLACEWITHPROGRAMNAME\",\n" +
                    "    \"POR_REQUESTED_PLAN_STATUS_NR\": \"REPLACEWITHPLANSTATUS\",\n" +
                    "    \"POR_OBJECTIVE\": \"REPLACEWITHOBJECTIVE\"\n" +
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

    public static final String UPDATE_POR_QUEUE_STATUS =
            "{\n" +
                    "  \"fields\": {\n" +
                    "    \"POR_REQUEST_STATUS\": \"REPLACEWITHPORQUEUESTATUS\"\n" +
                    "  }\n" +
                    "}";

    public static final String UPDATE_POR_HC_DATE_STATUS =
            "{\n" +
                    "  \"fields\": {\n" +
                    "    \"POR_HC_RELEASE_DATE\": \"REPLACEWITHPORQUEUESTATUS\"\n" +
                    "  }\n" +
                    "}";

    public static final String UPDATE_POR_UNBUNDEL =
            "{\n" +
                    "  \"fields\": {\n" +
                    "    \"POR_BUNDLING_DISPOSITION\": \"REPLACEWITHBUNDEL\"\n" +
                    "  }\n" +
                    "}";
}
