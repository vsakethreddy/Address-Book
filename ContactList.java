import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
class PersonInfo {
    String name;
    String address;
    String phoneNumber;
    PersonInfo(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
    void display() {
        JOptionPane.showMessageDialog(null, "Name: " + name + "\nAddress: " + address + "\nPhone no: " + phoneNumber);
    }
}
class ContactBook {
    ArrayList List;
    ContactBook() {
        List = new ArrayList();
        loadPersons();
    }
	void show(String s)
	{
		JOptionPane.showMessageDialog(null,s);
	}
    void addPerson()
    {
    	JPanel panel=new JPanel(new GridLayout(5,3));
    	JTextField tname=new JTextField(10);
    	JTextField tadd=new JTextField(10);
    	JTextField tpnum=new JTextField(10);
    	JLabel lname=new JLabel("Enter Name");
    	JLabel ladd=new JLabel("Enter Address");
    	JLabel lpnum=new JLabel("Enter Phone Number");
    	panel.add(lname);
    	panel.add(tname);
    	panel.add(ladd);
    	panel.add(tadd);
    	panel.add(lpnum);
    	panel.add(tpnum);
    	JOptionPane.showMessageDialog(null,panel);
    	String name,add,pNum;
    	name=tname.getText();
    	add=tadd.getText();
    	pNum=tpnum.getText();
        if(!name.equals("")&& !add.equals("") && !pNum.equals(""))
        {
        PersonInfo p = new PersonInfo(name, add, pNum);
        List.add(p);savePersons();}
       }
    void edit()
    {
        JPanel panel=new JPanel(new GridLayout(5,3));
    	JTextField tname=new JTextField(10);
        JLabel lname=new JLabel("Enter Name");
        panel.add(lname);
        panel.add(tname);
        JOptionPane.showMessageDialog(null,panel);
        String name=tname.getText();
        deletePerson(name,0);
        JPanel panel1=new JPanel(new GridLayout(5,3));
    	JTextField tname1=new JTextField(10);
    	JTextField tadd1=new JTextField(10);
    	JTextField tpnum1=new JTextField(10);
    	JLabel lname1=new JLabel("Enter New Name");
    	JLabel ladd1=new JLabel("Enter New Address");
    	JLabel lpnum1=new JLabel("Enter New Phone Number");
    	panel1.add(lname1);
    	panel1.add(tname1);
    	panel1.add(ladd1);
    	panel1.add(tadd1);
    	panel1.add(lpnum1);
    	panel1.add(tpnum1);
    	JOptionPane.showMessageDialog(null,panel1);
    	String name1,add,pNum;
    	name1=tname1.getText();
    	add=tadd1.getText();
    	pNum=tpnum1.getText();
        if(!name1.equals("")&& !add.equals("") && !pNum.equals(""))
        {
        PersonInfo p = new PersonInfo(name, add, pNum);
        List.add(p);savePersons();}
       }
    void searchPerson(String n) {
        new frame3(List,n);
    }
    void showAll()
    {
       	new frame2(List);
    }
    void deletePerson(String n,int x) {
        int i;
        int f=0;
        for (i = 0; i < List.size(); i++) {
            PersonInfo p = (PersonInfo) List.get(i);
            if (n.toLowerCase().equals(p.name.toLowerCase())) {
                List.remove(i);f=1;i--;
            }
        }
        if(f==1 && x==1)
        {
        	show("Contact deleted");
        }
        else if(x==1)
        {
        	show("Contact  not found");
        }
        savePersons();
    }
    void savePersons() {
        try {
            PersonInfo p;
            PersonInfo q;
            String line;
            FileWriter fw = new FileWriter("List.txt");
            PrintWriter pw = new PrintWriter(fw);
            for (int i = 0; i < List.size(); i++) {
                p = (PersonInfo) List.get(i);
                
                for(int k=i;k<List.size();k++)
                {
                    q = (PersonInfo)List.get(k);
                    if((p.name.toLowerCase()).compareTo((q.name).toLowerCase())>0)
                    {
                     String temp=p.name;
                     p.name=q.name;
                     q.name=temp;
                     String temp1=p.address;
                     p.address=q.address;
                     q.address=temp1;
                     String temp2=p.phoneNumber;
                     p.phoneNumber=q.phoneNumber;
                     q.phoneNumber=temp2;
                    }
            }
            line = p.name + "," + p.address + "," + p.phoneNumber;
            pw.println(line);
        }
            
            pw.flush();
            pw.close();
            fw.close();
        } catch(IOException ioEx) {
            System.out.println(ioEx);
        }
    }
    
