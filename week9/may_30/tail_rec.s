	.file	"tail_rec.c"
	.text
	.p2align 4,,15
.globl fact
	.type	fact, @function
fact:
.LFB0:
	.cfi_startproc
	cmpq	$1, %rdi
	movl	$1, %eax
	jg	.L4
	jmp	.L3
	.p2align 4,,10
	.p2align 3
.L7:
	movq	%rdx, %rdi
.L4:
	leaq	-1(%rdi), %rdx
	imulq	%rdi, %rax
	cmpq	$1, %rdx
	jne	.L7
.L3:
	rep
	ret
	.cfi_endproc
.LFE0:
	.size	fact, .-fact
	.p2align 4,,15
.globl factrec
	.type	factrec, @function
factrec:
.LFB1:
	.cfi_startproc
	cmpq	$1, %rdi
	movq	%rsi, %rax
	jle	.L9
	.p2align 4,,10
	.p2align 3
.L12:
	imulq	%rdi, %rax
	subq	$1, %rdi
	cmpq	$1, %rdi
	jne	.L12
.L9:
	rep
	ret
	.cfi_endproc
.LFE1:
	.size	factrec, .-factrec
	.p2align 4,,15
.globl fact2
	.type	fact2, @function
fact2:
.LFB2:
	.cfi_startproc
	cmpq	$1, %rdi
	movl	$1, %eax
	jle	.L16
	.p2align 4,,10
	.p2align 3
.L19:
	imulq	%rdi, %rax
	subq	$1, %rdi
	cmpq	$1, %rdi
	jne	.L19
.L16:
	rep
	ret
	.cfi_endproc
.LFE2:
	.size	fact2, .-fact2
	.ident	"GCC: (GNU) 4.4.7 20120313 (Red Hat 4.4.7-3)"
	.section	.note.GNU-stack,"",@progbits
