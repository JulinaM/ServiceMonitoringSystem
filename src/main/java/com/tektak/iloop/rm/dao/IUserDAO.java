package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rm.model.IUser;

/**
 * Created by tektak on 7/2/14.
 */
public interface IUserDAO {
    public Integer putUser(IUser user);

    public void fetchUser(String userId);

    public void editUser(String userId);

    public void removeUser(String userId);

}
