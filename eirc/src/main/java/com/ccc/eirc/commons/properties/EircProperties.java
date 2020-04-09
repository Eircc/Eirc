package com.ccc.eirc.commons.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * <a>Title:EircProperties</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/22 20:57
 * @Version 1.0.0
 */
@Data
@SpringBootConfiguration
@ConfigurationProperties(prefix = "eirc")
@PropertySource(value = "classpath:eirc.properties")
public class EircProperties {
    private ShiroPeoperties shiro = new ShiroPeoperties();
    private boolean autoOpenBrowser = true;
    private SwaggerProperties swaggerProperties = new SwaggerProperties();
    //    最大插入数量
    private int maxBatchInsertNum = 1000;

    private ValidateCodeProperties code = new ValidateCodeProperties();

}
