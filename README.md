YahooPortfolio 
============
A simple java program to parse the portfolios given in a text file and to retrive price of the symbols in each portfolio 
and to arrange the  . 

## How it works

Yahoo Finance API allows to retrive datas of the symbols in CSV format. This program parses it and gets the details required from the CSV


## Methods 

The library currently supports the following methods: 

```java
getPortfolioDetails();
getStockSymbol(); 
getTotalPriceForStock(); 
patternValidator(); 
getDetailsFromYahooAPI(); 
fetchDataOutput(); 
```

## Installation

This program can be executed in CMD.A runnable jar and a sample file is also present in this project under portfoio jar folder
execute that jar folder using the following command

java -jar PortFolio.jar sample.txt


## JunitTestCases
A  seperate Junit TestCase class is added in the latest release. Run the PortFolioTestcases.java as JUnit test


##Note
Application allows only .txt file with valid Symbols and pattern.
For some JUnit Testcases File path needs to be passed in the methods please ensure to give a correct file path to get the desired results
Sample Files are attached in the portfolio jar folder


##Thank You
