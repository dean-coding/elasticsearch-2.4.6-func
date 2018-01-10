# spring-data-elasticsearch springboot 集成使用
## 1.环境

windows 环境

springboot <version>1.5.9.RELEASE</version>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-elasticsearch</artifactId>
</dependency>

对应es的版本：2.4.0
<dependency>
    <groupId>org.elasticsearch</groupId>
    <artifactId>elasticsearch</artifactId>
    <version>${elasticsearch}</version>
</dependency>


elasticsearch官网文档：版本下载
https://www.elastic.co/guide/en/elasticsearch/reference/2.4/index.html

GUI客户端：kibana
https://www.elastic.co/cn/products/kibana

## 2.application.yml配置文档

spring:
  data:
    elasticsearch:
      cluster-name: xiaolang
      cluster-nodes: 192.168.1.108:9300
#      properties:
#        path:
#          logs: ./elasticsearch/log
#          data: ./elasticsearch/data
      repositories:
        enabled: true

如果配置： cluster-name和cluster-nodes地址，则是外部elasticsearch服务（注意 版本对应关系：参见：https://github.com/spring-projects/spring-data-elasticsearch）
反之，springboot内嵌的elasticsearch服务，本例：讲述外部连接操作

注意：cluster-name和cluster-nodes配置前，需修改外部elasticsearch服务的配置config/elasticsearch.yml：

cluster.name: xiaolang
network.host: 0.0.0.0 //开发节点连接IP
http.port: 9200
node.name: node-1

修改后，能用 ：本机IP地址+端口访问 -> json字符串，成功

## 3.domain 配置：

/**
 * @Doucument
 * <p>
 * <p>indexName //索引库 的名称；建议项目名
 * <p>type //类型，可以理解为表
 * <p>indexStoreType //索引存储类型 ：fs为文件存储
 * <p>shards //分区数
 * <p>replicas //分区备份数
 *<p> refreshInterval//刷新间隔
 */
@Data
@Document(indexName = "projectname", type = "article", indexStoreType = "fs", shards = 5, replicas = 1, refreshInterval = "-1")
public class Article implements Serializable {
	private static final long serialVersionUID = -8924587650447036483L;
	@Id
	private Long id;
	/** 标题 */
	private String title;
	/** 摘要 */
	private String abstracts;
	/** 内容 */
	private String content;
	/** 发表时间 */
	private Date postTime;
	/** 点击率 */
	private Long clickCount;
	private Author author;
}

## 4.db操作

public interface ArticleSearchRepository extends ElasticsearchRepository<Article, Long> {
}

## 5.测试操作：（参照他人示例）

@RestController
public class ArticleCtrl {
	@Autowired
	private ArticleSearchRepository articleSearchRepository;
	@RequestMapping("/add")
	public void testSaveArticleIndex() {
		Author author = new Author();
		author.setId(1L);
		author.setName("tianshouzhi");
		author.setRemark("java developer");
		Article article = new Article();
		article.setId(1L);
		article.setTitle("springboot integreate elasticsearch");
		article.setAbstracts("springboot integreate elasticsearch is very easy");
		article.setAuthor(author);
		article.setContent("elasticsearch based on lucene," + "spring-data-elastichsearch based on elaticsearch"
				+ ",this tutorial tell you how to integrete springboot with spring-data-elasticsearch");
		article.setPostTime(new Date());
		article.setClickCount(1L);
		articleSearchRepository.save(article);
	}
	@RequestMapping("/query")
	public Iterator<Article> testSearch(@RequestParam("keywords") String keywords) {
		QueryStringQueryBuilder builder = new QueryStringQueryBuilder(keywords);
		Iterable<Article> searchResult = articleSearchRepository.search(builder);
		Iterator<Article> iterator = searchResult.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		return iterator;
	}
}

## 6 结果展示：
http://localhost:8080/add						//添加一条记录
http://localhost:8080/query?keywords=springboot //查询一条记录
http://192.168.1.108:9200/projectname //外部es服务，查看结果

## 7 常见问题

org.elasticsearch.transport.NodeDisconnectedException //多半是端口或者地址的问题，检测节点端口：9300，ip地址，是否修改es服务的配置文件，允许连接

NodeDisconnectedException...等
查看es服务日志：java.lang.IllegalStateException: Received message from unsupported version:

//这个多半是版本不匹配的问题，检测，你应用的elasticsearch版本跟外部的elasticsearch服务版本是否是一致的
参照：https://github.com/spring-projects/spring-data-elasticsearch


个人整理，如有问题，请多指教