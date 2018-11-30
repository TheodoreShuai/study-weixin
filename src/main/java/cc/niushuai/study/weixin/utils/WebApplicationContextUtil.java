package cc.niushuai.study.weixin.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


@Component
public class WebApplicationContextUtil implements ServletContextListener {

    private WebApplicationContextUtil() {
    }

    private static WebApplicationContext webApplicationContext;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if (this.webApplicationContext == null) {
            this.webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        }
    }

    public static WebApplicationContext getWebApplicationContext() {
        return webApplicationContext;
    }

    public static ServletContext getServletContext() {
        return getWebApplicationContext().getServletContext();
    }

    public static void setAttribute(String name, Object value) {
        getServletContext().setAttribute(name, value);
    }

    public static Object getAttribute(String name){
        return getServletContext().getAttribute(name);
    }

}  