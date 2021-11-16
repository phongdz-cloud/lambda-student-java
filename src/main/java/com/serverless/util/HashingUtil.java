package com.serverless.util;

import com.serverless.model.User;

public class HashingUtil {

  // this sould be updated every year or two
  private static final UpdatableBCrypt bcrypt = new UpdatableBCrypt(11);

  public static String hash(String password) {
    return bcrypt.hash(password);
  }

  public static boolean verifyAndUpdateHash(String password, String hash,
      User user) {
    return bcrypt.verifyAndUpdateHash(password, hash, user);
  }
}
