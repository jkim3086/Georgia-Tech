;;===============================
;;Name: JEONGSOO KIM
;;===============================

.orig x3000

; CODE GOES HERE :D
AND R0, R0, 0 ; ORIGIN_LENGTH
AND R1, R1, 0 ; MEMORY ADDRESS
AND R2, R2, 0 ; A VALUE AT MEMORY ADDRESS
AND R3, R3, 0 ; SUB_LENGTH
AND R4, R4, 0 ; NEXT_VALUE
AND R5, R5, 0 ; TEMP
AND R6, R6, 0 ; NEXT_MEMORY_LOCATION
 
LD R0, LENGTH   ; LOAD LENGTH
LD R1, ARRAY    ; INITIAL POSITION
ADD R6, R6, R1  ; R6 = R1

LOOP 
AND R3, R3, 0   ; CLEAR R3
ADD R3, R0, -1  ; SUB_LENGTH=(LENGTH-1)
LD R1, ARRAY    ; INITIALIZE MEMORY LOCATION
LD R6, ARRAY    ; INITIALIZE MEMORY LOCATION
	
	SUBLOOP 
	LDR R2, R1, 0          ; LOAD VALUE AT MEMORRY ADDRESS
	ADD R6, R6, 1          ; R6++
	LDR R4, R6, 0  	       ; LOAD NEXT VALUE
	NOT R4, R4             ; FLIP R4
	ADD R4, R4, 1          ; TWO'S COMPLEMENT
	ADD R5, R2, R4         ; COMPARE
	BRNZ ELSE              ; IF R5 == 0 | R5 < 0, GO TO ELSE
	AND R5, R5, 0          ; CLEAR TEMP
	ADD R5, R5, R2         ; STORE R2
	NOT R4, R4             ; FLIP R4 AGAIN
	ADD R4, R4, 1          ; TWO'S COMPLEMENT
	STR R4, R1, 0	       ; STORE R4 TO MEM[R1]
	STR R5, R6, 0          ; STORE R5 TO MEM[R6]
	AND R2, R2, 0          ; CLEAR R2
	AND R4, R4, 0          ; CLEAR R4
	AND R5, R5, 0          ; CLEAR R5
	ELSE
	ADD R1, R1, 1	       ; R1++
	ADD R3, R3, -1         ; R3--
	BRP SUBLOOP            ; IF R3 > 0, GO TO SUBLOOP

ADD R0, R0, -1  ; R0--
BRP LOOP        ; IF R0 > 0, GO TO LOOP
HALT            ; HALT

ARRAY   .fill x6000
LENGTH  .fill 12
.end

.orig x6000
.fill 28
.fill -50
.fill 7
.fill 0
.fill 216
.fill 4
.fill 15
.fill -82
.fill 34
.fill 101
.fill -5
.fill 61
.end


