package com.springcloud.book.decompression.controller;

import com.springcloud.book.decompression.config.FinalFileConfig;
import com.springcloud.book.decompression.config.UnzipEnvironmentConfig;
import com.springcloud.book.decompression.domain.Unzip;
import com.springcloud.book.decompression.domain.vo.CustomUnzipVO;
import com.springcloud.book.decompression.service.UnzipService;
import com.springcloud.book.decompression.util.DateUtil;
import com.springcloud.book.decompression.util.ZIPUtill;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/unzip")
public class DecompressionController {

    private Logger logger = LoggerFactory.getLogger(DecompressionController.class);

    @Autowired
    private UnzipEnvironmentConfig unzipEnvironmentConfig;

    @Autowired
    private UnzipService unzipService;

    /**
     *
     * @param customUnzipVO 解压VO 包含必要参数 zipFilePath -- 压缩文件路径
     *                                            unzipToCataLog -- 压缩文件解压到路径文件夹子目录(如:2018/2019)
     * @return
     * @throws Exception
     */
    @GetMapping("/custom")
    public Object customUnzip(CustomUnzipVO customUnzipVO) {
        String showStr = null;
        if (StringUtils.isBlank(customUnzipVO.getZipFilePath())){
            return "ZIP file path is Non-existent ";
        }
        Set<String> catalogSet = new HashSet<>();
        //获取解压记录
        Unzip unzipSelect = new Unzip();
        Unzip unzip = new Unzip();
        unzipSelect.setUnzipEnvironment(unzipEnvironmentConfig.getEnvironment());
        try {
            Unzip uz = unzipService.selectByType(unzipSelect);
            if (uz != null){
                unzip.setUnzipId(uz.getUnzipId());
                unzip.setUnzipTimePosition(uz.getUnzipTimePosition());
                int isSave = uz.getUnzipCatalogSetIsSave();
                if (isSave > 0){
                    unzip.setUnzipCatalogSetIsSave(1);
                    //需要记录时，再处理记录集合
                    String oldSetStr = uz.getUnzipCatalogSet();
                    if (StringUtils.isNotBlank(oldSetStr)){
                        String[] oldSetStrArray = oldSetStr.split(FinalFileConfig.ENVIRONMENT_SPLIT_CHART);
                        Set<String> oldSetStrSet = new HashSet<>(Arrays.asList(oldSetStrArray));
                        catalogSet.addAll(oldSetStrSet);
                    }
                }else {
                    unzip.setUnzipCatalogSetIsSave(-1);
                }
            }else {
                unzip.setUnzipCatalogSetIsSave(1);
                unzip.setUnzipEnvironment(unzipEnvironmentConfig.getEnvironment());
            }
            //执行解压任务
            Map<String, Object> map = ZIPUtill.getUnzip(customUnzipVO.getZipFilePath(), customUnzipVO.getUnzipToPath(), unzip.getUnzipTimePosition(), catalogSet);
            if (map!=null && !map.isEmpty()){
                String msg = map.get("msg").toString();
                if (msg.equals("SUCCESS")){
                    Long time = (Long) map.get("time");
                    Set<String> jsonSet = (Set<String>) map.get("set");
                    String dateTimeStr = DateUtil.format(new Date(time),"yyyy-MM-dd HH:ss:mm");
                    unzip.setUnzipTimePosition(time);
                    unzip.setUnzipDateTimePosition(dateTimeStr);
                    if (unzip.getUnzipCatalogSetIsSave() > 0 && !jsonSet.isEmpty()){
                        unzip.setUnzipCatalogSet(StringUtils.join(jsonSet, FinalFileConfig.ENVIRONMENT_SPLIT_CHART));
                    }
                    unzipService.addUnzipRecords(unzip);
                }
                showStr = msg;
            }else {
                return "Not ZIP Files";
            }
        } catch (Exception e) {
            logger.error(e.getMessage()+":"+unzip.toString(),e);
        }
        return showStr;
    }
}
