package pmm71.dinas;

import android.os.Build;

import java.util.ArrayList;

class Recipe {
    private int time;
    private int kcal;
    private ArrayList<String> ingredients;
    private ArrayList<String> images;
    private ArrayList<String> steps;

    Recipe() {
        ingredients = new ArrayList<>();
        images = new ArrayList<>();
        steps = new ArrayList<>();
    }

    //region Setter'ы (или вроде того)
    void AddIngredient(String ingredient)
    {
        ingredients.add(ingredient);
    }

    void AddImage(String image)
    {
        images.add(image);
    }

    void AddStep(String step)
    {
        steps.add(step);
    }

    void setTime(int settingTime)
    {
        time = settingTime;
    }

    void setKcal(int settingKcal)
    {
        kcal = settingKcal;
    }
    //endregion

    //region Getter'ы
    int getTime() {
        return time;
    }

    int getKcal() {
        return kcal;
    }

    String getIngredient(int index) {
        return ingredients.get(index);
    }

    int getAmountIngredients() {
        return ingredients.size();
    }

    int getAmountSteps() {
        return steps.size();
    }

    String getImage(int index) {
        return images.get(index);
    }

    String getStep(int index) {
        return steps.get(index);
    }
    //endregion

    boolean HasIngredient(String searchingIngredient) {
        searchingIngredient.toLowerCase();
        for (String ingredient : ingredients) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (ingredient.toLowerCase().contains(searchingIngredient)) return true;
            }
        }
        return false;
    }
}
