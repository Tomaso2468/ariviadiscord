"""
A script that updates an ebf file to allow for better learning.
"""

file = open("learning.txt", mode="r")

def quick_replace(s):
    return s

list_data = []
command = False
for row in file:
    if "define" in row.lower():
        continue
    if row.lower().startswith("!"):
        comamnd = True
        continue
    if row.lower().startswith("/"):
        comamnd = True
        continue
    if row.lower().startswith("%"):
        comamnd = True
        continue
    if row.lower().startswith(":"):
        continue
    if "*" in row.lower():
        continue
    if row.lower().startswith("."):
        comamnd = True
        continue
    if command:
        command = False
        continue
    if row.strip() != "":
       list_data.append(row.strip().replace("@", ""))

file.close()

file = open("discord.ebf", mode="a")

for i in range(len(list_data) - 1):
    item1 = list_data[i]
    item2 = list_data[i + 1]

    file.write("io {" + "\n")
    file.write("    in {" + "\n")
    file.write("        " + item1 + "\n")
    file.write("    }" + "\n")
    file.write("    out {" + "\n")
    file.write("        " + item2 + "\n")
    file.write("    }" + "\n")
    file.write("}" + "\n")

file.close()
