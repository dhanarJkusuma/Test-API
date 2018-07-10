package id.dhanarjkusuma.app.loket.service.impl;

import id.dhanarjkusuma.app.loket.exception.StorageErrorException;
import id.dhanarjkusuma.app.loket.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.UUID;

@Service
public class StorageServiceImpl implements StorageService {


    private final Path rootLocation;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public StorageServiceImpl(@Value("${storage.base-path}") String storageBasePath) {
        this.rootLocation = Paths.get(storageBasePath);
    }

    @Override
    public Path store(MultipartFile file, String path, String filename, boolean hashDirectory) throws StorageErrorException {
        Path destinationPath;
        try {
            destinationPath = initFolderUpload(path);
            if(hashDirectory){
                String uniqueID = UUID.randomUUID().toString();
                uniqueID = uniqueID.replace("-","");
                path = path + "/" + uniqueID;
                destinationPath = initFolderUpload(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new StorageErrorException(e.getMessage());
        }
        String originFileName = StringUtils.cleanPath(file.getOriginalFilename());
        filename = String.join(".", filename, getOriginalExtension(originFileName));

        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new RuntimeException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationPath.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to store file " + filename, e);
        }
        return destinationPath.resolve(filename);
    }

    private Path initFolderUpload(String path) throws IOException {
        String pathRoot = rootLocation.toString();
        Path resultPath = Paths.get(String.join("/", pathRoot, path));
        if(!pathIsExist(resultPath)){
            // create directory
            try{
                Files.createDirectories(resultPath);
            }catch (AccessDeniedException e){
                logger.error("Directory Product Not Found, Access Denied to create a Directory.");
                throw new StorageErrorException("[Error] : Directory Product Not Found, Access Denied to create a Directory.");
            }

        }
        return resultPath;
    }

    private Boolean pathIsExist(Path resultPath){
        if(Files.isDirectory(resultPath, LinkOption.NOFOLLOW_LINKS)){
            return true;
        }
        return false;
    }

    private String getOriginalExtension(String filename){
        int dotExtension = filename.indexOf(".", -0);
        if(dotExtension == -1){
            throw new RuntimeException("Invalid filename !!!");
        }
        return filename.substring(dotExtension + 1, filename.length());
    }
}
