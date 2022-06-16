import networkx as nx
import pandas as pd

import edge_priority
import luna_util
import mutation_testing


def run():
    state_size = 5
    input_size = 1
    output_size = 1
    file_path = "output.fsm"
    apfd = pd.DataFrame(columns=["algorithm", "edge_betweenness", "corr", "node_degree", "node_betweenness", "node_closeness"])
    for algorithm in ["W"]: #, "Wp", "hsi", "P"]
        for state_size in range(5, 30):
            luna_util.create_fsm(state_size, input_size, output_size, 'test.kiss2')
            graph, start_node = luna_util.create_graph_from_kiss2('test.kiss2')
            luna_util.create_mutlu_from_kiss2(graph, file_path)
            test_suite = luna_util.run(file_path,algorithm)
            test_suite = luna_util.get_test_suite(test_suite.stdout)
            df = luna_util.create_dataframe_from_mutlu(file_path)
            df = pd.get_dummies(df, columns=["source", "target", "input"])
            corr = df.corr().iloc[0].to_dict()
            corr_centrality = edge_priority.convert_to_edge_centrality_from_corr(graph, corr)
            edge_betweenness_centrality = nx.edge_betweenness_centrality(graph)
            # edge_load_centrality = nx.edge_load_centrality(graph)
            node_degree_centrality = edge_priority.convert_to_edge_centrality(graph, nx.degree_centrality(graph))
            node_betweenness_centrality = edge_priority.convert_to_edge_centrality(graph, nx.betweenness_centrality(graph))
            # node_eigenvector_centrality = edge_priority.convert_to_edge_centrality(graph, nx.eigenvector_centrality(graph))
            node_closeness_centrality = edge_priority.convert_to_edge_centrality(graph, nx.closeness_centrality(graph))
            # clustering_coefficient_centrality = edge_priority.convert_to_edge_centrality(graph,nx.clustering(graph))

            edge_betweenness_test_suite = edge_priority.sort_test_case(graph, start_node, edge_betweenness_centrality, test_suite)
            corr_test_suite = edge_priority.sort_test_case(graph, start_node, corr_centrality, test_suite)
            # edge_load_test_suite = edge_priority.sort_test_case(graph, start_node, edge_load_centrality, test_suite)
            node_degree_test_suite = edge_priority.sort_test_case(graph, start_node, node_degree_centrality, test_suite)
            node_betweenness_test_suite = edge_priority.sort_test_case(graph, start_node, node_betweenness_centrality, test_suite)
            node_closeness_test_suite = edge_priority.sort_test_case(graph, start_node, node_closeness_centrality, test_suite)
            result = dict()
            result["edge_betweenness"] = mutation_testing.run(graph, start_node, edge_betweenness_test_suite)
            result["corr"] = mutation_testing.run(graph, start_node, corr_test_suite)
            # result["edge_load_apfd"] = mutation_testing.run(graph, start_node, edge_load_test_suite)
            result["node_degree"] = mutation_testing.run(graph, start_node, node_degree_test_suite)
            result["node_betweenness"] = mutation_testing.run(graph, start_node, node_betweenness_test_suite)
            result["node_closeness"] = mutation_testing.run(graph, start_node, node_closeness_test_suite)
            result["algorithm"] = algorithm
            print(result)
            apfd.loc[len(apfd.index)] = result
            apfd.to_excel(algorithm+".xlsx", index=False, header=True)

