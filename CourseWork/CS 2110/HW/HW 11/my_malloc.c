#include "my_malloc.h"

/* You *MUST* use this macro when calling my_sbrk to allocate the 
 * appropriate size. Failure to do so may result in an incorrect
 * grading!
 */
#define SBRK_SIZE 2048

/* If you want to use debugging printouts, it is HIGHLY recommended
 * to use this macro or something similar. If you produce output from
 * your code then you will receive a 20 point deduction. You have been
 * warned.
 */
#ifdef DEBUG
#define DEBUG_PRINT(x) printf x
#else
#define DEBUG_PRINT(x)
#endif


/* make sure this always points to the beginning of your current
 * heap space! if it does not, then the grader will not be able
 * to run correctly and you will receive 0 credit. remember that
 * only the _first_ call to my_malloc() returns the beginning of
 * the heap. sequential calls will return a pointer to the newly
 * added space!
 * Technically this should be declared static because we do not
 * want any program outside of this file to be able to access it
 * however, DO NOT CHANGE the way this variable is declared or
 * it will break the autograder.
 */
void* heap;

/* our freelist structure - this is where the current freelist of
 * blocks will be maintained. failure to maintain the list inside
 * of this structure will result in no credit, as the grader will
 * expect it to be maintained here.
 * Technically this should be declared static for the same reasons
 * as above, but DO NOT CHANGE the way this structure is declared
 * or it will break the autograder.
 */
metadata_t* freelist[8];
/**** SIZES FOR THE FREE LIST ****
 * freelist[0] -> 16
 * freelist[1] -> 32
 * freelist[2] -> 64
 * freelist[3] -> 128
 * freelist[4] -> 256
 * freelist[5] -> 512
 * freelist[6] -> 1024
 * freelist[7] -> 2048
 */

int get_index(int size){
	int index = 0;
		int base = size/16 + (size%16 != 0);
	while(base>1){
		base = (base/2) + (base%2 != 0);
		index++;
	}
	return index;
}

metadata_t* Take_block(int index){
  metadata_t* target_block = freelist[index];
  if(target_block->next != NULL){
    target_block->next->prev = NULL;
	}
	freelist[index] = target_block->next;
	target_block->next = NULL;
  return target_block;
}

metadata_t* Spliting_block(int tar_index, metadata_t* big_block){
        metadata_t* cur = big_block;
				cur->size = (cur->size) / 2;
	      metadata_t* split_block = (metadata_t *) ((char*)cur + cur->size); //Adding metadata??? or no need because metadata is already added in my_malloc
	      split_block->size = cur->size;
        split_block->in_use = 0;
				split_block->next = NULL;
				split_block->prev = NULL;
				cur->next = NULL;
				if(freelist[tar_index] != NULL){
					freelist[tar_index]->prev = cur;
					cur->next = freelist[tar_index];
				}
				freelist[tar_index]=cur;
				cur->prev = NULL;
				return split_block;
}

metadata_t* getBlock(int index) {
	metadata_t* block;
	int index_copy = index + 1;
	if(freelist[index] == NULL){
		if(index == 7){
			block = my_sbrk(SBRK_SIZE);
			if(block == NULL){
				ERRNO = OUT_OF_MEMORY;
				return NULL;
			}

			if(heap == NULL) heap = block;

			block->size = SBRK_SIZE;
    	block->in_use = 0;
			block->next = NULL;
			block->prev = NULL;	
			return block;
		}
		block = getBlock(index_copy);
		if(block == NULL) return NULL;
    return Spliting_block(index, block);
	}else{
     return Take_block(index);
  }
}

void* my_malloc(size_t size)
{	
	metadata_t* block;
	int actual_size = size + sizeof(metadata_t); //using special data 
	if(actual_size > SBRK_SIZE){
		ERRNO = SINGLE_REQUEST_TOO_LARGE;
		return NULL;
	}
	block = getBlock(get_index(actual_size));
	if(block != NULL){
		ERRNO = NO_ERROR;	
		block->in_use = 1;
		return block + 1;
	}
	return NULL;
}

void* my_calloc(size_t num, size_t size)
{
	char* a = my_malloc(num * size);
	if(a){
		for (int i = 0; i < (num*size); i++){
			a[i] = 0;
		}
	}		
	return a;	
}

