import math

import networkx as nx
import pandas as pd

import edge_priority
import luna_util
import mutation_testing


def run():
    apfd = pd.DataFrame(columns=["input_size", "state_size", "output_size", "transition_size", "algorithm",
                                 "betweenness", "degree", "closeness", "random"])
    for algorithm in ["W"]:
        for state_size in range(5, 15):
            luna_util.create_fsm(state_size, input_size, output_size, file_path)
            # graph, start_node = luna_util.create_graph_from_mutlu(file_path)
            graph, start_node = luna_util.create_graph_from_kiss2(file_path)
            # luna_util.create_mutlu_from_kiss2(graph, file_path)
            test_suite = luna_util.run("example.fsm", algorithm)
            test_suite = luna_util.get_test_suite(test_suite.stdout)
            # df = luna_util.create_dataframe_from_mutlu("example.fsm")
            # df = pd.get_dummies(df, columns=["source", "target", "input"])
            # corr = df.corr().iloc[0].to_dict()
            # corr_centrality = edge_priority.convert_to_edge_centrality_from_corr(graph, corr)
            edge_betweenness_centrality = nx.edge_betweenness_centrality(graph)

            # all_pairs_node_connectivity = nx.all_pairs_node_connectivity(graph)

            # edge_load_centrality = nx.edge_load_centrality(graph)
            node_degree_centrality = edge_priority.convert_to_edge_centrality(graph, nx.degree_centrality(graph))
            # node_betweenness_centrality = edge_priority.convert_to_edge_centrality(graph, nx.betweenness_centrality(graph))
            # node_eigenvector_centrality = edge_priority.convert_to_edge_centrality(graph, nx.eigenvector_centrality(graph))
            node_closeness_centrality = edge_priority.convert_to_edge_centrality(graph, nx.closeness_centrality(graph))
            # clustering_coefficient_centrality = edge_priority.convert_to_edge_centrality(graph,nx.clustering(graph))

            edge_betweenness_test_suite = edge_priority.sort_test_case(graph, start_node, edge_betweenness_centrality, test_suite)
            # corr_test_suite = edge_priority.sort_test_case(graph, start_node, corr_centrality, test_suite)
            # edge_load_test_suite = edge_priority.sort_test_case(graph, start_node, edge_load_centrality, test_suite)
            node_degree_test_suite = edge_priority.sort_test_case(graph, start_node, node_degree_centrality, test_suite)
            # node_betweenness_test_suite = edge_priority.sort_test_case(graph, start_node, node_betweenness_centrality, test_suite)
            node_closeness_test_suite = edge_priority.sort_test_case(graph, start_node, node_closeness_centrality, test_suite)

            result = dict()
            result["input_size"] = input_size
            result["state_size"] = state_size
            result["output_size"] = output_size
            result["transition_size"] = state_size * math.pow(2, input_size) - 1
            result["betweenness"] = mutation_testing.run(graph, start_node, edge_betweenness_test_suite, input_size, output_size)
            # result["corr"] = mutation_testing.run(graph, start_node, corr_test_suite, input_size, output_size)
            # result["edge_load_apfd"] = mutation_testing.run(graph, start_node, edge_load_test_suite, input_size, output_size)
            result["degree"] = mutation_testing.run(graph, start_node, node_degree_test_suite, input_size, output_size)
            # result["node_betweenness"] = mutation_testing.run(graph, start_node, node_betweenness_test_suite, input_size, output_size)
            result["closeness"] = mutation_testing.run(graph, start_node, node_closeness_test_suite, input_size, output_size)
            result["random"] = mutation_testing.run(graph, start_node, test_suite, input_size, output_size)

            result["algorithm"] = algorithm
            print(result)
            apfd.loc[len(apfd.index)] = result
            apfd.to_excel(algorithm + ".xlsx", index=False, header=True)

