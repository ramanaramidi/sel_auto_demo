package rest.misc;

import commons.constants.ApiConstants.MiscConstants;
import commons.objects.ApiRequest;
import commons.objects.ApiResponse;
import commons.objects.ImportFile;
import commons.objects.MarketSwitch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.helper.RestClientLib;

import java.util.HashMap;
import java.util.Map;

public class MiscService {

    Logger logReport = LoggerFactory.getLogger(MiscService.class);
    String baseUrl = "https://magentabuiltstg.t-mobile.com/api";

    public ApiResponse importFile(ImportFile importFile){
        logReport.info(" Start of ----importFile");
        MiscRequestBuilder miscRequestBuilder = new MiscRequestBuilder();
        String url = MiscConstants.RUN_IMPORT
                .replace("REPLACEWITHIMPORTID",importFile.importId);
        Map<String,String> headers = miscRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+ url,null,headers, null);
        ApiResponse responseDetails = RestClientLib.post(requestDetails,importFile.importFile,importFile.importFilePath);
        logReport.info(" End of ----importFile");
        return responseDetails;
    }

    public ApiResponse importFileWithParam(ImportFile importFile,Map<String,String> params){
        logReport.info(" Start of ----importFile");
        MiscRequestBuilder miscRequestBuilder = new MiscRequestBuilder();
        String url = MiscConstants.RUN_IMPORT
                .replace("REPLACEWITHIMPORTID",importFile.importId);
        Map<String,String> headers = miscRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+ url,params,headers, null);
        ApiResponse responseDetails = RestClientLib.post(requestDetails,importFile.importFile,importFile.importFilePath);
        logReport.info(" End of ----importFile");
        return responseDetails;
    }

    public ApiResponse getImportFileDetails(String processID){
        logReport.info(" Start of ----getImportFileDetails");
        MiscRequestBuilder miscRequestBuilder = new MiscRequestBuilder();
        String url = MiscConstants.GET_IMPORT_DETAILS_BY_PROCESSID
                .replace("REPLACEWITHPROCESSID",processID);
        Map<String,String> headers = miscRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+ url,null,headers, null);
        ApiResponse responseDetails = RestClientLib.get(requestDetails);
        logReport.info(" End of ----getImportFileDetails");
        return responseDetails;
    }

    public ApiResponse getMsmSwitchByMarket(MarketSwitch marketSwitch){
        logReport.info(" Start of ----getMamSwitchByMarket");
        MiscRequestBuilder miscRequestBuilder = new MiscRequestBuilder();
        Map<String,String> headers = miscRequestBuilder.getHeader();
        Map<String,String> params = new HashMap<>();
        params.put("fields","TRACKOR_KEY,MSM_SWITCH,MSM_MGWMMEAMF");
        params.put("MSM_MARKET",marketSwitch.market);
        params.put("MSM_RECORD_TYPE",marketSwitch.recordType);
        params.put("MSM_SELECTABLE",marketSwitch.msmSelectable);
        ApiRequest requestDetails = new ApiRequest(baseUrl+ MiscConstants.MARKET_SWITCH_TRACKER,params,headers, null);
        ApiResponse responseDetails = RestClientLib.get(requestDetails);
        logReport.info(" End of ----getMamSwitchByMarket");
        return responseDetails;
    }

    public ApiResponse getAmfDetails(MarketSwitch marketSwitch){
        logReport.info(" Start of ----getAmfDetails");
        MiscRequestBuilder miscRequestBuilder = new MiscRequestBuilder();
        Map<String,String> headers = miscRequestBuilder.getHeader();
        Map<String,String> params = new HashMap<>();
        params.put("fields","TRACKOR_KEY,MSM_SWITCH,MSM_MGWMMEAMF");
        params.put("MSM_MARKET",marketSwitch.market);
        params.put("MSM_SWITCH",marketSwitch.msmSwitch);
        params.put("MSM_RECORD_TYPE",marketSwitch.recordType);
        params.put("MSM_SELECTABLE",marketSwitch.msmSelectable);
        ApiRequest requestDetails = new ApiRequest(baseUrl+ MiscConstants.MARKET_SWITCH_TRACKER,params,headers, null);
        ApiResponse responseDetails = RestClientLib.get(requestDetails);
        logReport.info(" End of ----getAmfDetails");
        return responseDetails;
    }

    public ApiResponse getBncDetailsForSwitch(MarketSwitch marketSwitch){
        logReport.info(" Start of ----getBncDetailsForSwitch");
        MiscRequestBuilder miscRequestBuilder = new MiscRequestBuilder();
        Map<String,String> headers = miscRequestBuilder.getHeader();
        Map<String,String> params = new HashMap<>();
        params.put("fields","TRACKOR_KEY,MSM_SWITCH,MSM_MGWMMEAMF,MSM_BSCRNC");
        params.put("MSM_MARKET",marketSwitch.market);
        params.put("MSM_SWITCH",marketSwitch.msmSwitch);
        params.put("MSM_RECORD_TYPE",marketSwitch.recordType);
        params.put("MSM_SELECTABLE",marketSwitch.msmSelectable);
        ApiRequest requestDetails = new ApiRequest(baseUrl+ MiscConstants.MARKET_SWITCH_TRACKER,params,headers, null);
        ApiResponse responseDetails = RestClientLib.get(requestDetails);
        logReport.info(" End of ----getBncDetailsForSwitch");
        return responseDetails;
    }

    public ApiResponse getProcessIDForReport(String reportID){
        logReport.info(" Start of ----getProcessIDForReport");
        MiscRequestBuilder miscRequestBuilder = new MiscRequestBuilder();
        String url = MiscConstants.REPORT_DETAILS
                .replace("REPLACEWITHREPORTID",reportID);
        Map<String,String> headers = miscRequestBuilder.getHeader();
        ApiRequest requestDetails = new ApiRequest(baseUrl+ url,null,headers, null);
        ApiResponse responseDetails = RestClientLib.get(requestDetails);
        logReport.info(" End of ----getProcessIDForReport");
        return responseDetails;
    }
}
