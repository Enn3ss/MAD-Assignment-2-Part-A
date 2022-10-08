package com.example.mad_assignment_part_a;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BackgroundTaskHandler implements Runnable
{
    private Activity uiActivity;
    private UsersViewModel usersViewModel;
    private ProgressBar progressBar;


    public BackgroundTaskHandler(Activity uiActivity, UsersViewModel usersViewModel, ProgressBar progressBar)
    {
        this.uiActivity = uiActivity;
        this.usersViewModel = usersViewModel;
        this.progressBar = progressBar;
    }

    @Override
    public void run()
    {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        LoadUsersTask loadUsersTask = new LoadUsersTask(uiActivity);
        Future<String> loadUsersPlaceholder = executorService.submit(loadUsersTask);
        String loadUsersResult = waitingForSearch(loadUsersPlaceholder);

        if(loadUsersResult != null)
        {
            uiActivity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    usersViewModel.setUsersData(loadUsersResult);
                }
            });
        }
        else
        {
            showError(4, "Load Users");
        }

        executorService.shutdown();
    }

    public String waitingForSearch(Future<String> loadUsersPlaceholder)
    {
        uiActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        showToast("Search Starts");
        String loadUsersData = null;

        try
        {
            //loadUsersData = loadUsersPlaceholder.get(6000, TimeUnit.MILLISECONDS);
            loadUsersData = loadUsersPlaceholder.get();
        }
        catch(ExecutionException e)
        {
            e.printStackTrace();
            showError(1, "Search");
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            showError(2, "Search");
        }
        /*catch(TimeoutException e)
        {
            e.printStackTrace();
            showError(3, "Search");
        }*/

        showToast("Search Ends");

        uiActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        return loadUsersData;
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
            showToast(taskName + " Task Execution Exception");
        }
        else if(code == 2)
        {
            showToast(taskName + " Task Interrupted Exception");
        }
        else if(code == 3)
        {
            showToast(taskName + " Task Timeout Exception");
        }
        else
        {
            showToast(taskName + " Task could not be performed. Restart!!");
        }
    }
}
