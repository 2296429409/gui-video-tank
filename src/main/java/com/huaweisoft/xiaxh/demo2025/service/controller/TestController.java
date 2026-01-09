package com.huaweisoft.xiaxh.demo2025.service.controller;

import Utils.InsideColoredTankUtils;
import com.huaweisoft.xiaxh.demo2025.service.util.FfmpegUtil;
import com.librian.lib.APNGCollector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.librian.lib.APNGCollector.APNG_BLEND_OP_SOURCE;
import static com.librian.lib.APNGCollector.APNG_DISPOSE_OP_NONE;

/**
 * TODO 测试用，需要删除
 *
 * @author : hw
 * @date : 2023-11-02
 */
@RestController
@RequestMapping("/")
public class TestController {

    @GetMapping("test")
    public String test(String filePath1,String filePath2, String imgPath) throws IOException {
        File file1 = new File(filePath1);
        File file2 = new File(filePath2);
        String img1 = file1.getParent() + "/img1";
        String img2 = file2.getParent() + "/img2";
        String tank = file2.getParent() + "/tank";
        String tankPng = file2.getParent() + "/tankTest.png";
        new File(img1).mkdir();
        new File(img2).mkdir();
        new File(tank).mkdir();
        String[] imageFile1 = FfmpegUtil.videoToPng(filePath1, img1);
        String[] imageFile2 = FfmpegUtil.videoToPng(filePath2, img2);

        for (int i = 0; i < imageFile2.length; i++) {
            InsideColoredTankUtils.run(
                    new File(img1 + "/" +imageFile1[i]),
                    new File(img2 + "/" +imageFile2[i]),
                    tank + "/" + String.format("%05d", i) + ".png");
        }

        File[] list = new File(tank).listFiles((dir, fileName) -> fileName.endsWith(".png"));
        APNGCollector apng = new APNGCollector(ImageIO.read(new File(imgPath)), 0, (short) 1, (short) 32767, APNG_DISPOSE_OP_NONE, APNG_BLEND_OP_SOURCE);
        for (File file : list) {
            apng.addFrame(ImageIO.read(file), 0, 0, (short) 1, (short) 10, APNG_DISPOSE_OP_NONE, APNG_BLEND_OP_SOURCE);
        }
        FileOutputStream f = new FileOutputStream(tankPng);
        f.write(apng.build());
        f.close();
        return "Hello World!";
    }
}
