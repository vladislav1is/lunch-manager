package com.redfox.lunchmanager.service.datajpa;

import com.redfox.lunchmanager.service.AbstractUserServiceTest;
import org.springframework.test.context.ActiveProfiles;

import static com.redfox.lunchmanager.Profiles.DATAJPA;


@ActiveProfiles(DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
}