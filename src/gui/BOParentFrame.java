package gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import boextraction.DProvider;
import boextraction.ExcelExport;
import boextraction.XMLReader;
import utils.FreeHandSQLExport;

public class BOParentFrame extends JFrame {
	
    private String adminUn ="";
    private String adminPw ="";
    private String url ="";
    private String restWs ="";
    private String httpType ="";
    private String port ="";
    private String excelFileName ="";
    private String sqlFileName ="";
    private String exportPath ="";
    private String folderId = "";
    
    public List<String> reportList = new ArrayList<>(0);
    private XMLReader xmlr = new XMLReader();  
     
    private ConnectionPanel jpl;
	private JPanel contentPane;	
	private JPanel pnl_main;
	private JPanel pnl_action;
	
	private JPanel txt = new ConnectionPanel(this);
	private JPanel btn = new ConnectAction(this);

	/**
	 * Create the frame.
	 */
	public BOParentFrame() {
		setTitle("Business Objects  4.1 WebAPI front end");
			    
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 445, 371);
		this.setMaximumSize(new Dimension(445, 371));
		this.setMinimumSize(new Dimension(445, 371));
		this.setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);	
		pnl_action = new JPanel();
		pnl_action.setBounds(10, 295, 416, 49);
		contentPane.add(pnl_action);
		pnl_action.setLayout(new CardLayout(0, 0));
		
		pnl_main = new JPanel();
		pnl_main.setBounds(10, 10, 416, 285);
		contentPane.add(pnl_main);
		pnl_main.setLayout(new CardLayout(0, 0));
		
		this.setContentPane(contentPane);
		this.pnl_main.add(txt);
		this.pnl_action.add(btn);
		
	}
	
    public void addPanelInMain(JPanel txt){
        pnl_main.removeAll();
        pnl_main.add(txt);
        pnl_main.repaint();
        pnl_main.revalidate();
    }
    
    public void addPanelInAction(JPanel btn){
        pnl_action.removeAll();
        pnl_action.add(btn);
        pnl_action.repaint();
        pnl_action.revalidate();
    }
    
    public Boolean setConnectionProperties(){
    	
    	jpl = (ConnectionPanel)pnl_main.getComponent(0);
    	this.url=jpl.getTxtURL();
    	this.httpType=jpl.getHttp();
    	this.port=jpl.getTxtPort();
    	this.restWs=jpl.getTxtREST();
    	this.adminUn=jpl.getTextUserName();
    	this.adminPw=jpl.getPasswordField();
    	
    	return true;
    }
    
    public Boolean setFilenames(){
    	
    	this.folderId = jpl.getTextFolderId();
    	this.excelFileName = jpl.getTxtFileName();
    	this.sqlFileName = jpl.getTxtFileName().replace("xlsx", "sql");
    	this.exportPath = jpl.getTxtPath();
    	return true;
    }
    
    public Boolean excelExport(){
    	    	
		/*=======================================================================================*/
    	/* Excel export 																		 */
    	/*=======================================================================================*/
    	   	
		ExcelExport excel = new ExcelExport();
		
		try {
		excel.setCMS(this.url);
		excel.setUserId(this.adminUn);
		excel.setPassword(this.adminPw);
		excel.setFolderID(this.folderId);

		excel.initCMSConnection();
		excel.initExcel();

		excel.addRow("Report Name|Report ID|Report Path|Obj1 Name|Obj1 Type|Obj1 Path|Obj2 Name|Obj2 Type|Obj2 Path");

		excel.getReporDetails();
		reportList = excel.reportList;
		excel.setFinalfilename(this.excelFileName);
		excel.saveExcel(this.exportPath);
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
    	return true;
    }
       
    public Boolean sqlExport(){

		/*=======================================================================================*/
		/* Free hand extraction 																	 */
		/*=======================================================================================*/		
		
		String fileName = this.sqlFileName;
		String colDelimiter = "********************";
		String rowDelimiter = "####################";
		
		try
		{
			
			FreeHandSQLExport fh = new FreeHandSQLExport();
					
			/** Defines the URL of the CMS repository. */
			fh.setCMS_SERVER_URL(this.httpType+"://"+this.url+":"+this.port);		
			
			/** USERNAME used to log in to the CMS. You must fill your USER_NAME. */
			fh.setCMS_USER(this.adminUn);

			/** PASSWORD used to log in to the CMS. You must fill your PASSWORD. */
			fh.setCMS_PASS(this.adminPw);

			/** Authentication CMS Type */
			fh.setCMS_AUTH("secEnterprise");
			
			
			fh.setBIP_RWS(this.restWs);
			
			
			fh.setRL_RWS(this.restWs + "/raylight/v1");
			
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			String content = "";
			FreeHandSQLExport.setUp();
			
			bw.write(content);
			
			for ( String repID : this.reportList) {
				//System.out.println(repID);
				List<DProvider> dpList = new ArrayList<>(0);

				dpList=fh.ExportFHSQL(repID);
					
				for (DProvider dp : dpList) {
					content = dp.getDocumentId()+
					 "\n" + colDelimiter
					+"\n" + dp.getDataProviderId()
					+"\n" + colDelimiter
					+"\n" + dp.getDataProviderName()
					+"\n" + colDelimiter
					+"\n" + dp.getDataConnectionName()
					+"\n" + colDelimiter					
					+"\n" + dp.getFhsql()
					+"\n" + rowDelimiter +"\n" ;
					
					bw.append(content);
				}
				//System.out.println("Panagis 3" + content);
			}
			bw.close();
			FreeHandSQLExport.tearDown();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
    	return true;
    }
    
    public Boolean setProviders(){
    	
   	
    	return true;
    }
 

    
    public Boolean purgeReports(){
    	
    	return true;
    }
    
    public Boolean replaceDataProvider(){
    	
    	return true;
    }
    
    
    /* Getters -  Setters*/
	public String getAdminUn() {
		return adminUn;
	}

	public void setAdminUn(String adminUn) {
		this.adminUn = adminUn;
	}

	public String getAdminPw() {
		return adminPw;
	}

	public void setAdminPw(String adminPw) {
		this.adminPw = adminPw;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRestWs() {
		return restWs;
	}

	public void setRestWs(String restWs) {
		this.restWs = restWs;
	}

	public String getHttpType() {
		return httpType;
	}

	public void setHttpType(String httpType) {
		this.httpType = httpType;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public XMLReader getXmlr() {
		return xmlr;
	}

	public void setXmlr(XMLReader xmlr) {
		this.xmlr = xmlr;
	}

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	public String getSqlFileName() {
		return sqlFileName;
	}

	public void setSqlFileName(String sqlFileName) {
		this.sqlFileName = sqlFileName;
	}

	public String getExportPath() {
		return exportPath;
	}

	public void setExportPath(String exportPath) {
		this.exportPath = exportPath;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	

}
