;;===============================
;;Name: JEONGSOO KIM
;;===============================

.orig x3000

; CODE GOES HERE! :D
AND R0, R0, 0 ; Number
AND R1, R1, 0 ; Length
AND R2, R2, 0 ; MEMORY ADDRESS               
AND R3, R3, 0 ; A VALUE AT MEMORY ADDRESS
AND R4, R4, 0 ; A VALUE FOR IF-STATEMENT
AND R5, R5, 0 ; ANSWER

LD R0, NUMBER ; STORE NUMBER
LD R1, LENGTH ; STORE LENGTH
LD R2, ARRAY  ; STORE ARRAY
NOT R0, R0    ; NOT R0
ADD R0, R0, 1 ; TWO'S COMPLEMENT

LOOP 
LDR R3, R2, 0      ; GETTING A VALUE
ADD R4, R3, R0     ; CHECKING THE CONDITION
BRNP ELSE          ; R4 != 0 GO TO ELSE
ADD R5, R5, 1      ; COUNT++
ELSE ADD R2, R2, 1 ; MEMORY ADDRESS++
ADD R1, R1, -1     ; R1-- 
BRP LOOP           ; IF R1 > 0, GO TO LOOP
ST R5, ANSWER      ; ANSWER = R5
HALT               ; HALT           
	
NUMBER  .fill 9
ARRAY   .fill x6000
LENGTH  .fill 10
ANSWER	.fill 0
.end

.orig x6000
.fill 8
.fill 9
.fill 7
.fill 0
.fill -3
.fill 11
.fill 9
.fill -9
.fill 2
.fill 9
.end
