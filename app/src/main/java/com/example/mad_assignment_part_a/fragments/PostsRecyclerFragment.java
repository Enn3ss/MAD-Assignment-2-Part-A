package com.example.mad_assignment_part_a.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad_assignment_part_a.R;
import com.example.mad_assignment_part_a.data.PostData;
import com.example.mad_assignment_part_a.data.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostsRecyclerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostsRecyclerFragment extends Fragment
{
    private Activity uiActivity;
    private List<PostData> posts;
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

    public PostsRecyclerFragment()
    {
        // Required empty public constructor
    }

    public PostsRecyclerFragment(Activity uiActivity, List<UserData> users, List<PostData> posts, UserData user, Context context)
    {
        this.uiActivity = uiActivity;
        this.users = users;
        this.posts = posts;
        this.user = user;
        this.context = context;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostsRecyclerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostsRecyclerFragment newInstance(String param1, String param2)
    {
        PostsRecyclerFragment fragment = new PostsRecyclerFragment();
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
        View view = inflater.inflate(R.layout.fragment_posts_recycler, container, false);

        // Setting up fragment GUI
        Button backButton = (Button) view.findViewById(R.id.postBackButton);
        RecyclerView rv = view.findViewById(R.id.postsRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        // Getting posts made by current user
        List<PostData> userPosts = new ArrayList<>();

        for(PostData post : posts)
        {
            if(post.getUserId() == user.getId())
            {
                userPosts.add(post);
            }
        }

        Adapter adapter = new Adapter(userPosts);
        rv.setAdapter(adapter);

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                UserDetailsFragment userDetailsFragment = new UserDetailsFragment(uiActivity, users, user, context);
                fm.beginTransaction().replace(R.id.fragment_container, userDetailsFragment).commit();
            }
        });

        return view;
    }

    private class ViewHolder extends RecyclerView.ViewHolder // ViewHolder inner class
    {
        public TextView titleTextView;
        public TextView bodyTextView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            bodyTextView = itemView.findViewById(R.id.bodyTextView);
        }
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> // Adapter inner class
    {
        public List<PostData> data;

        public Adapter(List<PostData> data)
        {
            this.data = data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.each_post_view, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position)
        {
            PostData post = data.get(position);
            holder.titleTextView.setText(post.getTitle());
            holder.bodyTextView.setText(post.getBody());
        }

        @Override
        public int getItemCount()
        {
            return data.size();
        }
    }
}