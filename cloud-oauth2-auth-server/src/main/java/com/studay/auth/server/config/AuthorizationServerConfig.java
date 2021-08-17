package com.studay.auth.server.config;

import com.studay.auth.server.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;

/**
 * @description: 认证服务器配置类
 * @author: huangxinle
 * @date: 2021/8/16 11:27
 */
@EnableAuthorizationServer //开启认证服务器功能
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private DataSource dataSource;

    // 授权码管理策略
    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService(){
        return new JdbcClientDetailsService(dataSource);
    }
    /**
     * 配置被允许访问此认证服务器的客户端详情信息
     * 方式1：内存方式管理
     * 方式2：数据库管理
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        // 使用内存方式
//        clients.inMemory()
//                // 客户端id
//                .withClient("wj-pc")
//                // 客户端密码，要加密,不然一直要求登录
//                .secret(encoder.encode("wj-secret"))
//                // 资源id, 如商品资源
//                .resourceIds("product-server")
//                // 授权类型, 可同时支持多种授权类型
//                .authorizedGrantTypes("authorization_code", "password", "implicit", "client_credentials", "refresh_token")
//                // 授权范围标识，哪部分资源可访问（all是标识，不是代表所有）
//                .scopes("all")
//                // false 跳转到授权页面手动点击授权，true 不用手动授权，直接响应授权码，
//                .autoApprove(false)
//                .redirectUris("http://www.baidu.com/");// 客户端回调地址
        //使用数据库方式
//        clients.withClientDetails(jdbcClientDetailsService());

        //动态查询数据库
        clients.jdbc(dataSource)
                .passwordEncoder(encoder);
    }

    /**
     * 重写父类的方法
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //密码模式需要设置此认证管理器
        endpoints.authenticationManager(authenticationManager);
        // 刷新令牌获取新令牌时需要
        endpoints.userDetailsService(customUserDetailsService);
        //设置token存储策略
        endpoints.tokenStore(tokenStore);
        endpoints.authorizationCodeServices(authorizationCodeServices());
    }

    // 向容器中导入AuthorizationCodeServices
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(){
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //所有人可以访问/oauth/token_key后面获取公钥，默认拒绝访问
        security.tokenKeyAccess("permitAll()");
        //认证后可访问/oauth/check_token，默认拒绝访问
        security.checkTokenAccess("isAuthenticated()");
    }
}
