package com.huaweisoft.xiaxh.demo2025.service.util;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.bytedeco.javacpp.Loader;

import java.io.File;

/**
 * @author xiaxh
 * @date 2024/9/12
 */
public class FfmpegUtil {

    public static void main(String[] args) {

        String[] strings = videoToPng("E:\\jg\\demo\\video\\2.mp4", "E:\\jg\\demo\\video\\sf");
        for (String string : strings) {
            System.out.println(string);
        }
    }

    /**
     * 视频转图片（使用默认参数）
     *
     * @param videoPath 视频路径
     * @param pngPath 输出图片路径
     * @return
     */
    public static String[] videoToPng(String videoPath, String pngPath) {
        return videoToPng(videoPath, pngPath, 10, 180, 2);
    }

    /**
     * 视频转图片
     *
     * @param videoPath 视频路径
     * @param pngPath 输出图片路径
     * @param fps 帧率（每秒帧数）
     * @param minWidth 最小宽度
     * @param quality 视频质量（1-31，数值越小质量越高）
     * @return
     */
    public static String[] videoToPng(String videoPath, String pngPath, int fps, int minWidth, int quality) {
        String name = new File(videoPath).getName();
        name = name.substring(0, name.lastIndexOf("."));
        try {
            //创建FFmpeg对象
            FFmpeg ffmpeg = new FFmpeg(Loader.load(org.bytedeco.ffmpeg.ffmpeg.class));
            //创建FFmpegBuilder对象
            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(videoPath)
                    .overrideOutputFiles(true)
                    .addOutput(pngPath+"/"+name+"_%05d.jpg")
                    .addExtraArgs("-vf", String.format("fps=%d,scale='min(%d,iw)':-1", fps, minWidth))
                    .addExtraArgs("-qscale:v", String.valueOf(quality))
                    .done();
            new FFmpegExecutor(ffmpeg).createJob(
                    builder,
                    progress -> System.out.println("ffmpeg: " + progress)
            ).run();
            return new File(pngPath).list((dir, fileName) -> fileName.endsWith(".jpg"));
        } catch (Exception e) {
            throw new RuntimeException("视频转图片.异常:" + e.getMessage());
        }
    }

}
