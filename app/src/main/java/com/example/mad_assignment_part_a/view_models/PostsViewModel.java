package com.example.mad_assignment_part_a.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PostsViewModel extends ViewModel
{
    public MutableLiveData<String> postsData;

    public PostsViewModel()
    {
        postsData = new MutableLiveData<String>();
    }

    public String getPostsData()
    {
        return postsData.getValue();
    }

    public void setPostsData(String value)
    {
        postsData.postValue(value);
    }
}
