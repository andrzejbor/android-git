package com.example.andrzej.repositories;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RepositoriesAdapter.RepositoryClickAction {

    @BindView(R.id.activity_main)
    protected RecyclerView mRepoList;
    private RepositoriesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Ustawiamy co ma pokazać się na ekranie na tym oknie (Activity)
        ButterKnife.bind(this);

        // Tworzymy obiekt adaptera, żeby uzupełnić go za chwilę danymi i przekazać do RecyclerView
        // w celu wyświetlenia listy
        mAdapter = new RepositoriesAdapter();
        //Mówimy adapterowi, że bieżący obiekt (this) reaguje na zdarzenia kliknięcia
        mAdapter.setmClickListener(this);


        // Mówimy dla RecyclerView w jakis sposób mają być umieszczone elementy na liście :
        // tutaj używamy klas z Androida, nie musimy implementować własnych
        // (najczęściej LinearLayoutManager - pionowy układ)
        mRepoList.setLayoutManager(new LinearLayoutManager(this));
        // Ustawiamy Adapter na RecyclerView, żeby wiedział co ma wyświetlić.
        mRepoList.setAdapter(mAdapter);

        GithubApi api = GithubApiFactory.getApi();
        api.listRepositories("octocat").enqueue(new Callback<List<GithubRepositories>>() {
            @Override
            public void onResponse(Call<List<GithubRepositories>> call, Response<List<GithubRepositories>> response) {
                List<GithubRepositories> repos = response.body();
                mAdapter.setmData(repos);
            }

            @Override
            public void onFailure(Call<List<GithubRepositories>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onClick(GithubRepositories repositories) {
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(repositories.getHtmlUrl()));
        startActivity(websiteIntent);
    }
}
