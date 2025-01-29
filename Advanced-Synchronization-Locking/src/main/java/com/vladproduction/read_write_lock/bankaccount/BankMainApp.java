package com.vladproduction.read_write_lock.bankaccount;

public class BankMainApp {
    public static void main(String[] args) {

        BankAccount bankAccount = new BankAccount(1000);

        //create 5 readers:
        for (int i = 1; i < 6; i++) {
            new Thread(new BalanceReader(bankAccount)).start();
        }

        //create 2 writers (updaters)
        for (int i = 1; i < 3; i++) {
            new Thread(new BalanceUpdater(bankAccount, i)).start();
        }

    }
}
