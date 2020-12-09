package com.bjtu.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *  SpringBootApplication
 * 用于代替 @SpringBootConfiguration（@Configuration）、 @EnableAutoConfiguration 、 @ComponentScan。
 * <p>
 * SpringBootConfiguration（Configuration） 注明为IoC容器的配置类，基于java config
 * EnableAutoConfiguration 借助@Import的帮助，将所有符合自动配置条件的bean定义加载到IoC容器
 * ComponentScan 自动扫描并加载符合条件的组件
 */
@SpringBootApplication
public class RedisDemoApplication {
    public static String COUNTERPATH = "src/main/resources/counter.json";


    public static void main(String[] args) throws ParseException {

        SpringApplication.run(RedisDemoApplication.class, args);
        init();//初始化
        Scanner in = new Scanner(System.in);
        in.useDelimiter("\n");
       while (true)
       { JedisPool jedisPool = JedisInstance.getInstance();
          char cin =  in.next().charAt(0);
          switch (cin) {
              case 'A':
              case 'a':
                  System.out.println("Counter 自增，不输值默认增加 1。");
                      JedisUtils.increaseCounter();
                break;
              case 'B':
              case 'b':
                  System.out.println("Counter 自减，不输值默认减少 1。");
                  JedisUtils.decreaseCounter();
                  break;
              case 'C':
              case 'c':
                  System.out.println("Counter 自定义增减。");
                  System.out.println("请输入值");
                  int tmp = Integer.parseInt(String.valueOf(in.next()));
                  JedisUtils.increaseCounter(tmp);
                  break;
              case 'D':
              case 'd':
                  try {
                      System.out.println("frequent周期统计");
                      System.out.println("请输入开始时间(格式：yyyy-MM-dd HH:mm:ss)");
                      Date date1 = new Date();
                      Date date2 = new Date();
                      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                      //  String nowTime = simpleDateFormat.format(date.getTime());
                      String tmp1 = in.next();
                     // date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tmp1);
                     // tmp1 = simpleDateFormat.format(date1);
                      System.out.println("请输入结束时间(格式：yyyy-MM-dd HH:mm:ss)");
                      String tmp2 = in.next();
                    //  date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tmp2);
                     // tmp2 = simpleDateFormat.format(date2);
                      JedisUtils.getFreq(tmp1, tmp2, "counter");
                      break;

                  }catch (Exception e)
                  {
                      e.printStackTrace();
                  }
                  break;
              case 'E':
              case 'e':
                  System.out.println("log查看。");
                  JedisUtils.getLog("counter");
                  break;
              case 'F':
              case 'f':
                  System.out.println("新增 set 值，默认不设置过期时间");
                  System.out.println("输入 key 值");
                  String tmp3 = in.next();
                  System.out.println("输入 value 值");
                  String tmp4 = in.next();
                  JedisUtils.set(tmp3,tmp4);
                  break;
              case 'G':
              case 'g':
                  System.out.println("查看 set 值");
                  System.out.println("请输入要查看的值：");
                  String tmp5 = in.next();
                  if(JedisUtils.JudgeExists(tmp5))
                  System.out.println(JedisUtils.get(tmp5));
                  else
                      System.out.println("没有您要找的值");
                  break;
              case 'H':
              case 'h':
                  System.out.println("删除 set 中的 key 值");
                  System.out.println("请输入要删除的值：");
                  String tmp6 = in.next();
                  if(JedisUtils.JudgeExists(tmp6))
                     JedisUtils.Delete(tmp6);
                  else
                      System.out.println("没有您要找的值");
                  break;
              case 'I':
              case 'i':
                  System.out.println("在list中添加左值");
                  System.out.println("输入 key 值");
                  String tmp7 = in.next();
                  System.out.println("输入 value 值");
                  String tmp8 = in.next();
                  JedisUtils.lpush(tmp7,tmp8);
                  break;
              case 'J':
              case 'j':
                  System.out.println("删除list中的右值");
                  System.out.println("输入 key 值");
                  String tmp9 = in.next();
                      JedisUtils.rpop(tmp9);
                  break;

              case 'K':
              case 'k':
                  System.out.println("获取列list表长度");
                  System.out.println("输入 key 值");
                  String tmp10 = in.next();
                      JedisUtils.llen(tmp10);
                  break;
              case 'L':
              case 'l':
                  System.out.println("向zset集合中添加若干个元素");
                  System.out.println("输入 key 值");
                  String tmp11 = in.next();
                  System.out.println("输入 若干个 value 值");
                  String tmp12 = in.next();
                  JedisUtils.sadd(tmp11,tmp12);
                  break;
              case 'M':
              case 'm':
                  System.out.println("移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。");
                  System.out.println("输入 key 值");
                  String tmp13 = in.next();
                  System.out.println("输入 若干个 member 值");
                  String tmp14 = in.next();
                  JedisUtils.srem(tmp13,tmp14);
                  break;
              case 'N':
              case 'n':
                  System.out.println("将一个或多个 member 元素及其 score 值加入到有序集 key 当中。,其中score用来排序");
                  System.out.println("输入 key 值");
                  String tmp15 = in.next();
                  System.out.println("输入 score 值");
                  Double tmp16 = Double.parseDouble(String.valueOf(in.next()));
                  System.out.println("输入 若干个 member 值");
                  String tmp17 = in.next();
                  JedisUtils.zadd(tmp15,tmp16,tmp17);
                  break;
              case 'O':
              case 'o':
                  System.out.println("返回有序集 key 中，指定区间内的成员。");
                  System.out.println("输入 key 值");
                  String tmp18 = in.next();
                  System.out.println("输入 start 值(数字)");
                  Long tmp19 = Long.parseLong(String.valueOf(in.next()));
                  System.out.println("输入 end 值(数字)");
                  Long tmp20 = Long.parseLong(String.valueOf(in.next()));
                  JedisUtils.zrange(tmp18,tmp19,tmp20);
                  break;
              case 'P':
              case 'p':
                  System.out.println("删除zset中指定的value");
                  System.out.println("输入 key 值");
                  String tmp21 = in.next();
                  System.out.println("输入 若干value 值");
                  String tmp22 = in.next();
                  JedisUtils.zrem(tmp21,tmp22);
                  break;
              case 'Q':
              case 'q':
                  System.out.println("将哈希表 key 中的域 field 的值设为 value 。");
                  System.out.println("输入 key 值");
                  String tmp23 = in.next();
                  System.out.println("输入 field 值");
                  String tmp24 = in.next();
                  System.out.println("输入 value 值");
                  String tmp25 = in.next();
                  JedisUtils.hset(tmp23,tmp24,tmp25);
                  break;
              case 'R':
              case 'r':
                  System.out.println("返回哈希表 key 中给定域 field 的值。");
                  System.out.println("输入 key 值");
                  String tmp26 = in.next();
                  System.out.println("输入 field 值");
                  String tmp27 = in.next();
                  JedisUtils.hget(tmp26,tmp27);
                  break;
              case 'S':
              case 's':
                  System.out.println("返回哈希表 key 中域的数量。");
                  System.out.println("输入 key 值");
                  String tmp28 = in.next();
                  JedisUtils.hlen(tmp28);
                  break;
              case 'T':
              case 't':
                  System.out.println("删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。");
                  System.out.println("输入 key 值");
                  String tmp29 = in.next();
                  System.out.println("输入 若干field 值");
                  String tmp30 = in.next();
                  JedisUtils.hdel(tmp29,tmp30);
                  break;
              case 'U':
              case 'u':
                  System.out.println("返回满足pattern表达式的所有key");
                  System.out.println("输入 key 值");
                  String tmp40 = in.next();
                  JedisUtils.keys(tmp40);
                  break;
              case 'V':
              case 'v':
                  System.out.println("通过key判断值得类型");
                  System.out.println("输入 key 值");
                  String tmp50 = in.next();
                  JedisUtils.type(tmp50);
                  break;
              case 'Z':
              case 'z':
                  System.exit(0);
                  break;
          }

       }

    }


