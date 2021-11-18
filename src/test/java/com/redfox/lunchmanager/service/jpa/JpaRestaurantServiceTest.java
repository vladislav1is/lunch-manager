package com.redfox.lunchmanager.service.jpa;

import com.redfox.lunchmanager.service.AbstractRestaurantServiceTest;
import org.springframework.test.context.ActiveProfiles;

import static com.redfox.lunchmanager.Profiles.JPA;

@ActiveProfiles(JPA)
class JpaRestaurantServiceTest extends AbstractRestaurantServiceTest {
}
