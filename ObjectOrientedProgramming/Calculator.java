//Lee Jeong Geun & Kwon Tae Du
//2020-11-20
//Object oriented programming 2020 second
//team assignment

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

//below packages enable coding Javascript in Java 
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Calculator {

	public static void main(String[] args) {
		
		//below frame contains a tab
		//tab has panels for some calculations.
		//calculations are
		//basic engineering calculation
		//matrix operation
		//converting among decimal, binary, hexadecimal numbers
		//converting between degree and radian
		//solving 2 degree equation
		//differentiating a function at any x value
		//integrating a function on any interval
		JFrame frame = new JFrame("Calculator");
		
		//when pressing x button, window is closed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//tab having panels
		JTabbedPane tp = new JTabbedPane();
		
		//adding panels on tab
		tp.addTab("Basic", new BasicPanel());
		tp.addTab("Matrix", new MatrixCalculatorPanel());
		tp.addTab("Digit", new DigitPanel());
		tp.addTab("DegRad", new DegRadPanel());
		tp.addTab("Eqn", new EquationPanel());
		tp.addTab("Diff", new DiffPanel());
		tp.addTab("Integral", new IntegralPanel());
		
		//adding tab on content pane
		frame.getContentPane().add(tp);
		frame.pack();
		frame.setVisible(true);
	}
}

//button panel for basic engineering calculation
class ButtonPanel extends JPanel{

	//hash map for buttons
	HashMap<String, JButton> buttonMap = new HashMap<String, JButton>();
	
	//constructor
	public ButtonPanel() {
		//generate number buttons 
		for(int i=0; i<=9; i++) {
			buttonMap.put(""+i, new JButton(""+i));
			
		}
		//generate buttons; dot, plus, minus, product, divide, equal, backspace, clear all
		buttonMap.put(".", new JButton("."));
		buttonMap.put("+", new JButton("+"));
		buttonMap.put("-", new JButton("-"));
		buttonMap.put("*", new JButton("*"));
		buttonMap.put("/", new JButton("/"));
		buttonMap.put("=", new JButton("="));
		buttonMap.put("backspace", new JButton("backspace"));
		buttonMap.put("clear all", new JButton("clear all"));
		
		//generate  buttons; degree, radian, %, sin, cos, tan, arcsin, arccos, arctan
		//ln, log, root, pi, e, power, (, ), !
		buttonMap.put("degree", new JButton("deg"));
		buttonMap.put("radian", new JButton("rad"));
		buttonMap.put("%", new JButton("%"));
		buttonMap.put("sin", new JButton("sin"));
		buttonMap.put("cos", new JButton("cos"));
		buttonMap.put("tan", new JButton("tan"));
		buttonMap.put("asin", new JButton("asin"));
		buttonMap.put("acos", new JButton("acos"));
		buttonMap.put("atan", new JButton("atan"));
		buttonMap.put("ln", new JButton("ln"));
		buttonMap.put("log", new JButton("log"));
		buttonMap.put("sqrt", new JButton("sqrt"));
		buttonMap.put("PI", new JButton("PI"));
		buttonMap.put("e", new JButton("e"));
		buttonMap.put("power", new JButton("power"));
		buttonMap.put("(", new JButton("("));
		buttonMap.put(")", new JButton(")"));
		buttonMap.put("!", new JButton("!"));
		buttonMap.put(",", new JButton(","));
		
		//setting size and font of buttons
		for(String key: buttonMap.keySet()) {
			buttonMap.get(key).setPreferredSize(new Dimension(30, 40));
			buttonMap.get(key).setFont(new Font("Helvetica", Font.BOLD, 15));
		}
		
		//layout of buttons as grid 4 by 8
		setLayout(new GridLayout(12,3));
		
		//add buttons
		add(buttonMap.get("7"));add(buttonMap.get("8"));add(buttonMap.get("9"));
		add(buttonMap.get("4"));add(buttonMap.get("5"));add(buttonMap.get("6"));
		add(buttonMap.get("1"));add(buttonMap.get("2"));add(buttonMap.get("3"));
		add(buttonMap.get("0"));add(buttonMap.get("."));add(buttonMap.get(","));		
		add(buttonMap.get("+"));add(buttonMap.get("-"));add(new JPanel());
		add(buttonMap.get("*"));add(buttonMap.get("/"));add(buttonMap.get("%"));
		add(buttonMap.get("="));add(buttonMap.get("backspace"));add(buttonMap.get("clear all"));
		add(buttonMap.get("sin"));add(buttonMap.get("cos"));add(buttonMap.get("tan"));
		add(buttonMap.get("asin"));add(buttonMap.get("acos"));add(buttonMap.get("atan"));
		add(buttonMap.get("ln"));add(buttonMap.get("log"));add(buttonMap.get("sqrt"));
		add(buttonMap.get("PI"));add(buttonMap.get("e"));add(buttonMap.get("power"));
		add(buttonMap.get("("));add(buttonMap.get(")"));add(buttonMap.get("!"));
	}
}

//display for basic engineering claculation
class DisplayPanel extends JPanel{
	
	//expression that user entered
	String expressionStr="";
	//on display, we show only subexpression
	//subexpression length is only 25
	String subExpressionStr="";
	//evaluation of expression
	String evaluationStr="";
	
	//panels for labels
	JPanel expressionPanel;
	JPanel evaluationPanel;
	
	//label of expression
	JLabel expressionLabel;
	//label showing expression
	JLabel expression;
	//label of evaluation
	JLabel evaluationLabel;
	//label showing evaluation
	JLabel evaluation;
	
	//constructor
	public DisplayPanel() {
		//initial value
		expressionStr = "";
		evaluationStr = "";
		//generate panels for expression and evaluation
		expressionPanel = new JPanel();
		evaluationPanel = new JPanel();
		//generate labels
		expressionLabel = new JLabel("expression:", JLabel.LEFT);
		expressionLabel.setPreferredSize(new Dimension(100, 40));
		evaluationLabel = new JLabel("evaluation: ", JLabel.LEFT);
		evaluationLabel.setPreferredSize(new Dimension(100, 40));
		
		//generate expression and evaluation
		expression = new JLabel(expressionStr, JLabel.LEFT);
		expression.setPreferredSize(new Dimension(250, 40));
		expression.setBorder(new LineBorder(Color.BLACK, 2));
		evaluation = new JLabel(evaluationStr, JLabel.LEFT);
		evaluation.setPreferredSize(new Dimension(250, 40));
		
		//set font of labels
		expressionLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
		expression.setFont(new Font("Helvetica", Font.PLAIN, 15));
		evaluationLabel.setFont(new Font("Helvetica", Font.BOLD, 15));
		evaluation.setFont(new Font("Helvetica", Font.BOLD, 15));
			
		//add label on panel
		expressionPanel.add(expressionLabel);
		expressionPanel.add(expression);
		evaluationPanel.add(evaluationLabel);
		evaluationPanel.add(evaluation);
		
		//add panels on basic calculation panel
		add(expressionPanel);		
		add(evaluationPanel);
	
		//set layout of basic calculation panel as vertical
		setLayout( new BoxLayout(this, BoxLayout.Y_AXIS));
		//set size of panel
		setPreferredSize(new Dimension(400, 90));
	}
}