    public static void init(){
        System.out.println("*************( redis 封装 )*****************");
        System.out.println("请选择要进行的操作：");
        System.out.println("###################( Counter & frequent )########################");
        System.out.println("a:  Counter 自增，不输值默认增加 1。 ");
        System.out.println("b:  Counter 自减，不输值默认减少 1。 ");
        System.out.println("c:  Counter 自定义增减");
        System.out.println("d:  frequent周期统计");
        System.out.println("e:   log查看 ");
        System.out.println("##################( String )#########################");
        System.out.println("f:  新增 set 值，默认不设置过期时间");
        System.out.println("g:  查看 set 值");
        System.out.println("h:  删除 set 中的 key 值");
        System.out.println("###################( List )########################");
        System.out.println("i:  在list中添加左值");
        System.out.println("j:  删除list中的右值");
        System.out.println("k:  获取列list表长度");
        //System.out.println(":  对list进行剪枝，保存 start 到 end 中间的值");
        System.out.println("################( set )###########################");
        System.out.println("l:  向set集合中添加若干个元素");
        System.out.println("m:  移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。");
      //  System.out.println(":  判断 member 元素是否集合 key 的成员。");
        System.out.println("################( zset )###########################");
        System.out.println("n:  将一个或多个 member 元素及其 score 值加入到有序集 key 当中。,其中score用来排序");
        System.out.println("o:  返回有序集 key 中，指定区间内的成员。");
        System.out.println("p:  删除zset中指定的value");
        System.out.println("################( hash )###########################");
        System.out.println("q:  将哈希表 key 中的域 field 的值设为 value 。");
      //  System.out.println("t:  同时将多个 field-value (域-值)对设置到哈希表 key 中。");
        System.out.println("r:  返回哈希表 key 中给定域 field 的值。 ");
        System.out.println("s:  返回哈希表 key 中域的数量。");
        System.out.println("t:  删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。");
        //System.out.println("x:  返回哈希表 key 中，所有的域和值。");
        System.out.println("################( other )###########################");
        System.out.println("u:  返回满足pattern表达式的所有key");
        System.out.println("v:  通过key判断值得类型");
        System.out.println("z：  退出程序");

    }
}

