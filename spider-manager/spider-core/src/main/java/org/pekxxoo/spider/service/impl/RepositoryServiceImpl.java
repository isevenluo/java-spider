package org.pekxxoo.spider.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.pekxxoo.spider.entity.UrlType;
import org.pekxxoo.spider.service.IRepositoryService;
import org.pekxxoo.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chong on 2017/5/26.
 */
@Service
public class RepositoryServiceImpl implements IRepositoryService{
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public String poll() {
        String url = "";
        Object object = redisUtil.rpop(UrlType.HIGHLEVEL.name());

        if(object == null) {
            object = redisUtil.rpop(UrlType.LOWLEVEL.name());
        }
        if(object != null) {
            url = object.toString();
        }
        return url;
    }

    @Override
    public void addHighLevel(String url) {
        redisUtil.lpush(UrlType.HIGHLEVEL.name(),url);
    }

    @Override
    public void addLowLevel(String url) {
        redisUtil.lpush(UrlType.LOWLEVEL.name(),url);
    }

    @Override
    public void addStartUrl() {
        List<Object> startUrl = redisUtil.lrange(UrlType.STARTURL.name(),0,-1);
        if(startUrl !=null && !startUrl.isEmpty()) {
            for(Object url : startUrl) {
                redisUtil.lpush(UrlType.HIGHLEVEL.name(),url.toString());
            }
        }
    }
}
