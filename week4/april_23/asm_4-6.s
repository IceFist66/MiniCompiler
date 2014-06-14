
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
	subq $344 , %rsp
	movq %rdi , -200(%rbp)
	movq $1 , -208(%rbp)
	movq -208(%rbp) , -216(%rbp)
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq -216(%rbp) , %rdi
	movq $2 , %rbx
	movq %rbx , %r10
	movq %r10 , %rsi
	movq $3 , %r11
	movq %r11 , %r12
	movq %r12 , %rdx
	movq $4 , -8(%rbp)
	movq -8(%rbp) , -16(%rbp)
	movq -16(%rbp) , %rcx
	movq $5 , -24(%rbp)
	movq -24(%rbp) , -32(%rbp)
	movq -32(%rbp) , %r8
	movq $6 , -40(%rbp)
	movq -40(%rbp) , -48(%rbp)
	movq -48(%rbp) , %r9
	movq $7 , -56(%rbp)
	movq -56(%rbp) , -64(%rbp)
	movq -64(%rbp) , 0(%rsp)
	movq $8 , -72(%rbp)
	movq -72(%rbp) , -80(%rbp)
	movq -80(%rbp) , 8(%rsp)
	movq $9 , -88(%rbp)
	movq -88(%rbp) , -96(%rbp)
	movq -96(%rbp) , 16(%rsp)
	movq $10 , -104(%rbp)
	movq -104(%rbp) , -112(%rbp)
	movq -112(%rbp) , 24(%rsp)
	movq $11 , -120(%rbp)
	movq -120(%rbp) , -128(%rbp)
	movq -128(%rbp) , 32(%rsp)
	movq $12 , -136(%rbp)
	movq -136(%rbp) , -144(%rbp)
	movq -144(%rbp) , 40(%rsp)
	movq $13 , -152(%rbp)
	movq -152(%rbp) , -160(%rbp)
	movq -160(%rbp) , 48(%rsp)
	movq $14 , -168(%rbp)
	movq -168(%rbp) , -176(%rbp)
	movq -176(%rbp) , 56(%rsp)
	movq $15 , -184(%rbp)
	movq -184(%rbp) , -192(%rbp)
	movq -192(%rbp) , 64(%rsp)
	call simple
	movq %rax , -224(%rbp)
	popq %r11
	popq %r10
	popq %r9
	popq %r8
	popq %rsi
	popq %rdi
	popq %rcx
	popq %rax
	movq -224(%rbp) , %rax
	jmp L1
