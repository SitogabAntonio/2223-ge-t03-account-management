package fintech.driver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

import fintech.model.Account;
import fintech.model.Transaction;

public class Driver2 {

    static Map<String, Account> accounts = new TreeMap<String, Account>();
    static List<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = null;

        while (true) {
            input = scanner.nextLine();
            if (input.equals("---")) {
                break;
            }

            String[] data = input.split("#");

            switch (data[0]) {
                case "create-account":
                    createAccount(data);
                    break;
                case "find-account":
                    findAccount(data);
                    break;
                case "create-transaction":
                    createTransaction(data);
                    break;
                case "remove-account":
                    removeAccount(data);
                    break;
                case "show-accounts":
                    showAccounts(data);
                    break;
                default:
            }
        }
        scanner.close();
    }

    private static void createAccount(String[] data) {
        Account newAccount = new Account(data[1], data[2]);
        if (accounts.get(newAccount.getName()) == null) {
            accounts.put(newAccount.getName(), newAccount);
            System.out.println(newAccount);
        }
    }

    private static void findAccount(String[] data) {
        String ownerName = data[1];
        Account account = accounts.values().stream().filter(a -> a.getName().equalsIgnoreCase(ownerName)).findFirst()
                .orElse(null);
        if (account != null) {
            System.out.println(account);
            List<Transaction> transactionsForAccount = transactions.stream()
                    .filter(t -> t.getName().equalsIgnoreCase(ownerName)
                            || (t instanceof Transaction
                                    && ((Transaction) t).getName().equalsIgnoreCase(ownerName)))
                    .sorted(Comparator.comparing(Transaction::getPosted_at))
                    .collect(Collectors.toList());
            for (Transaction t : transactionsForAccount) {
            }
        } else {
        }
    }

    private static void createTransaction(String[] data) {
        Account account = accounts.get(data[2]);
        if (account != null) {
            // cek apakah nama sender dan recipient valid
            if (data[1] != null && data[2] != null && data[3] != null && data[4] != null && data[5] != null) {
                try {
                    String senderName = data[1];
                    String recipientName = data[2];
                    double amount = Double.parseDouble(data[3]);
                    String postedAt = data[4];
                    String note = data[5];

                    if (Double.parseDouble(data[3]) < 0) {
                        throw new Exception("Insufficient Balance");
                    }

                    // cek saldo sender mencukupi untuk melakukan transaksi
                    double senderBalance = accounts.get(senderName).getBalance();
                    if (senderBalance - amount >= 0) {
                        // buat transaksi
                        Transaction newTransaction = new Transaction(senderName, amount, postedAt, note);
                        addTransaction(newTransaction);

                        // kurangi saldo sender dan tambahkan saldo recipient
                        accounts.get(senderName).setBalance(senderBalance - amount);
                        accounts.get(recipientName)
                                .setBalance(accounts.get(recipientName).getBalance() + amount);

                    }
                } catch (Exception e) {
                    if (e.getMessage().equals("Insufficient Balance")) {
                    } else {
                        System.out.println(e.getMessage());
                    }
                }
            }
        } else if (account == null) {
            if (data[1] != null) {
                try {
                    if (Double.parseDouble(data[2]) < 0
                            && accounts.get(data[1]).getBalance() + Double.parseDouble(data[2]) > 0) {
                        throw new Exception("Access");
                    } else if (Double.parseDouble(data[2]) > 0) {
                        throw new Exception("Access");
                    }
                } catch (Exception e) {
                    if (e.getMessage().equals("Access")) {
                        Transaction newTransaction = new Transaction(data[1], Double.parseDouble(data[2]),
                                data[3], data[4]);
                        addTransaction(newTransaction);
                        accounts.get(newTransaction.getName()).addTransaction(newTransaction);
                    }
                }
            }
        }
    }

    private static void showAccounts(String[] data) {
        List<Account> sortedAccounts = accounts.values().stream()
                .sorted(Comparator.comparing(Account::getName)).collect(Collectors.toList());

        for (Account account : sortedAccounts) {
            List<Transaction> transactionsForAccount = transactions.stream()
                    .filter(t -> t.getName().equalsIgnoreCase(account.getName())
                            || (t instanceof Transaction && ((Transaction) t).getRecipientName()
                                    .equalsIgnoreCase(account.getName())))

                    .sorted(Comparator.comparing(Transaction::getPosted_at)).collect(Collectors.toList());
            System.out.println(account);

            for (Transaction t : transactionsForAccount) {
                System.out.println(t);
            }

        }
    }

    private static void removeAccount(String[] data) {
        String accountId = data[1].toLowerCase();
        Account account = accounts.get(accountId);
        if (account != null) {
            accounts.remove(accountId);
        }
    }


    private static void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}
