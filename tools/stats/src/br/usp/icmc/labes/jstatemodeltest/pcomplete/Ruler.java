package br.usp.icmc.labes.jstatemodeltest.pcomplete;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.TestSequence;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ruler {
  private static Logger logger = Logger.getAnonymousLogger();
  
  ArrayList<Pair> C;
  
  ArrayList<Pair> D;
  
  ArrayList<String> T;
  
  ArrayList<Trigger> queue;
  
  public enum AddedTo {
    C, D;
  }
  
  public void setQueue(ArrayList<Trigger> queue) {
    this.queue = queue;
  }
  
  public static Logger getLogger() {
    return logger;
  }
  
  public ArrayList<Trigger> getQueue() {
    return this.queue;
  }
  
  public Ruler(ArrayList<Pair> C, ArrayList<Pair> D, ArrayList<String> T) {
    logger.setLevel(Level.OFF);
    this.C = C;
    this.D = D;
    this.T = T;
    this.queue = new ArrayList<Trigger>();
  }
  
  public void applyRules(Pair newpair, AddedTo addedTo) {
    logger.info("applying rules");
    this.queue.clear();
    this.queue.add(new Trigger(newpair, addedTo));
    while (this.queue.size() >= 1) {
      Trigger current = this.queue.remove(0);
      Pair pair = current.getPair();
      if (current.getAddedto() == AddedTo.C) {
        rule01(pair);
        rule02(pair);
        rule04(pair);
        rule07(pair);
        rule09(pair);
        continue;
      } 
      if (current.getAddedto() == AddedTo.D) {
        rule03(pair);
        rule05(pair);
        rule06(pair);
        rule08(pair);
        rule10(pair);
      } 
    } 
  }
  
  public void rule01(Pair newpair) {
    ArrayList<Pair> temp = new ArrayList<Pair>();
    String alpha1 = newpair.getLeft();
    String beta1 = newpair.getRight();
    String alpha2 = newpair.getRight();
    String beta2 = newpair.getLeft();
    for (Pair pair : this.C) {
      if (pair.getLeft().equals(alpha1)) {
        String chi = pair.getRight();
        temp.add(new Pair(beta1, chi));
        continue;
      } 
      if (pair.getRight().equals(alpha1)) {
        String chi = pair.getLeft();
        temp.add(new Pair(beta1, chi));
        continue;
      } 
      if (pair.getLeft().equals(alpha2)) {
        String chi = pair.getRight();
        temp.add(new Pair(beta2, chi));
        continue;
      } 
      if (pair.getRight().equals(alpha2)) {
        String chi = pair.getLeft();
        temp.add(new Pair(beta2, chi));
      } 
    } 
    for (Pair p : temp) {
      if (Pair.add(this.C, p)) {
        logger.info("Rule 01 " + p);
        Trigger t = new Trigger(p, AddedTo.C);
        this.queue.add(t);
      } 
    } 
  }
  
  public void rule02(Pair newpair) {
    String alpha = newpair.getLeft();
    String beta = newpair.getRight();
    for (String fi1 : this.T) {
      if (TestSequence.isProperPrefixOf(alpha, fi1)) {
        String fi1_1 = TestSequence.getSuffixFrom(fi1, alpha);
        for (String fi2 : this.T) {
          if (TestSequence.isProperPrefixOf(beta, fi2)) {
            String fi2_2 = TestSequence.getSuffixFrom(fi2, beta);
            if (fi1_1.equals(fi2_2)) {
              Pair p = new Pair(fi1, fi2);
              if (Pair.add(this.C, p)) {
                logger.info("Rule 02 " + p);
                Trigger t = new Trigger(p, AddedTo.C);
                this.queue.add(t);
              } 
            } 
          } 
        } 
      } 
    } 
  }
  
  public void rule03(Pair newpair) {
    ArrayList<String> gammas = TestSequence.getCommonSuffixesFrom(newpair.getLeft(), newpair.getRight());
    gammas.remove("EPSILON");
    for (String gamma : gammas) {
      String alpha_ = TestSequence.getPrefixFrom(newpair.getLeft(), gamma);
      String beta_ = TestSequence.getPrefixFrom(newpair.getRight(), gamma);
      Pair p = new Pair(alpha_, beta_);
      if (Pair.add(this.D, p)) {
        logger.info("Rule 03 " + p);
        Trigger t = new Trigger(p, AddedTo.D);
        this.queue.add(t);
      } 
    } 
  }
  
  public void rule04(Pair newpair) {
    String alpha = newpair.getLeft();
    String beta = newpair.getRight();
    for (String chi : this.T) {
      if (Pair.in(new Pair(alpha, chi), this.D)) {
        Pair p = new Pair(beta, chi);
        if (Pair.add(this.D, p)) {
          logger.info("Rule 04 " + p);
          Trigger t = new Trigger(p, AddedTo.D);
          this.queue.add(t);
        } 
      } 
      if (Pair.in(new Pair(beta, chi), this.D)) {
        Pair p = new Pair(alpha, chi);
        if (Pair.add(this.D, p)) {
          logger.info("Rule 04 " + p);
          Trigger t = new Trigger(p, AddedTo.D);
          this.queue.add(t);
        } 
      } 
    } 
  }
  
  public void rule05(Pair newpair) {
    String alpha = newpair.getLeft();
    String beta = newpair.getRight();
    for (String chi : this.T) {
      if (Pair.in(new Pair(alpha, chi), this.C)) {
        Pair p = new Pair(beta, chi);
        if (Pair.add(this.D, p)) {
          logger.info("Rule 05 " + p);
          Trigger t = new Trigger(p, AddedTo.D);
          this.queue.add(t);
        } 
      } 
      if (Pair.in(new Pair(beta, chi), this.C)) {
        Pair p = new Pair(alpha, chi);
        if (Pair.add(this.D, p)) {
          logger.info("Rule 05 " + p);
          Trigger t = new Trigger(p, AddedTo.D);
          this.queue.add(t);
        } 
      } 
    } 
  }
  
  public void rule06(Pair newpair) {
    String alpha = newpair.getLeft();
    String beta = newpair.getRight();
    if (TestSequence.isProperPrefixOf(beta, alpha)) {
      String temp = alpha;
      alpha = beta;
      beta = temp;
    } 
    if (TestSequence.isProperPrefixOf(alpha, beta)) {
      String fi = TestSequence.getSuffixFrom(beta, alpha);
      for (String fi2 : getPotency(fi)) {
        Pair p = new Pair(alpha, TestSequence.concat(alpha, fi2));
        if (Pair.add(this.D, p)) {
          logger.info("Rule 06 " + p);
          Trigger t = new Trigger(p, AddedTo.D);
          this.queue.add(t);
        } 
      } 
    } 
  }
  
  public ArrayList<String> getPotency(String fi) {
    ArrayList<String> ret = new ArrayList<String>();
    String[] fis = fi.split(",");
    for (int k = 2; k <= fis.length; k++) {
      String[] sep = new String[k];
      boolean canbe = false;
      for (int i = 0; i < k; i++) {
        int seqlen = fis.length / k;
        if (seqlen * k == fis.length) {
          canbe = true;
          sep[i] = "";
          for (int j = i * seqlen; j < i * seqlen + seqlen; j++)
            sep[i] = String.valueOf(sep[i]) + "," + fis[j]; 
          sep[i] = sep[i].replaceFirst(",", "");
        } 
      } 
      if (canbe) {
        boolean equals = true;
        for (int j = 1; j < k; j++) {
          if (!sep[j].equals(sep[j - 1])) {
            equals = false;
            break;
          } 
        } 
        if (equals)
          ret.add(sep[0]); 
      } 
    } 
    return ret;
  }
  
  public void rule07(Pair newpair) {
    String alpha = newpair.getShorter();
    String sequence = newpair.getlonger();
    ArrayList<Pair> temp = new ArrayList<Pair>();
    if (TestSequence.isProperPrefixOf(alpha, sequence)) {
      String betagamma = TestSequence.getSuffixFrom(sequence, alpha);
      for (Pair pair : this.D) {
        if (pair.getLeft().equals(alpha) && TestSequence.isProperPrefixOf(alpha, pair.getRight())) {
          String gamma = TestSequence.getSuffixFrom(pair.getRight(), alpha);
          if (TestSequence.isProperSufixOf(gamma, betagamma)) {
            String beta = TestSequence.getPrefixFrom(betagamma, gamma);
            temp.add(new Pair(alpha, TestSequence.concat(alpha, beta)));
          } 
        } 
        if (pair.getRight().equals(alpha) && TestSequence.isProperPrefixOf(alpha, pair.getLeft())) {
          String gamma = TestSequence.getSuffixFrom(pair.getLeft(), alpha);
          if (TestSequence.isProperSufixOf(gamma, betagamma)) {
            String beta = TestSequence.getPrefixFrom(betagamma, gamma);
            temp.add(new Pair(alpha, TestSequence.concat(alpha, beta)));
          } 
        } 
      } 
      for (Pair pair : temp) {
        if (Pair.add(this.D, pair)) {
          logger.info("Rule 07 " + pair);
          Trigger t = new Trigger(pair, AddedTo.D);
          this.queue.add(t);
        } 
      } 
    } 
  }
  
  public void rule08(Pair newpair) {
    String alpha = newpair.getShorter();
    String seq = newpair.getlonger();
    if (TestSequence.isProperPrefixOf(alpha, seq)) {
      String gamma = TestSequence.getSuffixFrom(seq, alpha);
      for (Pair pair : this.C) {
        if (pair.getLeft().equals(alpha) && TestSequence.isProperPrefixOf(alpha, pair.getRight())) {
          String betagamma = TestSequence.getSuffixFrom(pair.getRight(), alpha);
          if (TestSequence.isProperSufixOf(gamma, betagamma)) {
            String beta = TestSequence.getPrefixFrom(betagamma, gamma);
            Pair p = new Pair(alpha, TestSequence.concat(alpha, beta));
            if (Pair.add(this.D, p)) {
              logger.info("Rule 08 " + p);
              Trigger t = new Trigger(p, AddedTo.D);
              this.queue.add(t);
            } 
          } 
        } 
        if (pair.getRight().equals(alpha) && TestSequence.isProperPrefixOf(alpha, pair.getLeft())) {
          String betagamma = TestSequence.getSuffixFrom(pair.getLeft(), alpha);
          if (TestSequence.isProperSufixOf(gamma, betagamma)) {
            String beta = TestSequence.getPrefixFrom(betagamma, gamma);
            Pair p = new Pair(alpha, TestSequence.concat(alpha, beta));
            if (Pair.add(this.D, p)) {
              logger.info("Rule 08 " + p);
              Trigger t = new Trigger(p, AddedTo.D);
              this.queue.add(t);
            } 
          } 
        } 
      } 
    } 
  }
  
  public void rule09(Pair newpair) {
    String alpha = newpair.getShorter();
    String seq = newpair.getlonger();
    if (TestSequence.isProperPrefixOf(alpha, seq)) {
      ArrayList<Pair> temp = new ArrayList<Pair>();
      String gamma = TestSequence.getSuffixFrom(seq, alpha);
      for (Pair pair : this.D) {
        if (TestSequence.isProperSufixOf(gamma, pair.getRight())) {
          String beta = TestSequence.getPrefixFrom(pair.getRight(), gamma);
          if (beta.equals(pair.getLeft()))
            temp.add(new Pair(alpha, beta)); 
        } 
        if (TestSequence.isProperSufixOf(gamma, pair.getLeft())) {
          String beta = TestSequence.getPrefixFrom(pair.getLeft(), gamma);
          if (beta.equals(pair.getRight()))
            temp.add(new Pair(alpha, beta)); 
        } 
      } 
      for (Pair pair : temp) {
        if (Pair.add(this.D, pair)) {
          logger.info("Rule 09 " + pair);
          Trigger t = new Trigger(pair, AddedTo.D);
          this.queue.add(t);
        } 
      } 
    } 
  }
  
  public void rule10(Pair newpair) {
    String beta = newpair.getShorter();
    String seq = newpair.getlonger();
    if (TestSequence.isProperPrefixOf(beta, seq)) {
      String gamma = TestSequence.getSuffixFrom(seq, beta);
      for (Pair pair : this.C) {
        if (TestSequence.isProperSufixOf(gamma, pair.getRight())) {
          String alpha = TestSequence.getPrefixFrom(pair.getRight(), gamma);
          if (alpha.equals(pair.getLeft())) {
            Pair p = new Pair(alpha, beta);
            if (Pair.add(this.D, p)) {
              logger.info("Rule 10 " + p);
              Trigger t = new Trigger(p, AddedTo.D);
              this.queue.add(t);
            } 
          } 
        } 
        if (TestSequence.isProperSufixOf(gamma, pair.getLeft())) {
          String alpha = TestSequence.getPrefixFrom(pair.getLeft(), gamma);
          if (alpha.equals(pair.getRight())) {
            Pair p = new Pair(alpha, beta);
            if (Pair.add(this.D, p)) {
              logger.info("Rule 10 " + p);
              Trigger t = new Trigger(p, AddedTo.D);
              this.queue.add(t);
            } 
          } 
        } 
      } 
    } 
  }
}
