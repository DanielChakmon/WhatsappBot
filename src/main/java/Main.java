import javax.swing.*;

class Main extends JFrame {

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        this.setTitle("Whatsapp bot");
        this.setLayout(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        this.add(new MainPanel(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        this.setVisible(true);

    }


}