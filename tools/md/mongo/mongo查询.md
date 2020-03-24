# 1. mongo只查询某个字段

```java
https://www.jianshu.com/p/24a44c4c7651
或者如下：
Query query = new Query();
query.addCriteria(Criteria.where("status").is(3));
query.skip(skipNumber);
query.limit(pageSize);
query.fields().include("name").include("status");
return mongoTemplate.find(query, CompanyInformation.class);


```



### 2. 只查询某些字段

1. 查询全部字段，根据条件查询

```
List<CodeRulePackage> getAllByConfigFatherIdAndShow
(String configFatherId, Boolean show);
```

2.  只查询几个字段，用对象查询

其中PackageSelect是只有id，custom,customServert三个字段的对象

```
    @Query(value = "{ 'configFatherId' : ?0 ,'show' : ?1}", fields = "{ 'id' : 1, 'custom' : 1,'customServert':1}")
    List<PackageSelect> getAllOnlyIdByConfigFatherIdAndShow(String configFatherId, Boolean show);
    或者直接查询：
    
 @Query(value = "{ 'configFatherId' : ?0 ,'show' : ?1}")
   List<PackageSelect> getAllOnlyIdByConfigFatherIdAndShow(String configFatherId, Boolean show);
```

3. 用map、List查询都不可以，因为查询的结果是JSON

 ## 3. 多表联查

问题1：objectId不能和string联查，解决方案是：

```sql

```



```sql
collection.aggregate([
  { 
    $addFields: { "_id": { "$toString": "$_id" } }
  },
  {
    $lookup: {
      from: "category",
      localField: "_id",
      foreignField: "mId",
      as: "categories"
    }
  }
])
```

