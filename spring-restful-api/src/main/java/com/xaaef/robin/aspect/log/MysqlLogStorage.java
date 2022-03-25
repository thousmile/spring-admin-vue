package com.xaaef.robin.aspect.log;

import com.xaaef.robin.entity.LoginLog;
import com.xaaef.robin.entity.OperLog;
import com.xaaef.robin.service.LoginLogService;
import com.xaaef.robin.service.OperLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


/**
 * <p>
 * 操作日志
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/8/17 16:18
 */

@Slf4j
@Component
@AllArgsConstructor
public class MysqlLogStorage implements LogStorage {

    private final LoginLogService loginLogService;

    private final OperLogService operLogService;

    @Async
    @Override
    public void asyncLoginSave(LoginLog loginLog) {
        loginLogService.save(loginLog);
    }


    @Async
    @Override
    public void asyncOperateSave(OperLog operLog) {
        operLogService.save(operLog);
    }


}
