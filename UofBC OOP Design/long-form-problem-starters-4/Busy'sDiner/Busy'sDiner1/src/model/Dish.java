package model;

import java.util.ArrayList;
import java.util.List;

public class Dish {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    private String description;
    private List<String> ingredients = new ArrayList<>();
    private String recipe;

    public Dish(String name) {
        this.name = name;
        this.description = "";
        this.ingredients = new ArrayList<>();
        this.recipe = "";
    }

    public Dish(String name, String description, List<String> ingredients, String recipe) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.recipe = recipe;
    }

}