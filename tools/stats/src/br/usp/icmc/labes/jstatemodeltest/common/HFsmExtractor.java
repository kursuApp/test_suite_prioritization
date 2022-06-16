package br.usp.icmc.labes.jstatemodeltest.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class HFsmExtractor {
  public static void extract(File file) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String line = "";
      while ((line = reader.readLine()) != null) {
        line = line.trim();
        if (line.startsWith("FSM") || line.startsWith("s") || line.startsWith("i") || 
          line.startsWith("o") || line.startsWith("n"))
          continue; 
        if (line.startsWith("H test for"))
          break; 
        if (!line.equals("")) {
          String[] tokens = line.split(" ");
          System.out.println(String.valueOf(tokens[0]) + " -- " + tokens[1] + " / " + tokens[3] + " -> " + tokens[2]);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public static void main(String[] args) {
    if (args.length >= 1) {
      File file = new File(args[0]);
      extract(file);
      System.exit(0);
    } 
    System.exit(-1);
  }
}
