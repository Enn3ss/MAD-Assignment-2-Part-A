package com.example.mad_assignment_part_a;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UsersViewModel extends ViewModel
{
    public MutableLiveData<String> usersData;

    public UsersViewModel()
    {
            usersData = new MutableLiveData<String>();
    }

    public String getUsersData()
    {
        return usersData.getValue();
    }

    public void setUsersData(String value)
    {
        usersData.postValue(value);
    }
}
