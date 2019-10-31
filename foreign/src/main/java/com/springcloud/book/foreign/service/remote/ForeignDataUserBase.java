package com.springcloud.book.foreign.service.remote;

import com.springcloud.book.foreign.config.PageData;

public interface ForeignDataUserBase {
    void saveForeignDataUser(PageData pageData);
    void updataForeignDataUser(PageData pageData);
    PageData getForeignDataUser(PageData pageData);
}
