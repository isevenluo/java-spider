package org.pekxxoo.spider.entity;

import org.apache.solr.client.solrj.beans.Field;
import org.jsoup.nodes.Element;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * 页面信息实体类
 * Created by chong on 2017/6/24.
 */
@Document
public class Page extends AbstractDocument{

    //页面内容
    @Transient
    private Element content;
    //总播放量
    @Field
    private String allnumber;
    //每日播放增量
    private String daynumber;
    //评论数
    private String commentnumber;
    //收藏数
    private String collectnumber;
    //赞
    private String supportnumber;
    //踩
    private String againstnumber;

    //电视剧名称
    @Field
    private String tvname;
    //页面url
    @Field
    private String url;
    //子集数据
    private String episodenumber;

    //电视剧id
    @Indexed()
    @Field
    private String tvId;

    //存储电视剧url（包含列表url和详情页url）
    @Transient
    private List<String> urlList = new ArrayList<String>();

    // 网站类型
    private PageType type;

    //网站电视剧评分
    private String grade;

    //上映时间
    @Field
    private String releaseTime;

    //抓取日期
    private String grabTime;

    private Date createTime;

    public Page(String allnumber, String commentnumber, String supportnumber, String url, String tvId) {
        this.allnumber = allnumber;
        this.commentnumber = commentnumber;
        this.supportnumber = supportnumber;
        this.url = url;
        this.tvId = tvId;
    }

    public  Page() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
        String time = sdf.format(date);
        this.grabTime = time;
        this.createTime = date;
    }

    public Element getContent() {
        return content;
    }

    public void setContent(Element content) {
        this.content = content;
    }

    public String getAllnumber() {
        return allnumber;
    }

    public void setAllnumber(String allnumber) {
        this.allnumber = allnumber;
    }

    public String getDaynumber() {
        return daynumber;
    }

    public void setDaynumber(String daynumber) {
        this.daynumber = daynumber;
    }

    public String getCommentnumber() {
        return commentnumber;
    }

    public void setCommentnumber(String commentnumber) {
        this.commentnumber = commentnumber;
    }

    public String getCollectnumber() {
        return collectnumber;
    }

    public void setCollectnumber(String collectnumber) {
        this.collectnumber = collectnumber;
    }

    public String getSupportnumber() {
        return supportnumber;
    }

    public void setSupportnumber(String supportnumber) {
        this.supportnumber = supportnumber;
    }

    public String getAgainstnumber() {
        return againstnumber;
    }

    public void setAgainstnumber(String againstnumber) {
        this.againstnumber = againstnumber;
    }

    public String getTvname() {
        return tvname;
    }

    public void setTvname(String tvname) {
        this.tvname = tvname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEpisodenumber() {
        return episodenumber;
    }

    public void setEpisodenumber(String episodenumber) {
        this.episodenumber = episodenumber;
    }

    public String getTvId() {
        return tvId;
    }

    public void setTvId(String tvId) {
        this.tvId = tvId;
    }

    public List<String> getUrlList() {
        return urlList;
    }

    public void addUrl(String url) {
        this.urlList.add(url);
    }

    public PageType getType() {
        return type;
    }

    public void setType(PageType type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getGrabTime() {
        return grabTime;
    }

    public void setGrabTime(String grabTime) {
        this.grabTime = grabTime;
    }
}
