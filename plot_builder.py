import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

df = pd.read_csv('results.csv')

df['n'] = df['n'].astype(int)
df['time_ms'] = df['time_ms'].astype(float)
df['comparisons'] = df['comparisons'].astype(float)
df['max_depth'] = df['max_depth'].astype(float)

def calc_ratio(row):
    n = row['n']
    comps = row['comparisons']
    if 'Select' in row['algorithm']:
        return comps / n
    else:
        return comps / (n * np.log2(n))

df['ratio'] = df.apply(calc_ratio, axis=1)

input_types = df['input'].unique()

plt.figure(figsize=(10, 6))
for algo in df['algorithm'].unique():
    for inp in input_types:
        sub = df[(df['algorithm'] == algo) & (df['input'] == inp)]
        plt.plot(sub['n'], sub['time_ms'], marker='o', label=f'{algo} ({inp})')

plt.xscale('log')
plt.xlabel('Array Size (n)')
plt.ylabel('Time (ms)')
plt.title('Execution Time vs Array Size (n)')
plt.legend()
plt.grid(True, which="both", ls="--")
plt.tight_layout()
plt.savefig('time_vs_n.png')
plt.close()

plt.figure(figsize=(10, 6))
for algo in df['algorithm'].unique():
    for inp in input_types:
        sub = df[(df['algorithm'] == algo) & (df['input'] == inp)]
        plt.plot(sub['n'], sub['max_depth'], marker='o', label=f'{algo} ({inp})')

plt.xscale('log')
plt.xlabel('Array Size (n)')
plt.ylabel('Max Recursion Depth')
plt.title('Max Recursion Depth vs Array Size (n)')
plt.legend()
plt.grid(True, which="both", ls="--")
plt.tight_layout()
plt.savefig('depth_vs_n.png')
plt.close()

plt.figure(figsize=(10, 6))
for algo in df['algorithm'].unique():
    for inp in input_types:
        sub = df[(df['algorithm'] == algo) & (df['input'] == inp)]
        plt.plot(sub['n'], sub['ratio'], marker='o', label=f'{algo} ({inp})')

plt.xscale('log')
plt.xlabel('Array Size (n)')
plt.ylabel('Ratio (Comparisons / Complexity)')
plt.title('Ratio vs Array Size (n)')
plt.legend()
plt.grid(True, which="both", ls="--")
plt.tight_layout()
plt.savefig('ratio_vs_n.png')
plt.close()

print("Graphs created successfully: time_vs_n.png, depth_vs_n.png, ratio_vs_n.png")