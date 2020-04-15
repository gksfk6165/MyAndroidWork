package com.lec.android.a003_widget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    EditText tvResult;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tvResult = findViewById(R.id.tvResult);
    }

    char operator;
    double num1;
    double num2;
    double mid_num = 0.0;
    double result;

    @Override
    public void onClick(View v) {
        String text = tvResult.getText().toString();
        switch (v.getId()) {
            case R.id.btn1:
                tvResult.setText(text + "1");
                break;
            case R.id.btn2:
                tvResult.setText(text + "2");
                break;
            case R.id.btn3:
                tvResult.setText(text + "3");
                break;
            case R.id.btn4:
                tvResult.setText(text + "4");
                break;
            case R.id.btn5:
                tvResult.setText(text + "5");
                break;
            case R.id.btn6:
                tvResult.setText(text + "6");
                break;
            case R.id.btn7:
                tvResult.setText(text + "7");
                break;
            case R.id.btn8:
                tvResult.setText(text + "8");
                break;
            case R.id.btn9:
                tvResult.setText(text + "9");
                break;
            case R.id.btn0:
                tvResult.setText(text + "0");
                break;
            case R.id.btnpid:
                tvResult.setText(text + ".");
            case R.id.btndel:
                if(text.length()<=0)
                    break;
                tvResult.setText(text.substring(0,text.length()-1));
                break;
            case R.id.btnPlus:
                if (text.equals("")) {
                    Toast.makeText(this, "숫자를 입력하세요", Toast.LENGTH_SHORT).show();
                    break;
                }
                operator = '+';
                num1 = Double.parseDouble(text);
                if (mid_num == 0.0)
                    mid_num = num1;
                else
                    mid_num += num1;
                tvResult.setText("");
                break;
            case R.id.btnMinus:
                if (text.equals("")) {
                    Toast.makeText(this, "숫자를 입력하세요", Toast.LENGTH_SHORT).show();
                    break;
                }
                operator = '-';
                num1 = Double.parseDouble(text);
                if (mid_num == 0.0)
                    mid_num = num1;
                else
                    mid_num -= num1;
                tvResult.setText("");
                break;
            case R.id.btnMul:
                if (text.equals("")) {
                    Toast.makeText(this, "숫자를 입력하세요", Toast.LENGTH_SHORT).show();
                    break;
                }
                operator = '*';
                num1 = Double.parseDouble(text);
                if (mid_num == 0.0)
                    mid_num = num1;
                else
                    mid_num *= num1;
                tvResult.setText("");
                break;
            case R.id.btnDiv:
                if (text.equals("")) {
                    Toast.makeText(this, "숫자를 입력하세요", Toast.LENGTH_SHORT).show();
                    break;
                }
                operator = '/';
                num1 = Double.parseDouble(text);
                if (mid_num == 0.0)
                    mid_num = num1;
                else
                    mid_num /= num1;
                tvResult.setText("");
                break;
            case R.id.btnMod:
                if (text.equals("")) {
                    Toast.makeText(this, "숫자를 입력하세요", Toast.LENGTH_SHORT).show();
                    break;
                }
                operator = '%';
                num1 = Double.parseDouble(text);
                if (mid_num == 0.0)
                    mid_num = num1;
                else
                    mid_num %= num1;
                tvResult.setText("");
                break;
            case R.id.btnClear:
                num1 = 0;
                tvResult.setText("");
                break;
            case R.id.btnAns:
                if (text.equals("")) {
                    Toast.makeText(this, "숫자를 입력하세요", Toast.LENGTH_SHORT).show();
                    break;
                }
                num2 = Double.parseDouble(text);
                switch (operator) {
                    case '+':
                        result = mid_num + num2;
                        break;
                    case '-':
                        result = mid_num - num2;
                        break;
                    case '*':
                        result = mid_num * num2;
                        break;
                    case '/':
                        result = mid_num / num2;
                        break;
                    case '%':
                        result = (mid_num % num2);
                    default:
                        break;
                }
                tvResult.setText("" + result);
                mid_num = 0.0;
                num1 = 0;
                break;


        }

    }
}
