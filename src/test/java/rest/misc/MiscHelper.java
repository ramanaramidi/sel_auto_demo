package rest.misc;

import com.jayway.jsonpath.JsonPath;
import commons.objects.ApiResponse;
import commons.objects.ImportFile;
import commons.objects.MarketSwitch;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MiscHelper {

    public ImportFile importFileAndGetStatus(ImportFile importFile){
        MiscService miscService = new MiscService();
        ApiResponse response = miscService.importFile(importFile);
        if(response.responseCode == 200){
            Integer processId = JsonPath.read(response.responseBody, "process_id");
            importFile.processId = processId.toString();
            response = miscService.getImportFileDetails(importFile.processId);
            if(response.responseCode == 200) {
                importFile.importStatus = JsonPath.read(response.responseBody, "status");
                return importFile;
            }
        }
        return null;
    }

    public ImportFile importFileAndGetStatusWithParam(ImportFile importFile, Map<String,String> params ){
        MiscService miscService = new MiscService();
        ApiResponse response = miscService.importFileWithParam(importFile,params);
        if(response.responseCode == 200){
            Integer processId = JsonPath.read(response.responseBody, "process_id");
            importFile.processId = processId.toString();
            response = miscService.getImportFileDetails(importFile.processId);
            if(response.responseCode == 200) {
                importFile.importStatus = JsonPath.read(response.responseBody, "status");
                return importFile;
            }
        }
        return null;
    }

    public ImportFile getImportStatus(ImportFile importFile){
        MiscService miscService = new MiscService();
        ApiResponse response = miscService.getImportFileDetails(importFile.processId);
        if(response.responseCode == 200){
            importFile.importStatus = JsonPath.read(response.responseBody, "status");
                return importFile;
        }
        return null;
    }

    public List<String> getSwitchMasterListForMarket(MarketSwitch marketSwitch){
        MiscService miscService = new MiscService();
        ApiResponse response = miscService.getMsmSwitchByMarket(marketSwitch);
        if(response.responseCode==200){
            List<String> switchList = JsonPath.read(response.responseBody,"$.[*].TRACKOR_KEY");
            return switchList;
        }
        return null;
    }

    public List<String> getAmfDetailsForSwitch(MarketSwitch marketSwitch){
        MiscService miscService = new MiscService();
        ApiResponse response = miscService.getAmfDetails(marketSwitch);
        if(response.responseCode==200){
            List<String> switchList = JsonPath.read(response.responseBody,"$.[*].MSM_MGWMMEAMF");
            return switchList;
        }
        return null;
    }

    public List<String> getBncDetailsForSwitch(MarketSwitch marketSwitch){
        MiscService miscService = new MiscService();
        ApiResponse response = miscService.getBncDetailsForSwitch(marketSwitch);
        if(response.responseCode==200){
            List<String> switchList = JsonPath.read(response.responseBody,"$.[*].MSM_BSCRNC");
            return switchList;
        }
        return null;
    }

    public LinkedList<String> getReportProcessIds(String reportId){
        MiscService miscService = new MiscService();
        ApiResponse response = miscService.getProcessIDForReport(reportId);
        if(response.responseCode == 200){
            LinkedList<String> processList = JsonPath.read(response.responseBody,"$.[*].process_id");
            return processList;
        }
        return null;
    }
}
