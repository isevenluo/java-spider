package org.pekxxoo.spider.util;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * Jsoup下载页面工具类
 * Created by chong on 2017/6/24.
 */
public class JsoupDownloadUtil {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36";

    public static Element getPageContent(String url) {
        if(StringUtils.isEmpty(url)) {
            return null;
        }
        Element content = null;
        try {
            Document document = Jsoup.connect(url).userAgent(USER_AGENT).timeout(10000).get();
            content = document.body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
