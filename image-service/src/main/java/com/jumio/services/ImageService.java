package com.jumio.services;

import com.google.gson.Gson;
import models.Image;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.JsonHelper;

import java.io.FileNotFoundException;
import java.io.FileReader;

@RestController
@SpringBootApplication
public class ImageService {

    /**
     * read json file and produces with simple image (not converted to base64)
      * @return
     */
    @RequestMapping(value = "/image", produces = MediaType.APPLICATION_JSON_VALUE)
    public Image image() {
        Image image = (Image)JsonHelper.readFromJson("c:\\temp\\image.json");
        return image;
    }

    /**
     * read json file and produces with base64 image
     * @return
     */
    @RequestMapping(value = "/image-base64", produces = MediaType.APPLICATION_JSON_VALUE)
    public Image convertedImage() {
        Image image = (Image)JsonHelper.readFromJson("c:\\temp\\base64Image.json");
        return image;
    }

    public static void main(String[] args) {
        SpringApplication.run(ImageService.class, args);
    }
}

