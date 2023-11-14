package only.leo.wfm.web.config;

import only.leo.wfm.common.beans.UserVO;
import only.leo.wfm.core.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

/**
 * @Author: LEO
 * @Date: 2021/9/15 12:53
 */
@Configuration("securityConfig")
@DependsOn("appStarter")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private IUserService userService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/file/download").permitAll()
                .antMatchers("/file/stream").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/fonts/*").permitAll()
                .antMatchers("/css/*").permitAll()
                .antMatchers("/img/*").permitAll()
                .antMatchers("/js/*").permitAll()
                .antMatchers("/*.js").permitAll()
                .antMatchers("/**/*.html").permitAll()
                .antMatchers("/**/*.png").permitAll()
                .antMatchers("/**/*.json").permitAll()
                .antMatchers("/admin/initConfig").permitAll()
                .antMatchers("/admin/getBaseConfig").permitAll()
                .antMatchers("/admin/getIntranetIps").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(digestAuthenticationEntryPoint())
                .and()
                .addFilter(digestAuthenticationFilter());
    }

    @Bean
    DigestAuthenticationEntryPoint digestAuthenticationEntryPoint() {
        DigestAuthenticationEntryPoint entryPoint = new DigestAuthenticationEntryPoint();
        entryPoint.setKey("admin");
        entryPoint.setRealmName("realm");
        entryPoint.setNonceValiditySeconds(1000);
        return entryPoint;
    }
    @Bean
    DigestAuthenticationFilter digestAuthenticationFilter() {
        DigestAuthenticationFilter filter = new DigestAuthenticationFilter();
        filter.setAuthenticationEntryPoint(digestAuthenticationEntryPoint());
        filter.setUserDetailsService(userDetailsService());
        return filter;
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        String password = "123456";
        UserVO userVO = userService.getUserByName("admin");
        if(userVO!=null)password = userVO.getPassword();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin").password(password).roles("admin").build());
        return manager;
    }


}