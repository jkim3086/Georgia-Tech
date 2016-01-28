; Name:


; Main
; Do not edit this function!

.orig x3000

	LD R6, STACK	; Initialize the stack

	LEA R0, STRING	; R0 = &str[0]
	ADD R1, R0, 0

SL_LOOP	LDR R2, R1, 0	; \ R1 = strlen(str)
	BRz SL_END	; |
	ADD R1, R1, 1	; |
	BR SL_LOOP	; |
SL_END	NOT R2, R0	; |
	ADD R2, R2, 1	; |
	ADD R1, R1, R2	; /

	ADD R6, R6, -2	; \ R0 = eval(str, len)
	STR R0, R6, 0	; |
	STR R1, R6, 1	; |
	LD R2, EVALPTR	; |
	JSRR R2		; |
	LDR R0, R6, 0	; |
	ADD R6, R6, 3	; /

	ST R0, ANS
	HALT

STACK	.fill xf000
ANS	.fill -1
EVALPTR	.fill EVAL
STRING	.stringz "1+2+3+4+5"
	.blkw 200

EVAL
	; Write your function here
	ADD R6, R6, -3
	STR R7, R6, 1	; ret add
	STR R5, R6, 0	; ofp
	ADD R5, R6, -1	; fp pointer

	ADD R6, R6, -1	; MOVE THE LOCATION TO THE R5 -1 LOCAL VAR I 
	AND R3, R3, 0
	STR R3, R6, 0

	ADD R6, R6, -2  ; ROOM FOR LEFT AND RIGHT

	LDR R1, R5, 5
	
	BRZ GO

	;CHECKING +
	ADDICTION
	LDR R4, R5, 4    ; GET STR
	LDR R1, R5, 5    ; GET LEN
	ADD R0, R4, R3   ; STR + I
	LDR R3, R0, 0    ; GET CHAR
	ADD R3, R3, -15  ; CHECKING +
	ADD R3, R3, -15
	ADD R3, R3, -13
	BRNP SKIP1

	ADD R6, R6, -2	; \ R0 = eval(str, len)
	LDR R0, R5, 4   ; STORE STR
	STR R0, R6, 0	;
	LDR R1, R5, 0   ; LOAD i
	STR R1, R6, 1	; 
	JSR EVAL        ; FOR LEFT
	
	LDR R0, R6, 0
	ADD R6, R6, 3
	STR R0, R5, -1
	
	ADD R6, R6, -2	; \ R0 = eval(str, len)
	LDR R0, R5, 4   ; STORE STR
	LDR R3, R5, 0  ; LOAD I
	ADD R3, R3, 1
	ADD R0, R0, R3
	STR R0, R6, 0
	NOT R3, R3
	ADD R3, R3, 1   ; FLIP
	LDR R1, R5, 5   ; LOAD LEN
	ADD R1, R1, R3
	STR R1, R6, 1	; 
	JSR EVAL        ; FOR RIGHT	
	
	LDR R0, R6, 0
	ADD R6, R6, 3
	STR R0, R5, -2

	LDR R0, R5, -2
	LDR R1, R5, -1
	ADD R0, R0, R1
	STR R0, R5, 3	
	BR EXIT	
	
	SKIP1
	LDR R3, R5, 0
	ADD R3, R3, 1     ; I++
	STR R3, R5, 0
	NOT R1, R1
	ADD R1, R1, 1
	ADD R1, R3, R1
	BRN ADDICTION

	GO
	LDR R0, R5, 4	
	LDR R0, R0, 0	
	ADD R0, R0, -16
	ADD R0, R0, -16
	ADD R0, R0, -16	
	STR R0, R5, 3	

	EXIT
	ADD R6, R5, 3    ; STORE R6 RETURN VALUE ADDRESS
	LDR R7, R5, 2
	LDR R5, R5, 1
 	RET

.end
