import networkx as nx


def edge_centrality(graph, start_node, centrality, test_suite):
    test_suite_costs = {}
    current_node = start_node
    for test_case in test_suite:
        cost = 0
        for input_ in test_case:
            for u, v, k, d in graph.edges(current_node, data=True, keys=True):
                if d['input'] == input_:
                    cost += centrality[(u, v, k)]
                    current_node = v
                    break
            test_suite_costs[tuple(test_case)] = cost
    return test_suite_costs


def convert_to_edge_centrality(graph, n_centrality):
    e_centrality = {}
    for edge in graph.edges(keys=True):
        e_centrality[edge] = n_centrality[edge[0]] + n_centrality[edge[1]]
    return e_centrality


def convert_to_edge_centrality_from_corr(graph, corr):
    e_corr = {}
    for u, v, k, d in graph.edges(keys=True, data=True):
        e_corr[(u, v, k)] = abs(corr['source_'+u]) + abs(corr['target_'+v]) + abs(corr['input_'+d['input']])
    return e_corr


def sort_test_case(graph, start_node, centrality, test_suite):
    test_suite_costs = edge_centrality(graph, start_node, centrality, test_suite)
    result = list()
    for k in sorted(test_suite_costs, key=test_suite_costs.get, reverse=True):
        result.append(list(k))
    return result
