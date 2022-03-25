package com.xaaef.robin.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.xaaef.robin.jwt.JwtSecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDateTime;


/**
 * <p>
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0
 * @date 2021/7/8 9:21
 */


@Slf4j
@Configuration
@EnableTransactionManagement
public class MybatisPlusConfiguration {

    /**
     * 单页分页条数限制(默认无限制,参见 插件#handlerLimit 方法)
     */
    private static final Long MAX_LIMIT = 100L;

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,
     * 需要设置 MybatisConfiguration#useDeprecatedExecutor = false
     * 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        //分页插件: PaginationInnerInterceptor
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setMaxLimit(MAX_LIMIT);

        //防止全表更新与删除插件: BlockAttackInnerInterceptor
        BlockAttackInnerInterceptor blockAttackInnerInterceptor = new BlockAttackInnerInterceptor();

        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        interceptor.addInnerInterceptor(blockAttackInnerInterceptor);
        return interceptor;
    }

    @Slf4j
    @Component
    public static class MyMetaObjectHandler implements MetaObjectHandler {

        private final static String CREATE_TIME = "createTime";

        private final static String CREATE_USER = "createUser";

        private final static String LAST_UPDATE_TIME = "lastUpdateTime";

        private final static String LAST_UPDATE_USER = "lastUpdateUser";

        @Override
        public void insertFill(MetaObject metaObject) {
            if (JwtSecurityUtils.getAuthentication() != null) {
                Long userId = JwtSecurityUtils.getUserId();
                this.strictInsertFill(metaObject, CREATE_USER, () -> userId, Long.class);
                this.strictInsertFill(metaObject, LAST_UPDATE_USER, () -> userId, Long.class);
            }
            this.strictInsertFill(metaObject, CREATE_TIME, LocalDateTime::now, LocalDateTime.class);
            this.strictInsertFill(metaObject, LAST_UPDATE_TIME, LocalDateTime::now, LocalDateTime.class);
        }

        @Override
        public void updateFill(MetaObject metaObject) {
            if (JwtSecurityUtils.getAuthentication() != null) {
                Long userId = JwtSecurityUtils.getUserId();
                this.strictInsertFill(metaObject, LAST_UPDATE_USER, () -> userId, Long.class);
            }
            this.strictInsertFill(metaObject, LAST_UPDATE_TIME, LocalDateTime::now, LocalDateTime.class);
        }

    }

}
