package com.huaweisoft.xiaxh.demo2025.service.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageResizer {

    public static void resizeImage(String inputImagePath,
                                   String outputImagePath,
                                   int scaledWidth,
                                   int scaledHeight) throws IOException {
        // 读取原始图片
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);

        // 创建目标图像（TYPE_INT_ARGB 支持透明通道）
        BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);

        // 获取 Graphics2D 对象用于绘制
        Graphics2D g2d = outputImage.createGraphics();

        // 设置高质量渲染参数（可选但推荐）
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 绘制缩放后的图像
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        // 写入输出文件（自动根据扩展名选择格式）
        String formatName = outputImagePath.substring(outputImagePath.lastIndexOf('.') + 1);
        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }

    public static void main(String[] args) throws IOException {
//        try {
//            resizeImage("E:\\jg\\demo\\1.png", "E:\\jg\\demo\\sf\\1.png", 600, 600);
//            resizeImage("E:\\jg\\demo\\2.png", "E:\\jg\\demo\\sf\\2.png", 600, 600);
//            resizeImage("E:\\jg\\demo\\3.png", "E:\\jg\\demo\\sf\\3.png", 600, 600);
//            resizeImage("E:\\jg\\demo\\4.png", "E:\\jg\\demo\\sf\\4.png", 600, 600);
//            resizeImage("E:\\jg\\demo\\5.png", "E:\\jg\\demo\\sf\\5.png", 600, 600);
//            resizeImage("E:\\jg\\demo\\6.png", "E:\\jg\\demo\\sf\\6.png", 600, 600);
//
//            resizeImage("E:\\jg\\demo\\1 (1).png", "E:\\jg\\demo\\sf\\1 (1).png", 600, 600);
//            resizeImage("E:\\jg\\demo\\1 (2).png", "E:\\jg\\demo\\sf\\1 (2).png", 600, 600);
//            resizeImage("E:\\jg\\demo\\1 (3).png", "E:\\jg\\demo\\sf\\1 (3).png", 600, 600);
//            resizeImage("E:\\jg\\demo\\1 (4).png", "E:\\jg\\demo\\sf\\1 (4).png", 600, 600);
//            resizeImage("E:\\jg\\demo\\1 (5).png", "E:\\jg\\demo\\sf\\1 (5).png", 600, 600);
//            resizeImage("E:\\jg\\demo\\1 (6).png", "E:\\jg\\demo\\sf\\1 (6).png", 600, 600);
//            System.out.println("图片缩放成功！");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        File[] list = new File("E:\\jg\\demo\\video\\png").listFiles((dir, fileName) -> fileName.endsWith(".png"));
        for (int i = 0; i < list.length; i++) {
            //720x1280 -> 360x640
            System.out.println(list[i].getName());
            resizeImage(list[i].getPath(), "E:\\jg\\demo\\video\\sf\\"+String.format("%05d", i)+".png", 180, 320);
        }
    }
}