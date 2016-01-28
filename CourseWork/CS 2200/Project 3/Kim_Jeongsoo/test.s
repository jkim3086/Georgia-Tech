main:			la $sp, stack	! Initialize stack pointer
        		lw $sp, 0($sp)

				la $at, factorial       ! load address of factorial label into $at
        		addi $a0, $zero,8       ! $a0 = 8, the number to factorialize
        		jalr $at, $ra           ! jump to factorial, set $ra to return addr
Halt:			beq $zero, $zero, Halt                    ! when we return, just halt

factorial:      addi    $sp, $sp, -1    ! push RA
                sw      $ra, 0($sp)		! Store numbers for each stack period
                addi    $sp, $sp, -1    ! push a0
                sw      $a0, 0($sp)
                addi    $sp, $sp, -1    ! push s0
                sw      $s0, 0($sp)
                addi    $sp, $sp, -1    ! push s1
                sw      $s1, 0($sp)

                beq     $a0, $zero, base_zero ! Base_zero checking
                addi    $s1, $zero, 1
                beq     $a0, $s1, base_one	  ! Base_one Checking
                beq     $zero, $zero, recurse
                
    base_zero:  addi    $v0, $zero, 1   ! 0! = 1
                beq     $zero, $zero, done

    base_one:   addi    $v0, $zero, 1   ! 1! = 1
                beq     $zero, $zero, done

    recurse:    add     $s1, $a0, $zero     ! save n in s1
                addi    $a0, $a0, -1        ! n! = n * (n-1)!
                la      $at, factorial      ! Breaking down the initial number
                jalr    $at, $ra

                add     $s0, $v0, $zero     ! use s0 to store (n-1)!
                add     $v0, $zero, $zero   ! use v0 as sum register
        mul:    beq     $s1, $zero, done    ! use s1 as counter (from n to 0)
                add     $v0, $v0, $s0
                addi    $s1, $s1, -1
                beq     $zero, $zero, mul

    done:       lw      $s1, 0($sp)     ! pop s1
                addi    $sp, $sp, 1		! Poping numbers from each stack period
                lw      $s0, 0($sp)     ! pop s0
                addi    $sp, $sp, 1
                lw      $a0, 0($sp)     ! pop a0
                addi    $sp, $sp, 1
                lw      $ra, 0($sp)     ! pop RA
                addi    $sp, $sp, 1
                jalr    $ra, $zero




stack: .fill 0xFFFFF0
