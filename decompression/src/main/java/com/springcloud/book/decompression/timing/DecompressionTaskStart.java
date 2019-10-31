package com.springcloud.book.decompression.timing;

import com.springcloud.book.decompression.config.UnzipEnvironmentConfig;
import com.springcloud.book.decompression.controller.DecompressionController;
import com.springcloud.book.decompression.domain.vo.CustomUnzipVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DecompressionTaskStart {

    private Logger logger = LoggerFactory.getLogger(DecompressionTaskStart.class);

    @Autowired
    private DecompressionController decompressionController;

    @Autowired
    private UnzipEnvironmentConfig unzipEnvironmentConfig;

    @Scheduled(cron = "30 5,25,45 * * * ?")
    public void DecompressionTiming() throws InterruptedException {
        CustomUnzipVO customUnzipVO = new CustomUnzipVO();
        customUnzipVO.setZipFilePath(unzipEnvironmentConfig.getZipFilePath());
        customUnzipVO.setUnzipToCataLog(unzipEnvironmentConfig.getUnzipToCataLog());
        Object msg = decompressionController.customUnzip(customUnzipVO);
        logger.info(msg.toString());
    }


}
