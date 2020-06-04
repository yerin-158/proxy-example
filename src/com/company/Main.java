package com.company;

import com.company.handler.PeopleHandler;
import com.company.object.People;
import com.company.object.PeopleImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Main {
    interface If {
        void originalMethod(String s);
    }

    static class Original implements If {
        @Override
        public void originalMethod(String s) {
            System.out.println(s);
        }
    }

    static class Handler implements InvocationHandler {
        private final If original;

        public Handler(If original) {
            this.original = original;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            System.out.println("BEFORE");
            method.invoke(original, args);
            System.out.println("AFTER");
            return null;
        }
    }


    public static void main(String[] args) {
        /*Original original = new Original();
        Handler handler = new Handler(original);
        If f = (If) Proxy.newProxyInstance(If.class.getClassLoader(), new Class[] {If.class}, handler);
        f.originalMethod("Hallo");*/

        People people = (People) Proxy.newProxyInstance(People.class.getClassLoader(), new Class[] {People.class}, new PeopleHandler());
        people.talking("Hello world!");
        people.eating("chicken");
        people.studying("math");
    }
}