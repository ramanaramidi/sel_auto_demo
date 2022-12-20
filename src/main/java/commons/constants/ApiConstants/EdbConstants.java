package commons.constants.ApiConstants;

public class EdbConstants {
    public static final String EDB_CONSUMER_TRACKER_URL = "/v3/trackor_types/EDBConsumerMessageTracker/trackors"; //same URL to be used with Put for update

    public static final String EDB_C_UPDATE_EXISTING_SECTOR = "{\n" +
            "  \"fields\": {\n" +
            "    \"XITOR_CLASS_ID\": \"Asset\",\n" +
            "    \"ECM_CONSUMER_MESSAGE\": \"{\\\"occurred\\\":\\\"2021-02-10T04:48:30.000Z\\\",\\\"data\\\":{\\\"name\\\":\\\"REPLACEWITHSECTORID\\\",\\\"antenna\\\":{\\\"index\\\":5,\\\"location\\\":{\\\"epsg\\\":4269,\\\"longitude\\\":-81.938014,\\\"latitude\\\":39.965902},\\\"azimuth\\\":0,\\\"tilt\\\":0,\\\"height_m\\\":15,\\\"model\\\": {\\\"type\\\": \\\"BeamSwitching\\\",\\\"device\\\":{\\\"name\\\":\\\"AEHC\\\"},\\\"patterns\\\": [{\\\"name\\\":\\\"TEST\\\",\\\"electricalTilt\\\":4,\\\"horizontalBW\\\":12.6,\\\"verticalBW\\\":6,\\\"offsetAngle\\\":0,\\\"gain\\\":24.86000061,\\\"channels\\\":[             \\\"control\\\"]}]}},\\\"location\\\":{\\\"name\\\":\\\"REPLACEWITHSITEID\\\",\\\"id\\\":\\\"876817EC-410C-4466-8DDB-2F1D875095CC\\\"},\\\"metadata\\\":{\\\"modifiedOn\\\":\\\"2021-02-09T20:46:29.000Z\\\",\\\"source\\\":\\\"AssetRadio\\\",\\\"modifedBy\\\":\\\"rvelusa\\\",\\\"createdOn\\\":\\\"2021-02-09T20:29:36.000Z\\\",\\\"createdBy\\\":\\\"rvelusa\\\"},\\\"links\\\":{\\\"self\\\":\\\"/Cells/a4157406-b203-4261-9b85-d35e4c50aaa1/Antennas/4\\\"},\\\"id\\\":\\\"602aa732-ab14-480b-aef0-b8ef848509d5\\\"},\\\"recordType\\\":\\\"CellAntenna\\\",\\\"messageId\\\":\\\"a4157406-b203-4261-9b85-d35e4c50aaa1\\\",\\\"operation\\\":\\\"Updated\\\"}\"\n" +
            "  }\n" +
            "}";

    public static final String EDB_C_ADDED_EXISTING_SECTOR = "{\n" +
            "  \"fields\": {\n" +
            "    \"XITOR_CLASS_ID\": \"Asset\",\n" +
            "    \"ECM_CONSUMER_MESSAGE\": \"{\\\"occurred\\\":\\\"2022-10-24T21:12:38.000Z\\\",\\\"data\\\":{\\\"name\\\":\\\"REPLACEWITHSECTORID\\\",\\\"antenna\\\":{\\\"mechanicalTilt\\\":0,\\\"index\\\":1002,\\\"location\\\":{\\\"epsg\\\":4326,\\\"longitude\\\":-80.266739,\\\"latitude\\\":25.8536},\\\"azimuth\\\":180,\\\"model\\\":{\\\"type\\\":\\\"Passive\\\",\\\"device\\\":{\\\"name\\\":\\\"FFVV-65C-R3-V1\\\"},\\\"patterns\\\":[{\\\"channels\\\":[\\\"control\\\",\\\"traffic\\\"],\\\"electricalTilt\\\":3,\\\"offsetAngle\\\":0,\\\"name\\\":\\\"FFVV-65C-R3-V1\\\\FFVV-65C-R3-V1_700MHz_03DT\\\",\\\"horizontalBW\\\":63,\\\"verticalBW\\\":8.5,\\\"gain\\\":15.55000019}]},\\\"tilt\\\":0,\\\"height_m\\\":15.24},\\\"location\\\":{\\\"name\\\":\\\"REPLACEWITHSITEID\\\",\\\"id\\\":\\\"557be89c-68d7-4c44-9355-f5e45eea1115\\\"},\\\"metadata\\\":{\\\"modifiedOn\\\":\\\"2022-10-24T14:12:35.000Z\\\",\\\"modifiedBy\\\":\\\"EDS_SOA_Client\\\",\\\"source\\\":\\\"AssetRadio\\\",\\\"createdOn\\\":\\\"2022-03-02T05:19:07.000Z\\\",\\\"createdBy\\\":\\\"REPLACEWITHUSER\\\"},\\\"links\\\":{\\\"self\\\":\\\"/events/a0d08437-c0e4-4a99-96a7-10ca634c0673\\\",\\\"entity\\\":\\\"/projects/South/cells/REPLACEWITHSECTORID\\\"},\\\"id\\\":\\\"968d22f7-620c-40e2-b08b-c53793683ba9\\\"},\\\"recordType\\\":\\\"CellAntenna\\\",\\\"messageId\\\":\\\"a0d08437-c0e4-4a99-96a7-10ca634c0673\\\",\\\"operation\\\":\\\"Added\\\"}\"\n" +
            "  }\n" +
            "}";

