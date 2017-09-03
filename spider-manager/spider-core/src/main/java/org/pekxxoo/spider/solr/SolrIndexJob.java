package org.pekxxoo.spider.solr;

/**
 * Created by chong on 2017/8/27.
 */


/**
 * 定时增量solr索引
 */

import org.apache.commons.lang3.StringUtils;
import org.pekxxoo.spider.entity.Page;
import org.pekxxoo.spider.entity.SolrConstant;
import org.pekxxoo.spider.service.IStoreService;
import org.pekxxoo.spider.util.SolrUtil;
import org.pekxxoo.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SolrIndexJob {

    private static Logger logger = LoggerFactory.getLogger(SolrIndexJob.class);

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IStoreService storeService;

    @Scheduled(cron = "0 36 * * * ?")
    public void buildIndex2Solr() {
        String tvId = "";
        try {
            logger.info("定时增加索引开始执行>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            Object object = redisUtil.rpop(SolrConstant.SOLR_TV_INDEX.toString());
            if(object == null) {
                tvId = "";
            }
            tvId = object.toString();
            while (true) {
                if(StringUtils.isNotEmpty(tvId)) {
                    Page page = storeService.findByTvId(tvId);
                    if(checkData(page)) {
                        SolrUtil.addIndex(page);
                    }
                    object = redisUtil.rpop(SolrConstant.SOLR_TV_INDEX.toString());
                    if(object == null) {
                        tvId = "";
                    }
                    tvId = object.toString();
                } else {
                    logger.info("目前没有数据添加到Solr索引中了>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    break;
                }
            }
        } catch (Exception e) {
            redisUtil.lpush(SolrConstant.SOLR_TV_INDEX.toString(),tvId);
            logger.error("往solr中添加索引数据出错了>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>",e.getMessage(),e);
        }

    }

    private boolean checkData(Page page) {
        if(page == null) {
            return false;
        }
        if(page.getTvname().equals("0") || page.getUrl().equals("0") || page.getAllnumber().equals("0") || page.getTvId().equals("0") || page.getReleaseTime().equals("0")) {
            return false;
        }
        return true;
    }

}
