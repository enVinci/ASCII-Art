package pl.edu.pwr.pp;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class SourceChooser extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private String imagePath;
	private MainFrame mParent;
	private JRadioButton explorerRadio;
	private JRadioButton urlRadio;
	private ButtonGroup radios;
	private JButton wczytajPlikButton;
	private JButton okButton;
	private GroupLayout panel;
	private JButton anulujButton;
	private JPanel panel_1;
	private JTextField fileTextField;
	private JTextField urlTextField;

	/**
	 * Create the frame.
	 */
	// public void setParent(MainFrame parent) {
	// mParent = parent;
	// }
	public SourceChooser(MainFrame parent, boolean isHighQuality, String widthOption) {
		setTitle("Wczytaj obrazek");
		mParent = parent;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 240);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		radios = new ButtonGroup();

		wczytajPlikButton = new JButton("Przeglądaj");
		wczytajPlikButton.setEnabled(false);
		wczytajPlikButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wczytajPlikHandler();
			}
		});

		okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				okImageHandler();
			}
		});

		anulujButton = new JButton("Anuluj");
		anulujButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		panel_1 = new JPanel();

		panel = new GroupLayout(contentPane);
		panel.setHorizontalGroup(
				panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(panel.createSequentialGroup()
								.addGroup(panel.createParallelGroup(Alignment.LEADING)
										.addGroup(Alignment.TRAILING,
												panel.createSequentialGroup().addContainerGap()
														.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 461,
																Short.MAX_VALUE)
														.addGap(10).addComponent(wczytajPlikButton))
										.addGroup(panel.createSequentialGroup().addGap(24).addComponent(okButton)
												.addGap(18).addComponent(anulujButton)))
								.addContainerGap()));
		panel.setVerticalGroup(panel.createParallelGroup(Alignment.LEADING)
				.addGroup(panel.createSequentialGroup()
						.addGroup(
								panel.createParallelGroup(Alignment.LEADING)
										.addGroup(panel.createSequentialGroup().addGap(22)
												.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 135,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(panel.createParallelGroup(Alignment.BASELINE)
														.addComponent(okButton).addComponent(anulujButton)))
										.addGroup(panel.createSequentialGroup().addGap(57)
												.addComponent(wczytajPlikButton)))
						.addContainerGap(66, Short.MAX_VALUE)));
		panel_1.setLayout(null);

		urlRadio = new JRadioButton("Wczytanie z URL");
		urlRadio.setBounds(6, 64, 262, 23);
		panel_1.add(urlRadio);
		radios.add(urlRadio);

		explorerRadio = new JRadioButton("Wczytanie z pliku");
		explorerRadio.setBounds(6, 7, 262, 23);
		panel_1.add(explorerRadio);
		radios.add(explorerRadio);

		fileTextField = new JTextField();
		fileTextField.setEditable(false);
		fileTextField.setBounds(16, 37, 411, 20);
		panel_1.add(fileTextField);
		fileTextField.setColumns(10);

		urlTextField = new JTextField();
		urlTextField.setEditable(false);
		urlTextField.setBounds(16, 94, 411, 20);
		panel_1.add(urlTextField);
		urlTextField.setColumns(10);

		urlRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileTextField.setEditable(false);
				urlTextField.setEditable(true);
				wczytajPlikButton.setEnabled(false);
			}
		});

		explorerRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				urlTextField.setEditable(false);
				fileTextField.setEditable(true);
				wczytajPlikButton.setEnabled(true);
			}
		});
		contentPane.setLayout(panel);
	}

	private String getFileExtension(String filePath) {
		try {
			return filePath.substring(filePath.lastIndexOf(".") + 1);
		} catch (Exception e) {
			return "";
		}
	}

	public void clearTextFields() {
		urlTextField.setText("");
		fileTextField.setText("");
	}

	private void okImageHandler() {
		BufferedImage image = null;

		if (urlRadio.isSelected()) {
			URL url;
			try {
				url = new URL(urlTextField.getText());
				if (!getFileExtension(urlTextField.getText()).equals("pgm")) {
					try {
						image = ImageIO.read(url);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null,
								"Wystąpił błąd przy otwieraniu obrazka z URL\n" + e.getMessage());
					}
				} else {
					try {
						int[][] intensities = ImageFileReader.readPgmUrl(url);
						image = ImageConverter.intensitiesToImage(intensities);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Wystąpił błąd przy otwieraniu obrazka pgm z URL\n" + e.getMessage());
					}
				}
			} catch (MalformedURLException e) {
				JOptionPane.showMessageDialog(null, "Wystąpił błąd przy otwieraniu obrazka z URL\n" + e.getMessage());
			}

		} else {
			imagePath = fileTextField.getText();
			if (!getFileExtension(imagePath).equals("pgm")) {
				try {
					image = ImageIO.read(new File(imagePath));
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Wystąpił błąd przy otwieraniu obrazka\n" + e.getMessage());
				}
			} else {
				try {
					int[][] intensities = ImageFileReader.readPgmFile(imagePath);
					image = ImageConverter.intensitiesToImage(intensities);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Wystąpił błąd przy otwieraniu obrazka pgm\n" + e.getMessage());
				}
			}
		}
		if(image!=null) mParent.setImage(image);
		dispose();
	}

	private void wczytajPlikHandler() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "gif", "ppm", "png", "pgm");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(getParent());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			fileTextField.setText(chooser.getSelectedFile().getPath());

		}
	}
}
