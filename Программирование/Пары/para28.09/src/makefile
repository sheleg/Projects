#makefile
.SUFFIXES: .class .java
TARG = test
JC = javac -g
JM = java -ea -jar
JR = jar -cfe
OBJ = Matrix.class \
	  test.class

.java.class:
	$(JC) $<

run: build
	$(JM) $(TARG).jar

build: $(OBJ)
	$(JR) $(TARG).jar test $(OBJ)

clean:
	rm -f *.class

#Matrix.class: Matrix.java makefile
#
#test.class: test.java makefile
#
#eof