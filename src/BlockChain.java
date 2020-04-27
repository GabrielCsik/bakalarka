import java.util.ArrayList;
import java.util.List;

public class BlockChain {
//    public int counter = 0;
//    public int numOfTrans = 0;
    volatile boolean stopAddingTrans = false;
    public static List<Block> blockchain;
    public ArrayList<Transaction> pendingTransactions;
    public static int difficulty;
    public int miningReward = 100;
    private Object obj = new Object();

    public BlockChain() {
        blockchain = new ArrayList<>();
        blockchain.add(createGenenisBlock());
        pendingTransactions = new ArrayList<>();
    }

    public Block createGenenisBlock() {
        Block genesisBlock = new Block();
        genesisBlock.setPrevHash("0");
        return genesisBlock;
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
        Block newBLock = new Block();
        newBLock.mineBlock(difficulty);
        synchronized (obj) {
            newBLock.setPrevHash(getLatestBlock().hash);
//        System.out.println(counter);
//        System.out.print(miningRewardAdress);
            newBLock.setTransactionListInBlock((ArrayList<Transaction>) pendingTransactions.clone());
            blockchain.add(newBLock);
            pendingTransactions.clear();
            pendingTransactions.add(new Transaction("CoinBase", miningRewardAdress, miningReward));
        }
    }

    public void createTransaction(Transaction transaction) {
//        synchronized (obj) {
            pendingTransactions.add(transaction);
//        System.out.print(" T");
//        }
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
    public int getNumofTransactions() {
        int num = 0;
        if(blockchain.isEmpty() || blockchain == null) return 0;
        for (Block block : blockchain) {
            if (block.transactionListInBlock != null && !block.transactionListInBlock.isEmpty() && block.transactionListInBlock != null) {
                num += block.transactionListInBlock.size();
            }
        }
        return num;
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
                System.out.println(previousBlock.hash);
                System.out.println(currentBlock.previousHash);
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

    public int getNumofBlocks() {
        int num = 0;
        if(blockchain.isEmpty() || blockchain == null) return 0;
        for (Block block : blockchain) {
            num++;
        }
        return num;
    }
}
