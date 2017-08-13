package org.pekxxoo.spider.util;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Pattern;

/**
 * Created by chong on 2017/6/25.
 */
public class HtmlUtil {

    /**
     * 通过选择器和正则表达式匹配DOM中的元素
     * @param element
     * @param selector
     * @param regex
     * @return
     */
    public static String getFieldByRegex(Element element, String selector, String regex){
        String content = element.select(selector).text();
        Pattern numberPattern = Pattern.compile(regex, Pattern.DOTALL);

        String number = RegexUtil.getPageInfoByRegex(content,numberPattern,0);

        return number;
    }

    /**
     * 通过选择器和属性名获得属性值
     * @param element
     * @param selector
     * @param attributeKey
     * @return
     */
    public static String getAttributeByName(Element element, String selector, String attributeKey) {
        Element link = element.select(selector).first(); // 取得匹配的第一个元素
        String linkHref = "";
        if(link != null) {
           linkHref = link.attr(attributeKey); //取得链接地址
        }
        return linkHref;
    }

    /**
     * 使用选择器语法来查找元素
     * @param element
     * @param selector
     * @return
     */
    public static Elements getElementsBySelector(Element element, String selector) {
        Elements elements = element.select(selector);
        return elements;
    }
}
