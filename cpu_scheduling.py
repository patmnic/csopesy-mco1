import sys
from process import Process

def done(processes):
    for p in processes:
        if not p.done:
            return False
    return True

def wait(processes, time):
    for p in processes:
        if not p.done and not p.running:
            p.wait += time

def FCFS(processes):
    current_time = 0

    while not done(processes):
        next_process = None
        earliest_arrival = sys.maxsize
        for p in processes:
            if not p.done and p.arrival < earliest_arrival:
                next_process = p
                earliest_arrival = p.arrival

        if earliest_arrival > current_time:
            t = earliest_arrival - current_time
            wait(processes=processes, time=t)
            current_time = earliest_arrival
        
        

    