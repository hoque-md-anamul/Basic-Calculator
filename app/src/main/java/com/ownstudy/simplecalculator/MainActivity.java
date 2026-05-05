package com.ownstudy.simplecalculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    protected Button btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine, btnZero, btnDot;
    protected Button btnPlus, btnMinus, btnMultiple, btnDivision, btnEqual;
    protected Button btnAC, btnDelete, btnSign, btnBracket;
    protected TextView inputTxt, resultTxt;
    protected boolean addition;
    protected boolean minus = false;
    protected boolean division = false;
    protected boolean multiple = false;

    double firstNumber = 0;
    double secondNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Initialize all elements
        initializeAllElements();

        //Set OnClick Events For All Number Buttons
        setClickEventsAllButtons();
    }


    //Initialize all elements

    private void initializeAllElements() {

        //Two Text Views
        inputTxt = findViewById(R.id.edt_input_num);
        resultTxt = findViewById(R.id.txt_result_show);

        //All button 0 to 9
        btnOne = findViewById(R.id.btn_one);
        btnTwo = findViewById(R.id.btn_two);
        btnThree = findViewById(R.id.btn_three);
        btnFour = findViewById(R.id.btn_four);
        btnFive = findViewById(R.id.btn_five);
        btnSix = findViewById(R.id.btn_six);
        btnSeven = findViewById(R.id.btn_seven);
        btnEight = findViewById(R.id.btn_eight);
        btnNine = findViewById(R.id.btn_nine);
        btnZero = findViewById(R.id.btn_zero);
        btnDot = findViewById(R.id.btn_dot);

        //All Operators
        btnPlus = findViewById(R.id.btn_plus);
        btnMinus = findViewById(R.id.btn_minus);
        btnMultiple = findViewById(R.id.btn_multiple);
        btnDivision = findViewById(R.id.btn_division);
        btnEqual = findViewById(R.id.btn_equal);

        btnAC = findViewById(R.id.btn_all_clear);
        btnDelete = findViewById(R.id.btn_delete);
        btnSign = findViewById(R.id.btn_minus_plus);
        btnBracket = findViewById(R.id.btn_bracket);
    }

    //Set OnClick Events For All Number Buttons
    private void setClickEventsAllButtons() {

        // Click Events for 0 to 9 all number buttons
        View.OnClickListener allNumberButtonsClickEvents = myView -> {
            Button oneButton = (Button) myView;
            oneButton.animate()
                    .alpha(0.5f)
                    .scaleX(0.9f)
                    .scaleY(0.9f)
                    .setDuration(80)
                    .withEndAction(() ->
                            oneButton.animate()
                                    .alpha(1f)
                                    .scaleX(1f)
                                    .scaleY(1f)
                                    .setDuration(80)
                                    .start()
                    ).start();

            String buttonValues = oneButton.getText().toString();
            inputTxt.append(buttonValues);
        };


        // Click Events for 0 to 9 all number buttons
        btnOne.setOnClickListener(allNumberButtonsClickEvents);
        btnTwo.setOnClickListener(allNumberButtonsClickEvents);
        btnThree.setOnClickListener(allNumberButtonsClickEvents);
        btnFour.setOnClickListener(allNumberButtonsClickEvents);
        btnFive.setOnClickListener(allNumberButtonsClickEvents);
        btnSix.setOnClickListener(allNumberButtonsClickEvents);
        btnSeven.setOnClickListener(allNumberButtonsClickEvents);
        btnEight.setOnClickListener(allNumberButtonsClickEvents);
        btnNine.setOnClickListener(allNumberButtonsClickEvents);
        btnZero.setOnClickListener(allNumberButtonsClickEvents);

        // click Events for All Clear
        btnAC.setOnClickListener(v -> {
            animationButtons(v);
            inputTxt.setText("");
            resultTxt.setText("");
            resetAllOperatorsFalse();
        });

        // Click Events for Delete
        btnDelete.setOnClickListener(v -> {
            animationButtons(v);
            String currentValue = inputTxt.getText().toString();
            if (currentValue.length() > 1) {
                StringBuilder stringBuilder = new StringBuilder(currentValue);
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                inputTxt.setText(stringBuilder.toString());
            } else {
                inputTxt.setText("");
            }
        });

        // Click Events for dot Operator (.)
        btnDot.setOnClickListener(v -> {
            animationButtons(v);
            String cValue = inputTxt.getText().toString();
            if (!cValue.contains(".")) {
                inputTxt.append(".");
            }
        });

// Click Events Handling for this operator ( _ / +)
        btnSign.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                animationButtons(v);
                String CurrentValue = inputTxt.getText().toString();
                if (CurrentValue.equals("0")) {
                    return;
                }
                if (CurrentValue.startsWith("-")) {
                    inputTxt.setText(CurrentValue.substring(1));
                } else {
                    inputTxt.setText("-" + CurrentValue);
                }
            }
        });

        // Operators Buttons On Click Events
        btnPlus.setOnClickListener(v -> {
            animationButtons(v);
            String currentValue = inputTxt.getText().toString();
            if (!currentValue.isEmpty() && !isOperator(currentValue)) {
                // IF an operator was already active (e.g., user typed 2-3 and just pressed +)
                if (addition || minus || multiple || division) {
                    calculate(); // Calculate the 2-3 first
                } else {
                    // Otherwise, this is the very first number
                    firstNumber = Double.parseDouble(currentValue);
                }

                inputTxt.append("+");
                resetAllOperatorsFalse();
                addition = true;
            }

        });

        btnMinus.setOnClickListener(v -> {
            animationButtons(v);
            String currentValue = inputTxt.getText().toString();
            if (!currentValue.isEmpty() && !isOperator(currentValue)) {
                // IF an operator was already active (e.g., user typed 2-3 and just pressed +)
                if (addition || minus || multiple || division) {
                    calculate(); // Calculate the 2-3 first
                } else {
                    // Otherwise, this is the very first number
                    firstNumber = Double.parseDouble(currentValue);
                }

                inputTxt.append("-");
                resetAllOperatorsFalse();
                minus = true;
            }
        });
        btnMultiple.setOnClickListener(v -> {
            animationButtons(v);
            String currentValue = inputTxt.getText().toString();
            if (!currentValue.isEmpty() && !isOperator(currentValue)) {
                // IF an operator was already active (e.g., user typed 2-3 and just pressed +)
                if (addition || minus || multiple || division) {
                    calculate(); // Calculate the 2-3 first
                } else {
                    // Otherwise, this is the very first number
                    firstNumber = Double.parseDouble(currentValue);
                }

                inputTxt.append("*");
                resetAllOperatorsFalse();
                multiple = true;
            }
        });
        btnDivision.setOnClickListener(v -> {
            animationButtons(v);
            String currentValue = inputTxt.getText().toString();
            if (!currentValue.isEmpty() && !isOperator(currentValue)) {
                // IF an operator was already active (e.g., user typed 2-3 and just pressed +)
                if (addition || minus || multiple || division) {
                    calculate(); // Calculate the 2-3 first
                } else {
                    // Otherwise, this is the very first number
                    firstNumber = Double.parseDouble(currentValue);
                }

                inputTxt.append("/");
                resetAllOperatorsFalse();
                division = true;
            }
        });


        btnEqual.setOnClickListener(v -> {
            animationButtons(v);
            String currentValue = inputTxt.getText().toString();
            // 1. Check if the input is empty or just an operator
            if (currentValue.isEmpty() || isOperator(currentValue)) {
                return;
            }
            // 2. Run the loop-based calculation
            calculate();
            // 3. Move the final result to the top display
            // We use the 'firstNumber' which was updated inside calculate()
            resultTxt.setText(String.valueOf(firstNumber));
            // 4. Clear the bottom result view and reset flags
            inputTxt.setText("");
            resetAllOperatorsFalse();
        });
    }


    // Animation Function for All Animation

    private void animationButtons(@NonNull View myViews) {
        myViews.animate()
                .alpha(0.5f)
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(80)
                .withEndAction(() ->
                        myViews.animate()
                                .alpha(1f)
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(80)
                                .start()
                ).start();
    }

    private boolean isOperator(String text) {
        if (text == null || text.isEmpty()) return false;
        char last = text.trim().charAt(text.length() - 1);
        return "+-*/".indexOf(last) != -1; // Returns true if 'last' is found in this string
    }

    // Create this helper method to handle the math


    private void calculate() {
        String content = inputTxt.getText().toString();
        if (content.isEmpty()) return;
        // Safety: If there's no operator, just parse the single number
        if (!content.contains("+") && !content.contains("-") &&
                !content.contains("*") && !content.contains("/")) {
            firstNumber = Double.parseDouble(content);
            return;
        }

        try {
            // 1. Split the string into numbers using Regex
            // This keeps the numbers: ["2", "3", "4"]
            String[] numbers = content.split("[+\\-*/]");

            // 2. Extract the operators into a list
            // This keeps the symbols: ["-", "+"]
            String operators = content.replaceAll("[0-9.]", "");

            if (numbers.length > 0) {
                double total = Double.parseDouble(numbers[0]);

                // 3. Loop through the operators and apply them to the next number
                for (int i = 0; i < operators.length(); i++) {
                    char op = operators.charAt(i);
                    double nextNum = Double.parseDouble(numbers[i + 1]);

                    if (op == '+') total += nextNum;
                    else if (op == '-') total -= nextNum;
                    else if (op == '*') total *= nextNum;
                    else if (op == '/') {
                        if (nextNum != 0) total /= nextNum;
                        else {
                            Toast.makeText(this, "Divide by zero!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }

                // Update result display
                firstNumber = total; // Store for the next operation
                resultTxt.setText(String.valueOf(total));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Reset All Operators
    private void resetAllOperatorsFalse() {
        addition = false;
        minus = false;
        division = false;
        multiple = false;
    }

}