- mysql8.0查看索引有几层树

```sql
SELECT
	b.NAME,
	a.NAME,
	index_id,
	type,
	a.space,
	a.PAGE_NO 
FROM
	information_schema.INNODB_INDEXES a,
	information_schema.INNODB_TABLES b 
WHERE
	a.table_id = b.table_id 
	AND a.space <> 0;
```

