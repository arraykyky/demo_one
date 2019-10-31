package com.springcloud.book.foreign.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.domain.DocumentDatabase;
import com.springcloud.book.foreign.dao.DocumentDatabaseDao;
import com.springcloud.book.foreign.domain.vo.DocumentStoreVO;
import com.springcloud.book.foreign.service.DocumentDatabaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springcloud.book.foreign.util.Tools;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author grl
 * @since 2019-08-19
 */
@Service
public class DocumentDatabaseServiceImpl extends ServiceImpl<DocumentDatabaseDao, DocumentDatabase> implements DocumentDatabaseService {

    @Override
    public void getCurrentUserMyDocumentStorePage(Page page, DocumentStoreVO documentStoreVO) throws Exception{
        List<DocumentDatabase> list = baseMapper.selectCurrentUserMyDocumentStorePage(page, documentStoreVO);
        page.setRecords(list);
    }

    @Override
    @Transactional
    public void createCurrentUserSetUpDocumentDatabase(DocumentStoreVO documentStoreVO) throws Exception{
        baseMapper.insertCurrentUserSetUpDocumentDatabase(documentStoreVO);
        baseMapper.createCurrentUserSetUpDocumentDatabase(documentStoreVO);
    }

    @Override
    @Transactional
    public void renameMyDocumentDatabaseById(DocumentStoreVO documentStoreVO) throws Exception{
        baseMapper.updateById(documentStoreVO);
    }

    @Override
    @Transactional
    public void deleteCurrentDocumentDatabaseById(DocumentStoreVO documentStoreVO) throws Exception{
        DocumentDatabase documentDatabase = baseMapper.selectById(documentStoreVO.getDocumentDatabaseId());
        documentStoreVO.setDocumentDatabaseRealName(documentDatabase.getDocumentDatabaseRealName());
        baseMapper.deleteById(documentStoreVO.getDocumentDatabaseId());
        baseMapper.dropCurrentDocumentDatabase(documentStoreVO);
    }

    @Override
    public void getCurrentDocumentPage(Page page, DocumentStoreVO documentStoreVO) throws Exception {
        DocumentDatabase documentDatabase = baseMapper.selectById(documentStoreVO.getDocumentDatabaseId());
        List<PageData> documentList = baseMapper.selectCurrentDocumentPage(page,documentDatabase);
        page.setRecords(documentList);
    }

    @Override
    @Transactional
    public void deleteDocumentCollectedBySelectedIds(DocumentStoreVO documentStoreVO) throws Exception{
        if (!documentStoreVO.getDocumentDatabaseIdList().isEmpty()){
            DocumentDatabase documentDatabase = baseMapper.selectById(documentStoreVO.getDocumentDatabaseId());
            documentStoreVO.setDocumentDatabaseRealName(documentDatabase.getDocumentDatabaseRealName());
            baseMapper.deleteDocumentCollectedByIds(documentStoreVO);
        }else {
            throw new RuntimeException("documentDatabaseIdsStr参数不能为空!");
        }
    }

    @Override
    public void addDocumentCollection(DocumentStoreVO documentStoreVO) {
        DocumentDatabase documentDatabase = baseMapper.selectById(documentStoreVO.getDocumentDatabaseId());
        documentStoreVO.setDocumentDatabaseRealName(documentDatabase.getDocumentDatabaseRealName());
        documentStoreVO.setDocumentDatabaseId(Tools.getUUID());
        baseMapper.insertDocumentCollection(documentStoreVO);
    }
}