//panel for basic engineering calculation
class BasicPanel extends JPanel{
	//generate script engine manager 
	ScriptEngineManager manager = new ScriptEngineManager();
	//generate Javascript engine
	ScriptEngine engine = manager.getEngineByName("js");
	//maximum length showed by display
	private final int DISPLAY_LENGTH = 25;
	//generate display panel
	private DisplayPanel displayPanel = new DisplayPanel();
	//generate button panel
	private ButtonPanel buttonPanel = new ButtonPanel();
	//generate token list
	//user inputs expression as token
	private ArrayList<String> tokenList = new ArrayList<String>();
	//generate listener for button
	private ButtonListener btnListener = new ButtonListener();
	//generate set of functions
	private final Set<String> functionSet = new HashSet<>(
			Arrays.asList(
					"power","sin","cos","tan","asin","acos","atan","ln","log","sqrt","!"
					)
			);
	//generate set of closing tokens
	//at the left of closing tokens, we add *1.0
	//because we want make evaluation as floating value
	private final Set<String> closingSet = new HashSet<>(
			Arrays.asList(
					"+","-","*","/",")",","
					)
			);
	//function that convert token list as expression that Java or Javascript can know
	private String toJavaExpression(ArrayList<String> tokenList) {
		//string that will have result of converting
		String javaExpression = "";
		//strings that take token and converted token
		String token, addedStr;
		int k;
		
		//take each tokens from token list
		for(k=0; k<tokenList.size(); k++) {
			token = tokenList.get(k);
			
			//convert each token to form of Java(script)
			switch(token) {
			//opening of functions
			case "power(": addedStr = "Math.pow("; break;
			case "sin(": addedStr = "Math.sin("; break;
			case "cos(": addedStr = "Math.cos("; break;
			case "tan(": addedStr = "Math.tan("; break;
			case "asin(": addedStr = "Math.asin("; break;
			case "acos(": addedStr = "Math.acos("; break;
			case "atan(": addedStr = "Math.atan("; break;
			case "ln(": addedStr = "Math.log("; break;
			case "log(": addedStr = "log10("; break;
			case "sqrt(": addedStr = "Math.sqrt("; break;
			case "!(": addedStr ="factorial("; break;
			default:
				//closing of function or bracket
				if(closingSet.contains(token)) addedStr = "*1.0"+token;
				else{
					//convert constant values
					if(token.equals("PI")) addedStr = "Math.PI";
					else if(token.equals("e")) addedStr = "Math.E";
					//case of any other tokens
					else addedStr = token;
				}
			}
			
			//adding converted token
			javaExpression += addedStr;
		}
		return javaExpression;
	}
	//constructor for basic engineering calculation
	public BasicPanel() {
		try {
			//defining factorial function in the Javascript engine
			engine.eval(
					"function factorial(n){"
					+ "if( (parseInt(n)-n) != 0) throw '';"
					+ "if( n<0 ) throw '';"
					+ "if(n==0) return 1;"
					+ "var res = 1;"
					+ "for(i=1; i<=n; i++) res*=i;"
					+ "return res;"
					+ "}"
					+ "function log10(n){"
					+ "return (1/Math.log(10))*Math.log(n);"
					+ "}");
		}
		//each use of Javascript engine needs handling of exception
		catch(Exception e) {
		}
		//adding display and buttons
		add(displayPanel);
		add(buttonPanel);
		//adding button listener for all buttons
		for(String key: buttonPanel.buttonMap.keySet()) {
			buttonPanel.buttonMap.get(key).addActionListener(btnListener);
		}
		//set layout as vertical
		setLayout( new BoxLayout(this, BoxLayout.Y_AXIS));
		//set size of calculator
		setPreferredSize(new Dimension(400, 600));
	}
	//listener for buttons
	private class ButtonListener implements ActionListener{
		//when expression is not correct, we show below message
		final String INCORRECT_SYNTAX_MESSAGE = "incorrect syntax";
		//below function is invoked when any action like clicking occurs
		public void actionPerformed(ActionEvent event) {
			//get button where event occurs
			JButton b = (JButton)event.getSource();
			//case of = button
			if(b.getText().equals("=")) {
				//get texts from evaluation and change texts of expression to those 
				String evalStr = displayPanel.evaluation.getText();
				if(!evalStr.equals(INCORRECT_SYNTAX_MESSAGE)) {
					displayPanel.expression.setText(evalStr);
					//make token list empty and add new tokens
					tokenList = new ArrayList<String>();
					//evaluation has no function opening
					//so we can make token list
					//by only changing evaluation string to character array
					char[] tempArr = evalStr.toCharArray();
					for(char ch : tempArr) {
						tokenList.add(""+ch);
					}
				}
			}
			//case of backspace button
			//delete only one token
			else if(b.getText().equals("backspace")) {
				if(tokenList.size()>0) tokenList.remove(tokenList.size()-1);
			}
			//case of clear all button
			//make token list empty list
			else if(b.getText().equals("clear all")) {
				if(tokenList.size()>0) tokenList = new ArrayList<String>();
			}
			else{
				//case of function button
				//add concatenation of function name and opening bracket
				if( functionSet.contains(b.getText()) )tokenList.add(b.getText()+"(");
				//case of the other buttons
				else{
					tokenList.add(b.getText());
				}
			}
			//prepare new expression string
			displayPanel.expressionStr = "";
			//add each token to expression string
			for(String token: tokenList) {
				displayPanel.expressionStr += token;
			}
			if(displayPanel.expressionStr.length()<=DISPLAY_LENGTH) displayPanel.subExpressionStr = displayPanel.expressionStr;
			//case that expression string length is longer than limit
			//display only substring
			else displayPanel.subExpressionStr = displayPanel.expressionStr.substring(displayPanel.expressionStr.length()-DISPLAY_LENGTH);
			displayPanel.expression.setText(displayPanel.subExpressionStr);
			
			//use Javascript engine to calculate expression
			try {
				String javaExpression = toJavaExpression(tokenList);
				if(javaExpression.equals("")) displayPanel.evaluationStr = "";
				else{
					displayPanel.evaluationStr = ""+engine.eval(javaExpression);
				}
			}
			//case that expression is not correct
			//show incorrect syntax message
			catch(Exception e){
				displayPanel.evaluationStr = INCORRECT_SYNTAX_MESSAGE;
			}
			//show evaluation on display
			displayPanel.evaluation.setText(displayPanel.evaluationStr);
		}
	}
}

//field that shows an element of matrix
//field panel has a radio button, a label and a string
//the string contains a element as string
class MatrixFieldPanel extends JPanel{
	JRadioButton radio;
	JLabel label;
	String str;
	//constructor of field
	//set sizes and layout
	public MatrixFieldPanel() {
		str="";
		label = new JLabel("");
		radio = new JRadioButton("");
		label.setBorder(new LineBorder(Color.BLACK, 2));
		label.setPreferredSize(new Dimension(100,20));
		radio.setPreferredSize(new Dimension(10, 20));
		setLayout( new GridLayout(1,2));
	}
}
//field when it has position of right on the matrix
class MatrixFieldRightPanel extends MatrixFieldPanel{	
	public MatrixFieldRightPanel() {
		radio.setHorizontalAlignment(SwingConstants.LEFT);
		add(label);add(radio);
	}
}
//field when it has position of left on the matrix
class MatrixFieldLeftPanel extends MatrixFieldPanel{
	public MatrixFieldLeftPanel() {
		radio.setHorizontalAlignment(SwingConstants.RIGHT);
		add(radio);add(label);
	}
}
//panel for a matrix as operand
//it has label for matrix name and fields for elements
class MatrixPanel extends JPanel{
	JLabel label;
	MatrixFieldLeftPanel f11, f21;
	MatrixFieldRightPanel f12, f22;
	
	//constructor generates label and fields
	//and set text of radio buttons
	//and layout of matrix
	//and add label, fields
	//and select a radio button as default
	public MatrixPanel() {
		label = new JLabel();
		f11 = new MatrixFieldLeftPanel();
		f12 = new MatrixFieldRightPanel();
		f21 = new MatrixFieldLeftPanel();
		f22 = new MatrixFieldRightPanel();
		f11.radio.setText("(1,1)");
		f12.radio.setText("(1,2)");
		f21.radio.setText("(2,1)");
		f22.radio.setText("(2,2)");
		
		setLayout( new GridLayout(3,2) );
		add(label);add(new JPanel());
		add(f11);add(f12);
		add(f21);add(f22);
		f11.radio.setSelected(true);
	}
}

//result matrix panel
//it has label for matrix name and field for elements of matrix
class MatrixResultPanel extends JPanel{
	JLabel label;
	JLabel f11, f12, f21, f22;
	//constructor generates label and fields
	//and set borders of fields
	//and set layout and 3 by 4
	//and add label and fields
	public MatrixResultPanel() {
		label = new JLabel();
		f11 = new JLabel();
		f12 = new JLabel();
		f21 = new JLabel();
		f22 = new JLabel();
		f11.setBorder(new LineBorder(Color.BLACK, 2));
		f12.setBorder(new LineBorder(Color.BLACK, 2));
		f21.setBorder(new LineBorder(Color.BLACK, 2));
		f22.setBorder(new LineBorder(Color.BLACK, 2));
		setLayout( new GridLayout(3,4));
		add(label); add(new JPanel());add(new JPanel());add(new JPanel());
		add(new JPanel());add(f11);add(f12);add(new JPanel());
		add(new JPanel());add(f21);add(f22);add(new JPanel());
	}
}

//panel for matrix operators
//panel has 4 radio buttons for each operation
class MatrixOperatorPanel extends JPanel{
	JLabel label;
	JRadioButton plus, minus, product, inverse;
	//constructro generates label and radio buttons
	//and set text and font size for them
	//select plus as default
	public MatrixOperatorPanel() {
		label = new JLabel();
		plus = new JRadioButton();
		minus = new JRadioButton();
		product = new JRadioButton();
		inverse = new JRadioButton();
		label.setText("Operation:");
		plus.setText("A+B");
		plus.setFont(new Font("helvetica",Font.BOLD,12));
		minus.setText("A-B");
		minus.setFont(new Font("helvetica",Font.BOLD,12));
		product.setText("A*B");
		product.setFont(new Font("helvetica",Font.BOLD,12));
		inverse.setText("inverse of A");
		inverse.setFont(new Font("helvetica",Font.BOLD,12));
		plus.setSelected(true);
		add(label);
		add(plus); add(minus); add(product); add(inverse);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}
}

//number panel for matrix calculation
class NumberPanel extends JPanel{
	
	//hash map of buttons
	HashMap<String, JButton> buttonMap = new HashMap<String, JButton>();
	
