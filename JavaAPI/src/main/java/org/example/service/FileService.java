package org.example.service;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

@Service
public class FileService {

    @Value("${upload.dir}")
    private String uploadDir;

    private String extension = "webp";

    private Map<String, Integer> sizeMap = Map.of(
            "small", 30,
            "medium", 100,
            "large", 1000
    );

    public String load(MultipartFile file) {
        // Перевіряємо, чи файл не порожній
        if (file.isEmpty()) return "";

        try (var inputStream = file.getInputStream()) {
            var fileName = saveStreamToFile(inputStream);
            return fileName;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public String load(String imageUrl) {
        // Перевіряємо, чи посилання коректне
        if (!imageUrl.startsWith("http://") && !imageUrl.startsWith("https://"))
            return "";

        try (var inputStream = new URL(imageUrl).openStream()) {
            var fileName = saveStreamToFile(inputStream);
            return fileName;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public String loadBase64(String base64String) {
        try {
            String[] parts = base64String.split(",", 2);
            if (parts.length != 2) return "";

            byte[] imageBytes = Base64.getDecoder().decode(parts[1]);
            try (InputStream stream = new ByteArrayInputStream(imageBytes)) {
                return saveStreamToFile(stream);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }


    private String saveStreamToFile(InputStream stream) throws IOException {
        Files.createDirectories(Paths.get(uploadDir));  //створить якщо не існує
        String fileName = UUID.randomUUID() + "." + extension;
        var bufferedImage = ImageIO.read(stream);

        for (var entry : sizeMap.entrySet()) {
            var folder = entry.getKey();
            var size = entry.getValue();
            Files.createDirectories(Paths.get(uploadDir, folder));
            String filePath = Paths.get(uploadDir, folder, fileName).toString();
            Thumbnails.of(bufferedImage).size(size,size).outputFormat(extension).toFile(filePath);
        }
        return fileName;
    }

    public void remove(String fileName) {
        try {
            for (String folder : sizeMap.keySet()) {
                Path filePath = Paths.get(uploadDir, folder, fileName);
                Files.deleteIfExists(filePath);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String replace(String oldFileName, MultipartFile newFile) {
        var newFileName = load(newFile);
        if (newFileName == ""){
            return oldFileName;
        }
        remove(oldFileName);
        return newFileName;
    }
}