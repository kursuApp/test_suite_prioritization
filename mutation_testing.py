def has_node(test_case, changed_node):
    for u, v, k in test_case:
        if u == changed_node or v == changed_node:
            return True
    return False


def has_edge(test_case, changed_edge):
    for edge in test_case:
        if edge == changed_edge:
            return True
    return False


def process(graph, test_suite):
    cost = 0
    key = -1
    for node in graph.nodes:
        for key, test_case in enumerate(test_suite):
            if has_node(test_case, node):
                break
        cost += (key + 1)

    for edge in graph.edges:
        # Missing of Transition(MOT)
        for key, test_case in enumerate(test_suite):
            if has_node(test_case, edge):
                break
        cost += (key + 1)

    # M = is the total no. of faults exposed in the system or module under T
    m = graph.number_of_edges() + graph.number_of_nodes()
    # N = is the total no. of test cases in T
    n = len(test_suite)
    print(len(test_suite), " : ", graph.number_of_edges(), " : ", graph.number_of_nodes())
    return 1 - (cost / (m * n)) + 1 / (2 * n)


def run(graph, test_suite):
    cost = process(graph, test_suite)
    return cost
