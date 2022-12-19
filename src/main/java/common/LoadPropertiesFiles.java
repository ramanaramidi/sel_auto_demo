package common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by tahmmed1 on 1/9/2017.
 */
public class LoadPropertiesFiles {

    public static Properties loadProperties(String filePath) throws IOException {
        Properties prop = new Properties();
        InputStream ism = new FileInputStream(filePath);
        prop.load(ism);
        ism.close();
        return prop;
    }
}
