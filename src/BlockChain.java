import java.util.ArrayList;
import java.util.List;

public class BlockChain {

    public static List<Block> blockchain;
    public ArrayList<Transaction> pendingTransactions;
    public static int difficulty;
    public int miningReward = 100;

    public BlockChain() {
        blockchain = new ArrayList<>();
        blockchain.add(createGenenisBlock());
        pendingTransactions = new ArrayList<>();
    }

    public Block createGenenisBlock() {
        return new Block("0");
    }

    public Block getLatestBlock() {
        return blockchain.get(blockchain.size() - 1);
    }

    //    public void addBlock(Block newBlock){
//        newBlock.previousHash = getLatestBlock().hash;
//        newBlock.mineBlock(difficulty);
//        blockchain.add(newBlock);
//    }
    public void minePendingTransactions(String miningRewardAdress) {
        Block newBLock = new Block(getLatestBlock().hash);
        newBLock.mineBlock(difficulty);
        System.out.println(miningRewardAdress);
//        System.out.print(miningRewardAdress + ": " + pendingTransactions);
        newBLock.setTransactionListInBlock((ArrayList<Transaction>) pendingTransactions.clone());
        blockchain.add(newBLock);
        pendingTransactions.clear();
        pendingTransactions.add(new Transaction("CoinBase", miningRewardAdress, miningReward));
    }

    public void createTransaction(Transaction transaction) {
        pendingTransactions.add(transaction);
//        System.out.print(" T");
    }

    public int getBalanceOfAddress(String adress) {
        int balance = 0;
        for (Block block : blockchain) {
            if (block.transactionListInBlock != null) {
                for (Transaction tmp : block.transactionListInBlock) {
                    if (tmp.getFromAdress().equals(adress)) {
                        balance = balance - tmp.getAmount();
                    }
                    if (tmp.getToAdress().equals(adress)) {
                        balance = balance + tmp.getAmount();
                    }
                }
            }
        }
        return balance;
    }

    public static Boolean isChainValid(BlockChain blockChain) {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        //loop through blockchain to check hashes:
        for (int i = 1; i < blockChain.getBlockchain().size(); i++) {
            currentBlock = blockChain.getBlockchain().get(i);
            previousBlock = blockChain.getBlockchain().get(i - 1);
            //compare registered hash and calculated hash:
            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Current Hashes not equal");
                return false;
            }
            //compare previous hash and registered previous hash
            if (!previousBlock.hash.equals(currentBlock.previousHash)) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
            if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }

    public List<Block> getBlockchain() {
        return blockchain;
    }

    public static void setDifficulty(int difficulty) {
        BlockChain.difficulty = difficulty;
    }
}
