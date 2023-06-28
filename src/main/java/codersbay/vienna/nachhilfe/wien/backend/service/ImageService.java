package codersbay.vienna.nachhilfe.wien.backend.service;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class ImageService {

    public void checkBase64(String imageBase64, Integer maxLength) {
        if (imageBase64 == null) {
            throw new IllegalArgumentException("Missing image data");
        }
        if (imageBase64.length() > maxLength) {
            throw new IllegalArgumentException("Image is too large");
        }

        try {
            String[] parts = imageBase64.split(",");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid image data");
            }
            String base64Part = parts[1];
            Base64.getDecoder().decode(base64Part);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid base64 input");
        }
    }
}
