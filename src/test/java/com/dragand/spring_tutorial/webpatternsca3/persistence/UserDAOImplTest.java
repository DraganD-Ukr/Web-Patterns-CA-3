package com.dragand.spring_tutorial.webpatternsca3.persistence;

import com.dragand.spring_tutorial.webpatternsca3.business.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for UserDaoImpl
 *
 * @Author Jo Art Mahilaga
 */
public class UserDAOImplTest {

    private static User user1, guyToBeDeleted, newUser, newUser2;
    private static UserDaoImpl userDao;

    /**
     * Initialize DAO instance and sample test data.
     */
    @BeforeAll
    public static void init() {
        userDao = new UserDaoImpl("database-test.properties");

        // Sample users for testing
        user1 = User.builder()
                .firstName("John")
                .lastName("Doe")
                .userName("johndoe")
                .password("password123")
                .userID(1)
                .build();

        guyToBeDeleted = User.builder()
                .firstName("Guy")
                .lastName("ToBeDeleted")
                .userName("guytobedeleted")
                .password("hashed_password_3")
                .userID(3)
                .build();

        newUser = User.builder()
                .firstName("New")
                .lastName("User")
                .userName("newuser")
                .password("hashed_password_new")
                .userID(4)
                .build();

        newUser2 = User.builder()
                .firstName("New")
                .lastName("User")
                .userName("newuser2")
                .password("hashed_password_new2")
                .userID(5)
                .build();
    }

    /**
     * Clean up test data after all tests are executed.
     */
    @AfterAll
    public static void cleanUp() {
        userDao.deleteUser(user1);
        userDao.deleteUser(guyToBeDeleted);
        userDao.deleteUser(newUser);
        userDao.deleteUser(newUser2);
    }

    /**
     * Test adding a valid user.
     */
    @Test
    public void testAddUser_Success() {
        boolean result = userDao.addUser(newUser);
        assertTrue(result, "Adding a valid user should succeed.");
    }

    /**
     * Test adding a null user, expecting failure.
     */
    @Test
    public void testAddUser_Failure_NullUser() {
        boolean result = userDao.addUser(null);
        assertFalse(result, "Adding a null user should fail.");
    }

    /**
     * Test retrieving the password of an existing user.
     */
    @Test
    public void testGetPasswordByUserName_UserExists() {
        userDao.addUser(user1); // Ensure user exists
        String password = userDao.getPasswordByUserName(user1.getUserName());
        assertEquals(user1.getPassword(), password, "Password should match for the existing user.");
    }

    /**
     * Test retrieving the password for a non-existent user.
     */
    @Test
    public void testGetPasswordByUserName_NotFound() {
        String password = userDao.getPasswordByUserName("nonexistentuser");
        assertNull(password, "Password should be null for a nonexistent user.");
    }

    /**
     * Test retrieving an existing user by username.
     */
    @Test
    public void testGetUserByName_UserExists() {
        userDao.addUser(user1); // Ensure user exists
        User user = userDao.getUserByName(user1.getUserName());
        assertNotNull(user, "User should not be null for an existing user.");
        assertEquals(user1, user, "User data should match the one added.");
    }

    /**
     * Test retrieving a user by a non-existent username.
     */
    @Test
    public void testGetUserByName_NotFound() {
        User user = userDao.getUserByName("nonexistentuser");
        assertNull(user, "User should be null for a nonexistent username.");
    }

    /**
     * Test deleting an existing user.
     */
    @Test
    public void testDeleteUser_Success() {
        userDao.addUser(guyToBeDeleted); // Ensure user exists
        boolean result = userDao.deleteUser(guyToBeDeleted);
        assertTrue(result, "Deleting an existing user should succeed.");
    }

    /**
     * Test deleting a null user, expecting failure.
     */
    @Test
    public void testDeleteUser_Failure_NullUser() {
        boolean result = userDao.deleteUser(null);
        assertFalse(result, "Deleting a null user should fail.");
    }

    /**
     * Test deleting a user with an invalid ID, expecting failure.
     */
    @Test
    public void testDeleteUser_Failure_InvalidID() {
        User invalidUser = User.builder()
                .firstName("Invalid")
                .lastName("User")
                .userName("invaliduser")
                .password("hashed_password_invalid")
                .userID(-1)
                .build();

        boolean result = userDao.deleteUser(invalidUser);
        assertFalse(result, "Deleting a user with an invalid ID should fail.");
    }

    /**
     * Test checking if an existing username exists in the database.
     */
    @Test
    public void testExistsByUserName_UserExists() {
        userDao.addUser(newUser2); // Ensure user exists
        boolean exists = userDao.existsbyUserName(newUser2.getUserName());
        assertTrue(exists, "The username should exist for a newly added user.");
    }

    /**
     * Test checking if a non-existent username exists in the database.
     */
    @Test
    public void testExistsByUserName_NotFound() {
        boolean exists = userDao.existsbyUserName("nonexistentusername");
        assertFalse(exists, "The username should not exist for a nonexistent user.");
    }

    /**
     * Test retrieving the subscription end date for an existing user.
     */
    @Test
    public void testGetSubscriptionEndDate_UserExists() {
        userDao.addUser(user1); // Ensure user exists
        LocalDate subscriptionEndDate = userDao.getSubscriptionEndDate(user1.getUserID());
        assertNull(subscriptionEndDate, "Subscription end date should be null if not set.");
    }

    /**
     * Test updating the subscription end date for an existing user.
     */
    @Test
    public void testUpdateSubscriptionEndDate_Success() {
        userDao.addUser(user1); // Ensure user exists
        LocalDate newDate = LocalDate.now().plusYears(1);
        boolean result = userDao.updateSubscriptionEndDate(user1.getUserID(), newDate);
        assertTrue(result, "Updating subscription end date should succeed.");
    }
}
