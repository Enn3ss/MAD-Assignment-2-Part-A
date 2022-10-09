package com.example.mad_assignment_part_a.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mad_assignment_part_a.LoadPostsTaskHandler;
import com.example.mad_assignment_part_a.R;
import com.example.mad_assignment_part_a.data.PostData;
import com.example.mad_assignment_part_a.data.UserData;
import com.example.mad_assignment_part_a.view_models.PostsViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserDetailsFragment extends Fragment
{
    private Activity uiActivity;
    private List<UserData> users;
    private UserData user;
    private Context context;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserDetailsFragment()
    {
        // Required empty public constructor
    }

    public UserDetailsFragment(Activity uiActivity, List<UserData> users, UserData user, Context context)
    {
        this.uiActivity = uiActivity;
        this.users = users;
        this.user = user;
        this.context = context;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserDetailsFragment newInstance(String param1, String param2)
    {
        UserDetailsFragment fragment = new UserDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_details, container, false);

        TextView nameTextView = (TextView) view.findViewById(R.id.nameTextView);
        TextView usernameTextView = (TextView) view.findViewById(R.id.usernameTextView);
        TextView phoneTextView = (TextView) view.findViewById(R.id.phoneTextView);
        TextView emailTextView = (TextView) view.findViewById(R.id.emailTextView);
        TextView streetTextView = (TextView) view.findViewById(R.id.streetTextView);
        TextView suiteTextView = (TextView) view.findViewById(R.id.suiteTextView);
        TextView cityTextView = (TextView) view.findViewById(R.id.cityTextView);
        TextView zipcodeTextView = (TextView) view.findViewById(R.id.zipcodeTextView);
        TextView geoTextView = (TextView) view.findViewById(R.id.geoTextView);
        TextView websiteTextView = (TextView) view.findViewById(R.id.websiteTextView);
        TextView companyNameTextView = (TextView) view.findViewById(R.id.companyNameTextView);
        TextView catchPhraseTextView = (TextView) view.findViewById(R.id.catchPhraseTextView);
        TextView bsTextView = (TextView) view.findViewById(R.id.bsTextView);
        Button viewPostButton = (Button) view.findViewById(R.id.viewPostsButton);
        Button backButton = (Button) view.findViewById(R.id.backButton);
        ProgressBar progressBar = (ProgressBar) requireActivity().findViewById(R.id.progressBar);

        nameTextView.setText("Name: " + user.getName());
        usernameTextView.setText("Username: " + user.getUsername());
        phoneTextView.setText("Phone: " + user.getPhone());
        emailTextView.setText("Email: " + user.getEmail());
        streetTextView.setText("Street: " + user.getAddress().getStreet());
        suiteTextView.setText("Suite: " + user.getAddress().getSuite());
        cityTextView.setText("City: " + user.getAddress().getCity());
        zipcodeTextView.setText("Zipcode: " + user.getAddress().getZipcode());
        geoTextView.setText("Geo: " + user.getAddress().getGeo().getLat() + ", " + user.getAddress().getGeo().getLng());
        websiteTextView.setText("Website: " + user.getWebsite());
        companyNameTextView.setText("Company Name: " + user.getCompany().getName());
        catchPhraseTextView.setText("Catch Phrase: " + user.getCompany().getCatchPhrase());
        bsTextView.setText("BS: " + user.getCompany().getBs());

        viewPostButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                // Loading posts
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                PostsViewModel postsViewModel = new ViewModelProvider(requireActivity(), (ViewModelProvider.Factory) new ViewModelProvider.NewInstanceFactory()).get(PostsViewModel.class);
                LoadPostsTaskHandler loadPostsTaskHandler = new LoadPostsTaskHandler(uiActivity, postsViewModel, progressBar);
                executorService.execute(loadPostsTaskHandler);

                postsViewModel.postsData.observe(getViewLifecycleOwner(), new Observer<String>()
                {
                    @Override
                    public void onChanged(String s)
                    {
                        try
                        {
                            // Parsing posts into list
                            String postsData = postsViewModel.getPostsData();
                            JSONArray jList = new JSONArray(postsData);
                            List<PostData> posts = new ArrayList<>();

                            for(int i = 0; i < jList.length(); i++)
                            {
                                JSONObject jObject = jList.getJSONObject(i);
                                PostData post = new PostData(jObject.getInt("userId"), jObject.getInt("id"), jObject.getString("title"), jObject.getString("body"));
                                posts.add(post);
                            }

                            // Creating posts fragment
                            FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                            PostsRecyclerFragment recyclerFragment = new PostsRecyclerFragment(uiActivity, users, posts, user, context);
                            fm.beginTransaction().replace(R.id.fragment_container, recyclerFragment).commit();
                        }
                        catch(JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Going back to list of users fragment
                FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                UsersRecyclerFragment recyclerFragment = new UsersRecyclerFragment(uiActivity, users);
                fm.beginTransaction().replace(R.id.fragment_container, recyclerFragment).commit();
            }
        });

        return view;
    }
}