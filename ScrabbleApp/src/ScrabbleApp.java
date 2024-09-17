import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

public class ScrabbleApp extends JFrame{

	private JTextField txtTiles;
	private JTextArea txtOutput;
	private JButton btnGenerate;
	private static final String PLACEHOLDER_TEXT = "Write your 7 letters here";
	private static final String OUTPUT_DEFAULT_TEXT = "Your scramble will appear here :)";
	private JLabel lblNewLabel;

	/**
	 * Create the frame.
	 */
	public ScrabbleApp() {
		JFrame frame = new JFrame();
		getContentPane().setBackground(new Color(153, 153, 204));
		setTitle("Scrabble App");
		setBounds(100, 100, 600, 400); // Set frame size
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Initialize and set the size of the input field
		txtTiles = new JTextField(PLACEHOLDER_TEXT);
		txtTiles.setBackground(new Color(204, 204, 255));
		txtTiles.setBounds(120, 55, 351, 40);
		txtTiles.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		txtTiles.setPreferredSize(new Dimension(500, 40)); // Larger input field

		// Add focus listener to handle placeholder text
		txtTiles.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent e) {
				if (txtTiles.getText().equals(PLACEHOLDER_TEXT)) {
					txtTiles.setText("");
					txtTiles.setForeground(Color.BLACK);
				}
			}

			public void focusLost(FocusEvent e) {
				if (txtTiles.getText().isEmpty()) {
					txtTiles.setText(PLACEHOLDER_TEXT);
					txtTiles.setForeground(Color.GRAY);
				}
			}
		});
		getContentPane().setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(45, 107, 504, 146);
		getContentPane().add(scrollPane_1);

		// Initialize and set the size of the output area
		txtOutput = new JTextArea();
		txtOutput.setBackground(new Color(204, 204, 255));
		scrollPane_1.setViewportView(txtOutput);
		txtOutput.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		txtOutput.setEditable(false);
		txtOutput.setLineWrap(true);
		txtOutput.setWrapStyleWord(true);
		txtOutput.setText(OUTPUT_DEFAULT_TEXT); // Default text

		// Set placeholder text color
		txtTiles.setForeground(Color.GRAY);
		getContentPane().add(txtTiles);

		// Initialize and set the size of the button
		btnGenerate = new JButton("Click here to generate your scrambles");
		btnGenerate.setBounds(85, 270, 420, 40);
		btnGenerate.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		btnGenerate.setPreferredSize(new Dimension(300, 30));
		getContentPane().add(btnGenerate);

		lblNewLabel = new JLabel("SCRABBLE APP");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		lblNewLabel.setBounds(185, 15, 217, 30);
		getContentPane().add(lblNewLabel);

		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateScrambles();
			}
		});
	}

	// Generates and displays all scrambles of the tiles.

	private void generateScrambles() {
		String input = txtTiles.getText().trim();

		// Debugging statement to check the input for testing
		System.out.println("Input: " + input);

		if (input.equals(PLACEHOLDER_TEXT) || input.isEmpty()) {
			txtOutput.setText("Error: Input cannot be blank.");
			return;
		}

		if (input.length() > 7) {
			txtOutput.setText("Error: No more than 7 tiles can be entered.");
			return;
		}
		if (!input.matches("[a-zA-Z]*")) {
			txtOutput.setText("Error: Only alphabetic characters are allowed.");
			return;
		}

		List<String> scrambles = scramble(input);

		// Debugging statement to check scrambles list
		System.out.println("Scrambles: " + scrambles);

		if (scrambles.isEmpty()) {
			txtOutput.setText("No scrambles available.");
		} else {
			txtOutput.setText("Scrambles:\n" + String.join(", ", scrambles));
		}
	}

	// Generates scrambles of a string.

	private List<String> scramble(String str) {
		List<String> results = new ArrayList<>();
		scrambleHelper("", str, results);
		return results;
	}

	private void scrambleHelper(String prefix, String str, List<String> results) {
		int n = str.length();
		if (n == 0) {
			results.add(prefix);
		} else {
			for (int i = 0; i < n; i++) {
				String newPrefix = prefix + str.charAt(i);
				String remaining = str.substring(0, i) + str.substring(i + 1);
				scrambleHelper(newPrefix, remaining, results);
			}
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				ScrabbleApp frame = new ScrabbleApp();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	/*
	 * ***************************************TESTING *********************************************************
	 * 
	 * TEST 1: no entry --> OUTPUT: Error: Input cannot be blank.
	 * 
	 * TEST 2: too many characters --> INPUT: hamburger --> OUTPUT: Error: No more than 7 tiles can be entered.
	 * 
	 * TEST 3: functionality --> INPUT: taco --> OUTPUT: Scrambles: taco, taoc, tcao, tcoa, toac, toca, atco, 
	 * atoc, acto, acot, aotc, aoct, ctao, ctoa, cato, caot, cota, coat, otac, otca, oatc, oact, octa, ocat
	 * 
	 * ********************************************************************************************************
	 */
}

