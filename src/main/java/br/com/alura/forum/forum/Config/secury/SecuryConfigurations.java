package br.com.alura.forum.forum.Config.secury;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.alura.forum.forum.repository.UsuarioRepository;


@EnableWebSecurity
@Configuration
public class SecuryConfigurations extends WebSecurityConfigurerAdapter{
    
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

    //configuração de autenticação
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
    }

    
    //configuração de recursos  estátiscos( js,css,img e etc)  
    @Override
    public void configure(WebSecurity web) throws Exception {

    }

    //configuracao de autorização
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //liberando os end points publicos
        http.authorizeRequests()
            .antMatchers(HttpMethod.GET,"/topicos").permitAll()
            .antMatchers(HttpMethod.GET,"/topicos/*").permitAll()
            .antMatchers(HttpMethod.POST,"/auth").permitAll()
            .antMatchers(HttpMethod.GET,"/actuator/**").permitAll()
         // metodos autenticados
            .anyRequest().authenticated() 
            .and().csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().addFilterBefore(new AutenticacaoViaTokenFilter(this.tokenService,this.usuarioRepository) , UsernamePasswordAuthenticationFilter.class);
    }

}
