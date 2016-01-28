/**
 * CS 2110 - Spring 2015 - Homework #10
 * Edited by: Brandon Whitehead, Andrew Wilder
 *
 * list.c: Complete the functions!
 */

#include <stdlib.h>
#include <stdio.h>
#include "list.h"

/* The node struct.  Has a prev pointer, next pointer, and data. */
/* DO NOT DEFINE ANYTHING OTHER THAN WHAT'S GIVEN OR YOU WILL GET A ZERO*/
/* Design consideration only this file should know about nodes */
/* Only this file should be manipulating nodes */
/* DO NOT TOUCH THIS DECLARATION DO NOT PUT IT IN OTHER FILES */
typedef struct lnode
{
  struct lnode* prev; /* Pointer to previous node */
  struct lnode* next; /* Pointer to next node */
  void* data; /* User data */
} node;


/* Do not create any global variables here. Your linked list library should obviously work for multiple linked lists */
// This function is declared as static since you should only be calling this inside this file.
static node* create_node(void* data);

/** create_node
  *
  * Helper function that creates a node by allocating memory for it on the heap.
  * Be sure to set its pointers to NULL.
  *
  * @param data a void pointer to data the user wants to store in the list
  * @return a node
  */
static node* create_node(void* data)
{
	node* new_node = (node*) malloc(sizeof(node) * 1);
	new_node->prev = NULL;
	new_node->next = NULL;
	new_node->data = data;

    /// @todo Implement changing the return value!
    	return new_node;
}

/** create_list
  *
  * Creates a list by allocating memory for it on the heap.
  * Be sure to initialize size to zero and head/tail to NULL.
  *
  * @return an empty linked list
  */
list* create_list(void)
{
	list* new_list = (list*) malloc(sizeof(list) * 1);
	new_list->head = NULL;
	new_list->tail = NULL;
	new_list->size = 0;
    /// @todo Implement changing the return value!
    	return new_list;
}

/** push_front
  *
  * Adds the data to the front of the linked list
  *
  * @param llist a pointer to the list.
  * @param data pointer to data the user wants to store in the list.
  */
void push_front(list* llist, void* data)
{
	node* add_node = create_node(data);
	
	if(llist->tail == NULL || llist->head == NULL){
		llist->head = add_node;		
		llist->tail = add_node;
	} else{
		add_node->next = llist->head;
		llist->head->prev = add_node;	
		llist->head = add_node;
		//add_node->prev == NULL;
	}
	llist->size += 1;
}

/** push_back
  *
  * Adds the data to the back/end of the linked list
  *
  * @param llist a pointer to the list.
  * @param data pointer to data the user wants to store in the list.
  */
void push_back(list* llist, void* data)
{
	node* add_node = create_node(data);

	if(llist->head == NULL && llist->tail == NULL){
		llist->head = add_node;
		llist->tail = add_node;
	} else{
		add_node->prev = llist->tail;	
		llist->tail->next = add_node;	
		llist->tail = add_node;
	}
	llist->size += 1;
}

/** front
  *
  * Gets the data at the front of the linked list
  * If the list is empty return NULL.
  *
  * @param llist a pointer to the list
  * @return The data at the first node in the linked list or NULL.
  */
void* front(list* llist)
{
    /// @todo Implement changing the return value!
    /// @note you are returning the HEAD's DATA not the head node. Remember the user should never deal with the linked list nodes.
	if(llist->head == NULL){
    		return NULL;
	}
	return llist->head->data;
}

/** back
  *
  * Gets the data at the "end" of the linked list
  * If the list is empty return NULL.
  *
  * @param llist a pointer to the list
  * @return The data at the last node in the linked list or NULL.
  */
void* back(list* llist)
{
    /// @todo Implement changing the return value!
	if(llist->head == NULL){
    		return NULL;
	}
	return llist->tail->data;
}

/** remove_front
  *
  * Removes the node at the front of the linked list
  *
  * @warning Note the data the node is pointing to is also freed. If you have any pointers to this node's data it will be freed!
  *
  * @param llist a pointer to the list.
  * @param free_func pointer to a function that is responsible for freeing the node's data.
  * @return -1 if the remove failed (which is only there are no elements) 0 if the remove succeeded.
  */
int remove_front(list* llist, list_op free_func)
{
	node* temp = llist->head;
    /// @todo Implement
    /// @note remember to also free the node itself
    /// @note free_func is a function that is responsible for freeing the node's data only.
	if(llist->head == NULL){    
		return -1;
	}
	llist->head = llist->head->next;
	if(llist->head != NULL){
		llist->head->prev = NULL;
	}
	free_func(temp->data);
	free(temp);
	llist->size--;
	return 0;
}	

