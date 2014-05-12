	.file	"loop2.c"
	.section	.rodata.str1.1,"aMS",@progbits,1
.LC0:
	.string	"%ld %ld\n"
	.text
	.p2align 4,,15
.globl foo
	.type	foo, @function
foo:
.LFB11:
	.cfi_startproc
	pushq	%r13
	.cfi_def_cfa_offset 16
	.cfi_offset 13, -16
	movq	%rsi, %r13
	pushq	%r12
	.cfi_def_cfa_offset 24
	.cfi_offset 12, -24
	movq	%rdi, %r12
	pushq	%rbp
	.cfi_def_cfa_offset 32
	.cfi_offset 6, -32
	pushq	%rbx
	.cfi_def_cfa_offset 40
	.cfi_offset 3, -40
	subq	$8, %rsp
	.cfi_def_cfa_offset 48
	testq	%rdi, %rdi
	jle	.L4
	movl	$2, %ebp
	xorl	%ebx, %ebx
	.p2align 4,,10
	.p2align 3
.L3:
	movq	%rbp, %rdx
	movq	%rbx, %rsi
	xorl	%eax, %eax
	movl	$.LC0, %edi
	addq	$1, %rbx
	addq	%r13, %rbp
	call	printf
	cmpq	%rbx, %r12
	jg	.L3
.L4:
	addq	$8, %rsp
	.cfi_def_cfa_offset 40
	popq	%rbx
	.cfi_def_cfa_offset 32
	popq	%rbp
	.cfi_def_cfa_offset 24
	popq	%r12
	.cfi_def_cfa_offset 16
	popq	%r13
	.cfi_def_cfa_offset 8
	ret
	.cfi_endproc
.LFE11:
	.size	foo, .-foo
	.ident	"GCC: (GNU) 4.4.7 20120313 (Red Hat 4.4.7-3)"
	.section	.note.GNU-stack,"",@progbits
