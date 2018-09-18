/*
package com.qf.shop.common.jedis;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class JedisTest {

    //单机版本
    @Test
    public void testJedis1(){

        //获取jedis对象
        Jedis jedis = new Jedis("39.106.185.96",6379);

        // 检验密码
        jedis.auth("@qwcyt");

         //设值
        jedis.lpush("javalist1",new String[]{"ab","cf","gg"});


        //取值
        System.out.println(jedis.get("name"));

        //释放资源
        jedis.close();


    }

    //连接池版本
    @Test
    public void testJedis2(){
        //获取Jedis连接池
        JedisPool jedisPool = new JedisPool("39.106.185.96",6379);
        //在连接池中获取资源
        Jedis jedis = jedisPool.getResource();
        jedis.auth("@qwcyt");

        //设置取值
        System.out.println(jedis.lrange("javalist1",0,-1));
        //释放资源
        jedis.close();
        jedisPool.close();


    }

    //集群版本
    @Test
    public void testJedis3() throws IOException {

        JedisCluster cluster =null;
        try {
            //获取集合
            Set<HostAndPort> nodes = new HashSet<HostAndPort>();
            //集合元素进行添加
            nodes.add(new HostAndPort("39.106.185.96",7002));
            nodes.add(new HostAndPort("39.106.185.96",7003));
            nodes.add(new HostAndPort("39.106.185.96",7004));
            nodes.add(new HostAndPort("39.106.185.96",7005));
            nodes.add(new HostAndPort("39.106.185.96",7006));
            nodes.add(new HostAndPort("39.106.185.96",7007));

            //获取redis集群
            cluster = new JedisCluster(nodes);
            //在集群中设置和取值
            System.out.println(cluster.set("name","qwcyt"));

        }catch (Exception e){
            e.printStackTrace();
            //释放资源
            cluster.close();
        }



    }












}
*/
