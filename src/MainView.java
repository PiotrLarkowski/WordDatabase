import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MainView extends JFrame implements ActionListener {
    JButton exitButton, applyWordButton;
    ArrayList<String> allSavedWords = new ArrayList();
    JTextField WordTextField;

    JLabel middleLabel;
    public MainView() {
        setSize(800, 300);
        setTitle("Main Window");
        setLayout(null);
        setLocationRelativeTo(null);

        exitButton = new JButton("Exit / Wyjscie");
        exitButton.setBounds(130, 130, 200, 40);
        add(exitButton);
        exitButton.addActionListener(this);

        applyWordButton = new JButton("Apply / Zatwierdz");
        applyWordButton.setBounds(360, 130, 200, 40);
        add(applyWordButton);
        applyWordButton.addActionListener(this);

        middleLabel = new JLabel("Enter a word / Wpisz slowo");
        middleLabel.setBounds(270, 30, 300, 60);
        add(middleLabel);

        WordTextField = new JTextField("");
        WordTextField.setBounds(200, 80, 300, 30);
        add(WordTextField);
    }

    public static void main(String[] args) {
        MainView window = new MainView();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public void ReadFile(){
        allSavedWords = new ArrayList<>();
        try {
            File myObj = new File("C:/words.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String word = myReader.nextLine();
                allSavedWords.add(word);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this,"Create file to save words / Stworz plik do zapisu slow C:/words.txt");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object event = e.getSource();
        if (event == exitButton) {
            dispose();
        } else if (event == applyWordButton) {
            String word = WordTextField.getText();
            ReadFile();
            if(checkWordIsFine(word)){
                addWordToFile(word);
            }else{
                JOptionPane.showMessageDialog(this,"Error with saving the word / Błąd zapisu słowa");
            }
        }
    }

    private boolean checkWordIsFine(String word) {
        if(word.length() == 3 || word.length() == 4 || word.length() == 5 || word.length() == 6 || word.length() == 7 ||word.length() == 13) {
            return checkWordIsntInFileAlready(word);
        }else{
            JOptionPane.showMessageDialog(this, "Word have wrong length / Slowo ma zla dlugosc");
            return false;
        }
    }

    private boolean checkWordIsntInFileAlready(String word) {
        boolean wordOk = false;
        if (allSavedWords.size() == 0) {
            return true;
        }else {
            for (int i = 0; i < allSavedWords.size(); i++) {
                if (allSavedWords.get(i).equals(word.toLowerCase())) {
                    JOptionPane.showMessageDialog(this, "Created word already exist / Podane slowo juz istnieje");
                    wordOk = false;
                    break;
                } else {
                    wordOk = true;
                }
            }
        }
        return wordOk;
    }

    private void addWordToFile(String word) {
        try {
            FileWriter fileWriter = new FileWriter("C:/words.txt");
            allSavedWords.add(word.toLowerCase());
            for (int j = 0; j < allSavedWords.size(); j++) {
                fileWriter.write(allSavedWords.get(j) + "\r\n");
            }
            fileWriter.close();
            JOptionPane.showMessageDialog(this, "Word has been added / Slowo zostało dodane");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}