	//constructor
	//generate buttons for numbers and number operator and backspace, calculate, clear all, constants
	//only operator -,*,/ are possible
	public NumberPanel() {
		for(int i=0; i<=9; i++) {
			buttonMap.put(""+i, new JButton(""+i));		
		}
		buttonMap.put(".", new JButton("."));
		buttonMap.put("-", new JButton("-"));
		buttonMap.put("backspace", new JButton("backspace"));
		buttonMap.put("calculate", new JButton("calculate"));
		buttonMap.put("clear all", new JButton("clear all"));
		buttonMap.put("*", new JButton("*"));
		buttonMap.put("/", new JButton("/"));
		buttonMap.put("PI", new JButton("PI"));
		buttonMap.put("e", new JButton("e"));
		//set size and font for buttons
		for(String key: buttonMap.keySet()) {
			buttonMap.get(key).setPreferredSize(new Dimension(30, 40));
			buttonMap.get(key).setFont(new Font("Helvetica", Font.BOLD, 15));
		}
		//set layout of buttons as grid 4 by 8
		setLayout(new GridLayout(7,3));
		//add buttons
		add(buttonMap.get("7"));add(buttonMap.get("8"));add(buttonMap.get("9"));
		add(buttonMap.get("4"));add(buttonMap.get("5"));add(buttonMap.get("6"));
		add(buttonMap.get("1"));add(buttonMap.get("2"));add(buttonMap.get("3"));
		add(buttonMap.get("0"));add(buttonMap.get("."));add(buttonMap.get("-"));
		add(buttonMap.get("*"));add(buttonMap.get("/"));add(buttonMap.get("PI"));
		add(buttonMap.get("e"));add(buttonMap.get("backspace"));add(buttonMap.get("clear all"));
		add(buttonMap.get("calculate"));
	}
}
//matrix calculator panel
//has 2 operand matrix and 1 result matrix and number buttons and operation radio buttons
//has button listener and list of elements field
//has script engine manager and Javascript engine
class MatrixCalculatorPanel extends JPanel{
	MatrixPanel matrix1, matrix2;
	MatrixResultPanel result;
	NumberPanel numberBtn;
	ButtonGroup elementGroup;
	ButtonGroup operationGroup;
	MatrixOperatorPanel matrixOperator;
	ButtonListener btnListener;
	ArrayList<MatrixFieldPanel> fieldList;
	ScriptEngineManager manager = new ScriptEngineManager();
	ScriptEngine engine = manager.getEngineByName("js");
	
	//constructor generates matrix panels and add fields to fieldList
	//generates operator panel and add radio buttons on button group
	//set layout as vertical and add button listener on buttons
	public MatrixCalculatorPanel() {
		matrix1 = new MatrixPanel();
		matrix1.label.setText("matrix A:");
		matrix2 = new MatrixPanel();
		matrix2.label.setText("matrix B:");
		fieldList = new ArrayList<MatrixFieldPanel>();
		
		fieldList.add(matrix1.f11);
		fieldList.add(matrix1.f12);
		fieldList.add(matrix1.f21);
		fieldList.add(matrix1.f22);
		fieldList.add(matrix2.f11);
		fieldList.add(matrix2.f12);
		fieldList.add(matrix2.f21);
		fieldList.add(matrix2.f22);
		
		matrixOperator = new MatrixOperatorPanel();
		numberBtn = new NumberPanel();
		elementGroup = new ButtonGroup();
		elementGroup.add(matrix1.f11.radio);
		elementGroup.add(matrix1.f12.radio);
		elementGroup.add(matrix1.f21.radio);
		elementGroup.add(matrix1.f22.radio);
		elementGroup.add(matrix2.f11.radio);
		elementGroup.add(matrix2.f12.radio);
		elementGroup.add(matrix2.f21.radio);
		elementGroup.add(matrix2.f22.radio);
		operationGroup = new ButtonGroup();
		operationGroup.add(matrixOperator.plus);
		operationGroup.add(matrixOperator.minus);
		operationGroup.add(matrixOperator.product);
		operationGroup.add(matrixOperator.inverse);
		result = new MatrixResultPanel();
		result.label.setText("result:");
		result.label.setFont(new Font("hevetica",Font.PLAIN,15));
		
		add(matrix1);
		add(matrix2);
		add(result);
		add(matrixOperator);
		add(numberBtn);

		setLayout( new BoxLayout(this, BoxLayout.Y_AXIS));
		btnListener = new ButtonListener();
		for(String key: numberBtn.buttonMap.keySet()) {
			numberBtn.buttonMap.get(key).addActionListener(new ButtonListener());
		}
	}
	
	//function setting result field text as incorrect on syntax
	private void setIncorrectsyntax() {
		result.f11.setText("incorrect syntax");
		result.f12.setText("incorrect syntax");
		result.f21.setText("incorrect syntax");
		result.f22.setText("incorrect syntax");
	}
	//function invoked when inverse of A is not defined
	private void setUndefined() {
		result.f11.setText("undefined");
		result.f12.setText("undefined");
		result.f21.setText("undefined");
		result.f22.setText("undefined");
	}
	//function that convert constants to Java(sript) style
	protected String constToJavaExpression(String str) {
		String newStr = "";
		String subStr = "";
		for(int i=0; i<str.length();i++) {
			subStr = str.substring(i, i+1);
			if(subStr.equals("P")) {
				newStr += "Math.PI";
			}
			else if(subStr.equals("I")) {
				
			}
			else if(subStr.equals("e")) {
				newStr += "Math.E";
			}
			else {
				newStr += subStr;
			}
		}
		return newStr;
	}
	//function that convert element field texts of matrix panel
	//to floating number in matrix array
	//this version is for binary operation
	private void getMatrix(double[][] matA, double[][] matB) throws NumberFormatException, ScriptException {
		matA[0][0] = Double.parseDouble(""+engine.eval(constToJavaExpression(matrix1.f11.label.getText())));
		matA[0][1] = Double.parseDouble(""+engine.eval(constToJavaExpression(matrix1.f12.label.getText())));
		matA[1][0] = Double.parseDouble(""+engine.eval(constToJavaExpression(matrix1.f21.label.getText())));
		matA[1][1] = Double.parseDouble(""+engine.eval(constToJavaExpression(matrix1.f22.label.getText())));
		matB[0][0] = Double.parseDouble(""+engine.eval(constToJavaExpression(matrix2.f11.label.getText())));
		matB[0][1] = Double.parseDouble(""+engine.eval(constToJavaExpression(matrix2.f12.label.getText())));
		matB[1][0] = Double.parseDouble(""+engine.eval(constToJavaExpression(matrix2.f21.label.getText())));
		matB[1][1] = Double.parseDouble(""+engine.eval(constToJavaExpression(matrix2.f22.label.getText())));
	}
	//function that convert element field texts of matrix panel
		//to floating number in matrix array
		//this version is for unary operation, that is, inverse
	private void getOneMatrix(double[][] matA) throws NumberFormatException, ScriptException {
		matA[0][0] = Double.parseDouble(""+engine.eval(constToJavaExpression(matrix1.f11.label.getText())));
		matA[0][1] = Double.parseDouble(""+engine.eval(constToJavaExpression(matrix1.f12.label.getText())));
		matA[1][0] = Double.parseDouble(""+engine.eval(constToJavaExpression(matrix1.f21.label.getText())));
		matA[1][1] = Double.parseDouble(""+engine.eval(constToJavaExpression(matrix1.f22.label.getText())));
	}
	//function for matrix addition
	private void matrixAdd(double[][] resultMat) throws NumberFormatException, ScriptException {
		double[][] matA = new double[2][2];
		double[][] matB = new double[2][2];
		getMatrix(matA, matB);
		for(int i=0; i<2; i++) {
			for(int j=0; j<2; j++) {
				resultMat[i][j] = matA[i][j]+matB[i][j];
			}
		}
	}
	//function for matrix subtraction
	private void matrixSub(double[][] resultMat) throws NumberFormatException, ScriptException {
		double[][] matA = new double[2][2];
		double[][] matB = new double[2][2];
		getMatrix(matA, matB);
		for(int i=0; i<2; i++) {
			for(int j=0; j<2; j++) {
				resultMat[i][j] = matA[i][j]-matB[i][j];
			}
		}
	}
	//function for matrix multiplication
	private void matrixMult(double[][] resultMat) throws NumberFormatException, ScriptException {
		double[][] matA = new double[2][2];
		double[][] matB = new double[2][2];
		getMatrix(matA, matB);
		for(int i=0; i<2; i++) {
			for(int j=0; j<2; j++) {
				resultMat[i][j] = 0;
				for(int k=0; k<2; k++)
				resultMat[i][j] += matA[i][k]*matB[k][j];
			}
		}
	}
	//function for matrix determinant
	private double determinant(double[][] matA) {
		return matA[0][0]*matA[1][1]-matA[0][1]*matA[1][0];
	}
	//function for matrix inversion
	private void matrixInv(double[][] resultMat) throws NumberFormatException, ScriptException {
		double[][] matA = new double[2][2];
		getOneMatrix(matA);
		double det = determinant(matA);
		resultMat[0][0] = matA[1][1]/det;
		resultMat[1][1] = matA[0][0]/det;
		resultMat[0][1] = -matA[0][1]/det;
		resultMat[1][0] = -matA[1][0]/det;
	}
	
	//function that converts floating matrix array to result matrix field text
	//constrain as 15 length
	private void setMatrix(double[][] mat) {
		//result.f11.setText(Double.toString(mat[0][0]));
		//result.f12.setText(Double.toString(mat[0][1]));
		//result.f21.setText(Double.toString(mat[1][0]));
		//result.f22.setText(Double.toString(mat[1][1]));
		result.f11.setText(String.format("%-15g", mat[0][0]));
		result.f12.setText(String.format("%-15g", mat[0][1]));
		result.f21.setText(String.format("%-15g", mat[1][0]));
		result.f22.setText(String.format("%-15g", mat[1][1]));
	}
	//button listener
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			//get pushed button
			JButton pushedButton = (JButton)event.getSource();
			//enumeration for finding pushed radio button for operator
			//enumeration has element of radio button group
			Enumeration<AbstractButton> enu;
			//variables for iterated and found radio button
			JRadioButton rb = null, jb;
			AbstractButton ab;
			
