package com.example.andrzej.repositories;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Andrzej on 21.11.2016.
 */

// Ta klasa odpowiada dla widoku listy (RecyclerView) na pytania :
// - ile elementów (getItemCount)
// - jak mają wyglądać (onCreateViewHolder) ?
// - jakie dane mają zawierać (onBindViewHolder) ?
// Jej metody nie są wołane bezpośrednio przez nas, tylko przez komponenty systemu !
public class RepositoriesAdapter extends RecyclerView.Adapter<RepositoriesAdapter.RepositoryViewHolder> {
    // Zmianna w ktorej trzymamy zbiór obiektów, które chcemy wyświetlić na ekranie w postaci listy.
    private List<GithubRepositories> mData = Collections.emptyList();
    private RepositoryClickAction mClickListener;

    public void setmClickListener(RepositoryClickAction mClickListener) {
        this.mClickListener = mClickListener;
    }

    // W związku z tym, że mData jest prywatne - dodaliśmy metodę setData, pozwalającą na ustawienie
    // danych do wyświetlenia.
    public void setmData(List<GithubRepositories> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    // Ta funkcja ma za zadanie stworzyć obiekt widoku pojedyńczego wiersza, czyli odpowiedzieć
    // dla RecyclerView na pytanie jak mają wyglądać jego elementy !
    // Pytanie : jak wygląda ?
    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // LayoutInflater - komponent do tworzenia obiektów View na podstawie plików XML (R.layout.XXX)
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // Metoda inflate tworzy obiekt View na podstawie podanego pliku XML
        // Drugi jej parametr to konterner względem którego ma wymiarować nowo tworzony widok
        // Trzeci parametr mówi czy chcemy nowo tworzony widok dodać od razu do parent.
        View rowView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        return new RepositoryViewHolder(rowView);
    }

    // Funkcja ma za zadanie wypełnić pojedyńczy utworzony wcześniej wiersz na liście
    // danymi na podstawie parametru (int position).
    // Pytanie : jakie dane ?
    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {
        //1. Pobierz dane z zadanej pozycji (parametr position)
        GithubRepositories repositories = mData.get(position);

        //2. Uzupełnij widok wiersza (parametr holder) danymi
        holder.mLabel.setText(repositories.getName());
        holder.mRepositories = repositories;
    }

    // Mówi dla RecyclerView ile elementów ma zostać wyświetlone dla użytkownika na ekranie.
    // Pytanie : ile elementów / wierszy ?
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // ViewHolder zapewnia nam możliwość wyszukiwania elementów wiersza na liście, tylko raz
    // podczas tworzenia widoku tego wiersza (onCreateViewHolder), tak żebyśmy nie musieli robić tego
    // za każdym razem w funkcji onBindViewHolder.
    public class RepositoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(android.R.id.text1)
        TextView mLabel;
        GithubRepositories mRepositories;

        public RepositoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @OnClick
        protected void onViewClick() {
            mClickListener.onClick(mRepositories);
        }
    }

    //Ten interfejs definiuje nam sprsób powiadamiania zainteresowanych z zewnątrz o kliknięciach na wiersze
    //reprezentujące konkretne
    public interface RepositoryClickAction {
        void onClick(GithubRepositories repositories);
    }

}
