package edu.upc.dsa.ui.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import edu.upc.dsa.R;
import edu.upc.dsa.models.Repo;
import edu.upc.dsa.models.User;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    List<Repo> listRepos;
    Context context;

    public RecyclerAdapter(Context context, List<Repo> listRepos){
        this.listRepos = listRepos;
        this.context = context;
    }

    //To create the views.
    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_entry,parent,false);
        return new ViewHolder(view);
    }

    //To insert data into the views.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Repo repo = this.listRepos.get(position);
        holder.title.setText(repo.getName());
        holder.language.setText(repo.getLanguage());


    }

    //Creates the number of views.
    @Override
    public int getItemCount() {
        return this.listRepos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, language;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleID);
            language = itemView.findViewById(R.id.languageID);


        }
    }
}
