package gui;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JSeparator;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class ConnectionPanel extends MainPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtURL;
	private JTextField textUserName;
	private JPasswordField passwordField;
	private JTextField txtPort;
	private JTextField txtREST;
	
	private JRadioButton rdbtnHttp = new JRadioButton("http");
	private JRadioButton rdbtnHttps = new JRadioButton("https");
	private JTextField txtOldDP;
	private JTextField txtNewDP;
	private JTextField txtPath;
	private JTextField txtFileName;
	private JTextField textFolderId;
	private JTextField txtReportId;

	/**
	 * Create the panel.
	 */

	public ConnectionPanel(BOParentFrame bopf) {
		super(bopf);	
		setBorder(new TitledBorder(null, "Connection details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(null);
		
		txtURL = new JTextField();
		txtURL.setText("VMBI42sp4");
		txtURL.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				  changeFilename();
				  changeRESTfulWS();
			  }
			  public void removeUpdate(DocumentEvent e) {
				  changeFilename();
				  changeRESTfulWS();
			  }
			  public void insertUpdate(DocumentEvent e) {
				  changeFilename();
				  changeRESTfulWS();
			  }

			  public void warn() {
			     if (Integer.parseInt(txtURL.getText())<=0){
			       JOptionPane.showMessageDialog(null,
			          "Error: Please enter number bigger than 0", "Error Massage",
			          JOptionPane.ERROR_MESSAGE);
			     }
			  }
			});


		txtURL.setBounds(95, 22, 227, 20);
		add(txtURL);
		txtURL.setColumns(10);
		
		JLabel lblServerURL = new JLabel("Server URL:");
		lblServerURL.setToolTipText("e.g. botst01 or 10.192.154.10 ");
		lblServerURL.setBounds(10, 25, 75, 14);
		add(lblServerURL);
		
		JLabel lblUserName = new JLabel("User Name:");
		lblUserName.setBounds(10, 118, 75, 14);
		add(lblUserName);
		
		textUserName = new JTextField();
		textUserName.setText("Administrator");
		textUserName.setBounds(95, 115, 227, 20);
		textUserName.setColumns(10);
		add(textUserName);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 146, 75, 14);
		add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(95, 143, 227, 20);
		add(passwordField);
		
		txtPort = new JTextField();
		txtPort.setText("6405");
		txtPort.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				  changeRESTfulWS();
			  }
			  public void removeUpdate(DocumentEvent e) {
				  changeRESTfulWS();
			  }
			  public void insertUpdate(DocumentEvent e) {
				  changeRESTfulWS();
			  }

			  public void warn() {
			     if (Integer.parseInt(txtPort.getText())<=0){
			       JOptionPane.showMessageDialog(null,
			          "Error: Please enter number bigger than 0", "Error Massage",
			          JOptionPane.ERROR_MESSAGE);
			     }
			  }
			});
		txtPort.setColumns(10);
		txtPort.setBounds(95, 53, 227, 20);
		add(txtPort);
		
		JLabel lblPort = new JLabel("Server Port:");
		lblPort.setBounds(10, 56, 75, 14);
		add(lblPort);
		
		txtREST = new JTextField();
		txtREST.setColumns(10);
		txtREST.setBounds(95, 84, 227, 20);
		add(txtREST);
		
		JLabel lblREST = new JLabel("RESTful WS:");
		lblREST.setBounds(10, 87, 75, 14);
		add(lblREST);
		
		rdbtnHttp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnHttpActionPerformed(e);
			}
		});
		
		rdbtnHttp.setBounds(328, 21, 56, 23);
		add(rdbtnHttp);
		rdbtnHttp.setSelected(true);
		rdbtnHttps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnHttpsActionPerformed(e);
			}
		});
		
		
		rdbtnHttps.setBounds(328, 52, 56, 23);
		add(rdbtnHttps);
		
		JLabel lblSettings = new JLabel("Settings");
		lblSettings.setBounds(10, 173, 46, 14);
		add(lblSettings);
		
		JLabel lblOldDP = new JLabel("Old DP:");
		lblOldDP.setBounds(10, 199, 56, 14);
		add(lblOldDP);
		
		txtOldDP = new JTextField();
		txtOldDP.setColumns(10);
		txtOldDP.setBounds(70, 196, 97, 20);
		add(txtOldDP);
		
		JLabel lblNewDP = new JLabel("New DP:");
		lblNewDP.setBounds(10, 227, 56, 14);
		add(lblNewDP);
		
		txtNewDP = new JTextField();
		txtNewDP.setColumns(10);
		txtNewDP.setBounds(70, 224, 97, 20);
		add(txtNewDP);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(76, 179, 328, 2);
		add(separator);
		
		txtPath = new JTextField();
		txtPath.setColumns(10);
		txtPath.setBounds(225, 224, 123, 20);
		add(txtPath);
		txtPath.setText(System.getProperty("user.dir"));
		
		JLabel lblPath = new JLabel("File:");
		lblPath.setBounds(193, 199, 41, 14);
		add(lblPath);
		
		JLabel lblFileName = new JLabel("Path:");
		lblFileName.setBounds(193, 227, 41, 14);
		add(lblFileName);
		
		txtFileName = new JTextField();
		txtFileName.setText("URL_FolderID.xlsx");
		txtFileName.setColumns(10);
		txtFileName.setBounds(225, 196, 123, 20);
		add(txtFileName);
		
		JButton btnGetPath = new JButton("...");
		btnGetPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPathChooser(e);
			}
		});
		btnGetPath.setBounds(358, 223, 26, 23);
		add(btnGetPath);
		
		JButton btnGetFile = new JButton("...");
		btnGetFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showFileChooser(e);
			}
		});
		btnGetFile.setBounds(358, 195, 26, 23);
		add(btnGetFile);
		
		JLabel lblFolderId = new JLabel("Folder ID:");
		lblFolderId.setBounds(10, 255, 56, 14);
		add(lblFolderId);
		
		textFolderId = new JTextField();
		textFolderId.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				  changeFilename();
			  }
			  public void removeUpdate(DocumentEvent e) {
				  changeFilename();
			  }
			  public void insertUpdate(DocumentEvent e) {
				  changeFilename();
			  }

			  public void warn() {
			     if (Integer.parseInt(textFolderId.getText())<=0){
			       JOptionPane.showMessageDialog(null,
			          "Error: Please enter number bigger than 0", "Error Massage",
			          JOptionPane.ERROR_MESSAGE);
			     }
			  }
			});
		textFolderId.setColumns(10);
		textFolderId.setBounds(70, 252, 97, 20);
		add(textFolderId);
		
		JLabel lblReportId = new JLabel("Report ID:");
		lblReportId.setBounds(191, 255, 56, 14);
		add(lblReportId);
		
		txtReportId = new JTextField();
		txtReportId.setColumns(10);
		txtReportId.setBounds(251, 252, 97, 20);
		add(txtReportId);
		
	}
	
	private void rdbtnHttpActionPerformed(ActionEvent e){
		rdbtnHttps.setSelected(false);
		changeRESTfulWS();
	};
	
	private void rdbtnHttpsActionPerformed(ActionEvent e){
		rdbtnHttp.setSelected(false);
		changeRESTfulWS();
	}
	
	private void changeFilename(){
		this.txtFileName.setText(this.txtURL.getText()+"_"+
								 this.textFolderId.getText()+".xlsx");
	}
	
	private void changeRESTfulWS(){
		
		String restWs = this.getHttp()+"://"+this.txtURL.getText()+":"+	this.txtPort.getText()+"/biprws";
		
		this.txtREST.setText(restWs);
	}
	
	public void showFileChooser(ActionEvent e){
		String filename = File.separator + "tmp";
		JFileChooser fc = new JFileChooser(new File(filename));
		
		fc.showSaveDialog(this);
		File selFile = fc.getSelectedFile();
		File selpath = fc.getCurrentDirectory();
		this.txtFileName.setText(selFile.getName());
		this.txtPath.setText(selpath.getAbsolutePath());
	}
	
	public void showPathChooser(ActionEvent e){
		String filename = File.separator + "tmp";
		JFileChooser fc = new JFileChooser(new File(filename));
		
		fc.showSaveDialog(this);
		File selpath = fc.getCurrentDirectory();
		this.txtPath.setText(selpath.getAbsolutePath());
	}

	/*Getters Setters*/
	public String getTxtURL() {
		return txtURL.getText();
	}

	public String getTextUserName() {
		return textUserName.getText();
	}

	public String getPasswordField() {
		return new String(passwordField.getPassword());
	}


	public String getTxtPort() {
		return txtPort.getText();
	}

	public String getTxtREST() {
		return txtREST.getText();
	}

	public String getHttp() {
		if (rdbtnHttps.isSelected())
			return "https";
		else 
			return "http";
	}


	public String getTxtOldDP() {
		return txtOldDP.getText();
	}

	public String getTxtNewDP() {
		return txtNewDP.getText();
	}

	public String getTxtPath() {
		return txtPath.getText();
	}

	public String getTxtFileName() {
		return txtFileName.getText();
	}

	public String getTextFolderId() {
		return textFolderId.getText();
	}

	public String getTxtReportId() {
		return txtReportId.getText();
	}
	
	
	
}
