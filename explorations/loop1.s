	.file	"loop1.c"
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
	pushq	%r12
	.cfi_def_cfa_offset 16
	.cfi_offset 12, -16
	testq	%rdi, %rdi
	pushq	%rbp
	.cfi_def_cfa_offset 24
	.cfi_offset 6, -24
	movq	%rdi, %rbp
	pushq	%rbx
	.cfi_def_cfa_offset 32
	.cfi_offset 3, -32
	jle	.L4
	leaq	7(%rsi), %r12
	xorl	%ebx, %ebx
	.p2align 4,,10
	.p2align 3
.L3:
	movq	%rbx, %rsi
	xorl	%eax, %eax
	movq	%r12, %rdx
	movl	$.LC0, %edi
	addq	$1, %rbx
	call	printf
	cmpq	%rbx, %rbp
	jg	.L3
.L4:
	popq	%rbx
	.cfi_def_cfa_offset 24
	popq	%rbp
	.cfi_def_cfa_offset 16
	popq	%r12
	.cfi_def_cfa_offset 8
	ret
	.cfi_endproc
.LFE11:
	.size	foo, .-foo
	.ident	"GCC: (GNU) 4.4.7 20120313 (Red Hat 4.4.7-3)"
	.section	.note.GNU-stack,"",@progbits
