package commons.constants;

public class Constants {

    public static final String TRACKER_DATA =
            System.getProperty("user.dir") +
                    "/src/test/resources/testData/tracker_data.xlsx";
    public static final String IMAGE_FILE_UPLOAD =
            System.getProperty("user.dir") +
                    "/src/test/resources/testData/uploads/simpleImage.png";
    public static final String EXCEL_FILE_UPLOAD =
            System.getProperty("user.dir") +
                    "/src/test/resources/testData/uploads/sample.xlsx";
    public static final String SECTOR_IMPORT_DATA =
            System.getProperty("user.dir") +
                    "/src/test/resources/testData/imports/sector_imports.csv";
    public static final String MSM_IMPORT_DATA =
            System.getProperty("user.dir") +
                    "/src/test/resources/testData/imports/msm_imports.csv";

    public static final String PF7075_IMPORT_DATA =
            System.getProperty("user.dir") +
                    "/src/test/resources/testData/imports/pf7075_imports.csv";

    public static final String Ring_IMPORT_DATA =
            System.getProperty("user.dir") +
                    "/src/test/resources/testData/imports/ring_imports.csv";
    public static final String Site_IMPORT_DATA =
            System.getProperty("user.dir") +
                    "/src/test/resources/testData/imports/site_imports.csv";
    // public static final String SECTOR_IMPORT_EXE = System.getProperty("user.dir") +
    // "/src/test/resources/testData/imports/SectorImport.exe";
    public static final String VALID_LATITUDE = "37.42162057";
    public static final String VALID_LONGITUDE = "-122.09686581";

    // TODO: need to find a way to generate post data refresh
    public static final String SITE_CODE_SCIP = "AUJIGBNF";
    public static final String RING_CODE = "AUO0KR2"; // Ring --Active and with at least 1 site
    public static final String RING_CODE_NEW = "AUO0KR2";
    public static final String RING_CODE1 = "AUO0KR1"; // Ring & Site Status Active , Build Status primary

    public static final String RING_CODE2 = "AUO0KR3"; // New Ring only
    public static final String RING_CODE3 = "AU00KR4"; // New Ring and New Site only

    public static final String RING_CODE4 = "AU00KR5"; // New Ring and New Site only
    public static final String PROJECT_CODE_NEW1 = "NCYH001A-0000050136";

    public static final String RING_CODE_ACTIVE_SITE_PRIMARY_BUILD = "";
    // TODO: need to find a way to generate post data refresh
    public static final String POR_CODE = "POR0415944";
    public static final String PROJECT_CODE_NEW = "00TESTOA-0002037960";
}
