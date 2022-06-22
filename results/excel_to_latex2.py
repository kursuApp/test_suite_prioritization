import pandas as pd
algorithm = "W"
apfd = pd.read_excel('data.xlsx')
print("****************W******************")
for index, row in apfd[apfd["algorithm"] == "W"].sort_values(by=['transition_size']).iterrows():
    # print("( " + str(row["transition_size"]), ", ", str((row["betweenness"] + row["degree"] + row["closeness"])/3),
    # ")")
    print("( " + str(row["transition_size"]), ", ", str(row["random"]), ")")
print("****************Wp******************")
for index, row in apfd[apfd["algorithm"] == "Wp"].sort_values(by=['transition_size']).iterrows():
    # print("( " + str(row["transition_size"]), ", ", str((row["betweenness"] + row["degree"] + row["closeness"])/3),
    # ")")
    print("( " + str(row["transition_size"]), ", ", str(row["random"]), ")")
print("*****************hsi*****************")
for index, row in apfd[apfd["algorithm"] == "hsi"].sort_values(by=['transition_size']).iterrows():
    # print("( " + str(row["transition_size"]), ", ", str((row["betweenness"] + row["degree"] + row["closeness"])/3),
    # ")")
    print("( " + str(row["transition_size"]), ", ", str(row["random"]), ")")
print("*****************P*****************")
for index, row in apfd[apfd["algorithm"] == "P"].sort_values(by=['transition_size']).iterrows():
    # print("( " + str(row["transition_size"]), ", ", str((row["betweenness"] + row["degree"] + row["closeness"])/3),
    # ")")
    print("( " + str(row["transition_size"]), ", ", str(row["random"]), ")")
print("*****************random*****************")
# for index, row in apfd[apfd["algorithm"] == "W"].sort_values(by=['transition_size']).iterrows():
    # print("( " + str(row["transition_size"]), ", ", str(row["random"]), ")")
