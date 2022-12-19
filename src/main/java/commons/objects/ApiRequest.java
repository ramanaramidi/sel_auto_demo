package commons.objects;

import java.util.Map;

public class ApiRequest {
    public String url;
    public Map<String,String> params;
    public Map<String,String> headers;

    public String body;

    public ApiRequest(String path, Map<String,String> params, Map<String,String> headers, String body)
    {
        this.url = path;
        this.params = params;
        this.headers = headers;
        this.body = body;
    }
}
