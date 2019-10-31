package com.springcloud.book.foreign.config;

import com.springcloud.book.foreign.service.remote.ForeignDataUserBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.stereotype.Component;

@Component
public class RemoteServerConfig {

    @Autowired
    private ForeignDataUserBase foreignDataUserBase;

    //发布服务
    @Bean(name = "/foreign/userBaseService")
    public HessianServiceExporter accountService() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(foreignDataUserBase);
        exporter.setServiceInterface(ForeignDataUserBase.class);
        return exporter;
    }
}
