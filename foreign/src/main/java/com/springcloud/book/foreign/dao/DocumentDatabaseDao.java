package com.springcloud.book.foreign.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.domain.DocumentDatabase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springcloud.book.foreign.domain.vo.DocumentStoreVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author grl
 * @since 2019-08-19
 */
public interface DocumentDatabaseDao extends BaseMapper<DocumentDatabase> {

    List<DocumentDatabase> selectCurrentUserMyDocumentStorePage(Page page, @Param("pd") DocumentStoreVO documentStoreVO);

    void insertCurrentUserSetUpDocumentDatabase(DocumentStoreVO documentStoreVO);

    void createCurrentUserSetUpDocumentDatabase(DocumentStoreVO documentStoreVO);

    void dropCurrentDocumentDatabase(DocumentStoreVO documentStoreVO);

    List<PageData> selectCurrentDocumentPage(Page page, @Param("pd") DocumentDatabase documentDatabase);

    void deleteDocumentCollectedByIds(DocumentStoreVO documentStoreVO);

    void insertDocumentCollection(DocumentStoreVO documentStoreVO);
}
