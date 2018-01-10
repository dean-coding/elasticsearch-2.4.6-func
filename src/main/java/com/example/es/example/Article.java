package com.example.es.example;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;

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