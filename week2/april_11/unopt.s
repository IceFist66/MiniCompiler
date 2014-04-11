	.file	"unopt.c"
	.text
.globl func
	.type	func, @function
func:
	pushl	%ebp
	movl	%esp, %ebp
	subl	$16, %esp
	movl	$2, -8(%ebp)
	movl	$3, -4(%ebp)
	movl	-4(%ebp), %eax
	imull	8(%ebp), %eax
	addl	-8(%ebp), %eax
	leave
	ret
	.size	func, .-func
	.ident	"GCC: (GNU) 4.4.7 20120313 (Red Hat 4.4.7-3)"
	.section	.note.GNU-stack,"",@progbits
