import math
import subprocess
import networkx as nx
import pandas as pd


def create_dataframe_from_mutlu(file_path):
    df = pd.DataFrame(columns=["source", "target", "input", "output"])
    file = open(file_path, "r")
    for line in file:
        parts = line.split()
        df.at[len(df.index)] = {"source": parts[0], "target": parts[6], "input": parts[2], "output": parts[4]}
    file.close()
    return df


def create_mutlu_from_kiss2(graph, output_file_path):
    with open(output_file_path, 'w') as file:
        for u, v, k, d in graph.edges(keys=True, data=True):
            line = u + " -- " + d['input'] + " / " + d['output'] + " -> " + v + "\n"
            file.write(line)


def create_graph_from_kiss2(output_file):
    graph = nx.MultiDiGraph()
    start_node = None
    input_file = open(output_file, "r")
    for line in input_file:
        if not line.startswith("."):
            parts = line.split()
            input_value = parts[0]
            output_value = parts[3]
            graph.add_edge(parts[1], parts[2], input=input_value, output=output_value)
            if start_node is None:
                start_node = parts[1]
    input_file.close()
    return graph, start_node


def create_graph_from_mutlu(file_path):
    graph = nx.MultiDiGraph()
    file = open(file_path, "r")
    start_node = None
    for line in file:
        parts = line.split()
        input_value = parts[2]
        output_value = parts[4]
        graph.add_edge(parts[0], parts[6], input=input_value, output=output_value)
        if start_node is None:
            start_node = parts[0]
    file.close()
    return graph, start_node


def get_test_suite(test_suite):
    test_cases = []
    for test_case in test_suite.splitlines():
        tmp = test_case.decode('ascii').split(',')
        test_cases.append(tmp)
    return test_cases


def create_fsm(state_size, input_, output_, file):
    transition_size = state_size * math.pow(2, input_) - 1
    subprocess.run(["tools/genstate.exe", "-i", str(input_), "-o", str(output_), "-t", str(transition_size), "-s",
                    str(state_size),
                    file])


def run(file, tool):
    return subprocess.run(["java", "-jar", "tools/" + tool + ".jar", file], capture_output=True)
