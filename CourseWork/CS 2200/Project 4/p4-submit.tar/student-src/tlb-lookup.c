#include <stdlib.h>
#include <stdio.h>
#include "tlb.h"
#include "pagetable.h"
#include "global.h" /* for tlb_size */
#include "statistics.h"

/*******************************************************************************
 * Looks up an address in the TLB. If no entry is found, attempts to access the
 * current page table via cpu_pagetable_lookup().
 *
 * @param vpn The virtual page number to lookup.
 * @param write If the access is a write, this is 1. Otherwise, it is 0.
 * @return The physical frame number of the page we are accessing.
 */
pfn_t tlb_lookup(vpn_t vpn, int write) {
   pfn_t pfn;

   /* 
    * FIX ME : Step 6
    */

   /* 
    * Search the TLB for the given VPN. Make sure to increment count_tlbhits if
    * it was a hit!
    */
    	int i = 0;
	for(i=0; i < tlb_size; i++) {
		
		if(tlb[i].valid) {
		
			if(tlb[i].vpn == vpn) {

				tlb[i].used = 1;
				tlb[i].dirty = write;
				count_tlbhits++;
				return tlb[i].pfn;
			
			}
		}
	}
	
	
   /* If it does not exist (it was not a hit), call the page table reader */
   pfn = pagetable_lookup(vpn, write);

   /* 
    * Replace an entry in the TLB if we missed. Pick invalid entries first,
    * then do a clock-sweep to find a victim.
    */
	int set = 0;
	i = 0;
	
	while((set != 3) && (i < tlb_size)) {
		
		if(set == 0){ //Allow when set == 0 for check invalid first
		
			if(!tlb[i].valid) {
				tlb[i].vpn = vpn;
				tlb[i].pfn = pfn;
				tlb[i].valid = 1;
				tlb[i].dirty = write;
				tlb[i].used = 1;
				set = 1;
				i = 0;
			}
	
		}
		
		if(set == 1) { // Check for unused
			
			if(!tlb[i].used) {
				tlb[i].vpn = vpn;
				tlb[i].pfn = pfn;
				tlb[i].valid = 1;
				tlb[i].dirty = write;
				tlb[i].used = 1;
				set = 3;
			} else{
				tlb[i].used = 0;
			}
			
			if(((i+1) == tlb_size) && (set == 1)) { //End of TLB case
				tlb[0].vpn = vpn;
				tlb[0].pfn = pfn;
				tlb[0].valid = 1;
				tlb[0].dirty = write;
				tlb[i].used = 1;
				set = 3;
			}
		}
		
		i++;
		if((i == tlb_size) && (set == 0)) { //Reset i for unused check
			i = 0;
			set = 1;
		}
	
	}

   /*
    * Perform TLB house keeping. This means marking the found TLB entry as
    * accessed and if we had a write, dirty. We also need to update the page
    * table in memory with the same data.
    *
    * We'll assume that this write is scheduled and the CPU doesn't actually
    * have to wait for it to finish (there wouldn't be much point to a TLB if
    * we didn't!).
    */

   return pfn;
}

