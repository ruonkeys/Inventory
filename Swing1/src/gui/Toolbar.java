package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Toolbar extends JPanel implements ActionListener{
	private JButton helloButton;
	private JButton goodByeButton;
	private StringListener listener;
	public Toolbar()
	{
		helloButton=new JButton("hello");
		goodByeButton = new JButton("goodBye");
		helloButton.addActionListener(this);
		goodByeButton.addActionListener(this);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(helloButton);
		add(goodByeButton);
	}
	public void setStringListener(StringListener listener)
	{
		this.listener=listener;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton clicked=(JButton)e.getSource();
		if(listener!=null)
		{
		    if(clicked==helloButton)
		    {
		    	listener.textEmitted("hello\n");
		    }
		    else if(clicked==goodByeButton)
		    {
		    	listener.textEmitted("goodBye\n");
		    }
		}
	}
}
