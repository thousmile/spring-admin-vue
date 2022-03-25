package com.xaaef.robin.util.useragent;

import com.xaaef.robin.util.useragent.browser.Browser;
import com.xaaef.robin.util.useragent.browser.BrowserParser;
import com.xaaef.robin.util.useragent.os.Os;
import com.xaaef.robin.util.useragent.os.OsParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author doujiajun 402550833@qq.com
 */

public class UserAgentParser {
    private OsParser osParser;
    private BrowserParser browserParser;
    private Yaml yaml;

    private final static Pattern netTypePattern = Pattern.compile("\\W(WIFI|5G|4G|3G|2G)\\W*", Pattern.CASE_INSENSITIVE);

    public UserAgentParser() throws IOException {
        loadConfig();
    }

    public void loadConfig() throws IOException {
        yaml = new Yaml(new SafeConstructor());
        osParser = OsParser.addList(readConfig("useragent/os.yml"));
        browserParser = BrowserParser.addList(readConfig("useragent/browser.yml"));
    }

    public List<Map<String, String>> readConfig(String file) throws IOException {
        try (InputStream stream = new ClassPathResource(file).getInputStream()) {
            @SuppressWarnings("unchecked")
            List<Map<String, String>> configs = (List<Map<String, String>>) yaml.load(stream);
            if (configs == null) {
                throw new IllegalArgumentException("config loading failed.");
            }
            return configs;
        }
    }

    public UserAgent parse(String agentString) {
        if (StringUtils.isBlank(agentString)) {
            return null;
        }
        Os os = osParser.parse(agentString);
        Browser browser = browserParser.parse(agentString);
        return new UserAgent(os, browser);
    }

    private String parseNetType(String agentString) {
        Matcher matcher = netTypePattern.matcher(agentString.toUpperCase());
        String result = "";
        if (matcher.find()) {
            result = matcher.group(1);
        }
        return StringUtils.isBlank(result) ? Constants.DEFAULT_VALUE : result;
    }

}
