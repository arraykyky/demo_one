package com.springcloud.book.foreign.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.domain.DocumentDatabase;
import com.baomidou.mybatisplus.extension.service.IService;
import com.springcloud.book.foreign.domain.vo.DocumentStoreVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grl
 * @since 2019-08-19
 */
public interface DocumentDatabaseService extends IService<DocumentDatabase> {
    //通过当前用户的id获取文献库列表
    void getCurrentUserMyDocumentStorePage(Page page, DocumentStoreVO documentStoreVO) throws Exception;
    //添加用户文献库船舰信息，并创建对应的数据表
    void createCurrentUserSetUpDocumentDatabase(DocumentStoreVO documentStoreVO) throws Exception;
    //通过文献库id修改文献库的展示名字
    void renameMyDocumentDatabaseById(DocumentStoreVO documentStoreVO)throws Exception;
    //通过文献库id删除该文献库的所有收藏
    void deleteCurrentDocumentDatabaseById(DocumentStoreVO documentStoreVO) throws Exception;
    //通过文献库id获取收藏库名字，进而获取数据
    void getCurrentDocumentPage(Page page, DocumentStoreVO documentStoreVO) throws Exception;
    //通过文献库id获取收藏库名字，进而删除数据
    void deleteDocumentCollectedBySelectedIds(DocumentStoreVO documentStoreVO)throws Exception;
    //根据文献库id获取对应的文献库名，进而保存收藏数据
    void addDocumentCollection(DocumentStoreVO documentStoreVO);
}
