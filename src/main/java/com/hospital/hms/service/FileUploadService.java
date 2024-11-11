package com.hospital.hms.service;


import com.hospital.hms.model.Lab;
import com.hospital.hms.repository.Labrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileUploadService {
@Autowired
    Labrepo labrepo;
    @Value("${file.upload-dir}")
    private String uploadDir;
   public Lab createLab(Lab lab)
   {
       return labrepo.save(lab);
   }
   public List<Lab>getLab()
   {
       return labrepo.findAll();
   }
    // Allowed image extensions (jpg, jpeg, png)
    private static final String[] ALLOWED_EXTENSIONS = {"jpg", "jpeg", "png"};

    // Method to check if the uploaded file has a valid image extension
    public boolean isValidImage(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename == null) {
            return false;
        }
        String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
        for (String ext : ALLOWED_EXTENSIONS) {
            if (extension.equals(ext)) {
                return true;
            }
        }
        return false;
    }

    // Method to save the uploaded file to the server
    public String uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("No file selected");
        }

        // Validate if the file is an image
        if (!isValidImage(file)) {
            throw new IOException("Invalid file type. Only jpg, jpeg, and png are allowed.");
        }

        // Create the uploads directory if it doesn't exist
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Generate the file path
        String filename = file.getOriginalFilename();
        Path path = Paths.get(uploadDir, filename);

        // Save the file to the specified path
        Files.copy(file.getInputStream(), path);

        return filename;
    }
    public String getFileStatus(Long labId) {
        Lab lab = labrepo.findById(labId).orElse(null);
        if (lab != null) {
            return lab.getFileStatus(); // Return the file upload status
        }
        return "Lab not found";
    }
}

