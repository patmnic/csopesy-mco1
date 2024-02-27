class Process():
    def __init__(self, id, arrival, burst):
        self.id = id
        self.arrival = arrival
        self.burst = burst
        self.start = -1
        self.end = -1
        self.wait = -1
        self.done = False
        self.running = False

    def output(self):
        print("P[%i] start time: %i end time: %i | Waiting time: %i" % self.id, self.start, self.end, self.wait)

    def run(self, start, reduce=-1):
        if time == -1:
            time = self.burst
        
        self.start = start
        self.burst = self.burst - time
        
