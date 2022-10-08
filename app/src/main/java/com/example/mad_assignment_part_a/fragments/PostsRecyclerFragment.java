package com.example.mad_assignment_part_a.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mad_assignment_part_a.R;
import com.example.mad_assignment_part_a.data.PostData;
import com.example.mad_assignment_part_a.data.UserData;
import com.example.mad_assignment_part_a.view_models.PostsViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostsRecyclerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostsRecyclerFragment extends Fragment
{
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

    public PostsRecyclerFragment(List<UserData> users, UserData user, Context context)
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

        String test2 = "[\n" +
                "  {\n" +
                "    \"userId\": 1,\n" +
                "    \"id\": 1,\n" +
                "    \"title\": \"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\n" +
                "    \"body\": \"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"userId\": 1,\n" +
                "    \"id\": 2,\n" +
                "    \"title\": \"qui est esse\",\n" +
                "    \"body\": \"est rerum tempore vitae\\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\\nqui aperiam non debitis possimus qui neque nisi nulla\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"userId\": 1,\n" +
                "    \"id\": 3,\n" +
                "    \"title\": \"ea molestias quasi exercitationem repellat qui ipsa sit aut\",\n" +
                "    \"body\": \"et iusto sed quo iure\\nvoluptatem occaecati omnis eligendi aut ad\\nvoluptatem doloribus vel accusantium quis pariatur\\nmolestiae porro eius odio et labore et velit aut\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"userId\": 1,\n" +
                "    \"id\": 4,\n" +
                "    \"title\": \"eum et est occaecati\",\n" +
                "    \"body\": \"ullam et saepe reiciendis voluptatem adipisci\\nsit amet autem assumenda provident rerum culpa\\nquis hic commodi nesciunt rem tenetur doloremque ipsam iure\\nquis sunt voluptatem rerum illo velit\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"userId\": 1,\n" +
                "    \"id\": 5,\n" +
                "    \"title\": \"nesciunt quas odio\",\n" +
                "    \"body\": \"repudiandae veniam quaerat sunt sed\\nalias aut fugiat sit autem sed est\\nvoluptatem omnis possimus esse voluptatibus quis\\nest aut tenetur dolor neque\"\n" +
                "  }\n" +
                "]";
        try
        {
            String postsData = test2;
            JSONArray jList = new JSONArray(postsData);
            posts = new ArrayList<>();

            for(int i = 0; i < jList.length(); i++)
            {
                JSONObject jObject = jList.getJSONObject(i);
                PostData post = new PostData(jObject.getInt("userId"), jObject.getInt("id"), jObject.getString("title"), jObject.getString("body"));
                posts.add(post);
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }

        Button backButton = (Button) view.findViewById(R.id.postBackButton);
        RecyclerView rv = view.findViewById(R.id.postsRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        Adapter adapter = new Adapter(posts);
        rv.setAdapter(adapter);


        //Toast.makeText(getContext(), posts.get(0).getTitle(), Toast.LENGTH_SHORT).show();

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                UserDetailsFragment userDetailsFragment = new UserDetailsFragment(users, user, context);
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
        List<PostData> data;
        //Context context;

        public Adapter(List<PostData> data)
        {
            this.data = data;
            //this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.each_post_view, parent, false);
            ViewHolder postViewHolder = new ViewHolder(view);

            return postViewHolder;
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