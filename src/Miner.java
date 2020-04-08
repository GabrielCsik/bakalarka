public class Miner extends Thread {

    public String name;
    private BlockChain blockChain;

    public Miner(String name, BlockChain blockChain) {
        this.name = name;
        this.blockChain = blockChain;
    }

    public void minerStart() {
        int i = 0;
        while (i < 4) {
            blockChain.minePendingTransactions(name);
//            System.out.print(this.name);
            i++;
        }
    }


    public String getMinerName() {
        return name;
    }

    @Override
    public void run() {
        this.minerStart();
    }
}