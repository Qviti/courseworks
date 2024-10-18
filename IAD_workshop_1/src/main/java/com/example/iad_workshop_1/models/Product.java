package com.example.iad_workshop_1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private String name;
    private double price;
    private int amount;
    private String imagePath;
    @Getter
    private byte[] imageFile;

    public void setImageFile(MultipartFile file) throws IOException {
        this.imageFile = file.getBytes();
        this.imagePath = file.getOriginalFilename();
    }

}



