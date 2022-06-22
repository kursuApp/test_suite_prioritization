import networkx as nx


def get_edges(graph, current_node, test_case):
    edges = set()
    input_index = 0
    test_case_index = 0
    df = nx.to_pandas_edgelist(graph, edge_key='key')
    while input_index < len(test_case):
        edge = df[(df['input'] == test_case[input_index]) & (df['source'] == current_node)]
        if not edge.empty:
            input_index += 1
            test_case_index += 1
            current_node = edge.iloc[0]['target']
            edges.add((edge.iloc[0]['source'], edge.iloc[0]['target'], edge.iloc[0]['key']))
        else:
            break
    return edges


def get_test_suite_by_edge(graph, start_node, test_suite):
    test_suite_by_edge = dict()
    for index, test_case in enumerate(test_suite):
        test_suite_by_edge[index] = get_edges(graph, start_node, test_case)
    return test_suite_by_edge
