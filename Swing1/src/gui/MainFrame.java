package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import controller.Controller;

public class MainFrame extends JFrame {
	private TextPanel textPanel;
	// private JButton btn;
	private Toolbar toolbar;
	private FormPanel formPanel;
	private JFileChooser fileChooser;
	private Controller controller;
	private TablePanel tablePanel;
	
	public MainFrame() {
		super("hello World");

		setLayout(new BorderLayout());
		textPanel = new TextPanel();
		toolbar = new Toolbar();
		formPanel = new FormPanel();
		tablePanel = new TablePanel();
	
		controller = new Controller();
		
		tablePanel.setData(controller.getPeople());
		
		tablePanel.setPersonTableListener(new PersonTableListener(){
			public void rowDeleted(int row)
			{
				controller.removePerson(row);
			}
		});
	
		fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new PersonFileFilter());
		
		setJMenuBar(createMenuBar());
		/*
		 * btn = new JButton("Click Me");
		 * 
		 * btn.addActionListener(new ActionListener(){ public void
		 * actionPerformed(ActionEvent e) { textPanel.appendText("hello\n"); }
		 * });
		 */
		toolbar.setStringListener(new StringListener() {
			public void textEmitted(String text) {
				textPanel.appendText(text);
			}
		});
		formPanel.setFormListener(new FormListener() {
			public void formEventOccurred(FormEvent e) {
				/*String name = e.getName();
				String occupation = e.getOccupation();
				int ageCat = e.getAgeCategory();
				String empCat = e.getEmploymentCategory();
				String gender = e.getGender();
				textPanel.appendText(name + ":" + occupation + ":" + ageCat
						+ "," + empCat + "\n");*/
				controller.addPerson(e);
				tablePanel.refresh();
			}
		});
		add(toolbar, BorderLayout.NORTH);
		//add(textPanel, BorderLayout.CENTER);
		add(tablePanel, BorderLayout.CENTER);
		// add(btn, BorderLayout.SOUTH);
		add(formPanel, BorderLayout.WEST);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 500);
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("file");
		JMenuItem importDataItem = new JMenuItem("import data...");
		JMenuItem exportDataItem = new JMenuItem("export data...");
		JMenuItem exitItem = new JMenuItem("exit");
		fileMenu.add(importDataItem);
		fileMenu.add(exportDataItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);

		JMenu windowMenu = new JMenu("window");
		JMenu showForm = new JMenu("show");
		final JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem(
				"person form");
		showForm.add(showFormItem);
		windowMenu.add(showForm);
		showFormItem.setSelected(true);

		menuBar.add(fileMenu);
		menuBar.add(windowMenu);

		showFormItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				formPanel.setVisible(showFormItem.isSelected());
			}
		});

		fileMenu.setMnemonic(KeyEvent.VK_F);
		exitItem.setMnemonic(KeyEvent.VK_E);

		windowMenu.setMnemonic(KeyEvent.VK_A);

		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.CTRL_MASK));
		
		importDataItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(fileChooser.showOpenDialog(MainFrame.this)==JFileChooser.APPROVE_OPTION)
				{
					try {
						controller.loadFromFile(fileChooser.getSelectedFile());
						tablePanel.refresh();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(MainFrame.this,
								"Could not load data from file.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		exportDataItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(fileChooser.showSaveDialog(MainFrame.this)==JFileChooser.APPROVE_OPTION)
				{
					try {
						controller.saveToFile(fileChooser.getSelectedFile());
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(MainFrame.this,
								"Could not save data to file.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*String msg=JOptionPane.showInputDialog(MainFrame.this,
						"enter username","Enter",
						JOptionPane.OK_OPTION|JOptionPane.INFORMATION_MESSAGE);
				
				System.out.println("msg");*/
				
				int action = JOptionPane.showConfirmDialog(MainFrame.this,
						"Do you really want to exit", "Confirm Dialog",
						JOptionPane.OK_CANCEL_OPTION);
				
				if(action==JOptionPane.OK_OPTION)
				{
				System.exit(0);
				}
			}
		});

		return menuBar;
	}
}
