package com.zachbenevento;

import org.apache.commons.lang3.time.StopWatch;
import javax.imageio.ImageIO;
import javax.visrec.ml.classification.ImageClassifier;
import javax.visrec.ml.classification.NeuralNetImageClassifier;
import javax.visrec.ml.model.ModelCreationException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

public class WhichKidApp {

    public static void main(String[] args) throws ModelCreationException, IOException {
        String imagesDir = "training_data/KidImages";
        String modelFile = imagesDir + "/" + "train/model-kids.dnet";
        System.out.println("imagesDir: " + imagesDir);
        System.out.println("modelFile: " + modelFile);
        var cl = NeuralNetImageClassifier.builder()
                .inputClass(BufferedImage.class)
                .imageHeight(64)
                .imageWidth(64)
                .importModel(Paths.get(modelFile))
                .build();

        StopWatch sw = new StopWatch();
        sw.start();
        System.out.println("Predictions:");
        Map<String, Float> fkMap = classifyImage(cl, imagesDir + "/" + "fk-1.jpg");
        System.out.println(classifyImage(cl, imagesDir + "/" + "fk-1.jpg"));
        System.out.println(classifyImage(cl, imagesDir + "/" + "fk-2.jpg"));
        System.out.println(classifyImage(cl, imagesDir + "/" + "ltk-1.jpg"));
        System.out.println(classifyImage(cl, imagesDir + "/" + "ltk-2.jpg"));
        System.out.println(classifyImage(cl, imagesDir + "/" + "nk.png"));
        sw.stop();
        System.out.println("Classification completed in " + sw.getTime() + " ms ====================================");
    }

    private static Map<String, Float> classifyImage(ImageClassifier<BufferedImage> imc, String filename)
            throws IOException {

        File f = new File(filename);
        BufferedImage image = ImageIO.read(f);
        System.out.println("File: " + f.getAbsolutePath());
        return imc.classify(image);
    }
}