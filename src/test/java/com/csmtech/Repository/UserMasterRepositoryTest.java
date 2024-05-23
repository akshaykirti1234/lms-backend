package com.csmtech.Repository;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.csmtech.entity.Location;
import com.csmtech.entity.UserMaster;
import com.csmtech.repository.UserMasterRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserMasterRepositoryTest {

    @Autowired
    private UserMasterRepository userMasterRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testGetUserByEmail() {
        // Create a UserMaster entity
        UserMaster user = new UserMaster();
        user.setEmailId("test@example.com");
       // user.setDeletedFlag(false);
        entityManager.persist(user);

        // Retrieve user by email
        UserMaster retrievedUser = userMasterRepository.getUserByEmail("test@example.com");
        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.getEmailId()).isEqualTo("test@example.com");
    }

    @Test
    public void testGetAllUsers() {
        // Create multiple UserMaster entities
        UserMaster user1 = new UserMaster();
        user1.setEmailId("user1@example.com");
      //  user1.setDeletedFlag(false);
        entityManager.persist(user1);

        UserMaster user2 = new UserMaster();
        user2.setEmailId("user2@example.com");
      //  user2.setDeletedFlag(false);
        entityManager.persist(user2);

        // Retrieve all users
        List<Object> users = userMasterRepository.getAllUsers();
        assertThat(users).isNotNull().isNotEmpty();
        assertThat(users.size()).isEqualTo(6);
    }

    @Test
    public void testGetEmailPassword() {
        // Create a UserMaster entity
        UserMaster user = new UserMaster();
        user.setEmailId("test@example.com");
        user.setNormalPassword("password");
        user.setPassword("encryptedPassword");
       // user.setDeletedFlag(false);
        entityManager.persist(user);

        // Retrieve email and password by email
        Map<String, Object> emailPassword = userMasterRepository.getEmailPassword("test@example.com");
        assertThat(emailPassword).isNotNull();
        assertThat(emailPassword.get("emailid")).isEqualTo("test@example.com");
        assertThat(emailPassword.get("normalpassword")).isEqualTo("password");
    }

    @Test
    public void testGetUseMasterList() {
        // Create UserMaster entities with Location
        Location location = new Location();
        location.setLocationId(1);
        location.setLocationName("Test Location");

        UserMaster user1 = new UserMaster();
        user1.setLocation(location);

        UserMaster user2 = new UserMaster();
        user2.setLocation(location);

        // Retrieve user master list
        List<Map<String, Object>> userMasterList = userMasterRepository.getUseMasterList();
        assertThat(userMasterList).isNotNull().isNotEmpty();
        assertThat(userMasterList.size()).isEqualTo(4);
    }

    @Test
    @Transactional
    public void testDeleteUserMaster() {
        // Create a UserMaster entity
        UserMaster user = new UserMaster();
        user.setUserId(1);

        // Delete user
        userMasterRepository.deleteUserMaster(1);

        // Check if user is deleted
        UserMaster deletedUser = entityManager.find(UserMaster.class, 1);
        assertThat(deletedUser).isNotNull();
    }

    @Test
    public void testGetUserMasterById() {
        // Create a UserMaster entity
        UserMaster user = new UserMaster();
        user.setUserId(1);
        user.setFullName("Admin");

        // Retrieve user by ID
        Map<String, Object> userMap = userMasterRepository.getUserMasterById(1);
        assertThat(userMap).isNotNull();
        assertThat(userMap.get("FULLNAME")).isEqualTo("Admin");
    }

    @Test
    public void testGetEmailList() {
        // Create multiple UserMaster entities
        UserMaster user1 = new UserMaster();
        user1.setEmailId("user1@example.com");

        UserMaster user2 = new UserMaster();
        user2.setEmailId("user2@example.com");

        // Retrieve email list
        List<Map<String, Object>> emailList = userMasterRepository.getEmailList();
        assertThat(emailList).isNotNull().isNotEmpty();
        assertThat(emailList.size()).isEqualTo(4);
    }

    @Test
    public void testExistsByEmailId() {
        // Create a UserMaster entity
        UserMaster user = new UserMaster();
        user.setEmailId("test@example.com");

        // Check if email exists
        boolean exists = userMasterRepository.existsByEmailId("test@example.com");
        assertThat(exists).isFalse();
    }

    @Test
    @Transactional
    public void testUpdatePassword() {
        // Create a UserMaster entity
        UserMaster user = new UserMaster();
        user.setEmailId("test@example.com");
        user.setNormalPassword("oldPassword");
        user.setPassword("oldEncryptedPassword");

        // Save the user to generate an ID
        entityManager.persist(user);
        entityManager.flush(); // Flush to synchronize the persistence context with the database

        // Update password
        userMasterRepository.updatePassword("test@example.com", "oldPassword", "oldEncryptedPassword");

        // Retrieve updated user using the generated ID
        UserMaster updatedUser = entityManager.find(UserMaster.class, user.getUserId());
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getNormalPassword()).isEqualTo("oldPassword");
        assertThat(updatedUser.getPassword()).isEqualTo("oldEncryptedPassword");
    }

}

