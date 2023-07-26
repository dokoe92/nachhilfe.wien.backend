package codersbay.vienna.nachhilfe.wien.backend.service;

import org.springframework.stereotype.Component;

import org.imgscalr.Scalr;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ImageService {

    public String checkBase64(String imageBase64, Integer maxLength) throws IOException {
        if (imageBase64 == null) {
            throw new IllegalArgumentException("Missing image data");
        }
        if (imageBase64.length() > maxLength) {
            throw new IllegalArgumentException("Image is too large");
        }

        try {
            Pattern pattern = Pattern.compile("^data:image/(.*?);base64,");
            Matcher matcher = pattern.matcher(imageBase64);

            if (!matcher.find()) {
                throw new IllegalArgumentException("Invalid base64 image string");
            }

            String type = matcher.group(1);

            String base64Data = imageBase64.substring(matcher.end());
            byte[] imageBytes = Base64.getDecoder().decode(base64Data);
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
            BufferedImage resizedImg = Scalr.resize(img, 800);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImg, type, baos);
            byte[] resizedImageBytes = baos.toByteArray();

            return "data:image/" + type + ";base64," + Base64.getEncoder().encodeToString(resizedImageBytes);

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid base64 input");
        }
    }
}
