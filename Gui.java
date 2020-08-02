

import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class Gui implements ActionListener{

	private JFrame frame;
	private int count=0;
	BlockChain bc = new BlockChain();
	ArrayList <Wallet> Users=new ArrayList<Wallet>();
	JButton submit = new JButton("Submit");
	JButton report = new JButton("View Reports");
	JTextArea textArea = new JTextArea();
	JComboBox sen = new JComboBox();
	JComboBox rec = new JComboBox();
	JLabel lblNewLabel_5 = new JLabel("Name :");
	JTextField username = new JTextField();
	JButton create = new JButton("Create");
	JLabel lblNewLabel_6 = new JLabel("New User");
	JButton crtBlck = new JButton("Create block");
	JComboBox vwUser = new JComboBox();
	
	DefaultComboBoxModel dms = new DefaultComboBoxModel();
	DefaultComboBoxModel dmr = new DefaultComboBoxModel();
	DefaultComboBoxModel dmv = new DefaultComboBoxModel();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("unchecked")
	private void initialize() {
		username.setBounds(188, 404, 198, 20);
		username.setColumns(10);
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 204));
		frame.setBounds(100, 100, 650, 489);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Blockchain medical system");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(139, 41, 406, 31);
		frame.getContentPane().add(lblNewLabel);
		
		
		textArea.setBounds(209, 187, 264, 138);
		frame.getContentPane().add(textArea);
		
		JLabel lblNewLabel_1 = new JLabel("Report :");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Serif", Font.BOLD, 15));
		lblNewLabel_1.setBounds(113, 185, 72, 25);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Receiver :");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Serif", Font.BOLD, 15));
		lblNewLabel_2.setBounds(371, 111, 72, 31);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/doc.png")).getImage();
		lblNewLabel_3.setIcon(new ImageIcon(img));
		lblNewLabel_3.setBounds(64, 11, 82, 105);
		frame.getContentPane().add(lblNewLabel_3);
		
		
		submit.addActionListener(this);
		submit.setForeground(new Color(0, 0, 0));
		submit.setBackground(new Color(255, 255, 255));
		submit.setBounds(504, 265, 89, 31);
		frame.getContentPane().add(submit);
		report.setBackground(new Color(255, 255, 255));
		
		
		report.addActionListener(this);
		report.setBounds(26, 339, 111, 31);
		frame.getContentPane().add(report);
		
		JLabel lblNewLabel_4 = new JLabel("Sender :");
		lblNewLabel_4.setFont(new Font("Serif", Font.BOLD, 15));
		lblNewLabel_4.setBounds(106, 116, 72, 21);
		frame.getContentPane().add(lblNewLabel_4);
		

		sen.setBounds(188, 117, 60, 22);
		frame.getContentPane().add(sen);
		

		rec.setBounds(453, 117, 60, 22);
		frame.getContentPane().add(rec);
		lblNewLabel_5.setFont(new Font("Serif", Font.BOLD, 15));
		lblNewLabel_5.setBounds(118, 400, 60, 25);
		
		frame.getContentPane().add(lblNewLabel_5);
		
		frame.getContentPane().add(username);
		create.setBounds(456, 396, 89, 36);
		
		frame.getContentPane().add(create);
		lblNewLabel_6.setFont(new Font("Serif", Font.BOLD, 16));
		lblNewLabel_6.setBounds(281, 356, 161, 14);
		
		frame.getContentPane().add(lblNewLabel_6);
		
		create.addActionListener(this);
		Wallet w = new Wallet("Apollo");
		Users.add(w);
		dms.addElement("Apollo");
		dmr.addElement("Apollo");
		dmv.addElement("Apollo");
		sen.setModel(dms);
		rec.setModel(dmr);
		vwUser.setModel(dmv);
		
		vwUser.setBounds(26, 269, 111, 22);
		frame.getContentPane().add(vwUser);
		
		crtBlck.setBounds(504, 339, 111, 31);
		frame.getContentPane().add(crtBlck);
		crtBlck.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==submit)
		{
			if(((String)rec.getSelectedItem()).equals((String)sen.getSelectedItem()))
			{
				JOptionPane.showMessageDialog(frame,"Couldn't send report,Sender and Receiver have to be different!");
			}
			else{
			PublicKey r=Users.get(0).publickey;
			Wallet s=Users.get(0);
			Wallet receiver = null;
			for(Wallet user : Users)
			{
				String str=(String)rec.getSelectedItem();
				if(user.name.equals(str)){
					r=user.publickey;
					receiver=user;
				}
			}
			for(Wallet user : Users)
			{
				String str=(String)sen.getSelectedItem();
				if(user.name.equals(str)) s=user;
			}
			int rounds = 10;
		    int flag = 0;
		    for(int i = 0;i< rounds;i++) {
		    	if(receiver.verifyid(s)) {
		          flag = flag +1;
		        }
		    }
		    if(flag == rounds) {
		        Transaction t = s.sendData(r,(String)sen.getSelectedItem(),(String)rec.getSelectedItem(),textArea.getText());
		        ++count;
		        bc.undoneTransaction.add(t);
		        if(count%3==0)
		        {
		          bc.createBlock();
		          Data.writeJSON(bc,"hello.json");
		        }
		        textArea.setText("");
		        JOptionPane.showMessageDialog(frame,"Report sent!");
		   }
		   else {
		        JOptionPane.showMessageDialog(frame,"Couldn't verify id");
		   }
		}	
		}
		if(e.getSource()==report)
		{
			for(Wallet user : Users)
			{
				if(user.name.equals((String)vwUser.getSelectedItem()))
				{
					if(bc.undoneTransaction.size()>0)
					{
						bc.createBlock();
						Data.writeJSON(bc,"hello.json");
						count=0;
					}
 					String pr=bc.viewUser(user.publickey,user.name);
					JOptionPane.showMessageDialog(frame,pr);
				}
			}
		}
		if(e.getSource()==create)
		{
			if(username.getText().equals("")){
				JOptionPane.showMessageDialog(frame,"Name is empty, Please fill it!");
			}
			else{
			Wallet w=new Wallet(username.getText());
			Users.add(w);
			dms.addElement((String)username.getText());
			dmr.addElement((String)username.getText());
			dmv.addElement((String)username.getText());
			sen.setModel(dms);
			rec.setModel(dmr);
			vwUser.setModel(dmv);
			dms.setSelectedItem(username.getText());
			dmr.setSelectedItem(username.getText());
			dmv.setSelectedItem(username.getText());
			username.setText("");
			JOptionPane.showMessageDialog(frame,"User created!");
			}
		}
		if(e.getSource()==crtBlck)
		{
			bc.createBlock();
			Data.writeJSON(bc,"hello.json");
			count=0;
		}
	}
}
