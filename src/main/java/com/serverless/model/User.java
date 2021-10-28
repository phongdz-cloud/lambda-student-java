package com.serverless.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "user_table")
public class User {

  private String userId;
  private String password;
  private Role role;

  @DynamoDBHashKey(attributeName = "user_id")
  public String getUserId() {
    return userId;
  }

  @DynamoDBAttribute(attributeName = "password")
  public String getPassword() {
    return password;
  }

  @DynamoDBAttribute(attributeName = "role")
  public Role getRole() {
    return role;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return String.format("User [user_id = %s, password=%s]", this.userId, this.password);
  }
}
