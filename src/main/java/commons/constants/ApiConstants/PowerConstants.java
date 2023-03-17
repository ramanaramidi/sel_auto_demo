package commons.constants.ApiConstants;

public class PowerConstants {

    public static final String CREATE_CABINET_TRACKER_URL =
            "/v3/trackor_types/CabinetTracker/trackors";

    public static final String CREATE_CABINET_EQUIPMENT_TRACKER_URL =
            "/v3/trackor_types/CabinetEquipment/trackors";

    public static final String CREATE_CABINETS =
            "{\n" +
                    "  \"fields\": {\n" +
                    "    \"CAB_CABINET_VENDOR\": \"REPLACEWITHVENDOR\"\n" +
                    "  },\n" +
                    "  \"parents\": [\n" +
                    "      {\n" +
                    "          \"trackor_type\": \"SiteTracker\",\n" +
                    "          \"filter\": {\n" +
                    "               \"TRACKOR_KEY\": \"REPLACEWITHSITEID\"\n" +
                    "          }\n" +
                    "      }\n" +
                    "      ]\n" +
                    "}";

    public static final String UPDATE_FOR_CABINET_MODEL =
            "{\n" +
                    "  \"fields\": {\n" +
                    "    \"CAB_CABINET_MODEL\": \"REPLACEWITHMODEL\"\n" +
                    "  }\n" +
                    "}";

    public static final String CREATE_CABINETS_EQUIPMENT =
            "{\n" +
                    "  \"fields\": {\n" +
                    "    \"CAB_CABINET_VENDOR\": \"REPLACEWITHVENDOR\"\n" +
                    "  },\n" +
                    "  \"parents\": [\n" +
                    "      {\n" +
                    "          \"trackor_type\": \"SiteTracker\",\n" +
                    "          \"filter\": {\n" +
                    "               \"TRACKOR_KEY\": \"REPLACEWITHSITEID\"\n" +
                    "          }\n" +
                    "      }\n" +
                    "      ]\n" +
                    "}";

}
