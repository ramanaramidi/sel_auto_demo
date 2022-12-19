package utility.helper;

import common.ExtentTestManager;
import commons.objects.ApiRequest;
import commons.objects.ApiResponse;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RestClientLib {
    private static final OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static ApiResponse get(ApiRequest request){
        ApiResponse apiResponse = new ApiResponse();
        Headers headerBuild = Headers.of("Accept","application/json");
        HttpUrl.Builder httpBuilder = HttpUrl.parse(request.url).newBuilder();
        if (request.params != null) {
            for(Map.Entry<String, String> param : request.params.entrySet()) {
                httpBuilder.addQueryParameter(param.getKey(),param.getValue());
            }
        }
        if (request.headers != null) {
            headerBuild = Headers.of(request.headers);
        }

        Request requestBuild = new Request.Builder()
                .headers(headerBuild)
                .url(httpBuilder.build())
                .build();
        Response response = null;
        String responseString = null;
        try {
            response = client.newCall(requestBuild).execute();
            responseString = response.body().string();
            apiResponse.response = response;
            apiResponse.responseCode = response.code();
            apiResponse.responseBody = responseString;
            ExtentTestManager.printReport(" GET REQUEST URL ::: "+request.url+ "\n"+" RESPONSE ::: "+apiResponse.responseBody,"PASSED",RestClientLib.class);

        } catch (IOException e) {
            responseString = e.getMessage();
            apiResponse.response = null;
            apiResponse.responseCode = -1;
            apiResponse.responseBody = responseString;
            ExtentTestManager.printReport(" GET REQUEST URL ::: "+request.url+"\n"+" RESPONSE ::: "+apiResponse.responseBody,"FAILED",RestClientLib.class);
        }
        return apiResponse;
    }

    public static ApiResponse post(ApiRequest request){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.isError=false;
        RequestBody body = RequestBody.create(JSON, request.body);
        HttpUrl.Builder httpBuilder = HttpUrl.parse(request.url).newBuilder();
        Headers headerBuild = Headers.of("Accept","application/json");
        if (request.params != null) {
            for(Map.Entry<String, String> param : request.params.entrySet()) {
                httpBuilder.addQueryParameter(param.getKey(),param.getValue());
            }
        }
        if (request.headers != null) {
            headerBuild = Headers.of(request.headers);
        }
        Request requestBuild = new Request.Builder()
                .headers(headerBuild)
                .url(httpBuilder.build())
                .post(body)
                .build();
        Response response = null;
        String responseString = null;
        try {
            response = client.newCall(requestBuild).execute();
            responseString = response.body().string();
            apiResponse.response = response;
            apiResponse.responseCode = response.code();
            apiResponse.responseBody = responseString;
            ExtentTestManager.printReport("POST REQUEST BODY ::: "+ request.body+"\n"+" POST REQUEST URL ::: "+request.url+"\n"+" RESPONSE ::: "+apiResponse.responseBody,"PASSED",RestClientLib.class);
        } catch (IOException e) {
            responseString = e.getMessage();
            apiResponse.response = null;
            apiResponse.responseCode = -1;
            apiResponse.responseBody = responseString;
            apiResponse.isError = true;
            ExtentTestManager.printReport("POST REQUEST BODY ::: "+ request.body+"\n"+" POST REQUEST URL ::: "+request.url+"\n"+" RESPONSE ::: "+apiResponse.responseBody,"FAILED",RestClientLib.class);
        }
        return apiResponse;
    }

    public static ApiResponse post(ApiRequest request,String fileName,String path){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.isError=false;
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", fileName,
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File(path))).build();
        HttpUrl.Builder httpBuilder = HttpUrl.parse(request.url).newBuilder();
        Headers headerBuild = Headers.of("Accept","application/json");
        if (request.params != null) {
            for(Map.Entry<String, String> param : request.params.entrySet()) {
                httpBuilder.addQueryParameter(param.getKey(),param.getValue());
            }
        }
        if (request.headers != null) {
            headerBuild = Headers.of(request.headers);
        }
        Request requestBuild = new Request.Builder()
                .headers(headerBuild)
                .url(httpBuilder.build())
                .post(requestBody)
                .build();
        Response response = null;
        String responseString = null;
        try {
            response = client.newCall(requestBuild).execute();
            responseString = response.body().string();
            apiResponse.response = response;
            apiResponse.responseCode = response.code();
            apiResponse.responseBody = responseString;
            ExtentTestManager.printReport("POST REQUEST BODY ::: "+ request.body+"\n"+" POST REQUEST URL ::: "+request.url+"\n"+" RESPONSE ::: "+apiResponse.responseBody,"PASSED",RestClientLib.class);
        } catch (IOException e) {
            responseString = e.getMessage();
            apiResponse.response = null;
            apiResponse.responseCode = -1;
            apiResponse.responseBody = responseString;
            apiResponse.isError = true;
            ExtentTestManager.printReport("POST REQUEST BODY ::: "+ request.body+"\n"+" POST REQUEST URL ::: "+request.url+"\n"+" RESPONSE ::: "+apiResponse.responseBody,"FAILED",RestClientLib.class);
        }
        return apiResponse;
    }

    public static ApiResponse put(ApiRequest request){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.isError = false;
        RequestBody body = RequestBody.create(JSON, request.body);
        HttpUrl.Builder httpBuilder = HttpUrl.parse(request.url).newBuilder();
        Headers headerBuild = Headers.of("Accept","application/json");
        if (request.params != null) {
            for(Map.Entry<String, String> param : request.params.entrySet()) {
                httpBuilder.addQueryParameter(param.getKey(),param.getValue());
            }
        }
        if (request.headers != null) {
            headerBuild = Headers.of(request.headers);
        }
        Request requestBuild = new Request.Builder()
                .headers(headerBuild)
                .url(httpBuilder.build())
                .put(body)
                .build();
        Response response = null;
        String responseString = null;
        try {
            response = client.newCall(requestBuild).execute();
            responseString = response.body().string();
            apiResponse.response = response;
            System.out.println("resp "+response.body());
            apiResponse.responseCode = response.code();
            apiResponse.responseBody = responseString;
            System.out.println("resp "+responseString);
            ExtentTestManager.printReport("PUT REQUEST BODY ::: "+ request.body+"\n"+" PUT REQUEST URL ::: "+request.url+"\n"+" RESPONSE ::: "+apiResponse.responseBody,"PASSED",RestClientLib.class);
        } catch (IOException e) {
            responseString = e.getMessage();
            apiResponse.response = null;
            apiResponse.responseCode = -1;
            apiResponse.responseBody = responseString;
            apiResponse.isError=true;
            ExtentTestManager.printReport("PUT REQUEST BODY ::: "+ request.body+"\n"+" PUT REQUEST URL ::: "+request.url+"\n"+" RESPONSE ::: "+apiResponse.responseBody,"FAILED",RestClientLib.class);
        }
        return apiResponse;
    }

    public static ApiResponse delete(ApiRequest request){
        ApiResponse apiResponse = new ApiResponse();
        Headers headerBuild = Headers.of("Accept","application/json");
        HttpUrl.Builder httpBuilder = HttpUrl.parse(request.url).newBuilder();
        if (request.params != null) {
            for(Map.Entry<String, String> param : request.params.entrySet()) {
                httpBuilder.addQueryParameter(param.getKey(),param.getValue());
            }
        }
        if (request.headers != null) {
            headerBuild = Headers.of(request.headers);
        }

        Request requestBuild = new Request.Builder()
                .headers(headerBuild)
                .url(httpBuilder.build())
                .delete()
                .build();
        Response response = null;
        String responseString = null;
        try {
            response = client.newCall(requestBuild).execute();
            responseString = response.toString();
            apiResponse.response = response;
            apiResponse.responseCode = response.code();
            apiResponse.responseBody = responseString;
            ExtentTestManager.printReport(" DELETE REQUEST URL ::: "+request.url+"\n"+" RESPONSE ::: "+apiResponse.responseBody,"PASSED",RestClientLib.class);
        } catch (IOException e) {
            responseString = e.getMessage();
            apiResponse.response = null;
            apiResponse.responseCode = -1;
            apiResponse.responseBody = responseString;
            ExtentTestManager.printReport(" DELETE REQUEST URL ::: "+request.url+"\n"+" RESPONSE ::: "+apiResponse.responseBody,"FAILED",RestClientLib.class);
        }
        return apiResponse;
    }
}
