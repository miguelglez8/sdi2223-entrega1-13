package com.uniovi.sdimywallapop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;
import com.uniovi.sdimywallapop.entities.User;

@Service
public class InsertSampleDataService {

    @Autowired
    private UsersService usersService;
    @Autowired
    private RolesService rolesService;
    @PostConstruct
    public void init() {
        User user1 = new User("99999990A", "Pedro", "Díaz");
        user1.setPassword("123456");
        user1.setRole(rolesService.getRoles()[0]);
        User user2 = new User("99999991B", "Lucas", "Núñez");
        user2.setPassword("123456");
        user2.setRole(rolesService.getRoles()[0]);
        User user3 = new User("99999992C", "María", "Rodríguez");
        user3.setPassword("123456");
        user3.setRole(rolesService.getRoles()[0]);
        User user4 = new User("99999993D", "Marta", "Almonte");
        user4.setPassword("123456");
        user4.setRole(rolesService.getRoles()[1]);
        User user5 = new User("99999977E", "Pelayo", "Valdes");
        user5.setPassword("123456");
        user5.setRole(rolesService.getRoles()[1]);


        usersService.addUser(user1);
        usersService.addUser(user2);
        usersService.addUser(user3);
        usersService.addUser(user4);
        usersService.addUser(user5);

    }
}
