package com.ionutradu.whistspringbootthymeleaf.authentication.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.List;

@Document("users")
public class User {
    @Id
    private ObjectId _id;
    private String username;
    private String password;
    @DBRef
    private Collection<Role> roles;

    public void set_id(ObjectId _id) { this._id = _id; }
    public String get_id() { return this._id.toHexString(); }
    public void setPassword(String password) { this.password = password; }
    public String getPassword() { return password; }
    public void setUsername(String username) { this.username = username; }
    public String getUsername() { return username; }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
