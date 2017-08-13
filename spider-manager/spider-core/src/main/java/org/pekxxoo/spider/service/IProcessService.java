package org.pekxxoo.spider.service;

import org.pekxxoo.spider.entity.Page;
import org.pekxxoo.spider.entity.PageType;

/**
 * Created by chong on 2017/6/25.
 */
public interface IProcessService {

    // 解析页面中所需要的数据
    public void process(Page page, PageType PageType);
}
