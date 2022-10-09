package com.example.mad_assignment_part_a;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mad_assignment_part_a.view_models.PostsViewModel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class LoadPostsTaskHandler implements Runnable
{
    private final Activity uiActivity;
    private final PostsViewModel postsViewModel;
    private final ProgressBar progressBar;


    public LoadPostsTaskHandler(Activity uiActivity, PostsViewModel postsViewModel, ProgressBar progressBar)
    {
        this.uiActivity = uiActivity;
        this.postsViewModel = postsViewModel;
        this.progressBar = progressBar;
    }

    @Override
    public void run()
    {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        LoadPostsTask loadPostsTask = new LoadPostsTask(uiActivity);
        Future<String> loadPostsPlaceholder = executorService.submit(loadPostsTask);
        String loadPostsResult = waitingForPosts(loadPostsPlaceholder);

        if(loadPostsResult != null)
        {
            uiActivity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    postsViewModel.setPostsData(loadPostsResult);
                }
            });
        }
        else
        {
            showError(4, "Load Posts");
        }

        executorService.shutdown();
    }

    public String waitingForPosts(Future<String> loadPostsPlaceholder)
    {
        uiActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        showToast("Loading Posts Start");
        String loadPostsData = null;

        try
        {
            loadPostsData = loadPostsPlaceholder.get(6000, TimeUnit.MILLISECONDS);
            //loadPostsData = loadPostsPlaceholder.get();
        }
        catch(ExecutionException e)
        {
            e.printStackTrace();
            showError(1, "Load Posts");
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            showError(2, "Load Posts");
        }
        catch(TimeoutException e)
        {
            e.printStackTrace();
            showError(3, "Load Posts");
        }

        showToast("Loading Posts Finished");

        uiActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        return loadPostsData;
    }

    public void showToast(String message)
    {
        uiActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(uiActivity, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showError(int code, String taskName)
    {
        if(code == 1)
        {
            showToast(taskName + ": Task Execution Exception");
        }
        else if(code == 2)
        {
            showToast(taskName + ": Task Interrupted Exception");
        }
        else if(code == 3)
        {
            showToast(taskName + ": Task Timeout Exception");
        }
        else
        {
            showToast(taskName + ": Task could not be performed. Try again!");
        }
    }
}