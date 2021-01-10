import com.sun.jdi.InvocationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Class foo=testClass.class;
        Object testObject = foo.newInstance();
        Method[] methods= foo.getDeclaredMethods();
        ArrayList<Method> arr=new ArrayList<>();
        Method beforeMethod= null;
        Method afterMethod=null;
        for (Method o : foo.getDeclaredMethods()){
            if(o.isAnnotationPresent(Test.class)){
                arr.add(o);
            }
            if (o.isAnnotationPresent(BeforeSuite.class)){
                if (beforeMethod == null) beforeMethod = o;
                else throw new RuntimeException("Больше одного метода с аннотацией BeforeSuite");
        }
            if (o.isAnnotationPresent(AfterSuite.class)) {
                if (afterMethod == null) afterMethod = o;
                else throw new RuntimeException("Больше одного метода с аннотацией AfterSuite");
            }

            for(int i =1; i<=6;i++){
                for (int j=0; j<methods.length;j++){
                    if(methods[j].getAnnotation(Test.class)!=null){
                    if(methods[j].getAnnotation(Test.class).priority()==j){
                        arr.add(methods[j]);

                    }
                }
            }
        }
    }

        if (beforeMethod != null) beforeMethod.invoke(testObject, null);
        for (Method o : arr) o.invoke(testObject, null);
        if (afterMethod != null) afterMethod.invoke(testObject, null);
}}

