1. user-mongodb是一个测试mongo功能的springboot工程，而非dubbo工程，
原本考虑做成dubbo工程的，但是觉得有点麻烦，就索性还是springboot了


2. 我的mongo是本地安装的,默认没有密码，这点可以自行修改
3. 项目中用到的id是雪花id

```sql
//mongodb查看db里有几个collection
show collections

//mongodb查询数据库有哪些表

db.col.find(...).count() 
db.col.find(...).limit(n) //根据条件查找数据并返回指定记录数 
db.col.find(...).skip(n) 
db.col.find(...).sort(...) //查找排序 
db.col.findOne([query]) //根据条件查询只查询一条数据 
db.col.getDB() get DB object associated with collection //返回表所属的库 
db.col.getIndexes() //显示表的所有索引 
db.col.group( { key : ..., initial: ..., reduce : ...[, cond: ...] } ) //根据条件分组 
db.col.mapReduce( mapFunction , reduceFunction , <optional params> ) 
db.col.remove(query) //根据条件删除数据 
db.col.renameCollection( newName ) renames the collection //重命名表 
db.col.save(obj) //保存数据 
db.col.stats() //查看表的状态 
db.col.storageSize() - includes free space allocated to this collection //查询分配到表空间大小 
db.col.totalIndexSize() - size in bytes of all the indexes //查询所有索引的大小 
db.col.totalSize() - storage allocated for all data and indexes //查询表的总大小 
db.col.update(query, object[, upsert_bool]) //根据条件更新数据 
db.col.validate() - SLOW //验证表的详细信息 
db.col.getShardVersion() - only for use with sharding

//正则
{"n": {'$regex' : '.*' + _req.n + '.*'}}


//mongodb 实现lbs查询
/***************************
首先需对col里的loc设置索引为'2d',方可进行$near查询
db.col.ensureIndex({'loc':'2d'})
db.col.getIndexes()
****************************/
db.col.find({"loc": {$near: [longtitude, lattitude], $maxDistance:0.1}})

// mongodb里数组值查询
/*****************************
{ _id: 1, results: [ 82, 85, 88 ] }
{ _id: 2, results: [ 75, 88, 89 ] }
db.scores.find(
   { results: { $elemMatch: { $gte: 80, $lt: 85 } } }
)
******************************/
db.col.find({"img": {$elemMatch: {$regex: ".*willschang.*"}}})
```







```sql
//查询nickName字段为int类型的数据
db.getCollection('mongoUser').find({nickName:{$type:"int"}})
//查询nickName字段为null的记录和没有nickName列的数据
db.getCollection('mongoUser').find({nickName:null})
//查询nickName字段为null的记录而且必须有nickName列
db.getCollection('mongoUser').find({nickName:{$exists:true,$eq:null}})
//查询nickName字段为null的记录
db.getCollection('mongoUser').find({nickName:{$type:10}})
```

```sql

//查询imgUrl字段存在，而且长度大于1的，只看5个
db.mongoUser.find({imgUrl:{$exists:true},$where:"(this.imgUrl.length > 1)"}).limit(5)
```


4. mongo  win10如何修改密码？
```sql
## 1.登陆mongo
use admin 
## 2. 创建管理员账户root 密码是：xiyou
db.createUser({user: 'root', pwd: 'xiyou', roles: ['root']})
## 3. 停止mongo服务,找到 MongoDB 安装目录，打开 mongod.cfg文件，找到以下这句：
 security:
修改为：
security:
  authorization: enabled

## 4. 重启mongo服务，输入账户密码登陆
```

5. 给某个库创建权限和密码？
```sql
## 1. 切换到xiyou数据库
use xiyou
## 2. 创建读写权限账户
db.createUser({user:'xiyouReadWrite',pwd:'xiyouReadWrite',roles: [{role:'readWrite',db:'xiyou'}]})});
## 3. 其实没有必要用命令行，直接用robo 3T界面化创建用户名和密码
```

### 2 索引属性：

1. unique唯一索引

