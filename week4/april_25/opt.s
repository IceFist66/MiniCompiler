	.file	"opt.c"
	.text
	.p2align 4,,15
.globl square
	.type	square, @function
square:
.LFB18:
	.cfi_startproc
	movq	%rdi, %rax
	imulq	%rdi, %rax
	ret
	.cfi_endproc
.LFE18:
	.size	square, .-square
	.section	.rodata.str1.1,"aMS",@progbits,1
.LC0:
	.string	"%ld %ld %ld %ld %ld %ld %ld\n"
	.text
	.p2align 4,,15
.globl print_them
	.type	print_them, @function
print_them:
.LFB19:
	.cfi_startproc
	subq	$24, %rsp
	.cfi_def_cfa_offset 32
	movq	32(%rsp), %rax
	movq	%r9, (%rsp)
	movq	%r8, %r9
	movq	%rcx, %r8
	movq	%rdx, %rcx
	movq	%rsi, %rdx
	movq	%rdi, %rsi
	movl	$.LC0, %edi
	movq	%rax, 8(%rsp)
	xorl	%eax, %eax
	call	printf
	addq	$24, %rsp
	.cfi_def_cfa_offset 8
	ret
	.cfi_endproc
.LFE19:
	.size	print_them, .-print_them
	.p2align 4,,15
.globl main
	.type	main, @function
main:
.LFB20:
	.cfi_startproc
	subq	$24, %rsp
	.cfi_def_cfa_offset 32
	movl	$5, %r9d
	movl	$4, %r8d
	movl	$3, %ecx
	movl	$2, %edx
	movl	$1, %esi
	movl	$.LC0, %edi
	xorl	%eax, %eax
	movq	$100, 8(%rsp)
	movq	$6, (%rsp)
	call	printf
	xorl	%eax, %eax
	addq	$24, %rsp
	.cfi_def_cfa_offset 8
	ret
	.cfi_endproc
.LFE20:
	.size	main, .-main
	.ident	"GCC: (GNU) 4.4.7 20120313 (Red Hat 4.4.7-3)"
	.section	.note.GNU-stack,"",@progbits
