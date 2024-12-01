package edu.northeastern.numad24fa_group15project.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements Serializable {

    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String school;
    private List<String> interests;
    private List<String> dietaryRestrictions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public List<String> getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void setDietaryRestrictions(List<String> dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public void updateFromMap(Map<String, Object> data) {
        if (data.containsKey("firstName")) this.firstName = (String) data.get("firstName");
        if (data.containsKey("lastName")) this.lastName = (String) data.get("lastName");
        if (data.containsKey("phone")) this.phone = (String) data.get("phone");
        if (data.containsKey("school")) this.school = (String) data.get("school");
        if (data.containsKey("interests")) this.interests = (List<String>) data.get("interests");
        if (data.containsKey("dietaryRestrictions")) this.dietaryRestrictions = (List<String>) data.get("dietaryRestrictions");
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("email", email);
        map.put("password", password);
        map.put("firstName", firstName);
        map.put("lastName", lastName);
        map.put("phone", phone);
        map.put("school", school);
        map.put("interests", interests);
        map.put("dietaryRestrictions", dietaryRestrictions);
        return map;
    }
}
