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

# 几款好用的插件

1、FindBugs  查找潜在bug

2、Maven help分析pom文件的依赖情况（方便解决依赖版本的冲突问题）

# 配置文件

## 1、配置文件

SpringBoot使用一个全局的配置文件，配置文件名是固定的；

•application.properties

•application.yml

配置文件的作用：修改SpringBoot自动配置的默认值；SpringBoot在底层都给我们自动配置好；

标记语言：

​	以前的配置文件；大多都使用的是  **xxxx.xml**文件；

​	YAML：**以数据为中心**，比json、xml等更适合做配置文件；

​	YAML：配置例子

```yaml
server:
  port: 8081
```

​	XML：

```xml
<server>
	<port>8081</port>
</server>
```

## 2、YAML语法：

### 1、基本语法

k:(空格)v：表示一对键值对（空格必须有）；

以**空格**的缩进来控制层级关系；只要是左对齐的一列数据，都是同一个层级的

```yaml
server:
    port: 8081
    path: /hello
```

属性和值也是大小写敏感；



### 2、值的写法

#### 字面量：普通的值（数字，字符串，布尔）

​	k: v：字面直接来写；

​		字符串默认不用加上单引号或者双引号；

​		""：双引号；不会转义字符串里面的特殊字符；特殊字符会作为本身想表示的意思

​				name:   "zhangsan \n lisi"：输出；zhangsan 换行  lisi

​		''：单引号；会转义特殊字符，特殊字符最终只是一个普通的字符串数据

​				name:   ‘zhangsan \n lisi’：输出；zhangsan \n  lisi



#### 对象、Map（属性和值）（键值对）：

​	k: v：在下一行来写对象的属性和值的关系；注意缩进

​		对象还是k: v的方式

```yaml
friends:
		lastName: zhangsan
		age: 20
```

行内写法：

```yaml
friends: {lastName: zhangsan,age: 18}
```



#### 数组（List、Set）：

用- 值表示数组中的一个元素

```yaml
pets:
 - cat
 - dog
 - pig
```

行内写法

```yaml
pets: [cat,dog,pig]
```

## 3、配置文件值注入

配置文件

```yaml
person:
    lastName: hello
    age: 18
    boss: false
    birth: 2017/12/12
    maps: {k1: v1,k2: 12}
    lists:
      - lisi
      - zhaoliu
    dog:
      name: 小狗
      age: 12
```

javaBean：

```java
/**
 * 将配置文件中配置的每一个属性的值，映射到这个组件中
 * @ConfigurationProperties：告诉SpringBoot将本类中的所有属性和配置文件中相关的配置进行绑定；
 *      prefix = "person"：配置文件中哪个下面的所有属性进行一一映射
 *
 * 只有这个组件是容器中的组件，才能容器提供的@ConfigurationProperties功能；
 *
 */
@Component
@ConfigurationProperties(prefix = "person")
public class Person {

    private String lastName;
    private Integer age;
    private Boolean boss;
    private Date birth;

    private Map<String,Object> maps;
    private List<Object> lists;
    private Dog dog;

```

Controller

```
@RestController
public class PersonController {
    @Autowired
    private Person person;
    @GetMapping(value = "GetPersonInfo")
    public String GetPersonInfo(){
        return person.toString();
    }
}
```

我们可以导入配置文件处理器，以后编写配置就有提示了

```xml
<!--导入配置文件处理器，配置文件进行绑定就会有提示-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-configuration-processor</artifactId>
			<optional>true</optional>
		</dependency>
```

#### 1、properties配置文件在idea中默认utf-8可能会乱码

调整

![idea配置乱码](E:/%E6%BA%90%E7%A0%81%E3%80%81%E8%B5%84%E6%96%99%E3%80%81%E8%AF%BE%E4%BB%B6/%E6%96%87%E6%A1%A3/Spring%20Boot%20%E7%AC%94%E8%AE%B0/images/%E6%90%9C%E7%8B%97%E6%88%AA%E5%9B%BE20180130161620.png)

#### 2、@Value获取值和@ConfigurationProperties获取值比较

