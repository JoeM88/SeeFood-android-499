//package com.example.seefood.restaurantDetails;
//
//import java.util.Arrays;
//import java.util.List;
//
//public class MealsData {
//
//    public static List<Meal<>> makeMeals() {
//        return Arrays.asList(makeBreakfastMeal(),
//                makeLunchMeal(),
//                makeDinnerMeal());
//
//    }
//
//
//    public static Meals makeBreakfastMeal() {
//        return new Meals("Breakfast", makeBreakfastFood());
//    }
//
//
//    public static List<Food> makeBreakfastFood() {
//        Food eggs = new Food("Eggs", "20 calories", "Eggs", "https://i2.wp.com/www.downshiftology.com/wp-content/uploads/2018/06/poached-eggs-9.jpg");
//        Food yogurt = new Food("Yogurt", "10 calories", "Yogurt", "https://s23209.pcdn.co/wp-content/uploads/2018/02/How-to-Make-Yogurt-in-an-Instant-PotIMG_5455-copy.jpg");
//
//        return Arrays.asList(eggs, yogurt);
//    }
//
//    public static Meals makeLunchMeal() {
//        return new Meals("Lunch", makeLunchFood());
//    }
//
//    public static List<Food> makeLunchFood() {
//        Food beans = new Food("Beans", "50 calories", "Beans", "https://nusciencesolutions.com/wp-content/uploads/2018/09/No-Gas-Home-Cooked-Beans-Cropped.jpg");
//        Food burger = new Food("Hamburger", "250 calories", "Hamburger", "https://www.recipetineats.com/wp-content/uploads/2016/02/Beef-Hamburgers_7-landscape.jpg");
//
//
//        return Arrays.asList(beans, burger);
//    }
//
//    public static Meals makeDinnerMeal() {
//        return new Meals("Dinner", makeDinnerFood());
//    }
//
//    public static List<Food> makeDinnerFood() {
//        Food food1 = new Food("Beans", "50 calories", "Beans", "https://nusciencesolutions.com/wp-content/uploads/2018/09/No-Gas-Home-Cooked-Beans-Cropped.jpg");
//        Food food2 = new Food("Hamburger", "250 calories", "Hamburger", "https://www.recipetineats.com/wp-content/uploads/2016/02/Beef-Hamburgers_7-landscape.jpg");
//
//
//        return Arrays.asList(food1, food2);
//    }
//
//
//
//}
