/* CS 2200 - Project 5 - Fall 2015
 * Name - Jeongsoo Kim
 * GTID - 903093339
 */

#include "cachesim.h"
#define Address_Length 64
/**
 * Sub-routine for initializing your CACHE with the parameters.
 * You may initialize any global variables here.
 */
 

 
 struct CACHE {
	 uint64_t c_Lines;
	 uint64_t set_as;
	 uint64_t tag;
	 uint64_t offset;
	 uint64_t index;
	 uint64_t sets;
 } cache;
 
 struct BLOCK {
	 int valid;
	 int LRU;
	 int dirty;
	 uint64_t tag;
	 uint64_t data;
 };

struct SET{
	struct BLOCK* sblock;
 };
 struct SET* Set;
 

 
/** @param C The total size of your CACHE is 2^C bytes
 * @param S The set associativity is 2^S
 * @param B The size of your BLOCK is 2^B bytes
 */
void cache_init(uint64_t C, uint64_t S, uint64_t B) {

	cache.offset = B;
	cache.index = ((C - B) - S);
	cache.tag = Address_Length - cache.offset - cache.index;
	cache.sets = ((uint64_t) 1 << S); // # of sets using S bits
	cache.c_Lines = ((uint64_t) 1 << (cache.index));
	
	Set = (struct SET*) calloc(cache.sets, sizeof(struct SET));

	for(int i = 0; i < cache.sets; i++) {
		Set[i].sblock = (struct BLOCK*) calloc(cache.c_Lines, sizeof(struct BLOCK));
	}
	
}



/**
 * Subroutine that simulates one CACHE event at a time.
 * @param rw The type of access, READ or WRITE
 * @param address The address that is being accessed
 * @param stats The struct that you are supposed to store the stats in
 */
void cache_access (char rw, uint64_t address, struct cache_stats_t *stats) {
	
	stats->accesses++;
	(rw == 'r')? (stats->reads++): (stats->writes++);
	
	int hit_frag, pos, victim;
	hit_frag = pos = victim = 0;
	int victim_frag = -1;
	uint64_t Tag = (address >> (cache.index + cache.offset));
	uint64_t temp = (address >> (cache.offset));
	uint64_t Index = (temp & (cache.c_Lines - ((uint64_t) 1)));
	
	for(pos = 0; pos < cache.sets; pos++) {
		if(Set[pos].sblock[Index].tag == Tag) {
			
			if(Set[pos].sblock[Index].valid == 1) {
				
				hit_frag = 1;
				Set[pos].sblock[Index].LRU = 1;
				
				if(rw == 'w') {
					Set[pos].sblock[Index].dirty = 1;
				}
				//Set[pos].sblock[Index].dirty = (rw = 'w')? 1: 0;
			} 
		} else { 	
			Set[pos].sblock[Index].LRU++;	
		}
	}
	
	
	if(hit_frag == 0) { //Miss cases handle

		for(pos = 0; (victim_frag < 0 && pos < cache.sets); pos++) {
			if(Set[pos].sblock[Index].valid == 0) {
				victim = pos;
				victim_frag = 1;
			}
		}
		
		if(victim_frag < 0) {
			for(pos = 0; pos < cache.sets; pos++) {
				if(Set[pos].sblock[Index].LRU > Set[victim].sblock[Index].LRU) {
					victim = pos;
				}
			}
		}
		
		
		
		Set[victim].sblock[Index].valid = 1;
		Set[victim].sblock[Index].LRU = 1;
		Set[victim].sblock[Index].tag = Tag;

		if(Set[victim].sblock[Index].dirty == 1) {
			stats->write_backs++;
		}

		stats->misses++;
		
		if(rw == 'r') {
			Set[victim].sblock[Index].dirty = 0;
			stats->read_misses++;
		}else if(rw == 'w') {
			Set[victim].sblock[Index].dirty = 1;
			stats->write_misses++;
		}
		
	}

}

/**
 * Subroutine for cleaning up memory operations and doing any calculations
 * Make sure to free malloced memory here.
 *
 */
void cache_cleanup (struct cache_stats_t *stats) {
	//int i;	
	for(int i = 0; i <  cache.set_as; i++) {
		free(Set[i].sblock);
	}
	free(Set);
	//printf("accesses: %" PRIu64, (stats->accesses));
	stats->miss_rate = (double) (stats->misses) / (stats->accesses);
	stats->avg_access_time = ((stats->access_time) + ((stats->miss_penalty)*(stats->miss_rate)));
}
