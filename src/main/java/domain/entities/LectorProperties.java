package domain.entities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LectorProperties {
    public Properties leerProperties(String path) {
        Properties properties = new Properties();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(path);

        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }
}
