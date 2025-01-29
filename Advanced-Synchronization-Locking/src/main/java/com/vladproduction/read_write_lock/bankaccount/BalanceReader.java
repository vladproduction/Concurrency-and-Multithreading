package com.vladproduction.read_write_lock.bankaccount;

public class BalanceReader implements Runnable {

    private BankAccount bankAccount;

    public BalanceReader(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println(Thread.currentThread().getName() + " sees balance: $" + bankAccount.getBalance());
            try {
                Thread.sleep(100);
            }catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
