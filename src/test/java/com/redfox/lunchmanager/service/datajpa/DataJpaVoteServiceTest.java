package com.redfox.lunchmanager.service.datajpa;

import com.redfox.lunchmanager.service.AbstractVoteServiceTest;
import org.springframework.test.context.ActiveProfiles;

import static com.redfox.lunchmanager.Profiles.DATAJPA;

@ActiveProfiles(DATAJPA)
class DataJpaVoteServiceTest extends AbstractVoteServiceTest {
}