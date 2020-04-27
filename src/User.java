import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class User extends Thread {
    private String name;
    private BlockChain blockChain;
    private List<User> userList;
    private Random random = new Random();

    public User(String name, BlockChain blockChain) {
        this.name = name;
        this.blockChain = blockChain;
    }

    public String getUserName() {
        return name;
    }

    public void createTransaction() {
//        int i = 0;
        while (true) {
            try {
                this.sleep(random.nextInt(10000)+1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            blockChain.createTransaction(new Transaction(this.getUserName(), userList.get(random.nextInt(userList.size()) + 0).getUserName(), random.nextInt(200)));
//            i++;
        }
    }

    @Override
    public void run() {
        this.createTransaction();
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<User> getUserList() {
        return userList;
    }
}
