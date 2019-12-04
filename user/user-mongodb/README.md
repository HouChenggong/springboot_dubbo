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