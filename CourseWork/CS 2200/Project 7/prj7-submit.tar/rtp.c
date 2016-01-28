#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include "rtp.h"

/* GIVEN Function:
 * Handles creating the client's socket and determining the correct
 * information to communicate to the remote server
 */
CONN_INFO* setup_socket(char* ip, char* port){
	struct addrinfo *connections, *conn = NULL;
	struct addrinfo info;
	memset(&info, 0, sizeof(struct addrinfo));
	int sock = 0;

	info.ai_family = AF_INET;
	info.ai_socktype = SOCK_DGRAM;
	info.ai_protocol = IPPROTO_UDP;
	getaddrinfo(ip, port, &info, &connections);

	/*for loop to determine corr addr info*/
	for(conn = connections; conn != NULL; conn = conn->ai_next){
		sock = socket(conn->ai_family, conn->ai_socktype, conn->ai_protocol);
		if(sock <0){
			if(DEBUG)
				perror("Failed to create socket\n");
			continue;
		}
		if(DEBUG)
			printf("Created a socket to use.\n");
		break;
	}
	if(conn == NULL){
		perror("Failed to find and bind a socket\n");
		return NULL;
	}
	CONN_INFO* conn_info = malloc(sizeof(CONN_INFO));
	conn_info->socket = sock;
	conn_info->remote_addr = conn->ai_addr;
	conn_info->addrlen = conn->ai_addrlen;
	return conn_info;
}

void shutdown_socket(CONN_INFO *connection){
	if(connection)
		close(connection->socket);
}

/* 
 * ===========================================================================
 *
 *			STUDENT CODE STARTS HERE. PLEASE COMPLETE ALL FIXMES
 *
 * ===========================================================================
 */


/*
 *  Returns a number computed based on the data in the buffer.
 */
static int checksum(char *buffer, int length){

	/*  ----  FIXME  ----
	 *
	 *  The goal is to return a number that is determined by the contents
	 *  of the buffer passed in as a parameter.  There a multitude of ways
	 *  to implement this function.  For simplicity, simply sum the ascii
	 *  values of all the characters in the buffer, and return the total.
	 */ 
	 
	 
	int xor = 0;
	for(int i = 0; i < length; i++) {
		xor ^= buffer[i];
	}
	return xor;
}

/*
 *  Converts the given buffer into an array of PACKETs and returns
 *  the array.  The value of (*count) should be updated so that it 
 *  contains the length of the array created.
 */
static PACKET* packetize(char *buffer, int length, int *count){

	/*  ----  FIXME  ----
	 *  The goal is to turn the buffer into an array of packets.
	 *  You should allocate the space for an array of packets and
	 *  return a pointer to the first element in that array.  Each 
	 *  packet's type should be set to DATA except the last, as it 
	 *  should be LAST_DATA type. The integer pointed to by 'count' 
	 *  should be updated to indicate the number of packets in the 
	 *  array.
	 */ 
	 
	int packet_size = 0;
	 
	packet_size = length / MAX_PAYLOAD_LENGTH;
	if((packet_size*MAX_PAYLOAD_LENGTH) != length) {
		packet_size++; //Allocate length for remaining messages whose length is less than MAX_PAYLOAD_LENGTH 
	}
	*count = packet_size;
	 
	PACKET* array_packets = calloc(packet_size, sizeof(PACKET)); //or using array indexing way
	PACKET* block = calloc(1, sizeof(PACKET)); // or just a block for traversing.
	block = array_packets;
	PACKET* first = array_packets; //To remember the address of first element in the array_packets
	 
	int inner_pos, outer_pos, buffer_pos;
	inner_pos = 0;
	outer_pos = 0;
	buffer_pos = 0;
	 
	for(buffer_pos = 0; buffer_pos < length; buffer_pos++, inner_pos++) {

		if(inner_pos == MAX_PAYLOAD_LENGTH) {
			outer_pos++;
			array_packets= block +outer_pos;
			inner_pos = 0;
		}
		
		array_packets->payload[inner_pos] = buffer[buffer_pos];
		
		if(buffer_pos < (length-1)) {
			array_packets->payload_length = MAX_PAYLOAD_LENGTH;
			array_packets->type = DATA;
			array_packets->checksum = checksum(array_packets->payload, array_packets->payload_length);
		} else {
			array_packets->payload_length = inner_pos + 1; //for a case that has partial length of message
			array_packets->type = LAST_DATA;
			array_packets->checksum = checksum(array_packets->payload, array_packets->payload_length);
			array_packets = first; //reset the array_packets to the address of its first element.
		}
	}

	return array_packets;	
}

