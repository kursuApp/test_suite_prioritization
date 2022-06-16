import copy
import random
import networkx as nx



def get_output(graph, start_node, test_case):
    outputs = []
    founded = False
    current_node = start_node
    for input_ in test_case:
        for u, v, k, d in graph.edges(current_node, data=True, keys=True):
            if d['input'] == input_:
                current_node = v
                outputs.append(d['output'])
                founded = True
        if founded:
            founded = False
        else:
            return outputs
    return outputs


def equal(mutant, graph, start_node, test_case):
    result = get_output(mutant, start_node, test_case) != get_output(graph, start_node, test_case)
    return 1 if result else 0


def process(graph, start_node, test_suite):
    cost = 0

    for node in graph.nodes:
        mutant = copy.deepcopy(graph)
        mutant.remove_node(node)
        for key, test_case in enumerate(test_suite):
            if equal(mutant, graph, start_node, test_case):
                cost += (key + 1)
        del mutant

    for edge in graph.edges:
        # Change of Next State (CONS)
        # Missing of Transition(MOT)
        mutant = copy.deepcopy(graph)
        mutant.remove_edge(edge[0], edge[1])
        for key, test_case in enumerate(test_suite):
            if equal(mutant, graph, start_node, test_case):
                cost += (key + 1)
                break
        del mutant
        # Change of Output (COO)
        mutant = copy.deepcopy(graph)
        nx.set_edge_attributes(mutant, random.randrange(1), "output")
        for key, test_case in enumerate(test_suite):
            if equal(mutant, graph, start_node, test_case):
                cost += (key + 1)
                break
        del mutant
        # Change of Input(COI)
        mutant = copy.deepcopy(graph)
        nx.set_edge_attributes(mutant, random.randrange(1), "input")
        for key, test_case in enumerate(test_suite):
            if equal(mutant, graph, start_node, test_case):
                cost += (key + 1)
                break
        del mutant
    # M = is the total no. of faults exposed in the system or module under T
    m = graph.number_of_edges() * 3 + graph.number_of_nodes()
    # N = is the total no. of test cases in T
    n = len(test_suite)
    return 1 - (cost / (m * n)) + 1 / (2 * n)


def run(graph, start_node, test_suite):
    cost = process(graph, start_node, test_suite)
    return cost
