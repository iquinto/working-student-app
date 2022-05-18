package com.iquinto.workingstudent.controller;

import com.iquinto.workingstudent.model.Photo;
import com.iquinto.workingstudent.model.User;
import com.iquinto.workingstudent.payload.MessageResponse;
import com.iquinto.workingstudent.payload.FileResponse;
import com.iquinto.workingstudent.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/photo")
public class PhotoController {
    private static final Logger log = LoggerFactory.getLogger(PhotoController.class);

    @Value("${iquinto.app.filePath}")
    private String filePath;

    @Autowired
    private PhotoStorageService photoStorageService;

    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationService authenticationService;


    @RequestMapping(value ="/avatar", method = RequestMethod.GET)
    public ResponseEntity<?> getProfilePicture() {

        User user = userService.findByUsername(authenticationService.getCurrentUsername());

        if(user.getPhoto() == null){
            String message = "El usuario no dispone una foto de perfil. Para definir la foto de perfil por favor pulsa el buton 'Editar Foto'!";
            log.info("[c: file] [m:getProfilePicture] " + message);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
        }
        Photo dbPhoto = photoStorageService.getFile(user.getPhoto().getId());
        String fileDownloadUri  = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(filePath + "photo/")
                .path(dbPhoto.getId())
                .toUriString();
        FileResponse fileResponse = new FileResponse(
                dbPhoto.getName(),
                fileDownloadUri,
                dbPhoto.getType(),
                dbPhoto.getData().length);
        return ResponseEntity.status(HttpStatus.OK).body(fileResponse);
    }

    @RequestMapping(value ="/avatar/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> getAvatarByUsername(@PathVariable String username) {
        log.info("[c: file] [m:getAvatarByUsername] username: " + username);
        User user = userService.findByUsername(username);
        log.info("[c: file] [m:getAvatarByUsername] user " + user);

        if(user == null){
            String message = "El usuario no existe en el sistema.";
            log.error("[c: file] [m:getAvatarByUsername] user not found!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(message));
        }

        Photo photo = user.getPhoto();
        log.info("[c: file] [m:getAvatarByUsername] photo:" + photo);

        if(photo == null){
            String message = "El usuario no dispone del foto de perfil.";
            log.warn("[m:getAvatarByUsername] file not found!");
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
        }

        String fileDownloadUri  = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(filePath + "photo/")
                .path(photo.getId())
                .toUriString();

        FileResponse fileResponse = new FileResponse(
                photo.getName(),
                fileDownloadUri,
                photo.getType(),
                photo.getData().length);

        log.info("[c: file] [m:getAvatarByUsername] photo uri:" + fileDownloadUri);


        return ResponseEntity.status(HttpStatus.OK).body(fileResponse);
    }

    @RequestMapping(value ="/setProfilePicture", method = RequestMethod.POST)
    public ResponseEntity<?> setProfilePic(@RequestParam("file") MultipartFile file) {

        User user = userService.findByUsername(authenticationService.getCurrentUsername());
        String message = "";
        log.info("[c: file] [m:setProfilePic] uploading file for : " + user.toString() );
        try {
            Photo f = photoStorageService.store(file);
            user.setPhoto(f);
            userService.save(user);
            message = "Se ha actualizado la foto de perfil correctamente.";
            log.info("[c: file] [m:setProfilePic] " + message );
            Photo dbPhoto = photoStorageService.getFile(f.getId());
            String fileDownloadUri  = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path(filePath + "photo/")
                    .path(dbPhoto.getId())
                    .toUriString();
            FileResponse fileResponse = new FileResponse(
                    dbPhoto.getName(),
                    fileDownloadUri,
                    dbPhoto.getType(),
                    dbPhoto.getData().length);
            fileResponse.setMessage(message);
            log.info("[c: file] [m:setProfilePic] " + fileResponse.getUrl() );
            return ResponseEntity.status(HttpStatus.OK).body(fileResponse);
        } catch (Exception e) {
            message = "No se puede actualizar " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }

}
