import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

//metamorphosis
public class KeyEventDemo extends JFrame
		implements KeyListener,
		ActionListener
{
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final String FILE_HEADER = "";
	private static final String FILE_HEADER1 = "";
	/*rainbows occur when sunlight shines through raindrops in the sky. the raindrops act like tiny prisms, splitting the sunlight into different 
colors.*/ 
	/*"User,H.period,DD.period.t,UD.period.t,H.t,DD.t.i,UD.t.i,H.i,DD.i.e,UD.i.e," +
			"H.e,DD.e.five,UD.e.five,H.five,DD.five.Shift.r,UD.five.Shift.r,H.Shift.r,DD.Shift.r.o,UD.Shift.r.o,H.o," +
			"DD.o.a,UD.o.a,H.a,DD.a.n,UD.a.n,H.n,DD.n.l,UD.n.l,H.l,DD.l.Return,UD.l.Return,H.Return";*/
	

	private static boolean flag = true;
	JTextArea displayArea;
	JTextArea para;
	JTextField typingArea;

	static final String newline = System.getProperty("line.separator");

	public static void main(String[] args) {

        /* Use an appropriate Look and Feel */
		try {
			 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
        /* Turn off metal's use of bold fonts */
		UIManager.put("swing.boldMetal", Boolean.FALSE);

		//Schedule a job for event dispatch thread:
		//creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from the
	 * event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		//Create and set up the window.
		KeyEventDemo frame = new KeyEventDemo("Keystroke Logging Application");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//JTextArea area=new JTextArea("TYPE THE WORD - metamorphosis");  
        //area.setBounds(10,30, 200,200);  
        //frame.add(area);  
        frame.setSize(300,300); 
		//Set up the content pane.
		frame.addComponentsToPane();


		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	private void addComponentsToPane()  {

		JButton button = new JButton("Clear");
		button.addActionListener(this);
		button.setFont(new Font("Arial", Font.BOLD, 28));
		button.setPreferredSize(new Dimension(100, 50));
		// Sample paragraph for demonstration
		String paragraphText = "                                           Type the below sentence                   \n\n"
        + "rainbows occur when sunlight shines through raindrops in the sky.";
		
		para = new JTextArea(paragraphText);
		para.setLineWrap(true); 
		para.setWrapStyleWord(true); 
		para.setEditable(false);
		para.setFont(new Font("Arial", Font.PLAIN, 40)); 
		para.setBackground(new Color(225, 245, 254));
		
		// Set the preferred size of the JTextArea
		para.setPreferredSize(new Dimension(500, 500));
		typingArea = new JTextField(20);
		typingArea.setFont(new Font("Arial", Font.BOLD, 28)); 
		typingArea.addKeyListener(this);
		
		JScrollPane scrollPaneForTypingArea = new JScrollPane(typingArea);
		scrollPaneForTypingArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneForTypingArea.setPreferredSize(new Dimension(50, 350));
	
		displayArea = new JTextArea();
		displayArea.setEditable(false);
		displayArea.setFont(new Font("Arial", Font.PLAIN, 14));
		displayArea.setBackground(new Color(225, 245, 254));
		
		JScrollPane scroll = new JScrollPane(displayArea);
		scroll.setPreferredSize(new Dimension(50, 350));
		
		JPanel verticalPanel = new JPanel();
		verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
	
		// Add components to the vertical panel
		verticalPanel.add(para); 
		verticalPanel.add(scrollPaneForTypingArea); 
		verticalPanel.add(scroll);    
	
		// Add the vertical panel to the content pane
		getContentPane().add(verticalPanel, BorderLayout.CENTER);
		getContentPane().add(button, BorderLayout.PAGE_END);
	}

	KeyDataStore store;
    int pressIndex = 0;
    int releaseIndex = 0;

	public KeyEventDemo(String name) {
	    super(name);
        this.store = new KeyDataStore();
	}

	/** Handle the key typed event from the text field. */
	public void keyTyped(KeyEvent e) {


	}

	
	/*
	private static final String FILE_HEADER = generateFileHeader(KeyEvent e);
	private static String generateFileHeader(KeyEvent e) {
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append("User,");

        key = this.store.getKey(e);
        for (Field field : fields) {
            String fieldName = field.getName();
            headerBuilder.append(fieldName).append(",");
        }
        return headerBuilder.toString();
    }*/

	/** Handle the key pressed event from the text field. */
	Integer id = 1;
	public void keyPressed(KeyEvent e) {
		displayArea.setText("");
		
		String currentText = typingArea.getText();
		List<Character> typedCharacters = new ArrayList<Character>();
		Character keyChar = e.getKeyChar();
		typedCharacters.add(keyChar);
		generateCsv1(typedCharacters,id);
			if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
				//String newText = currentText.substring(0, currentText.length() - 1);
        		//typingArea.setText(newText);
				//typingArea.setText("");
				this.store.removeLastEntry();
				releaseIndex--;
				pressIndex--;
			}



		if(e.getKeyCode() == KeyEvent.VK_ENTER){

			String str = typingArea.getText();
			System.out.println(str);
			//if( str.equals(".tie5Roanl")){
				
				List<Double> keyStoreParams = this.store.process();    //stores strokes
				
				List<Long> keyStoreParamsInNano = this.store.processInNano();
				System.out.println(keyStoreParamsInNano.toString());
				System.out.println(keyStoreParams.toString());
				System.out.println(keyStoreParams.size());
				this.store.initialize();
				pressIndex = 0;
				typingArea.setText("");
				generateCsv(keyStoreParams,"");
				generateCsvInNano(keyStoreParamsInNano,"");
				id++;
				return;
//in a faraway galaxy was an adult llama that jumped away.
//			//}else{
//				displayArea.setText(str);
//				displayArea.setText("Please insert correct password");
//				typingArea.setText("");
//				this.store.removeContainsOfMap();
//				releaseIndex=0;
//				pressIndex = 0;
//				return;
//
//			}


        }

		TypedKeyObject typedKeyObject = new TypedKeyObject();
		typedKeyObject.pressTime = System.currentTimeMillis();
		typedKeyObject.pressTimeinNano = System.nanoTime();
		this.store.storeTypedObject(pressIndex,typedKeyObject);
		pressIndex++;
		
	}

	/** Handle the key released event from the text field. */
	public void keyReleased(KeyEvent e) {
		//displayInfo(e, "KEY RELEASED: ");
		String currentText = typingArea.getText();
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
		    releaseIndex = 0;
		    return;
        }
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
			//String newText = currentText.substring(0, currentText.length() - 1);
        	//typingArea.setText(newText);
			//typingArea.setText("");
			this.store.removeLastEntry();
			//pressIndex = 0;
			releaseIndex--;
			pressIndex--;
			
		}
		if(this.store.getKey(releaseIndex)!=null) {
          this.store.getKey(releaseIndex).releaseTime = System.currentTimeMillis();
          this.store.getKey(releaseIndex).releaseTimeinNano = System.nanoTime();

			releaseIndex++;
        }
	}

	/**
	 * Handle the clear button click.
	 */
	public void actionPerformed(ActionEvent e) {
		//Clear the text components.
		displayArea.setText("");
		typingArea.setText("");
		//Return the focus to the typing area.
		typingArea.requestFocusInWindow();
	}


	/**
	 *  This function create csv which stores your keystroke eachtime after
	 *  you enter valid paragraph.
	 *  Function is used data collection. This data will be used for
	 *  training and tesing machine learning model.
	 * @param keyStoreParams : list of keyhold,kyup,keydown timings
	 */
	public void generateCsv(List<Double> keyStoreParams,String userName) {

		FileWriter fileWriter = null;
		try {

			String filename = userName+"Keystrokes.csv";
			File file = new File(filename);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			fileWriter = new FileWriter(file.getAbsoluteFile(), true);


			BufferedReader reader = new BufferedReader(new FileReader(filename));

			if (reader.readLine() == null) {
				fileWriter.append(FILE_HEADER.toString());
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			fileWriter.append(userName);
			fileWriter.append(COMMA_DELIMITER);
			for (int j = 0; j < keyStoreParams.size() - 1; j++) {
				fileWriter.append("" + keyStoreParams.get(j));
				fileWriter.append(COMMA_DELIMITER);
			}
			fileWriter.append("" + keyStoreParams.get(keyStoreParams.size() - 1));

			fileWriter.append(NEW_LINE_SEPARATOR);
			System.out.println("CSV file was created successfully !!!");


			typingArea.setText("");
			//Return the focus to the typing area.
			displayArea.setText("Typing pattern is: \n" + keyStoreParams.toString());
			typingArea.requestFocusInWindow();

		} catch (Exception e2) {
			System.out.println("Error in CsvFileWriter !!!");
			e2.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public void generateCsv1(List<Character> typedCharacters,Integer id) {
		FileWriter fileWriter = null;
		try {

			String filename = "Frequency.csv";
			File file = new File(filename);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			fileWriter = new FileWriter(file.getAbsoluteFile(), true);


			BufferedReader reader = new BufferedReader(new FileReader(filename));

			if (reader.readLine() == null) {
				fileWriter.append(FILE_HEADER1.toString());
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			//fileWriter.append(id.toString());
			fileWriter.append(COMMA_DELIMITER);
			for (int j = 0; j < typedCharacters.size() - 1; j++) {
				fileWriter.append("" + typedCharacters.get(j));
				fileWriter.append(COMMA_DELIMITER);
			}
			fileWriter.append(typedCharacters.get(typedCharacters.size() - 1));

			//fileWriter.append(COMMA_DELIMITER);
			System.out.println("CSV file was created successfully !!!");
			

			//typingArea.setText("");
			//Return the focus to the typing area.
			//displayArea.setText("Typing pattern is: \n" + typedCharacters.toString());
			//typingArea.requestFocusInWindow();

		} catch (Exception e2) {
			System.out.println("Error in CsvFileWriter !!!");
			e2.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
	}

	public void generateCsvInNano(List<Long> keyStoreParams,String userName) {

		FileWriter fileWriter = null;
		try {
			String fileName = userName+"KeystrokesInNano.csv";
			//fileWriter = null;
			File file = new File(fileName);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			fileWriter = new FileWriter(file.getAbsoluteFile(), true);


			BufferedReader reader = new BufferedReader(new FileReader(fileName));

			if (reader.readLine() == null) {
				fileWriter.append(FILE_HEADER.toString());
				fileWriter.append(NEW_LINE_SEPARATOR);
			}



			fileWriter.append(userName);
			fileWriter.append(COMMA_DELIMITER);
			for (int j = 0; j < keyStoreParams.size() - 1; j++) {
				fileWriter.append("" + keyStoreParams.get(j));
				fileWriter.append(COMMA_DELIMITER);
			}
			fileWriter.append("" + keyStoreParams.get(keyStoreParams.size() - 1));

			fileWriter.append(NEW_LINE_SEPARATOR);
			System.out.println("CSV file was created successfully !!!");

			typingArea.setText("");
			//Return the focus to the typing area.
		//	displayArea.setText(keyStoreParams.toString());
			typingArea.requestFocusInWindow();

		} catch (Exception e2) {
			System.out.println("Error in CsvFileWriter !!!");
			e2.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}

