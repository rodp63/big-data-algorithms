# Map-Reduce algorithms
Simple Hadoop Map-Reduce algorithms in Java

## Setup and run a Hadoop Job

* Inside any folder, run the following commands:
  * Copy the input data from host to HDFS.
  
	```bash
	$ hdfs dfs -mkdir input
	$ hdfs dfs -put input/* input
	```
  * Compile the `<FILE>.java` file to create a `<EXEC_FILE>.jar` executable.
  
	```bash
	$ hadoop com.sun.tools.javac.Main <FILE>.java
	$ jar cf <EXEC_FILE>.jar <FILE>*.class 
	```
  * Execute the main function of the `<EXEC_CLASS>` class inside the job.
  
	```bash
	$ hadoop jar <EXEC_FILE>.jar <EXEC_CLASS> input output
	```
  * Check the output data.
	
	```bash
	$ hdfs dfs -ls output
	$ hdfs dfs -cat output/part-r-00000
	```
