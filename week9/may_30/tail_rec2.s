	.file	"tail_rec2.c"
	.text
.globl fact
	.type	fact, @function
fact:
.LFB0:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	subq	$16, %rsp
	movq	%rdi, -8(%rbp)
	cmpq	$0, -8(%rbp)
	jg	.L2
	movl	$1, %eax
	jmp	.L3
.L2:
	movq	-8(%rbp), %rax
	subq	$1, %rax
	movq	%rax, %rdi
	call	fact
	imulq	-8(%rbp), %rax
.L3:
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE0:
	.size	fact, .-fact
.globl factrec
	.type	factrec, @function
factrec:
.LFB1:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	subq	$16, %rsp
	movq	%rdi, -8(%rbp)
	movq	%rsi, -16(%rbp)
	cmpq	$0, -8(%rbp)
	jg	.L6
	movq	-16(%rbp), %rax
	jmp	.L7
.L6:
	movq	-8(%rbp), %rax
	imulq	-16(%rbp), %rax
	movq	-8(%rbp), %rdx
	subq	$1, %rdx
	movq	%rax, %rsi
	movq	%rdx, %rdi
	call	factrec
.L7:
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE1:
	.size	factrec, .-factrec
.globl fact2
	.type	fact2, @function
fact2:
.LFB2:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	subq	$16, %rsp
	movq	%rdi, -8(%rbp)
	movq	-8(%rbp), %rax
	movl	$1, %esi
	movq	%rax, %rdi
	call	factrec
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE2:
	.size	fact2, .-fact2
	.ident	"GCC: (GNU) 4.4.7 20120313 (Red Hat 4.4.7-3)"
	.section	.note.GNU-stack,"",@progbits
