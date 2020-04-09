package com.ccc.eirc.commons.authentication;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.ccc.eirc.commons.properties.EircProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * <a>Title:ShiroConfig</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * Shiro 配置类
 *
 * @Author ccc
 * @Date 2020/3/22 20:52
 * @Version 1.0.0
 */
@Configuration
public class ShiroConfig {

    @Autowired
    private EircProperties eircProperties;
//    服务器地址
    @Value("${spring.redis.host}")
    private String host;

//    端口号
    @Value("${spring.redis.port}")
    private int port;

//    默认密码
    @Value("${spring.redis.password:}")
    private String password;

//    超时时间
    @Value("${spring.redis.timeout}")
    private int timeout;

//    默认索引
    @Value("${spring.redis.database:0}")
    private int database;

    /**
     * Shiro 中配置 redis
     *
     * 连接
     * @return
     */
    private RedisManager redisManager(){
        RedisManager redisManager = new RedisManager();
//        拼接端口号
        redisManager.setHost(host+":"+port);
        if (StringUtils.isNotBlank(password))
            redisManager.setPassword(password);
        redisManager.setTimeout(timeout);
        redisManager.setDatabase(database);
        return redisManager;
    }

    /**
     * Shiro 中配置 redis
     *
     * 缓存
     * @return
     */
    private RedisCacheManager cacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        设置 securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
//        登录的 url
        shiroFilterFactoryBean.setLoginUrl(eircProperties.getShiro().getLoginUrl());
//        登录成功后跳转的 url
        shiroFilterFactoryBean.setSuccessUrl(eircProperties.getShiro().getSuccessUrl());
//        未授权 url
        shiroFilterFactoryBean.setUnauthorizedUrl(eircProperties.getShiro().getUnauthorizedUrl());
//        拿到一个有序HashMap
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//        设置免认证 url 每个url以 ， 号分隔
        String[] anonUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(eircProperties.getShiro().getAnonUrl(), ",");
//        将url遍历
        for (String url : anonUrls) {
//            将免认证 url 放到授权路径中
            filterChainDefinitionMap.put(url,"anon");
        }
//        配置退出过滤器，其中具体的退出代码 Shiro已经替我们实现了
        filterChainDefinitionMap.put(eircProperties.getShiro().getLogoutUrl(),"logout");
//        除上以外所有 url都必须认证通过才可以访问，未通过认证自动访问 LoginUrl
        filterChainDefinitionMap.put("/**", "user");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 注入自定义 ShiroRealm
     * @param shiroRealm
     * @return
     */
    @Bean
    public SecurityManager securityManager(ShiroRealm shiroRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 配置 SecurityManager，并注入 shiroRealm
        securityManager.setRealm(shiroRealm);
        // 配置 shiro session管理器
        securityManager.setSessionManager(sessionManager());
        // 配置 缓存管理类 cacheManager
        securityManager.setCacheManager(cacheManager());
        // 配置 rememberMeCookie
        securityManager.setRememberMeManager(rememberMeManager());

        return securityManager;
    }


    /**
     * rememberMe cookie 记住我之后重开浏览器后无需重新登录
     * @return SimpleCookie
     */
    private SimpleCookie rememberMeCookie() {
//        设置 cookie 名称，对应 login.html 页面的 <input type="checkbox" name="rememberMe"/>
        SimpleCookie cookie = new SimpleCookie("rememberMe");
//        设置 cookie 的过期时间，单位为秒，这里为一天
        cookie.setMaxAge(eircProperties.getShiro().getCookieTimeout());
        return cookie;
    }

    private CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
//        rememberMe cookie 加密的密钥
        String encryptKey = "EIRC_SHIRO_KEY";
        byte[] encryptKeyBytes = encryptKey.getBytes(StandardCharsets.UTF_8);
        String rememberKey = Base64Utils.encodeToString(Arrays.copyOf(encryptKeyBytes, 16));
        cookieRememberMeManager.setCipherKey(Base64.decode(rememberKey));
        return cookieRememberMeManager;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 用于开启 Thymeleaf 中的 shiro 标签的使用
     *
     * @return ShiroDialect shiro 方言对象
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * session 管理对象
     *
     * @return DefaultWebSessionManager
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        Collection<SessionListener> listeners = new ArrayList<>();
        listeners.add(new ShiroSessionListener());
        // 设置 session超时时间
        sessionManager.setGlobalSessionTimeout(eircProperties.getShiro().getSessionTimeout() * 1000L);
        sessionManager.setSessionListeners(listeners);
        sessionManager.setSessionDAO(redisSessionDAO());
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }
}