			//case for clear all button
			//makes all field empty
			if(pushedButton.getText().equals("clear all")) {
				matrix1.f11.label.setText("");
				matrix1.f12.label.setText("");
				matrix1.f21.label.setText("");
				matrix1.f22.label.setText("");
				matrix2.f11.label.setText("");
				matrix2.f12.label.setText("");
				matrix2.f21.label.setText("");
				matrix2.f22.label.setText("");
				result.f11.setText("");
				result.f12.setText("");
				result.f21.setText("");
				result.f22.setText("");
				matrix1.f11.str = "";
				matrix1.f12.str = "";
				matrix1.f21.str = "";
				matrix1.f22.str = "";
				matrix2.f11.str = "";
				matrix2.f12.str = "";
				matrix2.f21.str = "";
				matrix2.f22.str = "";
				
				return;
			}
			//case for calculate button
			if(pushedButton.getText().equals("calculate")) {
				try {
					enu = operationGroup.getElements();
					//find pushed radio button for operator
					//get element from enumerator and check if it is set true
					//if true, store it on the variable
					while(enu.hasMoreElements()) {
						ab = enu.nextElement();
						jb = (JRadioButton) ab;
						if(jb.isSelected()) rb = jb;
					}
					//select operation function as button text and set result
					double[][] resultMat=new double[2][2];
					switch(rb.getText()) {
					case "A+B":
						matrixAdd(resultMat);
						setMatrix(resultMat);
						break;
					case "A-B":
						matrixSub(resultMat);
						setMatrix(resultMat);
						break;
					case "A*B":
						matrixMult(resultMat);
						setMatrix(resultMat);
						break;
					case "inverse of A":
						double[][] matA=new double[2][2];;
						getOneMatrix(matA);
						if(determinant(matA)==0) {
							setUndefined();
						}
						else {
							matrixInv(resultMat);
							setMatrix(resultMat);
						}
					}
				}
				//handle when incorrect expression is in a field
				catch(Exception e) {
					setIncorrectsyntax();
				}
				return;
			}
			
			//case when non calculation button is pressed
			
			//fine radio button among matrix field
			enu = elementGroup.getElements();
			while(enu.hasMoreElements()) {
				ab = enu.nextElement();
				jb = (JRadioButton) ab;
				if(jb.isSelected()) rb = jb;
			}
			
			MatrixFieldPanel field = null;
			//find field that radio button pushed
			for(MatrixFieldPanel f:fieldList) {
				if(f.radio.equals(rb)) field = f;
			}
			//backspace is pressed then one character is deleted
			//but PI is deleted at one time
			if(pushedButton.getText().equals("backspace")) {
				if(field.str.length()>0) {
					if(field.str.substring(field.str.length()-1).equals("I")) {
						field.str = field.str.substring(0, field.str.length()-2);
					}
					else
						field.str = field.str.substring(0, field.str.length()-1);
				}
			}
			//the other button is pressed then button text is input on field
			else {
				field.str = field.str+pushedButton.getText();
			}
			//only 15 characters is shown on field
			if(field.str.length()<=15) field.label.setText(field.str);
			else {
				field.label.setText(field.str.substring(field.str.length()-15, field.str.length()));
			}
		}
	}
}

//panel of display for digit base converting
//has radio buttons for base selection
//has labels for base number field
//has fields for base number
//has row panels that has a radio button, a label, a field
//has button group that bounds radio buttons

class DigitDisplay extends JPanel{
	JRadioButton[] radioBtn = new JRadioButton[3];
	JLabel[] labelArr = new JLabel[3];
	JLabel[] fieldArr = new JLabel[3];
	JPanel[] rowArr = new JPanel[3];
	ButtonGroup btnGroup = new ButtonGroup();
	//constructor
	//generates radio buttons, labels, fields, rows
	//and sets their sizes or borders
	//and adds radio buttons on button group
	//and adds radio button and field on row
	//and set default radio button value
	//and set text of labels
	//and set layout as vertical boxes
	public DigitDisplay() {
		for(int i=0; i<3; i++) {
			radioBtn[i] = new JRadioButton();
			btnGroup.add(radioBtn[i]);
			labelArr[i] = new JLabel();
			fieldArr[i] = new JLabel();
			fieldArr[i].setPreferredSize(new Dimension(200, 40));
			rowArr[i] = new JPanel();
			rowArr[i].add(radioBtn[i]);
			rowArr[i].add(labelArr[i]);
			rowArr[i].add(fieldArr[i]);
			fieldArr[i].setBorder(new LineBorder(Color.BLACK, 2));
			
			add(rowArr[i]);
		}
		radioBtn[0].setSelected(true);
		labelArr[0].setText("decimal:         ");
		labelArr[1].setText("binary:           ");
		labelArr[2].setText("hexadecimal:");
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}	
}

//number panel for digit base converting
//has map that link name and button itself
class DigitNumberPanel extends JPanel{
	//hash map of buttons
	HashMap<String, JButton> buttonMap = new HashMap<String, JButton>();
	
	//constructor
	//generates number buttons, backspace, calculate, clear all buttons
	//and adds all buttons on map
	public DigitNumberPanel() {
		for(int i=0; i<=9; i++) {
			buttonMap.put(""+i, new JButton(""+i));		
		}
		
		buttonMap.put("a", new JButton("a"));
		buttonMap.put("b", new JButton("b"));
		buttonMap.put("c", new JButton("c"));
		buttonMap.put("d", new JButton("d"));
		buttonMap.put("e", new JButton("e"));
		buttonMap.put("f", new JButton("f"));
		buttonMap.put("backspace", new JButton("backspace"));
		buttonMap.put("calculate", new JButton("calculate"));
		buttonMap.put("clear all", new JButton("clear all"));
	
		for(String key: buttonMap.keySet()) {
			buttonMap.get(key).setPreferredSize(new Dimension(30, 40));
			buttonMap.get(key).setFont(new Font("Helvetica", Font.BOLD, 15));
		}
		//layout as grid 4 by 8
		setLayout(new GridLayout(7,3));
		//add buttons
		add(buttonMap.get("d"));add(buttonMap.get("e"));add(buttonMap.get("f"));
		add(buttonMap.get("a"));add(buttonMap.get("b"));add(buttonMap.get("c"));
		add(buttonMap.get("7"));add(buttonMap.get("8"));add(buttonMap.get("9"));
		add(buttonMap.get("4"));add(buttonMap.get("5"));add(buttonMap.get("6"));
		add(buttonMap.get("1"));add(buttonMap.get("2"));add(buttonMap.get("3"));
		add(buttonMap.get("0"));add(buttonMap.get("backspace"));add(buttonMap.get("clear all"));
		add(buttonMap.get("calculate"));
		
	}
}
//panel for digit base converting
//defines maximum display length
//generate display panel and number panel
//has strings for numbers of 3 digit bases
class DigitPanel extends JPanel{
	final int DISPLAY_LENGTH = 25;
	DigitDisplay digitDisplay = new DigitDisplay();
	DigitNumberPanel numberPad = new DigitNumberPanel();
	String str10="", str2="", str16="";
	
