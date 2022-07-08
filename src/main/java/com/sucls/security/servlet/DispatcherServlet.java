package com.sucls.security.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author sucl
 * @date 2022/6/27 18:11
 * @since 1.0.0
 */
public class DispatcherServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherServlet.class);

    private static final String PAGE_PREFIX = "/WEB-INF/pages/";
    private static final String PAGE_SUFFIX = ".jsp";

    private Map<String, RequestInfo> requestMappings;

    private RequestPathHandler requestPathHandler = new RequestPathHandler();

    private RequestPathParser requestPathParser = new RequestPathParser();

    @Override
    public void init() throws ServletException {
        LOGGER.info(" init  RequestMapping");
        requestMappings = requestPathParser.parsePath();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LOGGER.info(" 调用servlet {}.  {} ", req.getRequestURI() , Thread.currentThread().getName());

        String uri = req.getRequestURI();
        if(uri.endsWith(".html") || uri.lastIndexOf("/")==0){
            toPage(req,resp,findRealPage(uri));
        }else{
            toApi(req,resp,uri);
        }
    }

    private String findRealPage(String uri) {
        if(uri.startsWith("/")){
            uri = uri.substring(1);
        }
        if(uri.endsWith(".html")){
            uri = uri.substring(0,uri.length()-5);
        }
        return uri;
    }

    private void toPage(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        if("".equals(path)){
            path = "index";
        }
        path = PAGE_PREFIX + path + PAGE_SUFFIX;
        request.getRequestDispatcher(path).forward(request,response);
    }

    private void toApi(HttpServletRequest request, HttpServletResponse response, String path) {
        RequestInfo requestInfo = requestMappings.get(path);
        if(requestInfo != null){
            requestPathHandler.handle(requestInfo,request,response);
        }
    }

}
