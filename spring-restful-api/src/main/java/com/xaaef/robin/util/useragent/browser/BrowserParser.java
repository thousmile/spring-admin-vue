package com.xaaef.robin.util.useragent.browser;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author doujiajun 402550833@qq.com
 */
public class BrowserParser {
    private final List<BrowserPattern> patterns;

    BrowserParser(List<BrowserPattern> patterns) {
        this.patterns = patterns;
    }

    public static BrowserParser addList(List<Map<String, String>> list) {
        return new BrowserParser(
                list.stream().map(config -> {
                    return BrowserPattern.addFromMap(config);
                }).collect(Collectors.toList())
        );
    }

    public Browser parse(String agentString){
        if(StringUtils.isBlank(agentString)){
            return Browser.DEFAULT_BROWSER;
        }

        Browser browser;
        for (BrowserPattern p : patterns) {
            if ((browser = p.match(agentString))!=null) {
                return browser;
            }
        }
        return Browser.DEFAULT_BROWSER;
    }
}