/*
 * Send a message via RTP using the connection information
 * given on UDP socket functions sendto() and recvfrom()
 */
int rtp_send_message(CONN_INFO *connection, MESSAGE*msg){
	/* ---- FIXME ----
	 * The goal of this function is to turn the message buffer
	 * into packets and then, using stop-n-wait RTP protocol,
	 * send the packets and re-send if the response is a NACK.
	 * If the response is an ACK, then you may send the next one
	 */

	int count = 0;
	PACKET* send_pack = packetize(msg->buffer, msg->length, &count);

	PACKET* rec_res = calloc(1, sizeof(PACKET));
	int next_pack = 0;

	while(next_pack < count) {
		sendto(connection->socket, &send_pack[next_pack], sizeof(PACKET), 0, connection->remote_addr, connection->addrlen);
		recvfrom(connection->socket, rec_res, sizeof(PACKET), 0, connection->remote_addr, &(connection->addrlen));
		
		if(rec_res->type == ACK) next_pack++;
	}
	free(send_pack);
	free(rec_res);
	return 1;
}

/*
 * Receive a message via RTP using the connection information
 * given on UDP socket functions sendto() and recvfrom()
 */
MESSAGE* rtp_receive_message(CONN_INFO *connection){
	/* ---- FIXME ----
	 * The goal of this function is to handle 
	 * receiving a message from the remote server using
	 * recvfrom and the connection info given. You must 
	 * dynamically resize a buffer as you receive a packet
	 * and only add it to the message if the data is considered
	 * valid. The function should return the full message, so it
	 * must continue receiving packets and sending response 
	 * ACK/NACK packets until a LAST_DATA packet is successfully 
	 * received.
	 */
	
	MESSAGE* msg = calloc(1,sizeof(MESSAGE));
	msg->length = 0;
	int Last_flag = 0;
	
	while(!Last_flag) {
		
		PACKET* rec_pack = calloc(1, sizeof(PACKET));
		PACKET* send_res = calloc(1, sizeof(PACKET));
		send_res->checksum = 0;
		send_res->payload_length = 0;
		recvfrom(connection->socket, rec_pack, sizeof(PACKET), 0, connection->remote_addr, &connection->addrlen);

		int check_sum = checksum(rec_pack->payload, rec_pack->payload_length);
		if(check_sum == rec_pack->checksum) { //Match adding payload to buffer
			send_res->type = ACK;

			int buffer_size = msg->length + rec_pack->payload_length;
			char* buffer = calloc(buffer_size, sizeof(char));
			//char* buffer = malloc(sizeof(char)*buffer_size + 1);			
			int pos;
			for(pos = 0; pos < msg->length; pos++) {
				buffer[pos] = msg->buffer[pos];
			}
			for(pos = 0; pos < rec_pack->payload_length; pos++) {
				buffer[pos + msg->length] = rec_pack->payload[pos];
			}
			
			free(msg->buffer);
			msg->buffer = buffer;
			msg->length = buffer_size;	
			
			if(rec_pack->type == LAST_DATA) Last_flag = 1; //May take out
		} else {
			send_res->type = NACK;
		}
		sendto(connection->socket, send_res, sizeof(PACKET), 0, connection->remote_addr, connection->addrlen);
		free(rec_pack);
		free(send_res);
	}
	return msg;
}



















