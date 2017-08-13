package org.pekxxoo.spider.service;

/**
 * Created by chong on 2017/5/26.
 */
public interface IRepositoryService {
    /**
     * 存储url仓库接口
     * Created by taurus on 16-12-18.
     */

        String poll();
        void addHighLevel(String url);
        void addLowLevel(String url);
        void addStartUrl();
    }
