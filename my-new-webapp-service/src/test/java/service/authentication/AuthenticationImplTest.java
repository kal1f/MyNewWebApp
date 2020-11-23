package service.authentication;

import database.entity.Customer;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

public class AuthenticationImplTest {

    Map<String, Customer> customerProfileData;

    @Before
    public void setup(){
        customerProfileData = new ConcurrentHashMap<>();
        customerProfileData.put("HGDALN21SCWR21", new Customer("lasd2cz2", "12dz@wfc3", "maks", 101 ));
        customerProfileData.put("2131KKSA2CZXCW", new Customer("bhnf9bh", "zfe@awfg", "sadw", 212 ));
        customerProfileData.put("123CXLLD2FVLKJ", new Customer("asd21xaqw", "qwzw1!wfc3", "lasca", 121 ));
    }

    @Test
    public void returnFalseWithExistingSession() {
        Authentication authentication = new AuthenticationImpl(customerProfileData);
        boolean isSessionPresent = authentication.isSessionPresent("HGDALN21SCWR21");
        assertTrue(isSessionPresent);
    }

    @Test
    public void returnTrueWithExistingSession() {
        Authentication authentication = new AuthenticationImpl(customerProfileData);
        boolean isSessionPresent = authentication.isSessionPresent("12KNJ31D2VLKJ90");
        assertFalse(isSessionPresent);
    }

    @Test
    public void returnTrueWithAuthenticatedCustomer() {
        Authentication authentication = new AuthenticationImpl(customerProfileData);
        boolean authenticated = authentication.isUserAuthenticated("2131KKSA2CZXCW", new Customer("bhnf9bh", "zfe@awfg", "sadw", 212 ));
        assertTrue(authenticated);
    }

    @Test
    public void returnFalseWithNotAuthenticatedCustomer() {
        Authentication authentication = new AuthenticationImpl(customerProfileData);
        boolean authenticated = authentication.isUserAuthenticated("2131KKRTGBTVSXCW", new Customer("bhnf9bh", "zfe@awfg", "sadw", 212 ));
        assertFalse(authenticated);
    }

    @Test
    public void returnFalseWithNullCustomerAndPresentSession() {
        Authentication authentication = new AuthenticationImpl(customerProfileData);
        boolean authenticated = authentication.isUserAuthenticated("2131KKSA2CZXCW", null);
        assertFalse(authenticated);
    }

    @Test
    public void returnFalseWithNullSessionAndPresentCustomer() {
        Authentication authentication = new AuthenticationImpl(customerProfileData);
        boolean authenticated = authentication.isUserAuthenticated(null, new Customer("bhnf9bh", "zfe@awfg", "sadw", 212 ));
        assertFalse(authenticated);
    }

    @Test
    public void returnFalseWithNullSessionAndNullCustomer() {
        Authentication authentication = new AuthenticationImpl(customerProfileData);
        boolean authenticated = authentication.isUserAuthenticated(null, new Customer("bhnf9bh", "zfe@awfg", "sadw", 212 ));
        assertFalse(authenticated);
    }

    @Test
    public void returnTrueIfSessionPresent(){
        Authentication authentication = new AuthenticationImpl(customerProfileData);
        boolean present = authentication.isSessionPresent("123CXLLD2FVLKJ");
        assertTrue(present);
    }

    @Test
    public void returnFalseIfSessionNotPresent(){
        Authentication authentication = new AuthenticationImpl(customerProfileData);
        boolean present = authentication.isSessionPresent("YTYWNcCXLdas32");
        assertFalse(present);
    }

    @Test
    public void returnFalseIfSessionNull(){
        Authentication authentication = new AuthenticationImpl(customerProfileData);
        boolean present = authentication.isSessionPresent(null);
        assertFalse(present);
    }

    @Test
    public void returnNotNullWithExistedSessionId() {
        Authentication authentication = new AuthenticationImpl(customerProfileData);
        Customer customer = authentication.getCustomer("HGDALN21SCWR21");
        assertNotNull(customer);
    }

    @Test
    public void returnNullWithNotExistedSessionId() {
        Authentication authentication = new AuthenticationImpl(customerProfileData);
        Customer customer = authentication.getCustomer("HGDALLDA322DWS");
        assertNull(customer);
    }

    @Test
    public void setCustomerWithNullSessionIdNotAddNewCustomerDontChangeSize(){
        Authentication authentication = new AuthenticationImpl(customerProfileData);
        authentication.setCustomer(null, new Customer("bhnf9bh", "zfe@awfg", "sadw", 212 ));
        assertEquals(3, customerProfileData.size());
    }

    @Test
    public void setCustomerWithSessionIdAddNewCustomerChangeSize(){
        Authentication authentication = new AuthenticationImpl(customerProfileData);
        authentication.setCustomer("dHGGH12DC", new Customer("bhnf9bh", "zfe@awfg", "sadw", 212 ));
        assertEquals( 4, customerProfileData.size());
    }

    @Test
    public void setNullCustomerDontChangeSize(){
        Authentication authentication = new AuthenticationImpl(customerProfileData);
        authentication.setCustomer("dHGGH12DC", null);
        assertEquals( 3, customerProfileData.size());
    }

    @Test
    public void removeCustomerByExistingSessionIdChangeSize() {
        Authentication authentication = new AuthenticationImpl(customerProfileData);
        authentication.removeCustomer("2131KKSA2CZXCW");
        assertEquals(2, customerProfileData.size());
    }

    @Test
    public void removeCustomerByNullDontChangeSize() {
        Authentication authentication = new AuthenticationImpl(customerProfileData);
        authentication.removeCustomer(null);

        assertEquals(3, customerProfileData.size());
    }

    @Test
    public void removeCustomerByNotExistingSessionIdDontChangeSize() {
        Authentication authentication = new AuthenticationImpl(customerProfileData);
        authentication.removeCustomer("JHDBCGW21CW");
        assertEquals(3, customerProfileData.size());
    }
}