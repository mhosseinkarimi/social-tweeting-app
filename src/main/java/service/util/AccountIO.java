package service.util;

import java.util.ArrayList;

public interface AccountIO {

    public abstract ArrayList<String> getFollowers(String username);
    public abstract ArrayList<String> getFollowings(String username);



}