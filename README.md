##说明
本项目为oauth2的学习示例项目  
代码参考以下博客  
https://github.com/wuyouzhuguli/SpringAll  
https://www.cnblogs.com/wwjj4811/p/14505081.html
##Url解释
Spring Security 对 OAuth2 默认提供了可直接访问端点，即URL：  
/oauth/authorize：申请授权码 code, 涉及的类AuthorizationEndpoint    
http://{{host}}:{{port}}/oauth/authorize
?response_type=code
&client_id=wj-pc
&redirect_uri=http://www.baidu.com/
&scope=all  
/oauth/token：获取令牌 token, 涉及的类TokenEndpoint  
/oauth/check_token：用于资源服务器请求端点来检查令牌是否有效, 涉及的类CheckTokenEndpoint  
/oauth/confirm_access：用户确认授权提交, 涉及的类WhitelabelApprovalEndpoint  
/oauth/error：授权服务错误信息, 涉及的类WhitelabelErrorEndpoint  
/oauth/token_key：提供公有密匙的端点，使用 JWT 令牌时会使用 , 涉及的类TokenKeyEndpoint
