package commons.constants.ApiConstants;

public class ProjectConstants {

    public static final String CREATE_PROJECT_TRACKER_URL =
            "/v3/trackor_types/Project/trackors"; // same URL to be used
    // with Put for update

    public static final String ACTIVATE_PROJECT =
            "{\n" +
                    "  \"fields\": {\n" +
                    "    \"PJ_WORKPLAN_ACTIVATED\":\"1\"\n" +
                    "  }\n" +
                    "}";

    public static final String SEND_TO_PIER_PROJECT =
            "{\n" +
                    "  \"fields\": {\n" +
                    "    \"PJ_SEND_PIER_UPDATE\":\"1\"\n" +
                    "  }\n" +
                    "}";
}