	//constructor add display and number panel
	//and add button listener on buttons
	//and set layout as vertical boxes
	public DigitPanel() {
		add(digitDisplay);
		add(numberPad);
		for(String str: numberPad.buttonMap.keySet()) {
			numberPad.buttonMap.get(str).addActionListener(new ButtonListener());
		}
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	//function that each field as corresponding current value of digit base number
	private void setAllField() {
		digitDisplay.fieldArr[0].setText(str10.length()<DISPLAY_LENGTH? str10:str10.substring(str16.length()-DISPLAY_LENGTH, str10.length()) );
		digitDisplay.fieldArr[1].setText(str2.length()<DISPLAY_LENGTH? str2:str2.substring(str2.length()-DISPLAY_LENGTH, str2.length()) );
		digitDisplay.fieldArr[2].setText(str16.length()<DISPLAY_LENGTH? str16:str16.substring(str16.length()-DISPLAY_LENGTH, str16.length()) );
	}
	//function that finds pushed radio button and selected digit base
	//and convert it to the other bases
	//uses Integer static method parseInt, toBinaryString, toHexString
	private void convert() {		
		int num;
		if(digitDisplay.radioBtn[0].isSelected()) {
			num = Integer.parseInt(str10);
			str2 = Integer.toBinaryString(num);
			str16 = Integer.toHexString(num);
			setAllField();
		}
		if(digitDisplay.radioBtn[1].isSelected()) {
			num = Integer.parseInt(str2,2);
			str10 = Integer.toString(num);
			str16 = Integer.toHexString(num);
			setAllField();
		}
		if(digitDisplay.radioBtn[2].isSelected()) {
			num = Integer.parseInt(str16,16);
			str10 = Integer.toString(num);
			str2 = Integer.toBinaryString(num);
			setAllField();
		}
	}
	//button listener
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			//case for calculate button
			//provoke convert function
			//if field has incorrect syntax then alert it.
			if(((JButton)event.getSource()).getText().equals("calculate")) {
				try {
					convert();
				}
				catch(Exception e) {
					if(!digitDisplay.radioBtn[0].isSelected()) str10 = "incorrect syntax";
					if(!digitDisplay.radioBtn[1].isSelected()) str2 = "incorrect syntax";
					if(!digitDisplay.radioBtn[2].isSelected()) str16 = "incorrect syntax";
					setAllField();
				}
			}
			//case for backspace button
			//deletes only one character
			else if(((JButton)event.getSource()).getText().equals("backspace")) {
				if(digitDisplay.radioBtn[0].isSelected()) if(str10.length()>0) str10 = str10.substring(0, str10.length()-1);
				if(digitDisplay.radioBtn[1].isSelected()) if(str2.length()>0) str2 = str2.substring(0, str2.length()-1);
				if(digitDisplay.radioBtn[2].isSelected()) if(str16.length()>0) str16 = str16.substring(0, str16.length()-1);
				setAllField();
			}
			//case for clear all button
			//set all digit base number strings as null string
			else if(((JButton)event.getSource()).getText().equals("clear all")) {
				str10 = "";
				str2 = "";
				str16 = "";
				setAllField();
			}
			//case for number buttons
			//constrain possible buttons as corresponding radio button
			else {
				String newStr = ((JButton)event.getSource()).getText();
				if(digitDisplay.radioBtn[0].isSelected()) {
					if(!newStr.equals("a")&&!newStr.equals("b")&&!newStr.equals("c")&&!newStr.equals("d")&&!newStr.equals("e")&&!newStr.equals("f"))str10 = str10+newStr;
				}
				if(digitDisplay.radioBtn[1].isSelected()) {
					if(newStr.equals("0") || newStr.equals("1")) str2 = str2+newStr;
				}
				if(digitDisplay.radioBtn[2].isSelected()) str16 = str16+newStr;
				setAllField();
			}
		}
	}
}

//display for conversion between degree and radian
//has radio buttons, fields, labels, rows for degree and radian value
//has button group for radio buttons
class DegRadDisplay extends JPanel{
	JRadioButton[] radioBtn = new JRadioButton[3];
	JLabel[] labelArr = new JLabel[2];
	JLabel[] fieldArr = new JLabel[2];
	JPanel[] rowArr = new JPanel[2];
	ButtonGroup btnGroup = new ButtonGroup();
	//constructor generates radio buttons, labels, fields, rows and add them
	//sets size and border of fields
	//sets default radio button value
	//sets label texts
	//sets layout as vertical boxes
	public DegRadDisplay() {
		for(int i=0; i<2; i++) {
			radioBtn[i] = new JRadioButton();
			btnGroup.add(radioBtn[i]);
			labelArr[i] = new JLabel();
			fieldArr[i] = new JLabel();
			fieldArr[i].setPreferredSize(new Dimension(200, 40));
			rowArr[i] = new JPanel();
			rowArr[i].add(radioBtn[i]);
			rowArr[i].add(labelArr[i]);
			rowArr[i].add(fieldArr[i]);
			fieldArr[i].setBorder(new LineBorder(Color.BLACK, 2));
			
			add(rowArr[i]);
		}
		radioBtn[0].setSelected(true);
		labelArr[0].setText("degree:         ");
		labelArr[1].setText("radian:         ");
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}	
}

//panel for conversion between degree and radian
//has display, number panel
//has string storing degree and radian values
//has scipt engine manager and engine for simplifying entered value
class DegRadPanel extends JPanel{
	final int DISPLAY_LENGTH = 25;
	DegRadDisplay degRadDisplay = new DegRadDisplay();
	NumberPanel numberPad = new NumberPanel();
	String strDeg="", strRad="";
	ScriptEngineManager manager = new ScriptEngineManager();
	ScriptEngine engine = manager.getEngineByName("js");
	
	//constructor adds display, number panel
	//adds button listener on buttons
	//sets layout as vertical boxes
	public DegRadPanel() {
		
		add(degRadDisplay);
		add(numberPad);
		for(String str: numberPad.buttonMap.keySet()) {
			numberPad.buttonMap.get(str).addActionListener(new ButtonListener());
		}
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	//function that sets texts for all fields
	//constrain text length as DISPLAY_LENGTH
	private void setAllField() {
		degRadDisplay.fieldArr[0].setText(strDeg.length()<DISPLAY_LENGTH? strDeg:strDeg.substring(strDeg.length()-DISPLAY_LENGTH, strDeg.length()) );
		degRadDisplay.fieldArr[1].setText(strRad.length()<DISPLAY_LENGTH? strRad:strRad.substring(strRad.length()-DISPLAY_LENGTH, strRad.length()) );
	}
	//function that converts constants to Java(sript) form
	protected String constToJavaExpression(String str) {
		String newStr = "";
		String subStr = "";
		for(int i=0; i<str.length();i++) {
			subStr = str.substring(i, i+1);
			if(subStr.equals("P")) {
				newStr += "Math.PI";
			}
			else if(subStr.equals("I")) {
				
			}
			else if(subStr.equals("e")) {
				newStr += "Math.E";
			}
			else {
				newStr += subStr;
			}
		}
		return newStr;
	}
	
	//function that converts values between degree and radian
	//selected field value is converted to the other value
	private void convert() throws NumberFormatException, ScriptException {		
		double deg, rad;
		if(degRadDisplay.radioBtn[0].isSelected()) {
			deg = Double.parseDouble(""+engine.eval(constToJavaExpression(strDeg)));
			rad = deg*Math.PI/180;
			//strRad = Double.toString(rad);
			strRad = String.format("%-25g", rad);
			setAllField();
		}
		if(degRadDisplay.radioBtn[1].isSelected()) {
			rad = Double.parseDouble(""+engine.eval(constToJavaExpression(strRad)));
			deg = rad*180/Math.PI;
			//strDeg = Double.toString(deg);
			strDeg = String.format("%-25g", deg);
			setAllField();
		}
	}
	
	//button listener
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			//case for calculate button
			//provoke convert function
			//if selected field has incorrect syntax then the other field alert it
			if(((JButton)event.getSource()).getText().equals("calculate")) {
				try {
					convert();
				}
				catch(Exception e) {
					if(!degRadDisplay.radioBtn[0].isSelected()) strDeg = "incorrect syntax";
					if(!degRadDisplay.radioBtn[1].isSelected()) strRad = "incorrect syntax";
					setAllField();
				}
			}
			//case for backspace button
			//delete one character on selected field
			//if deleted character is I of PI then delete 2 characters
			else if(((JButton)event.getSource()).getText().equals("backspace")) {
				if(degRadDisplay.radioBtn[0].isSelected())
					if(strDeg.length()>0) {
						if(strDeg.substring(strDeg.length()-1).equals("I")) {
							strDeg = strDeg.substring(0, strDeg.length()-2);
						}
						else
							strDeg = strDeg.substring(0, strDeg.length()-1);
					}
				if(degRadDisplay.radioBtn[1].isSelected()) {
					if(strRad.length()>0) {
						if(strRad.substring(strRad.length()-1).equals("I")) {
							strRad = strRad.substring(0, strRad.length()-2);
						}
						else
							strRad = strRad.substring(0, strRad.length()-1);
					}
				}
				setAllField();
			}
			//case for clear all button
			//clear all field
			else if(((JButton)event.getSource()).getText().equals("clear all")) {
				strDeg = "";
				strRad = "";
				setAllField();
			}
			//case for the others
			//just add the button text
			else {
				String newStr = ((JButton)event.getSource()).getText();
				if(degRadDisplay.radioBtn[0].isSelected()) {
					strDeg = strDeg+newStr;
				}
				if(degRadDisplay.radioBtn[1].isSelected()) {
					strRad = strRad+newStr;
				}
				setAllField();
			}
		}
	}
}

//display for equation
//extends digit display so use three field of it to display coefficients
//beside, has solution panel of 2 solutions
class EquationDisplay extends DigitDisplay{
	String[] solutionStr = {"",""};
	JLabel[] solutionLabels = {new JLabel("solution 1: "), new JLabel("solution 2: ")};
	JLabel[] solutions = {new JLabel(solutionStr[0]), new JLabel(solutionStr[1])};
	JPanel[] solutionPanels = { new JPanel(), new JPanel()};
	
	//constructor
	//sets name for coefficient field
	//sets size, border for solution field
	//adds solution label and fields
	//adds solution panels
	public EquationDisplay() {
		labelArr[0].setText("a:");
		labelArr[1].setText("b:");
		labelArr[2].setText("c:");
		solutions[0].setPreferredSize(new Dimension(200,40));
		solutions[1].setPreferredSize(new Dimension(200,40));
		solutions[0].setBorder(new LineBorder(Color.BLACK,2));
		solutions[1].setBorder(new LineBorder(Color.BLACK,2));
		solutionPanels[0].add(solutionLabels[0]);
		solutionPanels[0].add(solutions[0]);
		solutionPanels[1].add(solutionLabels[1]);
		solutionPanels[1].add(solutions[1]);
		add(solutionPanels[0]);
		add(solutionPanels[1]);
	}
}

