
def calculate_test_case_cost(centrality, test_suite):
    test_suite_cost = dict()
    for index, test_case in enumerate(test_suite):
        test_case_edges = dict()
        for edge in test_case:
            test_case_edges[edge] = centrality[edge]
        test_suite_cost[index] = test_case_edges
    return test_suite_cost


def update(used_edges, test_suite_cost):
    max_test_case = None
    max_cost = 0
    for index in list(test_suite_cost.keys()):
        test_suite_cost[index] = {k: test_suite_cost[index][k] for k in set(test_suite_cost[index].keys()) - set(used_edges)}
        cost = sum(test_suite_cost[index].values())
        if cost > max_cost:
            max_cost = cost
            max_test_case = test_suite_cost[index].keys()
        elif cost == 0:
            del test_suite_cost[index]
    return max_test_case, max_cost


def get_prioritized_test_suite(test_suite_cost):
    prioritized_test_suite = []
    used_edges = []
    test_case, cost = update(used_edges, test_suite_cost)
    while cost > 0:
        prioritized_test_suite.append(list(test_case))
        test_case, cost = update(test_case, test_suite_cost)
    return prioritized_test_suite


def run(centrality, test_suite_by_edge):
    test_suite_cost = calculate_test_case_cost(centrality, test_suite_by_edge)
    prioritized_test_suite = get_prioritized_test_suite(test_suite_cost)
    return prioritized_test_suite
