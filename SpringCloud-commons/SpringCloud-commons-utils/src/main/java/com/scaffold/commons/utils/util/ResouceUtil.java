package com.scaffold.commons.utils.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class ResouceUtil {

    public static List<String> readResouce(String path) {
        List<String> lines = new ArrayList<>();
        try {
            ClassPathResource resource = new ClassPathResource(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();
            System.out.println(lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static String readResouce(Resource resource) {
        StringBuilder lines = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.append(line);
            }
            reader.close();
            System.out.println(lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines.toString();
    }
}