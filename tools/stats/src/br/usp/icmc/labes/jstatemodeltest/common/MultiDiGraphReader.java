package br.usp.icmc.labes.jstatemodeltest.common;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.ds.Arc;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.ds.Vertex;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.ds.MultiDiGraph;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MultiDiGraphReader {
    private final File file;

    private boolean validFile;

    private final MultiDiGraph<String> multiDiGraph;

    public MultiDiGraphReader(File file) {
        this.file = file;
        this.multiDiGraph = new MultiDiGraph<>();
        toMultiDiGraph();
    }
    private void toMultiDiGraph(){
        this.validFile = true;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.file));
            String line = "";
            int index = 0;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.equals("") && !line.startsWith("#")) {
                    line = line.replaceAll(" -- ", " ");
                    line = line.replaceAll(" / ", " ");
                    line = line.replaceAll(" -> ", " ");
                    String[] token = line.split(" ");
                    if (token.length != 4)
                        throw new Exception("Non well formed transition");
                    Vertex<String> s1 = multiDiGraph.addVertex(token[0]);
                    Vertex<String> s2 = multiDiGraph.addVertex(token[3]);
                    Arc<String> arc = multiDiGraph.addArc(String.valueOf(index), s1.getLabel(), s2.getLabel(), token[1], token[2],100);
                    index++;
                }
            }
        } catch (Exception e) {
            this.validFile = false;
            e.printStackTrace();
        }
    }
    public boolean isValidFile() {
        return this.validFile;
    }
}
