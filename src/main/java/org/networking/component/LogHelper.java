package org.networking.component;

import org.apache.commons.lang3.StringUtils;
import org.networking.entity.User;
import org.networking.enums.LogType;
import org.networking.tools.SpringSecurityUtils;
import org.springframework.stereotype.Component;

/**
 * Log users' operations.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 11/6/15
 * @since JDK1.8
 */
@Component
public class LogHelper {

    public void logUsersOperations(LogType logType, String resource, User currentUser) throws Exception {
        // Get ip and clientId
        String ip = SpringSecurityUtils.getCurrentUserIp();
        ip = StringUtils.isBlank(ip) ? "0.0.0.0.0.0.0.0:1" : ip;
        String clientId = SpringSecurityUtils.getCurrentUsername();

        // Log users' operations.
        //logDomain.create(new LogParam(ip, logType, clientId, resource), currentUser);
    }

}
