package com.iquinto.workingstudent.service;

import com.iquinto.workingstudent.model.Photo;
import com.iquinto.workingstudent.model.User;
import com.iquinto.workingstudent.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class PhotoStorageService {
    @Autowired
    private PhotoRepository photoRepository;

    public Photo store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Photo f = new Photo(fileName, file.getContentType(), file.getBytes());
        return photoRepository.save(f);
    }

    public Photo storeWithFile(String fileName, String contentType, byte[] data) throws IOException {
        Photo f = new Photo(fileName, contentType, data);
        return photoRepository.save(f);
    }

    public Photo getFile(String id) {
        return photoRepository.findById(id);
    }

    public Photo findByUser(User user) {
        return photoRepository.findByUser(user);
    }

    public void delete(Photo photo) {
        photoRepository.delete(photo);
    }

    public Stream<Photo> getAllFiles() {
        return photoRepository.findAll().stream();
    }

    public void deleteAll(){
        photoRepository.deleteAll();
    }
}