|                      | @ConfigurationProperties | @Value     |
| -------------------- | ------------------------ | ---------- |
| 功能                 | 批量注入配置文件中的属性 | 一个个指定 |
| 松散绑定（松散语法） | 支持                     | 不支持     |
| SpEL                 | 不支持                   | 支持       |
| JSR303数据校验       | 支持                     | 不支持     |
| 复杂类型封装         | 支持                     | 不支持     |

配置文件yml还是properties他们都能获取到值；

如果说，我们只是在某个业务逻辑中需要获取一下配置文件中的某项值，使用@Value；

如果说，我们专门编写了一个javaBean来和配置文件进行映射，我们就直接使用@ConfigurationProperties；



#### 3、配置文件注入值数据校验

```java
@Component
@ConfigurationProperties(prefix = "person")
@Validated
public class Person {

    /**
     * <bean class="Person">
     *      <property name="lastName" value="字面量/${key}从环境变量、配置文件中获取值/#{SpEL}"></property>
     * <bean/>
     */

   //lastName必须是邮箱格式
    @Email
    //@Value("${person.last-name}")
    private String lastName;
    //@Value("#{11*2}")
    private Integer age;
    //@Value("true")
    private Boolean boss;

    private Date birth;
    private Map<String,Object> maps;
    private List<Object> lists;
    private Dog dog;
```

#### 4、@PropertySource

@**PropertySource**：加载指定的配置文件；

```java
/**
 * 将配置文件中配置的每一个属性的值，映射到这个组件中
 * @ConfigurationProperties：告诉SpringBoot将本类中的所有属性和配置文件中相关的配置进行绑定；
 *      prefix = "person"：配置文件中哪个下面的所有属性进行一一映射
 *
 * 只有这个组件是容器中的组件，才能容器提供的@ConfigurationProperties功能；
 *  @ConfigurationProperties(prefix = "person")默认从全局配置文件中获取值；
 *
 */
@PropertySource(value = {"classpath:person.properties"})
@Component
@ConfigurationProperties(prefix = "person")
//@Validated
public class Person {

    /**
     * <bean class="Person">
     *      <property name="lastName" value="字面量/${key}从环境变量、配置文件中获取值/#{SpEL}"></property>
     * <bean/>
     */

   //lastName必须是邮箱格式
   // @Email
    //@Value("${person.last-name}")
    private String lastName;
    //@Value("#{11*2}")
    private Integer age;
    //@Value("true")
    private Boolean boss;

```

**注：这里不知为何读取不了以.yml结尾的文件**

## 4、配置文件占位符

### 1、随机数

```java
${random.value}、${random.int}、${random.long}
${random.int(10)}、${random.int[1024,65536]}

```



### 2、占位符获取之前配置的值，如果没有可以是用:指定默认值

```properties
person.last-name=张三${random.uuid}
person.age=${random.int}
person.birth=2017/12/15
person.boss=false
person.maps.k1=v1
person.maps.k2=14
person.lists=a,b,c
person.dog.name=${person.hello:hello}_dog
person.dog.age=15
```

## 5、Profile

### 1、多Profile文件

我们在主配置文件编写的时候，文件名可以是   application-{profile}.properties/yml

默认使用application.properties的配置；



### 2、yml支持多文档块方式

```yml
server:
  port: 8081
spring:
  profiles:
    active: prod

---
server:
  port: 8083
spring:
  profiles: dev


---

server:
  port: 8084
spring:
  profiles: prod  #指定属于哪个环境
```





### 3、激活指定profile

​	1、在配置文件中指定  spring.profiles.active=dev

```
spring:
  profiles:
    active: dev
```

​	2、命令行：

​		java -jar spring-boot-02-config-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev；

​		可以直接在测试的时候，配置传入命令行参数

​	3、虚拟机参数；

​		-Dspring.profiles.active=dev



## 6、配置文件加载位置

springboot 启动会扫描以下位置的application.properties或者application.yml文件作为Spring boot的默认配置文件

–file:./config/

–file:./

–classpath:/config/

–classpath:/

优先级由高到底，高优先级的配置会覆盖低优先级的配置；

SpringBoot会从这四个位置全部加载主配置文件；**互补配置**；

# 基于注解实现SpringBoot多数据源配置

## 1、在application.yml中添加多数据源配置

 添加多个数据源和mapper文件路径配置，此配置用于基于java的配置数据源中使用。 

