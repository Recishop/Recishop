package com.example.recishop;

import android.animation.RectEvaluator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recishop.fragments.ShoppingListFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    private Context context;
    private List<Recipe> recipeList;
    private RecyclerViewListener recyclerViewListener;

    public RecipesAdapter(List<Recipe> recipes, Context context) {
        this.recipeList = recipes;
        this.context = context;
        this.recyclerViewListener = recyclerViewListener;
    }

    public void setRecyclerViewListener(RecyclerViewListener recyclerViewListener) {
        this.recyclerViewListener = recyclerViewListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // Goodies
        TextView tvRecipeName;
        ImageView ivRecipeImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRecipeName = itemView.findViewById(R.id.tvRecipeName);
            ivRecipeImage = itemView.findViewById(R.id.ivRecipePicture);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            recyclerViewListener.onItemClick(position);
                        }
                    }
                }
            });
        }

        public void bind(Recipe recipe) {
            // Bind the recipe to the id attributes (aka goodies)
            tvRecipeName.setText(recipe.getName());

            // TODO: Maybe change this to something more relevant?  Like perhaps use the camera intent?
            ivRecipeImage.setImageResource(R.drawable.ic_baseline_fastfood_24);
        }
    }
}
