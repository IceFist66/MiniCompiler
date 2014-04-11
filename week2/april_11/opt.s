	.file	"opt.c"
	.text
	.p2align 4,,15
.globl func
	.type	func, @function
func:
	pushl	%ebp 
	movl	%esp, %ebp
	movl	8(%ebp), %eax //save x
	popl	%ebp
	leal	2(%eax,%eax,2), %eax //x = 2x+2
	ret
	.size	func, .-func
	.ident	"GCC: (GNU) 4.4.7 20120313 (Red Hat 4.4.7-3)"
	.section	.note.GNU-stack,"",@progbits
