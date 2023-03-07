package com.lasgcircular.softcitygroup.model;

public class UserInformationModel {
    private String profilePic;
    private String lastName;
    private String createdAt;
    private Object emailVerifiedAt;
    private boolean isPasswordChanged;
    private String userType;
    private String updatedAt;
    private String phone;
    private int roleId;
    private String name;
    private int id;
    private String email;
    private boolean isApiUser;

    public UserInformationModel(String profilePic,
                                String lastName,
                                String createdAt,
                                Object emailVerifiedAt,
                                boolean isPasswordChanged,
                                String userType,
                                String updatedAt,
                                String phone,
                                int roleId,
                                String name,
                                int id,
                                String email,
                                boolean isApiUser) {

        this.profilePic = profilePic;
        this.lastName = lastName;
        this.createdAt = createdAt;
        this.emailVerifiedAt = emailVerifiedAt;
        this.isPasswordChanged = isPasswordChanged;
        this.userType = userType;
        this.updatedAt = updatedAt;
        this.phone = phone;
        this.roleId = roleId;
        this.name = name;
        this.id = id;
        this.email = email;
        this.isApiUser = isApiUser;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Object getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public boolean getIsPasswordChanged() {
        return isPasswordChanged;
    }

    public String getUserType() {
        return userType;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getPhone() {
        return phone;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public boolean getIsApiUser() {
        return isApiUser;
    }
}
