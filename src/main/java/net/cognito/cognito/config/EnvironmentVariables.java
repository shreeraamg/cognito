package net.cognito.cognito.config;

import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class EnvironmentVariables {
    private static final Map<String, String> env;

    static {
        env = loadEnvFile("C:\\Users\\shree\\Learnings\\Projects\\cognito\\.env.local");
    }

    public static String getEnvVariable(String key) {
        return env.get(key);
    }

    private static Map<String, String> loadEnvFile(String pathToEnvFile) {
        Map<String, String> env = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(pathToEnvFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if(parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    env.put(key, value);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error loading .env file: " + ex.getMessage());
        }

        return env;
    }
}
