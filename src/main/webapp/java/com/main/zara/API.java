package com.main.zara;


import nu.pattern.OpenCV;
import org.opencv.core.*;

import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;

import java.util.ArrayList;
import java.util.Arrays;


public class API {
    //Lab4
    public String testSegments(int height_search, int width_search) {
        int rect1 = 0, rect2 = 0;
        String info="";
        try {
            OpenCV.loadLocally();

            String pathImg = new Constant().pathResources + new Constant().nomer1;
            Mat mImg = Imgcodecs.imread(pathImg);

            String dirPath = new Constant().pathResources + new Constant().pathFLab4;

            // 1
            Mat grayImage = new Mat();
            Imgproc.cvtColor(mImg, grayImage, Imgproc.COLOR_BGR2GRAY);
            Imgcodecs.imwrite(dirPath + "grayImage.jpg", grayImage);
            // 2
            Mat denoisingImage = new Mat();
            Photo.fastNlMeansDenoising(grayImage, denoisingImage);
            Imgcodecs.imwrite(dirPath + "noNoise.jpg", denoisingImage);
            // 3
            Mat histogramEqualizationImage = new Mat();
            Imgproc.equalizeHist(denoisingImage, histogramEqualizationImage);
            Imgcodecs.imwrite(dirPath + "histogramEq.jpg", histogramEqualizationImage);
            // 4
            Mat morphologicalOpeningImage = new Mat();
            Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
            Imgproc.morphologyEx(histogramEqualizationImage, morphologicalOpeningImage,
                    Imgproc.MORPH_RECT, kernel);
            Imgcodecs.imwrite(dirPath + "morphologicalOpening.jpg", morphologicalOpeningImage);
            // 5
            Mat subtractImage = new Mat();
            Core.subtract(histogramEqualizationImage, morphologicalOpeningImage, subtractImage);
            Imgcodecs.imwrite(dirPath + "subtract.jpg", subtractImage);
            // 6
            Mat thresholdImage = new Mat();
            double threshold = Imgproc.threshold(subtractImage, thresholdImage, 127, 255,
                    Imgproc.THRESH_OTSU);
            Imgcodecs.imwrite(dirPath + "threshold.jpg", thresholdImage);
            thresholdImage.convertTo(thresholdImage, CvType.CV_16SC1);
            // 7
            Mat edgeImage = new Mat();
            thresholdImage.convertTo(thresholdImage, CvType.CV_8U);
            //displayImage(thresholdImage, "tr2");
            Imgproc.Canny(thresholdImage, edgeImage, threshold, threshold * 3, 3, true);
            Imgcodecs.imwrite(dirPath + "edge.jpg", edgeImage);
            // 8
            Mat dilatedImage = new Mat();
            Imgproc.dilate(thresholdImage, dilatedImage, kernel);
            Imgcodecs.imwrite(dirPath + "dilation.jpg", dilatedImage);
            // 9

            ArrayList<MatOfPoint> contours = new ArrayList<>();

            //displayImage(denoisingImage, "gray");
            Imgproc.findContours(dilatedImage, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
            //contours.sort(Collections.reverseOrder(Comparator.comparing(Imgproc::contourArea)));

            /*
            Mat gImg = new Mat(mImg.size(), mImg.type());

            for (int i=0; i<contours.size(); i++){
                Imgproc.drawContours(gImg, contours, -1, new Scalar(255,255,0), 1);
            }
            */



            for (MatOfPoint contour : contours.subList(0, contours.size())) {
                //new Constant().run(Imgproc.contourArea(contour));
                MatOfPoint2f point2f = new MatOfPoint2f();
                MatOfPoint2f approxContour2f = new MatOfPoint2f();
                MatOfPoint approxContour = new MatOfPoint();
                contour.convertTo(point2f, CvType.CV_32FC2);
                double arcLength = Imgproc.arcLength(point2f, true);
                Imgproc.approxPolyDP(point2f, approxContour2f, 0.01 * arcLength, true);
                approxContour2f.convertTo(approxContour, CvType.CV_32S);
                Rect rect = Imgproc.boundingRect(approxContour);
                //double ratio = (double) rect.height / rect.width;


                if (approxContour.total() == 4) {

                    if(rect.width >= width_search && rect.height >= height_search){
                        rect2++;
                    }

                    rect1++;
                }

            }//for

            //logger("Обнаружено = " + rect1 + " прямоугольников");
            new Constant().run("Обнаружено = " + rect1 + " прямоугольников");
            new Constant().run("Обнаружено с размерами (" + width_search +"х" +height_search + ") и больше = "
                    + rect2 + " прямоугольников");

            info = "Обнаружено с размерами (" + width_search +"х" +height_search + ") и больше = "
                    + rect2 + " прямоугольников";

        }catch (Exception ex){
            new Constant().run(Arrays.toString(ex.getStackTrace()));
        }

        return info;



    }//testSegments

}
