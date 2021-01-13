package UI;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class GeneralForm extends JFrame{
	ImageIcon img = new ImageIcon("/Users/jaehyeokchoi/Desktop/문제들/2017 식권발매/DataFiles/main.jpg");
	JLabel image = new JLabel(img);
	JButton btn[] = new JButton[5];
	public GeneralForm() {
		setTitle("Manage");
		JPanel a = new JPanel();
		makeBtn();
		for(int i=0;i<btn.length;i++) {
			a.add(btn[i]);
		}
		JPanel b = new JPanel();
		b.add(image);
		add(a,BorderLayout.NORTH);
		add(b,BorderLayout.CENTER);
		setSize(800,500);
		setVisible(true);
		
	}
	public void makeBtn() {
		String name[] = {"register menu","manage menu","lookup payment","menu charts","exit"};
		for(int i=0;i<name.length;i++) {
			btn[i] = new JButton(name[i]);
			btn[i].addActionListener(new MyActionListener());
		}
	}
	class MyActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			switch(command) {
			case "register menu":
				dispose();
				new AddMenu();
				break;
			case "manage menu":
				new Manage();
				break;
			case "lookup payment":
				new Lookup();
				break;
			case "menu charts":
				new Chart();
				dispose();
				break;
			case "exit":
				dispose();
				break;
				
			}
		}
		
	}
//	public static void main(String[] args) {
//		new GeneralForm();
//	}
}
