import java.util.ArrayList;
import java.util.Date;

public class Block {
    public String hash;
    public String previousHash;
    public ArrayList<Transaction> transactionListInBlock;
    private long timeStamp; //as number of milliseconds since 1/1/1970.
    private int nonce;

    public Block(String previousHash) {
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash(); //Making sure we do this after we set the other values.
    }

    public String calculateHash() {
        String listString = "";
        if(transactionListInBlock != null) {
            for (Transaction transaction : transactionListInBlock) {
                listString += transaction.hashTransaction();
            }
        }
        String calculatedhash = StringUtil.applySha256(
                previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + listString
        );
        return calculatedhash;
    }

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0"
        while(!hash.substring( 0, difficulty).equals(target)) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            nonce ++;
            hash = calculateHash();
        }
//        System.out.println("Block Mined!!! : " + hash);
    }

    public void setTransactionListInBlock(ArrayList<Transaction> transactionListInBlock) {
        this.transactionListInBlock = transactionListInBlock;
    }
}
