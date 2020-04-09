package com.ccc.eirc.commons.service;

import com.ccc.eirc.commons.domain.EircConstant;
import com.ccc.eirc.commons.domain.ImageType;
import com.ccc.eirc.commons.exception.EircException;
import com.ccc.eirc.commons.properties.EircProperties;
import com.ccc.eirc.commons.properties.ValidateCodeProperties;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * <a>Title:ValidateCodeService</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * 验证码服务
 *
 * @Author ccc
 * @Date 2020/3/23 16:29
 * @Version 1.0.0
 */
@Service
public class ValidateCodeService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private EircProperties eircProperties;

    /**
     *  captcha 打码
     * @param request
     * @param response
     * @throws IOException
     */
    public void create(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String key = session.getId();
        ValidateCodeProperties code =eircProperties.getCode();
        setHeader(response, code.getType());

        Captcha captcha = createCaptcha(code);
        redisService.set(EircConstant.CODE_PREFIX+key, StringUtils.lowerCase(captcha.text()),code.getTime());
        captcha.out(response.getOutputStream());
    }

    /**
     * 比较
     * @param key
     * @param value
     */
    public void check(String key,String value) {
        Object codeInRedis=redisService.get(EircConstant.CODE_PREFIX+key);
        if (StringUtils.isBlank(value)){
            throw new EircException("请输入验证码！");
        }
        if (codeInRedis==null){
            throw new EircException("验证码已过期！");
        }
        if (!StringUtils.equalsIgnoreCase(value,String.valueOf(codeInRedis))){
            throw new EircException("验证码不正确！");
        }
    }

    /**
     * 打码
     * @param code
     * @return
     */
    private Captcha createCaptcha(ValidateCodeProperties code) {
        Captcha captcha=null;
        if (StringUtils.equalsIgnoreCase(code.getType(),ImageType.GIF)){
            captcha = new GifCaptcha(code.getWidth(),code.getHeight(),code.getLength());
        }else {
            captcha = new SpecCaptcha(code.getWidth(),code.getHeight(),code.getLength());
        }
        captcha.setCharType(code.getCharType());
        return captcha;
    }


    /**
     * 请求头
     * @param response
     * @param type
     */
    private void setHeader(HttpServletResponse response,String type){
        /**
         * gif 类型
         */
        if (StringUtils.equalsIgnoreCase(type, ImageType.GIF)){
            response.setContentType(MediaType.IMAGE_GIF_VALUE);
        }else{
            /**
             * png 类型
             */
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
        }

        response.setHeader(HttpHeaders.PRAGMA,"No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL,"No-cache");
        response.setDateHeader(HttpHeaders.EXPECT,0L);
    }

}
