package com.anshu.bitebuddy.utils;

import android.text.TextUtils;
import android.util.Log;

import com.anshu.bitebuddy.BuildConfig;
import com.anshu.bitebuddy.core.database.model.Food;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.inject.Inject;


public final class FoodItemList {
    private static final String TAG = "FoodItemList";
    final ArrayList<Food> indianFoodList = new ArrayList<>();
    private static final String NO_IMAGE_FOUND = "https://www.istockphoto.com/photos/no-food-or-drink-sign";

    @Inject
    FirebaseFirestore db;

    @Inject
    public FoodItemList() {
//        indianFoodList.add(new Food("Idli and Sambar", 60, "Soft steamed rice cakes made from fermented batter, served with a flavorful lentil and vegetable stew.", "valid_image_url_idli_sambar", "Breakfast", 5, ""));
//        indianFoodList.add(new Food("Dosa", 70, "A crispy crepe made from fermented rice and lentil batter, often served with sambar and chutney.", "valid_image_url_dosa", "Breakfast", 5, ""));
//        indianFoodList.add(new Food("Paratha", 60, "A golden-brown, flaky flatbread made with whole wheat flour, often served with pickle and yogurt.", "valid_image_url_paratha", "Breakfast", 4, ""));
//        indianFoodList.add(new Food("Methi Thepla", 50, "A thin, soft whole-wheat flatbread made with fenugreek leaves and spices.", "valid_image_url_methi_thepla", "Breakfast", 4, ""));
//        indianFoodList.add(new Food("Batata Poha", 40, "Flattened rice cooked with potatoes, onions, spices, and a hint of lemon.", "valid_image_url_batata_poha", "Breakfast", 4, ""));
//        indianFoodList.add(new Food("Upma", 50, "A savory porridge made with roasted semolina or oats, vegetables, and spices.", "valid_image_url_upma", "Breakfast", 4, ""));
//        indianFoodList.add(new Food("Rava Uttapam", 70, "A thick savory pancake made from semolina batter with vegetables and spices.", "valid_image_url_rava_uttapam", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Khaman Dhokla", 60, "A savory, soft, and fluffy steamed cake made from chickpea flour.", "valid_image_url_khaman_dhokla", "Breakfast", 4, ""));
//        indianFoodList.add(new Food("Rava Idli", 50, "Quick and easy steamed cakes made from semolina batter.", "valid_image_url_rava_idli", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Oats Chilla", 60, "A healthy and savory pancake made from oats and vegetables.", "valid_image_url_oats_chilla", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Poori Bhaji", 70, "Deep-fried whole wheat bread served with a spiced potato curry.", "valid_image_url_poori_bhaji", "Breakfast", 4, ""));
//        indianFoodList.add(new Food("Aloo Paratha", 60, "Flatbread stuffed with spiced mashed potatoes.", "valid_image_url_aloo_paratha", "Breakfast", 4, ""));
//        indianFoodList.add(new Food("Masala Dosa", 80, "Crispy crepe filled with a spiced potato mixture.", "valid_image_url_masala_dosa", "Breakfast", 5, ""));
//        indianFoodList.add(new Food("Upma Poha", 45, "A combination of savory semolina porridge and flattened rice.", "valid_image_url_upma_poha", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Besan Cheela", 55, "A savory pancake made from chickpea flour and spices.", "valid_image_url_besan_cheela", "Breakfast", 4, ""));
//        indianFoodList.add(new Food("Anda Bhurji", 70, "Scrambled eggs cooked with onions, tomatoes, and spices.", "valid_image_url_anda_bhurji", "Breakfast", 4, ""));
//        indianFoodList.add(new Food("Appam", 75, "A spongy pancake made from fermented rice batter and coconut milk.", "valid_image_url_appam", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Puttu", 65, "Steamed rice cylinders cooked with grated coconut.", "valid_image_url_puttu", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Sabudana Khichdi", 60, "A dish made from soaked sago pearls, potatoes, and peanuts.", "valid_image_url_sabudana_khichdi", "Breakfast", 4, ""));
//        indianFoodList.add(new Food("Pongal", 65, "A dish made with rice and lentils, available in sweet and savory versions.", "valid_image_url_pongal", "Breakfast", 4, ""));
//        indianFoodList.add(new Food("Semiya Upma", 45, "A savory breakfast dish made with vermicelli, vegetables, and spices.", "valid_image_url_semiya_upma", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Idiyaapam", 70, "Steamed rice noodles, often served with coconut milk or curry.", "valid_image_url_idiyaapam", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Aapam", 75, "A thick crepe with a soft and spongy center and crispy edges.", "valid_image_url_aappam", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Sattu Ke Parathe", 55, "Flatbreads stuffed with roasted gram flour and spices.", "valid_image_url_sattu_parathe", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Neer Dosa", 60, "Thin and lacy crepes made from rice batter.", "valid_image_url_neer_dosa", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Dhuska", 50, "Deep-fried pancakes made from rice and lentil batter.", "valid_image_url_dhuska", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Radhaballabhi", 80, "Lentil-stuffed fried bread, a Bengali specialty.", "valid_image_url_radhaballabhi", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Chura Bhaja", 40, "Fried flattened rice, a popular breakfast in Odisha.", "valid_image_url_chura_bhaja", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Kachori", 50, "Deep-fried pastry filled with spiced lentils or potatoes.", "valid_image_url_kachori_breakfast", "Breakfast", 4, ""));
//        indianFoodList.add(new Food("Momos", 60, "Steamed dumplings with vegetable or meat filling.", "valid_image_url_momos_breakfast", "Breakfast", 4, ""));
//        indianFoodList.add(new Food("Aloo Puri", 70, "Puffed fried bread served with a spicy potato curry.", "valid_image_url_aloo_puri", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Rice Bhakri", 55, "Flatbread made from rice flour, popular in Goa and Konkan.", "valid_image_url_rice_bhakri", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Roti Omelette", 75, "A popular Haryanvi breakfast where an omelette wraps a roti.", "valid_image_url_roti_omelette", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Halwa Poori", 80, "Fried bread served with a sweet semolina pudding.", "valid_image_url_halwa_poori", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Dal Parathas", 65, "Flatbreads stuffed with cooked lentils.", "valid_image_url_dal_parathas", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Kanda Poha", 40, "Flattened rice cooked with onions and spices.", "valid_image_url_kanda_poha", "Breakfast", 4, ""));
//        indianFoodList.add(new Food("Rava Pongal", 60, "A savory pudding made with semolina, lentils, and spices.", "valid_image_url_rava_pongal", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Adai", 70, "A thick pancake made from a batter of mixed lentils and rice.", "valid_image_url_adai", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Pesarattu Upma", 65, "A dosa made from green gram lentils, stuffed with upma.", "valid_image_url_pesarattu_upma", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Khura", 50, "Buckwheat pancakes, a traditional breakfast in Arunachal Pradesh.", "valid_image_url_khura", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Sabudana Idli", 60, "Steamed cakes made from sago pearls.", "valid_image_url_sabudana_idli", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Thalipeeth", 55, "A savory multi-grain flatbread.", "valid_image_url_thalipeeth", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Kolkata Egg Roll", 85, "Paratha wrapped around eggs and vegetables.", "valid_image_url_kolkata_egg_roll", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Quinoa Dosa", 80, "A dosa made with quinoa instead of rice.", "valid_image_url_quinoa_dosa", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Palak Paneer Paratha", 70, "Flatbread stuffed with spinach and Indian cheese.", "valid_image_url_palak_paneer_paratha", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Gobi Paratha", 65, "Flatbread stuffed with spiced cauliflower.", "valid_image_url_gobi_paratha", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Mooli Paratha", 60, "Flatbread stuffed with spiced radish.", "valid_image_url_mooli_paratha", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Paneer Paratha", 75, "Flatbread stuffed with spiced Indian cheese.", "valid_image_url_paneer_paratha", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Vegetable Uttapam", 70, "Thick savory pancake with mixed vegetables.", "valid_image_url_vegetable_uttapam", "Breakfast", 3, ""));
//        indianFoodList.add(new Food("Onion Uttapam", 65, "Thick savory pancake with onions.", "valid_image_url_onion_uttapam", "Breakfast", 3, ""));

//        // Lunch Items
//        indianFoodList.add(new Food("Vegetable Pulao", 80, "Fragrant rice cooked with mixed vegetables and spices.", "valid_image_url_veg_pulao", "Lunch", 4, ""));
//        indianFoodList.add(new Food("Dal Makhani", 120, "Creamy black lentils and kidney beans cooked with butter and spices.", "valid_image_url_dal_makhani", "Lunch", 5, ""));
//        indianFoodList.add(new Food("Chana Masala", 90, "Chickpeas cooked in a tangy tomato-based gravy with aromatic spices.", "valid_image_url_chana_masala", "Lunch", 4, ""));
//        indianFoodList.add(new Food("Rajma Chawal", 100, "Red kidney beans cooked in a thick gravy, served with steamed rice.", "valid_image_url_rajma_chawal", "Lunch", 4, ""));
//        indianFoodList.add(new Food("Veg Biryani", 110, "Aromatic rice dish cooked with mixed vegetables, spices, and herbs.", "valid_image_url_veg_biryani", "Lunch", 4, ""));
//        indianFoodList.add(new Food("Veg Thali", 150, "A complete vegetarian meal served with rice, dal, vegetables, roti, and more.", "valid_image_url_veg_thali", "Lunch", 4, ""));
//        indianFoodList.add(new Food("Palak Paneer", 110, "Indian cheese cooked in a creamy spinach gravy with spices.", "valid_image_url_palak_paneer", "Lunch", 5, ""));
//        indianFoodList.add(new Food("Aloo Gobi", 90, "Potatoes and cauliflower cooked together with spices.", "valid_image_url_aloo_gobi", "Lunch", 4, ""));
//        indianFoodList.add(new Food("Sambhar Rice", 70, "A comforting South Indian meal of rice mixed with lentil and vegetable stew.", "valid_image_url_sambhar_rice", "Lunch", 4, ""));
//        indianFoodList.add(new Food("Curd Rice", 60, "Cooked rice mixed with yogurt, often tempered with spices.", "valid_image_url_curd_rice", "Lunch", 4, ""));
//        indianFoodList.add(new Food("Chicken Biryani", 150, "Fragrant rice cooked with chicken, spices, and herbs.", "valid_image_url_chicken_biryani", "Lunch", 5, ""));
//        indianFoodList.add(new Food("Fish Curry Rice", 160, "Fish cooked in a flavorful curry, served with steamed rice.", "valid_image_url_fish_curry_rice", "Lunch", 4, ""));
//        indianFoodList.add(new Food("Mutter Paneer", 100, "Indian cheese and green peas cooked in a tomato-based gravy.", "valid_image_url_mutter_paneer", "Lunch", 4, ""));
//        indianFoodList.add(new Food("Kadhi Chawal", 80, "A yogurt-based curry with gram flour dumplings, served with rice.", "valid_image_url_kadhi_chawal", "Lunch", 3, ""));
//        indianFoodList.add(new Food("Baingan Bharta", 90, "Mashed roasted eggplant cooked with onions, tomatoes, and spices.", "valid_image_url_baingan_bharta", "Lunch", 4, ""));
//        indianFoodList.add(new Food("Aloo Matar", 85, "Potatoes and green peas cooked in a spiced tomato gravy.", "valid_image_url_aloo_matar", "Lunch", 3, ""));
//        indianFoodList.add(new Food("Mixed Vegetable Curry", 95, "A curry made with a variety of fresh vegetables and spices.", "valid_image_url_mixed_veg_curry", "Lunch", 4, ""));
//        indianFoodList.add(new Food("Roti Sabzi", 70, "Whole wheat flatbread served with a vegetable side dish.", "valid_image_url_roti_sabzi", "Lunch", 3, ""));
//        indianFoodList.add(new Food("Rice Dal", 65, "Steamed rice served with lentil soup.", "valid_image_url_rice_dal", "Lunch", 3, ""));
//        indianFoodList.add(new Food("Paneer Butter Masala", 130, "Indian cheese cooked in a rich, buttery tomato gravy.", "valid_image_url_paneer_butter_masala", "Lunch", 5, ""));
//        indianFoodList.add(new Food("Soya Chunks Biryani", 100, "Fragrant rice cooked with soya chunks, vegetables, and spices.", "valid_image_url_soya_biryani", "Lunch", 3, ""));
//        indianFoodList.add(new Food("Steamed Cabbage", 50, "Shredded cabbage simply steamed, often seasoned lightly.", "valid_image_url_steamed_cabbage", "Lunch", 2, ""));
//        indianFoodList.add(new Food("Cabbage Stir Fry", 60, "Shredded cabbage stir-fried with spices and sometimes other vegetables.", "valid_image_url_cabbage_stir_fry", "Lunch", 3, ""));
//        indianFoodList.add(new Food("Yam Fry", 75, "Yam slices fried until crispy, often seasoned with spices.", "valid_image_url_yam_fry", "Lunch", 3, ""));
//        indianFoodList.add(new Food("Carrot Beans Stir Fry", 55, "Carrots and beans stir-fried with spices and coconut.", "valid_image_url_carrot_beans_fry", "Lunch", 3, ""));
//        indianFoodList.add(new Food("Puliyogare", 70, "Tamarind rice, a tangy and flavorful South Indian rice dish.", "valid_image_url_puliyogare", "Lunch", 4, ""));
//        indianFoodList.add(new Food("Schezwan Fried Rice", 90, "Fried rice cooked with vegetables and spicy Schezwan sauce.", "valid_image_url_schezwan_fried_rice", "Lunch", 3, ""));
//        indianFoodList.add(new Food("Ghee Rice", 70, "Basmati rice cooked with ghee and aromatic spices.", "valid_image_url_ghee_rice", "Lunch", 3, ""));
//        indianFoodList.add(new Food("Tomato Rice", 65, "Rice cooked with tomatoes, onions, and spices.", "valid_image_url_tomato_rice", "Lunch", 3, ""));
//        indianFoodList.add(new Food("Lemon Rice", 60, "Rice cooked with lemon juice, mustard seeds, and curry leaves.", "valid_image_url_lemon_rice", "Lunch", 3, ""));
//        indianFoodList.add(new Food("Coconut Rice", 75, "Rice cooked in coconut milk with mild spices.", "valid_image_url_coconut_rice", "Lunch", 3, ""));
//        indianFoodList.add(new Food("Peas Pulao", 85, "Rice cooked with green peas and mild spices.", "valid_image_url_peas_pulao", "Lunch", 3, ""));
//        indianFoodList.add(new Food("Mushroom Biryani", 120, "Fragrant rice cooked with mushrooms, spices, and herbs.", "valid_image_url_mushroom_biryani", "Lunch", 4, ""));
//        indianFoodList.add(new Food("Dal Tadka (Lunch)", 90, "Lentils tempered with spices, a common and flavorful side dish.", "valid_image_url_dal_tadka_lunch", "Lunch", 4, ""));
//        indianFoodList.add(new Food("Saag", 95, "A nutritious dish made from leafy greens like spinach.", "valid_image_url_saag", "Lunch", 3, ""));
//        indianFoodList.add(new Food("Korma", 100, "A mild and creamy curry made with vegetables or meat.", "valid_image_url_korma", "Lunch", 3, ""));
//        indianFoodList.add(new Food("Kaju Dingri Mattar", 115, "A creamy curry with cashews, mushrooms, and green peas.", "valid_image_url_kaju_dingri_mutter", "Lunch", 3, ""));
//        indianFoodList.add(new Food("Allepey Fish Curry", 140, "Fish curry cooked with raw mango and coconut milk.", "valid_image_url_allepey_fish_curry", "Lunch", 3, ""));
//        indianFoodList.add(new Food("Malabar Fish Curry", 150, "Creamy fish curry with coconut milk and spices.", "valid_image_url_malabar_fish_curry", "Lunch", 4, ""));
//        indianFoodList.add(new Food("Keema", 110, "Minced meat curry with peas or potatoes.", "valid_image_url_keema_lunch", "Lunch", 3, ""));
//        indianFoodList.add(new Food("Khichdi", 75, "A simple dish made with rice and lentils.", "valid_image_url_khichdi_lunch", "Lunch", 3, ""));
//        indianFoodList.add(new Food("Palak Paneer (Lunch)", 105, "Spinach and Indian cheese cooked together.", "valid_image_url_palak_paneer_lunch", "Lunch", 4, ""));
//        indianFoodList.add(new Food("Tomato Bath", 70, "Spicy rice dish cooked with tomatoes and vegetables.", "valid_image_url_tomato_bath", "Lunch", 3, ""));
//        indianFoodList.add(new Food("Capsicum Rice", 75, "Rice cooked with bell peppers and spices.", "valid_image_url_capsicum_rice", "Lunch", 3, ""));
//        indianFoodList.add(new Food("Tehri", 80, "Vegetable and rice pilaf.", "valid_image_url_tehri", "Lunch", 3, ""));
//        indianFoodList.add(new Food("Kathi Roll", 90, "Spiced filling wrapped in a flatbread.", "valid_image_url_kathi_roll", "Lunch", 4, ""));
//        indianFoodList.add(new Food("Veg Wraps", 85, "Vegetables and spices wrapped in a roti.", "valid_image_url_veg_wraps", "Lunch", 3, ""));
//        indianFoodList.add(new Food("Paneer Pulao", 115, "Rice cooked with Indian cheese and spices.", "valid_image_url_paneer_pulao", "Lunch", 4, ""));
//        indianFoodList.add(new Food("Mushroom Fried Rice", 95, "Fried rice with mushrooms and vegetables.", "valid_image_url_mushroom_fried_rice", "Lunch", 3, ""));
//
//        // Dinner Items
//        indianFoodList.add(new Food("Butter Chicken", 180, "Tender chicken cooked in a creamy tomato-based gravy.", "valid_image_url_butter_chicken", "Dinner", 5, ""));
//        indianFoodList.add(new Food("Rogan Josh", 200, "Aromatic lamb curry from Kashmir with vibrant red color.", "valid_image_url_rogan_josh", "Dinner", 4, ""));
//        indianFoodList.add(new Food("Dal Tadka", 90, "Lentils tempered with spices like cumin, mustard seeds, and garlic.", "valid_image_url_dal_tadka_dinner", "Dinner", 4, ""));
//        indianFoodList.add(new Food("Paneer Butter Masala", 130, "Indian cheese cooked in a rich, buttery tomato gravy.", "valid_image_url_paneer_butter_masala_dinner", "Dinner", 5, ""));
//        indianFoodList.add(new Food("Naan", 40, "Soft and chewy leavened flatbread cooked in a tandoor.", "valid_image_url_naan_dinner", "Dinner", 5, ""));
//        indianFoodList.add(new Food("Roti", 20, "Unleavened whole wheat flatbread.", "valid_image_url_roti_dinner", "Dinner", 5, ""));
//        indianFoodList.add(new Food("Chicken Biryani", 180, "Fragrant rice cooked with chicken, spices, and herbs.", "valid_image_url_chicken_biryani_dinner", "Dinner", 5, ""));
//        indianFoodList.add(new Food("Mutton Biryani", 220, "Fragrant rice cooked with mutton, spices, and herbs.", "valid_image_url_mutton_biryani_dinner", "Dinner", 5, ""));
//        indianFoodList.add(new Food("Veg Biryani", 150, "Aromatic rice dish cooked with mixed vegetables and spices.", "valid_image_url_veg_biryani_dinner", "Dinner", 5, ""));
//        indianFoodList.add(new Food("Shahi Paneer", 140, "Rich and creamy Indian cheese curry.", "valid_image_url_shahi_paneer_dinner", "Dinner", 4, ""));
//        indianFoodList.add(new Food("Malai Kofta", 130, "Fried potato and paneer dumplings in a creamy tomato gravy.", "valid_image_url_malai_kofta_dinner", "Dinner", 4, ""));
//        indianFoodList.add(new Food("Saag Paneer", 115, "Spinach and Indian cheese cooked together in a spiced gravy.", "valid_image_url_saag_paneer_dinner", "Dinner", 4, ""));
//        indianFoodList.add(new Food("Dal Makhani (Dinner)", 120, "Creamy black lentils and kidney beans cooked with butter and spices.", "valid_image_url_dal_makhani_dinner", "Dinner", 5, ""));
//        indianFoodList.add(new Food("Chana Masala (Dinner)", 95, "Chickpeas cooked in a tangy tomato-based gravy.", "valid_image_url_chana_masala_dinner", "Dinner", 4, ""));
//        indianFoodList.add(new Food("Rajma Chawal (Dinner)", 105, "Red kidney beans cooked in a thick gravy, served with rice.", "valid_image_url_rajma_chawal_dinner", "Dinner", 4, ""));
//        indianFoodList.add(new Food("Aloo Gobi (Dinner)", 95, "Potatoes and cauliflower cooked with spices.", "valid_image_url_aloo_gobi_dinner", "Dinner", 4, ""));
//        indianFoodList.add(new Food("Baingan Bharta (Dinner)", 100, "Mashed roasted eggplant cooked with spices.", "valid_image_url_baingan_bharta_dinner", "Dinner", 4, ""));
//        indianFoodList.add(new Food("Mutter Paneer (Dinner)", 110, "Indian cheese and green peas in a tomato gravy.", "valid_image_url_mutter_paneer_dinner", "Dinner", 4, ""));
//        indianFoodList.add(new Food("Vegetable Curry (Dinner)", 90, "A mixed vegetable curry with various spices.", "valid_image_url_veg_curry_dinner", "Dinner", 4, ""));
//        indianFoodList.add(new Food("Kadai Paneer", 135, "Indian cheese cooked in a wok with bell peppers and onions.", "valid_image_url_kadai_paneer_dinner", "Dinner", 4, ""));
//        indianFoodList.add(new Food("Navratan Korma", 145, "A rich and creamy curry with nine different vegetables and nuts.", "valid_image_url_navratan_korma_dinner", "Dinner", 4, ""));
//        indianFoodList.add(new Food("Kofta Curry (Dinner)", 120, "Dumplings made of vegetables or meat in a spiced gravy.", "valid_image_url_kofta_curry_dinner", "Dinner", 3, ""));
//        indianFoodList.add(new Food("Vegetable Jalfrezi", 100, "Stir-fried vegetables with onions, peppers, and a spicy tomato sauce.", "valid_image_url_veg_jalfrezi_dinner", "Dinner", 3, ""));
//        indianFoodList.add(new Food("Palak Dal (Dinner)", 90, "Lentils cooked with spinach and tempered with spices.", "valid_image_url_palak_dal_dinner", "Dinner", 3, ""));
//        indianFoodList.add(new Food("Chole Bhature (Dinner)", 110, "Spicy chickpeas curry served with large, fluffy fried bread.", "valid_image_url_chole_bhature_dinner", "Dinner", 4, ""));
//        indianFoodList.add(new Food("Bhindi Masala (Dinner)", 95, "Okra cooked with onions, tomatoes, and spices.", "valid_image_url_bhindi_masala_dinner", "Dinner", 3, ""));
//        indianFoodList.add(new Food("Lauki Kofta (Dinner)", 105, "Bottle gourd dumplings in a spiced tomato gravy.", "valid_image_url_lauki_kofta_dinner", "Dinner", 3, ""));
//        indianFoodList.add(new Food("Mushroom Curry (Dinner)", 120, "Mushrooms cooked in a flavorful gravy with onions and tomatoes.", "valid_image_url_mushroom_curry_dinner", "Dinner", 3, ""));
//        indianFoodList.add(new Food("Aloo Methi (Dinner)", 85, "Potatoes cooked with fenugreek leaves and spices.", "valid_image_url_aloo_methi_dinner", "Dinner", 3, ""));
//        indianFoodList.add(new Food("Paneer Tikka Masala (Dinner)", 140, "Grilled Indian cheese cooked in a spiced tomato-onion gravy.", "valid_image_url_paneer_tikka_masala_dinner", "Dinner", 4, ""));
//        indianFoodList.add(new Food("Vegetable Pulao (Dinner)", 90, "Fragrant rice cooked with mixed vegetables and spices.", "valid_image_url_veg_pulao_dinner", "Dinner", 4, ""));
//        indianFoodList.add(new Food("Jeera Rice (Dinner)", 70, "Basmati rice cooked with cumin seeds.", "valid_image_url_jeera_rice_dinner", "Dinner", 3, ""));
//        indianFoodList.add(new Food("Peas Pulao (Dinner)", 95, "Rice cooked with green peas and mild spices.", "valid_image_url_peas_pulao_dinner", "Dinner", 3, ""));
//        indianFoodList.add(new Food("Kashmiri Pulao (Dinner)", 110, "Sweet and aromatic rice dish with dry fruits and nuts.", "valid_image_url_kashmiri_pulao_dinner", "Dinner", 3, ""));
//        indianFoodList.add(new Food("Methi Paratha (Dinner)", 65, "Flatbread made with fenugreek leaves.", "valid_image_url_methi_paratha_dinner", "Dinner", 3, ""));
//        indianFoodList.add(new Food("Garlic Naan (Dinner)", 50, "Soft leavened flatbread flavored with garlic.", "valid_image_url_garlic_naan_dinner", "Dinner", 4, ""));
//        indianFoodList.add(new Food("Tandoori Roti (Dinner)", 30, "Whole wheat flatbread cooked in a tandoor.", "valid_image_url_tandoori_roti_dinner", "Dinner", 4, ""));
//        indianFoodList.add(new Food("Malabar Fish Curry (Dinner)", 165, "Creamy fish curry with coconut milk and spices from Kerala.", "valid_image_url_malabar_fish_curry_dinner", "Dinner", 4, ""));
//        indianFoodList.add(new Food("Rogan Josh (Vegetarian)", 120, "Vegetarian version of the Kashmiri curry with similar spices.", "valid_image_url_veg_rogan_josh_dinner", "Dinner", 3, ""));
//        indianFoodList.add(new Food("Dal Palak (Dinner)", 90, "Lentils cooked with spinach and tempered with spices.", "valid_image_url_dal_palak_dinner", "Dinner", 3, ""));
//        indianFoodList.add(new Food("Vegetable Biryani (Hyderabadi)", 160, "Aromatic rice dish with vegetables cooked in Hyderabadi style.", "valid_image_url_hyderabadi_veg_biryani_dinner", "Dinner", 4, ""));
//        indianFoodList.add(new Food("Paneer Do Pyaza", 130, "Indian cheese cooked with double the amount of onions in a spiced gravy.", "valid_image_url_paneer_do_pyaza_dinner", "Dinner", 3, ""));
//        indianFoodList.add(new Food("Aloo Saag (Dinner)", 80, "Potatoes cooked with mustard greens.", "valid_image_url_aloo_saag_dinner", "Dinner", 3, ""));
//        indianFoodList.add(new Food("Tomato Curry (Dinner)", 85, "Tomatoes cooked in a spiced gravy.", "valid_image_url_tomato_curry_dinner", "Dinner", 3, ""));
//        indianFoodList.add(new Food("Mixed Vegetable Sabzi (Dinner)", 95, "A dry preparation of mixed vegetables with spices.", "valid_image_url_mixed_veg_sabzi_dinner", "Dinner", 3, ""));
//        indianFoodList.add(new Food("Dal Fry (Dinner)", 85, "Lentils cooked and tempered with spices.", "valid_image_url_dal_fry_dinner", "Dinner", 4, ""));
//        indianFoodList.add(new Food("Baingan Bharta (Smoked)", 100, "Smoked and mashed eggplant cooked with spices.", "valid_image_url_smoked_baingan_bharta_dinner", "Dinner", 4, ""));
//        indianFoodList.add(new Food("Vegetable Kofta Curry (Dinner)", 120, "Vegetable dumplings in a spiced gravy.", "valid_image_url_veg_kofta_curry_dinner", "Dinner", 3, ""));
//        indianFoodList.add(new Food("Aloo Palak (Dinner)", 90, "Potatoes cooked with spinach.", "valid_image_url_aloo_palak_dinner", "Dinner", 3, ""));
//        indianFoodList.add(new Food("Chana Dal (Dinner)", 80, "Split chickpeas cooked with spices.", "valid_image_url_chana_dal_dinner", "Dinner", 3, ""));
//
//        // Snack Items
        indianFoodList.add(new Food("Samosa", 30, "A fried pastry with a savory filling, such as spiced potatoes.", "valid_image_url_samosa", "Snacks", 5, ""));
        indianFoodList.add(new Food("Pani Puri", 40, "Crispy hollow balls filled with spiced potatoes, chickpeas, and tangy water.", "valid_image_url_pani_puri", "Snacks", 5, ""));
        indianFoodList.add(new Food("Bhel Puri", 50, "A savory snack made with puffed rice, vegetables, and chutneys.", "valid_image_url_bhel_puri", "Snacks", 4, ""));
        indianFoodList.add(new Food("Vada Pav", 40, "A spiced potato fritter served in a bread bun.", "valid_image_url_vada_pav", "Snacks", 5, ""));
        indianFoodList.add(new Food("Pakora", 60, "Deep-fried fritters made with vegetables or paneer and chickpea flour.", "valid_image_url_pakora", "Snacks", 4, ""));
        indianFoodList.add(new Food("Chaat", 70, "A category of savory snacks with tangy, spicy, and sweet flavors.", "valid_image_url_chaat", "Snacks", 5, ""));
        indianFoodList.add(new Food("Kachori", 40, "A deep-fried pastry filled with spiced lentils or potatoes.", "valid_image_url_kachori_snacks", "Snacks", 4, ""));
        indianFoodList.add(new Food("Aloo Tikki", 50, "Spiced potato patties, often served with chutneys.", "valid_image_url_aloo_tikki", "Snacks", 4, ""));
        indianFoodList.add(new Food("Sev Puri", 60, "Crispy puris topped with potatoes, onions, chutneys, and sev.", "valid_image_url_sev_puri", "Snacks", 4, ""));
        indianFoodList.add(new Food("Bhaji", 50, "Spiced vegetable fritters, like onion or potato bhaji.", "valid_image_url_bhaji", "Snacks", 4, ""));
        indianFoodList.add(new Food("Papdi Chaat", 70, "Crispy flat crackers topped with potatoes, chickpeas, yogurt, and chutneys.", "valid_image_url_papdi_chaat", "Snacks", 4, ""));
        indianFoodList.add(new Food("Dahi Puri", 65, "Crispy puris filled with potatoes, chickpeas, yogurt, and spices.", "valid_image_url_dahi_puri", "Snacks", 4, ""));
        indianFoodList.add(new Food("Bread Pakora", 45, "Bread slices dipped in chickpea batter and deep-fried.", "valid_image_url_bread_pakora", "Snacks", 3, ""));
        indianFoodList.add(new Food("Frankie", 60, "A spiced filling wrapped in a flatbread.", "valid_image_url_frankie", "Snacks", 4, ""));
        indianFoodList.add(new Food("Jalebi", 50, "Deep-fried batter spirals soaked in sugar syrup.", "valid_image_url_jalebi_snacks", "Snacks", 4, ""));
        indianFoodList.add(new Food("Gulab Jamun (Snacks)", 40, "Deep-fried milk solid balls soaked in sugar syrup.", "valid_image_url_gulab_jamun_snacks", "Snacks", 4, ""));
        indianFoodList.add(new Food("Masala Pav", 55, "Bread roll stuffed with a spiced vegetable mixture.", "valid_image_url_masala_pav", "Snacks", 3, ""));
        indianFoodList.add(new Food("Pav Bhaji (Snacks)", 80, "A mashed vegetable curry served with buttered bread rolls.", "valid_image_url_pav_bhaji_snacks", "Snacks", 5, ""));
        indianFoodList.add(new Food("Misal Pav (Snacks)", 70, "A spicy lentil curry served with bread rolls.", "valid_image_url_misal_pav_snacks", "Snacks", 4, ""));
        indianFoodList.add(new Food("Medu Vada (Snacks)", 45, "Deep-fried lentil doughnuts.", "valid_image_url_medu_vada_snacks", "Snacks", 4, ""));
        indianFoodList.add(new Food("Onion Rings (Indian Style)", 50, "Gram flour coated and fried onion rings.", "valid_image_url_onion_rings", "Snacks", 3, ""));
        indianFoodList.add(new Food("French Fries (Indian Spiced)", 80, "Potato fries seasoned with Indian spices.", "valid_image_url_masala_fries", "Snacks", 4, ""));
        indianFoodList.add(new Food("Spring Rolls (Vegetable)", 70, "Crispy rolls filled with mixed vegetables.", "valid_image_url_veg_spring_rolls", "Snacks", 3, ""));
        indianFoodList.add(new Food("Chicken 65 (Snacks)", 120, "Spicy and deep-fried chicken pieces.", "valid_image_url_chicken_65_snacks", "Snacks", 4, ""));
        indianFoodList.add(new Food("Paneer Tikka (Snacks)", 110, "Grilled cubes of Indian cheese marinated in spices and yogurt.", "valid_image_url_paneer_tikka_snacks", "Snacks", 4, ""));
        indianFoodList.add(new Food("Veg Cutlet", 40, "Spiced vegetable patties, often breaded and fried.", "valid_image_url_veg_cutlet", "Snacks", 3, ""));
        indianFoodList.add(new Food("Corn Bhel", 50, "A savory snack made with corn, vegetables, and chutneys.", "valid_image_url_corn_bhel", "Snacks", 3, ""));
        indianFoodList.add(new Food("Bonda", 35, "Deep-fried potato or lentil fritters.", "valid_image_url_bonda", "Snacks", 3, ""));
        indianFoodList.add(new Food("Idli Fry (Snacks)", 50, "Steamed idlis that are then fried and seasoned.", "valid_image_url_idli_fry", "Snacks", 3, ""));
        indianFoodList.add(new Food("Wada Sambar (Snacks)", 55, "Lentil doughnuts served with sambar.", "valid_image_url_wada_sambar_snacks", "Snacks", 4, ""));
        indianFoodList.add(new Food("Dahi Vada (Snacks)", 60, "Lentil fritters soaked in yogurt and topped with chutneys.", "valid_image_url_dahi_vada_snacks", "Snacks", 4, ""));
        indianFoodList.add(new Food("Chivda", 40, "Flattened rice roasted or fried with spices, peanuts, and dry fruits.", "valid_image_url_chivda", "Snacks", 3, ""));
        indianFoodList.add(new Food("Murukku", 30, "Crispy, crunchy spirals made from rice flour and lentil flour.", "valid_image_url_murukku", "Snacks", 3, ""));
        indianFoodList.add(new Food("Chakli", 30, "Spiral-shaped, deep-fried snack made from rice flour and spices.", "valid_image_url_chakli", "Snacks", 3, ""));
        indianFoodList.add(new Food("Bhakarwadi", 40, "Spicy and sweet rolled pastry snack.", "valid_image_url_bhakarwadi", "Snacks", 3, ""));
        indianFoodList.add(new Food("Namkeen", 50, "A variety of savory Indian snacks.", "valid_image_url_namkeen", "Snacks", 3, ""));
        indianFoodList.add(new Food("Khakhra", 35, "Thin and crispy crackers made from wheat flour.", "valid_image_url_khakhra", "Snacks", 3, ""));
        indianFoodList.add(new Food("Chiwda (Snacks)", 40, "Similar to Chivda, a savory snack made from flattened rice.", "valid_image_url_chiwda_snacks", "Snacks", 3, ""));
        indianFoodList.add(new Food("Banana Chips", 30, "Thinly sliced and fried banana chips.", "valid_image_url_banana_chips", "Snacks", 3, ""));
        indianFoodList.add(new Food("Aloo Bhujia", 45, "Crispy and spicy potato noodles.", "valid_image_url_aloo_bhujia", "Snacks", 3, ""));
        indianFoodList.add(new Food("Mathri", 35, "Flaky and savory crackers.", "valid_image_url_mathri", "Snacks", 3, ""));
        indianFoodList.add(new Food("Khandvi", 60, "Rolled bite-sized snacks made from gram flour and yogurt.", "valid_image_url_khandvi", "Snacks", 3, ""));
        indianFoodList.add(new Food("Dhokla (as snack)", 55, "Steamed chickpea flour cake, can be eaten as a snack.", "valid_image_url_dhokla_snack", "Snacks", 4, ""));
        indianFoodList.add(new Food("Thepla (as snack)", 45, "Thin flatbread, can be enjoyed as a snack.", "valid_image_url_thepla_snack", "Snacks", 3, ""));
        indianFoodList.add(new Food("Poha Chivda (Snacks)", 40, "Flattened rice snack mix.", "valid_image_url_poha_chivda", "Snacks", 3, ""));
        indianFoodList.add(new Food("Masala Peanuts", 40, "Peanuts coated in spices and fried or roasted.", "valid_image_url_masala_peanuts", "Snacks", 3, ""));
        indianFoodList.add(new Food("Sukkha Bhel", 55, "A dry version of Bhel Puri.", "valid_image_url_sukkha_bhel", "Snacks", 3, ""));
        indianFoodList.add(new Food("Sabudana Vada (Snacks)", 50, "Deep-fried patties made from sago pearls and potatoes.", "valid_image_url_sabudana_vada", "Snacks", 3, ""));
        indianFoodList.add(new Food("Bread Roll (Fried)", 45, "Spiced potato filling inside a bread roll, deep-fried.", "valid_image_url_bread_roll", "Snacks", 3, ""));
        indianFoodList.add(new Food("Veg Manchurian (Dry)", 80, "Deep-fried vegetable balls tossed in a spicy sauce.", "valid_image_url_veg_manchurian_dry", "Snacks", 3, ""));
    }

