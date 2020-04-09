package com.ccc.eirc.commons.properties;

import com.ccc.eirc.commons.domain.ImageType;
import lombok.Data;

/**
 * <a>Title:ValidateCodeProperties</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * 验证码设置
 *
 * @Author ccc
 * @Date 2020/3/23 16:33
 * @Version 1.0.0
 */
@Data
public class ValidateCodeProperties {

    /**
     * 验证码有效时间，单位秒
     */
    private Long time = 120L;
    /**
     * 验证码类型，可选值 png和 gif
     */
    private String type = ImageType.PNG;
    /**
     * 图片宽度，px
     */
    private Integer width = 130;
    /**
     * 图片高度，px
     */
    private Integer height = 48;
    /**
     * 验证码位数
     */
    private Integer length = 4;
    /**
     * 验证码值的类型
     * 1. 数字加字母
     * 2. 纯数字
     * 3. 纯字母
     */
    private Integer charType = 2;
}