L1:
	addq $48 , %rsp
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
	subq $576 , %rsp
	movq %rdi , -216(%rbp)
	movq %rsi , -224(%rbp)
	movq %rdx , -152(%rbp)
	movq %rcx , -176(%rbp)
	movq %r8 , -120(%rbp)
	movq %r9 , -144(%rbp)
	movq 16(%rbp) , -112(%rbp)
	movq 24(%rbp) , -408(%rbp)
	movq 32(%rbp) , -432(%rbp)
	movq 40(%rbp) , -360(%rbp)
	movq 48(%rbp) , -384(%rbp)
	movq 56(%rbp) , -312(%rbp)
	movq 64(%rbp) , -336(%rbp)
	movq 72(%rbp) , -264(%rbp)
	movq 80(%rbp) , -288(%rbp)
	movq -112(%rbp) , -96(%rbp)
	movq -96(%rbp) , -104(%rbp)
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq -104(%rbp) , %rdi
	movq -120(%rbp) , %rbx
	movq %rbx , %r10
	movq %r10 , %rsi
	movq -144(%rbp) , %r11
	movq %r11 , %r12
	movq %r12 , %rdx
	movq -152(%rbp) , -8(%rbp)
	movq -8(%rbp) , -16(%rbp)
	movq -16(%rbp) , %rcx
	movq -176(%rbp) , -24(%rbp)
	movq -24(%rbp) , -32(%rbp)
	movq -32(%rbp) , %r8
	movq -216(%rbp) , -40(%rbp)
	movq -40(%rbp) , -48(%rbp)
	movq -48(%rbp) , %r9
	movq -224(%rbp) , -56(%rbp)
	movq -56(%rbp) , -64(%rbp)
	movq -64(%rbp) , 0(%rsp)
	movq -264(%rbp) , -72(%rbp)
	movq -72(%rbp) , -80(%rbp)
	movq -80(%rbp) , 8(%rsp)
	call simple2
	movq %rax , -88(%rbp)
	popq %r11
	popq %r10
	popq %r9
	popq %r8
	popq %rsi
	popq %rdi
	popq %rcx
	popq %rax
	movq -112(%rbp) , -128(%rbp)
	movq -120(%rbp) , -136(%rbp)
	movq -128(%rbp) , -200(%rbp)
	subq -136(%rbp) , -200(%rbp)
	movq -144(%rbp) , -160(%rbp)
	movq -152(%rbp) , -168(%rbp)
	movq -160(%rbp) , -184(%rbp)
	imulq -168(%rbp) , -184(%rbp)
	movq -176(%rbp) , -192(%rbp)
	pushq %rdx
	pushq %rax
	movq -184(%rbp) , %rax
	cqto 
	idivq -192(%rbp)
	movq %rax , -208(%rbp)
	popq %rax
	popq %rdx
	movq -200(%rbp) , -248(%rbp)
	addq -208(%rbp) , -248(%rbp)
	movq -216(%rbp) , -232(%rbp)
	movq -224(%rbp) , -240(%rbp)
	movq -232(%rbp) , -256(%rbp)
	imulq -240(%rbp) , -256(%rbp)
	movq -248(%rbp) , -272(%rbp)
	addq -256(%rbp) , -272(%rbp)
	movq -264(%rbp) , -280(%rbp)
	movq -272(%rbp) , -296(%rbp)
	addq -280(%rbp) , -296(%rbp)
	movq -288(%rbp) , -304(%rbp)
	movq -296(%rbp) , -320(%rbp)
	addq -304(%rbp) , -320(%rbp)
	movq -312(%rbp) , -328(%rbp)
	movq -320(%rbp) , -344(%rbp)
	addq -328(%rbp) , -344(%rbp)
	movq -336(%rbp) , -352(%rbp)
	movq -344(%rbp) , -368(%rbp)
	addq -352(%rbp) , -368(%rbp)
	movq -360(%rbp) , -376(%rbp)
	movq -368(%rbp) , -392(%rbp)
	addq -376(%rbp) , -392(%rbp)
	movq -384(%rbp) , -400(%rbp)
	movq -392(%rbp) , -416(%rbp)
	addq -400(%rbp) , -416(%rbp)
	movq -408(%rbp) , -424(%rbp)
	movq -416(%rbp) , -440(%rbp)
	addq -424(%rbp) , -440(%rbp)
	movq -432(%rbp) , -448(%rbp)
	movq -440(%rbp) , -456(%rbp)
	addq -448(%rbp) , -456(%rbp)
	movq -456(%rbp) , %rax
	jmp L3
L3:
	addq $48 , %rsp
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
	movq %rdi , -168(%rbp)
	movq %rsi , -176(%rbp)
	movq %rdx , -40(%rbp)
	movq %rcx , -64(%rbp)
	movq %r8 , %rdx
	movq %r9 , -16(%rbp)
	movq 16(%rbp) , %rbx
	movq 24(%rbp) , -120(%rbp)
	movq %rbx , %r12
	movq %rdx , -8(%rbp)
	movq %r12 , -24(%rbp)
	addq -8(%rbp) , -24(%rbp)
	movq -16(%rbp) , -32(%rbp)
	movq -24(%rbp) , -48(%rbp)
	addq -32(%rbp) , -48(%rbp)
	movq -40(%rbp) , -56(%rbp)
	movq -48(%rbp) , -72(%rbp)
	addq -56(%rbp) , -72(%rbp)
	movq -64(%rbp) , -80(%rbp)
	movq -72(%rbp) , -88(%rbp)
	addq -80(%rbp) , -88(%rbp)
	movq -168(%rbp) , -96(%rbp)
	movq -88(%rbp) , -104(%rbp)
	addq -96(%rbp) , -104(%rbp)
	movq -176(%rbp) , -112(%rbp)
	movq -104(%rbp) , -128(%rbp)
	addq -112(%rbp) , -128(%rbp)
	movq -120(%rbp) , -136(%rbp)
	movq -128(%rbp) , -144(%rbp)
	addq -136(%rbp) , -144(%rbp)
	movq -144(%rbp) , -152(%rbp)
	movq -152(%rbp) , -160(%rbp)
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq $.LL2 , %rdi
	movq -160(%rbp) , %rsi
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
	addq $48 , %rsp
	movq %rbp , %rsp
	popq %rbp
	ret 
	.cfi_endproc
.LFE2:
	.size	simple2, .-simple2
