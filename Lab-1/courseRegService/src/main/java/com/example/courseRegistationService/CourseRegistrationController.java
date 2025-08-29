package com.example.courseRegistationService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/courses")
public class CourseRegistrationController {
    
    private String readFile(String filename) throws IOException {
        
        File file = new File(filename);
        if (!file.exists()){
            file.createNewFile();

            try (FileWriter writer = new FileWriter(file)) {
                writer.write("{}");
            }
        }
        return new String(Files.readAllBytes(Paths.get(filename)));
    }

    private void writeFile(String filename, JSONObject jsonObject) throws IOException {
        
        try(FileWriter fileWriter = new FileWriter(filename)) {
            
            fileWriter.write(jsonObject.toString(4));
        }
    
    }

    @GetMapping("/register/{registrationID}")
    public String getMethodName(@PathVariable String registrationID) {
        String filename = "C:/Users/parij/Downloads/Cloud/Lab/Lab-1/registered.json";

        try {

            String content = readFile(filename);
            JSONObject registrationData = new JSONObject(content);
            
            if (registrationData.has(registrationID)) {
                return "You are already registered!";
            } 
                
            registrationData.put(registrationID, new JSONArray());
            
            writeFile(filename, registrationData);
            
            return "Registration successful!";
            

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
}
