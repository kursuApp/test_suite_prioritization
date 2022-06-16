package br.usp.icmc.labes.jstatemodeltest.testgen.fsm.auxiliar;

import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.ds.Arc;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.ds.Pair;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.ds.MultiDiGraph;
import br.usp.icmc.labes.jstatemodeltest.testgen.fsm.ds.Vertex;


import java.util.*;

public class AtlasSetConstructor {

    public AtlasSetConstructor(MultiDiGraph<String> graph) {

    }
    void topological_sort(MultiDiGraph<String> graph, Vertex<String> vertex, HashSet<String> visited, Stack<Vertex<String>> stack){
        visited.add(vertex.getLabel());
        for (String arcLabel: graph.getArcs(vertex.getLabel())){
            if (!visited.contains(graph.getArc(arcLabel).getDestNodeLabel())){
                topological_sort(graph, graph.getVertex(graph.getArc(arcLabel).getDestNodeLabel()), visited, stack);
            }
        }
        stack.push(vertex);
    }

    Pair<LinkedList<Arc<String>>, MultiDiGraph<String>> longest_path(MultiDiGraph<String> graph, String start){
        Stack<Vertex<String>> stack = new Stack<>();
        HashMap<String, Double> cost = new HashMap<>();
        String end = start;
        double end_cost = Double.NEGATIVE_INFINITY;
        HashSet<String> visited = new HashSet<>();
        HashMap<String, String> comes_from = new HashMap<>();
        HashMap<String,LinkedList<String>> self = new HashMap<>();
        MultiDiGraph<String> reversedGraph = new MultiDiGraph<>();
        for (Vertex<String> vertex : graph.getVertices()){
            if (!visited.contains(vertex.getLabel())){
                topological_sort(graph, vertex, visited, stack);
            }
        }
        for (Vertex<String> vertex : graph.getVertices()){
            cost.put(vertex.getLabel(), Double.NEGATIVE_INFINITY);
        }
        cost.put(start, 0.0);
        HashSet<String> contained = new HashSet<>();
        while (!stack.empty()){
            Vertex<String> vertex = stack.pop();
            contained.add(vertex.getLabel());
            for (String arcLabel : graph.getArcs(vertex.getLabel())){
                String neighbor = graph.getArc(arcLabel).getDestNodeLabel();
                if (!contained.contains(neighbor)){
                    Double new_cost = cost.get(vertex.getLabel()) + graph.getArc(arcLabel).getWeight();
                    if (cost.get(neighbor) < new_cost ){
                        cost.put(neighbor, new_cost);
                        comes_from.put(neighbor, graph.getArc(arcLabel).getLabel());
                        if (end_cost < new_cost){
                            end_cost = new_cost;
                            end = neighbor;
                        }
                    }
                }
                else if (vertex.getLabel().equals(neighbor)){
                    if (!self.containsKey(vertex.getLabel()))
                        self.put(vertex.getLabel(), new LinkedList<>());
                    self.get(vertex.getLabel()).add(arcLabel);
                }
                else if (!vertex.getLabel().equals(neighbor)){
                    reversedGraph.addArc(graph.getArc(arcLabel));
                }
            }
        }
        LinkedList<Arc<String>> path = new LinkedList<>();
        path.addFirst(graph.getArc(comes_from.get(end)));
        while (!comes_from.containsKey(path.getFirst().getDestNodeLabel())){
            Vertex<String> node = graph.getVertex(path.getFirst().getDestNodeLabel());
            path.addFirst(graph.getArc(comes_from.get(node.getLabel())));
        }
        return new Pair<>(path, reversedGraph);
    }


    private Pair<LinkedList<Arc<String>>,Set<String>> update(MultiDiGraph<String> graph, String start, Set<String> remainedArcs) {
        Pair<LinkedList<Arc<String>>, MultiDiGraph<String>> result1 = longest_path(graph, start);
        LinkedList<Arc<String>> path = result1.getLeft();
        for ( Arc<String> arc : result1.getLeft()){
            if (arc.getWeight() > 1 ){
                arc.setWeight(1);
                remainedArcs.remove(arc.getLabel());
            }
        }
        String reversedStart = result1.getLeft().getLast().getDestNodeLabel();
        if (reversedStart != null){
            Pair<LinkedList<Arc<String>>, MultiDiGraph<String>> result2 = longest_path(result1.getRight(), reversedStart);
            path.addAll(result2.getLeft());
            for ( Arc<String> arc : result2.getLeft()){
                if (arc.getWeight() > 1 ){
                    arc.setWeight(1);
                    remainedArcs.remove(arc.getLabel());
                }
            }

        }
        return new Pair<>(path, remainedArcs);
    }

    public LinkedList<LinkedList<Arc<String>>> generate(MultiDiGraph<String> graph, String start){
        LinkedList<LinkedList<Arc<String>>> paths = new LinkedList<>();
        Set<String> arcs = graph.getArcs();
        Pair<LinkedList<Arc<String>>,Set<String>> result = update(graph, start, arcs);
        paths.add(result.getLeft());
        while (result.getLeft().size() != 0){
            int arcSize = result.getRight().size();
            result = update(graph, start, result.getRight());
            if (arcSize == result.getRight().size()){
                return paths;
            }
            paths.add(result.getLeft());
        }
        return paths;
    }

    public LinkedList<String> getTestSuite(LinkedList<LinkedList<Arc<String>>> paths){
        LinkedList<String> testSuite = new LinkedList<>();
        for (LinkedList<Arc<String>> path : paths){
            StringBuilder testCase = new StringBuilder();
            for (Arc<String> arc : path){
                testCase.append(arc.getInput());
            }
            testSuite.add(testCase.toString());
        }
        return testSuite;
    }

    public LinkedList<String> run(MultiDiGraph<String> graph){
        LinkedList<LinkedList<Arc<String>>> paths = generate(graph, "0");
        return getTestSuite(paths);
    }
}
