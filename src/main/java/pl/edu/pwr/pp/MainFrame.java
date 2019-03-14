package pl.edu.pwr.pp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JFileChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.image.BufferedImage;
import pl.edu.pwr.pp.ImagePanel;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ImagePanel imagePanel;
	private JPanel panel = new JPanel();
	private GroupLayout mainPanel;
	private SourceChooser sourceChooser;
	private JLabel lblJakosc;
	private JLabel lblSzerokosc;
	private JLabel lblTlo;
	private JLabel lblRozmiarCzcionki;
	private JComboBox<String> comboBoxSzerokosc;
	private JComboBox<String> comboBoxJakosc;
	private JComboBox<String> comboBoxTlo;
	private JComboBox<Integer> comboBoxRozmiarCzcionki;
	private JButton wczytajObrazButton;
	private JButton pokazObrazButton;
	private JButton pokazSzaryButton;
	private JButton pokazAsciiArtButton;
	private JButton zapiszDoPlikuTxtButton;
	private JButton zapiszDoPlikuPngButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 673, 584);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);

		lblJakosc = new JLabel("Jakość");
		lblSzerokosc = new JLabel("Szerokość");
		lblTlo = new JLabel("Tło");
		lblRozmiarCzcionki = new JLabel("Rozmiar Czcionki");

		comboBoxJakosc = new JComboBox<String>();
		comboBoxJakosc.addItem("Niska");
		comboBoxJakosc.addItem("Wysoka");
		comboBoxJakosc.addItemListener(e -> {
			if (e.getItem() == "Wysoka")
				imagePanel.setQuality(true);
			else if (e.getItem() == "Niska")
				imagePanel.setQuality(false);
		});

		comboBoxSzerokosc = new JComboBox<String>();
		comboBoxSzerokosc.addItem("80 znaków");
		comboBoxSzerokosc.addItem("160 znaków");
		comboBoxSzerokosc.addItem("Szerokość ekranu");
		comboBoxSzerokosc.addItem("Szerokość okna");
		comboBoxSzerokosc.addItem("Oryginalna");
		comboBoxSzerokosc.addItemListener(e -> {
			switch (e.getItem().toString()) {
			case "80 znaków":
				imagePanel.setWidth(80);
				break;
			case "160 znaków":
				imagePanel.setWidth(160);
				break;
			case "Szerokość ekranu":
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				imagePanel.setWidth((int) screenSize.getWidth());
				break;
			case "Szerokość okna":
				imagePanel.setWidth((int) imagePanel.getWidth());
				break;
			case "Oryginalna":
				if(imagePanel.getOrginalImage()!=null) {
					imagePanel.setWidth(imagePanel.getOrginalImage().getWidth());
				}
				break;
			}
		});

		comboBoxTlo = new JComboBox<String>();
		comboBoxTlo.addItem("Białe");
		comboBoxTlo.addItem("Czarne");
		comboBoxTlo.addItemListener(e -> {
			switch (e.getItem().toString()) {
			case "Białe":
				imagePanel.setBgColor(Color.WHITE);
				break;
			case "Czarne":
				imagePanel.setBgColor(Color.BLACK);
				break;
			}
		});

		comboBoxRozmiarCzcionki = new JComboBox<Integer>(new Integer[]{8,9,10,11,12,14,16,18,20,22,24,26,28,36,48});
		
		wczytajObrazButton = new JButton("Wczytaj obraz ");
		wczytajObrazButton.addActionListener(e -> {
			wczytajHandler();
		});

		pokazObrazButton = new JButton("Pokaż obrazek");
		pokazObrazButton.addActionListener(e -> {
			imagePanel.paintOrginalImage();
		});

		pokazSzaryButton = new JButton("Pokaż szary");
		pokazSzaryButton.addActionListener(e -> {
			imagePanel.paintGrayscaleImage();
		});

		pokazAsciiArtButton = new JButton("Pokaż AsciiArt");
		pokazAsciiArtButton.addActionListener(e -> {
			imagePanel.paintAscii();
		});

		zapiszDoPlikuTxtButton = new JButton("Zapisz do pliku txt");
		zapiszDoPlikuTxtButton.addActionListener(e -> {
			zapiszDoPlikuTxtHandler();
		});

		zapiszDoPlikuPngButton = new JButton("Zapisz do pliku png");
		zapiszDoPlikuPngButton.addActionListener(e -> {
			zapiszDoPlikuPngHandler();
		});

		mainPanel = new GroupLayout(panel);
		mainPanel
				.setHorizontalGroup(
						mainPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(
										mainPanel.createSequentialGroup().addContainerGap().addGroup(
												mainPanel
														.createParallelGroup(Alignment.LEADING).addGroup(
																mainPanel
																		.createSequentialGroup()
																		.addGroup(mainPanel
																				.createParallelGroup(Alignment.LEADING)
																				.addComponent(lblTlo)
																				.addGroup(
																						mainPanel.createParallelGroup(
																								Alignment.TRAILING,
																								false)
																								.addComponent(
																										pokazAsciiArtButton,
																										Alignment.LEADING,
																										GroupLayout.DEFAULT_SIZE,
																										GroupLayout.DEFAULT_SIZE,
																										Short.MAX_VALUE)
																								.addComponent(
																										pokazSzaryButton,
																										Alignment.LEADING,
																										GroupLayout.DEFAULT_SIZE,
																										GroupLayout.DEFAULT_SIZE,
																										Short.MAX_VALUE)
																								.addComponent(
																										pokazObrazButton,
																										Alignment.LEADING,
																										GroupLayout.DEFAULT_SIZE,
																										GroupLayout.DEFAULT_SIZE,
																										Short.MAX_VALUE))
																				.addGroup(mainPanel
																						.createParallelGroup(
																								Alignment.TRAILING,
																								false)
																						.addComponent(lblJakosc,
																								Alignment.LEADING)
																						.addComponent(lblSzerokosc,
																								Alignment.LEADING)
																						.addComponent(
																								wczytajObrazButton,
																								Alignment.LEADING,
																								GroupLayout.DEFAULT_SIZE,
																								116, Short.MAX_VALUE)
																						.addComponent(comboBoxSzerokosc,
																								Alignment.LEADING,
																								0,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(comboBoxTlo,
																								Alignment.LEADING,
																								0,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(comboBoxJakosc,
																								Alignment.LEADING, 0,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)))
																		.addContainerGap(64, Short.MAX_VALUE))
														.addGroup(Alignment.TRAILING,
																mainPanel.createSequentialGroup()
																		.addGroup(mainPanel
																				.createParallelGroup(Alignment.TRAILING)
																				.addComponent(comboBoxRozmiarCzcionki,
																						Alignment.LEADING, 0, 123,
																						Short.MAX_VALUE)
																				.addComponent(zapiszDoPlikuPngButton,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE))
																		.addGap(64))
														.addGroup(mainPanel.createSequentialGroup()
																.addComponent(lblRozmiarCzcionki,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.addGap(86))
														.addGroup(mainPanel.createSequentialGroup()
																.addComponent(zapiszDoPlikuTxtButton,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.addGap(68)))));
		mainPanel.setVerticalGroup(mainPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(mainPanel.createSequentialGroup().addContainerGap().addComponent(wczytajObrazButton)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblJakosc).addGap(1)
						.addComponent(comboBoxJakosc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblSzerokosc).addGap(1)
						.addComponent(comboBoxSzerokosc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblTlo).addGap(1)
						.addComponent(comboBoxTlo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(32).addComponent(pokazObrazButton).addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(pokazSzaryButton).addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(pokazAsciiArtButton).addGap(44).addComponent(zapiszDoPlikuTxtButton).addGap(18)
						.addComponent(lblRozmiarCzcionki).addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(comboBoxRozmiarCzcionki, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(zapiszDoPlikuPngButton)
						.addContainerGap(82, Short.MAX_VALUE)));
		panel.setLayout(mainPanel);
		setupBgImage();
	}

	private void setupBgImage() {
		imagePanel = new ImagePanel();
		getContentPane().add(imagePanel);
		imagePanel.setLayout(null);
	}

	public void setImage(BufferedImage image) {
		imagePanel.setOrginalImage(image);
		if (comboBoxSzerokosc.getSelectedItem().toString() == "Oryginalna") {
			imagePanel.setWidth(image.getWidth());
		}
	}

	private void wczytajHandler() {
		sourceChooser = new SourceChooser(this, comboBoxJakosc.getSelectedItem().equals("Wysoka"),
				comboBoxSzerokosc.getSelectedItem().toString());
		sourceChooser.clearTextFields();
		sourceChooser.setVisible(true);
	}

	private void zapiszDoPlikuTxtHandler() {
		if (imagePanel.getOrginalImage() != null) {
			JFileChooser chooser = new JFileChooser();
			int returnVal = chooser.showSaveDialog(getParent());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				imagePanel.saveAsciiartTxt(chooser.getSelectedFile().getPath());
			}
		} else {
			JOptionPane.showMessageDialog(null, "Brak obrazka do zapisania jako AsciiArt");
		}
	}

	private void zapiszDoPlikuPngHandler() {
		if (imagePanel.getOrginalImage() != null) {
			JFileChooser chooser = new JFileChooser();
			int returnVal = chooser.showSaveDialog(getParent());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				imagePanel.saveAsciiartPng((int)comboBoxRozmiarCzcionki.getSelectedItem(),chooser.getSelectedFile().getPath());
			}
		} else {
			JOptionPane.showMessageDialog(null, "Brak obrazka do zapisania jako AsciiArt");
		}
	}
}
