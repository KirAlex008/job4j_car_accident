package ru.job4j.di;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringDI {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("ru/job4j/di");
        context.refresh();
        StartUI ui = context.getBean(StartUI.class);
        ConsoleInput cl = context.getBean(ConsoleInput.class);
        ui.add("Petr Arsentev");
        ui.add("Ivan ivanov");
        ui.print();
        String answer = cl.askStr("Write something ...");
        ui.add(answer);
        StartUI another = context.getBean(StartUI.class);
        another.getStore().getAll().forEach(System.out::println);
    }
}
