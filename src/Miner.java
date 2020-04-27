import java.util.Random;

public class Miner extends Thread {
    private Random random = new Random();
    public String name;
    private BlockChain blockChain;
    private int miningPower;


    public Miner(String name, BlockChain blockChain) {
        this.name = name;
        this.blockChain = blockChain;
        miningPower = 100;
    }

    public void minerStart() {
//        int i = 0;
        while (true) {
            blockChain.minePendingTransactions(name, miningPower);
//            System.out.print(this.name);
//            i++;
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