package com.vladproduction.read_write_lock.bankaccount;

public class BalanceUpdater implements Runnable {

    private final BankAccount account;
    private final int id;

    public BalanceUpdater(BankAccount account, int id) {
        this.account = account;
        this.id = id;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {

            if(i % 2 == 0) {
                account.deposit(100);
            }else {
                account.withdraw(50);
            }

            try{
                Thread.sleep(150);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }

    }
}