    void loadPersons() {
        String tokens[] = null;
        String name, add, ph;
        try {
            FileReader fr = new FileReader("List.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                tokens = line.split(",");
                name = tokens[0];
                add = tokens[1];
                ph = tokens[2];
                PersonInfo p = new PersonInfo(name, add, ph);
                List.add(p);
                line = br.readLine();
            }
            br.close();
            fr.close();
        } catch(IOException ioEx) {
            System.out.println(ioEx);
        }
    }
}
class frame2 extends JFrame
{
	public frame2(ArrayList List)
	{
            String[][] person=new String[List.size()][3];
            for (int i = 0; i < List.size(); i++) 
            {
                PersonInfo p;
                p = (PersonInfo) List.get(i);
                person[i][0]=p.name;
                person[i][1]=p.address;
                person[i][2]=p.phoneNumber;
            }	
            String[] heading={"NAME","ADDRESS","PHONE NUMBER"};
            JTable  table=new JTable(person,heading);
            add(new JScrollPane(table));
            setLayout(new FlowLayout(FlowLayout.CENTER,10,15));
            setVisible(true);
            setSize(500,530);		
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
  
   
}
class frame3 extends JFrame
{
	public frame3(ArrayList List,String n)
	{
        int i,j=0;
		String[][] person=new String[List.size()][3];
        for ( i = 0; i < List.size(); i++) 
        {
        	PersonInfo p;
            p = (PersonInfo) List.get(i);
            if(IsSubstring((n.toLowerCase()),(p.name).toLowerCase()) ||  IsSubstring((n.toLowerCase()),(p.address).toLowerCase()) ||
             IsSubstring((n.toLowerCase()),(p.phoneNumber).toLowerCase()) ){
            person[j][0]=p.name;
            person[j][1]=p.address;
            person[j][2]=p.phoneNumber;
            j++;}
        }	
		String[] heading={"NAME","ADDRESS","PHONE NUMBER"};
		JTable  table=new JTable(person,heading);add(new JScrollPane(table));
        setLayout(new FlowLayout(FlowLayout.CENTER,10,15));
		setVisible(true);
		setSize(500,530);		
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
        if(i==List.size() && j==0)
        {
        	JOptionPane.showMessageDialog(null,"Contact is not found");
        }
    }
    
    boolean IsSubstring(String s1, String s2)
    {
        int M = s1.length();
        int N = s2.length();
        for (int i = 0; i <= N - M; i++) 
        {
            int j;
            for (j = 0; j < M; j++)
                if (s2.charAt(i + j)
                    != s1.charAt(j))
                    break;
            if (j == M)
                return true;
        }
        return false;
    }
}
class ContactList
{
    public static void main(String args[])
	{
		new frame1();
	}
}
class frame1 extends JFrame
{
	public frame1()
	{
        ContactBook cb= new ContactBook();
		setLayout(new FlowLayout(FlowLayout.CENTER,10,15));
		setBackground(Color.black);
        setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(300,200));
		JButton addContact=new JButton("Add Contact");
		JButton search=new JButton("Search Contact");
		JButton delete=new JButton("Delete Contact");
        JButton edit=new JButton("Edit Contact");
		JButton showAll=new JButton("Show All Contacts");
        addContact.setBackground(Color.red);
        search.setBackground(Color.orange);
        delete.setBackground(Color.green);
        showAll.setBackground(Color.yellow);
		add(addContact);
		add(search);
		add(delete);
        add(edit);
		add(showAll);
		addContact.addActionListener(e->
		{
			cb.addPerson();
		});
		search.addActionListener(e->
		{
            String s = JOptionPane.showInputDialog("Enter name to search:");
        	cb.searchPerson(s);
		});
		delete.addActionListener(e->
		{
			String s = JOptionPane.showInputDialog("Enter name to delete:");
            cb.deletePerson(s,1);
        });
        edit.addActionListener(e->
		{
			cb.edit();
		});
		showAll.addActionListener(e->
		{
			cb.showAll();
			dispose();
		});
	}
}