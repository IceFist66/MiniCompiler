main:
	.cfi_startproc
	pushq %rbp
	movq %rsp , %rbp
	movq $5 , r0
	movq r0 , %eax
	jmp L1
L1:
	leave 
	ret 
	.cfi_endproc
