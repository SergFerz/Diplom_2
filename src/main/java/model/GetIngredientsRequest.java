package model;

import java.util.ArrayList;

public class GetIngredientsRequest {
    private ArrayList<Ingredient> data;

    public GetIngredientsRequest(ArrayList<Ingredient> data) {
        this.data = data;
    }

    public ArrayList<Ingredient> getData() {
        return data;
    }
}