package com.ccc.eirc.commons.converter;

import com.ccc.eirc.commons.utils.DateUtil;
import com.wuwenze.poi.convert.WriteConverter;
import com.wuwenze.poi.exception.ExcelKitWriteConverterException;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

/**
 * <a>Title:converter</a>
 * <a>Author：<a>
 * <a>Description：<a>
 * <p>
 *
 * Execl导出时间类型字段格式化
 *
 * @Author ccc
 * @Date 2020/3/23 15:21
 * @Version 1.0.0
 */
@Slf4j
public class TimeConverter implements WriteConverter {

    @Override
    public String convert(Object value) {
        if (value == null)
            return "";
        else {
            try {
                return DateUtil.formatCSTTime(value.toString(), DateUtil.FULL_TIME_SPLIT_PATTERN);
            } catch (ParseException e) {
                String message = "时间转换异常!";
                log.error(message, e);
                throw new ExcelKitWriteConverterException(message);
            }
        }
    }
}
