#include "list.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

/* Here we are going to write some functions to support a linked list that stores
 * Person data (name, age)
 */
typedef struct person_t
{
    char* name;
    int age;
} Person;

/* Example functions given to you. If you want to truly grasp these concepts, try
 * writing your own structs and functions as well!
 */

// Create a new Person
Person* create_person(const char* name, int age)
{
    Person* p = (Person*) malloc(sizeof(Person));
    p->name = strdup(name); // Uses malloc!
    p->age = age;
    return p;
}

// Make a deep copy of a Person
void* copy_person(const void* data)
{
    Person *p = (Person*) data;
    return create_person(p->name, p->age);
}

// Print a Person
void print_person(void* data)
{
    Person *p = (Person*) data;
    printf("%s, %d\n", p->name, p->age);
}

// Free a Person
void free_person(void* data)
{
    // This is safe because we should only be passing in Person struct pointers
    Person *p = (Person*) data;
    // free any malloc'd pointers contained in the Person struct (just name)
    free(p->name);
    // Now free the struct itself; this takes care of non-malloc'd data, like age.
    free(p);
}

// Return 1 if the person's name is 8+ characters long
int long_name(const void *data)
{
	Person *p = (Person*) data;
	return strlen(p->name) > 7;
}

int too_old(const void *data)
{
	Person *p = (Person*) data;
	return (p->age) > 25;
}

/* This main function does a little testing
   Like all good CS Majors you should test
   your code here. There is no substitute for testing
   and you should be sure to test for all edge cases
   e.g., calling remove_front on an empty list.
*/
int main(void)
{
	/* Now to make use of all of this stuff */
	list* llist = create_list();

  	/* What does an empty list contain?  Lets use our handy traversal function */
  	printf("TEST CASE 1\nAn Empty list should print nothing here:\n");
  	traverse(llist, print_person);
	printf("\n");

 	/* Lets add a person and then print */
 	push_front(llist, create_person("Andrew", 24));
 	printf("TEST CASE 2\nA List with one person should print that person:\n");
 	traverse(llist, print_person);
 	printf("\n");

 	/* Lets remove that person and then print */
 	remove_front(llist, free_person);
 	printf("TEST CASE 3\nAnother Empty list should print nothing here:\n");
 	traverse(llist, print_person);
 	printf("\n");

 	/* Lets add two people and then print */
 	push_front(llist, create_person("Nick", 22));
 	push_front(llist, create_person("Randal", 21));
 	printf("TEST CASE 4\nA List with two people should print those two people:\n");
 	traverse(llist, print_person);
 	printf("\n");

	/* Lets copy this list */
	list* llist2 = copy_list(llist, copy_person);
	printf("TEST CASE 5\nA copied list should print out the same two people:\n");
 	traverse(llist2, print_person);
 	printf("\n");

  	/* Lets kill the list */
  	empty_list(llist, free_person);
 	printf("TEST CASE 6\nAfter freeing all nodes the list should be empty:\n");
 	traverse(llist, print_person);
	printf("\n");

	/* Let's make a list of people, and remove certain ones! */
	/* Should remove anyone whose name is 8+ characters long */
	push_front(llist, create_person("Josephine", 27));
	push_front(llist, create_person("Dave", 34));
	push_front(llist, create_person("Benjamin", 23));
	push_front(llist, create_person("Lisa", 41));
	push_front(llist, create_person("Maximilian", 24));
	remove_if(llist, long_name, free_person);
	printf("TEST CASE 7\nShould only print 2 people with short names:\n");
	traverse(llist, print_person);

 	/* YOU ARE REQUIRED TO MAKE MORE TEST CASES THAN THE ONES PROVIDED HERE */
 	/* You will get points off if you do not you should at least test each function here */

 	/* Testing over clean up*/
	empty_list(llist, free_person);
 	free(llist);
	empty_list(llist2, free_person);
	free(llist2);
	
	/*---------------------------Self test code----------------------------------------*/
	/* Test my linked_list! */
	/* Creat list */
	list* test_list = create_list();

	/* Make sure of the initial status of the list */
  	printf("CUSTOM TEST CASE 1\nAn Empty list should print nothing here:\n");
  	traverse(test_list, print_person);
	printf("\n");

	/* Adding people to the list */
	/* Test push_front and push_back */
 	push_front(test_list, create_person("Tyler", 21));
	push_back(test_list, create_person("Banjo", 21));
	push_back(test_list, create_person("Ronaldinho", 35));
	push_back(test_list, create_person("Jake", 21));
	push_front(test_list, create_person("Hanbeen", 21));
 	printf("CUSTON TEST CASE 2\nThe test_list should print Hanbeen and Tyler first and then Banjo, Ronaldinho and Jake:\n");
 	traverse(test_list, print_person);
 	printf("\n");

	/* Copy the test_list */
	list* test_list2 = copy_list(test_list, copy_person);
	printf("CUSTON TEST CASE 3\nThe test_list2 should print out the same people in the test_list:\n");
 	traverse(test_list2, print_person);
 	printf("\n");

	/* Testing Removing using test_list2*/
	remove_front(test_list2, free_person);
	printf("CUSTON TEST CASE 4\nThe test_list2 should not print Hanbeen:\n");
	traverse(test_list2, print_person);
 	printf("\n");
	remove_if(test_list2, too_old, free_person);
	printf("The test_list2 now should not print Ronaldinho:\n");
	traverse(test_list2, print_person);
 	printf("\n");	
	remove_back(test_list2, free_person);
	printf("The test_list2 now should not print Jake:\n");
	traverse(test_list2, print_person);
 	printf("\n");
	
	/* Testing is_empty, size, and empty_list */
	printf("CUSTON TEST CASE 5\nThis test case should print size:2 and is_empty:0\n");
	printf("Its size: %d, and is_empty returned: %d.\n", size(test_list2),is_empty(test_list2));
	printf("These are people, who are actually in the test_list2.\n");
	traverse(test_list2, print_person);
	printf("Now make the list empty! So the list should print nothing!\n");
	empty_list(test_list2, free_person);
	traverse(test_list2, print_person);
	printf("\n\n");

	/* Testing front back using test_list */
	printf("CUSTON TEST CASE 5\nIn this case, Hanbeen should be printed\n");
	print_person(front(test_list));
	printf("CUSTON TEST CASE 6\nIn this case, Jake should be printed\n");
	print_person(back(test_list));

	/* Testing over clean up*/
	empty_list(test_list, free_person);
 	free(test_list);
	empty_list(test_list2, free_person);
	free(test_list2);
  	return 0;
}

