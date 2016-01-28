;;===============================
;;Name:Jeongsoo Kim
;;===============================

.orig x3000

; CODE GOES HERE! :D
AND R0, R0, 0   ; CLEAR R0
AND R1, R1, 0   ; CLEAR R1
AND R2, R2, 0   ; CLEAR R2

LD R0, A        ; R0 = A
LD R1, B        ; R1 = B

NOT R0, R0      ; ~R0
ADD R0, R0, 1   ; TWO'S COMPLEMENT
NOT R1,R1       ; ~R1
ADD R1, R1, 1   ; TWO'S COMPLEMENT

AND R2, R0, R1  ; R0 & R1
NOT R2, R2      ; ~R2
ADD R2, R2, 1   ; TWO'S COMPLEMENT
ST R2, ANSWER   ; ANSWER = R2
HALT            ; HALT

A       .fill 6
B       .fill 11
ANSWER  .fill 0
.end
