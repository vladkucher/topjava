package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles(profiles = {Profiles.POSTGRES,Profiles.DATAJPA})
public class UserServiceDATAJPATest  extends UserServiceTest{
}