```
spring:
  datasource:
    first:
       driver-class-name: com.mysql.jdbc.Driver
       jdbc-url: jdbc:mysql://localhost:3306/db_person
       username: root
       password: root
    second:
       driver-class-name: com.mysql.jdbc.Driver
       jdbc-url: jdbc:mysql://localhost:3306/webdb?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull
       username: root
       password: root
  mvc:
    view:
    # 定位模板的目录
      prefix: classpath:/templates/
      # 给返回的页面添加后缀名
      suffix: .html
mybatis:
#映射的文件位置
  mapper-locations: classpath:mapper/*.xml
#  映射的实体位置
  type-aliases-package: com.example.demo.entity
```

##2、基于java的方式实现数据库配置

其中DemoUserDbConfig类源代码如下： 其中Configuration注解表识此类为Spring的配置类。 MapperScan注解中的basePackages、annotationClass、sqlSessionTemplateRef用于配置此数据库链接扫描com.example包中所有注解为DemoUserMapper的接口。 

```
@Configuration
@MapperScan(basePackages = {"com.example.demo.mapper"},annotationClass = DbSourceFirst.class,
        sqlSessionTemplateRef = "DbSourceFirstTemplate")
//@Component
public class FirstDbConfig {
    @Value("${spring.datasource.first.jdbc-url}")
    private String url;

    @Value("${spring.datasource.first.username}")
    private String userName;

    @Value("${spring.datasource.first.password}")
    private String password;

    @Value("${spring.datasource.first.driver-class-name}")
    private String driveClassName;

    @Value(value = "${mybatis.mapper-locations}")
    private String mapperLocation;


    @Bean(name = "dbFirst")
    @Primary     //需加这个注解否则会报错
    @ConfigurationProperties(prefix = "spring.datasource.first")
    public DataSource firstDataSource() {
        ///return dataSourceFactory(driveClassName, url, userName, password);
        return DataSourceBuilder.create().build();
    }

//    public DataSource dataSourceFactory(String driveClassName, String url, String userName, String password) {
//        DruidDataSource datasource = new DruidDataSource();
//        datasource.setDriverClassName(driveClassName);
//        datasource.setUrl(url);
//        datasource.setUsername(userName);
//        datasource.setPassword(password);
//        datasource.setMaxActive(20);
//        datasource.setInitialSize(20);
//        return datasource;
//    }

    @Bean(name = "DbSourceFirstTemplate")
    @Primary
    public SqlSessionTemplate dbFirstSqlTemplate() throws Exception {
        return new SqlSessionTemplate((sqlSessionFactory(firstDataSource(), mapperLocation)));
    }

    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, String mapperLocation) throws Exception{
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        Resource[] resource= resourceResolver.getResources(mapperLocation);
        factoryBean.setMapperLocations(resource);
        return factoryBean.getObject();
    }

    @Bean
    @Qualifier("dbFirstTransaction")
    public PlatformTransactionManager demoUserTransaction() {
        return new DataSourceTransactionManager(firstDataSource());
    }
}
```

使用相同的方法定义其他数据源。 

```
@Configuration
@MapperScan(basePackages = {"com.example.demo.mapper2"},annotationClass = DbSourceSecond.class,
        sqlSessionTemplateRef = "DbSourceSecondTemplate")
public class SecondDbConfig {
    @Value("${spring.datasource.second.jdbc-url}")
    private String url;

    @Value("${spring.datasource.second.username}")
    private String userName;

    @Value("${spring.datasource.second.password}")
    private String password;

    @Value("${spring.datasource.second.driver-class-name}")
    private String driveClassName;

    @Value(value = "${mybatis.mapper-locations}")
    private String mapperLocation;


    @Bean(name = "dbSecond")
    @ConfigurationProperties(prefix = "spring.datasource.second")
    public DataSource secondaryDataSource() {
        //return dataSourceFactory(driveClassName, url, userName, password);
        return DataSourceBuilder.create().build();
    }

//    public DataSource dataSourceFactory(String driveClassName, String url, String userName, String password) {
//        DruidDataSource datasource = new DruidDataSource();
//        datasource.setDriverClassName(driveClassName);
//        datasource.setUrl(url);
//        datasource.setUsername(userName);
//        datasource.setPassword(password);
//        datasource.setMaxActive(20);
//        datasource.setInitialSize(20);
//        return datasource;
//    }

    @Bean(name = "DbSourceSecondTemplate")
    public SqlSessionTemplate dbSecondSqlTemplate() throws Exception {
        return new SqlSessionTemplate((sqlSessionFactory(secondaryDataSource(), mapperLocation)));
    }

    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, String mapperLocation) throws Exception{
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        Resource[] resource= resourceResolver.getResources(mapperLocation);
        factoryBean.setMapperLocations(resource);
        return factoryBean.getObject();
    }

    @Bean
    @Qualifier("dbSecondTransaction")
    public PlatformTransactionManager demoUserTransaction() {
        return new DataSourceTransactionManager(secondaryDataSource());
    }
}
```

