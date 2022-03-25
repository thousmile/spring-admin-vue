package com.xaaef.robin;

import com.xaaef.robin.util.IdUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.util.UrlUtils;

class NoSpringTests {

    /**
     * 5748337749776
     * <p>
     * 5748337749815
     */
    @Test
    void contextLoads() {
        for (int i = 0; i < 100; i++) {
            System.out.println(IdUtils.getStandaloneId());
        }
    }

}
