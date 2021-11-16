package com.serverless.dao.Impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.serverless.dao.IScoreDAO;
import com.serverless.model.Score;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class ScoreDAO extends AbstractDAO<Score> implements IScoreDAO {

  private Logger logger = Logger.getLogger(this.getClass());

  private static ScoreDAO scoreDAO = null;

  public static ScoreDAO getInstance() {
    if (scoreDAO == null) {
      scoreDAO = new ScoreDAO();
      scoreDAO.setType(Score.class);
    }
    return scoreDAO;
  }

  @Override
  public List<Score> findAll() {
    List<Score> scores = null;
    try {
      scores = query();
      logger.info("Get success list score!");
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("Score getList error! DAO");
    }
    return scores;
  }

  @Override
  public Score findScoreById(String id) {
    Score score = null;
    Map<String, AttributeValue> av = new HashMap<String, AttributeValue>();
    av.put(":v1", new AttributeValue().withS(id));
    DynamoDBQueryExpression<Score> query = new DynamoDBQueryExpression<Score>()
        .withKeyConditionExpression("id = :v1")
        .withExpressionAttributeValues(av);
    PaginatedQueryList<Score> results = this.getMapper().query(Score.class, query);
    return results.size() > 0 ? results.get(0) : null;
  }

  @Override
  public String save(Score score) {
    try {
      insert(score);
    } catch (Exception e) {
      return null;
    }
    return score.getId();
  }

  @Override
  public String update(String id, Score score) {
    try {
      update(score, id);
      logger.info("Score updated success!");
    } catch (Exception e) {
      logger.error("Update failed score!");
      return null;
    }
    return id;
  }

  @Override
  public String delete(Score score) {
    try {
      if (remove(score) != null) {
        logger.info("deleted score success");
        return score.getId();
      } else {
        logger.info("Deleted failed!");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
