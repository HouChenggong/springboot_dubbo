nginx 

 [淘宝用户nginx](http://tengine.taobao.org/)

tengine

# 1.正向代理

正向代理就是我们访问不了Google，但是我在国外有一台vps，它可以访问Google，我访问它，叫它访问Google后，把数据传给我。

VPN 

# 2.反向代理

反向代理隐藏了真实的服务端，当我们请求 www.baidu.com 的时候，就像拨打10086一样，背后可能有成千上万台服务器为我们服务，但具体是哪一台，你不知道，也不需要知道，你只需要知道反向代理服务器是谁就好了，www.baidu.com 就是我们的反向代理服务器，反向代理服务器会帮我们把请求转发到真实的服务器那里去

### 2.1.1 隐藏端口，指向域名转发到tomcat服务器上

这是我们用nginx最简单方式，这样当我们访问域名的时候，就相当于访问IP:8080了，

最简单的就是我们启动了一个tomcat，因为是带端口的，但是我们不想要端口

```
#域名test.com的请求全部转发到http://xxxxip:8080服务上
```

```java
server {
        listen       80;                                                         
        server_name  test.com;                                               
        client_max_body_size 1024M;

        location / {
            proxy_pass http://xxxxip:8080;
            proxy_set_header Host $host:$server_port;
        }
    }

```



## 2.1反向代理能做什么？

### 2.1.2 Springboot2.+vue打包项目

比如当下非常流行的springboot2.x+vue的项目，就是用的当前这种模式

Nginx本身也是一个静态资源的服务器，当只有静态资源的时候，就可以使用Nginx来做服务器，首先看看Nginx做静态资源服务器

```css
server {
    listen       80;                                                         
    server_name  域名或者IP地址;                                               
    client_max_body_size 1024M;
    location / {
           root   e:wwwroot;
           index  index.html;
       }
}

```

### 2.1.3动静分离、图片服务器

我们已经知道了什么是正向代理与反向代理，这次我们就讲一下Nginx的动静分离的案例，其实质运用的就是反向代理，专门用一台服务器代理服务器上的图片资源。
想使用代理必然要配置代理，配置反向代理，必须要用到`proxy_pass`命令来配置。
打开nginx的配置文件nginx.conf，在你的server虚拟主机段中添加如下配置:

1. 把静态资源放到另外一个服务上，可以说端口不同，IP不同

```
location ~ \.(jpg|gif|png)$ {
    proxy_pass IP:port;
}
```

示例：

```
location ~ \.(jpg|gif|png)$ {
    #         协议://IP地址:端口号(默认是80)
    proxy_pass http://image.itbsl.com;
}
```
2. 把静态资源直接放到当前服务器的某个目录下：

![](E:\2020\code\springboot_dubbo\tools\md\JS\img\动静分离.png)





### 2.1.4 传递用户IP

思考？
反向代理导致了后端服务器接收的客户端IP为前端服务器的IP，而不是客户的真正IP，怎么办？
答: 代理服务器通过设置头信息字段，把用户IP传到后台服务器去。

```java
location ~ \.(jpg|gif|png)$ {
    proxy_set_header X-Forwarded-For $remote_addr;
    proxy_pass http://image.itbsl.com;
}
`

# 6.3 总结

- **正向代理隐藏了真实的客户端**。
- **反向代理隐藏了真实的服务器**。
- Nginx解决跨域问题通过Nginx反向代理将对真实服务器的请求转移到本机服务器来避免浏览器的"同源策略限制"。
- nginx最大理论10万
```

# 3. 负载均衡

轮询

```css
upstream backserver {
    server 192.168.0.14;
    server 192.168.0.15;
}
```

加权

```css
upstream backserver {
    server 192.168.0.14 weight=3;
    server 192.168.0.15 weight=7;
}
```

hash

```css
upstream backserver {
    ip_hash;
    server 192.168.0.14:88;
    server 192.168.0.15:80;
}
```

公平

```css
upstream backserver {
    server server1;
    server server2;
    fair;
}
```

5、url_hash（第三方）
按访问url的hash结果来分配请求，使每个url定向到同一个后端服务器，后端服务器为缓存时比较有效。

```
upstream backserver {
    server squid1:3128;
    server squid2:3128;
    hash $request_uri;
    hash_method crc32;
}123456
```

6.最少连接

```
upstream  dalaoyang-server {
       least_conn;
       server    localhost:10001 weight=1;
       server    localhost:10002 weight=2;
}
```

# 4.  nginx 虚拟主机



### 4.1 基于IP的虚拟主机(不经常用)

 Linux操作系统允许添加IP别名，就是在一块物理网卡上绑定多个lP地址。这样就能够在使用单一网卡的同一个服务器上运行多个基于IP的虚拟主机。

比如：

一台nginx服务器绑定两个ip：192.168.78.132、192.168.78.133，访问不同的ip请求不同的html目录，即：

访问http://192.168.78.132将访问html132目录下的html网页

访问http://192.168.78.133将访问html133目录下的html网页

```css
#user  nobody;
worker_processes  1;

#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

#pid        logs/nginx.pid;

events {
    worker_connections  1024;
}

http {
    include       mime.types;
    default_type  application/octet-stream;
 
    sendfile        on;

    keepalive_timeout  65;

    #配置虚拟主机192.168.78.132
    server {
        #监听的ip和端口，配置192.168.78.132:80
        listen       80;
        #虚拟主机名称这里配置ip地址

        server_name  192.168.78.132;

        #所有的请求都以/开始，所有的请求都可以匹配此location
        location / {
             #使用root指令指定虚拟主机目录即网页存放目录
             #比如访问http://ip/test.html将找到/usr/local/html3/test.html
             #比如访问http://ip/item/test.html将找到/usr/local/html3/item/test.html
             root   /usr/local/nginx/html132;

             #指定欢迎页面，按从左到右顺序查找
             index  index.html index.htm;
        }
    }

    #配置虚拟主机192.168.78.133
    server {
        listen       80;
        server_name  192.168.78.133;
        location / {
            root   /usr/local/nginx/html133;
            index  index.html index.htm;
        }
    }
}
```



### 4.2 基于域名的虚拟主机

两个域名指向同一台nginx服务器，用户访问不同的域名显示不同的网页内容。

两个域名是aaa.test.com和bbb.test.com

nginx服务器使用虚拟机192.168.78.132



这个其实很有意思，比如说多租户平台

```
#配置虚拟主机aaa.test.com
server {

    #监听的ip和端口，配置本机ip和端口
    listen 192.168.78.132:80;         

    #虚拟主机名称是aaa.test.com，请求域名aaa.test.com的url将由此server配置解析
    server_name aaa.test.com;  

    #所有的请求都以/开始，所有的请求都可以匹配此location
    location / {
        #使用root指令指定虚拟主机目录即网页存放目录
        #比如访问http://ip/test.html将找到/usr/local/aaa_html/test.html

        #比如访问http://ip/item/test.html将找到/usr/local/aaa_html/item/test.html
        root /usr/local/aaa_html;       

        #指定欢迎页面，按从左到右顺序查找
        index index.html index.htm;   
    }
}

#配置虚拟主机bbb.test.com
server {
    listen 192.168.78.132:80;
    server_name bbb.test.com;

    location / {
        root /usr/local/bbb_html;
        index index.html index.htm;
    }
}
```



### 4.3 基于端口的虚拟主机

nginx对外提供81和82两个端口监听服务。

请求81端口则请求html81目录下的html

请求82端口则请求html82目录下的html

```css
#user  nobody;
worker_processes  1;


events {
    worker_connections  1024;
}
 

http {

    include       mime.types;

    default_type  application/octet-stream;

    sendfile        on;

    keepalive_timeout  65;

    #配置虚拟主机
    server {
        #监听的ip和端口，配置80
        listen       80;
         #虚拟主机名称这里配置ip地址
        server_name  192.168.101.3;
        #所有的请求都以/开始，所有的请求都可以匹配此location
        location / {
             #使用root指令指定虚拟主机目录即网页存放目录
             #比如访问http://ip/test.html将找到/usr/local/html3/test.html
             #比如访问http://ip/item/test.html将找到/usr/local/html3/item/test.html

            root   /usr/local/nginx/html80;
            #指定欢迎页面，按从左到右顺序查找
            index  index.html index.htm;

        }
    }

    #配置虚拟主机

    server {
        listen       8080;
        server_name  192.168.101.3;

        location / {

            root   /usr/local/nginx/html8080;

            index  index.html index.htm;

        }

    }

}
```



# 5. nginx+keepalive集群

因为前端访问的是域名，但是一个域名只能在一个服务器上，假如nginx服务器挂了，那你的服务也就凉了，怎么办？

集群，或者主从

假设nginx有两台机器192.168.1.21，190.169.1.31，两台都要安装keepalive，因为两台之间要通信

keepalive提供的是虚拟IP  192.168.1.100，因为192.168.1.100不存在，所以叫虚拟IP，如果21活着200就会指向21，反之一样



# 6. NGINX解决跨域问题



# 7 总结

- **正向代理隐藏了真实的客户端**。
- **反向代理隐藏了真实的服务器**。
- Nginx解决跨域问题通过Nginx反向代理将对真实服务器的请求转移到本机服务器来避免浏览器的"同源策略限制"。
- nginx并发最大理论10万