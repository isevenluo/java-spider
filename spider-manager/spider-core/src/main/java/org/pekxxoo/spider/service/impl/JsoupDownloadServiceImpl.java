package org.pekxxoo.spider.service.impl;

import org.jsoup.nodes.Element;
import org.pekxxoo.spider.entity.Page;
import org.pekxxoo.spider.entity.PageType;
import org.pekxxoo.spider.service.IDownloadService;
import org.pekxxoo.spider.util.JsoupDownloadUtil;
import org.springframework.stereotype.Service;

/**
 * Created by chong on 2017/6/24.
 */
@Service
public class JsoupDownloadServiceImpl implements IDownloadService {
    @Override
    public Page download(String url, PageType type) {
        Page page = new Page();
        Element content = JsoupDownloadUtil.getPageContent(url);
        page.setContent(content);
        page.setUrl(url);
        page.setType(type);
        return page;
    }
}
