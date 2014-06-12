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
	subq $56 , %rsp
	movq $3 , %rax
	movq %rax , %rbx
	movq %rbx , %rcx
	movq $2 , %rax
	movq $0 , %rbx
	cmpq %rcx , %rax
	pushq %r15
	movq $1 , %r15
	cmovleq %r15 , %rbx
	popq %r15
	pushq %r15
	movq $1 , %r15
	cmpq %r15 , %rbx
	popq %r15
	je L2
	jmp L3
L2:
	movq $2 , %rdx
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq $.LL0 , %rdi
	movq %rdx , %rsi
	movq $0 , %rax
	call printf
	popq %r11
	popq %r10
	popq %r9
	popq %r8
	popq %rsi
	popq %rdi
	popq %rcx
	popq %rax
	jmp L4
L4:
	movq %rbx , %r12
	movq $3 , %rdx
	movq $0 , uncolorable
	cmpq %r12 , %rdx
	pushq %r15
	movq $1 , %r15
	cmovleq %r15 , uncolorable
	popq %r15
	pushq %r15
	movq $1 , %r15
	cmpq %r15 , uncolorable
	popq %r15
	je L5
	jmp L6
L5:
	movq $3 , %rdx
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq $.LL0 , %rdi
	movq %rdx , %rsi
	movq $0 , %rax
	call printf
	popq %r11
	popq %r10
	popq %r9
	popq %r8
	popq %rsi
	popq %rdi
	popq %rcx
	popq %rax
	jmp L7
L7:
	movq %rbx , %rdx
	movq $4 , %rbx
	movq $0 , %r12
	cmpq %rdx , %rbx
	pushq %r15
	movq $1 , %r15
	cmovleq %r15 , %r12
	popq %r15
	pushq %r15
	movq $1 , %r15
	cmpq %r15 , %r12
	popq %r15
	je L8
	jmp L9
L8:
	movq $4 , %rbx
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq $.LL0 , %rdi
	movq %rbx , %rsi
	movq $0 , %rax
	call printf
	popq %r11
	popq %r10
	popq %r9
	popq %r8
	popq %rsi
	popq %rdi
	popq %rcx
	popq %rax
	jmp L10
L10:
L1:
	addq $48 , %rsp
	movq %rbp , %rsp
	popq %rbp
	ret 
	.cfi_endproc
L9:
	jmp L10
L6:
	jmp L7
L3:
	jmp L4
.LFE0:
	.size	main, .-main
