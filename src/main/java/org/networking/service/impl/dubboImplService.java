package org.networking.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.networking.service.dubboInterface;

/**
 * Created by djy on 2016/12/5.
 */
@Service(version = "1.0.0")
public class dubboImplService implements dubboInterface {

    @Override
    public String sayHello() {
        return "hello world!";
    }
}
