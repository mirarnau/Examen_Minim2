package edu.upc.dsa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.upc.dsa.models.Repo;
import edu.upc.dsa.models.User;
import edu.upc.dsa.ui.main.RecyclerAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoUser extends AppCompatActivity {

    ImageView profileImage;
    TextView userName, followersText, followingText, repositories;
    ProgressBar circularProgress;

    ApiInterface apiInterface;
    RecyclerView recyclerView;

    String savedUserName;

    public static final String API_URL = "https://api.github.com/";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);

        this.profileImage = findViewById(R.id.profileImageID);
        this.userName = findViewById(R.id.nameID);
        this.followersText = findViewById(R.id.textFollowersID);
        this.followingText = findViewById(R.id.textFollowingID);
        this.repositories = findViewById(R.id.repositoriesID);
        this.circularProgress = findViewById(R.id.progressBarInfoPageID);

        circularProgress.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
        recyclerView = findViewById(R.id.recyclerID);

        SharedPreferences sharedPrefer = getSharedPreferences("user", Context.MODE_PRIVATE);
        this.savedUserName = sharedPrefer.getString("USER", null);

        getInfoUser(this.savedUserName);
        getReposUser(this.savedUserName);

    }

    public void initializeRecyclerView(List<Repo> listRepos){
        RecyclerAdapter myAdapter= new RecyclerAdapter(this, listRepos);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

    }


    public void getInfoUser (String user){
        Call<User> call = apiInterface.getInfoUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    Log.d("Profile", "Error code:" + response.code());
                    return;
                }
                userName.setText(response.body().getLogin());
                followersText.setText(response.body().getFollowers());
                followingText.setText(response.body().getFollowing());
                circularProgress.setVisibility(View.GONE);
                String m = "Repositories: " + response.body().getPublic_repos();
                repositories.setText(m);

                Picasso.get().load(response.body().getAvatar_url()).into(profileImage);
                Log.d("Profile", "Successful getUser "+ user);
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(InfoUser.this, "Error in getting response from service", Toast.LENGTH_LONG).show();
                Log.d("Profile", "Error in getting response from service: "+t.getMessage());
            }
        });
    }

    public void getReposUser (String user){
        Call<List<Repo>> call = apiInterface.getReposUser(user);
        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                if(!response.isSuccessful()){
                    Log.d("Profile", "Error code:" + response.code());
                    error();
                    return;
                }

                initializeRecyclerView(response.body());

                Log.d("Profile", "Successful getUser "+ user);
            }
            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Toast.makeText(InfoUser.this, "Error in getting response from service", Toast.LENGTH_LONG).show();
                Log.d("Profile", "Error in getting response from service: "+t.getMessage());
                error();

            }
        });
    }




    public void error() {
        Intent intent = new Intent(this, Error.class);
        startActivity(intent);
    }



    public void backClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
