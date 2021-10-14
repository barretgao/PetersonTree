JAVAFILES = TestTreeLock.java BasicTestTreeLock.java TreeLock.java PetersonNode.java Counter.java ThreadID.java

run: $(JAVAFILES)
	javac $(JAVAFILES)
	java BasicTestTreeLock
	java TestTreeLock
	# uncomment the below if you'd like to run with assertions enabled
	# java -ea TestTreeLock 

	
clean:
	rm -rf *.class
