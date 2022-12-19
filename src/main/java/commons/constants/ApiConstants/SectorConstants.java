package commons.constants.ApiConstants;

public class SectorConstants {

    public static final String CREATE_SECTOR_TRACKER_URL = "/v3/trackor_types/SectorTracker/trackors";
    public static final String CREATE_NODE_TRACKER_URL = "/v3/trackor_types/SlipedNodeBTracker/trackors";

    public static final String NEW_SECTOR = " {\"fields\": {\n" +
            "    \"TRACKOR_KEY\": \"REPLACEWITHSECTORID\",\n" +
            "    \"SEC_SECTOR_LATITUDE\": \"REPLACEWITHLATITUDE\",\n" +
            "    \"SEC_SECTOR_LONGITUDE\": \"REPLACEWITHLONGITUDE\"\n" +
            "  },\n" +
            "  \"parents\": [\n" +
            "      {\n" +
            "          \"trackor_type\": \"SiteTracker\",\n" +
            "          \"filter\": {\n" +
            "               \"TRACKOR_KEY\": \"REPLACEWITHTRACKERKEY\"\n" +
            "          }\n" +
            "      }\n" +
            "      ]\n" +
            "}";

    public static final String PLANNED_VALUES_SECTOR_SWITCH = "{\"fields\": {\n" +
            "    \"SEC_CELL_ID_PLANNED\": \"REPLACEWITHCELLID\",\n" +
            "    \"SEC_COVERAGE_TYPE_PLANNED\": \"REPLACEWITHCOVERAGETYPE\",\n" +
            "    \"SEC_VENDOR_PLANNED\": \"REPLACEWITHVENDOR\",\n" +
            "    \"SEC_SWITCH_PLANNED\": \"REPLACEWITHSWITCH\"\n" +
            "  }\n" +
            "}";

    public static final String PLANNED_VALUES_SECTOR_AMF_TAC = "{\"fields\": {\n" +
            "  \"SEC_AMF_PLANNED\": \"REPLACEWITHAMF\",\n" +
            "    \"SEC_LAC_TAC_PLANNED\": \"REPLACEWITHTAC\"\n" +
            "  }\n" +
            "}";

    public static final String PLANNED_VALUES_SECTOR_AMF = "{\"fields\": {\n" +
            "  \"SEC_AMF_PLANNED\": \"REPLACEWITHAMF\"\n" +
            "  }\n" +
            "}";
    public static final String SET_NODE_VALUES_SLIPED = "{\"fields\": {\n" +
            "    \"TRACKOR_KEY\": \"REPLACEWITHNODEID\",\n" +
            "    \"XITOR_CLASS_ID\": \"REPLACEWITHNODECLASS\",\n" +
            "    \"NBID_NODEB_ID\": \"REPLACEWITHINITIALID\",\n" +
            "     \"NBID_CABINET\": \"REPLACEWITHNODECABINET\",\n" +
            "     \"NBID_SITE_ID\":\"REPLACEWITHSITE\"\n" +
            "  }\n" +
            "}";

    public static final String UPDATE_SECTOR_WITH_GNODE = "{\"fields\": {\n" +
            "    \"SEC_GNODE_B_ID_PLANNED\":\"REPLACEWITHNODEID\"\n" +
            "  }\n" +
            "}";

    public static final String UPDATE_SECTOR_WITH_ENODE = "{\"fields\": {\n" +
            "    \"SEC_GNODE_B_ID_PLANNED\":\"REPLACEWITHNODEID\"\n" +
            "  }\n" +
            "}";
}
