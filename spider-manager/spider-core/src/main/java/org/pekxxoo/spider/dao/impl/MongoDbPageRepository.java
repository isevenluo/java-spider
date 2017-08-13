package org.pekxxoo.spider.dao.impl;

import org.pekxxoo.spider.dao.PageRepository;
import org.pekxxoo.spider.entity.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import static org.springframework.data.mongodb.core.query.Query.*;

import static org.springframework.data.mongodb.core.query.Criteria.*;

/**
 * {@link PageRepository} implementation using the Spring Data {@link MongoOperations} API.
 * Created by chong on 2017/8/8.
 */
@Repository
public class MongoDbPageRepository implements PageRepository{

    private final MongoOperations operations;


    /**
     * Creates a new {@link MongoDbPageRepository} using the given {@link MongoOperations}.
     *
     * @param operations must not be {@literal null}.
     */
    @Autowired
    public MongoDbPageRepository(MongoOperations operations) {
        Assert.notNull(operations,"operation对象为空");
        this.operations = operations;
    }

    @Override
    public Page findOne(Long id) {
        Query query = query(where("id").is(id));
        return operations.findOne(query,Page.class);
    }

    @Override
    public Page save(Page page) {
        operations.save(page);
        return page;
    }

    @Override
    public Page findByTvId(String tvId) {
        Query query = query(where("tvId").is(tvId));
        return operations.findOne(query,Page.class);
    }
}