## 3、定义接口和mapper文件

### 	（1）、以xml配置文件的方案读取

```
@Mapper
@DbSourceSecond
public interface NewsMapper {
    public List<News> findAll();
}
```

mapper文件如下： 

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.example.demo.mapper2.NewsMapper">
    <resultMap id="BaseResultMap" type="com.example.demo.entity.News">
    </resultMap>
    <select id="findAll" resultMap="BaseResultMap">
        select * from webdb.news
    </select>
</mapper>
```

### 	 （2）、以注解的方式读取数据

```
@Mapper
@DbSourceSecond
public interface NewsDao {
    @SelectProvider(type = NewsProvider.class,method = "GetTop1News")
    public News GetTop1News();
}
```

```
public class NewsProvider {
    public String GetTop1News(){
        return "select  * from webdb.news limit 1 ";
    }
}
```

其他数据源配置此处略去

## 4、定义注解

定义DbSourceFirst和DbSourceSecond注解，分别作为数据库的表识。 定义代码如下： 

```
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
@Mapper
public @interface DbSourceFirst {
    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     * @return the suggested component name, if any (or empty String otherwise)
     */
    String value() default "";
}
```

```
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
@Mapper
public @interface DbSourceSecond {
    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     * @return the suggested component name, if any (or empty String otherwise)
     */
    String value() default "";
}
```

## 5、使用单元测试验证配置

```
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
   @Autowired
   private NewsDao newsDao;
   @Test
   public void contextLoads() {
      News news = newsDao.GetTop1News();
      String json= JSONObject.toJSON(news).toString();
      System.out.print(json);
   }
}
```

# spring boot简单整合active mq消息

pom文件添加依赖

```
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-activemq</artifactId>
</dependency>
```

yml文件中添加属性配置

```
spring:
    activemq:
      broker-url: tcp://localhost:61616
      in-memory: true
      pool:
        enabled: false
```

生产者

```
/**
 * 生产者
 */
@Service("producer")
public class Producer {
    @Autowired
    private JmsMessagingTemplate template;

    //  目的地要发送的消息内容
    public void sendMessage(Destination destination, final String message) {
        template.convertAndSend(destination, message);  //调用convertAndSend方法，将信息发送进去
    }
}
```

消费者

```
/**
 * 消费者
 */
@Service
public class Consumer {
    @JmsListener(destination="mytest.queue")   //使用JMSListener 监听在Producer的消息
    public void received(String message) {
        //打印接收到的信息
        System.out.println("customer接收到的信息为： " +message);
    }
}
```

```
/**
 * 消费者
 */
@Service
public class Consumer2 {
    @JmsListener(destination="mytest.queue")   //使用JMSListener 监听在Producer的消息
    public void received(String message) {
        //打印接收到的信息
        System.out.println("customer2接收到的信息为： " +message);
    }
}
```

测试mq

```
@Autowired
private Producer producer;

/**
 * 测试mq
 * @throws InterruptedException
 */
@Test
public void  testActivemq()  throws InterruptedException{
    ActiveMQQueue activeMQQueue = new ActiveMQQueue("mytest.queue");   //设置目的地destination
    for(int i=0;i<10;i++) {
        producer.sendMessage(activeMQQueue, "xxh 是一个好人");
    }
}
```

测试结果

![1544890785444](C:\Users\25702\AppData\Local\Temp\1544890785444.png)

