//panel for equation
//has max displayable length
//has equation form
//has display and number panel
//has coefficients variables
//has script engine manager and Javascipt engine
class EquationPanel extends JPanel{
	final int DISPLAY_LENGTH = 25;
	JLabel equationForm = new JLabel("a*x^2 + b*x + c = 0");
	EquationDisplay eqnDisplay = new EquationDisplay(); 
	NumberPanel numberPad = new NumberPanel();
	String[] coefficients = {"","",""};
	ScriptEngineManager manager = new ScriptEngineManager();
	ScriptEngine engine = manager.getEngineByName("js");
	
	//function that converts constants to Java(script) form
	protected String constToJavaExpression(String str) {
		String newStr = "";
		String subStr = "";
		for(int i=0; i<str.length();i++) {
			subStr = str.substring(i, i+1);
			if(subStr.equals("P")) {
				newStr += "Math.PI";
			}
			else if(subStr.equals("I")) {
				
			}
			else if(subStr.equals("e")) {
				newStr += "Math.E";
			}
			else {
				newStr += subStr;
			}
		}
		return newStr;
	}
	//constructor
	//sets font of equation form
	//adds form, display, number pad
	//set layout as verticla boxes
	//add button listener to buttons
	public EquationPanel() {
		equationForm.setFont(new Font("Helvetica", Font.BOLD, 15));
		add(equationForm);
		add(eqnDisplay);
		add(numberPad);
		setLayout( new BoxLayout(this, BoxLayout.Y_AXIS));
		for(String key: numberPad.buttonMap.keySet()) {
			numberPad.buttonMap.get(key).addActionListener(new ButtonListener());
		}
	}
	//button listener
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			//case for calculate button
			//convert coefficient string to floating value
			//find determinant value
			if( ( (JButton)event.getSource() ).getText().equals("calculate")) {
				try {
					double a, b, c, det, sol1, sol2;
					a = Double.parseDouble(""+engine.eval(constToJavaExpression(coefficients[0])));
					b = Double.parseDouble(""+engine.eval(constToJavaExpression(coefficients[1])));
					c = Double.parseDouble(""+engine.eval(constToJavaExpression(coefficients[2])));
					det = b*b-4*a*c;
					//if coefficient of 2 degree is 0 then equation is 1 degree
					//so solution 2 is non-applicable(N/A)
					if(a == 0) {
						if(b == 0) {
							//0*x = 0 then x is indetermined
							if(c==0) {
								eqnDisplay.solutions[0].setText("indetermined");
								eqnDisplay.solutions[1].setText("N/A(1 degree eqn)");
							}
							//0*x = nonzero then x is impossible
							else {
								eqnDisplay.solutions[0].setText("impossible");
								eqnDisplay.solutions[1].setText("N/A(1 degree eqn)");
							}
						}
						//nonzero*x = nonzero then x is a real number
						else {
							eqnDisplay.solutions[0].setText(String.format("%-25g", -c/b));
							eqnDisplay.solutions[1].setText("N/A(1 degree eqn)");
						}
					}
					//case for 2 degree equation
					//if determinant is negative then x is a complex number
					//complex solution is form of -b/2/a + sqrt(-det)/2/a*i or -b/2/a - sqrt(-det)/2/a*i
					//which i is imaginary number
					else if(det<0) {
						eqnDisplay.solutions[0].setText(String.format("%-10g+%-10g*i", -b/2/a, Math.sqrt(-det)/2/a));
						eqnDisplay.solutions[1].setText(String.format("%-10g-%-10g*i", -b/2/a, Math.sqrt(-det)/2/a));
					}
					//if determinant is non-negative then x is a complex number
					//real solution is form of (-b+Math.sqrt(det))/2/a or (-b-Math.sqrt(det))/2/a
					else {
						sol1 = (-b+Math.sqrt(det))/2/a;
						sol2 = (-b-Math.sqrt(det))/2/a;
						//eqnDisplay.solutions[0].setText(""+sol1);
						//eqnDisplay.solutions[1].setText(""+sol2);
						eqnDisplay.solutions[0].setText(String.format("%-25g", sol1));
						eqnDisplay.solutions[1].setText(String.format("%-25g", sol2));
					}
				}
				//case that constants field has incorrect syntax
				//solution fields alert it
				catch(Exception e) {
					eqnDisplay.solutions[0].setText("incorrect syntax");
					eqnDisplay.solutions[1].setText("incorrect syntax");
				}
			}
			//case for clear all button
			//set all fields as null string
			else if( ( (JButton)event.getSource() ).getText().equals("clear all") ) {
				for(int i=0; i<3; i++) {
					coefficients[i] = "";
					eqnDisplay.fieldArr[i].setText("");
				}
				for(int i=0; i<2; i++) {
					eqnDisplay.solutions[i].setText("");
				}
			}
			//case for backspace button
			//delete only one character
			//exception of PI
			//PI is deleted at once
			//redisplay new string in range of DISPLAY_LENGTH
			else if( ( (JButton)event.getSource() ).getText().equals("backspace") ) {
				int i;
				for(i=0; i<3; i++) {
					if(eqnDisplay.radioBtn[i].isSelected()) break;
				}
				if(coefficients[i].length()<=0) return;
				if(coefficients[i].substring(coefficients[i].length()-1).equals("I")) {
					coefficients[i]  = coefficients[i].substring(0,coefficients[i].length()-2);
				}
				else
					coefficients[i]  = coefficients[i].substring(0,coefficients[i].length()-1);
				
				if(coefficients[i].length() < DISPLAY_LENGTH) {
					eqnDisplay.fieldArr[i].setText(coefficients[i]);
				}
				else {
					eqnDisplay.fieldArr[i].setText(coefficients[i].substring(coefficients[i].length()-DISPLAY_LENGTH, coefficients[i].length()));
				}
			}
			//the other case
			//just add one character
			//redisplay new string in range of DISPLAY_LENGTH
			else {
				String newString = ( (JButton)event.getSource() ).getText();
				int i;
				for(i=0; i<3; i++) {
					if(eqnDisplay.radioBtn[i].isSelected()) break;
				}
				coefficients[i] += newString;
				if(coefficients[i].length() < DISPLAY_LENGTH) {
					eqnDisplay.fieldArr[i].setText(coefficients[i]);
				}
				else {
					eqnDisplay.fieldArr[i].setText(coefficients[i].substring(coefficients[i].length()-DISPLAY_LENGTH, coefficients[i].length()));
				}
			}
		}
	}
}

//number panel for integral and differentiation
class NumVarPanel extends ButtonPanel{
	public NumVarPanel() {
		//by removing and putting, rearrange buttons
		remove(buttonMap.get("="));
		remove(buttonMap.get("%"));
		remove(buttonMap.get("!"));
		buttonMap.remove("=");
		buttonMap.remove("%");
		buttonMap.remove("!");
		//add x button for variable of function
		buttonMap.put("x", new JButton("x"));
		buttonMap.put("calculate", new JButton("calculate"));
		buttonMap.put("clear all", new JButton("clear all"));
		add(buttonMap.get("x"));
		add(buttonMap.get("calculate"));
		add(buttonMap.get("clear all"));
		remove(buttonMap.get("backspace"));
		add(buttonMap.get("backspace"));
	}
}

//panel for integral
//has maximum displayable length
//has radio buttons for fields
//fields are for function, lower bound, upper bound, allowable error and integral result
//panel has number pad, buttongroup for radio buttons and variable for each field, and token list for entering function expression
//hqw script manager and Javascript engine
class IntegralPanel extends JPanel{
	final int DISPLAY_LENGTH = 30;
	JPanel[] rowArr = {new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel()};
	JRadioButton[] radioArr = {new JRadioButton("function:"), new JRadioButton("lower bound:"), new JRadioButton("upper bound:"), new JRadioButton("allowable error:"), };
	JLabel[] labelArr = {new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel()};
	NumVarPanel numPad = new NumVarPanel();
	ButtonGroup btnGroup = new ButtonGroup();
	String[] values = {"","","","",""};
	ArrayList<String> tokenList = new ArrayList<String>();
	
	ScriptEngineManager manager = new ScriptEngineManager();
	ScriptEngine engine = manager.getEngineByName("js");
	