    public static final String EDB_C_REMOVE_SECTOR = "{\n" +
            "  \"fields\": {\n" +
            "    \"XITOR_CLASS_ID\": \"Asset\",\n" +
            "    \"ECM_CONSUMER_MESSAGE\": \"{\\\"occurred\\\":\\\"2022-10-24T18:57:08.000Z\\\",\\\"data\\\":{\\\"name\\\":\\\"REPLACEWITHSECTORID\\\",\\\"antenna\\\":{\\\"mechanicalTilt\\\":0,\\\"index\\\":40201,\\\"location\\\":{\\\"epsg\\\":4326,\\\"longitude\\\":-93.91,\\\"latitude\\\":31.82},\\\"azimuth\\\":120,\\\"model\\\":{\\\"type\\\":\\\"Passive\\\",\\\"device\\\":{\\\"name\\\":\\\"APXVA18_43-C-A20\\\"},\\\"patterns\\\":[{\\\"channels\\\":[\\\"control\\\",\\\"traffic\\\"],\\\"electricalTilt\\\":10,\\\"offsetAngle\\\":0,\\\"name\\\":\\\"APXVA18_43-C-A20\\\\APXVA18_43-C-A20_600MHz_10DT\\\",\\\"horizontalBW\\\":65.2,\\\"verticalBW\\\":16.1,\\\"gain\\\":14.10000038}]},\\\"tilt\\\":0,\\\"height_m\\\":21},\\\"location\\\":{\\\"name\\\":\\\"REPLACEWITHSITEID\\\",\\\"id\\\":\\\"55d00234-9212-4fe4-84cd-4414c722f582\\\"},\\\"metadata\\\":{\\\"modifiedOn\\\":\\\"2022-10-21T22:06:43.000Z\\\",\\\"modifiedBy\\\":\\\"REPLACEWITHUSER\\\",\\\"source\\\":\\\"AssetRadio\\\",\\\"createdOn\\\":\\\"2022-10-21T19:01:58.000Z\\\",\\\"createdBy\\\":\\\"OPS$rvelusa\\\"},\\\"links\\\":{\\\"self\\\":\\\"/events/affa0ef1-8df5-4b63-bbe2-0be0feabe534\\\",\\\"entity\\\":\\\"/projects/Northeast/cells/REPLACEWITHSECTORID\\\"},\\\"id\\\":\\\"affa0ef1-8df5-4b63-bbe2-0be0feabe534\\\"},\\\"recordType\\\":\\\"CellAntenna\\\",\\\"messageId\\\":\\\"affa0ef1-8df5-4b63-bbe2-0be0feabe534\\\",\\\"operation\\\":\\\"Removed\\\"}\"\n" +
            "  }\n" +
            "}";


