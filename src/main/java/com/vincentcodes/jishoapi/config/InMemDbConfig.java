package com.vincentcodes.jishoapi.config;

import com.vincentcodes.jishoapi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class InMemDbConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(InMemDbConfig.class);

    @Autowired
    UserService userService;

    @Value("${spring.inmemdb}")
    boolean useInMemDb;

    @PostConstruct
    public void initInMemDb(){
        if(!useInMemDb) return;

        LOGGER.info("Creating root account: \"root:password\" for h2");
        userService.createUser("root", "password");
        //userCrudDao.save(new AppUser("root", passwordEncoder.encode("password")));
    }
}
