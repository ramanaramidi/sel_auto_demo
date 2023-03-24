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

    public static final String UPDATE_FOR_CABINET_BATTERY =
            "{\n" +
                    "  \"fields\": {\n" +
                    "    \"CAB_NO_OF_BATTERY_STRINGS_SUP\": \"REPLACEWITHBATTERYSTRING\"\n" +
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

    public static final String CREATE_CABINET_EQUIPMENT =
            "{\n" +
                    " \"fields\": {\n" +
                    "    \"TRACKOR_KEY\": \"REPLACEWITHCABINETEQUIPMENTID\",\n" +
                    "    \"TRACKOR_CLASS_ID\": \"REPLACEWITHCLASSID\"\n" +
                    "      },\n" +
                    "  \"parents\": [\n" +
                    "      {\n" +
                    "          \"trackor_type\": \"CabinetTracker\",\n" +
                    "          \"filter\": {\n" +
                    "               \"TRACKOR_KEY\": \"REPLACEWITHCABINET\"\n" +
                    "          }\n" +
                    "      }\n" +
                    "      ]\n" +
                    "}";

    public static final String UPDATE_FOR_CABINET_EQUIPMENT_STRING =
            "{\n" +
                    " \"fields\": {\n" +
                    "    \"CABE_BAT_STRING_TYPE\": \"REPLACEWITHSTRING\",\n" +
                    "    \"CABE_BAT_MANUFACTURER\": \"REPLACEWITHMANUFACTURE\"\n" +
                    "      }\n" +
                    "}";

    public static final String UPDATE_FOR_CABINET_EQUIPMENT_MODEL =
            "{\n" +
                    "  \"fields\": {\n" +
                    "    \"CABE_BAT_MODEL\": \"REPLACEWITHMODEL\"\n" +
                    "  }\n" +
                    "}";

}
