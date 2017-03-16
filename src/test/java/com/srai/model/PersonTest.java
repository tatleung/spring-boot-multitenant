package com.srai.model;

import com.srai.model.repository.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tatleung on 3/15/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonTest {

    @Autowired
    PersonRepository personRepository;

    @Before
    public void setUp() {
        Person manager = new Person();
        manager.setFirstName("John");
        manager.setLastName("Smith");

        Person subordinate = new Person();
        subordinate.setFirstName("Worker");
        subordinate.setLastName("Bee");
        subordinate.setManager(manager);

        manager.setSubordinates(Arrays.asList(subordinate));
        personRepository.save(manager);
    }

    @Test
    public void testFindByLastName() {
        List<Person> personList = personRepository.findByLastName("Smith");
        assert(personList.size() > 0);
        List<Person> subordinates = personList.get(0).getSubordinates();
        System.out.println("--------------");
        System.out.println(subordinates);
    }
}
