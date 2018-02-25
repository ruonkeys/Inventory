package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class FormPanel extends JPanel {
	private JLabel nameLabel;
	private JLabel occupationLabel;
	private JTextField nameField;
	private JTextField occupationField;
	private JButton okBtn;
	private FormListener formListener;
	private JList ageList;
	private JComboBox empCombo;
	private JCheckBox citizenCheck;
	private JLabel taxLabel;
	private JTextField taxField;
	
	private JRadioButton maleRadio;
	private JRadioButton femaleRadio;
	private ButtonGroup genderGroup;
	public FormPanel()
	{
		Dimension dim=getPreferredSize();
		dim.width=250;
		setPreferredSize(dim);
		
		nameLabel = new JLabel("Name: ");
		occupationLabel = new JLabel("Occupation: ");
		nameField = new JTextField(10);
		occupationField = new JTextField(10);
		
		taxLabel = new JLabel("Tax id");
		taxField = new JTextField(10);
		citizenCheck = new JCheckBox();
		
		okBtn = new JButton("OK");
		
		//set Mnemonics
		okBtn.setMnemonic(KeyEvent.VK_O);
		nameLabel.setDisplayedMnemonic(KeyEvent.VK_N);
		nameLabel.setLabelFor(nameField);
		
		//gender group
		maleRadio=new JRadioButton("male");
		femaleRadio=new JRadioButton("female");
		genderGroup=new ButtonGroup();
		
		maleRadio.setSelected(true);
		maleRadio.setActionCommand("male");
		femaleRadio.setActionCommand("female");
		genderGroup.add(maleRadio);
		genderGroup.add(femaleRadio);
		
		
		//new list
		ageList = new JList();
		
		DefaultListModel ageModel = new DefaultListModel();
		ageModel.addElement(new AgeCategory(0,"under 18"));
		ageModel.addElement(new AgeCategory(1,"18 to 65"));
		ageModel.addElement(new AgeCategory(2,"65 or over"));
		
		ageList.setModel(ageModel);
		
		ageList.setPreferredSize(new Dimension(114, 66));//width, height
	    ageList.setBorder(BorderFactory.createEtchedBorder());
	    ageList.setSelectedIndex(1);
	    ageList.setBackground(Color.green);
	    
	    //new comboBox
	    empCombo = new JComboBox();
	    
	    DefaultComboBoxModel empModel = new DefaultComboBoxModel();
	    empModel.addElement("employed");
	    empModel.addElement("self-employed");
	    empModel.addElement("unemployed");
	    
	    empCombo.setModel(empModel);
	    
	    empCombo.setSelectedIndex(1);
	    empCombo.setEditable(true);
	    
	    //taxfield
	    taxLabel.setEnabled(false);
	    taxField.setEnabled(false);
	    
	    citizenCheck.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
			boolean isTicked = citizenCheck.isSelected();
			taxLabel.setEnabled(isTicked);
			taxField.setEnabled(isTicked);
			}
	    	
	    });
		
		
		okBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String name=nameField.getText();
				String occupation=occupationField.getText();
				
				AgeCategory ageCat=(AgeCategory)ageList.getSelectedValue();
				String empCat=(String) empCombo.getSelectedItem();
				String taxId=taxField.getText();
				boolean usCitizen=citizenCheck.isSelected();
				
				String gender=genderGroup.getSelection().getActionCommand();
				
				FormEvent ev=new FormEvent(this,name,occupation,ageCat.getId(),empCat,taxId,usCitizen,gender);
				
				if(formListener!=null)
				{
					formListener.formEventOccurred(ev);
				}
				
				//code to clear form fields to fill next record
				nameField.setText("");
				occupationField.setText("");
				ageList.setSelectedIndex(1);
				empCombo.setSelectedIndex(1);
				maleRadio.setSelected(true);
				citizenCheck.setSelected(false);
				taxLabel.setEnabled(false);
				taxField.setEnabled(false);
				taxField.setText("");
			}
		});
		Border innerBorder=BorderFactory.createTitledBorder("Add person");
		Border outerBorder=BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		layoutComponents();
	}
	public void layoutComponents()
	{
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
	
	//////////// next row ///////////////////////////////////
		
		gc.weightx = 1;
		gc.weighty = 0.1;
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);//top,left,bottom,right
		add(nameLabel, gc);
		
		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(nameField, gc);
		
		////////////next row ///////////////////////////////////
		
		gc.weightx = 1;
		gc.weighty = 0.1;
		
		gc.gridy++;
		gc.gridx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		gc.anchor = GridBagConstraints.LINE_END;
		add(occupationLabel, gc);
		
		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(occupationField, gc);
		
        ////////////next row ///////////////////////////////////
		
		gc.weightx=1;
		gc.weighty=0.2;
		
		gc.gridy++;
		//gc.gridy=2;
		gc.gridx=1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0,0,0,0);
		add(ageList, gc);
		
        ////////////next row ///////////////////////////////////
		
		gc.weightx=1;
		gc.weighty=0.2;
		gc.gridy++;
		
		gc.gridx=0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(0,0,0,5);
		add(new JLabel("employment:"), gc);
		
		gc.gridx=1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0,0,0,0);
		add(empCombo, gc);
		
        ////////////next row ///////////////////////////////////
		
		gc.weightx=1;
		gc.weighty=0.2;
		gc.gridy++;
		
		gc.gridx=0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(0,0,0,5);
		add(new JLabel("US Citizen: "), gc);
		
		gc.gridx=1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0,0,0,0);
		add(citizenCheck, gc);
		
        ////////////next row ///////////////////////////////////
		
		gc.weightx=1;
		gc.weighty=0.2;
		
		gc.gridy++;
		
		gc.gridx=0;
		gc.anchor=GridBagConstraints.FIRST_LINE_END;
		gc.insets=new Insets(0,0,0,5);
		add(taxLabel, gc);
		
		gc.gridx=1;
		gc.anchor=GridBagConstraints.FIRST_LINE_START;
		gc.insets=new Insets(0,0,0,0);
		add(taxField, gc);
		
        ////////////next row ///////////////////////////////////
		
		gc.weightx=1;
		gc.weighty=0.05;
		
		gc.gridy++;
		
		gc.gridx=0;
		gc.anchor=GridBagConstraints.LINE_END;//middle of row
		gc.insets=new Insets(0,0,0,5);
		add(new JLabel("Gender: "), gc);
		
		gc.gridx=1;
		gc.anchor=GridBagConstraints.FIRST_LINE_START;
		gc.insets=new Insets(0,0,0,0);
		add(maleRadio, gc);
		
        ////////////next row ///////////////////////////////////
		
		gc.weightx=1;
		gc.weighty=0.2;
		
		gc.gridy++;
		
		
		gc.gridx=1;
		gc.anchor=GridBagConstraints.FIRST_LINE_START;
		gc.insets=new Insets(0,0,0,0);
		add(femaleRadio, gc);
		
		////////////next row ///////////////////////////////////
		
		gc.weightx = 1;
		gc.weighty = 2.0;
		
		gc.gridy++;
		//gc.gridy = 3;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(okBtn, gc);
	}
	public void setFormListener(FormListener listener)
	{
		this.formListener=listener;
	}
}

class AgeCategory
{
	private String text;
	private int id;
	public AgeCategory( int id,String text)
	{
		this.text=text;
		this.id=id;
	}
	public String toString()
	{
		return text;
	}
	public int getId()
	{
		return id;
	}
}
