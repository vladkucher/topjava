package ru.javawebinar.topjava.service;


import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles(profiles = {Profiles.POSTGRES,Profiles.JDBC})
public class MealServiceJDBCTest extends MealServiceTest {
}