/** remove_back
  *
  * Removes the node at the back of the linked list
  *
  * @warning Note the data the node is pointing to is also freed. If you have any pointers to this node's data it will be freed!
  *
  * @param llist a pointer to the list.
  * @param free_func pointer to a function that is responsible for freeing the node's data.
  * @return -1 if the remove failed 0 if the remove succeeded.
  */
int remove_back(list* llist, list_op free_func)
{
	node* temp = llist->tail;
	if(llist->tail == NULL){
		return -1;
	}
	llist->tail = llist->tail->prev;
	if(llist->tail != NULL){
		llist->tail->next = NULL;
	}
	free_func(temp->data);
	free(temp);
	llist->size--;
	return 0;
    /// @todo Implement
    /// @note Remember to also free the node itself
    /// @note free_func is a function that is responsible for freeing the node's data only.
}

/** copy_list
  *
  * Create a new list structure, new nodes, and new copies of the data by using
  * the copy function. Its implementation for any test structure must copy
  * EVERYTHING!
  *
  * @param llist A pointer to the linked list to make a copy of
  * @param copy_func A function pointer to a function that makes a copy of the
  *        data that's being used in this linked list, allocating space for
  *        every part of that data on the heap. This is some function you must
  *        write yourself for testing, tailored specifically to whatever context
  *        you're using the linked list for in your test.
  * @return The linked list created by copying the old one
  */
list* copy_list(list* llist, list_cpy copy_func)
{
	list* copy_list = create_list();
	node* current = llist->head;
	if(llist->head == NULL){
		return copy_list;
	}
	while(current != NULL){
		push_back(copy_list,copy_func(current->data));
		current = current->next;
	}
	return copy_list;
	/*list traverse;
	if(llist->head != NULL){
		traverse->head = llist->head;
		for(int i = 0; i <= llist->size; i++){
			node* copy_node = creat_node(traverse.head->data);
			push_back(copy_list, copy_node->data);
			traverse.head = traverse.head->next;		
		}
	}*/
	/// @todo implement
}

/** size
  *
  * Gets the size of the linked list
  *
  * @param llist a pointer to the list
  * @return The size of the linked list
  */
int size(list* llist)
{
    ///@note simply return the size of the linked list.  Its that easy!
    return llist->size;
}

/** remove_if
  *
  * Removes all nodes whose data when passed into the predicate function returns true
  *
  * @param llist a pointer to the list
  * @param pred_func a pointer to a function that when it returns true it will remove the element from the list and do nothing otherwise @see list_pred.
  * @param free_func a pointer to a function that is responsible for freeing the node's data
  * @return the number of nodes that were removed.
  */
int remove_if(list* llist, list_pred pred_func, list_op free_func)
{
	node* current = llist->head;
	int count = 0;
	while(current != NULL){
		if(pred_func(current->data)){
			if(llist->head == current){
				if(current->next) current->next->prev = NULL;
				llist->head = current->next;
			} else if(llist->tail == current){
				if(current->prev) current->prev->next = NULL;
				llist->tail = current->prev;
			} else{
				current->prev->next = current->next;
				current->next->prev = current->prev;
			}
			node* tmp = current->next;
			free_func(current->data);
			free(current);
			count++;
			current = tmp;
		}
		else current = current->next;
	}
	llist->size = (llist->size) - count;
    /// @todo Implement changing the return value!
    /// @note remember to also free all nodes you remove.
    /// @note be sure to call pred_func on the NODES DATA to check if the node needs to be removed.
    /// @note free_func is a function that is responsible for freeing the node's data only.
    return count;
}

/** is_empty
  *
  * Checks to see if the list is empty.
  *
  * @param llist a pointer to the list
  * @return 1 if the list is indeed empty 0 otherwise.
  */
int is_empty(list* llist)
{
    ///@note an empty list by the way we want you to implement it has a size of zero and head points to NULL.
	return llist->head == NULL && llist->size == 0;
}

/** empty_list
  *
  * Empties the list after this is called the list should be empty.
  *
  * @param llist a pointer to a linked list.
  * @param free_func function used to free the node's data.
  */
void empty_list(list* llist, list_op free_func)
{
	while(llist->head != NULL){
		node* temp = llist->head;
		free_func(temp->data);
		llist->head = llist->head->next;		
		free(temp);
	}
    /// @todo Implement
    /// @note Free all of the nodes not the linked list itself.
    /// @note do not free llist.
}

/** traverse
  *
  * Traverses the linked list calling a function on each node's data.
  *
  * @param llist a pointer to a linked list.
  * @param do_func a function that does something to each node's data.
  */
void traverse(list* llist, list_op do_func)
{
	node* temp = llist->head;
	while(temp != NULL){
		do_func(temp->data);
		temp = temp->next;
	}
    /// @todo Implement
}
