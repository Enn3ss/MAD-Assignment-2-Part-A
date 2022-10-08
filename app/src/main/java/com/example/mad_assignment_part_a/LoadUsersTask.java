package com.example.mad_assignment_part_a;

import android.app.Activity;

import java.net.HttpURLConnection;
import java.util.concurrent.Callable;

public class LoadUsersTask implements Callable<String>
{
    private String url;
    private RemoteUtilities remoteUtilities;
    private Activity uiActivity;

    public LoadUsersTask(Activity uiActivity)
    {
        url = "https://jsonplaceholder.typicode.com/users";
        remoteUtilities = RemoteUtilities.getInstance(uiActivity);
        this.uiActivity = uiActivity;
    }

    @Override
    public String call() throws Exception
    {
        String response = null;
        HttpURLConnection connection = remoteUtilities.openConnection(url);

        if(connection != null)
        {
            if(remoteUtilities.isConnectionOkay(connection))
            {
                response = remoteUtilities.getResponseString(connection);
                connection.disconnect();

                try
                {
                    Thread.sleep(3000);
                }
                catch(Exception e)
                {

                }
            }
        }

        return response;
    }
}
