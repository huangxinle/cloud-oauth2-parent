package com.studay.resource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 * @description: 资源服务器配置
 * @author: huangxinle
 * @date: 2021/8/16 15:26
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    //配置当前资源服务器的ID
    private static final String RESOURCE_ID = "product-server";

    /**当前资源服务器的一些配置, 如资源服务器ID **/
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        // 配置当前资源服务器的ID, 会在认证服务器验证(客户端表的resources配置了就可以访问这个服务)
        resources.resourceId(RESOURCE_ID)
                // 实现令牌服务, ResourceServerTokenServices实例
                .tokenServices(tokenService());
    }

    /**
     *  配置资源服务器如何验证token有效性
     *  1. DefaultTokenServices
     *     如果认证服务器和资源服务器同一服务时,则直接采用此默认服务验证即可
     *  2. RemoteTokenServices (当前采用这个)
     *     当认证服务器和资源服务器不是同一服务时, 要使用此服务去远程认证服务器验证
     * */
    @Bean
    public ResourceServerTokenServices tokenService() {
        // 资源服务器去远程认证服务器验证 token 是否有效
        RemoteTokenServices service = new RemoteTokenServices();
        // 请求认证服务器验证URL，注意：默认这个端点是拒绝访问的，要设置认证后可访问
        service.setCheckTokenEndpointUrl("http://localhost:8090/auth/oauth/check_token");
        // 在认证服务器配置的客户端id
        service.setClientId("wj-pc");
        // 在认证服务器配置的客户端密码
        service.setClientSecret("wj-secret");
        return service;
    }

//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.sessionManagement()
//                //不创建session
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                //资源授权规则
//                .authorizeRequests().antMatchers("/product/**").hasAuthority("product")
//                //所有的请求对应访问的用户都要有all范围的权限
//                .antMatchers("/**").access("#oauth2.hasScope('all')");
//    }

}
