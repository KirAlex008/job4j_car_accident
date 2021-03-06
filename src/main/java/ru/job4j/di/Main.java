package ru.job4j.di;

public class Main {
    public static void main(String[] args) {
        Context context = new Context();
        context.reg(Store.class);
        context.reg(StartUI.class);
        context.reg(ConsoleInput.class);

        StartUI ui = context.get(StartUI.class);
        ConsoleInput cl = context.get(ConsoleInput.class);

        ui.add("Petr Arsentev");
        ui.add("Ivan ivanov");
        ui.print();

        String answer = cl.askStr("Write something ...");
        ui.add(answer);
        ui.print();
    }
}
