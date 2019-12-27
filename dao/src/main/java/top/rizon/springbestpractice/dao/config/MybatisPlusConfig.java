package top.rizon.springbestpractice.dao.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import top.rizon.springbestpractice.dao.utils.dynamictblname.DynamicTableNameUtils;

/**
 * @author rizon
 * @since 2018-08-10
 */
@Configuration
@MapperScan("top.rizon.springbestpractice.dao.mapper")
public class MybatisPlusConfig {
    /**
     * 分页插件
     */

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor interceptor = new PaginationInterceptor();
        DynamicTableNameUtils.registerDynamicTableName(interceptor);
        return interceptor;
    }

    /**
     * SQL执行效率插件
     * 只在 dev test 环境开启
     */
    @Bean
    @Profile({"dev", "test"})
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }
}
