package org.networking.component;

import org.apache.commons.lang3.StringUtils;
import org.networking.entity.Log;
import org.networking.entity.User;
import org.networking.enums.LogType;
import org.networking.repository.LogRepository;
import org.networking.tools.SpringSecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
        Log log = new Log();
        log.setLoginIP(ip);
        log.setClientId(clientId);
        log.setAccessResource(resource);
        log.setType(logType);
        log.setUserId(currentUser.getId());
        log.setUsername(currentUser.getUsername());
        // Log users' operations.
        logRepository.save(log);
    }

    @Autowired
    private LogRepository logRepository;

}
