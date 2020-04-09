package com.ccc.eirc.commons.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.lionsoul.ip2region.*;

import java.io.File;
import java.lang.reflect.Method;

/**
 * <a>Title:AddressUtil</a>
 * <a>Author：<a>
 * <a>Description：<a>
 * <p>
 * 获取 ip 地址
 *
 * @Author ccc
 * @Date 2020/3/23 18:00
 * @Version 1.0.0
 */
@Slf4j
public class AddressUtil {

    @SuppressWarnings("all")
    public static String getCityInfo(String ip) {
        DbSearcher searcher = null;
        try {
            String dbPath = AddressUtil.class.getResource("/ip2region/ip2region.db").getPath();
            File file = new File(dbPath);
            if (!file.exists()) {
                String tmpDir = System.getProperties().getProperty("java.io.tmpdir");
                dbPath = tmpDir + "ip.db";
                file = new File(dbPath);
                FileUtils.copyInputStreamToFile(AddressUtil.class.getClassLoader().getResourceAsStream("classpath:ip2region/ip2region.db"), file);
            }
            int algorithm = DbSearcher.BTREE_ALGORITHM;
            DbConfig config = new DbConfig();

            searcher = new DbSearcher(config, dbPath);
            Method method = null;
            switch (algorithm) {
                case DbSearcher.BTREE_ALGORITHM:
                    method = searcher.getClass().getMethod("btreeSearch", String.class);
                    break;
                case DbSearcher.BINARY_ALGORITHM:
                    method = searcher.getClass().getMethod("binarySearch", String.class);
                    break;
                case DbSearcher.MEMORY_ALGORITYM:
                    method = searcher.getClass().getMethod("memorySearch", String.class);
                    break;
            }
            DataBlock dataBlock = null;
            if (!Util.isIpAddress(ip)) {
                log.error("Error: Invalid ip address!");
            }
            dataBlock = (DataBlock) method.invoke(searcher, ip);
            return dataBlock.getRegion();
        } catch (Exception e) {
            log.error("获取IP地址失败，{}", e.getMessage());
        } finally {
            if (searcher != null) {
                try {
                    searcher.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
