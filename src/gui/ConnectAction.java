package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class ConnectAction extends MainAction {
	
	public ConnectAction(BOParentFrame bopf) {
		super(bopf);
		// TODO Auto-generated constructor stub
		//setBackground(Color.GREEN);
		super.bopf=bopf;
		setLayout(null);
		this.setBounds(super.getBounds());
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnExitActionPerformed(e);
			}
		});
		btnExit.setBounds(315, 15, 89, 23);
		add(btnExit);
		
		JButton btnReplace = new JButton("Replace");
		btnReplace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnReplaceActionPerformed(e);
			}
		});
		btnReplace.setBounds(216, 15, 89, 23);
		add(btnReplace);
		
		JButton btnPurge = new JButton("Purge");
		btnPurge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPurgeActionPerformed(e);
			}
		});
		btnPurge.setBounds(117, 15, 89, 23);
		add(btnPurge);
		
		JButton btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnExportActionPerformed(e);
			}
		});
		btnExport.setBounds(18, 15, 89, 23);
		add(btnExport);
	}
	
	private void btnExitActionPerformed(ActionEvent evt){
		this.bopf.dispose();
	}

	private void btnReplaceActionPerformed(ActionEvent evt){
		this.bopf.setConnectionProperties();
		this.bopf.replaceDataProvider();
	}

	private void btnPurgeActionPerformed(ActionEvent evt){
		this.bopf.setConnectionProperties();
		this.bopf.purgeReports();
	}
	
	private void btnExportActionPerformed(ActionEvent evt){
		this.bopf.setConnectionProperties();
		this.bopf.setFilenames();
		this.bopf.excelExport();
		this.bopf.sqlExport();
	}

}