    public static final String EDB_C_CREATE_RING_ASSET = "{\n" +
            "  \"fields\": {\n" +
            "    \"XITOR_CLASS_ID\": \"Asset\",\n" +
            "    \"ECM_CONSUMER_MESSAGE\": \"{\\\"occurred\\\":\\\"2022-10-24T14:12:50.000Z\\\",\\\"data\\\":{\\\"name\\\":\\\"REPLACEWITHRINGID\\\",\\\"location\\\":{\\\"epsg\\\":4269,\\\"latitude\\\":25.8536,\\\"longitude\\\":-80.266739},\\\"metadata\\\":{\\\"modifiedOn\\\":\\\"2021-06-22T21:02:38.000Z\\\",\\\"modifiedBy\\\":\\\"REPLACEWITHUSER\\\",\\\"source\\\":\\\"AssetRadio\\\",\\\"createdOn\\\":\\\"2020-10-15T18:46:26.000Z\\\",\\\"createdBy\\\":\\\"REPLACEWITHUSER\\\"},\\\"links\\\":{\\\"self\\\":\\\"/events/cc1e4f0b-165f-4c71-978f-b68ede6b1f58\\\",\\\"entity\\\":\\\"/projects/South/locations/REPLACEWITHRINGID\\\"},\\\"id\\\":\\\"599532eb-e14c-4bba-bf8f-a9845ec7efa5\\\"},\\\"recordType\\\":\\\"RingLocation\\\",\\\"messageId\\\":\\\"cc1e4f0b-165f-4c71-978f-b68ede6b1f58\\\",\\\"operation\\\":\\\"Created\\\"}\"\n" +
            "  }\n" +
            "}";

    public static final String EDB_C_UPDATE_RING_LATLONG_ASSET = "{\n" +
            "  \"fields\": {\n" +
            "    \"XITOR_CLASS_ID\": \"Asset\",\n" +
            "    \"ECM_CONSUMER_MESSAGE\": \"{\\\"occurred\\\":\\\"2022-10-24T14:12:50.000Z\\\",\\\"data\\\":{\\\"name\\\":\\\"REPLACEWITHRINGID\\\",\\\"location\\\":{\\\"epsg\\\":4269,\\\"latitude\\\":REPLACEWITHLAT,\\\"longitude\\\":REPLACEWITHLONG},\\\"metadata\\\":{\\\"modifiedOn\\\":\\\"2021-06-22T21:02:38.000Z\\\",\\\"modifiedBy\\\":\\\"REPLACEWITHUSER\\\",\\\"source\\\":\\\"AssetRadio\\\",\\\"createdOn\\\":\\\"2020-10-15T18:46:26.000Z\\\",\\\"createdBy\\\":\\\"REPLACEWITHUSER\\\"},\\\"links\\\":{\\\"self\\\":\\\"/events/cc1e4f0b-165f-4c71-978f-b68ede6b1f58\\\",\\\"entity\\\":\\\"/projects/South/locations/REPLACEWITHRINGID\\\"},\\\"id\\\":\\\"599532eb-e14c-4bba-bf8f-a9845ec7efa5\\\"},\\\"recordType\\\":\\\"RingLocation\\\",\\\"messageId\\\":\\\"cc1e4f0b-165f-4c71-978f-b68ede6b1f58\\\",\\\"operation\\\":\\\"Created\\\"}\"\n" +
            "  }\n" +
            "}";

    public static final String EDB_C_CREATE_SECTOR_ASSET = "{\n" +
            "  \"fields\": {\n" +
            "    \"XITOR_CLASS_ID\": \"Asset\",\n" +
            "    \"ECM_CONSUMER_MESSAGE\": \"{\\\"occurred\\\":\\\"2022-10-24T21:13:37.000Z\\\",\\\"data\\\":{\\\"coverage\\\":{\\\"name\\\":\\\"Outdoor Macro\\\",\\\"id\\\":\\\"A\\\"},\\\"carrier\\\":{\\\"name\\\":1},\\\"metadata\\\":{\\\"modifiedOn\\\":\\\"2022-10-24T14:13:24.000Z\\\",\\\"modifiedBy\\\":\\\"EDS_SOA_Client\\\",\\\"source\\\":\\\"AssetRadio\\\",\\\"createdOn\\\":\\\"2021-02-16T18:14:42.000Z\\\",\\\"createdBy\\\":\\\"SFateh2\\\"},\\\"name\\\":\\\"ON06150Z_11LPA\\\",\\\"location\\\":{\\\"name\\\":\\\"ON06150Z\\\",\\\"id\\\":\\\"070671e9-9d8a-4496-bb7e-ab2929bd113a\\\"},\\\"links\\\":{\\\"self\\\":\\\"/events/f4acf4e9-1229-4a1a-961e-aa2ce5d9113e\\\",\\\"entity\\\":\\\"/projects/Central/cells/ON06150Z_11LPA\\\"},\\\"id\\\":\\\"282c277e-be96-49c0-ac02-64324e448ecf\\\",\\\"technology\\\":{\\\"name\\\":\\\"LTE PCS\\\",\\\"assetCode\\\":\\\"LP\\\",\\\"abbreviation\\\":\\\"L1900\\\",\\\"radioAccessTechnology\\\":{\\\"name\\\":\\\"LTE\\\",\\\"generation\\\":\\\"4G\\\"},\\\"frequency\\\":{\\\"name\\\":\\\"PCS\\\",\\\"mHz\\\":1900}},\\\"sector\\\":{\\\"name\\\":1}},\\\"recordType\\\":\\\"Cell\\\",\\\"messageId\\\":\\\"f4acf4e9-1229-4a1a-961e-aa2ce5d9113e\\\",\\\"operation\\\":\\\"Created\\\"}\"\n" +
            "  }\n" +
            "}";

