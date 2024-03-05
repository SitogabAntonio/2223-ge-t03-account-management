package fintech.model;

/**
 * @author 12S21007 Dame J Sitinjak
 * @author 12S21015 Sitogab Antonio Octavianus Girsang
 */

public class Transaction {

    private final String name;
    private Double amount;
    private final String postedAt;
    private final String note;
    private double balance = 0.0;
    private static int idplus = 1;
    private int id;

    public Transaction(String accountName, Double amount, String postedAt, String note) {
        this.name = accountName;
        this.amount = amount;
        this.postedAt = postedAt;
        this.note = note;
        this.balance = 0.0;
        this.id = idplus++;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public double getAmount() {
        return amount;
    }

    public String getNote() {
        return note;
    }

    public String getPosted_at() {
        return postedAt;
    }

    public void setId(int newId) {
        this.id = newId;
    }

    @Override
    public String toString() {

        return this.id + "|" + this.name + "|" + this.amount + "|" + this.postedAt + "|"
                + this.note;

    }

    public String getRecipientName() {
        return null;
    }

}
