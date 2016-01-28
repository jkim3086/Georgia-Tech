/*
 * student.c
 * Multithreaded OS Simulation for CS 2200, Project 6
 * Fall 2015
 *
 * This file contains the CPU scheduler for the simulation.
 * Name: Jeongsoo Kim
 * GTID: 903093339
 */

#include <assert.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include "student.h"
#include "os-sim.h"
#include <string.h>


/*
 * current[] is an array of pointers to the currently running processes.
 * There is one array element corresponding to each CPU in the simulation.
 *
 * current[] should be updated by schedule() each time a process is scheduled
 * on a CPU.  Since the current[] array is accessed by multiple threads, you
 * will need to use a mutex to protect it.  current_mutex has been provided
 * for your use.
 */
void readyQ_push(pcb_t* add);
pcb_t* readyQ_pop();
int prior_comparison(pcb_t *process);

static pcb_t **current;
static pthread_mutex_t current_mutex;
static pthread_mutex_t readyQ_mutex;
int t_slice = -1;
static pcb_t *head = NULL;
static pthread_cond_t idle_cond;
static int SPS = 0;
static int glob_cpu_count;

/*
 * schedule() is your CPU scheduler.  It should perform the following tasks:
 *
 *   1. Select and remove a runnable process from your ready queue which 
 *	you will have to implement with a linked list or something of the sort.
 *
 *   2. Set the process state to RUNNING
 *
 *   3. Call context_switch(), to tell the simulator which process to execute
 *      next on the CPU.  If no process is runnable, call context_switch()
 *      with a pointer to NULL to select the idle process.
 *	The current array (see above) is how you access the currently running
 *	process indexed by the cpu id. See above for full description.
 *	context_switch() is prototyped in os-sim.h. Look there for more information 
 *	about it and its parameters.
 */
 
 // linked list ready-queue
  
void readyQ_push(pcb_t* add) {

    pcb_t* current = head;
    add->next = NULL;

	if(current == NULL) {
		head = add;
	}else {
	    while (current->next != NULL) {
		current = current->next;
	    }
	    current->next = add;
	}
}

pcb_t* readyQ_pop() {
    pcb_t* pop_target;
    pcb_t* next_node;

    if (head == NULL) {
	return NULL;
    } else {
	pop_target = head;
	next_node = head->next;
	head = NULL;
	head = next_node;
	return pop_target;
    }
}

int prior_comparison(pcb_t *process) {
	int pos, i, find;
	find = -1;
	unsigned int comp = process->static_priority;

	for(i = 0; i < glob_cpu_count; i++){
		if(current[i] != NULL) {

			if(current[i]->static_priority < comp) {
				pos = i;
				comp = current[i]->static_priority;
				find = 1;
			}
		}	
	}
	if(find != -1) {
		return pos;
	}else {
		return -1;
	}
}

static void schedule(unsigned int cpu_id)
{
    /* FIX ME */
	/*if(head == NULL) {
		context_switch(cpu_id, NULL, t_slice);
		//printf("Execute!\n");
		return;
	}*/

	pthread_mutex_lock(&readyQ_mutex);
	pcb_t* get_pc = readyQ_pop();
	pthread_mutex_unlock(&readyQ_mutex);

	if(get_pc == NULL){
		context_switch(cpu_id, NULL, t_slice);
	}else {
		/*if(get_pc == NULL) {
			printf("NULL!!!\n");
		}*/
		get_pc->state = PROCESS_RUNNING;
		
		pthread_mutex_lock(&current_mutex);
		current[cpu_id] = get_pc;
		pthread_mutex_unlock(&current_mutex);
		context_switch(cpu_id, get_pc, t_slice);
	}
}


/*
 * idle() is your idle process.  It is called by the simulator when the idle
 * process is scheduled.
 *
 * This function should block until a process is added to your ready queue.
 * It should then call schedule() to select the process to run on the CPU.
 */

extern void idle(unsigned int cpu_id)
{
        int Break = 0;

	pthread_mutex_lock(&readyQ_mutex);

	while(Break == 0) {
		if(head == NULL) {
			pthread_cond_wait(&idle_cond, &readyQ_mutex);
		}else if(head != NULL) {
			Break = 1;
		}
	}

	pthread_mutex_unlock(&readyQ_mutex);

        schedule(cpu_id);

    /*
     * REMOVE THE LINE BELOW AFTER IMPLEMENTING IDLE()
     *
     * idle() must block when the ready queue is empty, or else the CPU threads
     * will spin in a loop.  Until a ready queue is implemented, we'll put the
     * thread to sleep to keep it from consuming 100% of the CPU time.  Once
     * you implement a proper idle() function using a condition variable,
     * remove the call to mt_safe_usleep() below.
     */
    //mt_safe_usleep(1000000);
}


/*
 * preempt() is the handler called by the simulator when a process is
 * preempted due to its timeslice expiring.
 *
 * This function should place the currently running process back in the
 * ready queue, and call schedule() to select a new runnable process.
 */
