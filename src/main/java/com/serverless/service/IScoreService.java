package com.serverless.service;

import com.serverless.model.Score;
import java.util.List;

public interface IScoreService {

  List<Score> findAll();

  Score findScoreById(String id);

  String save(Score score);

  String update(String id, Score score);

  String delete(Score score);
}
