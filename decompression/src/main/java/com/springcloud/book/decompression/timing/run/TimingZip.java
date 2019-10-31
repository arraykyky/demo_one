package com.springcloud.book.decompression.timing.run;

import java.util.TimerTask;

import com.springcloud.book.decompression.util.ZIPUtill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimingZip extends TimerTask {
	
	private final Logger logger = LoggerFactory.getLogger(TimingZip.class);
	
	private String zipFilePath;
	private String dstPath;
	private String sourceType;
	
	public TimingZip(String zipFilePath,String dstPath,String sourceType){
		this.zipFilePath = zipFilePath;
		this.dstPath = dstPath;
		this.sourceType = sourceType;
	}
	
	@Override
	public void run() {
		try {
			if (zipFilePath != null && !"".equals(zipFilePath) && dstPath != null && !"".equals(dstPath)) {
				if (zipFilePath.equals(dstPath)) {
					dstPath = dstPath + "\\unzip";
				}
				logger.info("解压文件最终存放的路径为：" + dstPath);
				if (sourceType.equals("ENG")) {
					String contENG = "D:\\opt\\cont_ENG.txt";
					//ZIPUtill.getUnzip(zipFilePath, dstPath, sourceType, contENG);
					logger.info("压缩文件解压完成");
				} else {
					logger.error("文件解压类型输入错误");
				}

			} else {
				logger.error("压缩文件路径/解压文件路径为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