	//constructor
	//define factorial function in the engine
	//add radio button, label to row panel
	//add radio button to button group
	//set size and border of labels
	//modify text of last row as integral value:
	//set default radio button value
	//add number pad and button listener to buttons
	//set layout as vertical boxes
	public IntegralPanel() {
		try {
			engine.eval(
					"function factorial(n){"
					+ "if( (parseInt(n)-n) != 0) throw '';"
					+ "if( n<0 ) throw '';"
					+ "if(n==0) return 1;"
					+ "var res = 1;"
					+ "for(i=1; i<=n; i++) res*=i;"
					+ "return res;"
					+ "}"
					+ "function log10(n){"
					+ "return (1/Math.log(10))*Math.log(n);"
					+ "}");
		}
		catch(Exception e) {
		}
		for(int i=0; i<radioArr.length; i++) {
			add(rowArr[i]);
			rowArr[i].add(radioArr[i]);
			rowArr[i].add(labelArr[i]);
			labelArr[i].setBorder(new LineBorder(Color.BLACK, 1));
			labelArr[i].setPreferredSize(new Dimension(200, 40));
			btnGroup.add(radioArr[i]);
		}
		labelArr[labelArr.length-1].setBorder(new LineBorder(Color.BLACK, 1));
		labelArr[labelArr.length-1].setPreferredSize(new Dimension(200, 40));
		add(rowArr[rowArr.length-1]);
		rowArr[rowArr.length-1].add(new JLabel("integral value:"));
		rowArr[rowArr.length-1].add(labelArr[labelArr.length-1]);
		radioArr[0].setSelected(true);
		add(numPad);
		setLayout( new BoxLayout(this, BoxLayout.Y_AXIS));
		for(String key: numPad.buttonMap.keySet()) {
			numPad.buttonMap.get(key).addActionListener(new ButtonListener());
		}
	}
	//function that sets texts on fields in range of DISPLAY_LENGTH
	protected void setAllLabelText() {
		for(int i=0; i<values.length; i++) {
			if(values[i].length() > DISPLAY_LENGTH) {
				labelArr[i].setText(values[i].substring(values[i].length()-DISPLAY_LENGTH, values[i].length()));
			}
			else {
				labelArr[i].setText(values[i]);
			}
		}
	}
	
	//function that substitute character x with a number on function expression 
	protected String substitute(String func, String source) {
		String newStr = "";
		for(int i=0; i<func.length(); i++) {
			if(func.substring(i, i+1).equals("x")) {
				newStr += source;
			}
			else {
				newStr += func.substring(i, i+1);
			}
		}
		return newStr;
	}
	//function that evaluate function value at x
	protected Object funcVal(String func, double x) throws ScriptException {
		return engine.eval(substitute(func,"("+x+")"));
	}
	//calculate integral from lower to upper
	//interval is divided as numOfSub subintervals
	//height of subinterval is average of left end and right end of subinterval
	//area value of subinterval is the height * length of subinterval
	//returns sum of all area values of subintervals
	private double trapJoidalIntegral(String func, double lower, double upper, int numOfSub) throws NumberFormatException, ScriptException {
		double intervalLen = upper-lower;
		double delta = intervalLen/numOfSub;
		double subSum;
		double sum=0;
		double leftVal, rightVal;
		for(double startPoint = lower; startPoint< upper; startPoint+=delta) {
			leftVal = Double.parseDouble(""+funcVal(func, startPoint));
			rightVal = Double.parseDouble(""+funcVal(func, startPoint+delta));
			subSum = (leftVal+rightVal)/2*delta;
			sum += subSum;
		}
		return sum;
	}
	//below romberg method make new even better integral value
	//with 1 bad integral(interval length = h) and 1 better integral(interval length = h/2)
	//iterate that process until relative error is under some value
	protected double romberg(String func,double a, double b, double es) throws NumberFormatException, ScriptException {
		//square list of integral value
		ArrayList<ArrayList<Double>> I = new ArrayList<ArrayList<Double>>();
		//we use index from 1 not 0
		int n = 1;
		//prepare storing room for first integral value
		I.add(new ArrayList<Double>());
		I.add(new ArrayList<Double>());
		I.get(1).add(0.0);
		I.get(1).add(0.0);
		//store the first result of integral in the room
		I.get(1).set(1,trapJoidalIntegral(func, a, b, n));
		int iter = 0;
		int k, j;
		double ea;
		do {
			//increase iteration variable
			iter++;
			//increase number of subintervals by 2 times
			n = (int)Math.pow(2, iter);
			//prepare storing room for new integral value
			if(I.size()-1<iter+1) {
				while(I.size()-1<iter+1) {
					I.add(new ArrayList<Double>());
				}
			}
			if(I.get(iter+1).size()-1<1) {
				while(I.get(iter+1).size()-1<1) {
					I.get(iter+1).add(0.0);
				}
			}
			//store new integral value
			I.get(iter+1).set(1, trapJoidalIntegral(func, a, b, n));
			
			//make new better integral value with two close integral value, one is bad, another is better than it
			for(k=2; k<=iter+1; k++) {
				j = 2+ iter-k;
				//prepare storing room for new integral value
				if(I.size()-1<j) {
					while(I.size()-1<j) {
						I.add(new ArrayList<Double>());
					}
				}
				if(I.get(j).size()-1<k) {
					while(I.get(j).size()-1<k) {
						I.get(j).add(0.0);
					}
				}
				// new better integral value: I(j, k) = ( 4^(k-1)*I(j+1,k-1) - I(j,k-1) ) / ( 4^(k-1) - 1 )
				I.get(j).set(k, ( Math.pow(4,k-1)*I.get(j+1).get(k-1)-I.get(j).get(k-1) ) / (Math.pow(4,k-1)-1) );
			}
			//calculate relative error
			ea = Math.abs( (I.get(1).get(iter+1)-I.get(2).get(iter))/I.get(1).get(iter+1))*100;
		}while(ea > es);//if relative error is less than or equal to es then end loop
		//return I(1,iter+1)
		return I.get(1).get(iter+1);
	}
	
	//function that convert token list to Java(scipt) form string
	protected String toJavaExpression(ArrayList<String> tokenList) {
		String javaExpression = "";
		for(String token: tokenList) {
			switch(token) {
			case "sin(":
				javaExpression += "Math.sin(";
				break;
			case "cos(":
				javaExpression += "Math.cos(";
				break;
			case "tan(":
				javaExpression += "Math.tan(";
				break;
			case "asin(":
				javaExpression += "Math.asin(";
				break;
			case "acos(":
				javaExpression += "Math.acos(";
				break;
			case "atan(":
				javaExpression += "Math.atan(";
				break;
			case "ln(":
				javaExpression += "Math.log(";
				break;
			case "log(":
				javaExpression += "log10(";
				break;
			case "sqrt(":
				javaExpression += "Math.sqrt(";
				break;
			case "power(":
				javaExpression += "Math.pow(";
				break;
			case "PI":
				javaExpression += "Math.PI";
				break;
			case "e":
				javaExpression += "Math.E";
				break;
			default:
				javaExpression += token;
			}
		}
		return javaExpression;
	}
	//function that converts constants to Java(scipt) form 
	protected String constToJavaExpression(String str) {
		String newStr = "";
		String subStr = "";
		for(int i=0; i<str.length();i++) {
			subStr = str.substring(i, i+1);
			if(subStr.equals("P")) {
				newStr += "Math.PI";
			}
			else if(subStr.equals("I")) {
				
			}
			else if(subStr.equals("e")) {
				newStr += "Math.E";
			}
			else {
				newStr += subStr;
			}
		}
		return newStr;
	}
	//button listener
	protected class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			//case for clear all
			//set all values as null string and make token list empty
			if(event.getSource().equals(numPad.buttonMap.get("clear all"))){
				for(int i=0; i<values.length; i++) {
					values[i] = "";
				}
				setAllLabelText();
				tokenList = new ArrayList<String>();
			}
			//case for backspace
			//fine field where radio button is selected
			//and delete 1 character
			//but if deleted character is I of PI then delete P together
			else if(event.getSource().equals(numPad.buttonMap.get("backspace"))) {
				for(int i=0; i<radioArr.length; i++) {
					if(radioArr[i].isSelected()) {
						if(i==0) {
							if(tokenList.size()>0) {
								tokenList.remove(tokenList.size()-1);
								values[i] = "";
								for(String token: tokenList) {
									values[i] += token;
								}
							}
						}
						else {
							if(values[i].length()>0) {
								if(values[i].substring(values[i].length()-1).equals("I")) {
									values[i] = values[i].substring(0, values[i].length()-2);
								}
								else {
									values[i] = values[i].substring(0, values[i].length()-1);
								}
								
							}
						}
					}
				}
				setAllLabelText();
			}
			
			//case for calculate
			else if(event.getSource().equals(numPad.buttonMap.get("calculate"))) {
				try {
					//if upper bound value is not bigger than lower bound then alert it
					if(Double.parseDouble(""+engine.eval(constToJavaExpression(values[1])))>=Double.parseDouble(""+engine.eval(constToJavaExpression(values[2])))) {
						values[4] ="upper bound must be bigger";
					}
					//else, invoke romberg method and calculate the integral value and store it
					else {
						String func = toJavaExpression(tokenList);
						
						double lower = Double.parseDouble(""+engine.eval(constToJavaExpression(values[1])));
						double upper = Double.parseDouble(""+engine.eval(constToJavaExpression(values[2])));
						double es = Double.parseDouble(values[3]);
						double integralVal = romberg(func, lower, upper, es);
						//values[4] = Double.toString(integralVal);
						values[4] = String.format("%-30g", integralVal);
					};
				}
				//if incorrect syntax is in a field, alert it
				catch(Exception e) {
					values[4] = "incorrect syntax";
				}
				setAllLabelText();
			}
			//case for the other buttons
			else {
				//find field where radio button is selected
				for(int i=0; i<radioArr.length; i++) {
					if(radioArr[i].isSelected()) {
						//case for function expression field
						//add entered token
						//if token is a function name, add it with opening bracket
						if(i==0) {
							String buttonText = ( (JButton)event.getSource() ).getText();
							switch(buttonText) {
							case "sin":
							case "cos":
							case "tan":
							case "asin":
							case "acos":
							case "atan":
							case "sqrt":
							case "power":
							case "ln":
							case "log":
								tokenList.add(buttonText+"(");
								break;
							default:
								tokenList.add(buttonText);
							}
							values[i] = "";
							for(String token: tokenList) {
								values[i] += token;
							}
						}
						//case for lower or upper bound
						//just add number or -,*,/ operator or constants
						else if(i==1 || i==2){
							String buttonText = ( (JButton)event.getSource() ).getText();
							if(
									buttonText.equals("1")
									||buttonText.equals("2")
									||buttonText.equals("3")
									||buttonText.equals("4")
									||buttonText.equals("5")
									||buttonText.equals("6")
									||buttonText.equals("7")
									||buttonText.equals("8")
									||buttonText.equals("9")
									||buttonText.equals("0")
									||buttonText.equals(".")
									||buttonText.equals("-")
									||buttonText.equals("*")
									||buttonText.equals("/")
									||buttonText.equals("PI")
									||buttonText.equals("e")) {
								values[i] += buttonText;
							}
;						}
						//case for allowable error
						//add only numbers or dot
						else {
							String buttonText = ( (JButton)event.getSource() ).getText();
							if(
									buttonText.equals("1")
									||buttonText.equals("2")
									||buttonText.equals("3")
									||buttonText.equals("4")
									||buttonText.equals("5")
									||buttonText.equals("6")
									||buttonText.equals("7")
									||buttonText.equals("8")
									||buttonText.equals("9")
									||buttonText.equals("0")
									||buttonText.equals(".")) {
								values[i] += buttonText;
							}
						}
						setAllLabelText();
					}
				}
			}
		}
	}
}

