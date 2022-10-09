package com.example.mad_assignment_part_a;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mad_assignment_part_a.data.Address;
import com.example.mad_assignment_part_a.data.Company;
import com.example.mad_assignment_part_a.data.Geo;
import com.example.mad_assignment_part_a.data.UserData;
import com.example.mad_assignment_part_a.fragments.UsersRecyclerFragment;
import com.example.mad_assignment_part_a.view_models.UsersViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity
{
    private UsersViewModel usersViewModel;
    private List<UserData> users;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usersViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) new ViewModelProvider.NewInstanceFactory()).get(UsersViewModel.class);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        // Setting up Load Users task
        LoadUsersTaskHandler loadUsersTaskHandler = new LoadUsersTaskHandler(MainActivity.this, usersViewModel, progressBar);
        executorService.execute(loadUsersTaskHandler);

        // Observe when the UsersViewModel is updated i.e. user data is taken from website
        usersViewModel.usersData.observe(this, new Observer<String>()
        {
            @Override
            public void onChanged(String s)
            {
                try
                {
                    // Parsing users into list
                    String userData = usersViewModel.getUsersData();
                    JSONArray jList = new JSONArray(userData);
                    users = new ArrayList<>();

                    for (int i = 0; i < jList.length(); i++)
                    {
                        JSONObject jObject = jList.getJSONObject(i);
                        Geo geo = new Geo(jObject.getJSONObject("address").getJSONObject("geo").getDouble("lat"), jObject.getJSONObject("address").getJSONObject("geo").getDouble("lng"));
                        Company company = new Company(jObject.getJSONObject("company").getString("name"), jObject.getJSONObject("company").getString("catchPhrase"), jObject.getJSONObject("company").getString("bs"));
                        Address address = new Address(jObject.getJSONObject("address").getString("street"), jObject.getJSONObject("address").getString("suite"), jObject.getJSONObject("address").getString("city"), jObject.getJSONObject("address").getString("zipcode"), geo);
                        UserData user = new UserData(jObject.getInt("id"), jObject.getString("name"), jObject.getString("username"), jObject.getString("email"), address, jObject.getString("phone"), jObject.getString("website"), company);
                        users.add(user);
                    }

                    Toast.makeText(MainActivity.this, "Users Loaded", Toast.LENGTH_LONG).show();

                    // Creating user fragment
                    FragmentManager fm = getSupportFragmentManager();
                    UsersRecyclerFragment recyclerFragment = (UsersRecyclerFragment) fm.findFragmentById(R.id.fragment_container);

                    if(recyclerFragment == null)
                    {
                        recyclerFragment = new UsersRecyclerFragment(MainActivity.this, users);
                        fm.beginTransaction().add(R.id.fragment_container, recyclerFragment).commit();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}