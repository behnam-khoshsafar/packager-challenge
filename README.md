# Package Challenge

An Approach to solve package problem to achieve optimal list of items that have the lowest total weight and highest
total cost.

---

## Introduction

We want to send our friend a package with different things. Each thing we put inside the package has such parameters as
index number, weight and cost. The package has a weight limit. Our goal is to determine which things to put into the
package so that the total weight is less than or equal to the package limit, and the total cost is as large as possible.
We would prefer to send a package which weighs less in case there is more than one package with the same price.

Additional constraints:

1. Max weight that a package can take is ≤ 100
2. There might be up to 15 items you need to choose from
3. Max weight and cost of an item is ≤ 100

### Input sample

Our service accepts one argument that it is an absolute path of file. The input file contains several lines. Each line
is one pack. Each line contains the weight that the package can take (before the colon), and the list of items you need
to choose. Each item is enclosed in parentheses where the 1st number is an item’s index number, the 2nd is its weight,
and the 3rd is its cost. E.g. Our service will throw an APIException if incorrect parameters are being passed.

- 81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)
- 8 : (1,15.3,€34)
- 75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52)
  (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)
- 56 : (1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16) (5,46.81,€36)
  (6,48.77,€79) (7,81.80,€45) (8,19.36,€79) (9,6.76,€64)

### Output sample

For each set of items that we put into a package provide a new row in the output string (items’ index numbers are
separated by comma). E.g.

- 4
- \-
- 2,7
- 8,9

### Requirements wrench

- Java version 11 or higher.

### Usage

This solution is meant to be used as a library (i.e. maven dependency).

- We can import artifacts as a dependency:

```xml

<dependency>
    <groupId>com.mobiquity</groupId>
    <artifactId>packager-challenege</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

- Project has one main method which runs all the algorithm within. There is a class com.mobiquity.packer.Packer with a
  static API method named pack. This method accepts the absolute path to a file as a String. The pack method returns the
  solution as a String


