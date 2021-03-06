package com.example.user.userService.request;

import javax.validation.constraints.*;

public class UserRequest {

    @NotEmpty(message = "name should not be empty")
    private String name;

    @NotEmpty(message = "surname should not be empty")
    private String surname;

    @Positive(message = "age should be positive")
    @Min(value = 18, message = "Age should not be less than 18")
    @Max(value = 70, message = "Age should not be greater than 70")
    private Integer age;

    @Email(message = "email should be valid")
    private String email;

    private String mobile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