    public static final String EDB_C_CREATE_SITE_ASSET = "{\n" +
            "  \"fields\": {\n" +
            "    \"XITOR_CLASS_ID\": \"Asset\",\n" +
            "    \"ECM_CONSUMER_MESSAGE\": \"{\\\"occurred\\\":\\\"2022-10-27T21:27:31.000Z\\\",\\\"data\\\":{\\\"name\\\":\\\"REPLACEWITHSITEID\\\",\\\"location\\\":{\\\"epsg\\\":4326,\\\"latitude\\\":34.34671855,\\\"longitude\\\":-117.424523},\\\"metadata\\\":{\\\"modifiedOn\\\":\\\"2022-04-21T06:57:27.000Z\\\",\\\"modifiedBy\\\":\\\"REPLACEWITHUSER\\\",\\\"source\\\":\\\"AssetRadio\\\",\\\"createdOn\\\":\\\"2014-10-18T04:30:50.000Z\\\",\\\"createdBy\\\":\\\"EDS_SOA_Client\\\"},\\\"links\\\":{\\\"self\\\":\\\"/events/501dc656-8e87-4ed6-9fb7-7603c663e036\\\",\\\"entity\\\":\\\"/projects/West/locations/REPLACEWITHSITEID\\\"},\\\"id\\\":\\\"b5d2bf2f-0106-462e-8131-5e52a2982565\\\"},\\\"recordType\\\":\\\"SiteLocation\\\",\\\"messageId\\\":\\\"501dc656-8e87-4ed6-9fb7-7603c663e036\\\",\\\"operation\\\":\\\"Created\\\"}\"\n" +
            "  }\n" +
            "}";

    public static final String EDB_C_MOVE_SITE_ASSET = "{\n" +
            "  \"fields\": {\n" +
            "    \"XITOR_CLASS_ID\": \"Asset\",\n" +
            "    \"ECM_CONSUMER_MESSAGE\": \"{\\\"occurred\\\":\\\"2022-08-05T14:18:17.000Z\\\",\\\"data\\\":{\\\"name\\\":\\\"REPLACEWITHSITEID\\\",\\\"location\\\":{\\\"epsg\\\":4269,\\\"latitude\\\":28.183761,\\\"longitude\\\":-82.558007},\\\"metadata\\\":{\\\"modifiedOn\\\":\\\"2022-08-05T14:18:14.000Z\\\",\\\"modifiedBy\\\":\\\"REPLACEWITHUSER\\\",\\\"source\\\":\\\"AssetRadio\\\",\\\"createdOn\\\":\\\"2022-07-12T20:25:56.000Z\\\",\\\"createdBy\\\":\\\"REPLACEWITHUSER\\\"},\\\"links\\\":{\\\"self\\\":\\\"/events/6866d3ac-eb55-4f27-81ba-8225a1ce4262\\\",\\\"entity\\\":\\\"/projects/South/locations/REPLACEWITHSITEID\\\"},\\\"id\\\":\\\"854ac9a2-5f3f-4527-9131-0c6c2f1ada87\\\"},\\\"recordType\\\":\\\"SiteLocation\\\",\\\"messageId\\\":\\\"6866d3ac-eb55-4f27-81ba-8225a1ce4262\\\",\\\"operation\\\":\\\"Moved\\\"}\"\n" +
            "  }\n" +
            "}";

