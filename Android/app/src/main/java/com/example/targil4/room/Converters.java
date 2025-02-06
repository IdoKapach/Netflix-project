package com.example.targil4.room;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static String fromList(List<String> movies) {
        return movies == null ? null : gson.toJson(movies);
    }

    @TypeConverter
    public static List<String> toList(String moviesJson) {
        if (moviesJson == null) return null;
        Type listType = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(moviesJson, listType);
    }
}

