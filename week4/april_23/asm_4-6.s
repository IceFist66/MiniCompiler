	.section .rodata
.LL0:
	.string "%ld\n"
.LC0:
	.string "%ld "
.LS0:
	.string "%ld"
	.text
.globl main
	.type	main, @function
main:
	.cfi_startproc
	pushq %rbp
	movq %rsp , %rbp
	subq $120 , %rsp
	movq %rdi , %rbx
	movq $1 , %rbx
	movq %rbx , %rdx
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq %rdx , %rdi
	movq $2 , %rbx
	movq %rbx , %rcx
	movq %rcx , %rsi
	movq $3 , %rbx
	movq %rbx , %rcx
	movq %rcx , %rdx
	movq $4 , %rbx
	movq %rbx , %rdx
	movq %rdx , %rcx
	movq $5 , %rbx
	movq %rbx , %rcx
	movq %rcx , %r8
	movq $6 , %rbx
	movq %rbx , %rcx
	movq %rcx , %r9
	movq $7 , %rbx
	movq %rbx , %rcx
	movq %rcx , 16(%rsp)
	movq $8 , %rbx
	movq %rbx , %rcx
	movq %rcx , 24(%rsp)
	movq $9 , %rbx
	movq %rbx , %rcx
	movq %rcx , 32(%rsp)
	movq $10 , %rbx
	movq %rbx , %rcx
	movq %rcx , 40(%rsp)
	movq $11 , %rbx
	movq %rbx , %rcx
	movq %rcx , 48(%rsp)
	movq $12 , %rbx
	movq %rbx , %rcx
	movq %rcx , 56(%rsp)
	movq $13 , %rbx
	movq %rbx , %rcx
	movq %rcx , 64(%rsp)
	movq $14 , %rbx
	movq %rbx , %rcx
	movq %rcx , 72(%rsp)
	movq $15 , %rbx
	movq %rbx , %rcx
	movq %rcx , 80(%rsp)
	call simple
	movq %rax , %rbx
	popq %r11
	popq %r10
	popq %r9
	popq %r8
	popq %rsi
	popq %rdi
	popq %rcx
	popq %rax
	movq %rbx , %rax
	jmp L1
gen{%rbp, %rsp, %rdi, %rax, %rcx, %rsi, %r8, %r9, %r10, %r11, }
kill{%rbp, %rsp, r0, r1, r2, %rdi, r3, r4, %rsi, r5, r6, %rdx, r7, r8, %rcx, r9, r10, %r8, r11, r12, %r9, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31, %r11, %r10, %rax, }
LiveOut{%rsp, %rbp, }
L1:
	addq $48 , %rsp
	movq %rbp , %rsp
	popq %rbp
	ret 
	.cfi_endproc
gen{%rsp, %rbp, }
kill{%rsp, %rbp, }
LiveOut{}
.LFE0:
	.size	main, .-main
	.section .rodata
.LL0:
	.string "%ld\n"
.LC0:
	.string "%ld "
.LS0:
	.string "%ld"
	.text
.globl simple
	.type	simple, @function
simple:
	.cfi_startproc
	pushq %rbp
	movq %rsp , %rbp
	subq $168 , %rsp
	movq %rdi , %r11
	movq %rsi , %r12
	movq %rdx , %rdi
	movq %rcx , %r10
	movq %r8 , %rsp
	movq %r9 , %rbp
	movq 16(%rbp) , %rbx
	movq 24(%rbp) , uncolorable
	movq 32(%rbp) , uncolorable
	movq 40(%rbp) , uncolorable
	movq 48(%rbp) , uncolorable
	movq 56(%rbp) , uncolorable
	movq 64(%rbp) , uncolorable
	movq 72(%rbp) , %r8
	movq 80(%rbp) , %r9
	movq %rbx , %rcx
	movq %rsp , %rbx
	movq %rcx , %rsi
	subq %rbx , %rsi
	movq %rbp , %rsp
	movq %rdi , %rbx
	movq %rsp , %rcx
	imulq %rbx , %rcx
	movq %r10 , %rbx
	pushq %rdx
	pushq %rax
	movq %rcx , %rax
	cqto 
	idivq %rbx
	movq %rax , %rbx
	popq %rax
	popq %rdx
	movq %rsi , %rax
	addq %rbx , %rax
	movq %r11 , %rbx
	movq %r12 , %rcx
	movq %rbx , %rdx
	imulq %rcx , %rdx
	movq %rax , %rbx
	addq %rdx , %rbx
	movq %r8 , %rax
	movq %rbx , %rcx
	addq %rax , %rcx
	movq %r9 , %rax
	movq %rcx , %rbx
	addq %rax , %rbx
	movq uncolorable , %rax
	movq %rbx , %rcx
	addq %rax , %rcx
	movq uncolorable , %rax
	movq %rcx , %rbx
	addq %rax , %rbx
	movq uncolorable , %rax
	movq %rbx , %rcx
	addq %rax , %rcx
	movq uncolorable , %rax
	movq %rcx , %rbx
	addq %rax , %rbx
	movq uncolorable , %rax
	movq %rbx , %rcx
	addq %rax , %rcx
	movq uncolorable , %rax
	movq %rcx , %rbx
	addq %rax , %rbx
	movq %rbx , %rax
	jmp L3
gen{%rbp, %rsp, %rdi, %rsi, %rdx, %rcx, %r8, %r9, %rax, }
kill{%rbp, %rsp, r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, %rax, %rdx, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31, r32, r33, r34, r35, r36, r37, r38, r39, r40, r41, r42, r43, }
LiveOut{%rsp, %rbp, }
L3:
	addq $48 , %rsp
	movq %rbp , %rsp
	popq %rbp
	ret 
	.cfi_endproc
gen{%rsp, %rbp, }
kill{%rsp, %rbp, }
LiveOut{}
.LFE1:
	.size	simple, .-simple