    public static final String EDB_C_ELEMENT_EVENT_SITE = "{\n" +
            "    \"fields\": {\n" +
            "        \"XITOR_CLASS_ID\": \"Element\",\n" +
            "        \"ECM_CONSUMER_MESSAGE\": \"{\\\"linekey\\\":\\\"\\\",\\\"filetype\\\":\\\"PARSED\\\",\\\"lineindex\\\":0,\\\"filename\\\":\\\"ELEMENTSITE01_7566_ppolloned00039_20210116233003.csv\\\",\\\"inserttime\\\":\\\"2021-01-16T23:31:02.00\\\",\\\"linedataformat\\\":\\\"JSON\\\",\\\"purpose\\\":\\\"elementsite01\\\",\\\"filesize\\\":2948,\\\"linedata\\\":{\\\"LTE3500_ONAIR_DATE\\\":\\\"2020-05-22 11:00:00.000\\\",\\\"LTE_OFFAIR_DATE\\\":\\\"2020-01-03 11:00:00.000\\\",\\\"LTE3500_OFFAIR_DATE\\\":\\\"2020-01-22 11:00:00.000\\\",\\\"T700_OFFAIR_DATE\\\":\\\"2020-01-28 11:00:00.000\\\",\\\"N2100AWS3_OFFAIR_DATE\\\":\\\"2020-01-31 11:00:00.000\\\",\\\"LTE1900_OFFAIR_DATE\\\":\\\"2020-01-06 11:00:00.000\\\",\\\"FIVEG_OFFAIR_DATE\\\":\\\"2020-01-15 11:00:00.000\\\",\\\"LTE700_OFFAIR_DATE\\\":\\\"2020-01-05 11:00:00.000\\\",\\\"UMTS_OFFAIR_DATE\\\":\\\"2020-01-02 11:00:00.000\\\",\\\"N2100AWS3_ONAIR_DATE\\\":\\\"2020-05-31 11:00:00.000\\\",\\\"N1900_ONAIR_DATE\\\":\\\"2020-05-30 11:00:00.000\\\",\\\"SITE_ONAIR_DATE\\\":\\\"2020-05-01 11:00:00.000\\\",\\\"N47_ONAIR_DATE\\\":\\\"2020-05-27 11:00:00.000\\\",\\\"UMTS_ONAIR_INDICATOR\\\":\\\"\\\",\\\"UMTS2100_OFFAIR_DATE\\\":\\\"2020-01-09 11:00:00.000\\\",\\\"SITE_STATUS\\\":\\\"ACTIVE\\\",\\\"T600_ONAIR_DATE\\\":\\\"2020-05-18 11:00:00.000\\\",\\\"N24_ONAIR_DATE\\\":\\\"2020-05-27 11:00:00.000\\\",\\\"T600_OFFAIR_DATE\\\":\\\"2020-01-18 11:00:00.000\\\",\\\"N3700_OFFAIR_DATE\\\":\\\"2020-01-29 11:00:00.000\\\",\\\"LTE600_OFFAIR_DATE\\\":\\\"2020-01-10 11:00:00.000\\\",\\\"NBIOT_ONAIR_DATE\\\":\\\"2020-05-14 11:00:00.000\\\",\\\"NBIOT_OFFAIR_DATE\\\":\\\"2020-01-14 11:00:00.000\\\",\\\"LTE2500_ONAIR_DATE\\\":\\\"2020-05-23 11:00:00.000\\\",\\\"LTE2100_ONAIR_DATE\\\":\\\"2020-05-07 11:00:00.000\\\",\\\"MODIFIED_DATE\\\":\\\"2021-01-17 07:26:28.670\\\",\\\"CREATED_BY\\\":\\\"ON_AIR_AUTOMATION\\\",\\\"GSM_OFFAIR_DATE\\\":\\\"2020-01-01 11:00:00.000\\\",\\\"UMTS_ONAIR_DATE\\\":\\\"2020-05-02 11:00:00.000\\\",\\\"T2100_ONAIR_DATE\\\":\\\"2020-05-16 11:00:00.000\\\",\\\"N600_ONAIR_DATE\\\":\\\"2020-05-21 11:00:00.000\\\",\\\"FIVEG_ONAIR_DATE\\\":\\\"2020-05-15 11:00:00.000\\\",\\\"ONAIR_INDICATOR\\\":\\\"\\\",\\\"LTE2100AWS3_ONAIR_DATE\\\":\\\"2020-05-12 11:00:00.000\\\",\\\"CDMA_ONAIR_DATE\\\":\\\"2020-05-04 11:00:00.000\\\",\\\"SITE_CODE\\\":\\\"REPLACEWITHSITEID\\\",\\\"T2100_OFFAIR_DATE\\\":\\\"2020-01-16 11:00:00.000\\\",\\\"T1900_ONAIR_DATE\\\":\\\"2020-05-17 11:00:00.000\\\",\\\"ONAIR_INDICATOR_NBIOT\\\":\\\"\\\",\\\"UMTS2100_ONAIR_DATE\\\":\\\"2020-05-09 11:00:00.000\\\",\\\"LTE850_OFFAIR_DATE\\\":\\\"2020-01-11 11:00:00.000\\\",\\\"LTE_ONAIR_DATE\\\":\\\"2020-05-03 11:00:00.000\\\",\\\"N2500_ONAIR_DATE\\\":\\\"2020-05-25 11:00:00.000\\\",\\\"RING_STATUS\\\":\\\"ACTIVE\\\",\\\"N3700_ONAIR_DATE\\\":\\\"2020-05-29 11:00:00.000\\\",\\\"N600_OFFAIR_DATE\\\":\\\"2020-01-21 11:00:00.000\\\",\\\"UMTS1900_OFFAIR_DATE\\\":\\\"2020-01-08 11:00:00.000\\\",\\\"T700_ONAIR_DATE\\\":\\\"2020-05-28 11:00:00.000\\\",\\\"N28_ONAIR_DATE\\\":\\\"2020-05-19 11:00:00.000\\\",\\\"LTE800_ONAIR_DATE\\\":\\\"2020-05-24 11:00:00.000\\\",\\\"LTE600_ONAIR_DATE\\\":\\\"2020-05-10 11:00:00.000\\\",\\\"N24_OFFAIR_DATE\\\":\\\"2020-06-05 11:00:00.000\\\",\\\"LTE800_OFFAIR_DATE\\\":\\\"2020-01-24 11:00:00.000\\\",\\\"N39_ONAIR_DATE\\\":\\\"2020-05-20 11:00:00.000\\\",\\\"CDMA_OFFAIR_DATE\\\":\\\"2020-01-04 11:00:00.000\\\",\\\"ONAIR_INDICATOR_5G\\\":\\\"\\\",\\\"GSM_ONAIR_DATE\\\":\\\"2020-05-01 11:00:00.000\\\",\\\"LTE700_ONAIR_DATE\\\":\\\"2020-05-05 11:00:00.000\\\",\\\"N47_OFFAIR_DATE\\\":\\\"2020-08-15 11:00:00.000\\\",\\\"UMTS1900_ONAIR_DATE\\\":\\\"2020-05-08 11:00:00.000\\\",\\\"LTE2500_OFFAIR_DATE\\\":\\\"2020-01-23 11:00:00.000\\\",\\\"LTEUNLICENSED_ONAIR_DATE\\\":\\\"2020-05-13 11:00:00.000\\\",\\\"N1900_OFFAIR_DATE\\\":\\\"2020-01-30 11:00:00.000\\\",\\\"N39_OFFAIR_DATE\\\":\\\"2020-01-20 11:00:00.000\\\",\\\"N28_OFFAIR_DATE\\\":\\\"2020-01-19 11:00:00.000\\\",\\\"MODIFIED_BY\\\":\\\"\\\",\\\"N2500_OFFAIR_DATE\\\":\\\"2020-01-25 11:00:00.000\\\",\\\"T1900_OFFAIR_DATE\\\":\\\"2020-01-17 11:00:00.000\\\",\\\"CREATED_DATE\\\":\\\"2021-01-16 20:38:26.243\\\",\\\"LTE850_ONAIR_DATE\\\":\\\"2020-05-11 11:00:00.000\\\",\\\"LTE2100_OFFAIR_DATE\\\":\\\"2020-01-07 11:00:00.000\\\",\\\"LTE2100AWS3_OFFAIR_DATE\\\":\\\"2020-01-12 11:00:00.000\\\",\\\"LTEUNLICENSED_OFFAIR_DATE\\\":\\\"2020-01-13 11:00:00.000\\\",\\\"LTE1900_ONAIR_DATE\\\":\\\"2020-05-06 11:00:00.000\\\",\\\"SITE_OFFAIR_DATE\\\":\\\"2020-02-01 11:00:00.000\\\"}}\"\n" +
            "    }\n" +
            "}";