metadata_t* Find_buddy(metadata_t* target_bud)
{
	
	uintptr_t bud_pos = (((uintptr_t)target_bud - (uintptr_t)heap) ^ ((uintptr_t)target_bud->size)) + (uintptr_t)heap;
	metadata_t* find_bud = (metadata_t*) bud_pos;
  if (target_bud->size == find_bud->size) return find_bud; //Is that it?
	return NULL;
}

void return_block(int index, metadata_t* return_block){
			if(freelist[index] != NULL){			
				freelist[index]->prev = return_block;
				return_block->next = freelist[index];
				freelist[index] = return_block;
				return_block->prev = NULL;
			}else{
				freelist[index] = return_block;
				return_block->prev = NULL;
				return_block->next = NULL;	
			}
}

int compare_address(metadata_t* target, metadata_t* tar_buddy){
			if (target > tar_buddy) return 1;
			return 0;
}
/*void remove_blocks(metadata_t* left, metadata_t* right, int index){
			if(left->prev == NULL && right->next == NULL){			
				freelist[index] = NULL;
			}else if(left->prev != NULL && right->next == NULL){
				left->prev->next = NULL;
			}else if(left->prev == NULL && right->next != NULL){
				//right->next->prev = NULL;
				freelist[index] = right->next;
			}else if(left->prev != NULL && right->next != NULL){			
				left->prev->next = right->next;
				right->next->prev = left->prev;
			}
			left->next = NULL;
			right->prev = NULL;
}*/

metadata_t* remove_blocks(metadata_t* block, int index)
{	
	metadata_t* right_check = block->next;
	metadata_t* left_check = block->prev;

	if (left_check && right_check){
		left_check->next = right_check;
		right_check->prev = left_check;
	}else if (left_check && !right_check){
		left_check->next = NULL;
	}else if (!left_check && right_check){
		freelist[index] = right_check;
		right_check->prev = NULL;
	}else{
		freelist[index] = NULL;
	}
	block->next = NULL;
	block->prev = NULL;
	return block;
}

metadata_t* combine_blocks(metadata_t* target, metadata_t* tar_buddy, int tar_list_index){
		 	int position = compare_address(target, tar_buddy);
			if(position == 0){
			 	remove_blocks(target, tar_list_index);
				remove_blocks(tar_buddy, tar_list_index);
				target->size = (target->size) * 2;
				return target;
			}else{
		 		remove_blocks(target, tar_list_index);
				remove_blocks(tar_buddy, tar_list_index);
				tar_buddy->size = (tar_buddy->size) * 2;
				return tar_buddy;
			}
}

void add_block(metadata_t* block, int index){
	metadata_t* cur = freelist[index];
		if(cur != NULL){
			cur->prev = block;
			block->next = freelist[index];
			freelist[index] = block;
			block->prev = NULL;
		}else{
			freelist[index] = block;
			block->prev = NULL;
			block->next = NULL;
		} 
}

void my_free(void* ptr)
{
  //Do I need to do null checking for ptr
	metadata_t* target_block = ((metadata_t *)ptr) - 1;
	metadata_t* buddy = Find_buddy(target_block);
	metadata_t* combined_block;

	int size = (int)(target_block->size);
	int index = get_index(size);
		if(buddy == NULL && target_block->in_use == 0){
			printf("The Block is already free\n"); 
		}//Which error case should be used for this case
		else if(buddy != NULL && buddy->in_use == 1) {
				return_block(index, target_block);
		}// There is buddy for the targe block, but the buddy is already being used.
		else if(buddy != NULL && buddy->in_use == 0 && buddy->size != SBRK_SIZE) {
				combined_block = combine_blocks(target_block, buddy, index);
				add_block(combined_block, get_index(combined_block->size));
				
				if (Find_buddy(combined_block) && combined_block->size != SBRK_SIZE){
					my_free(((metadata_t*)combined_block) + 1);
				}else{
					 ERRNO = NO_ERROR;
				}
		}
		target_block->in_use = 0;
}

void* my_memmove(void* dest, const void* src, size_t num_bytes)
{
    char *DEST = (char *) dest;
    char *SOURCE = (char *) src;
    
    if(DEST == SOURCE){
    	return DEST;
    }

    if(SOURCE > DEST){
    	for (int i = 0; i < num_bytes; i++){
    		DEST[i] = SOURCE[i];
    	}
    }else if (DEST > SOURCE){
    	for (int i = num_bytes - 1; i >= 0; i--){
    		DEST[i] = SOURCE[i];
    	}
    }  
    return DEST;
}
