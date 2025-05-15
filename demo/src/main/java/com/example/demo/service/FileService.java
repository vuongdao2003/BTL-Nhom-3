package com.example.demo.service;

import com.example.demo.service.imp.FileServiceimp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
@Service
public class FileService implements FileServiceimp {

    @Value("${fileUpload.rootPath}")
    String rootPath;
    private Path root;
    private void init() {
       try{
           root = Paths.get(rootPath);
           if(!Files.exists(root)){
               Files.createDirectories(root);
           }
       }catch (Exception e){
           System.out.println("ERROR CREATING ROOT"+e.getMessage());
       }
    }
    @Override
    public boolean saveFile(MultipartFile file) {
        try{
            init();
            Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            return true;
        }catch (Exception e){
            System.out.println("ERROR SAVE FILE"+e.getMessage()); return false;
        }

    }

    @Override
    public org.springframework.core.io.Resource loadFile(String filename) {
        try{
            init();
            Path file = root.resolve(filename);
            UrlResource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return (org.springframework.core.io.Resource) resource;
            }
        } catch (Exception e) {
            System.out.println("ERROR LOADING FILE"+e.getMessage());
            return null;
        }
        return null;
    }
}
