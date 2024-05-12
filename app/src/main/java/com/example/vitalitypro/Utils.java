package com.example.vitalitypro;

public class Utils {
    /*public static List<Ingredient> parseIngredients(InputStream inputStream) {
        List<Ingredient> ingredients = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
            String[] nextLine;
            // Skip the header line
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                // Assuming the structure of the CSV matches the order of values in the Ingredient constructor
                String name = nextLine[1];
                double calories = Double.parseDouble(nextLine[6]);
                double fat = Double.parseDouble(nextLine[23]);
                double carbs = Double.parseDouble(nextLine[5]);
                double protein = Double.parseDouble(nextLine[13]);
                Ingredient ingredient = new Ingredient(name, calories, fat, carbs, protein);
                ingredients.add(ingredient);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return ingredients;
    }*/

}
