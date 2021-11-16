package com.serverless.util;


import com.serverless.model.User;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

public class UpdatableBCrypt {

  private Logger logger = Logger.getLogger(this.getClass());

  private final int logRounds;

  public UpdatableBCrypt(int logRounds) {
    this.logRounds = logRounds;
  }

  public String hash(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt(logRounds));
  }

  public boolean verifyAndUpdateHash(String password, String hash,
      User user) {
    if (BCrypt.checkpw(password, hash)) {
      int rounds = getRounds(hash);
      // It might be smart to only allow increasing the rounds.
      // If someone makes a mistake the ability to undo it would be nice though.
      if (rounds != logRounds) {
        logger.debug("Updateing password from " + rounds + "rounds to " + logRounds);
        user.setPassword(hash(password));
      }
      return true;
    }
    return false;
  }

  /*
   * Copy pasted from BCrypt internals :(. Ideally a method
   * to exports parts would be public. We only care about rounds
   * currently.
   */
  private int getRounds(String salt) {
    char minor = (char) 0;
    int off = 0;
    if (salt.charAt(0) != '$' || salt.charAt(1) != '2') {
      throw new IllegalArgumentException("Invalid sale version");
    }
    if (salt.charAt(2) == '$') {
      off = 3;
    } else {
      minor = salt.charAt(2);
      if (minor != 'a' || salt.charAt(3) != '$') {
        throw new IllegalArgumentException("Invalid salt revision");
      }
      off = 4;
    }
    // Extract number of rounds
    if (salt.charAt(off + 2) > '$') {
      throw new IllegalArgumentException("Missing salt rounds");
    }
    return Integer.parseInt(salt.substring(off, off + 2));
  }
}
