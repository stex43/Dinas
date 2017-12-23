package pmm71.dinas;

import android.app.Application;
import android.content.res.Resources;
import android.os.Build;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class OursApplication extends Application {
    public Map<String, Map<String, Recipe>> database;
    public HashSet<String> ingredietns;
    public ArrayList<String> recipeNames;
    private float density;
    Resources resources;

    public void ReadFiles() {
        resources = this.getResources();
        density = getApplicationContext().getResources().getDisplayMetrics().density;
        recipeNames = new ArrayList<>();
        ingredietns = new HashSet<>();

        String[] categories = new String[]{ "Салаты", "Десерты", "Завтраки", "Мясные блюда",
                "Супы", "Напитки", "Морепродукты", "Гарниры", "Выпечка" };
        database = new HashMap<>();
        for (String category : categories)
            database.put(category, new HashMap<String, Recipe>());

        InputStream in;
        BufferedReader br;

        Field[] fields = R.raw.class.getFields();

        for (Field field : fields) {
            int recId = resources.getIdentifier(field.getName(), "raw",
                    this.getPackageName());
            in = resources.openRawResource(recId);
            br = new BufferedReader(new InputStreamReader(in));

            String str;
            Recipe recipe;
            String recipeName;
            String categoryName;

            try {
                recipe = new Recipe();

                //region Составление рецепта
                recipeName = br.readLine();
                recipeNames.add(recipeName.replace("\"", ""));

                str = br.readLine();
                recipe.AddImage(str);

                str = br.readLine();
                recipe.AddImage(str);

                categoryName = br.readLine();

                str = br.readLine();
                recipe.setTime(Integer.parseInt(str));

                str = br.readLine();
                recipe.setKcal(Integer.parseInt(str));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    while (!Objects.equals(str = br.readLine(), "")) {
                        recipe.AddIngredient(str);
                        ingredietns.add(str.split(" — ")[0]);
                    }
                }

                while ((str = br.readLine()) != null) {
                    recipe.AddStep(str);

                    str = br.readLine();
                    recipe.AddImage(str);
                }
                //endregion

                database.get(categoryName).put(recipeName, recipe);

                br.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    int pxFromDp(double dp) {
        return (int) (dp * density);
    }

    class keysToRecipe {
        private String category;
        private String recipeName;

        String getCategory() {
            return category;
        }

        String getRecipeName() {
            return recipeName;
        }

        keysToRecipe(String cat, String name) {
            category = cat;
            recipeName = name;
        }
    }

    public ArrayList<keysToRecipe> searchRecipes(String category, String seacrhingString,
                                          ArrayList<String> inclIngrs,
                                          ArrayList<String> exclIngrs,
                                                 int kcal, int time) {
        ArrayList<keysToRecipe> result = new ArrayList<>();

        //поиск по названию в категории/ях
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Objects.equals(category, "")) {
                for (String cat : database.keySet()) {
                    ArrayList<String> recipes = searchInCategory(cat, seacrhingString);

                    for (String recipeName : recipes) {
                        keysToRecipe keys = new keysToRecipe(cat, recipeName);
                        result.add(keys);
                    }
                }
            } else {
                ArrayList<String> recipes = searchInCategory(category, seacrhingString);

                for (String recipeName : recipes) {
                    keysToRecipe keys = new keysToRecipe(category, recipeName);
                    result.add(keys);
                }
            }
        }

        //поиск по ингредиентам, времени и калориям
        Boolean timeF = time != 0;
        Boolean kcalF = kcal != 0;
        if (inclIngrs.size() != 0 || exclIngrs.size() != 0 || timeF || kcalF) {
            outer:
            for (int i = result.size() - 1; i >= 0; i--) {
                String cat = result.get(i).getCategory();
                String name = result.get(i).getRecipeName();
                Recipe recipe = database.get(cat).get(name);

                if (timeF && recipe.getTime() > time) {
                    result.remove(i);
                    continue;
                }

                if (kcalF && recipe.getKcal() > kcal) {
                    result.remove(i);
                    continue;
                }

                for (String ingredient : inclIngrs)
                    if (!recipe.HasIngredient(ingredient)) {
                        result.remove(i);
                        continue outer;
                    }

                for (String ingredient : exclIngrs)
                    if (recipe.HasIngredient(ingredient)) {
                        result.remove(i);
                        continue outer;
                    }
            }
        }
        return result;
    }

    private ArrayList<String> searchInCategory(String category, String seacrhingString) {
        ArrayList<String> result = new ArrayList<>();

        Set<String> recipeNames = database.get(category).keySet();

        for (String name : recipeNames)
        {
            String nameL = name.toLowerCase();

            if (searchWordInLine(nameL, seacrhingString.toLowerCase()))
                result.add(name);
        }

        return result;
    }

    private Boolean searchWordInLine (String str, String name) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (!Objects.equals(name, "")) {

                str = str.replaceAll("[^a-zA-Zа-яА-Я]", " ");
                name = name.replaceAll("[^a-zA-Zа-яА-Я]", " ");

                String[] words = str.split("\\s+");
                String[] wordsname = name.split("\\s+");
                for (String subName : wordsname) {
                    Boolean isContained = false;
                    for (String subStr : words) {
                        if (Objects.equals(subStr, subName))
                            isContained = true;
                    }
                    if (!isContained)
                        return false;
                }
            }
        }
        return true;
    }
}
