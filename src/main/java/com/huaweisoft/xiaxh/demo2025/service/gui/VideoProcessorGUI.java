package com.huaweisoft.xiaxh.demo2025.service.gui;

import Utils.InsideColoredTankUtils;
import com.huaweisoft.xiaxh.demo2025.service.util.FfmpegUtil;
import com.librian.lib.APNGCollector;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.librian.lib.APNGCollector.APNG_BLEND_OP_SOURCE;
import static com.librian.lib.APNGCollector.APNG_DISPOSE_OP_NONE;

/**
 * 视频处理GUI应用程序
 * 
 * @author hw
 * @date 2025
 */
public class VideoProcessorGUI extends JFrame {
    
    private JTextField filePath1Field;
    private JTextField filePath2Field;
    private JTextField imgPathField;
    private JTextField fpsField;
    private JTextField minWidthField;
    private JTextField qualityField;
    private JTextArea logArea;
    private JButton processButton;
    private JProgressBar progressBar;
    
    public VideoProcessorGUI() {
        initializeGUI();
    }
    
    private void initializeGUI() {
        setTitle("视频处理工具");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // 创建主面板
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // 创建文件选择面板
        JPanel filePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // 视频文件1
        gbc.gridx = 0;
        gbc.gridy = 0;
        filePanel.add(new JLabel("视频文件1:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        filePath1Field = new JTextField(30);
        filePanel.add(filePath1Field, gbc);
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JButton browse1Button = new JButton("浏览...");
        browse1Button.addActionListener(e -> selectVideoFile(filePath1Field));
        filePanel.add(browse1Button, gbc);
        
        // 视频文件2
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        filePanel.add(new JLabel("视频文件2:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        filePath2Field = new JTextField(30);
        filePanel.add(filePath2Field, gbc);
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JButton browse2Button = new JButton("浏览...");
        browse2Button.addActionListener(e -> selectVideoFile(filePath2Field));
        filePanel.add(browse2Button, gbc);
        
        // 图片文件
        gbc.gridx = 0;
        gbc.gridy = 2;
        filePanel.add(new JLabel("背景图片:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        imgPathField = new JTextField(30);
        filePanel.add(imgPathField, gbc);
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JButton browseImgButton = new JButton("浏览...");
        browseImgButton.addActionListener(e -> selectImageFile(imgPathField));
        filePanel.add(browseImgButton, gbc);
        
        // 配置参数
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        filePanel.add(new JLabel("帧率 (fps):"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        fpsField = new JTextField("10", 10);
        filePanel.add(fpsField, gbc);
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        filePanel.add(new JLabel("(默认: 10)"), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        filePanel.add(new JLabel("最小宽度:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        minWidthField = new JTextField("180", 10);
        filePanel.add(minWidthField, gbc);
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        filePanel.add(new JLabel("(默认: 180)"), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        filePanel.add(new JLabel("视频质量:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        qualityField = new JTextField("2", 10);
        filePanel.add(qualityField, gbc);
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        filePanel.add(new JLabel("(1-31, 默认: 2)"), gbc);
        
        // 处理按钮
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        processButton = new JButton("开始处理");
        processButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        processButton.addActionListener(new ProcessButtonListener());
        filePanel.add(processButton, gbc);
        
        // 进度条
        gbc.gridy = 7;
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setString("等待处理...");
        filePanel.add(progressBar, gbc);
        
        mainPanel.add(filePanel, BorderLayout.NORTH);
        
        // 日志区域
        logArea = new JTextArea(15, 50);
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("处理日志"));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void selectVideoFile(JTextField field) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("视频文件 (*.mp4, *.avi, *.mov, *.mkv)", "mp4", "avi", "mov", "mkv"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            field.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }
    
    private void selectImageFile(JTextField field) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("图片文件 (*.png, *.jpg, *.jpeg)", "png", "jpg", "jpeg"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            field.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }
    
    private void log(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }
    
    private void updateProgress(int value, String message) {
        SwingUtilities.invokeLater(() -> {
            progressBar.setValue(value);
            progressBar.setString(message);
        });
    }
    
    private class ProcessButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String filePath1 = filePath1Field.getText().trim();
            String filePath2 = filePath2Field.getText().trim();
            String imgPath = imgPathField.getText().trim();
            
            if (filePath1.isEmpty() || filePath2.isEmpty() || imgPath.isEmpty()) {
                JOptionPane.showMessageDialog(VideoProcessorGUI.this, 
                    "请选择所有必需的文件！", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!new File(filePath1).exists()) {
                JOptionPane.showMessageDialog(VideoProcessorGUI.this, 
                    "视频文件1不存在！", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!new File(filePath2).exists()) {
                JOptionPane.showMessageDialog(VideoProcessorGUI.this, 
                    "视频文件2不存在！", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!new File(imgPath).exists()) {
                JOptionPane.showMessageDialog(VideoProcessorGUI.this, 
                    "背景图片不存在！", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            processButton.setEnabled(false);
            logArea.setText("");
            updateProgress(0, "开始处理...");
            
            // 解析配置参数
            int fps = 10;
            int minWidth = 180;
            int quality = 2;
            
            try {
                String fpsText = fpsField.getText().trim();
                if (!fpsText.isEmpty()) {
                    fps = Integer.parseInt(fpsText);
                    if (fps <= 0) {
                        throw new NumberFormatException("帧率必须大于0");
                    }
                }
            } catch (NumberFormatException ex1) {
                JOptionPane.showMessageDialog(VideoProcessorGUI.this, 
                    "帧率格式错误，将使用默认值10", "警告", JOptionPane.WARNING_MESSAGE);
                fps = 10;
            }
            
            try {
                String minWidthText = minWidthField.getText().trim();
                if (!minWidthText.isEmpty()) {
                    minWidth = Integer.parseInt(minWidthText);
                    if (minWidth <= 0) {
                        throw new NumberFormatException("最小宽度必须大于0");
                    }
                }
            } catch (NumberFormatException ex2) {
                JOptionPane.showMessageDialog(VideoProcessorGUI.this, 
                    "最小宽度格式错误，将使用默认值180", "警告", JOptionPane.WARNING_MESSAGE);
                minWidth = 180;
            }
            
            try {
                String qualityText = qualityField.getText().trim();
                if (!qualityText.isEmpty()) {
                    quality = Integer.parseInt(qualityText);
                    if (quality < 1 || quality > 31) {
                        throw new NumberFormatException("视频质量必须在1-31之间");
                    }
                }
            } catch (NumberFormatException ex3) {
                JOptionPane.showMessageDialog(VideoProcessorGUI.this, 
                    "视频质量格式错误，将使用默认值2", "警告", JOptionPane.WARNING_MESSAGE);
                quality = 2;
            }
            
            final int finalFps = fps;
            final int finalMinWidth = minWidth;
            final int finalQuality = quality;
            
            // 在后台线程中执行处理
            new Thread(() -> {
                try {
                    processVideos(filePath1, filePath2, imgPath, finalFps, finalMinWidth, finalQuality);
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(VideoProcessorGUI.this, 
                            "处理完成！", "成功", JOptionPane.INFORMATION_MESSAGE);
                        processButton.setEnabled(true);
                        updateProgress(100, "处理完成！");
                    });
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(VideoProcessorGUI.this, 
                            "处理失败: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                        processButton.setEnabled(true);
                        updateProgress(0, "处理失败");
                    });
                    log("错误: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }).start();
        }
    }
    
    private void processVideos(String filePath1, String filePath2, String imgPath, 
                               int fps, int minWidth, int quality) throws IOException {
        log("开始处理视频...");
        log("视频文件1: " + filePath1);
        log("视频文件2: " + filePath2);
        log("背景图片: " + imgPath);
        log("配置参数 - 帧率: " + fps + ", 最小宽度: " + minWidth + ", 视频质量: " + quality);

        File img = new File(imgPath);
        File file1 = new File(filePath1);
        File file2 = new File(filePath2);
        String img1 = file1.getParent() + "/img1";
        String img2 = file2.getParent() + "/img2";
        String tank = file2.getParent() + "/tank";
        String tankPng = file2.getParent() + "/tankTest.png.gif";
        
        log("创建临时目录...");
        new File(img1).mkdir();
        new File(img2).mkdir();
        new File(tank).mkdir();
        
        updateProgress(10, "正在将视频1转换为图片...");
        log("正在将视频1转换为图片...");
        String[] imageFile1 = FfmpegUtil.videoToPng(filePath1, img1, fps, minWidth, quality);
        log("视频1转换完成，共 " + imageFile1.length + " 帧");
        
        updateProgress(30, "正在将视频2转换为图片...");
        log("正在将视频2转换为图片...");
        String[] imageFile2 = FfmpegUtil.videoToPng(filePath2, img2, fps, minWidth, quality);
        log("视频2转换完成，共 " + imageFile2.length + " 帧");
        
        updateProgress(50, "正在处理图片帧...");
        log("正在处理图片帧...");
        int totalFrames = imageFile2.length;
        int imageFile1Length = imageFile1.length;
        if (imageFile1Length == 0) {
            throw new IOException("视频1转换后没有生成图片文件");
        }
        log("视频1共 " + imageFile1Length + " 帧，视频2共 " + totalFrames + " 帧");
        if (imageFile1Length < totalFrames) {
            log("注意：视频1帧数少于视频2，将循环使用视频1的帧");
        }
        for (int i = 0; i < totalFrames; i++) {
            // 使用模运算循环使用 imageFile1 的文件
            int image1Index = i % imageFile1Length;
            InsideColoredTankUtils.run(
                    new File(img1 + "/" + imageFile1[image1Index]),
                    new File(img2 + "/" + imageFile2[i]),
                    tank + "/" + String.format("%05d", i) + ".png");
            
            int progress = 50 + (int) ((i + 1) * 30.0 / totalFrames);
            updateProgress(progress, String.format("处理图片帧 %d/%d", i + 1, totalFrames));
            
            if ((i + 1) % 10 == 0) {
                log("已处理 " + (i + 1) + "/" + totalFrames + " 帧");
            }
        }

        String tankImg = img.getParent() + "/"+img.getName()+"_tank.png";
        InsideColoredTankUtils.run(
                img,
                new File(img2 + "/" + imageFile2[0]),
                tankImg);

        
        updateProgress(80, "正在生成APNG...");
        log("正在生成APNG...");
        File[] list = new File(tank).listFiles((dir, fileName) -> fileName.endsWith(".png"));
        if (list == null || list.length == 0) {
            throw new IOException("未找到处理后的图片文件");
        }
        
        APNGCollector apng = new APNGCollector(
                ImageIO.read(new File(tankImg)),
                0, (short) 1, (short) 32767, 
                APNG_DISPOSE_OP_NONE, 
                APNG_BLEND_OP_SOURCE);
        
        for (File file : list) {
            apng.addFrame(
                    ImageIO.read(file), 
                    0, 0, (short) 1, (short) fps,
                    APNG_DISPOSE_OP_NONE, 
                    APNG_BLEND_OP_SOURCE);
        }
        
        updateProgress(90, "正在保存APNG文件...");
        log("正在保存APNG文件到: " + tankPng);
        FileOutputStream f = new FileOutputStream(tankPng);
        f.write(apng.build());
        f.close();
        
        updateProgress(95, "正在清理临时文件...");
        log("正在清理临时文件...");
        deleteDirectory(new File(img1));
        deleteDirectory(new File(img2));
        deleteDirectory(new File(tank));
        new File(tankImg).delete();
        log("临时文件清理完成");
        
        log("处理完成！输出文件: " + tankPng);
        updateProgress(100, "处理完成！");
    }
    
    /**
     * 递归删除目录及其所有内容
     */
    private void deleteDirectory(File directory) {
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
            directory.delete();
        }
    }
    
    public static void main(String[] args) {
        // 设置系统外观
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new VideoProcessorGUI().setVisible(true);
        });
    }
}