    public void fetchFoodImages() {
        var ref = db.collection("food").document("indian");
        for (Food food : indianFoodList) {
            String query = food.getName().replace(" ", "+") + "+indian+food";
            String imageUrl = fetchImageUrl(query);
            Log.d(TAG, "fetchFoodImages: " + imageUrl + " for " + food.getName());
            food.setImage(imageUrl);
            var id = ref.collection(food.getCategory()).document().getId();
            food.setPath(id);
            ref.collection(food.getCategory()).document(id).set(food);

        }
    }

    private String fetchImageUrlUnsplash(String query) {
        String apiKey = BuildConfig.unspashAccessKey;
        String urlString = "https://api.unsplash.com/search/photos?page=1&query=" + query + "&client_id=" + apiKey;

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                Log.e(TAG, "fetchImageUrl: " + responseCode);
                return "https://www.istockphoto.com/photos/no-food-or-drink-sign"; // Fallback image
            }

            Scanner scanner = new Scanner(url.openStream());
            StringBuilder jsonResponse = new StringBuilder();
            while (scanner.hasNext()) {
                jsonResponse.append(scanner.nextLine());
            }
            scanner.close();

            JSONObject jsonObject = new JSONObject(jsonResponse.toString());
            JSONArray results = jsonObject.getJSONArray("results");

