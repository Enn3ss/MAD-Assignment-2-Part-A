package com.example.mad_assignment_part_a.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mad_assignment_part_a.R;
import com.example.mad_assignment_part_a.data.UserData;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecyclerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecyclerFragment extends Fragment
{
    private List<UserData> users;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecyclerFragment()
    {
        // Required empty public constructor
    }

    public RecyclerFragment(List<UserData> users)
    {
        this.users = users;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecyclerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecyclerFragment newInstance(String param1, String param2)
    {
        RecyclerFragment fragment = new RecyclerFragment();
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
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);
        RecyclerView rv = view.findViewById(R.id.recView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        UserAdapter adapter = new UserAdapter(users, getContext());
        rv.setAdapter(adapter);

        return view;
    }

    private class UserViewHolder extends RecyclerView.ViewHolder // ViewHolder inner class
    {
        public TextView usernameTextView;

        public UserViewHolder(@NonNull View itemView)
        {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.username);
        }
    }

    private class UserAdapter extends RecyclerView.Adapter<UserViewHolder> // Adapter inner class
    {
        List<UserData> data;
        Context context;

        public UserAdapter(List<UserData> data, Context context)
        {
            this.data = data;
            this.context = context;
        }

        @NonNull
        @Override
        public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.each_username_view, parent, false);
            UserViewHolder userViewHolder = new UserViewHolder(view);

            return userViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull UserViewHolder holder, int position)
        {
            UserData user = data.get(position);
            holder.usernameTextView.setText(user.getUsername());

            holder.usernameTextView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    // Fragment
                    FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                    UserDetailsFragment userDetailsFragment = new UserDetailsFragment(data, user, context);
                    fm.beginTransaction().replace(R.id.fragment_container, userDetailsFragment).commit();
                }
            });
        }

        @Override
        public int getItemCount()
        {
            return data.size();
        }
    }
}