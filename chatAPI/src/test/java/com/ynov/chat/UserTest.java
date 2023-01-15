package com.ynov.chat;

import com.ynov.chat.model.User;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void testGetName() {
        User user = new User();
        user.setFirstname("Jane");
        assertEquals("Jane", user.getFirstname());
    }

    @Test
    public void testSetName() {
        User user = new User();
        user.setLastname("Smith");
        assertEquals("Smith", user.getLastname());
    }
}
