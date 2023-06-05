package com.sap.cc.tdd;

public class FizzBuzz {
    public String print(int number){
        var returnString = "";
        if (number%3 == 0) returnString += "Fizz";
        if (number%5 == 0) returnString += "Buzz";
        return  returnString.equals("") ?  String.valueOf(number) :  returnString;
    }
}
