	.file	"4-6.c"
	.text
.globl main
	.type	main, @function
main:
.LFB0:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	subq	$96, %rsp
	movl	%edi, -4(%rbp)
	movl	$15, 64(%rsp)
	movl	$14, 56(%rsp)
	movl	$13, 48(%rsp)
	movl	$12, 40(%rsp)
	movl	$11, 32(%rsp)
	movl	$10, 24(%rsp)
	movl	$9, 16(%rsp)
	movl	$8, 8(%rsp)
	movl	$7, (%rsp)
	movl	$6, %r9d
	movl	$5, %r8d
	movl	$4, %ecx
	movl	$3, %edx
	movl	$2, %esi
	movl	$1, %edi
	movl	$0, %eax
	call	simple
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE0:
	.size	main, .-main
.globl simple
	.type	simple, @function
simple:
.LFB1:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	movl	%edi, -4(%rbp)
	movl	%esi, -8(%rbp)
	movl	%edx, -12(%rbp)
	movl	%ecx, -16(%rbp)
	movl	%r8d, -20(%rbp)
	movl	%r9d, -24(%rbp)
	movl	-8(%rbp), %eax
	movl	-4(%rbp), %edx
	movl	%edx, %ecx
	subl	%eax, %ecx
	movl	-12(%rbp), %eax
	imull	-16(%rbp), %eax
	movl	%eax, %edx
	sarl	$31, %edx
	idivl	-20(%rbp)
	leal	(%rcx,%rax), %edx
	movl	-24(%rbp), %eax
	imull	16(%rbp), %eax
	leal	(%rdx,%rax), %eax
	addl	24(%rbp), %eax
	addl	32(%rbp), %eax
	addl	40(%rbp), %eax
	addl	48(%rbp), %eax
	addl	56(%rbp), %eax
	addl	72(%rbp), %eax
	addl	64(%rbp), %eax
	addl	80(%rbp), %eax
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE1:
	.size	simple, .-simple
	.ident	"GCC: (GNU) 4.4.7 20120313 (Red Hat 4.4.7-3)"
	.section	.note.GNU-stack,"",@progbits
