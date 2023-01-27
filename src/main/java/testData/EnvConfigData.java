package testData;

import common.LoadPropertiesFiles;
import commons.objects.EnvConfig;

import java.io.IOException;
import java.util.Properties;

public class EnvConfigData {

    private EnvConfigData(){}
    static Properties envConfigProperties;
    static {
        try {
            envConfigProperties = LoadPropertiesFiles.loadProperties(System.getProperty("user.dir") + "/src/test/resources/testData/userData.properties");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static EnvConfig envConfig ;
    public static EnvConfig setEnvData(String env,EnvConfig envConfig){

        switch (env){

            case "dev" :{
                envConfig.setEnv("dev");
                envConfig.setWebUrl(envConfigProperties.getProperty("dev.webUrl"));
                envConfig.setApiUrl(envConfigProperties.getProperty("dev.apiUrl"));
                break;
            }

            case "prod" :{
                envConfig.setEnv("prod");
                envConfig.setWebUrl(envConfigProperties.getProperty("prod.webUrl"));
                envConfig.setApiUrl(envConfigProperties.getProperty("prod.apiUrl"));
                break;
            }

            case "preProd" :{
                envConfig.setEnv("preProd");
                envConfig.setWebUrl(envConfigProperties.getProperty("preProd.webUrl"));
                envConfig.setApiUrl(envConfigProperties.getProperty("preProd.apiUrl"));
                break;
            }

            default:{
                envConfig.setEnv("uat");
                envConfig.setWebUrl(envConfigProperties.getProperty("uat.webUrl"));
                envConfig.setApiUrl(envConfigProperties.getProperty("uat.apiUrl"));
                break;
            }

        }
        return envConfig;
    }

    public static EnvConfig getInstance(){
        return envConfig;
    }
}
