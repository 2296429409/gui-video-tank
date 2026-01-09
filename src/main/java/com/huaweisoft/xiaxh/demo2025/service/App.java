package com.huaweisoft.xiaxh.demo2025.service;

import com.huaweisoft.xiaxh.demo2025.service.gui.VideoProcessorGUI;

import javax.swing.*;

public class App {

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
