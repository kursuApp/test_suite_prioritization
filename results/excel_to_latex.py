import pandas as pd
algorithm = "W"
apfd = pd.read_excel('data.xlsx')
print("****************betweenness******************")
for index, row in apfd[apfd["algorithm"] == "W"].sort_values(by=['transition_size']).iterrows():
    print("( " + str(row["transition_size"]), ", ", str(row["betweenness"]), ")")
print("****************degree******************")
for index, row in apfd[apfd[
                           "algorithm"] == "W"].sort_values(by=['transition_size']).iterrows():
    print("( " + str(row["transition_size"]), ", ", str(row["degree"]), ")")
print("*****************closeness*****************")
for index, row in apfd[apfd["algorithm"] == "W"].sort_values(by=['transition_size']).iterrows():
    print("( " + str(row["transition_size"]), ", ", str(row["closeness"]), ")")
print("*****************random*****************")
for index, row in apfd[apfd["algorithm"] == "W"].sort_values(by=['transition_size']).iterrows():
    print("( " + str(row["transition_size"]), ", ", str(row["random"]), ")")
