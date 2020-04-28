import java.security.Security;
import java.util.*;

public class UserInterface{
    BlockChain blockChain = new BlockChain();
    Scanner reader = new Scanner(System.in);

    char[] chars = "abcdefghijklmnopqrstuvwxyz123456789".toCharArray();
    StringBuilder sb = new StringBuilder(20);
    Random random = new Random();
    char c;

    public List<User> createUsers(int numberOfUsers){
        ArrayList<User> users = new ArrayList<>();
        for(int i = 1; i<= numberOfUsers; i++){
            sb.setLength(0);
            for (int x = 1; x <= 5; x++) {
                c = chars[random.nextInt(chars.length)];
                sb.append(c);
            }
            if(users.contains(sb.toString())){ continue;}
            users.add(new User(sb.toString(),blockChain));
        }
        return users;
    }
    public List<Miner> createMiners(int numberOfUsers){
        ArrayList<Miner> miners = new ArrayList<>();
        for(int i = 1; i<= numberOfUsers; i++){
            sb.setLength(0);
            for (int x = 1; x <= 5; x++) {
                c = chars[random.nextInt(chars.length)];
                sb.append(c);
            }
            if(miners.contains(sb.toString())){ continue;}
            miners.add(new Miner(sb.toString(),blockChain));
        }
        return miners;
    }

    public BlockChain getBlockChain() {
        return blockChain;
    }
    public void play(){
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        System.out.println("Enter number of users: ");
        int numOfusers = reader.nextInt();
//        System.out.println(numOfusers);
        ArrayList<User> users = (ArrayList<User>) createUsers(numOfusers);

        System.out.println("Enter number of miners: ");
        int numOfminers = reader.nextInt();
        System.out.println("Enter difficulty: ");
        blockChain.setDifficulty(reader.nextInt());
//        System.out.println(numOfminers);
        ArrayList<Miner> miners = (ArrayList<Miner>) createMiners(numOfminers);

        users.stream().forEach(p -> p.setUserList(users));
        for (User user : users) { user.start(); }
        System.out.println("Started users");
        for (Miner miner : miners) { miner.start(); }
        System.out.println("Started miners");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        miners.stream().forEach(Thread::stop);
//        users.stream().forEach(Thread::stop);
//        System.out.println(BlockChain.isChainValid(blockChain));
        System.out.println( "Blocks: "+blockChain.getNumofBlocks());
        System.out.println( "Transactions: "+blockChain.getNumofTransactions());
//        users.stream().forEach(p-> System.out.println(p.getUserName() + " " + blockChain.getBalanceOfAddress(p.getPublicKey())));
//        System.out.println();
//        miners.stream().forEach(p-> System.out.println(p.getMinerName() + " " + blockChain.getBalanceOfAddress(p.getPublicKey())));
        System.exit(0);
        //Setup Bouncey castle as a Security Provider

        //Create the new wallets
//        User user1 = new User("gabi", blockChain);
//        User user2 = new User("peto", blockChain);
//        //Test public and private keys
//        System.out.println("Private and public keys:");
//        System.out.println(user1.getPublicKey());
//        System.out.println(user2.getPublicKey());
//        //Create a test transaction from WalletA to walletB
//        Transaction transaction = new Transaction(user1.getPublicKey(), user2.getPublicKey(), 5, user1.getPrivateKey());
//        Transaction transaction2 = new Transaction(user2.getPublicKey(), 5);
//        //Verify the signature works and verify it from the public key
////        System.out.println("Is signature verified");
////        System.out.println(transaction.verifiySignature());
//        System.out.println(transaction2.toString());
    }

}
