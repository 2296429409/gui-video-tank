package com.huaweisoft.xiaxh.demo2025.service.util;


import Utils.ColoredTankUtils;
import Utils.InsideColoredTankUtils;
import Utils.WhiteAndBlackTankUtils;
import com.librian.lib.APNGCollector;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.librian.lib.APNGCollector.*;

/**
 * @author xiaxh
 * @date 2025/12/5
 */

public class PngToApng {

    //png   pc端正常   安卓端无表图
    //改gif  pc端正常   安卓端无内图

    public static void main(String[] args) throws Exception {
        String wFilePath = "E:\\jg\\demo\\video\\1.jpg";
        File wFile = new File(wFilePath);


        File[] listPng = new File("E:\\jg\\demo\\video\\png").listFiles((dir, fileName) -> fileName.endsWith(".jpg"));
        File[] listPngW = new File("E:\\jg\\demo\\video\\sf").listFiles((dir, fileName) -> fileName.endsWith(".jpg"));
//        for (int i = 0; i < listPng.length; i++) {
//            System.out.println(listPng[i].getName());
////            InsideColoredTankUtils.run(wFile, listPng[i], "E:\\jg\\demo\\video\\tank\\"+String.format("%05d", i)+".png");
//            InsideColoredTankUtils.run(listPngW[i], listPng[i], "E:\\jg\\demo\\video\\tank\\"+String.format("%05d", i)+".png");
//        }


        File[] list = new File("E:\\jg\\demo\\video\\tank\\").listFiles((dir, fileName) -> fileName.endsWith(".png"));
        APNGCollector apng = new APNGCollector(ImageIO.read(new File("E:\\jg\\demo\\video\\外.png")), 0, (short) 1, (short) 32767, APNG_DISPOSE_OP_NONE, APNG_BLEND_OP_SOURCE);
//        APNGCollector apng = new APNGCollector(ImageIO.read(list[0]), 0);
//        APNGCollector apng = new APNGCollector(ImageIO.read(wFile), 0);
        for (File file : list) {
            apng.addFrame(ImageIO.read(file), 0, 0, (short) 1, (short) 10, APNG_DISPOSE_OP_NONE, APNG_BLEND_OP_SOURCE);
        }
        FileOutputStream f = new FileOutputStream("E:\\jg\\demo\\video\\tank外.png");
        f.write(apng.build());
        f.close();
    }
    public static void main2(String[] args) throws IOException {


        for (int i = 1; i <= 6; i++) {
//            ColoredTankUtils.run(
//            InsideColoredTankUtils.run(
            WhiteAndBlackTankUtils.run(
                    new File("E:\\jg\\demo\\sf\\"+i+".png"),
                    new File("E:\\jg\\demo\\sf\\1 ("+i+").png"),
                    "E:\\jg\\demo\\tank\\"+i+".png");
        }

        BufferedImage
                i1 = ImageIO.read(new File("E:\\jg\\demo\\tank\\1.png")),
                i2 = ImageIO.read(new File("E:\\jg\\demo\\tank\\2.png")),
                i3 = ImageIO.read(new File("E:\\jg\\demo\\tank\\3.png")),
                i4 = ImageIO.read(new File("E:\\jg\\demo\\tank\\4.png")),
                i5 = ImageIO.read(new File("E:\\jg\\demo\\tank\\5.png")),
                i6 = ImageIO.read(new File("E:\\jg\\demo\\tank\\6.png"));

//        APNGCollector apng = new APNGCollector(i0, 0);
//        apng.addFrame(i1, 0, 0, (short) 20, (short) 60, APNG_DISPOSE_OP_NONE, APNG_BLEND_OP_OVER);
//        apng.addFrame(i2, 0, 0, (short) 60, (short) 80, APNG_DISPOSE_OP_NONE, APNG_BLEND_OP_OVER);
//        apng.addFrame(i3, 0, 0, (short) 80, (short) 100, APNG_DISPOSE_OP_NONE, APNG_BLEND_OP_OVER);
//        FileOutputStream f = new FileOutputStream("E:\\jg\\demo\\1/xia.png");
//        f.write(apng.build());
//        f.close();

        int dispose=3;
        int blend = 2;

        for (int i = 0; i < dispose; i++) {
            for (int g = 0; g < blend; g++) {
                APNGCollector apng = new APNGCollector(i1, 0);
                apng.addFrame(i2, 0, 0, (short) 1, (short) 1, (byte) i, (byte)g);
                apng.addFrame(i3, 0, 0, (short) 1, (short) 1, (byte)i, (byte)g);
                apng.addFrame(i4, 0, 0, (short) 1, (short) 1, (byte)i, (byte)g);
                apng.addFrame(i5, 0, 0, (short) 1, (short) 1, (byte)i, (byte)g);
                apng.addFrame(i6, 0, 0, (short) 1, (short) 1, (byte)i, (byte)g);
                System.out.println("E:\\jg\\demo\\1/"+i+"="+i4+".png");
                FileOutputStream f = new FileOutputStream("E:\\jg\\demo\\1/"+i+"="+g+".png");
                f.write(apng.build());
                f.close();
            }
        }


    }
}