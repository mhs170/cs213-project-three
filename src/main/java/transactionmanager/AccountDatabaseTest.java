package transactionmanager;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Contains unit tests for AccountDatabase
 *
 * @author Mohammed Salama, Dakshal Panicker
 */
public class AccountDatabaseTest {

    @Test
    public void testCloseSuccess() {
        AccountDatabase database = new AccountDatabase(new Account[]{}, 0);
        Profile profile = new Profile("John", "Doe", new Date("1/1/2000"));
        Checking checking = new Checking(profile, 100);

        database.open(checking);
        assertTrue(database.close(checking));
    }

    @Test
    public void testCloseFail() {
        AccountDatabase database = new AccountDatabase(new Account[]{}, 0);
        Profile profile = new Profile("John", "Doe", new Date("1/1/2000"));
        Checking checking = new Checking(profile, 100);

        assertFalse(database.close(checking));
    }
}