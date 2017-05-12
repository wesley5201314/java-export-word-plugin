package org.java.export.plugin.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wesley on 2017-05-10.
 * 上下文
 */
public class RequestResponseContext {

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseContext.class);

    public static HttpServletRequest getRequest(){
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            logger.debug("currentThread is not exits RequestContext");
            throw new IllegalStateException("currentThread is not exits RequestContext");
        }
        return attrs.getRequest();
    }
}
