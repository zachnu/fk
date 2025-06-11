package com.zachbenevento;

import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import javax.visrec.ml.classification.ImageClassifier;
import javax.visrec.ml.classification.NeuralNetImageClassifier;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Map;

@org.springframework.stereotype.Service
public class Service {

    private static final String UPLOAD_DIR = "static/uploads/";

    public Result store(MultipartFile file) {
        Result result = new Result();
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
            Path filePath = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
            Files.write(filePath, file.getBytes(), StandardOpenOption.CREATE);
            result.setFilename(file.getOriginalFilename());
            result.setRes(buildModelAndClassify(filePath.toString()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
        return result;
    }

//    public ArrayList<Result> storeZip(MultipartFile zipFile) {
//        ArrayList<Result> results = new ArrayList<>();
//        File dir = new File(UPLOAD_DIR + "/" + zipFile.getOriginalFilename() + "/");
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//        try (FileInputStream fis = new FileInputStream(zipFile)) {
//
//        }
//        return results;
//    }


    private static String buildModelAndClassify(String filename)  {
        String modelFile = "training_data/KidImages/train/model-kids.dnet";
        String result = "";
        try {
            var cl = NeuralNetImageClassifier.builder()
                    .inputClass(BufferedImage.class)
                    .imageHeight(64)
                    .imageWidth(64)
                    .importModel(Paths.get(modelFile))
                    .build();


            Map<String, Float> res = classifyImage(cl, filename);
            Float fks = res.get("FashionKid");
            result = fks >= 0.5 ? "Fashion Kid" : "Let the Kid";
        } catch (Exception ignored) {

        }
        return result;
    }

    private static Map<String, Float> classifyImage(ImageClassifier<BufferedImage> imc, String filename)
            throws IOException {

        File f = new File(filename);
        BufferedImage image = ImageIO.read(f);
        System.out.println("File: " + f.getAbsolutePath());
        return imc.classify(image);
    }
}
