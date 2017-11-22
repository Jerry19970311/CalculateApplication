package cn.edu.bistu.cs.se.calculateapplication;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

import cn.edu.bistu.cs.se.calculateapplication.IllegalInputException;

public class BaseCalculate{
    public static final String LEFT="(";
    public static final String FIRST="sincostancotlgln!";
    public static final String SECOND="^√";
    public static final String THIRD="*/";
    public static final String FORTH="+-";
    public static final String RIGHT=")";
    public static final String ALL=LEFT+SECOND+FIRST+THIRD+FORTH+RIGHT;
    public static double count(String str) throws IllegalInputException {
        int left=str.indexOf('(');
        int right=str.lastIndexOf(')');
        if(left!=-1&&right!=-1) {
            if (right <= left + 1) {
                throw new IllegalInputException("括号的输入不合法");
            }
            String s = str.substring(left + 1, right);
            double g = count(s);
            //str=str.replace(left);
        }

        double result;
        Stack<Character> port;
        Stack<Double> number;




        return 0;
    }
    //如果直接使用字符串处理，除了遍历使得效率降低外，在遇到三角函数等较为特殊的运算时不易处理，因此宜使用队列处理（在实际操作阶段直接构建队列，将数字同运算符分割开来置于不同元素）
    public static double count2(Queue<String> cal) throws IllegalInputException{
        double result=0;
        Stack<String> port=new Stack<String>();
        Stack<Double> number=new Stack<Double>();
        String s;
        if(cal.peek().equals("-")){
            number.push(0d);
        }
        do {
            s = cal.poll();
            System.out.println("BeforeCal:" + cal);
            System.out.println("64556465:" + s);
            int index = ALL.indexOf(s);
            if (index >= 0) {
                while (needCarry(port, index)){
                    if(s.equals(RIGHT)){
                        port.push(s);
                    }
                    carry(port, number);
                    if(s.equals(RIGHT)){
                        break;
                    }
                }
                /*while (needCarry(port,index)){
                    carry(port,number);
                }*/
                if(s.equals(RIGHT)){
                    continue;
                }
                port.push(s);
                //处理符号位。
                if(s.equals(LEFT)){
                    System.out.println("有正负号？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？");
                    if(cal.peek().equals("+")){
                        cal.poll();
                    }
                    if(cal.peek().equals("-")){
                        cal.poll();
                        port.push("!");
                    }
                }
            } else {
                try {
                    number.push(Double.valueOf(s));
                }catch (NumberFormatException e){
                    throw new IllegalInputException();
                }
            }
            System.out.println("number:" + number);
            System.out.println("port:" + port+"\n");
        }while (!cal.isEmpty());
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        while (!port.empty()){
            carry(port,number);
        }
        System.out.println("LaterNumber:" + number);
        result=number.pop();
        /*if(number.empty()==false||port.empty()==false){
            throw new IllegalInputException();
        }*/
        return result;
    }
    //判断符号入符号栈的时候是需要进行出栈操作(false)还是直接入栈(true)，不参与对任何数据结构的数据修改。
    public static boolean needCarry(Stack<String> port,int i){
        if(port.empty()){
            return false;
        }
        String temp=port.peek();
        System.out.println("？--------------------"+temp+"------------------------？"+i+"\t"+ALL.charAt(i)+"："+port);
        //0~13位是三角函数和括号，入栈时可以不考虑优先级而直接入栈
        if(i>=0&&i<=2){
            System.out.println("0~2？？？？？？？？？？？");
            return false;
        }else if(i<=19){
            //13、14位是乘方和开方符号，优先级在其他运算符之上（除三角函数），因此只要考虑与之同级别的运算符，如果是则进行先出栈后入栈操作，如果是其他运算符就直接入栈。
            if(SECOND.indexOf(temp)>=0){
                return true;
            }else{
                return false;
            }
        }else if(i<=21){
            //15、16位是乘法和除法符号，优先级在加号和减号之上，因此如果栈顶是加号、减号或者括号则直接入栈，否则先执行出栈到栈空或者栈顶为加号或减号为止。
            if(temp.equals(LEFT)||(FORTH.indexOf(temp)>=0)){
                return false;
            }else{
                return true;
            }
        }else if(i<=23){
            //17、18位是加法和减法符号，优先级仅高于左括号，因此只需判定栈顶元素是否为左括号，是则直接出栈，否则进行出栈操作直到栈空或者栈顶为左括号为止。
            if(temp.equals(LEFT)){
                return false;
            }else{
                return true;
            }
        }else{
            return true;
        }
    }
    //执行出栈和入栈操作，凡是needCarry方法返回值为true的情况均需执行该方法。
    public static void carry(Stack<String> port,Stack<Double> number) throws IllegalInputException {
        String p=port.pop();
        System.out.println("当前运算符:"+p);
        double result;
        System.out.println();
        if(FIRST.indexOf(p)>=0){
            System.out.println("InNumber1:"+number+"\tport:"+p);
            double n=number.pop();
            result=singleCount(p,n);
            number.push(result);
        }else if(p.equals(LEFT)){
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }else if(p.equals(RIGHT)){
            System.out.println("*******************************************************");
            while(!port.empty()&&!port.peek().equals(LEFT)){
                carry(port, number);
            }
            port.pop();
        }else{
            System.out.println("InNumber2:"+number+"\tport:"+p);
            System.out.println("InPort:"+port);
            double b=number.pop();
            double a=number.pop();
            result=doubleCount(p,a,b);
            number.push(result);
        }
        System.out.println();
    }
    //执行详细地计算操作（单目运算符）
    public static double singleCount(String port,double a) throws IllegalInputException {
        if(port.equals("sin")){
            System.out.println("用了sin，数为:"+a);
            return Math.sin(a);
        }
        if(port.equals("cos")){
            return Math.cos(a);
        }
        if(port.equals("tan")){
            return Math.tan(a);
        }
        if(port.equals("cot")){
            return (double)1/Math.tan(a);
        }
        if(port.equals("lg")){
            return Math.log10(a);
        }
        if(port.equals("ln")){
            return Math.log(a);
        }
        if(port.equals("!")){
            return 0d-a;
        }
        System.out.println("啥也没算出来？？？？？？？？？？？？？？？？？？？？？？？");
        throw new IllegalInputException("输入不合法：请规范输入运算符号。");
    }
    //执行详细地计算操作（双目运算符）
    public static double doubleCount(String port,double a,double b){
        if(port.equals("+")){
            return a+b;
        }
        if(port.equals("-")){
            return a-b;
        }
        if(port.equals("*")){
            return a*b;
        }
        if(port.equals("/")){
            //（未处理）除数为0会抛出ArithmeticException异常
            return a/b;
        }
        //a为底数，b为指数
        //（未处理）乘方和开方的取值限制问题
        if(port.equals("^")){
            return Math.pow(a,b);
        }
        if(port.equals("√")){
            return Math.pow(b,1d/(double)a);
        }
        return 0x0000ffff;
    }
}
