package com.example.mad_assignment_part_a.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mad_assignment_part_a.R;
import com.example.mad_assignment_part_a.data.UserData;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserDetailsFragment extends Fragment
{
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

    public UserDetailsFragment(List<UserData> users, UserData user, Context context)
    {
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

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                UsersRecyclerFragment recyclerFragment = new UsersRecyclerFragment(users);
                fm.beginTransaction().replace(R.id.fragment_container, recyclerFragment).commit();
            }
        });

        return view;
    }
}