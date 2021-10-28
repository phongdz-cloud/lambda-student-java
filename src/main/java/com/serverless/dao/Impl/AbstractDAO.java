package com.serverless.dao.Impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.serverless.connection.DynamoDBAdapter;
import com.serverless.dao.GenericDAO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class AbstractDAO<T> implements GenericDAO<T> {

  private Logger logger = Logger.getLogger(this.getClass());

  private Class<T> type = (Class<T>) this.getClass();

  private final DynamoDBMapper mapper;

  public AbstractDAO() {
    this.mapper = DynamoDBAdapter.getInstance()
        .createDbMapper(DynamoDBMapperConfig.builder().build());
  }

  public void setType(Class<T> type) {
    this.type = type;
  }

  @Override
  public List<T> query(Object... parameters) {
    if (parameters.length == 0) {
      DynamoDBScanExpression scanExp = new DynamoDBScanExpression();
      List<T> results = this.mapper.scan(type, scanExp);
      for (T p : results) {
        logger.info("findAll(): " + p.toString());
      }
      return results;
    } else {
      Object object = null;
      String id = (String) parameters[0];
      HashMap<String, AttributeValue> av = new HashMap<>();
      av.put(":v1", new AttributeValue().withS(id));
      DynamoDBQueryExpression<T> queryExp = new DynamoDBQueryExpression<T>()
          .withKeyConditionExpression("id = :v1")
          .withExpressionAttributeValues(av);
      PaginatedQueryList<T> results = this.mapper.query(type, queryExp);
      if (results.size() > 0) {
        object = results.get(0);
        logger.info("get(): " + object.toString());
      } else {
        logger.info("get(): Object - Not Found!");
      }
      return (List<T>) results.get(0);
    }
  }

  @Override
  public void insert(T object) {
    logger.info("Object - save(): " + object.toString());
    this.mapper.save(object);
  }

  @Override
  public void update(T object, String id) {
    try {
      mapper.save(object, buildExpression(id));
      logger.info("update successfully!");
    } catch (Exception e) {
      logger.error("update failed " + e);
    }
  }

  @Override
  public Boolean delete(T object, String id) {
    object = (T) query(object, id);
    if (object != null) {
      logger.info("delete(): " + object.toString());
      this.mapper.delete(object);
    } else {
      logger.info("delete(): does not exists!");
      return false;
    }
    return true;
  }

  private DynamoDBSaveExpression buildExpression(String id) {
    DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
    Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
    expectedAttributeValueMap.put("id",
        new ExpectedAttributeValue(new AttributeValue().withS(id)));
    dynamoDBSaveExpression.setExpected(expectedAttributeValueMap);
    return dynamoDBSaveExpression;
  }
}
