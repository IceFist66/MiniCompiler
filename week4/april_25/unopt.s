	.file	"unopt.c"
	.text
.globl square
	.type	square, @function
square:
.LFB0:
	.cfi_startproc
	pushq	%rbp              //Save base pointer
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp        //Set stack pointer as base pointer
	.cfi_def_cfa_register 6
	movq	%rdi, -8(%rbp)    //Move rdi to offset -8 from rbp
	movq	-8(%rbp), %rax    //Move offset -8 of rbp to rax
	imulq	-8(%rbp), %rax    //Multiply offset -8 of rbp with rax and save it with rax
	leave                   //restore base pointer
	.cfi_def_cfa 7, 8
	ret                     //return
	.cfi_endproc
.LFE0:
	.size	square, .-square
	.section	.rodata
.LC0:
	.string	"%ld %ld %ld %ld %ld %ld %ld\n"
	.text
.globl print_them
	.type	print_them, @function
print_them:
.LFB1:
	.cfi_startproc
	pushq	%rbp              //save base pointer
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp        //set stack pointer as base pointer
	.cfi_def_cfa_register 6
	pushq	%rbx              //saving the return value
	subq	$72, %rsp         //Subtract 72 from stack pointer
	movq	%rdi, -24(%rbp)   //move 1st argument to an offset of -24 from frame pointer
	movq	%rsi, -32(%rbp)   //move 2nd argument to an offset of -32 from frame pointer
	movq	%rdx, -40(%rbp)   //move 3rd argument to an offset of -40 from frame pointer
	movq	%rcx, -48(%rbp)   //move 4th argument to an offset of -48 from frame pointer
	movq	%r8, -56(%rbp)    //move 5th argument to an offset of -56 from frame pointer
	movq	%r9, -64(%rbp)    //move 6th argument to an offset of -64 from frame pointer
	movl	$.LC0, %eax       //move value of .LCO into eax
	movq	-56(%rbp), %r8    //move value back to 5th argument
	movq	-48(%rbp), %rdi   //move value to 1st argument
	movq	-40(%rbp), %rcx   //move value to 4th argument
	movq	-32(%rbp), %rdx   //move value to 3rd argument
	movq	-24(%rbp), %rbx   //move value to 2nd return value
	.cfi_offset 3, -24
	movq	16(%rbp), %rsi    //move value to 2nd arguement
	movq	%rsi, 8(%rsp)     //move value in rsi to offset of 8 from stack pointer
	movq	-64(%rbp), %rsi   //move value of offset -64 from frame pointer to 2nd argument
	movq	%rsi, (%rsp)      //move 2nd argument to stack pointer
	movq	%r8, %r9          //move 5th argument to 6th argument
	movq	%rdi, %r8         //move 1st argument to 5th argument
	movq	%rbx, %rsi        //move 2nd return argument to 2nd argument
	movq	%rax, %rdi        //move 1st return value to 1st argument
	movl	$0, %eax          //move the value 0 to the extended register
	call	printf            //print to screen
	addq	$72, %rsp         //add the value 72 to stack pointer
	popq	%rbx              //pop the return value
	leave                   //restore stack pointer
	.cfi_def_cfa 7, 8
	ret                     //return
	.cfi_endproc
.LFE1:
	.size	print_them, .-print_them
.globl main
	.type	main, @function
main:
.LFB2:
	.cfi_startproc
	pushq	%rbp                 //Save base pointer
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp           //set stack pointer as base pointer
	.cfi_def_cfa_register 6
	subq	$16, %rsp            //subtract 16 from stack pointer
	movl	$10, %edi            //move 10 into edi
	call	square               //call the sqare function
	movq	%rax, (%rsp)         //move the return value to the stack pointer (returned from square)
	movl	$6, %r9d             //move the value of 6 into r9d
	movl	$5, %r8d             //move the value of 5 into r8d
	movl	$4, %ecx             //move the value of 4 into ecx
	movl	$3, %edx             //move the value of 3 into edx
	movl	$2, %esi             //move the value of 2 into esi
	movl	$1, %edi             //move the value of 1 into edi
	call	print_them           //call print_them
	movl	$0, %eax             //move 0 into eax
	leave                      //restore stack pointer
	.cfi_def_cfa 7, 8
	ret                        //return
	.cfi_endproc
.LFE2:
	.size	main, .-main
	.ident	"GCC: (GNU) 4.4.7 20120313 (Red Hat 4.4.7-3)"
	.section	.note.GNU-stack,"",@progbits