2. sparse稀疏索引
3. [不区分大小写的索引](https://docs.mongodb.com/manual/core/index-case-insensitive/) 
4. 过期索引
5. 部分索引

(sparse indexes)在有索引字段的document上添加索引，如在address字段上添加稀疏索引时，只有document有address字段时才会添加索引。而普通索引则是为所有的document添加索引，使用普通索引时如果document没有索引字段的话，设置索引字段的值为null。

 expire after 用于设置document的过期时间，mongoDB会在document过期后将其删除，TTL非常容易实现类似缓存过期策略的功能。

create in background 为超大表建立索引，一定要true，否则需要占用很长时间写锁

局部索引

(Partial Indexes)顾名思义，只对collection的一部分添加索引。创建索引的时候，根据过滤条件判断是否对document添加索引，对于没有添加索引的文档查找时采用的全表扫描，对添加了索引的文档查找时使用索引。使用方法也比较简单：

## 3. 索引执行计划：

https://mp.weixin.qq.com/s/sH3eN7IF6zMedDeC_Vpcww?

查询计划：   .explain("executionStats")

查询方式，常见的有COLLSCAN/全表扫描、IXSCAN/索引扫描、FETCH/根据索引去检索文档、SHARD_MERGE/合并分片结果、IDHACK/针对_id进行查询

## 4. 索引类型

单键索引（主键和普通）、复合索引、多键索引、地理空间索引、全文本索引、哈希索引

### 4.1. 单键索引(Single Field Indexes)

```json
{
  "_id": ObjectId("570c04a4ad233577f97dc459"),
  "score": 1034,
  "location": { state: "NY", city: "New York" }
}
```

MongoDB默认创建的_id的索引就是单键索引。

值1表示以升序对项目进行排序的索引。值-1指定一个索引，该索引按降序对项目进行排序

```sql
--普通的单键索引。
db.records.createIndex( { score: 1 } )
值1表示以升序对项目进行排序的索引。值-1指定一个索引，该索引按降序对项目进行排序
```

```sql
--在嵌入式字段创建的单键索引
db.records.createIndex( { "location.state": 1 } )
```



```sql
--在嵌入式文档创建单键索引，好像必须完全匹配
db.records.createIndex( { location: 1 } )
```

如果是根据主键查询的话，winningPlan为：stage:`IDHACK`针对_id进行查询

如果是普通的单键索引，winningPlan为：stage:`IXSCAN`索引扫描



### 4. 2复合索引

```json
{
 "_id": ObjectId(...),
 "item": "Banana",
 "category": ["food", "produce", "grocery"],
 "location": "4th Street Store",
 "stock": 4,
 "type": "cases"
}
```

```sql
-- explain解释出来的有两个stage和复合索引的名称   
"winningPlan" : {
        "stage" : "FETCH",
        "inputStage" : {
            "stage" : "IXSCAN",
            "keyPattern" : {
                "file_hashsimHash_3" : 1,
                "file_hashsimHash_4" : 1,
                "file_hashsimHash_5" : 1,
                "file_hashsimHash_6" : 1
            },
            "indexName" : "file_hashsimHash_3_to_6_index",
```
1. 索引支持的排序

```sql
--创建复合索引，但是这个顺序很重要，而且满足最左匹配原则，和MySQL一样
db.products.createIndex( { "item": 1, "stock": 1 } )

--下面两个可以
db.events.find().sort( { item: 1, stock: 1 } )
db.events.find().sort( { item: -1, stock: -1 } )
--下面两个不可以
db.events.find().sort( { item: 1, stock: -1 } )
db.events.find().sort( { item: -1, stock: 1 } )
```



2. 索引支持的最左匹配原则

```sql
db.data.createIndex( { a:1, b: 1, c: 1, d: 1 } )
--Then, the following are prefixes for that index:
{ a: 1 }
{ a: 1, b: 1 }
{ a: 1, b: 1, c: 1 }


db.data.find().sort( { a: 1 } ) 		  --Index Prefix	{ a: 1 }
db.data.find().sort( { a: -1 } )	      --Index Prefix	{ a: 1 }
db.data.find().sort( { a: 1, b: 1 } )     --Index Prefix	{ a: 1, b: 1 }
db.data.find().sort( { a: -1, b: -1 } )   --Index Prefix	{ a: 1, b: 1 }
db.data.find().sort( { a: 1, b: 1, c: 1 } ) --Index Prefix{ a: 1, b: 1, c: 1 }
db.data.find( { a: { $gt: 4 } } ).sort( { a: 1, b: 1 } ) --Index Prefix	{ a: 1, b: 1 }
```

  3.排序和非前缀索引的子集

```sql
-- For example, the collection data has the following index:
{  a ： 1 ， b ： 1 ， c ： 1 ， d ： 1  }

-- The following operations can use the index to get the sort order:
db.data.find( { a: 5 } ).sort( { b: 1, c: 1 } )		--Index Prefix	{ a: 1 , b: 1, c: 1 }
db.data.find( { b: 3, a: 4 } ).sort( { c: 1 } )		--Index Prefix{ a: 1, b: 1, c: 1 }
db.data.find( { a: 5, b: { $lt: 3} } ).sort( { b: 1 } )	--Index Prefix{ a: 1, b: 1 }
--These operations will not efficiently use the index { a: 1, b: 1, c: 1, d: 1 } and may not even use the index to retrieve the documents.

db.data.find( { a: { $gt: 2 } } ).sort( { c: 1 } )
db.data.find( { c: 5 } ).sort( { c: 1 } )
```

4. 索引和排序规则

```sql
例如，该集合myColl在category具有排序规则语言环境的字符串字段上具有索引"fr"。
db.myColl.createIndex( { category: 1 }, { collation: { locale: "fr" } } )

--The following query operation, which specifies the same collation as the index, can use the index:
db.myColl.find( { category: "cafe" } ).collation( { locale: "fr" } )
--However, the following query operation, which by default uses the “simple” binary collator, cannot use the index:

db.myColl.find( { category: "cafe" } )
```

复合索引，即使用不到排序规则，也能用前缀索引，如下

```sql
db.myColl.createIndex(
   { score: 1, price: 1, category: 1 },
   { collation: { locale: "fr" } } )
   
--The following operations, which use "simple" binary collation for string comparisons, can use the index:   
db.myColl.find( { score: 5 } ).sort( { price: 1 } )
db.myColl.find( { score: 5, price: { $gt: NumberDecimal( "10" ) } } ).sort( { price: 1 } )
--The following operation, which uses "simple" binary collation for string comparisons on the indexed category field, 
--can use the index to fulfill only the score: 5 portion of the query:
--就是只能用score索引进行查询
db.myColl.find( { score: 5, category: "cafe" } )
```

#### [详细介绍Collation](http://www.mongoing.com/archives/3912)



### 4.3 多键索引

如果任何索引字段是数组，MongoDB都会自动创建一个多键索引；您无需显式指定多键类型。


## 副本集
1. 一主2从， 主挂掉，两个从节点开始选举，其中之一当主
2. 一主1从1仲裁者 ，仲裁者不存储数据，只负责投票
接下来是创建1主2从
压缩包地址：
链接：https://pan.baidu.com/s/1y-AVXlpoRHMabJSKnmkI_g 
提取码：lfa5

1. 启动mongodb.bat 之前确保里面的config是mongodNoAuth.conf ,启动如果想把历史数据删除，删除data里面的数据和mongod.log以及mongod.pid
2.运行mongodb.bat
3.在分别测试3个实例是否能被连接
4. 在主节点一个窗口执行下面的命令，注意_id是mongodNoAuth.conf副本集里面的值
```sql
 rsconf = {
   _id: "cobotWeb",
   members: [
     {
      _id: 0,
      host: "127.0.0.1:27017"
     },
     {
      _id: 1,
      host: "127.0.0.1:27018"
     },
     {
      _id: 2,
      host: "127.0.0.1:27019"
     }
    ]
 }
rs.initiate(rsconf)
rs.status()
db.isMaster()
插入数据验证一致性

```
4. 接下来就是
https://www.cnblogs.com/s6-b/p/11128002.html

