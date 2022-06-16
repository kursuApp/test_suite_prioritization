package br.usp.icmc.labes.jstatemodeltest.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class StateCountingSize {
  public static void main(String[] args) {
    if (args.length >= 1) {
      File file = new File(args[0]);
      try {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = "";
        while ((line = reader.readLine()) != null) {
          line = line.trim();
          if (line.startsWith(">>>Total Length for SC: ")) {
            System.out.println(line.replace(">>>Total Length for SC: ", ""));
            break;
          } 
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } 
      System.exit(0);
    } 
    System.exit(-1);
  }
}
