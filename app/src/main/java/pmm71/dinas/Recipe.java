package pmm71.dinas;

import java.util.ArrayList;

/**
 * Created by St-Ex on 10.12.2017.
 */

class Recipe {
    private String time;
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

    void setTime(String settingTime)
    {
        time = settingTime;
    }

    void setKcal(int settingKcal)
    {
        kcal = settingKcal;
    }
    //endregion

    //region Getter'ы
    String getTime() {
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
}
