import networkx as nx

import edge_priority
import iter_luna
import luna_util
import mutation_testing
from iter_luna_util import get_test_suite_by_edge

state_size = 10
input_size = 2
output_size = 1
file_path = "output.fsm"
luna_util.create_fsm(state_size, input_size, output_size, file_path)
result = dict()
graph, start_node = luna_util.create_graph_from_kiss2(file_path)
luna_util.create_mutlu_from_kiss2(graph, file_path)
test_suite = luna_util.run(file_path, "W")
test_suite = luna_util.get_test_suite(test_suite.stdout)
test_suite_by_edge = get_test_suite_by_edge(graph, start_node, test_suite)

betweenness_centrality = nx.edge_betweenness_centrality(graph)
degree_centrality = edge_priority.convert_to_edge_centrality(graph, nx.degree_centrality(graph))
closeness_centrality = edge_priority.convert_to_edge_centrality(graph, nx.closeness_centrality(graph))

prioritized_test_suite = iter_luna.run(degree_centrality, test_suite_by_edge)

mutation_testing.run(graph, start_node, prioritized_test_suite, input_size, output_size)