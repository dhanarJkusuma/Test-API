package id.dhanarjkusuma.app.loket.service;

import id.dhanarjkusuma.app.loket.exception.StorageErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {
    Path store(MultipartFile file, String path, String filename, boolean hashDirectory) throws StorageErrorException;
}
