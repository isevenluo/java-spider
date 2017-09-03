package org.pekxxoo.spider.schedule;

import org.apache.commons.lang3.StringUtils;
import org.pekxxoo.spider.entity.Page;
import org.pekxxoo.spider.entity.PageType;
import org.pekxxoo.spider.entity.SolrConstant;
import org.pekxxoo.spider.service.IDownloadService;
import org.pekxxoo.spider.service.IProcessService;
import org.pekxxoo.spider.service.IRepositoryService;
import org.pekxxoo.spider.service.IStoreService;
import org.pekxxoo.utils.AsyncTaskExecutorUtil;
import org.pekxxoo.utils.RedisConfig;
import org.pekxxoo.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chong on 2017/5/26.
 * 定时任务启动爬虫
 */
@Component
public class StartSpiderTask {
    @Autowired
    private IRepositoryService repositoryService;

    @Autowired
    private IDownloadService downloadService;

    @Autowired
    private IProcessService processService;

    @Autowired
    private IStoreService storeService;

    @Autowired
    private RedisUtil redisUtil;

    private static Logger logger = LoggerFactory.getLogger(StartSpiderTask.class);

    /**
     * 定时任务启动爬虫,爬取数据
     */

    @Scheduled(cron = "30 22 * * * ?")
    public void startSpider() {
        while (true) {
            // 从数据仓库获取url
            String url = repositoryService.poll();
            // 判断url是否为空
            if (StringUtils.isNotEmpty(url)) {
                AsyncTaskExecutorUtil.execute(() -> {
                    System.out.println("当前线程的id为 : " + Thread.currentThread().getId());
                    // 使用jsoup下载页面
                    Page page = downloadService.download(url,PageType.YOUKU);
                    // 对下载的页面进行处理,这里会将列表页面和详情页面都添加到urlList中
                    processService.process(page, PageType.YOUKU);

                    // 当前页面的url如果是详情页,则数据已经抓取,这里进行存储
                    if (page.getUrl().contains("http://list.youku.com/show/id_")) {
                        // TODO
                        // 将存储到mongoDB中的数据添加到redis中,为了同步到solr中
                        redisUtil.lpush(SolrConstant.SOLR_TV_INDEX.toString(),page.getTvId());

                        storeService.save(page);
                        logger.info("详情数据已经添加到数据库");
                    }
                    List<String> urlList = page.getUrlList();
                    if (urlList != null && !urlList.isEmpty()) {
                        for (String url1 : urlList) {
                            if (url1.contains("http://list.youku.com/category/show/")) {
                                // 说明是列表页面,因此加入高优先级队列
                                repositoryService.addHighLevel(url1);
                            } else {
                                repositoryService.addLowLevel(url1);
                            }
                        }
                    }
                    // 防止频繁访问ip被封,这里每个线程休息一定时间
                    AsyncTaskExecutorUtil.sleep(3000);
                });
            } else {
                System.out.println("队列中的所有url已经解析完毕>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                AsyncTaskExecutorUtil.sleep(5000);
            }
        }
    }

    /**
     * 定时任务启动爬虫,添加起始url
     */
    @Scheduled(cron = "00 00 22 * * ?")
    public void addStartUrl() {
        repositoryService.addStartUrl();
    }


}
