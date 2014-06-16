
	.comm	.scan , 8 , 8
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
	subq $368 , %rsp
	movq -200(%rbp) , %r15
	movq %rdi , %r15
	movq %r15 , -200(%rbp)
	movq -208(%rbp) , %r15
	movq $1 , %r15
	movq %r15 , -208(%rbp)
	movq -208(%rbp) , %r14
	movq -216(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -216(%rbp)
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq -216(%rbp) , %r14
	movq %r14 , %rdi
	movq $2 , %rbx
	movq %rbx , %r10
	movq %r10 , %rsi
	movq $3 , %r11
	movq %r11 , %r12
	movq %r12 , %rdx
	movq -8(%rbp) , %r15
	movq $4 , %r15
	movq %r15 , -8(%rbp)
	movq -8(%rbp) , %r14
	movq -16(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -16(%rbp)
	movq -16(%rbp) , %r14
	movq %r14 , %rcx
	movq -24(%rbp) , %r15
	movq $5 , %r15
	movq %r15 , -24(%rbp)
	movq -24(%rbp) , %r14
	movq -32(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -32(%rbp)
	movq -32(%rbp) , %r14
	movq %r14 , %r8
	movq -40(%rbp) , %r15
	movq $6 , %r15
	movq %r15 , -40(%rbp)
	movq -40(%rbp) , %r14
	movq -48(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -48(%rbp)
	movq -48(%rbp) , %r14
	movq %r14 , %r9
	movq -56(%rbp) , %r15
	movq $7 , %r15
	movq %r15 , -56(%rbp)
	movq -56(%rbp) , %r14
	movq -64(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -64(%rbp)
	movq -64(%rbp) , %r14
	movq %r14 , 0(%rsp)
	movq -72(%rbp) , %r15
	movq $8 , %r15
	movq %r15 , -72(%rbp)
	movq -72(%rbp) , %r14
	movq -80(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -80(%rbp)
	movq -80(%rbp) , %r14
	movq %r14 , 8(%rsp)
	movq -88(%rbp) , %r15
	movq $9 , %r15
	movq %r15 , -88(%rbp)
	movq -88(%rbp) , %r14
	movq -96(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -96(%rbp)
	movq -96(%rbp) , %r14
	movq %r14 , 16(%rsp)
	movq -104(%rbp) , %r15
	movq $10 , %r15
	movq %r15 , -104(%rbp)
	movq -104(%rbp) , %r14
	movq -112(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -112(%rbp)
	movq -112(%rbp) , %r14
	movq %r14 , 24(%rsp)
	movq -120(%rbp) , %r15
	movq $11 , %r15
	movq %r15 , -120(%rbp)
	movq -120(%rbp) , %r14
	movq -128(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -128(%rbp)
	movq -128(%rbp) , %r14
	movq %r14 , 32(%rsp)
	movq -136(%rbp) , %r15
	movq $12 , %r15
	movq %r15 , -136(%rbp)
	movq -136(%rbp) , %r14
	movq -144(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -144(%rbp)
	movq -144(%rbp) , %r14
	movq %r14 , 40(%rsp)
	movq -152(%rbp) , %r15
	movq $13 , %r15
	movq %r15 , -152(%rbp)
	movq -152(%rbp) , %r14
	movq -160(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -160(%rbp)
	movq -160(%rbp) , %r14
	movq %r14 , 48(%rsp)
	movq -168(%rbp) , %r15
	movq $14 , %r15
	movq %r15 , -168(%rbp)
	movq -168(%rbp) , %r14
	movq -176(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -176(%rbp)
	movq -176(%rbp) , %r14
	movq %r14 , 56(%rsp)
	movq -184(%rbp) , %r15
	movq $15 , %r15
	movq %r15 , -184(%rbp)
	movq -184(%rbp) , %r14
	movq -192(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -192(%rbp)
	movq -192(%rbp) , %r14
	movq %r14 , 64(%rsp)
	call simple
	movq -224(%rbp) , %r15
	movq %rax , %r15
	movq %r15 , -224(%rbp)
	popq %r11
	popq %r10
	popq %r9
	popq %r8
	popq %rsi
	popq %rdi
	popq %rcx
	popq %rax
	movq -224(%rbp) , %r14
	movq -240(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -240(%rbp)
	movq -240(%rbp) , %r14
	movq -232(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -232(%rbp)
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq $.LL0 , %rdi
	movq -232(%rbp) , %r14
	movq %r14 , %rsi
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
	movq -240(%rbp) , %r14
	movq -248(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -248(%rbp)
	movq -248(%rbp) , %r14
	movq %r14 , %rax
	jmp L1
L1:
	addq $368 , %rsp
	movq %rbp , %rsp
	popq %rbp
	ret 
	.cfi_endproc
.LFE0:
	.size	main, .-main
	.section .rodata
.LL1:
	.string "%ld\n"
.LC1:
	.string "%ld "
.LS1:
	.string "%ld"
	.text
.globl simple
	.type	simple, @function
simple:
	.cfi_startproc
	pushq %rbp
	movq %rsp , %rbp
	subq $720 , %rsp
	movq -256(%rbp) , %r15
	movq %rdi , %r15
	movq %r15 , -256(%rbp)
	movq -264(%rbp) , %r15
	movq %rsi , %r15
	movq %r15 , -264(%rbp)
	movq -288(%rbp) , %r15
	movq %rdx , %r15
	movq %r15 , -288(%rbp)
	movq -296(%rbp) , %r15
	movq %rcx , %r15
	movq %r15 , -296(%rbp)
	movq -320(%rbp) , %r15
	movq %r8 , %r15
	movq %r15 , -320(%rbp)
	movq -360(%rbp) , %r15
	movq %r9 , %r15
	movq %r15 , -360(%rbp)
	movq -368(%rbp) , %r15
	movq 16(%rbp) , %r15
	movq %r15 , -368(%rbp)
	movq -408(%rbp) , %r15
	movq 24(%rbp) , %r15
	movq %r15 , -408(%rbp)
	movq -432(%rbp) , %r15
	movq 32(%rbp) , %r15
	movq %r15 , -432(%rbp)
	movq -456(%rbp) , %r15
	movq 40(%rbp) , %r15
	movq %r15 , -456(%rbp)
	movq -480(%rbp) , %r15
	movq 48(%rbp) , %r15
	movq %r15 , -480(%rbp)
	movq -504(%rbp) , %r15
	movq 56(%rbp) , %r15
	movq %r15 , -504(%rbp)
	movq -552(%rbp) , %r15
	movq 64(%rbp) , %r15
	movq %r15 , -552(%rbp)
	movq -528(%rbp) , %r15
	movq 72(%rbp) , %r15
	movq %r15 , -528(%rbp)
	movq -576(%rbp) , %r15
	movq 80(%rbp) , %r15
	movq %r15 , -576(%rbp)
	movq -256(%rbp) , %r14
	movq %r14 , %rbx
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq $.LL1 , %rdi
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
	movq -264(%rbp) , %r14
	movq %r14 , %r12
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq $.LL1 , %rdi
	movq %r12 , %rsi
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
	movq -288(%rbp) , %r14
	movq -8(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -8(%rbp)
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq $.LL1 , %rdi
	movq -8(%rbp) , %r14
	movq %r14 , %rsi
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
	movq -576(%rbp) , %r14
	movq -16(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -16(%rbp)
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq $.LL1 , %rdi
	movq -16(%rbp) , %r14
	movq %r14 , %rsi
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
	movq -320(%rbp) , %r14
	movq -24(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -24(%rbp)
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq $.LL1 , %rdi
	movq -24(%rbp) , %r14
	movq %r14 , %rsi
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
	movq -360(%rbp) , %r14
	movq -32(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -32(%rbp)
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq $.LL1 , %rdi
	movq -32(%rbp) , %r14
	movq %r14 , %rsi
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
	movq -368(%rbp) , %r14
	movq -40(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -40(%rbp)
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq $.LL1 , %rdi
	movq -40(%rbp) , %r14
	movq %r14 , %rsi
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
	movq -408(%rbp) , %r14
	movq -48(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -48(%rbp)
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq $.LL1 , %rdi
	movq -48(%rbp) , %r14
	movq %r14 , %rsi
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
	movq -256(%rbp) , %r14
	movq -56(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -56(%rbp)
	movq -56(%rbp) , %r14
	movq -64(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -64(%rbp)
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq -64(%rbp) , %r14
	movq %r14 , %rdi
	movq -264(%rbp) , %r14
	movq -72(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -72(%rbp)
	movq -72(%rbp) , %r14
	movq -80(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -80(%rbp)
	movq -80(%rbp) , %r14
	movq %r14 , %rsi
	movq -288(%rbp) , %r14
	movq -88(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -88(%rbp)
	movq -88(%rbp) , %r14
	movq -96(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -96(%rbp)
	movq -96(%rbp) , %r14
	movq %r14 , %rdx
	movq -296(%rbp) , %r14
	movq -104(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -104(%rbp)
	movq -104(%rbp) , %r14
	movq -112(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -112(%rbp)
	movq -112(%rbp) , %r14
	movq %r14 , %rcx
	movq -320(%rbp) , %r14
	movq -120(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -120(%rbp)
	movq -120(%rbp) , %r14
	movq -128(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -128(%rbp)
	movq -128(%rbp) , %r14
	movq %r14 , %r8
	movq -360(%rbp) , %r14
	movq -136(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -136(%rbp)
	movq -136(%rbp) , %r14
	movq -144(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -144(%rbp)
	movq -144(%rbp) , %r14
	movq %r14 , %r9
	movq -368(%rbp) , %r14
	movq -152(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -152(%rbp)
	movq -152(%rbp) , %r14
	movq -160(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -160(%rbp)
	movq -160(%rbp) , %r14
	movq %r14 , 0(%rsp)
	movq -408(%rbp) , %r14
	movq -168(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -168(%rbp)
	movq -168(%rbp) , %r14
	movq -176(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -176(%rbp)
	movq -176(%rbp) , %r14
	movq %r14 , 8(%rsp)
	call simple2
	movq -184(%rbp) , %r15
	movq %rax , %r15
	movq %r15 , -184(%rbp)
	popq %r11
	popq %r10
	popq %r9
	popq %r8
	popq %rsi
	popq %rdi
	popq %rcx
	popq %rax
	movq -256(%rbp) , %r14
	movq -192(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -192(%rbp)
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq $.LL1 , %rdi
	movq -192(%rbp) , %r14
	movq %r14 , %rsi
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
	movq -264(%rbp) , %r14
	movq -200(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -200(%rbp)
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq $.LL1 , %rdi
	movq -200(%rbp) , %r14
	movq %r14 , %rsi
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
	movq -288(%rbp) , %r14
	movq -208(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -208(%rbp)
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq $.LL1 , %rdi
	movq -208(%rbp) , %r14
	movq %r14 , %rsi
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
	movq -296(%rbp) , %r14
	movq -216(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -216(%rbp)
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq $.LL1 , %rdi
	movq -216(%rbp) , %r14
	movq %r14 , %rsi
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
	movq -320(%rbp) , %r14
	movq -224(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -224(%rbp)
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq $.LL1 , %rdi
	movq -224(%rbp) , %r14
	movq %r14 , %rsi
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
	movq -360(%rbp) , %r14
	movq -232(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -232(%rbp)
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq $.LL1 , %rdi
	movq -232(%rbp) , %r14
	movq %r14 , %rsi
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
	movq -368(%rbp) , %r14
	movq -240(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -240(%rbp)
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq $.LL1 , %rdi
	movq -240(%rbp) , %r14
	movq %r14 , %rsi
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
	movq -408(%rbp) , %r14
	movq -248(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -248(%rbp)
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq $.LL1 , %rdi
	movq -248(%rbp) , %r14
	movq %r14 , %rsi
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
	movq -256(%rbp) , %r14
	movq -272(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -272(%rbp)
	movq -264(%rbp) , %r14
	movq -280(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -280(%rbp)
	movq -272(%rbp) , %r14
	movq -344(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -344(%rbp)
	movq -280(%rbp) , %r14
	movq -344(%rbp) , %r15
	subq %r14 , %r15
	movq %r15 , -344(%rbp)
	movq -288(%rbp) , %r14
	movq -304(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -304(%rbp)
	movq -296(%rbp) , %r14
	movq -312(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -312(%rbp)
	movq -304(%rbp) , %r14
	movq -328(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -328(%rbp)
	movq -312(%rbp) , %r14
	movq -328(%rbp) , %r15
	imulq %r14 , %r15
	movq %r15 , -328(%rbp)
	movq -320(%rbp) , %r14
	movq -336(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -336(%rbp)
	pushq %rdx
	pushq %rax
	xorq %rdx , %rdx
	xorq %rax , %rax
	movq -328(%rbp) , %r14
	movq %r14 , %rax
	cqto 
	movq -336(%rbp) , %r14
	idivq %r14
	movq -352(%rbp) , %r15
	movq %rax , %r15
	movq %r15 , -352(%rbp)
	popq %rax
	popq %rdx
	movq -344(%rbp) , %r14
	movq -392(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -392(%rbp)
	movq -352(%rbp) , %r14
	movq -392(%rbp) , %r15
	addq %r14 , %r15
	movq %r15 , -392(%rbp)
	movq -360(%rbp) , %r14
	movq -376(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -376(%rbp)
	movq -368(%rbp) , %r14
	movq -384(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -384(%rbp)
	movq -376(%rbp) , %r14
	movq -400(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -400(%rbp)
	movq -384(%rbp) , %r14
	movq -400(%rbp) , %r15
	imulq %r14 , %r15
	movq %r15 , -400(%rbp)
	movq -392(%rbp) , %r14
	movq -416(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -416(%rbp)
	movq -400(%rbp) , %r14
	movq -416(%rbp) , %r15
	addq %r14 , %r15
	movq %r15 , -416(%rbp)
	movq -408(%rbp) , %r14
	movq -424(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -424(%rbp)
	movq -416(%rbp) , %r14
	movq -440(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -440(%rbp)
	movq -424(%rbp) , %r14
	movq -440(%rbp) , %r15
	addq %r14 , %r15
	movq %r15 , -440(%rbp)
	movq -432(%rbp) , %r14
	movq -448(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -448(%rbp)
	movq -440(%rbp) , %r14
	movq -464(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -464(%rbp)
	movq -448(%rbp) , %r14
	movq -464(%rbp) , %r15
	addq %r14 , %r15
	movq %r15 , -464(%rbp)
	movq -456(%rbp) , %r14
	movq -472(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -472(%rbp)
	movq -464(%rbp) , %r14
	movq -488(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -488(%rbp)
	movq -472(%rbp) , %r14
	movq -488(%rbp) , %r15
	addq %r14 , %r15
	movq %r15 , -488(%rbp)
	movq -480(%rbp) , %r14
	movq -496(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -496(%rbp)
	movq -488(%rbp) , %r14
	movq -512(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -512(%rbp)
	movq -496(%rbp) , %r14
	movq -512(%rbp) , %r15
	addq %r14 , %r15
	movq %r15 , -512(%rbp)
	movq -504(%rbp) , %r14
	movq -520(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -520(%rbp)
	movq -512(%rbp) , %r14
	movq -536(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -536(%rbp)
	movq -520(%rbp) , %r14
	movq -536(%rbp) , %r15
	addq %r14 , %r15
	movq %r15 , -536(%rbp)
	movq -528(%rbp) , %r14
	movq -544(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -544(%rbp)
	movq -536(%rbp) , %r14
	movq -560(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -560(%rbp)
	movq -544(%rbp) , %r14
	movq -560(%rbp) , %r15
	addq %r14 , %r15
	movq %r15 , -560(%rbp)
	movq -552(%rbp) , %r14
	movq -568(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -568(%rbp)
	movq -560(%rbp) , %r14
	movq -584(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -584(%rbp)
	movq -568(%rbp) , %r14
	movq -584(%rbp) , %r15
	addq %r14 , %r15
	movq %r15 , -584(%rbp)
	movq -576(%rbp) , %r14
	movq -592(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -592(%rbp)
	movq -584(%rbp) , %r14
	movq -600(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -600(%rbp)
	movq -592(%rbp) , %r14
	movq -600(%rbp) , %r15
	addq %r14 , %r15
	movq %r15 , -600(%rbp)
	movq -600(%rbp) , %r14
	movq %r14 , %rax
	jmp L3
L3:
	addq $720 , %rsp
	movq %rbp , %rsp
	popq %rbp
	ret 
	.cfi_endproc
.LFE1:
	.size	simple, .-simple
	.section .rodata
.LL2:
	.string "%ld\n"
.LC2:
	.string "%ld "
.LS2:
	.string "%ld"
	.text
.globl simple2
	.type	simple2, @function
simple2:
	.cfi_startproc
	pushq %rbp
	movq %rsp , %rbp
	subq $296 , %rsp
	movq -168(%rbp) , %r15
	movq %rdi , %r15
	movq %r15 , -168(%rbp)
	movq -176(%rbp) , %r15
	movq %rsi , %r15
	movq %r15 , -176(%rbp)
	movq %rdx , %r12
	movq -24(%rbp) , %r15
	movq %rcx , %r15
	movq %r15 , -24(%rbp)
	movq -48(%rbp) , %r15
	movq %r8 , %r15
	movq %r15 , -48(%rbp)
	movq -72(%rbp) , %r15
	movq %r9 , %r15
	movq %r15 , -72(%rbp)
	movq -96(%rbp) , %r15
	movq 16(%rbp) , %r15
	movq %r15 , -96(%rbp)
	movq -120(%rbp) , %r15
	movq 24(%rbp) , %r15
	movq %r15 , -120(%rbp)
	movq -168(%rbp) , %r14
	movq %r14 , %rbx
	movq -176(%rbp) , %r14
	movq %r14 , %rdx
	movq -8(%rbp) , %r15
	movq %rbx , %r15
	movq %r15 , -8(%rbp)
	movq -8(%rbp) , %r15
	addq %rdx , %r15
	movq %r15 , -8(%rbp)
	movq -16(%rbp) , %r15
	movq %r12 , %r15
	movq %r15 , -16(%rbp)
	movq -8(%rbp) , %r14
	movq -32(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -32(%rbp)
	movq -16(%rbp) , %r14
	movq -32(%rbp) , %r15
	addq %r14 , %r15
	movq %r15 , -32(%rbp)
	movq -24(%rbp) , %r14
	movq -40(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -40(%rbp)
	movq -32(%rbp) , %r14
	movq -56(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -56(%rbp)
	movq -40(%rbp) , %r14
	movq -56(%rbp) , %r15
	addq %r14 , %r15
	movq %r15 , -56(%rbp)
	movq -48(%rbp) , %r14
	movq -64(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -64(%rbp)
	movq -56(%rbp) , %r14
	movq -80(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -80(%rbp)
	movq -64(%rbp) , %r14
	movq -80(%rbp) , %r15
	addq %r14 , %r15
	movq %r15 , -80(%rbp)
	movq -72(%rbp) , %r14
	movq -88(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -88(%rbp)
	movq -80(%rbp) , %r14
	movq -104(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -104(%rbp)
	movq -88(%rbp) , %r14
	movq -104(%rbp) , %r15
	addq %r14 , %r15
	movq %r15 , -104(%rbp)
	movq -96(%rbp) , %r14
	movq -112(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -112(%rbp)
	movq -104(%rbp) , %r14
	movq -128(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -128(%rbp)
	movq -112(%rbp) , %r14
	movq -128(%rbp) , %r15
	addq %r14 , %r15
	movq %r15 , -128(%rbp)
	movq -120(%rbp) , %r14
	movq -136(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -136(%rbp)
	movq -128(%rbp) , %r14
	movq -144(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -144(%rbp)
	movq -136(%rbp) , %r14
	movq -144(%rbp) , %r15
	addq %r14 , %r15
	movq %r15 , -144(%rbp)
	movq -144(%rbp) , %r14
	movq -152(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -152(%rbp)
	movq -152(%rbp) , %r14
	movq -160(%rbp) , %r15
	movq %r14 , %r15
	movq %r15 , -160(%rbp)
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq $.LL2 , %rdi
	movq -160(%rbp) , %r14
	movq %r14 , %rsi
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
L5:
	addq $296 , %rsp
	movq %rbp , %rsp
	popq %rbp
	ret 
	.cfi_endproc
.LFE2:
	.size	simple2, .-simple2
