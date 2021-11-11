package com.redfox.lunchmanager.service.datajpa;

import com.redfox.lunchmanager.service.AbstractRestaurantServiceTest;
import org.springframework.test.context.ActiveProfiles;

import static com.redfox.lunchmanager.Profiles.DATAJPA;

@ActiveProfiles(DATAJPA)
public class DataJpaRestaurantServiceTest extends AbstractRestaurantServiceTest {
}