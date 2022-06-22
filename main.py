import math

import networkx as nx
import pandas as pd

import edge_priority
import iter_luna
from temp import luna_util
import mutation_testing
from iter_luna_util import get_test_suite_by_edge

state_size = 10
input_size = 2
output_size = 1
file_path = "temp/output.fsm"
apfd = pd.DataFrame(columns=["input_size", "state_size", "output_size", "transition_size", "algorithm",
                             "betweenness", "degree", "closeness", "random"])
for algorithm in ["P"]:
    for state_size in range(5, 30):
        luna_util.create_fsm(state_size, input_size, output_size, file_path)
        graph, start_node = luna_util.create_graph_from_kiss2(file_path)
        luna_util.create_mutlu_from_kiss2(graph, file_path)
        test_suite = luna_util.run(file_path, algorithm)
        test_suite = luna_util.get_test_suite2(test_suite.stdout,input_size)
        if len(test_suite) < 1:
            pass
        test_suite_by_edge = get_test_suite_by_edge(graph, start_node, test_suite)
        betweenness_centrality = nx.edge_betweenness_centrality(graph)
        degree_centrality = edge_priority.convert_to_edge_centrality(graph, nx.degree_centrality(graph))
        closeness_centrality = edge_priority.convert_to_edge_centrality(graph, nx.closeness_centrality(graph))
        degree_test_suite = iter_luna.run(degree_centrality, test_suite_by_edge)
        betweenness_test_suite = iter_luna.run(betweenness_centrality, test_suite_by_edge)
        closeness_test_suite = iter_luna.run(closeness_centrality, test_suite_by_edge)
        result = dict()
        result["input_size"] = input_size
        result["state_size"] = state_size
        result["output_size"] = output_size
        result["transition_size"] = state_size * math.pow(2, input_size) - 1
        result["algorithm"] = algorithm
        result["betweenness"] = mutation_testing.run(graph, betweenness_test_suite)
        result["degree"] = mutation_testing.run(graph, degree_test_suite)
        result["closeness"] = mutation_testing.run(graph, closeness_test_suite)
        result["random"] = mutation_testing.run(graph, test_suite_by_edge)
        apfd.loc[len(apfd.index)] = result
        print(result)
        apfd.to_excel("hsi.xlsx", index=False, header=True)

