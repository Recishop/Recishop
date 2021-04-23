package com.example.recishop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recishop.models.Ingredient;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder>{

    private Context context;
    private List<Ingredient> ingredients;

    public IngredientsAdapter(Context context, List<Ingredient> ingredients){
        this.context = context;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_ingredient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.bind(ingredient);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvQty;
        private TextView tvIngrName;
        private TextView tvMeas;
        private TextView tvCategory;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvQty = itemView.findViewById(R.id.tvQty);
            tvMeas = itemView.findViewById(R.id.tvMeas);
            tvIngrName = itemView.findViewById(R.id.tvIngrName);
            tvCategory = itemView.findViewById(R.id.tvCategory);
        }

        public void bind(Ingredient ingredient){
            tvQty.setText(Double.toString(ingredient.getQuantity()));
            tvMeas.setText(ingredient.getMeasurement());
            tvIngrName.setText(ingredient.getItem());
            tvCategory.setText(ingredient.getCategory());
        }
    }
}
