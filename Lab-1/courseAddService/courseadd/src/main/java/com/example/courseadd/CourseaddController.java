package com.example.courseadd;

import java.io.*;
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
public class CourseaddController {
    
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


    @GetMapping("/add/{registrationID}/{courseID}")
    public String addCourse(@PathVariable String registrationID, @PathVariable String courseID) throws IOException {
        
        String filename = "C:/Users/parij/Downloads/Cloud/Lab/Lab-1/registered.json";
        
        String content = readFile(filename);
        JSONObject registrationData = new JSONObject(content);

        if(!registrationData.has(registrationID)){
            return "Please register first!";
        }

        JSONArray courses = registrationData.getJSONArray(registrationID);

        for (int i = 0; i < courses.length(); i++) {
            if (courses.getString(i).equals(courseID)) {
                return "Course already added!";
            }
        }

        courses.put(courseID);
        registrationData.put(registrationID, courses);

        writeFile(filename, registrationData);

        return "Course added successfully!";
        
    }   
    
}
