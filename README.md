# java-spider
使用java爬虫框架jsoup爬取视频网站内容

[jsoup使用指南](http://www.open-open.com/jsoup/)

**url分布式管理使用redis**

**数据存储使用mongoDB**

## 全文检索使用solr
### 策略是在保存到mongodDB的时候先将数据添加到redis中,然后定期去读取索引到solr中