    public static final String EDB_C_ELEMENT_EVENT_SECTOR = "{\n" +
            "    \"fields\": {\n" +
            "        \"XITOR_CLASS_ID\": \"Element-sector\",\n" +
            "        \"ECM_CONSUMER_MESSAGE\": \"{\\\"linekey\\\":\\\"\\\",\\\"filetype\\\":\\\"PARSED\\\",\\\"lineindex\\\":0,\\\"filename\\\":\\\"ELEMENTSECTOR01_38293_ppolloned00039_20210107144502.csv\\\",\\\"inserttime\\\":\\\"2021-01-07T14:46:02.00\\\",\\\"linedataformat\\\":\\\"JSON\\\",\\\"purpose\\\":\\\"elementsector01\\\",\\\"filesize\\\":442,\\\"linedata\\\":{\\\"ADDRESS\\\":\\\"4 SYLVAN COURT\\\",\\\"SECTOR_OFFICIAL_ON_AIR_DATE\\\":\\\"2021-01-05 11:00:00.000\\\",\\\"CITY\\\":\\\"PARSIPPANY\\\",\\\"ADDRESS2\\\":\\\"TESTING FOR SEQUENCE - 1-7-2021\\\",\\\"SITE_CODE\\\":\\\"REPLACEWITHSITEID\\\",\\\"MODIFIED_DATE\\\":\\\"2021-01-07 22:44:51.470\\\",\\\"COUNTY_NAME\\\":\\\"GLOUCESTER\\\",\\\"CREATED_BY\\\":\\\"REPLACEWITHUSER\\\",\\\"CROSS_STREET\\\":\\\"ELEMENT TO MB 1721\\\",\\\"SECTOR_TYPE\\\":\\\"Small Cell\\\",\\\"ZIP_CODE\\\":\\\"07054\\\",\\\"SECTOR_STATUS\\\":\\\"On Air\\\",\\\"MODIFIED_BY\\\":\\\"REPLACEWITHUSER\\\",\\\"CREATED_DATE\\\":\\\"2021-01-04 22:07:59.157\\\",\\\"SECTOR_CODE\\\":\\\"12NCB\\\",\\\"STATE_CODE\\\":\\\"NJ\\\",\\\"SECTOR_OFFICIAL_OFF_AIR_DATE\\\":\\\"\\\"}}\"\n" +
            "    }\n" +
            "}";

