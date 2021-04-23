package ru.javawebinar.topjava.service.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.util.Profiles;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {
    @Autowired
    private UserService service;

//    @Test
//    public void findUserWithMeals() {
//        User user = service.findUserWithMeals(100_001);
//        System.out.println(user.getMeals());
//
//    }
}
