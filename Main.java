import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;
import java.awt.Color;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
class Main extends JFrame implements ActionListener,KeyListener
{
	JMenu mf,me,ms,mv,mx;
	JMenuBar mb;
	JMenuItem mfsa,mfn,mfo,mfs,mfc;
	JMenuItem meu,mec,meo,mep,med;
	JMenuItem mxn,mxy;
	JMenuItem msf,msfr,msgoto;
	JMenuItem mvf;
	JCheckBoxMenuItem mvw;
	JTextArea jta;
	FileDialog fd;
	int opt;
	boolean saved;
	String[] choice;
	JLabel statusbar;
	int size;
	LinkedList <String> ls;
	//FontChooser fontdialog;
	int findnextlook;
	boolean typed;
	Main(String s)
	{
		super(s);
		opt=0;
		size=30;
		saved=false;
		findnextlook=0;
		typed=false;
		ls=new LinkedList<String>();
		ls.push("");
		//fontdialog=null;
		statusbar=new JLabel("Length : "+0+"               lines: "+1+"||               Ln 1,Col 1               ",JLabel.RIGHT);
		add(statusbar,BorderLayout.SOUTH);
		choice=new String[]{"SAVE","Don't SAVE","CANCEL"};
		//************************************************************************
		mvw=new JCheckBoxMenuItem("Word");
		mvf=new JMenuItem("Font...");
		
		mvw.addActionListener(this);
		mvf.addActionListener(this);
		
		mv=new JMenu("FORMAT");
		mv.setMnemonic(KeyEvent.VK_O);
		mv.add(mvw);
		mv.add(mvf);
		//***********************************************************************
		mfn=new JMenuItem("NEW",KeyEvent.VK_N);
		mfn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
		mfo=new JMenuItem("OPEN...",KeyEvent.VK_O);
		mfo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
		mfs=new JMenuItem("SAVE",KeyEvent.VK_S);
		mfs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
		mfsa=new JMenuItem("SAVE AS");
		mfc=new JMenuItem("CLOSE");
		
		mfo.addActionListener(this);
		mfn.addActionListener(this);
		mfs.addActionListener(this);
		mfsa.addActionListener(this);
		mfc.addActionListener(this);
		
		mf=new JMenu("FILE");
		mf.setMnemonic(KeyEvent.VK_F);
		mf.add(mfn);
		mf.add(mfo);
		mf.add(mfs);
		mf.add(mfsa);
		mf.add(mfc);
		
		//**************************************************
		
		meu=new JMenuItem("UNDO",KeyEvent.VK_U);
		meu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,ActionEvent.CTRL_MASK));
		mec=new JMenuItem("CUT");
		mec.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
		meo=new JMenuItem("COPY");
		meo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
		mep=new JMenuItem("PASTE");
		meo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK));
		med=new JMenuItem("DELETE");
		med.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0));
		
		
		meu.addActionListener(this);
		mec.addActionListener(this);
		meo.addActionListener(this);
		mep.addActionListener(this);
		med.addActionListener(this);
		
		me=new JMenu("EDIT");
		me.setMnemonic(KeyEvent.VK_E);
		me.add(meu);
		me.addSeparator();
		me.add(mec);
		me.add(meo);
		me.add(mep);
		me.addSeparator();
		me.add(med);
		
		/*********************************************8*/
		
		msf = new JMenuItem("FIND",KeyEvent.VK_F);
		msf.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,ActionEvent.CTRL_MASK));
		msfr = new JMenuItem("FIND AND REPLACE");
		msfr.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,ActionEvent.CTRL_MASK));
		msgoto = new JMenuItem("GO TO",KeyEvent.VK_G);
		msgoto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,ActionEvent.CTRL_MASK));
		
		msf.addActionListener(this);
		msfr.addActionListener(this);
		msgoto.addActionListener(this);
		
		ms = new JMenu("SEARCH");
		ms.setMnemonic(KeyEvent.VK_S);
		ms.add(msf);
		ms.add(msfr);
		ms.add(msgoto);
		
		/*********************************************8*/
		
		mxy=new JMenuItem("YES");
		mxn=new JMenuItem("NO");
		
		mxy.addActionListener(this);
		mxn.addActionListener(this);
		
		mx=new JMenu("EXIT");
		mx.setMnemonic(KeyEvent.VK_E);
		mx.add(mxy);
		mx.add(mxn);
		
		mb=new JMenuBar();
		mb.add(mf);
		mb.add(me);
		mb.add(ms);
		mb.add(mv);
		mb.add(mx);
		
		setJMenuBar(mb);//SET MENUBAR IN JFrame
		
		jta=new JTextArea("");
		// jta.setBackground(Color.LIGHT_GRAY);
		//jta.setBackground(Color.CYAN);
		JScrollPane scroll = new JScrollPane (jta,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scroll,BorderLayout.CENTER);//ADD SCROLL TO JTextArea
		//jta.setForeground(Color.RED);

		LineNumberingTextArea lineNumberingTextArea = new LineNumberingTextArea(jta);
		scroll.setRowHeaderView(lineNumberingTextArea);
		
		jta.getDocument().addDocumentListener(new DocumentListener()
		{
			@Override
			public void insertUpdate(DocumentEvent documentEvent)
			{
				lineNumberingTextArea.updateLineNumbers();
			}
			@Override
			public void removeUpdate(DocumentEvent documentEvent)
			{
				lineNumberingTextArea.updateLineNumbers();
			}

			@Override
			public void changedUpdate(DocumentEvent documentEvent)
			{
				lineNumberingTextArea.updateLineNumbers();
			}
		});
		
		jta.addCaretListener(new CaretListener()
		{
			public void caretUpdate(CaretEvent e)
			{
				JTextArea editarea=(JTextArea)e.getSource();
				int linenum=1;
				int columnnum=1;
				try 
				{
					int caretpos=editarea.getCaretPosition();
					linenum=editarea.getLineOfOffset(caretpos);
					columnnum = caretpos - editarea.getLineStartOffset(linenum);
					linenum++;
				}
				catch(Exception e1){}
				statusbar.setText("Length : "+jta.getText().length()+"               lines: "+jta.getLineCount()+"||               Ln "+linenum+", Col "+columnnum+"               ");
			}
		});

		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				if(jta.getText().length()==0)
					System.exit(0);
					if(!saved)
					{
						opt=JOptionPane.showOptionDialog(null,"Do you want to save or not","Notepad",JOptionPane.YES_OPTION,JOptionPane.INFORMATION_MESSAGE,null,choice,0);
						if(opt==0)
							saveAs();
						if(opt==1)
							System.exit(0);
						if(opt==2)
							setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
					}
					else 
						System.exit(0);
			}
		});
		//LOOK AND FEEL
		try 
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(this);
		
		//set font and size of 
		jta.setFont(new Font("Times New Roman", Font.PLAIN	, size));
		jta.addKeyListener(this);
		
		
		//SET SIZE 
		setBounds(100, 100, 700, 700);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
	
		setVisible(true);
	}
	/*************************************************************************************/
	public class LineNumberingTextArea extends JTextArea
	{
		private JTextArea textArea;
		public LineNumberingTextArea(JTextArea textArea)
		{
			this.textArea = textArea;
			setBackground(Color.LIGHT_GRAY);
			setEditable(false);
		}

		public void updateLineNumbers()
		{
			String lineNumbersText = getLineNumbersText();
			setFont(new Font("Times New Roman", Font.PLAIN	, size));
			setText(lineNumbersText);
		}

		private String getLineNumbersText()
		{
			int caretPosition = textArea.getDocument().getLength();
			Element root = textArea.getDocument().getDefaultRootElement();
			StringBuilder lineNumbersTextBuilder = new StringBuilder();
			lineNumbersTextBuilder.append("1").append(System.lineSeparator());

			for (int elementIndex = 2; elementIndex < root.getElementIndex(caretPosition) + 2; elementIndex++)
			{
				lineNumbersTextBuilder.append(elementIndex).append(System.lineSeparator());
			}
			return lineNumbersTextBuilder.toString();
		}
	}
	/*************************************************************************************/
	public void keyReleased(KeyEvent e)
	{}
	public void keyTyped(KeyEvent e)
	{}
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode()==KeyEvent.VK_OPEN_BRACKET)
		{
			int temp=jta.getCaretPosition();
			System.out.println("************"+jta.getCaretPosition());
			jta.insert("]",temp+1);
		}
		AWTKeyStroke ak = AWTKeyStroke.getAWTKeyStrokeForEvent(e);
        if(ak.equals(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ADD,InputEvent.CTRL_MASK)))
        {
			jta.setFont(new Font("Times New Roman", Font.PLAIN	, ++size));			
        }
		if(ak.equals(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_MINUS,InputEvent.CTRL_MASK)))
        {
			jta.setFont(new Font("Times New Roman", Font.PLAIN	, --size));			
        }
		if(e.getKeyCode()==KeyEvent.VK_ENTER || e.getKeyCode()==KeyEvent.VK_SPACE)
		{
			addInBuffer(jta.getText());
		}
		if(e.getKeyCode()==KeyEvent.VK_F5)
		{
		try 
		{
			parser(jta.getText().split(""));
		}
		catch(Exception e1){}
		}
	}
	public void parser(String[] lexims) throws Exception //5:45 pm 6 march 2018
	{
		int a=lexims.length;
		System.out.println("length:"+a);
		
		Highlighter highlighterk = jta.getHighlighter();
		Highlighter highlighterp = jta.getHighlighter();
		Highlighter highlighteri = jta.getHighlighter();
		Highlighter highlightert = jta.getHighlighter();
		Highlighter highlighterc = jta.getHighlighter();
		Highlighter highlighterq = jta.getHighlighter();
		HighlightPainter keyword = new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN);
		HighlightPainter punctuator = new DefaultHighlighter.DefaultHighlightPainter(Color.BLUE);
		HighlightPainter identifier = new DefaultHighlighter.DefaultHighlightPainter(Color.CYAN);
		HighlightPainter token = new DefaultHighlighter.DefaultHighlightPainter(Color.MAGENTA);
		HighlightPainter constant = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
		HighlightPainter quotation = new DefaultHighlighter.DefaultHighlightPainter(Color.ORANGE);
		
		int comments=0; 
		int i=0;
		int b=0;
		int loop=0;int loop2=0;int loop3=0,loop5=0;
		String s="";
		String[] keywords={"alignas","alignof","and","and_eq","asm","atomic_cancel","atomic_commit","atomic_noexcept","auto","bitand","bit||","bool","break","case","catch","char","char16_t","char32_t","class","compl","concept","const","constexpr","const_cast","continue","co_await","co_return","co_yield","decltype","default","delete","do","double","dynamic_cast","else","enum","explicit","export","extern","false","float","for","friend","goto","if","import","inline","int","long","module","mutable","namespace","new","noexcept","not","not_eq","nullptr","operator","or","or_eq","private","protected","public","register","reinterpret_cast","requires","return","short","signed","sileximseof","static","static_assert","static_cast","struct","switch","synchronileximsed","template","this","thread_local","throw","true","try","typedef","typeid","typename","union","unsigned","using","virtual","void","volatile","wchar_t","while","xor","xor_eq"};
		String[] punctuators={"[","]","(",")","{","}",",",";","=","#",":"};
		String[] identifires={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		String[] constants={"1","2","3","4","5","6","7","8","9","0"};
		//System.out.println(listoftoken.length);
		while(b<a)
		{
			if(lexims[b].equals("#") || lexims[b].equals("-") || lexims[b].equals("<") || lexims[b].equals("(") || lexims[b].equals("{") || lexims[b].equals("[") || lexims[b].equals(">") || lexims[b].equals(")") || lexims[b].equals("}") || lexims[b].equals("]") || lexims[b].equals(" ")|| lexims[b].equals(";") || lexims[b].equals("\n")  || lexims[b].equals("\t") || lexims[b].equals("+") || lexims[b].equals(",") || lexims[b].equals("=") || lexims[b].equals(":") || lexims[b].equals("/"))//
			{	
				//listoftoken[i]=lexims[b];
				if(lexims[b].equals("\t") || lexims[b].equals("\n") || lexims[b].equals(" "))
				{
					b++;
					continue;
				}
				if(lexims[b].equals("+"))
				{
					highlightert.addHighlight(b-1,b,token);
				}
				else if(lexims[b].equals("-"))
				{
					highlightert.addHighlight(b-1,b,token);
						b++;
						continue;
				}
				else if(lexims[b].equals("<"))
				{
					highlightert.addHighlight(b-1,b,token);
						b++;
						continue;
				}
				else if(lexims[b].equals(">"))
				{
					highlightert.addHighlight(b-1,b,token);
						b++;
						continue;
				}
				else if(lexims[b].equals("<"))
				{
					highlightert.addHighlight(b-1,b,token);
						b++;
						continue;
				}
				else if(lexims[b].equals(">"))
				{
					highlightert.addHighlight(b-1,b,token);
						b++;
						continue;
				}
				else if(lexims[b].equals("!"))
				{
					highlightert.addHighlight(b-1,b,token);
						b++;
						continue;
				}
				i++;
			}
			if(lexims[b].equals("\""))
			{
				int j=b+1;
				
				String st="";
				while(!lexims[j].equals("\""))
				{
					st+=lexims[j];
					j++;
				}
				highlighterq.addHighlight(b,j+1,quotation);
				b=j;
			}
			for(int loop1=0;loop1<identifires.length;loop1++)
			{
				if(lexims[b].equals(identifires[loop1]))
				{
					int j=b;
					String st2="";
					while(!(lexims[j].equals("#") || lexims[j].equals("-") || lexims[j].equals("<") || lexims[j].equals("(") || lexims[j].equals("{") || lexims[j].equals("[") || lexims[j].equals(">") || lexims[j].equals(")") || lexims[j].equals("}") || lexims[j].equals("]") || lexims[j].equals(" ")|| lexims[j].equals(";") || lexims[j].equals("\n")  || lexims[j].equals("\t") || lexims[j].equals("+") || lexims[j].equals(",") || lexims[j].equals("=") || lexims[j].equals(":") || lexims[j].equals("/")))
					{
						st2+=lexims[j];
						j++;
					}
					int temp=0;
					for(int vk=0;vk<keywords.length;vk++)
					{
						if(keywords[vk].equals(st2))
						{
							highlighterk.addHighlight(b,j,keyword);
							temp=1;
						}
					}
					// }
					if(temp==0)
						highlighteri.addHighlight(b,j,identifier); 
					b=j;
					continue;
				}
			}
			for(int loop4=0;loop4<constants.length;loop4++)
			{
				if(lexims[b].equals(constants[loop4]))
				{
					int j=b;
					String st2="";
					while(!(lexims[j].equals("#") || lexims[j].equals("-") || lexims[j].equals("<") || lexims[j].equals("(") || lexims[j].equals("{") || lexims[j].equals("[") || lexims[j].equals(">") || lexims[j].equals(")") || lexims[j].equals("}") || lexims[j].equals("]") || lexims[j].equals(" ")|| lexims[j].equals(";") || lexims[j].equals("\n")  || lexims[j].equals("\t") || lexims[j].equals("+") || lexims[j].equals(",") || lexims[j].equals("=") || lexims[j].equals(":") || lexims[j].equals("/")))
					{
						st2+=lexims[j];
						j++;
					}
					highlighterc.addHighlight(b,j,constant); 
					b=j;
					continue;
				}
			}
			b++;
		}
	}
	public void addInBuffer(String s)
	{
		ls.push(s);
	}
	public void actionPerformed(ActionEvent e)
	{
		JMenuItem m=(JMenuItem)e.getSource();
		if(m==mxy)
		{
			opt=JOptionPane.showOptionDialog(null,"Do you want to save or not","Notepad",JOptionPane.YES_OPTION,JOptionPane.INFORMATION_MESSAGE,null,choice,0);
			if(opt==0)
				saveAs();
			if(opt==1)
				System.exit(0);
		}
		else
			System.out.println(m.getLabel());
		if(m==mfn)//NEW 
		{
			// jta.setText("");
			Main b=new Main("Untiled Notepad");
		}
		if(m==mfo)//OPEN
		{
			setTitle(open());
		}
		if(m==mfc)
		{
			System.exit(0);
		}
		if(m==mfsa)
		{
			saveAs();
		}
		if(m==mfs)
		{
			if(!saved)
				saveAs();
			else
			{
				save();
			}
		}
		if(m==meu)
		{
			undo();
		}
		if(m==mec)
		{
			jta.cut();
		}
		if(m==meo)
		{
			jta.copy();
		}
		if(m==mep)
		{
			jta.paste();
		}
		if(m==med)
		{
			delete();
		}
		if(m==msf)
		{
			find();
		}
		if(m==msfr)
		{
			findAndReplace();
		}
		if(m==msgoto)
		{
			goTo();
		}
		if(m==mvw)
		{
			JCheckBoxMenuItem temp=(JCheckBoxMenuItem)e.getSource();
			jta.setLineWrap(temp.isSelected());
		}
		if(m==mvf)
		{
			/*JFontChooser fd = new JFontChooser(this,jta.getFont());
			fd.show();
			if(fd.getReturnStatus() == fd.RET_OK)
			{
				jta.setFont(fd.getFont());
			}
			fd.dispose();*/
		}
	}
	void saveAs()
	{
		saved=true;
		JFrame temp=new JFrame();
		//*****************************************
		try
		{
			fd=new FileDialog(temp,"FILE SAVE",FileDialog.SAVE);
			fd.setVisible(true);
			//FileWriter fw=new FileWriter();
			File f=new File(fd.getDirectory()+fd.getFile());
			FileWriter fw=new FileWriter(f);
			String str=jta.getText();
			int i=0,n=str.length();
			int val=0;
			while(i<n)
			{
				val=(int)str.charAt(i);
				fw.write(val);
				i++;
			}
			fw.close();
		}
		catch(Exception e){}
		setTitle(""+fd.getDirectory()+fd.getFile()+" Notepad");
		//*****************************************
	}
	String open()//throws Exception
	{
		JFrame temp=new JFrame();
		try
		{
			fd=new FileDialog(temp,"FIlE OPEN",FileDialog.LOAD);
			fd.setVisible(true);
			File f=new File(fd.getDirectory()+fd.getFile());
			String str="";
			FileReader fr=new FileReader(f);
			int val=0;
			while(true)
			{
				val=fr.read();
				if(val==-1)
					break;
				str=str+(char)val;
			}
			jta.setText(str);
			// flag=0;
			fr.close();
		}
		catch(Exception e)
		{}
		return ""+fd.getDirectory()+fd.getFile()+" Notepad" ;
	}
	void save()
	{
		try
		{
			File f=new File(fd.getDirectory()+fd.getFile());
			FileWriter fw=new FileWriter(f);
			String str=jta.getText();
			int i=0,n=str.length();
			int val=0;
			while(i<n)
			{
				val=(int)str.charAt(i);
				fw.write(val);
				i++;
			}
			fw.close();
		}
		catch(Exception e){}
	}
	void copy()
	{
		jta.copy();
	}
	void paste()
	{
		jta.paste();
	}
	void delete()
	{
		jta.setText(jta.getText().replace(jta.getSelectedText(),""));
	}
	void undo()
	{
		String temp="";
		if(ls.size()!=0)
			temp=ls.pop();
		jta.setText(temp);
	}
	void find()
	{
		msf.setEnabled(false);
		JFrame a=new JFrame("FIND");
		JLabel l1=new JLabel("FIND");
		JTextField textfield = new JTextField();
		JButton next = new JButton("Next");
			
		l1.setBounds(50,50,200,50);
		textfield.setBounds(300,50,200,50);
		next.setBounds(175,150,200,50);
			
		a.add(l1);
		a.add(textfield);
		a.add(next);
			
		a.setLayout(null);
		a.setSize(550,250);
		a.setVisible(true);
		
		a.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				findnextlook=0;
				msf.setEnabled(true);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
		next.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				findnextlook=nextFind(findnextlook,textfield.getText().split(""),jta.getText().split(""));
			}
		});	
	}
	int nextFind(int loop,String[] key,String[] str)
	{
		while(loop<str.length)
		{
			if(str[loop].equals(key[0]))
			{
				int j=1;
				int l=loop+1;
				int size=1;
				while(j<key.length)
				{
					if(str[l].equals(key[j]))
					{
						size++;
					}
					j++;
					l++;
				}
				if(j==size)
				{
					jta.select(loop,l);
					loop=l+1;
					return loop+1;
				}
			}
			loop++;
		}
		return loop;
	}
	void findAndReplace()
	{
		msfr.setEnabled(false);
		JFrame a=new JFrame("FIND AND REPLACE");
		JLabel l1=new JLabel("FIND");
		JLabel l2=new JLabel("REPLACE");
		JTextField textfield1 = new JTextField();
		JTextField textfield2 = new JTextField();
		JButton next = new JButton("Next");
		JButton replace = new JButton("Replace");
			
		l1.setBounds(50,50,200,50);
		l2.setBounds(50,150,200,50);
		textfield1.setBounds(300,50,200,50);
		textfield2.setBounds(300,150,200,50);
		next.setBounds(50,250,200,50);
		replace.setBounds(300,250,200,50);
			
		a.add(l1);
		a.add(l2);
		a.add(textfield1);
		a.add(textfield2);
		a.add(next);
		a.add(replace);
			
		a.setLayout(null);
		a.setSize(550,350);
		a.setVisible(true);
		
		a.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				findnextlook=0;
				msfr.setEnabled(true);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
		next.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				findnextlook=nextFind(findnextlook,textfield1.getText().split(""),jta.getText().split(""));
			}
		});
		replace.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jta.setText(jta.getText().replace(jta.getSelectedText(),textfield2.getText()));
			}
		});	
	}
	void goTo()
	{
		int lineNumber=0;
		try
		{
			lineNumber=jta.getLineOfOffset(jta.getCaretPosition())+1;
			String tempStr=JOptionPane.showInputDialog(null,"Enter Line Number:",""+lineNumber);
			if(tempStr==null)
			{
				return;
			}
			lineNumber=Integer.parseInt(tempStr);
			jta.setCaretPosition(jta.getLineStartOffset(lineNumber-1));
			}catch(Exception e){}
	}
	public static void main(String[] args) throws Exception
	{
		Main a=new Main("Untiled NotePad");
	}
}