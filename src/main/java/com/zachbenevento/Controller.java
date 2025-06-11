package com.zachbenevento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private final Service service;

    public Controller(Service imageStorageService) {
        this.service = imageStorageService;
    }

    @GetMapping("/")
    public String index(@RequestParam(required = false) String image,
                        @RequestParam(required = false) String status,
                        Model model) {

        model.addAttribute("image", image);
        model.addAttribute("status", status);
        return "index";
    }


    @PostMapping("/upload")
    public String handleImageUpload(@RequestParam("file") MultipartFile file) {
        try {
            Result status = service.store(file);
            return "redirect:/?image="
                    + URLEncoder.encode(status.getFilename(), StandardCharsets.UTF_8)
                    + "&status="
                    + URLEncoder.encode(status.getRes(), StandardCharsets.UTF_8);

        } catch (Exception e) {
            return "redirect:/?status=Upload+failed";
        }
    }

//    @PostMapping("/batchUpload")
//    public String handleBatchUpload(@RequestParam("file") MultipartFile zipFile) {
//        try {
//            Result status = service.store(zipFile);
//        } catch (Exception e) {
//            return "redirect:/?status=Upload+failed";
//        }
//    }
}
