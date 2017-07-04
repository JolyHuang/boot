package com.sharingif.cube.spring.boot.web.springmvc.autoconfigure.components;

import com.sharingif.cube.persistence.database.DataSourcePoolConfig;
import com.sharingif.cube.security.confidentiality.encrypt.TextEncryptor;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * TomcatComponentsAutoconfigure
 * 2017年5月3日 上午11:30:53
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Configuration
public class TomcatComponentsAutoconfigure {


	@Bean(name="tomcatFactory")
	public TomcatEmbeddedServletContainerFactory tomcatFactory(List<DataSourcePoolConfig> dataSourcePoolConfigList, @Qualifier("propertyTextEncryptor") TextEncryptor propertyTextEncryptor) {
		TomcatEmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory = new TomcatEmbeddedServletContainerFactory() {

			@Override
			protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(Tomcat tomcat) {
				tomcat.enableNaming();
				return super.getTomcatEmbeddedServletContainer(tomcat);
			}

			@Override
			protected void postProcessContext(Context context) {
				addNamingResources(context, dataSourcePoolConfigList, propertyTextEncryptor);
			}
		};

		return tomcatEmbeddedServletContainerFactory;
	}

	protected void addNamingResources(Context context, List<DataSourcePoolConfig> dataSourcePoolConfigList, TextEncryptor propertyTextEncryptor) {

		for(DataSourcePoolConfig dataSourcePoolConfig : dataSourcePoolConfigList) {

			ContextResource resource = new ContextResource();
			resource.setName(dataSourcePoolConfig.getJndiName());
			resource.setType(dataSourcePoolConfig.getType());
			resource.setProperty("driverClassName", dataSourcePoolConfig.getDriverClassName());
			resource.setProperty("url", dataSourcePoolConfig.getUrl());
			resource.setProperty("username", dataSourcePoolConfig.getUsername());

			String password = dataSourcePoolConfig.getPassword();
			if(propertyTextEncryptor != null) {
				password = propertyTextEncryptor.decrypt(password);
			}
			resource.setProperty("password", password);

			// 创建连接阶段
			resource.setProperty("initialSize", String.valueOf(dataSourcePoolConfig.getInitialSize()));											// 启动时初始化多少连接数
			resource.setProperty("maxTotal", String.valueOf(dataSourcePoolConfig.getMaxTotal()));												// 连接池中最多能有多少连接数
			resource.setProperty("maxWaitMillis", String.valueOf(dataSourcePoolConfig.getMaxWaitMillis()));										// 请求连接池最大等待时间，单位：毫秒
			resource.setProperty("maxIdle", String.valueOf(dataSourcePoolConfig.getMaxIdle()));													// 链接池中最大空闲连接
			resource.setProperty("minIdle", String.valueOf(dataSourcePoolConfig.getMinIdle()));													// 链接池中最小空闲连接

			// 程序关闭连接阶段,归还连接到连接池
			resource.setProperty("removeAbandonedOnBorrow", String.valueOf(dataSourcePoolConfig.isRemoveAbandonedOnBorrow()));					// 是否清理被遗弃的连接，和removeAbandonedTimeout一起使用
			resource.setProperty("removeAbandonedTimeout", String.valueOf(dataSourcePoolConfig.getRemoveAbandonedTimeout()));					// 清理被遗弃的连接等待时间，单位：秒

			// 空闭连接处理阶段,空闭连接断开从连接池中清处掉
			resource.setProperty("timeBetweenEvictionRunsMillis", String.valueOf(dataSourcePoolConfig.getTimeBetweenEvictionRunsMillis())); 	// 多长时间检查一次连接池中空闲的连接,单位：毫秒
			resource.setProperty("minEvictableIdleTimeMillis", String.valueOf(dataSourcePoolConfig.getMinEvictableIdleTimeMillis()));  			// 空闲时间超过多少时间的连接断开,直到连接池中的连接数到minIdle为止，单位：毫秒
			resource.setProperty("numTestsPerEvictionRun", String.valueOf(dataSourcePoolConfig.getNumTestsPerEvictionRun()));					// 每次检查连接的数目，建议设置和maxActive一样大
			resource.setProperty("logAbandoned", String.valueOf(dataSourcePoolConfig.isLogAbandoned()));										// 接池收回空闲的活动连接时是否打印消息

			// 连接验证阶段,防止mysql主动断开连接
			resource.setProperty("testOnBorrow", String.valueOf(dataSourcePoolConfig.isTestOnBorrow()));										// 取时检验
			resource.setProperty("testOnReturn", String.valueOf(dataSourcePoolConfig.isTestOnReturn()));										// 归还检验
			resource.setProperty("testWhileIdle", String.valueOf(dataSourcePoolConfig.isTestWhileIdle()));										// 空闲检验
			resource.setProperty("validationQuery", dataSourcePoolConfig.getValidationQuery());													// 验证sql

			context.getNamingResources().addResource(resource);
		}

	}



}
