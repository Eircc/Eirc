package com.ccc.eirc.commons.configuration;

import com.ccc.eirc.commons.utils.DateUtil;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

/**
 * <a>Title:P6spySqlFormatConfigure</a>
 * <a>Author：<a>
 * <a>Description：<a>
 *
 * @Author ccc
 * @Date 2020/3/23 20:12
 * @Version 1.0.0
 */
public class P6spySqlFormatConfigure implements MessageFormattingStrategy {
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        return StringUtils.isNotBlank(sql) ? DateUtil.formatFullTime(LocalDateTime.now(), DateUtil.FULL_TIME_SPLIT_PATTERN)
                + " | 耗时 " + elapsed + " ms | SQL 语句：" + StringUtils.LF + sql.replaceAll("[\\s]+", StringUtils.SPACE) + ";" : StringUtils.EMPTY;
    }
}
