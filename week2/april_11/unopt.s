	.file	"unopt.c"
	.text
.globl func
	.type	func, @function //creates a new function
func:
	pushl	%ebp //pushing a base pointer
	movl	%esp, %ebp //saving x
	subl	$16, %esp //subtract 16 from the stack pointer
	movl	$2, -8(%ebp) //Saving a=2
	movl	$3, -4(%ebp) //Saving b=3
	movl	-4(%ebp), %eax //move x into equation
	imull	8(%ebp), %eax //x*b = z --> z is for simplicity
	addl	-8(%ebp), %eax //z+a = g
	leave
	ret //return g
	.size	func, .-func
	.ident	"GCC: (GNU) 4.4.7 20120313 (Red Hat 4.4.7-3)"
	.section	.note.GNU-stack,"",@progbits
