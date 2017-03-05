# HelloChange

## Assumptions:
* Java SE 8 JRE or JDK installed
* Only whole numbers are allowed
* Only positive numbers are allowed
* Allowed number range is positive 32-bit (Java int) 0 to 2^31 - 1.

## Building
### Minimum requirement
gradlew classes

### Jar creation
gradlew jar

## Execution
###Command line execution (from build/classes/main directory):
java com.james.Main

### jar execution (after jar build):
java -jar ./build/libs/HelloChange-0.0.1.jar