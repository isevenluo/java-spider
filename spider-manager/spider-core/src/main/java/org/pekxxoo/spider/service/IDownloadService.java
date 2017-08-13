package org.pekxxoo.spider.service;

import org.pekxxoo.spider.entity.Page;
import org.pekxxoo.spider.entity.PageType;

/**
 * 页面下载接口
 * Created by chong on 2017/6/24.
 */
public interface IDownloadService {
    public Page download(String url, PageType type);
}
