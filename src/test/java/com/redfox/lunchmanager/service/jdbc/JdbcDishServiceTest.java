package com.redfox.lunchmanager.service.jdbc;

import com.redfox.lunchmanager.service.AbstractDishServiceTest;
import org.springframework.test.context.ActiveProfiles;

import static com.redfox.lunchmanager.Profiles.JDBC;

@ActiveProfiles(JDBC)
class JdbcDishServiceTest extends AbstractDishServiceTest {
}
