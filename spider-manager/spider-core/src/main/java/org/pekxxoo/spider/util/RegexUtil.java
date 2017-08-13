package org.pekxxoo.spider.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 匹配文本中符合正则表达式的内容
 * Created by chong on 2017/6/25.
 */
public class RegexUtil {

    public static String getPageInfoByRegex(String content, Pattern pattern, int groupNo){
        Matcher matcher = pattern.matcher(content);
        if(matcher.find()){
            return matcher.group(groupNo).trim();
        }
        return "0";
    }
}
