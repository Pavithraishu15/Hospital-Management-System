package com.hospital.hms.controller;


import com.hospital.hms.model.Lab;
import com.hospital.hms.service.FileUploadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController

public class FileUploadController {

    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }
    @GetMapping("/{id}/file-status")
    public ResponseEntity<String> getFileStatus(@PathVariable Long id) {
        String status = fileUploadService.getFileStatus(id);
        return ResponseEntity.ok(status);
    }
    @GetMapping("/lab")
    public List<Lab>getLab()
    {
        return fileUploadService.getLab();
    }
    @PostMapping("/lab")
    public Lab createLab(@RequestBody Lab lab)
    {
        return fileUploadService.createLab(lab);
    }

    // Handle image upload via POST request
    @PostMapping("/api/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("No file selected", HttpStatus.BAD_REQUEST);
        }

        try {
            // Call the service to upload the file
            String filename = fileUploadService.uploadImage(file);
            return ResponseEntity.ok().body("File uploaded successfully: " + filename);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

