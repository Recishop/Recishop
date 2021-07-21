package com.example.recishop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recishop.databinding.ItemIngredientBinding;
import com.example.recishop.databinding.ItemIngredientBindingImpl;
import com.example.recishop.models.Ingredient;

import java.util.List;

/**
 * Adapter for handling a list of ingredients to display in a Recyclerview
 *
 * @author tallt
 */
public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder>{

    /**
     * Context used by the adapter
     */
    private Context context;

    /**
     * List of ingredients to map to RecyclerView
     */
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

    class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * UI Handle to item_ingredient for Ingredient RecyclerView
         */
        private ItemIngredientBindingImpl itemIngredientBinding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemIngredientBinding = DataBindingUtil.findBinding(itemView);
        }

        public void bind(Ingredient ingredient){
//            itemIngredientBinding.tvQuantity.setText(Double.toString(ingredient.getQuantity()));
//            itemIngredientBinding.tvMeasurement.setText(ingredient.getMeasurement());
//            itemIngredientBinding.tvIngredientName.setText(ingredient.getItem());
//            itemIngredientBinding.tvCategory.setText(ingredient.getCategory());
        }
    }
}
