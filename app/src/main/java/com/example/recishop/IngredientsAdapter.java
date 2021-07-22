package com.example.recishop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recishop.databinding.ItemIngredientBinding;
import com.example.recishop.models.RecishopIngredient;

import java.util.List;

/**
 * Adapter for handling a list of ingredients to display in a Recyclerview
 *
 * @author tallt
 */
public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ShoppingListViewHolder>{

    /**
     * Context used by the adapter
     */
    private Context context;

    /**
     * List of ingredients to map to RecyclerView
     */
    private List<RecishopIngredient> recishopIngredientList;

    public IngredientsAdapter(Context context, List<RecishopIngredient> recishopIngredientList){
        this.context = context;
        this.recishopIngredientList = recishopIngredientList;
    }

    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemIngredientBinding binding = ItemIngredientBinding.inflate(inflater, parent, false);
        return new ShoppingListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListViewHolder holder, int position) {
        RecishopIngredient recishopIngredient = recishopIngredientList.get(position);
        holder.bind(recishopIngredient);
    }

    @Override
    public int getItemCount() {
        return recishopIngredientList.size();
    }

    public class ShoppingListViewHolder extends RecyclerView.ViewHolder {

        /**
         * UI Handle to item_ingredient for ParseIngredient RecyclerView
         */
        private final ItemIngredientBinding itemIngredientBinding;

        public ShoppingListViewHolder(ItemIngredientBinding itemIngredientBinding) {
            super(itemIngredientBinding.getRoot());
            this.itemIngredientBinding = itemIngredientBinding;
        }


        public void bind(RecishopIngredient recishopIngredient){
//            itemIngredientBinding.tvQuantity.setText(Double.toString(recishopIngredient.getQuantity()));
//            itemIngredientBinding.tvMeasurement.setText(recishopIngredient.getIngredientMeasurement().label);
//            itemIngredientBinding.tvIngredientName.setText(recishopIngredient.getIngredientName());
//            itemIngredientBinding.tvCategory.setText(recishopIngredient.getItemCategory().label);\
            itemIngredientBinding.setRecishopIngredient(recishopIngredient);
            itemIngredientBinding.executePendingBindings();
        }
    }
}
