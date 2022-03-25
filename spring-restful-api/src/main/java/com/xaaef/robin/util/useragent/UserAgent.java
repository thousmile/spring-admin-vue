package com.xaaef.robin.util.useragent;

import com.xaaef.robin.util.useragent.browser.Browser;
import com.xaaef.robin.util.useragent.os.Os;

/**
 * @author doujiajun 402550833@qq.com
 */


public class UserAgent {
    private final Os os;
    private final Browser browser;

    UserAgent(Os os, Browser browser) {
        this.os = os;
        this.browser = browser;
    }

    public Os getOs() {
        return os;
    }

    public Browser getBrowser() {
        return browser;
    }

}
