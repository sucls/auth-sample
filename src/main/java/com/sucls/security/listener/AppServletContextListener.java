package com.sucls.security.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author sucl
 * @date 2022/6/27 17:21
 * @since 1.0.0
 */
public class AppServletContextListener implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        LOGGER.info(" ServletContext Initialized ");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        LOGGER.info(" ServletContext Destroyed ");
    }
}
