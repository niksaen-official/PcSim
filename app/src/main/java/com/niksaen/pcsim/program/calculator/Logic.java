package com.niksaen.pcsim.program.calculator;

import java.util.ArrayList;
import java.util.List;

public class Logic {
    public static Double evaluate(String expression) {
        //  Этап 1
        //  На данном этапе преобразуем нашу строку в список строк, через совмещение цикла for-each и метода split(),
        //  также используем метод trim(), чтобы избавиться от пробелов по краям.
        //  Обратите внимание, что каждым вторым элиментом я добавляю " ". Для того, чтобы потом мне было легче работать со строкой

        List<String> strList = new ArrayList<>();
        for (String listElement : expression.trim().split(" ")) {
            strList.add(listElement);
            strList.add(" ");
        }
        strList.remove(strList.size() - 1);
        //  Этап 2
        //  Производим поиск символа "(" в списке если находим, то преобразуем все символы от '(' до последнего ')' в строку.
        //  Надо быть внимательными и проверить на случай двух контрукций: 1) (())  2) ()().
        //  P.S. После получения строки мы используем РЕКУРСИЮ (метод вызывает сам себя). Таким образом будем находить произведение скобок.

        if (strList.indexOf("(") != -1) {
            //Если "(" обнаружен, ищем подходящую конструкцию используя цикл.
            for (int i = strList.indexOf("(") + 1; i < strList.size() - 1; i++) {
                //Конструкция 1: первым элиментом, который мы отыскали были вторые "("
                String recursion = "";
                if (strList.get(i).equals("(")) {
                    for (int j = i; j < strList.lastIndexOf(")"); j++) {
                        recursion += strList.get(j);
                    }
                    // сверху считывали последовательность находящуюся в скобках (()) До lastIndex элемента
                    String test = expression.substring(expression.indexOf("("), expression.lastIndexOf(")") + 1);
                    // test - последовательность как и сверху, но с добавлением скобок по краям

                    // т.к. наш метод evaluate() возвращает Double, мы должны преобразовать результат рекрусии в String;
                    String testRecursion = String.valueOf(evaluate(recursion));
                    expression = expression.replace(test, testRecursion);
                    // преобразовали нашу строку с использование рекруси. Избавились от первых скобок
                    strList.removeAll(strList);
                    for (String newElement : expression.trim().split(" ")) {
                        strList.add(newElement);
                        strList.add(" ");
                    }
                    // Тут очищаем наш список и сново его заполняем (но уже раскрыв первые скобки)
                }

                //Конструкция 2: первым элиментом, который мы отыскали был  ")"
                String recursion2 = "";
                if (strList.get(i).equals(")")) {
                    for (int j = strList.indexOf("(") + 1; j < strList.indexOf(")"); j++) {
                        recursion2 += strList.get(j);
                    }
                    String test2 = expression.substring(expression.indexOf("("), expression.lastIndexOf(")") + 1);
                    String testRecursion2 = String.valueOf(evaluate(recursion2));
                    expression = expression.replace(test2, testRecursion2);
                    for (String newElement : expression.trim().split(" ")) {
                        strList.add(newElement);
                        strList.add(" ");
                    }
                    // Тут повторили тот же алгоритм, что и в первой конструкции
                }
            }
        }
        //  Этап 3
        //  Заключительный этап на котором мы будем реализовывать сами вычесления (*/-+)
        //  Всю реализацию помещаем в цикл while ( который прекратиться, если все действия будут выполнены (соответственно в списке останется 1 элемент)).
        //  Внимательно посмотрите на порядок операций: 1)/ 2)* 3)- 4)+

        // System.out.println(expression + "-------expression-");
        // System.out.println();
        // создаем очередной список для реализации вычеслений, на этот раз без добавления " ".
        List<String> stringList2 = new ArrayList<>();
        for (String element : expression.trim().split(" ")) {
            stringList2.add(element);
        }


        while (stringList2.size() != 0) {
            // работаем со списком: глубоком этапе рекрусии обрабатываем:  (2+0)
            // на среднем: 1 * 1  "или вот этой части уравнения "( ( 2 + 0 ) * 1 ) "
            // Посмтортите сами
            // for (String x : stringList2) System.out.print(x );
            // System.out.println();

            // наш Double :) Также стоит обратить внимание, что для получения класса обертки мы используем не
            // Double.parseDouble() а Double.valueOf()
            Double result = 0d;

            //  Сами алгоритмы вычеслений, впринципе понятны, поэтому не буду их комментировать.
            //  Однако обратите внимание на очередность, особенно при вычетании (там вместо 1 условия, 3)
            //  Если что в комментариях под постом немного объясню, если кто-нибудь дочитает до сюда и у него будет желание)
            if (stringList2.indexOf("/") != -1) {
                int index = stringList2.indexOf("/");
                result = Double.valueOf(stringList2.get(index - 1)) / Double.valueOf(stringList2.get(index + 1));
                stringList2.add(index - 1, String.valueOf(result));
                stringList2.remove(index + 2);
                stringList2.remove(index + 1);
                stringList2.remove(index);
            }
            else if (stringList2.indexOf("*") != -1) {
                int index = stringList2.indexOf("*");
                result = Double.valueOf(stringList2.get(index - 1)) * Double.valueOf(stringList2.get(index + 1));
                stringList2.add(index - 1, String.valueOf(result));
                stringList2.remove(index + 2);
                stringList2.remove(index + 1);
                stringList2.remove(index);
            }
            else if (stringList2.indexOf("-") != -1) {
                int index = stringList2.indexOf("-");
                int lastIndex = stringList2.lastIndexOf("-");
                if (index == 0) {
                    result = 0.0 - Double.valueOf(stringList2.get(index + 1));
                    stringList2.add(0, String.valueOf(result));
                    stringList2.remove(2);
                    stringList2.remove(1);
                }
                else if ((lastIndex-2>0) && (stringList2.get(lastIndex-2).equals("-"))){
                    result = Double.valueOf(stringList2.get(lastIndex + 1)) + Double.valueOf(stringList2.get(lastIndex - 1));
                    stringList2.add(lastIndex - 1, String.valueOf(result));
                    stringList2.remove(lastIndex + 2);
                    stringList2.remove(lastIndex + 1);
                    stringList2.remove(lastIndex);
                }
                else {
                    result = Double.valueOf(stringList2.get(index - 1)) - Double.valueOf(stringList2.get(index + 1));
                    stringList2.add(index - 1, String.valueOf(result));
                    stringList2.remove(index + 2);
                    stringList2.remove(index + 1);
                    stringList2.remove(index);
                }
            }
            else if (stringList2.indexOf("+") != -1) {
                int index = stringList2.indexOf("+");
                result = Double.valueOf(stringList2.get(index - 1)) + Double.valueOf(stringList2.get(index + 1));
                stringList2.add(index - 1, String.valueOf(result));
                stringList2.remove(index + 2);
                stringList2.remove(index + 1);
                stringList2.remove(index);
            }

            // Вот тут все немного коряво. (На всякий случий проверял отсутствие (*/+-))
            if ((stringList2.indexOf("*") == -1) && (stringList2.indexOf("/") == -1) && (stringList2.indexOf("+") == -1) && (stringList2.indexOf("-") == -1)) {
                return result;
            }
        }
        return Double.valueOf(stringList2.get(0));
    }
}
