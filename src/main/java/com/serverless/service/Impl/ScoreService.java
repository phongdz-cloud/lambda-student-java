package com.serverless.service.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.constant.Constant;
import com.serverless.dao.ICacheDAO;
import com.serverless.dao.IScoreDAO;
import com.serverless.dao.Impl.CacheDAO;
import com.serverless.dao.Impl.ScoreDAO;
import com.serverless.model.Score;
import com.serverless.service.IScoreService;
import java.util.List;

public class ScoreService implements IScoreService {


  private IScoreDAO scoreDAO = ScoreDAO.getInstance();

  private ICacheDAO cache = new CacheDAO();

  private ObjectMapper mapper = new ObjectMapper();

  @Override
  public List<Score> findAll() {
    List<Score> results = null;
    try {
      String jsonString = (String) cache.getObject(Constant.FINDALLSCORE);
      if (jsonString == null) {
        System.out.println("Chạm cache server");
        results = scoreDAO.findAll();
        cache.setObject(Constant.FINDALLSCORE, mapper.writeValueAsString(results));
      } else {
        System.out.println("Không Chạm cache server");
        results = mapper.readValue(jsonString, new TypeReference<List<Score>>() {
        });
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return results;
  }

  @Override
  public Score findScoreById(String id) {
    Score score = (Score) cache.getObject(id);
    if (score == null) {
      score = scoreDAO.findScoreById(id);
      cache.setObject(id, score);
    }
    return score;
  }

  @Override
  public String save(Score score) {
    if (scoreDAO.save(score) != null) {
      cache.updateFindALL(Constant.FINDALLSCORE, scoreDAO.findAll());
    }
    return "create score success!";
  }

  @Override
  public String update(String id, Score score) {
    if (scoreDAO.update(id, score) != null) {
      cache.updateFindALL(Constant.FINDALLSCORE, scoreDAO.findAll());
      cache.updateFindBy(id, score);
    }
    return "update score success!";
  }

  @Override
  public String delete(Score score) {
    if (scoreDAO.delete(score) != null) {
      cache.updateFindALL(Constant.FINDALLSCORE, scoreDAO.findAll());
      cache.deleteCache(score.getId());
    }
    return scoreDAO.delete(score);
  }
}
