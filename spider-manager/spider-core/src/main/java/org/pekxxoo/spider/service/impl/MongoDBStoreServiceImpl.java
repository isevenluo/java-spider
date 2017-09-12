package org.pekxxoo.spider.service.impl;

import org.pekxxoo.spider.dao.PageRepository;
import org.pekxxoo.spider.entity.Page;
import org.pekxxoo.spider.service.IStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chong on 2017/8/9.
 */
@Service
public class MongoDBStoreServiceImpl implements IStoreService {

    @Autowired
    private PageRepository pageRepository;

    @Override
    public void save(Page page) {
        pageRepository.save(page);
    }

    @Override
    public Page findByTvId(String tvId) {
        return pageRepository.findByTvId(tvId);
    }
}
