main:
	movq $num , r1
	addq r1 , rarp
	movq ---- , ----
	movq ---- , ----
	movq r0 , r2
	movq r2 , r3
	movq ---- , ----
	movq ---- , ----
	movq ---- , ----
	movq $0 , r5
	movq ---- , ----
	movq $1 , r7
	movq r7 , eax
	jmp L10
L10:
	ret 
