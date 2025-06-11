package com.zachbenevento;

import org.apache.commons.lang3.time.StopWatch;
import javax.visrec.ml.classification.ImageClassifier;
import javax.visrec.ml.classification.NeuralNetImageClassifier;
import javax.visrec.ml.model.ModelCreationException;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;

public class Train {

    public static void main(String[] args) {
        String iDir = "training_data/KidImages/train";
        StopWatch sw = new StopWatch();
        sw.start();
        try {
            ImageClassifier<BufferedImage> classifier = NeuralNetImageClassifier.builder()
                    .inputClass(BufferedImage.class)
                    .imageHeight(64)
                    .imageWidth(64)
                    .labelsFile(Paths.get(iDir + "/labels-kids.txt"))
                    .trainingFile(Paths.get(iDir + "/dataset-kids.txt"))
                    .networkArchitecture(Paths.get(iDir + "/arch.json"))
                    .exportModel(Paths.get(iDir + "/model-kids.dnet"))
                    .maxError(0.02f)
                    .maxEpochs(200)
                    .learningRate(0.002f)
                    .build();

        } catch (ModelCreationException mcx) {
            System.err.println("*****Cannot create Classifier (and model): " + mcx.getMessage());
        }
        System.out.println("Training completed in " + sw.getTime() + " ms");
    }
}
