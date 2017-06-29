package com.sharingif.cube.spring.boot.persistence.mybatis.autoconfigure.components;

import com.sharingif.cube.persistence.database.pagination.IPaginationHandler;
import com.sharingif.cube.persistence.mybatis.interceptor.PaginationInterceptor;
import com.sharingif.cube.persistence.mybatis.pagination.MySqlPaginationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisComponentsAutoconfigure
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/6/29 上午11:38
 */
@Configuration
public class MybatisComponentsAutoconfigure {

    @Bean(name="mySqlPaginationHandler")
    public IPaginationHandler createMySqlPaginationHandler() {
        IPaginationHandler mySqlPaginationHandler = new MySqlPaginationHandler();
        return mySqlPaginationHandler;
    }

    @Bean(name="paginationInterceptor")
    public PaginationInterceptor createPaginationInterceptor(IPaginationHandler mySqlPaginationHandler) {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setPaginationHandler(mySqlPaginationHandler);

        return paginationInterceptor;
    }

}