extern void preempt(unsigned int cpu_id)
{
    /* FIX ME */
	pthread_mutex_lock(&current_mutex);
	current[cpu_id]->state = PROCESS_READY;
	pthread_mutex_lock(&readyQ_mutex);
	readyQ_push(current[cpu_id]);
	pthread_mutex_unlock(&current_mutex);
    pthread_mutex_unlock(&readyQ_mutex);
	schedule(cpu_id);
}


/*
 * yield() is the handler called by the simulator when a process yields the
 * CPU to perform an I/O request.
 *
 * It should mark the process as WAITING, then call schedule() to select
 * a new process for the CPU.
 */
extern void yield(unsigned int cpu_id)
{
    /* FIX ME */
	pthread_mutex_lock(&current_mutex);
	current[cpu_id]->state = PROCESS_WAITING;
	pthread_mutex_unlock(&current_mutex);
	
	schedule(cpu_id);
}


/*
 * terminate() is the handler called by the simulator when a process completes.
 * It should mark the process as terminated, then call schedule() to select
 * a new process for the CPU.
 */
extern void terminate(unsigned int cpu_id)
{
    /* FIX ME */
	pthread_mutex_lock(&current_mutex);
	current[cpu_id]->state = PROCESS_TERMINATED;
	pthread_mutex_unlock(&current_mutex);
	
	schedule(cpu_id);
}


/*
 * wake_up() is the handler called by the simulator when a process's I/O
 * request completes.  It should perform the following tasks:
 *
 *   1. Mark the process as READY, and insert it into the ready queue.
 *
 *   2. If the scheduling algorithm is static priority, wake_up() may need
 *      to preempt the CPU with the lowest priority process to allow it to
 *      execute the process which just woke up.  However, if any CPU is
 *      currently running idle, or all of the CPUs are running processes
 *      with a higher priority than the one which just woke up, wake_up()
 *      should not preempt any CPUs.
 *	To preempt a process, use force_preempt(). Look in os-sim.h for 
 * 	its prototype and the parameters it takes in.
 */
extern void wake_up(pcb_t *process)
{
    /* FIX ME */
	int result = -1;
	
 	if(SPS == 1) {
		pthread_mutex_lock(&readyQ_mutex);
		pthread_mutex_lock(&current_mutex);
		
		result = prior_comparison(process);

		pthread_mutex_unlock(&current_mutex);
		pthread_mutex_unlock(&readyQ_mutex);

		if(result >= 0 && head != NULL) {
			force_preempt(result);
			
		}
	}
	
	pthread_mutex_lock(&readyQ_mutex);
	pthread_mutex_lock(&current_mutex);
	process->state = PROCESS_READY; 
	readyQ_push(process);
	pthread_cond_broadcast(&idle_cond);
	pthread_mutex_unlock(&current_mutex);
	pthread_mutex_unlock(&readyQ_mutex);
	
	
	/*
		- Checking and comparing infNULLely static_priorities of processes in current[] and readyQ
		So, if any process in current[] has a lower static_priorities than a process in readyQ has,
		call force_preemt()
		or just compare static_priority of process, the parameter, with static_priorities of processes in current[]
		??? How to distinguish between FIFO and static priority
		- For handling idle, checking queue size, and do nothing, if the size is equal to 0.
		- Make only space for the given process or actually replacing???
		- force_preemt change the state???
	*/
	
}


//static int SPS = 1; //static priority scheduler signal
/*
 * main() simply parses command line arguments, then calls start_simulator().
 * You will need to modify it to support the -r and -p command-line parameters.
 */
int main(int argc, char *argv[])
{
    int cpu_count;
    char RR[] = "-r";
    char SP[] = "-p";

    /* Parse command-line arguments */
    if (argc < 2 || argc > 5)
    {
        fprintf(stderr, "CS 2200 Project 4 -- Multithreaded OS Simulator\n"
            "Usage: ./os-sim <# CPUs> [ -r <time slice> | -p ]\n"
            "    Default : FIFO Scheduler\n"
            "         -r : Round-Robin Scheduler\n"
            "         -p : Static Priority Scheduler\n\n");
        return -1;
    }
    cpu_count = atoi(argv[1]);
    glob_cpu_count = cpu_count; // For use of traversing current[]

    /* FIX ME - Add support for -r and -p parameters*/

    if(argc == 2) { //FIFO
	SPS = 0;
    }else if(argc == 4 && (strcmp(RR,argv[2]) == 0)) { //round robin
	t_slice = atoi(argv[3]);
        SPS = 0;
    }else if(argc == 3 && (strcmp(SP,argv[2]) == 0)) { // static priority
	SPS = 1;
    }else {
	fprintf(stderr,"You put incorrect value to run this simulatore\n");
    }

    /* Allocate the current[] array and its mutex */
    current = malloc(sizeof(pcb_t*) * cpu_count);
    assert(current != NULL);
    pthread_mutex_init(&current_mutex, NULL);

    pthread_mutex_init(&readyQ_mutex, NULL);
	
    pthread_cond_init(&idle_cond, NULL);
	
    /* Start the simulator in the library */
    start_simulator(cpu_count);

    return 0;
}


