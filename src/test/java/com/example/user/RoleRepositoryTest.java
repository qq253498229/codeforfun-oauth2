package com.example.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@DataJpaTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class RoleRepositoryTest {
    private static final String USER = "USER";
    @Autowired
    private RoleRepository roleRepository;

    @Before
    public void setUp() {
        roleRepository.deleteAll();
        roleRepository.flush();
        roleRepository.save(new Role(USER));
    }

    @Test
    public void findByName() {
        Role byName = roleRepository.findByName(USER);
        assertNotNull(byName);
    }
}