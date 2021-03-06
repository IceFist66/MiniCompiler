package asm;

import java.io.*;
import java.util.*;

public abstract class Instruction_a{
    String arg1;
    String arg2;
    String arg3;
    String text;
    ArrayList<String> targets;
    ArrayList<String> sources;
	
    public String toString(){
    	return this.text;
    }

    public String getArg1(){
        return arg1;
    }

    public String getArg2(){
        return arg2;
    }

    public String getArg3(){
        return arg3;
    }

    public void setArg1(String arg1){
        this.arg1=arg1;
    }

    public void setArg2(String arg2){
        this.arg2=arg2;
    }

    public void setArg3(String arg3){
        this.arg3=arg3;
    }

    public ArrayList<String> getTargets(){
        return this.targets;
    }

    public ArrayList<String> getSources(){
        return this.sources;
    }
    
    public void resetText(){
    }

    public void resetText2(){
    }
}