            if (results.length() > 0) {
                for (int i = 0; i < results.length(); i++) {
                    JSONObject result = results.getJSONObject(i);
                    String description = result.optString("description", "").toLowerCase();
//                if description contains food name
                    if (description.contains(query.toLowerCase())) {
                        Log.i(TAG, "fetchImageUrl: Find the name on description");
                        return result.getJSONObject("urls").getString("regular");
                    }
                }
//                else return random image
                Log.i(TAG, "fetchImageUrl: else");
                Random random = new Random(results.length());
                return results.getJSONObject(random.nextInt(results.length())).getJSONObject("urls").getString("regular"); // Extract image URL
            }

        } catch (IOException | JSONException e) {
            Log.e(TAG, "fetchImageUrl: " + e.getMessage());
        }

        return "";
    }

    private String fetchImageUrl(String query) {
        String apiKey = BuildConfig.pixelAccesKey;
        String urlString = "https://api.pexels.com/v1/search?query=" + query + "&per_page=80";

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Set authorization header
            conn.setRequestProperty("Authorization", apiKey);
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                Log.e(TAG, "fetchImageUrl: " + responseCode);
                return NO_IMAGE_FOUND;
            }

            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder jsonResponse = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonResponse.append(line);
            }
            reader.close();

            JSONObject responseObject = new JSONObject(jsonResponse.toString());
            JSONArray photos = responseObject.getJSONArray("photos");

            // Additional response metadata
            int totalResults = responseObject.getInt("total_results");
            int page = responseObject.getInt("page");
            int perPage = responseObject.getInt("per_page");
            String nextPage = responseObject.optString("next_page", "");

            Log.d(TAG, "Total Results: " + totalResults +
                    ", Page: " + page +
                    ", Per Page: " + perPage +
                    ", Next Page: " + nextPage);

            if (photos.length() > 0) {
                // First, try to find an image matching the query
                for (int i = 0; i < photos.length(); i++) {
                    JSONObject photo = photos.getJSONObject(i);
                    String alt = photo.optString("alt", "").toLowerCase();

                    // Check if alt text contains food-related keywords
                    if (alt.contains("food") || alt.contains("dish") || alt.contains("cuisine")) {
                        Log.i(TAG, "fetchImageUrl: Found food-related image");
                        return getPreferredImageUrl(photo);
                    }
                }

                // If no specific match, return a random image
                Log.i(TAG, "fetchImageUrl: Returning random image");
                Random random = new Random();
                return getPreferredImageUrl(photos.getJSONObject(random.nextInt(photos.length())));
            }

        } catch (IOException | JSONException e) {
            Log.e(TAG, "fetchImageUrl: " + e.getMessage());
        }

        return NO_IMAGE_FOUND;
    }

    // Helper method to get preferred image URL
    private String getPreferredImageUrl(JSONObject photo) throws JSONException {
        JSONObject srcUrls = photo.getJSONObject("src");

        // Prefer large2x, fallback to other sizes
        String[] preferredSizes = {"large2x", "large", "medium", "original"};

        for (String size : preferredSizes) {
            String url = srcUrls.optString(size);
            if (!TextUtils.isEmpty(url)) {
                return url;
            }
        }

        return NO_IMAGE_FOUND;
    }


}
