package commons.objects;

import okhttp3.Response;

public class ApiResponse {
    public Response response;
    public String responseBody;
    public int responseCode;
    public boolean isError ;

    public ApiResponse(Response responseOfApi, String body, int code, boolean isErrorResponse){
        this.response = responseOfApi;
        this.responseBody = body;
        this.responseCode = code;
        this.isError = isErrorResponse;
    }
    public ApiResponse(String body, int code, boolean isErrorResponse){
        this.response = null;
        this.responseBody = body;
        this.responseCode = code;
        this.isError = isErrorResponse;
    }
    public ApiResponse(){
        this.response = null;
        this.responseBody = null;
        this.responseCode = -1;
        this.isError = true;
    }

}
