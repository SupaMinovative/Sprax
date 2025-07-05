package com.minovative.sprax;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LevelCardAdapter extends RecyclerView.Adapter<LevelCardAdapter.LevelCardViewHolder> {

    private List<Level> levelList;
    private RecyclerView recyclerView;
    private Context context;


    public LevelCardAdapter(List<Level> levelList, RecyclerView recyclerView, Context context) {
        this.levelList = levelList;
        this.recyclerView = recyclerView;
        this.context = context;
    }

    @NonNull
    @Override
    public LevelCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_level_view, parent, false);
        return new LevelCardAdapter.LevelCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelCardViewHolder holder,int position) {
        Level currentLevel = levelList.get(position);
        holder.levelText.setText("Level " + currentLevel.getLevelName());
        holder.levelDifficulty.setText(currentLevel.getLevelDif());
        startVocabActivity(holder.playBtn,currentLevel.getLevelClassName(),context);

    }
    private void startVocabActivity(ImageView item, String activityClassName, Context activityName) {

        item.setOnClickListener(view -> {

            Class<?> activityClass = null;

            try {
                activityClass = Class.forName("com.minovative.sprax." + activityClassName);

            } catch (ClassNotFoundException e) {

                throw new RuntimeException(e);
            }

            Intent intent = new Intent(activityName, activityClass);
            activityName.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {

        return levelList.size();
    }

    public static class LevelCardViewHolder extends RecyclerView.ViewHolder {
        TextView levelText;
        TextView levelDifficulty;
        ImageView playBtn;

        public LevelCardViewHolder(@NonNull View itemView) {

            super(itemView);
            levelText = itemView.findViewById(R.id.levelText);
            levelDifficulty = itemView.findViewById(R.id.levelDifficulty);
            playBtn = itemView.findViewById(R.id.playBtn);

        }

    }

}
