;;===============================
;;Name: Jeongsoo Kim
;;===============================

.orig x3000

; CODE GOES HERE! :D
AND R0, R0, 0        ; CLEAR R0
AND R1, R1, 0        ; CLEAR R1
AND R2, R2, 0        ; CLEAR R2

LD R0, A             ; RO=A
LD R1, B             ; R1=B
NOT R1, R1           ; ~R1
ADD R1, R1, 1        ; TWO'S COMPLEMENT

WHP
ADD R0, R0, R1       ; R0=R0+R1
BRP WHP              ; IF R0 > 0, GO TO WHP
BRZ STORE1           ; IF R0 == 0, GOTO STORE1
BRN STORE0           ; IF R0 < 0, GO TO STORE0
STORE1 ADD R2, R2, 1 ; R2++
STORE0 ST R2, ANSWER ; ANSWER=R2
HALT                 ; HALT

A       .fill 25
B       .fill 5
ANSWER  .fill 0
.end
