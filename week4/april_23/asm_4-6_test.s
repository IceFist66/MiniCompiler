	.section .rodata
.LS1:
	.string "%ld"
.comm .scan , 8 , 8
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
	movq %rcx , 0(%rsp)
	movq $8 , %rbx
	movq %rbx , %rcx
	movq %rcx , 8(%rsp)
	movq $9 , %rbx
	movq %rbx , %rcx
	movq %rcx , 16(%rsp)
	movq $10 , %rbx
	movq %rbx , %rcx
	movq %rcx , 24(%rsp)
	movq $11 , %rbx
	movq %rbx , %rcx
	movq %rcx , 32(%rsp)
	movq $12 , %rbx
	movq %rbx , %rcx
	movq %rcx , 40(%rsp)
	movq $13 , %rbx
	movq %rbx , %rcx
	movq %rcx , 48(%rsp)
	movq $14 , %rbx
	movq %rbx , %rcx
	movq %rcx , 56(%rsp)
	movq $15 , %rbx
	movq %rbx , %rcx
	movq %rcx , 64(%rsp)
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
L1:
	leave 
	ret 
	.cfi_endproc
.LFE0:
	.size	main, .-main
	.section .rodata
.LS1:
	.string "%ld"
.comm .scan , 8 , 8
	.text
.globl simple
	.type	simple, @function
simple:
	.cfi_startproc
	pushq %rbp
	movq %rsp , %rbp
	subq $144 , %rsp
	pushq %rsp
	pushq %rbp
	pushq %rbx
	pushq uncolorable
	pushq uncolorable
	pushq %r15
	pushq uncolorable
	pushq %r13
	pushq %r14
	pushq %r12
	movq %rdi , %r10
	movq %rsi , %r11
	movq %rdx , %rsi
	movq %rcx , %rdi
	movq %r8 , %rsp
	movq %r9 , %rbp
	movq 16(%rbp) , %rbx
	movq 24(%rbp) , uncolorable
	movq 32(%rbp) , uncolorable
	movq 40(%rbp) , %r15
	movq 48(%rbp) , uncolorable
	movq 56(%rbp) , %r13
	movq 64(%rbp) , %r14
	movq 72(%rbp) , %r9
	movq 80(%rbp) , %r12
	movq %rbx , %rcx
	movq %rsp , %rbx
	movq %rcx , %rsp
	subq %rbx , %rsp
	movq %rbp , %rcx
	movq %rsi , %rbx
	imulq %rcx , %rbx , %rdx
	movq %rdi , %rbx
	pushq %rdx
	pushq %rax
	movq %rdx , %rax
	cqto 
	idivq %rbx
	movq %rax , %rbx
	popq %rax
	popq %rdx
	movq %rsp , %rax
	addq %rbx , %rax
	movq %r10 , %rbx
	movq %r11 , %rcx
	imulq %rbx , %rcx , %r8
	movq %rax , %rbx
	addq %r8 , %rbx
	movq %r9 , %rax
	movq %rbx , %rcx
	addq %rax , %rcx
	movq %r12 , %rax
	movq %rcx , %rbx
	addq %rax , %rbx
	movq %r13 , %rax
	movq %rbx , %rcx
	addq %rax , %rcx
	movq %r14 , %rax
	movq %rcx , %rbx
	addq %rax , %rbx
	movq %r15 , %rax
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
L3:
	popq %r12
	popq %r14
	popq %r13
	popq uncolorable
	popq %r15
	popq uncolorable
	popq uncolorable
	popq %rbx
	popq %rbp
	popq %rsp
	leave 
	ret 
	.cfi_endproc
.LFE1:
	.size	simple, .-simple
