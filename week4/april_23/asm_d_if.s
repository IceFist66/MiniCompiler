main:
	.cfi_startproc
	pushq %rbp
	movq %rsp , %rbp
	subq $48 , %rsp
	movq $3 , r1
	movq r1 , r0
	movq r0 , r2
	movq $2 , r3
	movq $0 , r4
	cmpq r2 , r3
	pushq %r15
	movq $1 , %r15
	cmovleq %r15 , r4
	popq %r15
	pushq %r15
	movq $1 , %r15
	cmpq %r15 , r4
	popq %r15
	je L2
	jmp L3
gen{%rbp, %rsp, %r15, }
kill{%rbp, %rsp, r1, r0, r2, r3, r4, %r15, }
LiveOut{%rax, %rcx, %rdi, %rsi, %r8, %r9, %r10, %r11, r0, %r15, %rsp, %rbp, }

L2:
	movq $2 , r5
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq $.LL0 , %rdi
	movq r5 , %rsi
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
gen{%rax, %rcx, %rdi, %rsi, %r8, %r9, %r10, %r11, }
kill{r5, %rdi, %rsi, %rax, %r11, %r10, %r9, %r8, %rcx, }
LiveOut{r0, %r15, %rax, %rcx, %rdi, %rsi, %r8, %r9, %r10, %r11, %rsp, %rbp, }

L4:
	movq r0 , r7
	movq $3 , r8
	movq $0 , r9
	cmpq r7 , r8
	pushq %r15
	movq $1 , %r15
	cmovleq %r15 , r9
	popq %r15
	pushq %r15
	movq $1 , %r15
	cmpq %r15 , r9
	popq %r15
	je L5
	jmp L6
gen{r0, %r15, }
kill{r7, r8, r9, %r15, }
LiveOut{%rax, %rcx, %rdi, %rsi, %r8, %r9, %r10, %r11, r0, %r15, %rsp, %rbp, }

L5:
	movq $3 , r10
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq $.LL0 , %rdi
	movq r10 , %rsi
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
gen{%rax, %rcx, %rdi, %rsi, %r8, %r9, %r10, %r11, }
kill{r10, %rdi, %rsi, %rax, %r11, %r10, %r9, %r8, %rcx, }
LiveOut{r0, %r15, %rax, %rcx, %rdi, %rsi, %r8, %r9, %r10, %r11, %rsp, %rbp, }

L7:
	movq r0 , r12
	movq $4 , r13
	movq $0 , r14
	cmpq r12 , r13
	pushq %r15
	movq $1 , %r15
	cmovleq %r15 , r14
	popq %r15
	pushq %r15
	movq $1 , %r15
	cmpq %r15 , r14
	popq %r15
	je L8
	jmp L9
gen{r0, %r15, }
kill{r12, r13, r14, %r15, }
LiveOut{%rax, %rcx, %rdi, %rsi, %r8, %r9, %r10, %r11, %rsp, %rbp, }

L8:
	movq $4 , r15
	pushq %rax
	pushq %rcx
	pushq %rdi
	pushq %rsi
	pushq %r8
	pushq %r9
	pushq %r10
	pushq %r11
	movq $.LL0 , %rdi
	movq r15 , %rsi
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
gen{%rax, %rcx, %rdi, %rsi, %r8, %r9, %r10, %r11, }
kill{r15, %rdi, %rsi, %rax, %r11, %r10, %r9, %r8, %rcx, }
LiveOut{%rsp, %rbp, }

L10:
gen{}
kill{}
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

L9:
	jmp L10
gen{}
kill{}
LiveOut{%rsp, %rbp, }

L6:
	jmp L7
gen{}
kill{}
LiveOut{r0, %r15, %rax, %rcx, %rdi, %rsi, %r8, %r9, %r10, %r11, %rsp, %rbp, }

L3:
	jmp L4
gen{}
kill{}
LiveOut{r0, %r15, %rax, %rcx, %rdi, %rsi, %r8, %r9, %r10, %r11, %rsp, %rbp, }
