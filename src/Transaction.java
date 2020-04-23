public class Transaction {
    private String fromAdress;
    private String toAdress;
    private int amount;

    public Transaction(String fromAdress, String toAdress, int amount) {
        this.fromAdress = fromAdress;
        this.toAdress = toAdress;
        this.amount = amount;
    }

    public String getFromAdress() {
        return fromAdress;
    }
    public void setFromAdress(String fromAdress) {
        this.fromAdress = fromAdress;
    }

    public String getToAdress() {
        return toAdress;
    }

    public void setToAdress(String toAdress) {
        this.toAdress = toAdress;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return fromAdress + " " + toAdress + " " + amount;
    }
    public String hashTransaction(){
        return fromAdress+toAdress+amount;
    }
}
