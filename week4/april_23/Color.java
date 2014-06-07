// see notes 4-16
// May need to consider removing rbp and rsp

public enum Color {
	RAX("%rax"), RBX("%rbx"), RCX("%rcx"), RDX("%rdx"), RSP("%rsp"), RBP("%rbp"),
	   RSI("%rsi"), RDI("%rdi"), R8("%r8"), R9("%r9"), R10("%r10"), 
	   R11("%r11"), R12("%r12"), R13("%r13"), R14("%r14"), R15("%r15"),
	   UNC("uncolorable");
	   
	private final String text;
	
	private Color(final String text) {
	   this.text = text;
	}
	
	public String text() {
	   return this.text;
	}
}
