package org.pekxxoo.spider.service;

import org.pekxxoo.spider.entity.Page;

/**
 * Created by chong on 2017/8/9.
 */
public interface IStoreService {

    /**
     * 存储页面信息
     * @param page
     */
    public void save(Page page);

    /**
     * 通过电视剧Id获取一条
     * @param tvId
     * @return
     */
    Page findByTvId(String tvId);
}
