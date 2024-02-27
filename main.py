from process import Process
import cpu_scheduling

proceed = True

def clean_list(list):
    while("" in list):
        list.remove("")
    
    return list

def main():
    filename = input("Input file: ")

    with open(filename) as f:
        process_txt = f.readlines()

    process_txt = clean_list(process_txt)

    xyz = process_txt.pop(0).split()
    x = int(xyz[0])
    y = int(xyz[1])
    z = int(xyz[2])

    processes = []
    for i in y:
        p = process_txt.pop(0).split()
        p = clean_list(p)
        processes.append(Process(id=p[0], arrival=p[1], burst=p[2]))

    match x:
        case 1:
            cpu_scheduling.FCFS(processes=processes)

if __name__ == "__main__":
    main()
    print("lol")