//package com.example.es.example;
//
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//
//import org.elasticsearch.client.Client;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
//
//@Configuration
//@EnableElasticsearchRepositories(basePackages = "com.example.es.example")
//public class ElasticsearchConfiguration {
//
//	@Bean
//	public Client client() {
//
//		Settings settings = Settings.builder().put("cluster.name", "xiaolang").put("client.transport.sniff", true)
//				.build();
//		TransportClient client = null;
//
//		try {
//			client = TransportClient.builder().settings(settings).build()
//					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.1.108"), 9300));
//		} catch (UnknownHostException e) {
//			System.err.println("es主机地址配置不正确");
//			e.printStackTrace();
//		}
//		return client;
//	}
//
//	@Bean
//	public ElasticsearchOperations elasticsearchTemplate() {
//		return new ElasticsearchTemplate(client());
//	}
//}