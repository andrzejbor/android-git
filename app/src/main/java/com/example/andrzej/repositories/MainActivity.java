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

public class MainActivity extends AppCompatActivity implements RepositoriesAdapter.RepositoryClickAction {

    private RecyclerView mRepoList;
    private RepositoriesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Ustawiamy co ma pokazać się na ekranie na tym oknie (Activity)

        // Tworzymy obiekt adaptera, żeby uzupełnić go za chwilę danymi i przekazać do RecyclerView
        // w celu wyświetlenia listy
        mAdapter = new RepositoriesAdapter();
        //Mówimy adapterowi, że bieżący obiekt (this) reaguje na zdarzenia kliknięcia
        mAdapter.setmClickListener(this);

        // Tworzymy przykładową listę obiektów do pokazania na ekranie
        List<GithubRepositories> repos = new LinkedList<>();

        // Obiekt testowy 1
        GithubRepositories r1 = new GithubRepositories();
        r1.setName("Repo 1");
        r1.setHtmlUrl("http://www.wp.pl");
        repos.add(r1);  // Dodanie Repo 1 do kolekcji


        // Obiekt testowy 2
        r1 = new GithubRepositories();
        r1.setName("Repo 2");
        r1.setHtmlUrl("http://www.filmweb.pl");
        repos.add(r1); // Dodanie Repo 2 do kolekcji
        // Przekazujemy listę danych do Adaptera, aby te dane wyświetlić na ekranie !
        mAdapter.setmData(repos);


        // Uzyskujemy dostęp do kontrolki RecyclerView, ponieważ potrzebujemy go skonfigurować
        mRepoList = (RecyclerView) findViewById(R.id.activity_main);
        // Mówimy dla RecyclerView w jakis sposób mają być umieszczone elementy na liście :
        // tutaj używamy klas z Androida, nie musimy implementować własnych
        // (najczęściej LinearLayoutManager - pionowy układ)
        mRepoList.setLayoutManager(new LinearLayoutManager(this));
        // Ustawiamy Adapter na RecyclerView, żeby wiedział co ma wyświetlić.
        mRepoList.setAdapter(mAdapter);
    }

    @Override
    public void onClick(GithubRepositories repositories) {
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(repositories.getHtmlUrl()));
        startActivity(websiteIntent);
    }
}