/*

总结：

1、获取运行环境信息和回调接口。例如ApplicationContextIntializer、ApplicationListener。
完成后，通知所有SpringApplicationRunListener执行started()。

2、创建并准备Environment。
完成后，通知所有SpringApplicationRunListener执行environmentPrepared()

3、创建并初始化 ApplicationContext 。例如，设置 Environment、加载配置等
完成后，通知所有SpringApplicationRunListener执行contextPrepared()、contextLoaded()

4、执行 ApplicationContext 的 refresh，完成程序启动
完成后，遍历执行 CommanadLineRunner、通知SpringApplicationRunListener 执行 finished()

参考：
https://blog.csdn.net/zxzzxzzxz123/article/details/69941910
https://www.cnblogs.com/shamo89/p/8184960.html
https://www.cnblogs.com/trgl/p/7353782.html

分析：

1） 创建一个SpringApplication对象实例，然后调用这个创建好的SpringApplication的实例方法

public static ConfigurableApplicationContext run(Object source, String... args)

public static ConfigurableApplicationContext run(Object[] sources, String[] args)

2） SpringApplication实例初始化完成并且完成设置后，就开始执行run方法的逻辑了，
方法执行伊始，首先遍历执行所有通过SpringFactoriesLoader可以查找到并加载的
SpringApplicationRunListener，调用它们的started()方法。


public SpringApplication(Object... sources)

private final Set<Object> sources = new LinkedHashSet<Object>();

private Banner.Mode bannerMode = Banner.Mode.CONSOLE;

...

private void initialize(Object[] sources)

3） 创建并配置当前SpringBoot应用将要使用的Environment（包括配置要使用的PropertySource以及Profile）。

private boolean deduceWebEnvironment()

4） 遍历调用所有SpringApplicationRunListener的environmentPrepared()的方法，通知Environment准备完毕。

5） 如果SpringApplication的showBanner属性被设置为true，则打印banner。

6） 根据用户是否明确设置了applicationContextClass类型以及初始化阶段的推断结果，
决定该为当前SpringBoot应用创建什么类型的ApplicationContext并创建完成，
然后根据条件决定是否添加ShutdownHook，决定是否使用自定义的BeanNameGenerator，
决定是否使用自定义的ResourceLoader，当然，最重要的，
将之前准备好的Environment设置给创建好的ApplicationContext使用。

7） ApplicationContext创建好之后，SpringApplication会再次借助Spring-FactoriesLoader，
查找并加载classpath中所有可用的ApplicationContext-Initializer，
然后遍历调用这些ApplicationContextInitializer的initialize（applicationContext）方法
来对已经创建好的ApplicationContext进行进一步的处理。

8） 遍历调用所有SpringApplicationRunListener的contextPrepared()方法。

9） 最核心的一步，将之前通过@EnableAutoConfiguration获取的所有配置以及其他形式的
IoC容器配置加载到已经准备完毕的ApplicationContext。

10） 遍历调用所有SpringApplicationRunListener的contextLoaded()方法。

11） 调用ApplicationContext的refresh()方法，完成IoC容器可用的最后一道工序。

12） 查找当前ApplicationContext中是否注册有CommandLineRunner，如果有，则遍历执行它们。

13） 正常情况下，遍历执行SpringApplicationRunListener的finished()方法、
（如果整个过程出现异常，则依然调用所有SpringApplicationRunListener的finished()方法，
只不过这种情况下会将异常信息一并传入处理）


private <T> Collection<? extends T> getSpringFactoriesInstances(Class<T> type)

private <T> Collection<? extends T> getSpringFactoriesInstances(Class<T> type,
			Class<?>[] parameterTypes, Object... args)

public void setInitializers

private Class<?> deduceMainApplicationClass()

public ConfigurableApplicationContext run(String... args)

private void configureHeadlessProperty()

private SpringApplicationRunListeners getRunListeners(String[] args)

public static List<String> loadFactoryNames(Class<?> factoryClass, ClassLoader classLoader)


*/
