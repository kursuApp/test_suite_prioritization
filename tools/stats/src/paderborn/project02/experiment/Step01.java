package paderborn.project02.experiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import org.junit.Test;

public class Step01 {
  @Test
  public void test01() {
    File mapfile = new File("./project02-experiment/prices/prices-input2inputoutput.txt");
    ArrayList<String> events = new ArrayList<String>();
    ArrayList<String> inputoutput_pairs = new ArrayList<String>();
    try {
      BufferedReader reader = new BufferedReader(new FileReader(mapfile));
      String line = "";
      while ((line = reader.readLine()) != null) {
        line = line.trim();
        String[] lines = line.split(";");
        events.add(lines[0]);
        inputoutput_pairs.add(lines[1]);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    File file = new File("./project02-experiment/prices/prices-deterministic.dot");
    try {
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String line = "";
      while ((line = reader.readLine()) != null) {
        line = line.trim();
        for (int i = 0; i < events.size(); i++) {
          String lookedEvent = "\"" + (String)events.get(i) + "\"";
          if (line.contains(lookedEvent)) {
            String newPair = "\"" + (String)inputoutput_pairs.get(i) + "\"";
            line = line.replace(lookedEvent, newPair);
            break;
          } 
        } 
        System.out.println(line);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  @Test
  public void test02() {
    File file = new File("./project02-experiment/prices/prices-with-outputs.dot");
    try {
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String line = "";
      while ((line = reader.readLine()) != null) {
        line = line.trim();
        if (line.startsWith("\"")) {
          String[] tokens = line.split(" ");
          String state1 = tokens[0].replace("\"", "");
          state1 = state1.replace(" ", "_");
          String state2 = tokens[2].replace("\"", "");
          state2 = state2.replace(" ", "_");
          tokens = line.split("= ");
          String transition = tokens[1].replace(" ];", "");
          tokens = transition.split(" / ");
          String input = tokens[0].replace("\"", "");
          input = input.replace(" ", "_");
          String[] tmp_inputs = input.split("_");
          input = tmp_inputs[0];
          String output = tokens[1].replace("\"", "");
          output = output.replace(" ", "_");
          System.out.println(String.valueOf(state1) + " -- " + input + " / " + output + " -> " + state2);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
