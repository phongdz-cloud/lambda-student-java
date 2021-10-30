package com.serverless.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import java.io.Serializable;

@DynamoDBTable(tableName = "role_table")
@DynamoDBDocument
public class Role implements Serializable {

  private String id;
  private String name;


  @DynamoDBHashKey(attributeName = "id")
  @DynamoDBAutoGeneratedKey
  public String getId() {
    return id;
  }

  @DynamoDBAttribute(attributeName = "name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "Role{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        '}';
  }
}
