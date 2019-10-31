package com.springcloud.book.management.service;

import com.springcloud.book.management.domain.FullTextUploadFlow;
import com.baomidou.mybatisplus.extension.service.IService;
import com.springcloud.book.management.domain.vo.FullTextVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grl
 * @since 2019-09-28
 */
public interface FullTextUploadFlowService extends IService<FullTextUploadFlow> {
    void uploadFullText(FullTextVO fullTextVO) throws IOException;
    void addFullText(FullTextUploadFlow daoFullTextUploadFlow);
    FullTextUploadFlow getFullTextUploadFlowByAbstractId(String abstractId);
}