//panel for differentiation extends integral panel
class DiffPanel extends IntegralPanel{
	//function that centered difference
	//parameters are function and x value and radius of neighborhood
	private double centeredDifference(String func, double x, double neighborRadius) throws NumberFormatException, ScriptException {
		double deltaX = 2*neighborRadius;
		double leftVal = Double.parseDouble(""+funcVal(func, x-neighborRadius));
		double rightVal = Double.parseDouble(""+funcVal(func, x+neighborRadius));
		double diff = (rightVal - leftVal)/deltaX;
		return diff;
	}
	//function for Richardson interpolation
	//this method almost same with Romberg method
	//but this method use differentiation of 2 delta x
	//one is that delta x = h and the other is that delta x = h/2
	private double richardson(String func, double x, double neighborRadius, double es) throws NumberFormatException, ScriptException {
		//prepare store room for first differentiation
		ArrayList<ArrayList<Double>> I = new ArrayList<ArrayList<Double>>();
		int n = 1;
		I.add(new ArrayList<Double>());
		I.add(new ArrayList<Double>());
		I.get(1).add(0.0);
		I.get(1).add(0.0);
		//store first differentiation
		I.get(1).set(1,centeredDifference(func, x, neighborRadius));
		int iter = 0;
		int k, j;
		double ea;
		do {
			//increase iteration variable
			iter++;
			//make bigger divider of delta x
			n = (int)Math.pow(2, iter);
			//prepare store room for new differentiation
			if(I.size()-1<iter+1) {
				while(I.size()-1<iter+1) {
					I.add(new ArrayList<Double>());
				}
			}
			if(I.get(iter+1).size()-1<1) {
				while(I.get(iter+1).size()-1<1) {
					I.get(iter+1).add(0.0);
				}
			}
			//store new differentiation
			I.get(iter+1).set(1, centeredDifference(func, x, neighborRadius/n));
			//prepare store room for new differentiation
			for(k=2; k<=iter+1; k++) {
				j = 2+ iter-k;
				if(I.size()-1<j) {
					while(I.size()-1<j) {
						I.add(new ArrayList<Double>());
					}
				}
				if(I.get(j).size()-1<k) {
					while(I.get(j).size()-1<k) {
						I.get(j).add(0.0);
					}
				}
				//I(j,k) = ( 4^(k-1)*I(j+1,k-1)-I(j,k-1) ) / ( 4^(k-1) -1 )
				I.get(j).set(k, ( Math.pow(4,k-1)*I.get(j+1).get(k-1)-I.get(j).get(k-1) ) / (Math.pow(4,k-1)-1) );
				//System.out.println(I.get(j).get(k));
			}
			//calculate relative error
			ea = Math.abs( (I.get(1).get(iter+1)-I.get(2).get(iter))/I.get(1).get(iter+1))*100;
		}while(ea > es);//if relative error is less then or equal to es then end loop
		//return I(1, iter+1)
		return I.get(1).get(iter+1);
	}
	//constructor
	//change texts as differentiation form
	public DiffPanel() {
		radioArr[1].setText("x value:");
		radioArr[2].setText("initial neighbor radius:");
		rowArr[rowArr.length-1].remove(0);
		rowArr[rowArr.length-1].add(new JLabel("differentiation value:"), 0);
		ActionListener al;
		//remove all integral button listeners and add new differentiation button listener
		for(String key: numPad.buttonMap.keySet()) {
			al = numPad.buttonMap.get(key).getActionListeners()[0];
			numPad.buttonMap.get(key).removeActionListener(al);
			numPad.buttonMap.get(key).addActionListener(new DiffButtonListener());
		}
	}
	//button listener for differentiation
	protected class DiffButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			//case for clear all
			//delete all values and make token list empty
			if(event.getSource().equals(numPad.buttonMap.get("clear all"))){
				for(int i=0; i<values.length; i++) {
					values[i] = "";
				}
				setAllLabelText();
				tokenList = new ArrayList<String>();
			}
			//case for backspace
			//find field where radio button is selected
			//delete one character there
			//if deleted character is I then delete P together
			else if(event.getSource().equals(numPad.buttonMap.get("backspace"))) {
				for(int i=0; i<radioArr.length; i++) {
					if(radioArr[i].isSelected()) {
						if(i==0) {
							if(tokenList.size()>0) {
								tokenList.remove(tokenList.size()-1);
								values[i] = "";
								for(String token: tokenList) {
									values[i] += token;
								}
							}
						}
						else {
							if(values[i].length()>0) {
								if(values[i].substring(values[i].length()-1).equals("I")) {
									values[i] = values[i].substring(0, values[i].length()-2);
								}
								else {
									values[i] = values[i].substring(0, values[i].length()-1);
								}
							}
						}
					}
				}
				setAllLabelText();
			}
			//case for calculate
			//convert expressions to floating numbers
			//invoke Richardson method and calculate differentiation and store it
			//if field has incorrect syntax or neighbor radius is 0 then alert it
			else if(event.getSource().equals(numPad.buttonMap.get("calculate"))) {
				try {
					String func = toJavaExpression(tokenList);
					double x = Double.parseDouble(""+engine.eval(constToJavaExpression(values[1])));
					double neighborRadius = Double.parseDouble(""+engine.eval(constToJavaExpression(values[2])));
					double es = Double.parseDouble(values[3]);
					double diffVal = richardson(func, x, neighborRadius, es);
					if(neighborRadius==0) {
						values[4] = "incorrect syntax";
						setAllLabelText();
						return;
					}
					//values[4] = Double.toString(diffVal);
					values[4] = String.format("%-30g", diffVal);
					
				}
				catch(Exception e) {
					values[4] = "incorrect syntax";
				}
				setAllLabelText();
			}
			//case for the other buttons
			else {
				//fine field where radio button is selected
				for(int i=0; i<radioArr.length; i++) {
					if(radioArr[i].isSelected()) {
						//case for function expression field
						//add a token as button text
						//if text is a function name then add opening bracket together
						if(i==0) {
							String buttonText = ( (JButton)event.getSource() ).getText();
							switch(buttonText) {
							case "sin":
							case "cos":
							case "tan":
							case "asin":
							case "acos":
							case "atan":
							case "sqrt":
							case "power":
							case "ln":
							case "log":
								tokenList.add(buttonText+"(");
								break;
							default:
								tokenList.add(buttonText);
							}
							values[i] = "";
							for(String token: tokenList) {
								values[i] += token;
							}
						}
						//case for x value
						//just numbers and constants and -,*,/ are added
						else if(i==1){
							String buttonText = ( (JButton)event.getSource() ).getText();
							if(
									buttonText.equals("1")
									||buttonText.equals("2")
									||buttonText.equals("3")
									||buttonText.equals("4")
									||buttonText.equals("5")
									||buttonText.equals("6")
									||buttonText.equals("7")
									||buttonText.equals("8")
									||buttonText.equals("9")
									||buttonText.equals("0")
									||buttonText.equals(".")
									||buttonText.equals("-")
									||buttonText.equals("*")
									||buttonText.equals("/")
									||buttonText.equals("PI")
									||buttonText.equals("e")) {
								values[i] += buttonText;
							}
;						}
						//case for the other fields
						//just positive number is added
						else {
							String buttonText = ( (JButton)event.getSource() ).getText();
							if(
									buttonText.equals("1")
									||buttonText.equals("2")
									||buttonText.equals("3")
									||buttonText.equals("4")
									||buttonText.equals("5")
									||buttonText.equals("6")
									||buttonText.equals("7")
									||buttonText.equals("8")
									||buttonText.equals("9")
									||buttonText.equals("0")
									||buttonText.equals(".")) {
								values[i] += buttonText;
							}
						}
						setAllLabelText();
					}
				}
			}
		}
	}
}