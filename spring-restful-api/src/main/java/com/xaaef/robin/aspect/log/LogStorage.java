package com.xaaef.robin.aspect.log;

import com.xaaef.robin.entity.LoginLog;
import com.xaaef.robin.entity.OperLog;
import com.xaaef.robin.jwt.JwtLoginUser;
import com.xaaef.robin.util.IdUtils;
import com.xaaef.robin.util.IpUtils;
import com.xaaef.robin.util.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * <p>
 * 登录日志
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/8/11 15:29
 */

public interface LogStorage {

    String UA = "User-Agent";

    /**
     * 异步保存 登录日志 到数据库
     *
     * @author Wang Chen Chen
     * @date 2021/8/11 15:30
     */
    default void asyncLoginSave(JwtLoginUser loginUser, HttpServletRequest request) {
        var loginLog = new LoginLog();
        String ipAddr = IpUtils.getIpAddr(request);
        String ua = request.getHeader(UA);
        var agent = RequestUtils.getUserAgent(ua);
        // 设置IP
        loginLog.setRequestIp(ipAddr);
        // 设置登录方式
        loginLog.setGrantType("password");
        // 真是地址
        loginLog.setAddress(IpUtils.getRealAddressByIP(ipAddr));
        // 系统名称
        loginLog.setOsName(agent.getOs().getFamily());
        // 浏览器名称
        loginLog.setBrowser(agent.getBrowser().getFamily());
        // 登录时间
        loginLog.setCreateTime(LocalDateTime.now());

        // 登录的用户
        loginLog.setAvatar(loginUser.getAvatar());
        // 昵称
        loginLog.setNickname(loginUser.getNickname());
        // 用户名
        loginLog.setUsername(loginUser.getUsername());
        // 用户ID
        loginLog.setUserId(loginUser.getUserId());

        // 消息唯一ID
        loginLog.setMessageId(IdUtils.getClusterStrId());

        // 保存日志
        asyncLoginSave(loginLog);
    }


    /**
     * 异步保存 登录日志 到数据库
     *
     * @author Wang Chen Chen
     * @date 2021/8/11 15:30
     */
    void asyncLoginSave(LoginLog loginLog);


    /**
     * 异步保存 操作日志 到数据库
     *
     * @param operLog
     * @author Wang Chen Chen
     * @date 2021/8/11 15:30
     */
    void asyncOperateSave(OperLog operLog);


}
