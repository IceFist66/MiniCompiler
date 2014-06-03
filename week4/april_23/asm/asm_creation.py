'''
In order for this to work, call 'python ILOC_creation [template file]' otherwise it wont work.
Hope this helps.
'''

import sys

def create_Class(name, size, text):
   new_name = name.capitalize()
   new_class = "package asm;\n\n"
   new_class += "public class " + new_name + " extends Instruction_a{\n\n"
   new_class += "\tpublic " + new_name
   new_class += "("
   if(size >= 1):
       new_class += "String arg1"
   if(size >= 2):
       new_class +=", String arg2"
   if(size >= 3):
       new_class +=", String arg3"
   new_class +="){\n"
   if (size >=1):
       new_class +="\t\tthis.arg1 = arg1;\n"
   else:
       new_class +="\t\tthis.arg1 = null;\n"
   if(size >= 2):
       new_class +="\t\tthis.arg2 = arg2;\n"
   else:
       new_class +="\t\tthis.arg2 = null;\n"
   if(size >= 3):
       new_class +="\t\tthis.arg3 = arg3;\n"
   else:
       new_class +="\t\tthis.arg3 = null;\n"
   new_class +="\t\tthis.text = " + text + ";\n"
   new_class +="\t}\n\n"
   if(name == "imulq"):
       new_class += "\tpublic " + new_name
       new_class += "("
       new_class += "String arg1"
       new_class +=", String arg2"
       new_class +=", String arg3"
       new_class +="){\n"
       new_class +="\t\tthis.arg1 = arg1;\n"
       new_class +="\t\tthis.arg2 = arg2;\n"
       new_class +="\t\tthis.arg3 = arg3;\n"
       new_class +="\t\tthis.text = \"imulq \" + arg3 + \" , \" + arg2  + \" , \" + arg1;\n"
       new_class +="\t}\n\n"
   new_class +="}"
   return new_class

def string(name, text):
    return "\"" + name + " \" + " + text

def create_file(name, content):
    f = open(name.capitalize()+".java", "w")
    f.write(content)
    f.close()

def make_asm(pointer):
    null

def main():
    if(len(sys.argv)<2):
        print "Error: <iloc_> template_file"
        exit()
    with open(sys.argv[1]) as f:
        content = f.readlines()
    i = 0
    while(i<len(content)):
        args = int(content[i+1])
        text = content[i+2].rstrip()
        classes = content[i].split()
        j = 0
        while(j<len(classes)):
            name = classes[j]
            if(name == "cfi"):
                text = "\".cfi_\" + arg1"
                create_file(name, create_Class(name, args, text))           
            else if(name == "text"):
                text = "\".text\" + arg1"
                create_file(name, create_Class(name, args, text))           
            else:
                create_file(name, create_Class(name, args, string(name, text)))            
            j+=1
        i+=4

main()
