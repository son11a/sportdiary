package harjoitustyo.sportdiary.domain;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import harjoitustyo.sportdiary.domain.Category;
import harjoitustyo.sportdiary.domain.CategoryRepository;

@Configuration
public class WebSecurityConfig {

@Autowired
    private UserDetailsServiceImp userDetailsService; 

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

 

     @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/userlist/**").hasAnyRole("USER", "ADMIN") 
                .requestMatchers("/sportlist/**", "/addsport/**", "/savesport/**", "/delete/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/userlist")
                .permitAll()
            )
            .logout(logout -> logout.permitAll());

        return http.build();
    }


@Bean
    CommandLineRunner loadUsers(UserRepository userRepository,
                                PasswordEncoder passwordEncoder,
                            CategoryRepository categoryRepository) {
        return args -> {

            if (categoryRepository.count() == 0) {
                categoryRepository.save(new Category("Walking"));
                categoryRepository.save(new Category("Running"));
                categoryRepository.save(new Category("Cycling"));
                
            }

                User user = new User(
                        "user",
                        passwordEncoder.encode("password"),
                        "user@email.com",
                        "ROLE_USER"
                );

                User admin = new User(
                        "admin",
                        passwordEncoder.encode("admin"),
                        "admin@email.com",
                        "ROLE_ADMIN"
                );

                userRepository.save(user);
                userRepository.save(admin);

                
            };
        };
    

}
