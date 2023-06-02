package br.com.desenvolvimento.marcosApi;

import br.com.desenvolvimento.marcosApi.domain.Users;
import br.com.desenvolvimento.marcosApi.repository.UserRepository;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class Config {

    @Autowired
    private UserRepository repository;
    @Bean
    public void startDB(){
        Users u1 = new Users(null,"Valdir","valdir@gmail.com","123");
        Users u2 = new Users(null,"Luiz","luiz@gmail.com","123");

        repository.saveAll(List.of(u1,u2));

    }
}
