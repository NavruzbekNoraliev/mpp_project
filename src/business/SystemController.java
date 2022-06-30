package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
    public static Auth currentAuth = null;

    public void login(String username, String password) throws LoginException {
        DataAccess da = new DataAccessFacade();
        HashMap<String, User> map = da.readUserMap();
        if (!map.containsKey(username)) {
            throw new LoginException("Username " + username + " not found");
        }
        String passwordFound = map.get(username).getPassword();
        if (!passwordFound.equals(password)) {
            throw new LoginException("Password incorrect");
        }
        currentAuth = map.get(username).getAuthorization();
    }

    @Override
    public List<String> allMemberIds() {
        DataAccess da = new DataAccessFacade();
        List<String> retval = new ArrayList<>();
        retval.addAll(da.readMemberMap().keySet());
        return retval;
    }

    @Override
    public List<String> allBookIds() {
        DataAccess da = new DataAccessFacade();
        List<String> retval = new ArrayList<>();
        retval.addAll(da.readBooksMap().keySet());
        return retval;
    }

    @Override
    public void deleteBook(String isbn) {
        DataAccess da = new DataAccessFacade();
        da.deleteBook(isbn);
    }

    @Override
    public HashMap<String, CheckoutRecordEntry> allCheckouts() {
        DataAccess da = new DataAccessFacade();

        List<CheckoutRecordEntry> retval = new ArrayList<>();
        if (!da.readCheckoutRecordMap().values().isEmpty()) {
            retval.addAll(da.readCheckoutRecordMap().values());
        }
        return da.readCheckoutRecordMap();
    }

}
