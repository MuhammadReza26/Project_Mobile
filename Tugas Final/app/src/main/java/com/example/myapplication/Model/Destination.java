package com.example.myapplication.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Destination implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("country")
    private String country;
    @SerializedName("continent")
    private String continent;
    @SerializedName("description")
    private String description;
    @SerializedName("image")
    private String image;
    @SerializedName("population")
    private String population;
    @SerializedName("currency")
    private String currency;
    @SerializedName("language")
    private String language;
    @SerializedName("best_time_to_visit")
    private String best_time_to_visit;
    @SerializedName("top_attractions")
    private List<String> top_attractions;
    @SerializedName("local_dishes")
    private List<String> local_dishes;
    @SerializedName("activities")
    private List<String> activities;


    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getBest_time_to_visit() {
        return best_time_to_visit;
    }

    public void setBest_time_to_visit(String best_time_to_visit) {
        this.best_time_to_visit = best_time_to_visit;
    }

    public List<String> getTop_attractions() {
        return top_attractions;
    }

    public void setTop_attractions(List<String> top_attractions) {
        this.top_attractions = top_attractions;
    }

    public List<String> getLocal_dishes() {
        return local_dishes;
    }

    public void setLocal_dishes(List<String> local_dishes) {
        this.local_dishes = local_dishes;
    }

    public List<String> getActivities() {
        return activities;
    }

    public void setActivities(List<String> activities) {
        this.activities = activities;
    }
}
