package rest.project;

import commons.constants.ApiConstants.NtpConstants;
import commons.constants.ApiConstants.ProjectConstants;
import commons.objects.ApiRequest;
import commons.objects.ApiResponse;
import commons.objects.Project;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.helper.RestClientLib;

public class ProjectService {

    Logger logReport = LoggerFactory.getLogger(ProjectService.class);
    String baseUrl = "https://magentabuiltstg.t-mobile.com/api";

    public ApiResponse getProjectTrackerIdsForSite(String id) {
        logReport.info(" Start of ----getProjectTrackerIdsForSite");
        ProjectRequestBuilder projectRequestBuilder = new ProjectRequestBuilder();
        Map<String, String> headers = projectRequestBuilder.getHeader();
        Map<String, String> params = new HashMap<>();
        params.put("fields", "XITOR_KEY");
        params.put("TRACKOR_KEY", id);
        ApiRequest requestDetails = new ApiRequest(
                baseUrl + ProjectConstants.CREATE_PROJECT_TRACKER_URL,
                params,
                headers,
                null
        );
        ApiResponse responseDetails = RestClientLib.get(requestDetails);
        logReport.info(" End of ----getProjectTrackerIdsForSite");
        return responseDetails;
    }

    public ApiResponse getProjectTrackerIdsForProjectIdWithCustomFields(
            String id,
            String fields
    ) {
        logReport.info(
                " Start of ----getProjectTrackerIdsForProjectIdWithCustomFields"
        );
        ProjectRequestBuilder projectRequestBuilder = new ProjectRequestBuilder();
        Map<String, String> headers = projectRequestBuilder.getHeader();
        Map<String, String> params = new HashMap<>();
        params.put("fields", fields);
        params.put("TRACKOR_KEY", id);
        ApiRequest requestDetails = new ApiRequest(
                baseUrl + ProjectConstants.CREATE_PROJECT_TRACKER_URL,
                params,
                headers,
                null
        );
        ApiResponse responseDetails = RestClientLib.get(requestDetails);
        logReport.info(
                " End of ----getProjectTrackerIdsForProjectIdWithCustomFields"
        );
        return responseDetails;
    }

    public ApiResponse deleteProjectTracker(String trackerId) {
        logReport.info(" Start of ----deleteProjectTracker");
        ProjectRequestBuilder projectRequestBuilder = new ProjectRequestBuilder();
        Map<String, String> headers = projectRequestBuilder.getHeader();
        Map<String, String> params = new HashMap<>();
        params.put("trackor_type", "Project");
        params.put("trackor_id", trackerId);
        ApiRequest requestDetails = new ApiRequest(
                baseUrl + ProjectConstants.CREATE_PROJECT_TRACKER_URL,
                params,
                headers,
                null
        );
        ApiResponse responseDetails = RestClientLib.delete(requestDetails);
        logReport.info(" End of ----deleteProjectTracker");
        return responseDetails;
    }

    public ApiResponse activateProject(String id) {
        logReport.info(" Start of ----activateProject");
        ProjectRequestBuilder projectRequestBuilder = new ProjectRequestBuilder();
        Map<String, String> headers = projectRequestBuilder.getHeader();
        Map<String, String> params = new HashMap<>();
        params.put("TRACKOR_KEY", id);
        ApiRequest requestDetails = new ApiRequest(
                baseUrl + ProjectConstants.CREATE_PROJECT_TRACKER_URL,
                params,
                headers,
                ProjectConstants.ACTIVATE_PROJECT
        );
        ApiResponse responseDetails = RestClientLib.put(requestDetails);
        logReport.info(" End of ----activateProject");
        return responseDetails;
    }

    public ApiResponse sendToPier(String id) {
        logReport.info(" Start of ----sendToPier");
        ProjectRequestBuilder projectRequestBuilder = new ProjectRequestBuilder();
        Map<String, String> headers = projectRequestBuilder.getHeader();
        Map<String, String> params = new HashMap<>();
        params.put("TRACKOR_KEY", id);
        ApiRequest requestDetails = new ApiRequest(
                baseUrl + ProjectConstants.CREATE_PROJECT_TRACKER_URL,
                params,
                headers,
                ProjectConstants.SEND_TO_PIER_PROJECT
        );
        ApiResponse responseDetails = RestClientLib.put(requestDetails);
        logReport.info(" End of ----sendToPier");
        return responseDetails;
    }

    public ApiResponse uploadNTPFile(
            String trackerId,
            String field,
            String path,
            String fileName
    ) {
        logReport.info(" Start of ----uploadNTPFile");
        ProjectRequestBuilder projectRequestBuilder = new ProjectRequestBuilder();
        String url = NtpConstants.NTP_File
                .replace("PROJECTID", trackerId)
                .replace("NTPFIELD", field);
        Map<String, String> headers = projectRequestBuilder.getHeader();
        Map<String, String> params = new HashMap<>();
        params.put("file_name", fileName);
        ApiRequest requestDetails = new ApiRequest(
                baseUrl + url,
                params,
                headers,
                null
        );
        ApiResponse responseDetails = RestClientLib.post(
                requestDetails,
                fileName,
                path
        );
        logReport.info(" End of ----uploadNTPFile");
        return responseDetails;
    }
}
