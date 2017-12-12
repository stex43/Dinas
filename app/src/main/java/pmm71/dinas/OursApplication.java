package pmm71.dinas;

import android.app.Application;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by St-Ex on 10.12.2017.
 */

public class OursApplication extends Application {
    public Map<String, Map<String, Recipe>> database;
    int numRec = 3;
    private float density;
    Resources resources;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void ReadFiles() {
        resources = this.getResources();
        density = getApplicationContext().getResources().getDisplayMetrics().density;

        String[] categories = new String[]{ "Салаты" };
        database = new HashMap<>();
        for (String category : categories)
            database.put(category, new HashMap<String, Recipe>());

        InputStream in;
        BufferedReader br;

        for (int i = 0; i < numRec; i++) {
            String rawName = "rec".concat(String.valueOf(i + 1));
            int recId = resources.getIdentifier(rawName, "raw", this.getPackageName());
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

                str = br.readLine();
                recipe.AddImage(str);

                str = br.readLine();
                recipe.AddImage(str);

                categoryName = br.readLine();

                str = br.readLine();
                recipe.setTime(str);

                str = br.readLine();
                recipe.setKcal(Integer.parseInt(str));

                while (!Objects.equals(str = br.readLine(), ""))
                    recipe.AddIngredient(str);

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

        public String getCategory() {
            return category;
        }

        public String getRecipeName() {
            return recipeName;
        }

        public keysToRecipe(String cat, String name) {
            category = cat;
            recipeName = name;
        }
    }

    public ArrayList<keysToRecipe> searchRecipes(String category, String seacrhingString,
                                          ArrayList<String> inclIngrs,
                                          ArrayList<String> exclIngrs) {
        ArrayList<keysToRecipe> result = new ArrayList<>();

        //поиск по названию в категории/ях
        if (category == "") {
            Set<String> catNames = database.keySet();
            for (String cat : database.keySet()) {
                ArrayList<String> recipes = seacrhInCategory(cat, seacrhingString);

                for (String recipeName : recipes) {
                    keysToRecipe keys = new keysToRecipe(cat, recipeName);
                    result.add(keys);
                }
            }
        }
        else {
            ArrayList<String> recipes = seacrhInCategory(category, seacrhingString);

            for (String recipeName : recipes) {
                keysToRecipe keys = new keysToRecipe(category, recipeName);
                result.add(keys);
            }
        }

        //поиск по ингредиентам

        return result;
    }

    private ArrayList<String> seacrhInCategory(String category, String seacrhingString) {
        ArrayList<String> result = new ArrayList<>();

        Set<String> recipeNames = database.get(category).keySet();

        for (String name : recipeNames)
        {
            String nameL = name.toLowerCase();

            if (nameL.contains(seacrhingString.toLowerCase()))
                result.add(name);
        }

        return result;
    }
}