package com.bjtu.redis;


import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Tuple;

public class JedisUtils {
//连接池
   public   static JedisPool jedisPool = JedisInstance.getInstance();


  //设置  set 值
public static String set(String key , String value){
   Jedis jedis = getJedis();
    if(jedis == null){
        return "实例获取失败";
    }

    String result = null;
    try {
        result = jedis.set(key, value);
        System.out.println("输入成功");
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        jedisPool.returnResource(jedis);
    }

    return result;

}
//设置值 set 并添加 过期时间
public static String set(String key,String value,int expire){
    Jedis jedis = getJedis();
    if(jedis == null){
        return "实例获取失败";
    }

    String result = null;
    try {
        result = jedis.set(key, value);
        jedis.expire(key,expire);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        jedisPool.returnResource(jedis);
    }

    return result;
}
//通过 get 返回值（字符串格式）
public static  String get(String key){
    Jedis jedis = getJedis();
    if(jedis == null){
        return "实例获取失败";
    }

    String result = null;
    try {
        result = jedis.get(key);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        jedisPool.returnResource(jedis);
    }

    return result;
}


//输入 key值和相应的过期时间（单位，秒）
public  static long expire(String key,int seconds){
    Jedis jedis = getJedis();
    if(jedis == null){
        return -1;
    }

    long result = 0;
    try {
        result = jedis.expire(key,seconds);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        jedisPool.returnResource(jedis);
    }

    return result;
}
//incr操作，incr后自增1
public  static long incr(String key){
    Jedis jedis = getJedis();
    if(jedis == null){
        return -1;
    }

    long result = 0;
    try {
        result = jedis.incr(key);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        jedisPool.returnResource(jedis);
    }

    return result;
}
//给 key 增加值 interger
    public  static long incrby(String key,long integer){
        Jedis jedis = getJedis();
        if(jedis == null){
            return -1;
        }

        long result = 0;
        try {
            result = jedis.incrBy(key,integer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }

        return result;
    }

    //decr操作，decr后 key  自减 1
    public  static long decr(String key){
        Jedis jedis = getJedis();
        if(jedis == null){
            return -1;
        }

        long result = 0;
        try {
            result = jedis.decr(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }

        return result;
    }
    //给 key 减少值 interger
    public  static long decrby(String key,long integer){
        Jedis jedis = getJedis();
        if(jedis == null){
            return -1;
        }

        long result = 0;
        try {
            result = jedis.decrBy(key,integer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }

        return result;
    }



//判断 key 值是否存在
public  static boolean JudgeExists(String key){
    Jedis jedis = getJedis();
    if(jedis == null){
        return false;
    }

    boolean result = false;
    try {
        result = jedis.exists(key);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        jedisPool.returnResource(jedis);
    }

    return result;
}

//删除  key  值
public static void Delete(String...keys){
    Jedis jedis = getJedis();
    if(jedis == null){
        return;
    }
    long result =0;
    try {
        result = jedis.del(keys);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        jedisPool.returnResource(jedis);
    }

    return ;
}
//set if not exists  在没有当前值时set当前值
public static long setnx(String key,String value){
    long result =0;
    Jedis jedis = getJedis();
    if(jedis == null){
        return result ;
    }

    try {
        result = jedis.setnx(key,value);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        jedisPool.returnResource(jedis);
    }
    return result;
}

//set if not exists  在没有当前值时set当前值并添加过期时间
public static long setnx(String key,String value,int expire){
        long result =0;
        Jedis jedis = getJedis();
        if(jedis == null){
            return result ;
        }

        try {
            result = jedis.setnx(key,value);
            jedis.expire(key,expire);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return result;
    }


//list操作
//list 左加值，可以连续添加多个
public static  long lpush(String key,String...values){
    long result =0;
    Jedis jedis = getJedis();
    if(jedis == null){
        return result ;
    }

    try {
        result = jedis.lpush(key,values);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        jedisPool.returnResource(jedis);
    }
    return result;
}
//list操作
//list 右加值
    public static  long rpush(String key,String...values){
        long result =0;
        Jedis jedis = getJedis();
        if(jedis == null){
            return result ;
        }

        try {
            result = jedis.rpush(key,values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return result;
    }

//list操作
//list 右删除值
    public static  String rpop(String key){
        String result = null;
        Jedis jedis = getJedis();
        if(jedis == null){
            return result ;
        }

        try {
            result = jedis.rpop(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return result;
    }
//list操作
//列出 list 中的从start 到 end 的值  ，如果 start = 0， end = -1，则为列出所有值
    public static  List<String> lrange(String key,long start ,long end){
        List<String> result = null;
        Jedis jedis = getJedis();
        if(jedis == null){
            return null ;
        }

        try {
           result = jedis.lrange(key,start,end);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return result;
    }
//list操作
//获取列表长度
public static  long llen(String key){
    long result =0;
    Jedis jedis = getJedis();
    if(jedis == null){
        return result ;
    }

    try {
        result = jedis.llen(key);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        jedisPool.returnResource(jedis);
    }
    return result;
}
//list操作
//移除count数量的value值
public static  long lrem(String key,long count,String value){
    long result =0;
    Jedis jedis = getJedis();
    if(jedis == null){
        return result ;
    }

    try {
        result = jedis.lrem(key,count,value);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        jedisPool.returnResource(jedis);
    }
    return result;
}


//list操作
//对list进行剪枝，保存 start 到 end 中间的值
public static  String ltrim(String key,long start ,long end){
        Jedis jedis = getJedis();
        if(jedis == null){
            return "实例获取失败";
        }

        String result = null;
        try {
            result = jedis.ltrim(key,start,end);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }

        return result;
    }

//set集合操作
//向集合中添加若干个元素
public static  long sadd(String key,String...members){
    long result =0;
    Jedis jedis = getJedis();
    if(jedis == null){
        return result ;
    }

    try {
        result = jedis.sadd(key,members);
        System.out.println("ok");
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        jedisPool.returnResource(jedis);
    }
    return result;
}

//set集合操作
//移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。
public static  long srem(String key,String...members){
        long result =0;
        Jedis jedis = getJedis();
        if(jedis == null){
            return result ;
        }

        try {
            result = jedis.srem(key,members);
            System.out.println("ok");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return result;
    }
//移除并返回集合中的一个随机元素。
public static  String spop(String key){
        String result =null;
        Jedis jedis = getJedis();
        if(jedis == null){
            return result ;
        }

        try {
            result = jedis.spop(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return result;
    }
//set
//判断 member 元素是否集合 key 的成员。
public  static boolean sismember(String key,String member){
    Jedis jedis = getJedis();
    if(jedis == null){
        return false;
    }

    boolean result = false;
    try {
        result = jedis.sismember(key,member);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        jedisPool.returnResource(jedis);
    }

    return result;
}

//zset
//将一个或多个 member 元素及其 score 值加入到有序集 key 当中。,其中score用来排序
public static  long zadd(String key,double score,String member){
    long result =0;
    Jedis jedis = getJedis();
    if(jedis == null){
        return result ;
    }

    try {
        result = jedis.zadd(key,score,member);
        System.out.println("ok");
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        jedisPool.returnResource(jedis);
    }
    return result;
}

//zset
//zrange实现
public static Set<String> zrange(String key,long start,long end){
   Set<String> result = null ;
    Jedis jedis = getJedis();
    if(jedis == null){
        return result ;
    }

    try {
      result  =    jedis.zrange(key,start,end);
        Set<Tuple> tuples = jedis.zrangeWithScores(key, 0, -1);
        for (Tuple tuple : tuples) {
            System.out.println(tuple.getElement() + ":" + tuple.getScore());
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        jedisPool.returnResource(jedis);
    }
    return  result;
}

//zset
//删除zset中指定的value
public static  long zrem(String key,String...members){
    long result =0;
    Jedis jedis = getJedis();
    if(jedis == null){
        return result ;
    }

    try {
        result = jedis.zrem(key,members);
        System.out.println("ok");
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        jedisPool.returnResource(jedis);
    }
    return result;
}

//zset
//为有序集 key 的成员 member 的 score 值加上增量 increment 。
public  static Double zincrby(String key,double score,String member){
    double result =0.0;
    Jedis jedis = getJedis();
    if(jedis == null){
        return result ;
    }

    try {
        result = jedis.zincrby(key,score,member);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        jedisPool.returnResource(jedis);
    }
    return result;
}
//zset
//返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递增(从小到大)顺序排列。
public static  long zrank(String key,String member){
    long result =0;
    Jedis jedis = getJedis();
    if(jedis == null){
        return result ;
    }

    try {
        result = jedis.zrank(key,member);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        jedisPool.returnResource(jedis);
    }
    return result;
}


//Hash
//将哈希表 key 中的域 field 的值设为 value 。
public static  long hset(String key,String field,String value){
    long result =0;
    Jedis jedis = getJedis();
    if(jedis == null){
        return result ;
    }

    try {
        result = jedis.hset(key,field,value);
        System.out.println("ok");
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        jedisPool.returnResource(jedis);
    }
    return result;
}

//Hash
//将哈希表 key 中的域 field 的值设置为 value ，当且仅当域 field 不存在。
//若域 field 已经存在，该操作无效。
public static  long hsetnx(String key,String field,String value){
    long result =0;
    Jedis jedis = getJedis();
    if(jedis == null){
        return result ;
    }

    try {
        result = jedis.hsetnx(key,field,value);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        jedisPool.returnResource(jedis);
    }
    return result;
}
//hash
//同时将多个 field-value (域-值)对设置到哈希表 key 中。
//此命令会覆盖哈希表中已存在的域。
public static  String hmset(String key,Map<String,String> hash){
        String result =null;
        Jedis jedis = getJedis();
        if(jedis == null){
            return result ;
        }

        try {
            result = jedis.hmset(key,hash);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return result;
    }

//hash
//返回哈希表 key 中给定域 field 的值。
public static  String hget(String key,String field){
        String result =null;
        Jedis jedis = getJedis();
        if(jedis == null){
            return result ;
        }

        try {
            result = jedis.hget(key,field);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return result;
    }
//hash
//返回哈希表 key 中，一个或多个给定域的值。
//如果给定的域不存在于哈希表，那么返回一个 nil 值。
public static  List<String> hmget(String key, String... fields){
       List<String> result =null;
        Jedis jedis = getJedis();
        if(jedis == null){
            return result ;
        }

        try {
            result = jedis.hmget(key,fields);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return result;
    }
//hash
//为哈希表 key 中的域 field 的值加上增量 increment 。
//增量也可以为负数，相当于对给定域进行减法操作。
public static  long hincrby(String key,String field,Long value){
        long result =0;
        Jedis jedis = getJedis();
        if(jedis == null){
            return result ;
        }

        try {
            result = jedis.hincrBy(key,field,value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return result;
    }

//hash
//查看哈希表 key 中，给定域 field 是否存在。
public  static boolean hexists(String key,String field){
    Jedis jedis = getJedis();
    if(jedis == null){
        return false;
    }

    boolean result = false;
    try {
        result = jedis.hexists(key,field);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        jedisPool.returnResource(jedis);
    }

    return result;
}

//hash
//返回哈希表 key 中域的数量。
public static  long hlen(String key){
        long result =0;
        Jedis jedis = getJedis();
        if(jedis == null){
            return result ;
        }

        try {
            result = jedis.hlen(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return result;
    }
//hash
//删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
public static  long hdel(String key,String...fields){
    long result =0;
    Jedis jedis = getJedis();
    if(jedis == null){
        return result ;
    }

    try {
        result = jedis.hdel(key, fields);
        System.out.println("ok");
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        jedisPool.returnResource(jedis);
    }
    return result;
}

//hash
//返回哈希表 key 中，所有的域和值。
//在返回值里，紧跟每个域名(field name)之后是域的值(value)，所以返回值的长度是哈希表大小的两倍。
public Map<String,String>  hgetall(String key){
    Map<String,String> result =null;
    Jedis jedis = getJedis();
    if(jedis == null){
        return result ;
    }

    try {
        result = jedis.hgetAll(key);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        jedisPool.returnResource(jedis);
    }
    return result;
}
//统计freq周期次数
public static long getFreq(String beginTime,String endTime,String key){
    long result =0;
    Jedis jedis = getJedis();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if(jedis == null){
        return 0 ;
    }
    try {

        Date bgtm = simpleDateFormat.parse(beginTime);
        long bt = bgtm.getTime()/1000;
        Date edtm = simpleDateFormat.parse(endTime);
        long et = edtm.getTime()/1000;
        result = jedis.zcount(key,bt,et);//利用zset来统计freq的里的key值次数
        System.out.println(jedis.zcount(key,bt,et));
      //  JedisUtils.zrange(key,0,-1);
      //  jedis.zrange(key,0,-1);
        System.out.println("**********");
       // System.out.println(jedis.zrangeByScore("counter",0,-1,withscore));
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        jedisPool.returnResource(jedis);
    }
    return result;
}

//log记录
public static String getLog(String key){
    String result =null;
    Jedis jedis = getJedis();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if(jedis == null){
        return null ;
    }
    try {
        Set<String>  keySet = jsonUtils.freObject.keySet();
        Iterator<String> it1   = keySet.iterator();
        while (it1.hasNext()){
            String key2 = it1.next();
            Object value2 = jsonUtils.freObject.get(key2);
            System.out.println(key2);
            System.out.println(value2);
        }

     //   }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        jedisPool.returnResource(jedis);
    }
    return result;
}

//自增操作
//默认加一的自增
public static void increaseCounter(){

    Jedis jedis = getJedis();
    if(jedis == null){
        return  ;
    }
    try {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = simpleDateFormat.format(date.getTime());
        long time = date.getTime()/1000;
        Gson gson = new Gson();
        Counter icounter = gson.fromJson(jsonUtils.sss,Counter.class) ;
        //  icounter.setCounter(5);//test
        jedis.zadd("counter",time,icounter.getCounter()+1+"");
        icounter.setCounter(icounter.getCounter()+1);
        icounter.setTime(nowTime);
        jsonUtils.writeJsonFile(icounter,jsonUtils.objectPath);
       //   jsonUtils.freObject.add("counter")
        jsonUtils.freObject.put("time :"+nowTime+" ","counter :"+icounter.getCounter()+"");
        jedis.set("Frequent", JSON.toJSONString(jsonUtils.freObject));

        jsonUtils.writeJsonFile(icounter,jsonUtils.objectPath);
        jsonUtils.writeJsonFile(new JSONObject(jsonUtils.freObject),jsonUtils.frePaath);
        System.out.println("ok");


    }catch (Exception e){
        e.printStackTrace();
    }finally {
       // jedisPool.close();
        jedisPool.returnResource(jedis);
    }
    return ;

}

//自减操作
//默认加一的自增
    public static void decreaseCounter(){

        Jedis jedis = getJedis();
        if(jedis == null){
            return  ;
        }
        try {
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowTime = simpleDateFormat.format(date.getTime());
            long time = date.getTime()/1000;
            Gson gson = new Gson();
            Counter icounter = gson.fromJson(jsonUtils.sss,Counter.class) ;
            //  icounter.setCounter(5);//test
            jedis.zadd("counter",time,icounter.getCounter()-1+"");
            icounter.setCounter(icounter.getCounter()+1);
            icounter.setTime(nowTime);
            jsonUtils.writeJsonFile(icounter,jsonUtils.objectPath);
            //   jsonUtils.freObject.add("counter")
            jsonUtils.freObject.put("time :"+nowTime+" ","counter :"+icounter.getCounter()+"");
            jedis.set("Frequent", JSON.toJSONString(jsonUtils.freObject));

            jsonUtils.writeJsonFile(icounter,jsonUtils.objectPath);
            jsonUtils.writeJsonFile(new JSONObject(jsonUtils.freObject),jsonUtils.frePaath);
            System.out.println("ok");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
           //a jedisPool.close();
            jedisPool.returnResource(jedis);
        }
        return ;

    }
//自定义counter增减
public static void increaseCounter(int n){
    Jedis jedis = getJedis();
    if(jedis == null){
        return  ;
    }
    try {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = simpleDateFormat.format(date.getTime());
        long time = date.getTime()/1000;
        Gson gson = new Gson();
        Counter icounter = gson.fromJson(jsonUtils.sss,Counter.class) ;
        //  icounter.setCounter(5);//test
        jedis.zadd("counter",time,icounter.getCounter()+n+"");
        icounter.setCounter(icounter.getCounter()+n);
        icounter.setTime(nowTime);
        jsonUtils.writeJsonFile(icounter,jsonUtils.objectPath);
        //   jsonUtils.freObject.add("counter")
        jsonUtils.freObject.put("time :"+nowTime+" ","counter :"+icounter.getCounter()+"");
        jedis.set("Frequent", JSON.toJSONString(jsonUtils.freObject));

        jsonUtils.writeJsonFile(icounter,jsonUtils.objectPath);
        jsonUtils.writeJsonFile(new JSONObject(jsonUtils.freObject),jsonUtils.frePaath);
        System.out.println("ok");
    }catch (Exception e){
        e.printStackTrace();
    }finally {
        jedisPool.returnResource(jedis);
    }
    return ;

}

//pattern
//返回满足pattern表达式的所有key
public static Set<String> keys (String pattern){
    Set<String> result =null;
    Jedis jedis = getJedis();
    if(jedis == null){
        return result ;
    }

    try {
        result = jedis.keys(pattern);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        jedisPool.returnResource(jedis);
    }
    return result;
}
//type
//通过key判断值得类型
public static  String type(String key){
    String result =null;
    Jedis jedis = getJedis();
    if(jedis == null){
        return result ;
    }

    try {
        result = jedis.type(key);
        System.out.println(result);

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        jedisPool.returnResource(jedis);
    }
    return result;
}


    /**
     * 同步获取Jedis实例
     */
public static Jedis getJedis() {
        Jedis jedis = null;
        try {
            if (jedisPool != null) {
                jedis = jedisPool.getResource();
            }
        } catch (Exception e) {
          e.printStackTrace();
        }

        return jedis;
    }
}


