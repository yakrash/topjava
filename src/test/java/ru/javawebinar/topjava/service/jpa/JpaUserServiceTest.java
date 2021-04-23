package ru.javawebinar.topjava.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.util.Profiles;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles(Profiles.JPA)
public class JpaUserServiceTest extends UserServiceTest {
}
