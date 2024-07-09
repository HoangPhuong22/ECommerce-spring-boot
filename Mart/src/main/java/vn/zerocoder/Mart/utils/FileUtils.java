package vn.zerocoder.Mart.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class FileUtils {
    public static String save(String path, MultipartFile file) {
        try {
            Path uploadPath = Paths.get(path);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String fileName = file.getOriginalFilename() + "_" + timeStamp;
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);
            return fileName;
        }
        catch (IOException ex) {
            log.error("Could not create directory");
            throw new RuntimeException("Could not store the file. Error: " + ex.getMessage());
        }
    }

    public static void  delete(String path, String fileName) {
        if(fileName.equals("default/avatar.webp")) {
            log.error("Không thể xóa ảnh mặc định");
            return;
        }
        File file = new File(path + File.separator + fileName);
        if (file.exists()) {
            if (file.delete()) {
                log.info("Xóa thành công file: {}", fileName);
            } else {
                log.error("Xóa file thất bại");
            }
        } else {
            log.error("File không tồn tại");
        }
    }
}