    public static final String EDB_C_PIER_MESSAGES = "{\n" +
            "    \"fields\": {\n" +
            "        \"XITOR_CLASS_ID\": \"Pier\",\n" +
            "        \"ECM_CONSUMER_MESSAGE\": \"{\\\"actionType\\\":\\\"change\\\",\\\"payload\\\":{\\\"isNotImplemenedCiCR\\\":false,\\\"riskRequired\\\":false,\\\"activity\\\":\\\"Perform Change\\\",\\\"isBannerValidate\\\":false,\\\"plannedEnd\\\":\\\"2022-07-13T02:00:00.000Z\\\",\\\"isPierPublish\\\":true,\\\"changeType\\\":\\\"Normal\\\",\\\"impact\\\":\\\"No Impact\\\",\\\"project\\\":\\\"dfgdsh\\\",\\\"changeQuestionnaire\\\":[{\\\"action\\\":\\\"ADD\\\",\\\"ciClass\\\":\\\"NPE\\\",\\\"question\\\":\\\"How many mission critical systems of applications may be impacted by this change\\\",\\\"answer\\\":\\\"Zero to one\\\",\\\"crid\\\":0,\\\"cRID\\\":0},{\\\"action\\\":\\\"ADD\\\",\\\"ciClass\\\":\\\"NPE\\\",\\\"question\\\":\\\"How many users could be affected during the implementation of the change\\\",\\\"answer\\\":\\\"None\\\",\\\"crid\\\":0,\\\"cRID\\\":0},{\\\"action\\\":\\\"ADD\\\",\\\"ciClass\\\":\\\"NPE\\\",\\\"question\\\":\\\"To what degree will the system or application be impacted during implementation\\\",\\\"answer\\\":\\\"System will be available\\\",\\\"crid\\\":0,\\\"cRID\\\":0},{\\\"action\\\":\\\"ADD\\\",\\\"ciClass\\\":\\\"NPE\\\",\\\"question\\\":\\\"How important is the system or the application at the time of the change\\\",\\\"answer\\\":\\\"Not used - in maintenance window or non-business hours for application\\\",\\\"crid\\\":0,\\\"cRID\\\":0},{\\\"action\\\":\\\"ADD\\\",\\\"ciClass\\\":\\\"NPE\\\",\\\"question\\\":\\\"Is coordination required among other IT groups to implement the change\\\",\\\"answer\\\":\\\"No - wholly contained within your team\\\",\\\"crid\\\":0,\\\"cRID\\\":0},{\\\"action\\\":\\\"ADD\\\",\\\"ciClass\\\":\\\"NPE\\\",\\\"question\\\":\\\"Can the change be backed out\\\",\\\"answer\\\":\\\"Yes without disruption\\\",\\\"crid\\\":0,\\\"cRID\\\":0},{\\\"action\\\":\\\"ADD\\\",\\\"ciClass\\\":\\\"NPE\\\",\\\"question\\\":\\\"Has this change been done successfully before\\\",\\\"answer\\\":\\\"Yes\\\",\\\"crid\\\":0,\\\"cRID\\\":0},{\\\"action\\\":\\\"ADD\\\",\\\"ciClass\\\":\\\"NPE\\\",\\\"question\\\":\\\"What level of Pre-Deployment testing has been completed\\\",\\\"answer\\\":\\\"Functional Testing\\\",\\\"crid\\\":0,\\\"cRID\\\":0},{\\\"action\\\":\\\"ADD\\\",\\\"ciClass\\\":\\\"NPE\\\",\\\"question\\\":\\\"Is this a change to an API\\\",\\\"answer\\\":\\\"No\\\",\\\"crid\\\":0,\\\"cRID\\\":0}],\\\"crDescription\\\":\\\"sdfgdsgdfshgdf\\\",\\\"plannedStart\\\":\\\"2022-07-12T07:00:00.000Z\\\",\\\"changeWorkLog\\\":[{\\\"description\\\":\\\"advcsdVSD\\\"}],\\\"externalSystem\\\":\\\"CNSMP\\\",\\\"externalReferenceId\\\":\\\"1deb9a31-004e-4e60-ad25-43dfb62b8e36\\\",\\\"crShortDescription\\\":\\\"FSDDF\\\",\\\"changeApprover\\\":[{\\\"approver\\\":\\\"nnadell1\\\",\\\"action\\\":\\\"ADD\\\"}],\\\"changeConfigItem\\\":[{\\\"action\\\":\\\"ADD\\\",\\\"isDefault\\\":false,\\\"configItem\\\":\\\"DNGNRC01\\\"}],\\\"action_name\\\":\\\"create\\\",\\\"invoking_user\\\":\\\"pcdm_user\\\",\\\"changeVerificationStep\\\":[{\\\"verificationStepValue\\\":\\\"Yes\\\",\\\"verificationStep\\\":\\\"Agreed w/ Business\\\",\\\"action\\\":\\\"ADD\\\"},{\\\"verificationStepValue\\\":\\\"Yes\\\",\\\"verificationStep\\\":\\\"Agreed w/ Technology\\\",\\\"action\\\":\\\"ADD\\\"},{\\\"verificationStepValue\\\":\\\"Yes\\\",\\\"verificationStep\\\":\\\"Communication Complete\\\",\\\"action\\\":\\\"ADD\\\"},{\\\"verificationStepValue\\\":\\\"Yes\\\",\\\"verificationStep\\\":\\\"Maintenance Window Only\\\",\\\"action\\\":\\\"ADD\\\"},{\\\"verificationStepValue\\\":\\\"Yes\\\",\\\"verificationStep\\\":\\\"Comm Readiness Conducted\\\",\\\"action\\\":\\\"ADD\\\"},{\\\"verificationStepValue\\\":\\\"Not Applicable\\\",\\\"verificationStep\\\":\\\"GMLC/E911/CMAS Significant\\\",\\\"action\\\":\\\"ADD\\\"},{\\\"verificationStepValue\\\":\\\"Not Applicable\\\",\\\"verificationStep\\\":\\\"SOX Requirement Met\\\",\\\"action\\\":\\\"ADD\\\"}],\\\"is_SC_UI\\\":false},\\\"recordKey\\\":\\\"\\\",\\\"kafka_correlationId\\\":\\\"\\\",\\\"auditType\\\":\\\"create\\\",\\\"source\\\":\\\"\\\",\\\"error\\\":[{\\\"field\\\":\\\"$Error\\\",\\\"message\\\":\\\"CR not created, please include a Valid User ID\\\",\\\"code\\\":\\\"500\\\"}],\\\"transactionId\\\":\\\"\\\"}\"\n" +
            "    }\n" +
            "}";
}