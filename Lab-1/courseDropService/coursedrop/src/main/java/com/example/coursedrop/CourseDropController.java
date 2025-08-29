package com.example.coursedrop;

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

public class CourseDropController {
    
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
    
    @GetMapping("/drop/{registrationID}/{courseID}")
    public String dropCourse(@PathVariable String registrationID, @PathVariable String courseID) throws IOException {
        
        String filename = "C:/Users/parij/Downloads/Cloud/Lab/Lab-1/registered.json";
        
        String content = readFile(filename);
        JSONObject registrationData = new JSONObject(content);

        if(!registrationData.has(registrationID)){
            return "Please register first!";
        }

        JSONArray courses = registrationData.getJSONArray(registrationID);
        boolean removed = false;

        for (int i = 0; i < courses.length(); i++) {
            if (courses.getString(i).equals(courseID)) {
               courses.remove(i);
               removed = true;
               break;
            }
        }

        if (!removed) {
            return "Course not found in your registered courses!";
        }

        
        registrationData.put(registrationID, courses);

        writeFile(filename, registrationData);

        return "Course deleted successfully!";
    }   
    
}