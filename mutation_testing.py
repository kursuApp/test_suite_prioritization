import copy
import random
import networkx as nx


def get_output(graph, current_node, test_case):
    outputs = ''
    input_index = 0
    test_case_index = 0
    df = nx.to_pandas_edgelist(graph)
    while input_index < len(test_case):
        edge = df[(df['input'] == test_case[input_index]) & (df['source'] == current_node)]
        if not edge.empty:
            input_index += 1
            test_case_index += 1
            current_node = edge.iloc[0]['target']
            outputs += str(edge.iloc[0]['output'])
        else:
            break
    return outputs


def equal(mutant, graph, start_node, test_case):
    # print(get_output(mutant, start_node, test_case), " == ", get_output(graph, start_node, test_case))
    result = get_output(mutant, start_node, test_case) == get_output(graph, start_node, test_case)
    return 1 if result else 0


def process(graph, start_node, test_suite, input_size, output_size):
    cost = 0

    for node in graph.nodes:
        mutant = copy.deepcopy(graph)
        mutant.remove_node(node)
        key = 0
        for key, test_case in enumerate(test_suite):
            if not equal(mutant, graph, start_node, test_case):
                break
        cost += (key + 1)
        # print((key + 1))
        del mutant
    for edge in graph.edges:
        # Missing of Transition(MOT)
        mutant = copy.deepcopy(graph)
        mutant.remove_edge(edge[0], edge[1], edge[2])
        key = 0
        for key, test_case in enumerate(test_suite):
            if not equal(mutant, graph, start_node, test_case):
                break
        cost += (key + 1)
        # print((key + 1))
        del mutant

        # Change of Output (COO)
        mutant = copy.deepcopy(graph)
        nx.set_edge_attributes(mutant, {edge: {"output": random.randrange(output_size)}})
        key = 0
        for key, test_case in enumerate(test_suite):
            if not equal(mutant, graph, start_node, test_case):
                break
        cost += (key + 1)
        # print((key + 1))
        del mutant

        # Change of Input(COI)
        mutant = copy.deepcopy(graph)
        nx.set_edge_attributes(mutant, {edge: {"input": random.randrange(input_size)}})
        key = 0
        for key, test_case in enumerate(test_suite):
            if not equal(mutant, graph, start_node, test_case):
                break
        cost += (key + 1)
        # print((key + 1))
        del mutant
    # M = is the total no. of faults exposed in the system or module under T
    m = graph.number_of_edges() * 3 + graph.number_of_nodes()
    # N = is the total no. of test cases in T
    n = len(test_suite)
    return 1 - (cost / (m * n)) + 1 / (2 * n)


def run(graph, start_node, test_suite, input_size, output_size):
    cost = process(graph, start_node, test_suite, input_size, output_size)
    return cost
