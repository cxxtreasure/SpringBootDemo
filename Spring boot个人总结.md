# Spring boot实现监听器

```
package com.example.demo.listener.test;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 使用@WebListener注解，实现ServletContextListener接口
 * 在启动器处加注解@ServletComponentScan
 * @author cxx
 */
@WebListener
public class MyServletContextListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("ServletContext初始化");
        System.out.println(servletContextEvent.getServletContext().getServerInfo());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("ServletContext销毁");
    }
}
```

```
package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan(value = "com.example.demo.mapper")
//filter和listener监听器注解需要用的
@ServletComponentScan
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}

```

# Spring Boot实现过滤器

第一步：定义MyFilter过滤器

```
package com.example.demo.filter.test;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 使用注解过滤器
 * @WebFilter将一个实现了import javax.servlet.Filter接口的类定义为过滤器
 * 属性filterName生命过滤器的名称，可选
 * 属性urlPatterns指定要过滤的URL模式，也可使用属性value来声明（指定要过滤的URL是必选属性）
 */
@WebFilter(filterName = "MyFilter",urlPatterns = "/*")
public class MyFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("过滤器初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("执行过滤操作");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("过滤器销毁");
    }
}
```

第二部：在入口处添加@ServletComponentScan注解

```
package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan(value = "com.example.demo.mapper")
//filter和listener监听器注解需要用的
@ServletComponentScan
public class DemoApplication {

   public static void main(String[] args) {
      SpringApplication.run(DemoApplication.class, args);
   }
}
```

# Spring Boot全局异常处理

全局异常处理：@ControllerAdvice：包含@Componet,可以被扫描到

统一异常处理：@ExceptionHandler（Exception.class）:用在方法上面表示遇到这个异常就执行以下方法。

```
package com.example.demo.global.exception.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionTestController {
    @RequestMapping(value = "ExceptionTest",method = RequestMethod.GET)
    public String index(){
        String str=null;
        str.split("1");
        return "ExceptionTestController";
    }
}
```

```
package com.example.demo.global.exception.test;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
//如果返回的为json数据或其他对象，添加该注解
@ResponseBody
public class GlobalDefaultExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW="error";
    @ExceptionHandler({NullPointerException.class,NumberFormatException.class})
    public ModelAndView formatErrorHandler(HttpServletRequest request,Exception ex){
        System.out.println("已经捕获到异常");
        ModelAndView mv=new ModelAndView();
        mv.addObject("exception",ex);
        mv.addObject("timeStamp",new Date());
        mv.setViewName(DEFAULT_ERROR_VIEW);
        return mv;
    }
}
```











