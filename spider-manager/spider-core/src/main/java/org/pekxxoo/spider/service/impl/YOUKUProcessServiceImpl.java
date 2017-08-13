package org.pekxxoo.spider.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.pekxxoo.spider.entity.Page;
import org.pekxxoo.spider.entity.PageType;
import org.pekxxoo.spider.service.IDownloadService;
import org.pekxxoo.spider.service.IProcessService;
import org.pekxxoo.spider.util.HtmlUtil;
import org.pekxxoo.spider.util.JsoupDownloadUtil;
import org.pekxxoo.spider.util.RegexUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * Created by chong on 2017/6/25.
 */
@Service
public class YOUKUProcessServiceImpl implements IProcessService{
    @Autowired
    private Environment env;

    @Autowired
    private IDownloadService downloadService;

    @Override
    public void process(Page page, PageType pageType) {
        //TODO
        if(page == null || page.getUrl().isEmpty()) {
            return;
        }
        if(org.pekxxoo.spider.entity.PageType.YOUKU == pageType) {
            if(page.getUrl().startsWith("http://list.youku.com/show/id_")) { // 说明当前页面是电视剧详情页
                parseDetail(page);
                System.out.println("解析电视剧详情页面>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            }else {
                System.out.println("解析电视剧的列表页面>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                // 获取下一页的链接
                String nextUrl = HtmlUtil.getAttributeByName(page.getContent(),env.getProperty("parseNextUrl"),"abs:href");
                if(StringUtils.isNotEmpty(nextUrl)) {
                    page.addUrl(nextUrl);
                }
                System.out.println("nextUrl : " + nextUrl);
                // 获取列表页中的所有电视剧详情页
                Elements elements = HtmlUtil.getElementsBySelector(page.getContent(),env.getProperty("parseEachDetailUrl"));
                for(Element element : elements) {
                    // 优酷这里获取的链接为直接播放页面,没有我们想要爬取的数据,需要进行下一步处理
                    String link = element.attr("abs:href");
                    String detailUrl = HtmlUtil.getAttributeByName(downloadService.download(link,PageType.YOUKU).getContent(),
                            env.getProperty("detailUrl"), "abs:href");
                    if(StringUtils.isNotEmpty(detailUrl)) {
                        page.addUrl(detailUrl);
                    }
                    System.out.println("detailUrl : " + detailUrl);
                }
            }
        }

    }

    public void parseDetail(Page page) {
        // 获取总播放量
        String allNumber = HtmlUtil.getFieldByRegex(page.getContent(),env.getProperty("parseAllNumber"),env.getProperty("allnumberRegex"));
        page.setAllnumber(allNumber);

        // 获取评论数
        String commentNumber = HtmlUtil.getFieldByRegex(page.getContent(),
                env.getProperty("parseCommentNumber"),env.getProperty("commentnumberRegex"));
        page.setCommentnumber(commentNumber);

        // 获取赞
        String supportNumber = HtmlUtil.getFieldByRegex(page.getContent(),
                env.getProperty("parseSupportNumber"),env.getProperty("supportnumberRegex"));
        page.setSupportnumber(supportNumber);

        //获取优酷电视剧id
        Pattern pattern = Pattern.compile(env.getProperty("idRegex"),Pattern.DOTALL);
        page.setTvId("youku_" + RegexUtil.getPageInfoByRegex(page.getUrl(),pattern,1));

        // 获取电视剧名称
        String tvName = HtmlUtil.getFieldByRegex(page.getContent(),
                env.getProperty("parseTvName"),env.getProperty("tvNameRegex"));
        page.setTvname(tvName);

        // 获取电视剧上映时间
        String releaseTime = HtmlUtil.getFieldByRegex(page.getContent(),
                env.getProperty("parseReleaseTime"),env.getProperty("releaseTimeRegex"));
        page.setReleaseTime(releaseTime);

        // 获取电视剧的当前网站评分
        String grade = HtmlUtil.getElementsBySelector(page.getContent(),env.getProperty("parseGrade")).text();
        page.setGrade(grade);

    }


}
