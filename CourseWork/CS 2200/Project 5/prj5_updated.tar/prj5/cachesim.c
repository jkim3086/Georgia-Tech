/* CS 2200 - Project 5 - Fall 2015
 * Name -
 * GTID -
 */

#include "cachesim.h"
/**
 * Sub-routine for initializing your cache with the parameters.
 * You may initialize any global variables here.
 *
 * @param C The total size of your cache is 2^C bytes
 * @param S The set associativity is 2^S
 * @param B The size of your block is 2^B bytes
 */
void cache_init(uint64_t C, uint64_t S, uint64_t B) {

}

/**
 * Subroutine that simulates one cache event at a time.
 * @param rw The type of access, READ or WRITE
 * @param address The address that is being accessed
 * @param stats The struct that you are supposed to store the stats in
 */
void cache_access (char rw, uint64_t address, struct cache_stats_t *stats) {

}

/**
 * Subroutine for cleaning up memory operations and doing any calculations
 * Make sure to free malloced memory here.
 *
 */
void cache_cleanup (struct cache_stats_t *stats) {

}
