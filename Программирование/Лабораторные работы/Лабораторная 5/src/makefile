#makefile
.SUFFIXES: .class .java

PACK = laba5
TARG = test
JC = javac -g
JM = java -ea -jar
JR = jar -cfe
OBJ = $(PACK)/Polynom.class \
	  $(PACK)/test.class

.java.class:
	$(JC) $<

run: build
	$(JM) $(TARG).jar

build: $(OBJ)
	$(JR) $(TARG).jar $(PACK).test $(OBJ)

clean:
	rm -f *.class

#$(PACK)/Polynom.class: $(PACK)/Polynom.java makefile
#$(PACK)/test.class: #(PACK)/test.java makefile
#
#eof