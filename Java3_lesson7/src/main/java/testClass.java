public class testClass {

    @BeforeSuite
    public void start(){
        System.out.println("Начинаем!");
    }

    @Test()
    public void test1(){
        System.out.println("Первый тест");
    }

    @Test(priority = 1)
    public void test2(){
        System.out.println("Второй тест");
    }

    @Test(priority = 2)
    public void test3(){
        System.out.println("Третий тест");
    }

    @Test(priority = 5)
    public void test4(){
        System.out.println("Четвертый тест");
    }

    @Test(priority = 4)
    public void test5(){
        System.out.println("Пятый тест");
    }

        @Test()
        public void test6(){
            System.out.println("Я тут пришел не на долго ;)");
    }

        @AfterSuite
        public void end(){
            System.out.println("Пока!");
        }
}