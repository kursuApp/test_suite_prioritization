package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.genmethod.heuHsi;

import br.usp.icmc.labes.jstatemodeltest.common.FsmModelReader;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.FiniteStateMachine;

import java.io.*;
import java.util.ArrayList;


public class Atlas {
    public ArrayList<String> getTS(String ts) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(ts));
        ArrayList<String> tt = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            tt.add(line.trim());
        }
        return tt;
    }
    public void write(String fileName,ArrayList<String> ts) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        for(String t : ts){
            writer.write(t);
            writer.newLine();
        }
        writer.close();
    }


    public static void main(String[] args) throws Exception {
        String fsmFileName = "/home/savas/Dropbox/stats/test/fsm.txt";
        String ts = "/home/savas/Dropbox/stats/test/ts_atlas.txt";
        String outputFile = "/home/savas/Dropbox/stats/test/sonuc.txt";

        FsmModelReader reader = new FsmModelReader(new File(fsmFileName), false);
        FiniteStateMachine fsm = reader.getFsm();
        Atlas atlas = new Atlas();
        TraditionalHsiMethod method = new TraditionalHsiMethod(fsm);
        method.generateAtlas(atlas.getTS(ts));
        atlas.write(outputFile,method.sequences);
    }
}
