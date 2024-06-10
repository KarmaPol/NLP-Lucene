package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import lombok.SneakyThrows;
import searchEngine.SearchEngine;
import searchEngine.SearchResponse;

public class GUI {
    private static final int ITEMS_PER_PAGE = 3;
    private int currentPage = 0;
    private SearchEngine searchEngine;
    private List<SearchResponse> searchResponses;
    private String query;

    public GUI(SearchEngine searchEngine) {
        this.searchEngine = searchEngine;
    }

    public void display() {

        // Create frame
        JFrame frame = new JFrame("Search GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 750);
        frame.setLayout(null);

        // Create main title
        JLabel mainTitle = new JLabel("Find Movie Dialog");
        mainTitle.setFont(new Font("Arial", Font.BOLD, 24));
        mainTitle.setBounds(50, 10, 300, 30);
        frame.add(mainTitle);

        // Create search label
        JLabel label = new JLabel("Enter text to search:");
        label.setBounds(50, 50, 150, 30);
        frame.add(label);

        // Create text field with placeholder
        JTextField textField = new JTextField();
        textField.setBounds(50, 90, 200, 30);
        textField.setForeground(Color.GRAY);
        textField.setText("Enter the dialog");
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals("Enter the dialog")) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText("Enter the dialog");
                }
            }
        });
        frame.add(textField);

        // Create search button
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(260, 90, 80, 30);
        frame.add(searchButton);

        // Create result title
        JLabel resultTitle = new JLabel("");
        resultTitle.setFont(new Font("Arial", Font.BOLD, 18));
        resultTitle.setBounds(50, 140, 700, 30);
        frame.add(resultTitle);

        // Create result panel
        JPanel resultPanel = new JPanel();
        resultPanel.setBounds(50, 170, 700, 440);
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        frame.add(resultPanel);

        // Create navigation buttons
        JButton prevButton = new JButton("Prev");
        prevButton.setBounds(50, 640, 100, 30);
        prevButton.setVisible(false);
        frame.add(prevButton);

        JButton nextButton = new JButton("Next");
        nextButton.setBounds(160, 640, 100, 30);
        nextButton.setVisible(false);
        frame.add(nextButton);

        // Search button click event listener
        searchButton.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPage = 0;
                resultTitle.setText("Result");
                query = textField.getText();
                searchResponses = searchEngine.search(query);

                displayResults(resultPanel, currentPage);
                prevButton.setVisible(searchResponses.size() > ITEMS_PER_PAGE);
                nextButton.setVisible(searchResponses.size() > ITEMS_PER_PAGE);
            }
        });

        // Prev button click event listener
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage > 0) {
                    currentPage--;
                    displayResults(resultPanel, currentPage);
                }
            }
        });

        // Next button click event listener
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((currentPage + 1) * ITEMS_PER_PAGE < searchResponses.size()) {
                    currentPage++;
                    displayResults(resultPanel, currentPage);
                }
            }
        });

        // Display the frame
        frame.setVisible(true);
    }

    private void displayResults(JPanel resultPanel, int page) {
        resultPanel.removeAll();
        int startIndex = page * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, searchResponses.size());

        for (int i = startIndex; i < endIndex; i++) {
            SearchResponse searchResponse = searchResponses.get(i);

            int index = searchResponse.scriptIndex();
            String actor = searchResponse.character();
            String dialog = searchResponse.text();
            String movieTitle = searchResponse.movieName();
            String writer = searchResponse.movieAuthor();

            JPanel resultItemPanel = new JPanel();
            resultItemPanel.setLayout(new BorderLayout());
            resultItemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Poster panel
            JLabel posterLabel = new JLabel();
            posterLabel.setPreferredSize(new Dimension(100, 150));
            resultItemPanel.add(posterLabel, BorderLayout.WEST);

            // Text panel
            JPanel textPanel = new JPanel();
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
            JLabel indexAndDialogLabel = new JLabel("<html>( " + index + " ) " + actor + " : " + dialog + "</html>");
            JLabel movieTitleLabel = new JLabel("<html><font color='blue'>" + movieTitle + "</font></html>");
            JLabel writerLabel = new JLabel("<html>-<font color='gray'>" + writer + "</font></html>");
            textPanel.add(indexAndDialogLabel);
            textPanel.add(movieTitleLabel);
            textPanel.add(writerLabel);
            resultItemPanel.add(textPanel, BorderLayout.CENTER);

            // Fetch and set the movie poster image
            new Thread(() -> {
                try {
                    // Fetch the movie poster image
                    String imageUrl = fetchPosterUrl(movieTitle);
                    if (imageUrl != null) {
                        BufferedImage image = ImageIO.read(new URL(imageUrl));
                        ImageIcon icon = new ImageIcon(image);
                        posterLabel.setIcon(icon);
                    } else {
                        posterLabel.setText("<html><center>No<br>Poster</center></html>");
                        posterLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        posterLabel.setVerticalAlignment(SwingConstants.CENTER);
                        posterLabel.setForeground(Color.RED);
                        posterLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }).start();

            resultPanel.add(resultItemPanel);
        }

        // Add empty panels to fill the remaining space
        int remainingItems = ITEMS_PER_PAGE - (endIndex - startIndex);
        for (int i = 0; i < remainingItems; i++) {
            JPanel emptyPanel = new JPanel();
            emptyPanel.setPreferredSize(new Dimension(resultPanel.getWidth(), 150));
            resultPanel.add(emptyPanel);
        }

        resultPanel.revalidate();
        resultPanel.repaint();
    }

    private String fetchPosterUrl(String movieTitle) {
        String[] extensions = {"jpg", "jpeg", "png"};
        for (String extension : extensions) {
            String imageUrl = "https://imsdb.com/posters/" + movieTitle.replaceAll(" ", "%20") + "." + extension;
            if (isImageUrlValid(imageUrl)) {
                return imageUrl;
            }
        }
        return null;
    }

    private boolean isImageUrlValid(String imageUrl) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(imageUrl).openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            return false;
        }
    }
}
