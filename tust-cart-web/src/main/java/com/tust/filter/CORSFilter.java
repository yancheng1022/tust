package com.tust.filter;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*自定义拦截器 用于给每个都加上跨域的头*/
public class CORSFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //变成http的
        HttpServletResponse resp = (HttpServletResponse) response;
        // 添加参数，允许任意domain访问
//        resp.setContentType("text/html;charset=UTF-8");
        //禁用缓存，确保网页信息是最新数据
        HttpServletRequest req = (HttpServletRequest) request;
        String originHeader = req.getHeader("Origin");
        resp.setHeader("Access-Control-Allow-Origin",originHeader);
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        resp.setHeader("Access-Control-Max-Age", "3600");
        
        chain.doFilter(request, resp);
    }
    public void init(FilterConfig filterConfig) {}


    public void destroy() {}
}
