package com.redfox.lunchmanager.service.jdbc;

import com.redfox.lunchmanager.service.AbstractRestaurantServiceTest;
import org.springframework.test.context.ActiveProfiles;

import static com.redfox.lunchmanager.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcRestaurantServiceTest extends AbstractRestaurantServiceTest {
}
