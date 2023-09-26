package com.sap.cc.skillcheck;

import com.sap.cc.skillcheck.donttouch.FizzBuzz;
import org.springframework.stereotype.Component;

@Component
public class FizzBuzzImpl implements FizzBuzz {
    private static final String BINGO = "Bingo";
    private String previousResult = "";

    @Override
    public String evaluate(int number) {
        String res = "";

        if (number % 3 == 0) {
            res += "Fizz";
        }
        if (number % 5 == 0) {
            res += "Buzz";
        }
        if (res.isEmpty()) {
            res = Integer.toString(number);
        }

        return selectEvaluate(res);
    }

    private String selectEvaluate(String res) {
        if (previousResult.equals(res)) {
            return res + BINGO;
        }
        previousResult = res;
        return res;
    }
}
