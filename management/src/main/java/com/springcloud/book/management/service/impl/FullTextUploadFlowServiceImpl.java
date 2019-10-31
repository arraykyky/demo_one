package com.springcloud.book.management.service.impl;

import com.springcloud.book.management.config.Constants;
import com.springcloud.book.management.config.OverallConfig;
import com.springcloud.book.management.config.redis.RedisServer;
import com.springcloud.book.management.domain.FullTextUploadFlow;
import com.springcloud.book.management.dao.FullTextUploadFlowDao;
import com.springcloud.book.management.domain.NoTextRecord;
import com.springcloud.book.management.domain.vo.FullTextVO;
import com.springcloud.book.management.service.FullTextUploadFlowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springcloud.book.management.service.NoTextRecordService;
import com.springcloud.book.management.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author grl
 * @since 2019-09-28
 */
@Service
public class FullTextUploadFlowServiceImpl extends ServiceImpl<FullTextUploadFlowDao, FullTextUploadFlow> implements FullTextUploadFlowService {

    @Autowired
    private RedisServer redisServer;

    @Autowired
    private NoTextRecordService noTextRecordService;

    @Override
    @Transactional
    public void uploadFullText(FullTextVO fullTextVO) throws IOException {
        //创建上传文件对象
        File file = new File(OverallConfig.FILE_PATH, Tools.uploadFileRename(fullTextVO.getAbstractId(),fullTextVO.getFile().getOriginalFilename()));
        String userEllaId = (String) redisServer.get(Constants.getUserModuleCacheKeyId(fullTextVO.getToken()));
        //封装数据对象
        FullTextUploadFlow daoFullTextUploadFlow = new FullTextUploadFlow();
        daoFullTextUploadFlow.setAbstractId(fullTextVO.getAbstractId());
        daoFullTextUploadFlow.setId(userEllaId);
        daoFullTextUploadFlow.setUploadTime(String.valueOf(new Date().getTime()));
        daoFullTextUploadFlow.setUploadFileUrl(file.getAbsolutePath());
        daoFullTextUploadFlow.setFilePathUrl(OverallConfig.TEXT_FULL + File.separator + file.getName());
        FullTextUploadFlow fullTextRecord = this.getFullTextUploadFlowByAbstractId(fullTextVO.getAbstractId());
        //记录不存在时新增
        if (fullTextRecord == null){
            daoFullTextUploadFlow.setUploadFlowId(Tools.getUUID());
            this.addFullText(daoFullTextUploadFlow);
            //更新无全文记录
            NoTextRecord noTextRecord = noTextRecordService.getNotextRecordByAbstractId(fullTextVO.getAbstractId());
            if (noTextRecord != null){
                NoTextRecord daoNoTextRecord = new NoTextRecord();
                daoNoTextRecord.setNoTextId(noTextRecord.getNoTextId());
                daoNoTextRecord.setIsSeekOut(1);
                noTextRecordService.updateNoTextRecordById(daoNoTextRecord);
            }
        }
        //有记录时更新
        else {
            if (file.exists()){
                file.delete();
            }
            daoFullTextUploadFlow.setUploadFlowId(fullTextRecord.getUploadFlowId());
            baseMapper.updateById(daoFullTextUploadFlow);
        }
        //上传
        fullTextVO.getFile().transferTo(file);
    }

    @Override
    public void addFullText(FullTextUploadFlow fullTextUploadFlow) {
        baseMapper.insertFullText(fullTextUploadFlow);
    }

    @Override
    public FullTextUploadFlow getFullTextUploadFlowByAbstractId(String abstractId) {
        FullTextUploadFlow fullTextUploadFlow = new FullTextUploadFlow();
        fullTextUploadFlow.setAbstractId(abstractId);
        return baseMapper.selectFullTextUploadFlowByAbstractId(fullTextUploadFlow);
    }
}
