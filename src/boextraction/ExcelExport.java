/**
 * @author Panagis
 *
 */

package boextraction;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.crystaldecisions.sdk.exception.SDKException;
import com.crystaldecisions.sdk.framework.CrystalEnterprise;
import com.crystaldecisions.sdk.framework.IEnterpriseSession;
import com.crystaldecisions.sdk.framework.ISessionMgr;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;
import com.crystaldecisions.sdk.occa.infostore.IInfoObjects;
import com.crystaldecisions.sdk.occa.infostore.IInfoStore;
import com.crystaldecisions.sdk.plugin.desktop.folder.IFolder;
import com.crystaldecisions.sdk.properties.IProperties;
import com.crystaldecisions.sdk.properties.IProperty;

public class ExcelExport {

	IEnterpriseSession es;
	protected static IInfoStore iStore;
	XSSFWorkbook workbook = null;

	String objName;
	String objKind;
	String objPath;
	
	Cell cell;

	XSSFFont font;

	XSSFCellStyle my_style_1;
	XSSFCellStyle my_style_2;
	XSSFSheet sheet;
	XSSFSheet sheetnotes;
	XSSFCellStyle my_style_0;
	int rownum;
	int rownumn;
	public List<String> reportList = new ArrayList<>(0);
	private String CMS;
	private String userId;
	private String password;
	private String filepath;
	private String finalfilename;
	protected String folderid;
	private String parentFolderName;

	public void initCMSConnection() throws SDKException {
		System.out.println("Initiating CMS Connection.....");
		ISessionMgr sm = CrystalEnterprise.getSessionMgr();
		this.es = sm.logon(this.userId, this.password, this.CMS, "secEnterprise");
		iStore = (IInfoStore)this.es.getService("", "InfoStore");
	}

	public void setPassword(String s1)
	{
		this.password = s1;
	}

	public void setUserId(String s1) {
		this.userId = s1;
	}

	public void setCMS(String s1) {
		this.CMS = s1;
	}

	public void setFolderID(String s1) {
		this.folderid = s1;
	}

	public void initExcel() {
		System.out.println("Initiating Excel.....");

		this.workbook = new XSSFWorkbook();
		this.sheet = this.workbook.createSheet("ReportDetails");
		this.sheetnotes = this.workbook.createSheet("Notes");
		this.my_style_1 = this.workbook.createCellStyle();
		this.my_style_2 = this.workbook.createCellStyle();
		XSSFFont font = this.workbook.createFont();
		font.setFontHeightInPoints((short)10);
		font.setFontName("Arial");
		font.setColor(IndexedColors.WHITE.getIndex());
		font.setBold(true);
		font.setItalic(false);

		this.my_style_1.setBorderBottom(BorderStyle.THIN);
		this.my_style_1.setBorderTop(BorderStyle.THIN);
		this.my_style_1.setBorderRight(BorderStyle.THIN);
		this.my_style_1.setBorderLeft(BorderStyle.THIN);

		this.my_style_1.setAlignment(HorizontalAlignment.CENTER);
		this.my_style_1.setVerticalAlignment(VerticalAlignment.CENTER);

		this.my_style_2.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
		this.my_style_2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		this.my_style_2.setFont(font);

		this.my_style_2.setBorderBottom(BorderStyle.THIN);
		this.my_style_2.setBorderTop(BorderStyle.THIN);
		this.my_style_2.setBorderRight(BorderStyle.THIN);
		this.my_style_2.setBorderLeft(BorderStyle.THIN);

		this.my_style_2.setAlignment(HorizontalAlignment.CENTER);
		this.my_style_2.setVerticalAlignment(VerticalAlignment.CENTER);
		addNRow("======================================================================");
		addNRow("           // Created By Panagis and Nick //                          ");
		addNRow("======================================================================");		
		System.out.println("Fetching the Report Details from the CMS repository.....");
	}

	public void saveExcel() throws Exception {
		try {
			this.filepath = System.getProperty("user.dir");
			String finalpath = this.filepath + "\\" + this.finalfilename;
			FileOutputStream out = new FileOutputStream(new File(finalpath));
			this.workbook.write(out);
			out.close();
			System.out.println("Excel written successfully At: " + finalpath);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveExcel(String path) throws Exception {
		try {
			this.filepath = path;
			String finalpath = this.filepath + "\\" + this.finalfilename;
			FileOutputStream out = new FileOutputStream(new File(finalpath));
			this.workbook.write(out);
			out.close();
			System.out.println("Excel written successfully At: " + finalpath);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addNRow(String rowData) {
		int cellnum = 0;
		Row rown = this.sheetnotes.createRow(this.rownumn++);
		StringTokenizer st = new StringTokenizer(rowData, ":");
		while (st.hasMoreTokens()) {
			this.sheetnotes.autoSizeColumn(cellnum);
			Cell cell = rown.createCell(cellnum++);
			cell.setCellStyle(this.my_style_2);
			cell.setCellValue(st.nextToken());
		}
	}

	public void addRow(String rowData) {
		int cellnum = 0;
		Row row = this.sheet.createRow(this.rownum++);
		StringTokenizer st = new StringTokenizer(rowData, "|");
		while (st.hasMoreTokens()) {
			if (row.getRowNum() == 0)
			{
				this.sheet.autoSizeColumn(cellnum);
				Cell cell = row.createCell(cellnum++);
				cell.setCellStyle(this.my_style_2);
				this.sheet.setAutoFilter(CellRangeAddress.valueOf("A1:I1"));
				cell.setCellValue(st.nextToken());
			}
			else
			{
				this.sheet.autoSizeColumn(cellnum);
				Cell cell = row.createCell(cellnum++);
				cell.setCellStyle(this.my_style_1);
				cell.setCellValue(st.nextToken());
			}
		}
	}

	public void setFilename() throws SDKException {
		String sq1 = "SELECT SI_NAME from CI_INFOOBJECTS, CI_SYSTEMOBJECTS, CI_APPOBJECTS WHERE SI_ID = " + this.folderid;

		IInfoObjects iObjects = iStore.query(sq1);


		for (int i = 0; i < iObjects.size(); i++)
		{
			IInfoObject iobjects = (IInfoObject)iObjects.get(i);
			this.parentFolderName = iobjects.getTitle();
		}

		this.finalfilename = (this.CMS + "_" + this.folderid + "_" + this.parentFolderName + ".xlsx");
	}
	

	public void getReporDetails()
			throws SDKException
	{
		String sq = "SELECT TOP 50000 SI_NAME, SI_ID, SI_KIND, SI_UNIVERSE, SI_DSL_UNIVERSE, SI_FHSQL_RELATIONAL_CONNECTION, SI_PARENTID, SI_FILES, SI_PARENT_FOLDER from CI_INFOOBJECTS, CI_SYSTEMOBJECTS, CI_APPOBJECTS WHERE si_kind = 'Webi' AND SI_INSTANCE = 0 AND SI_ANCESTOR=" + folderid;

		IInfoObjects iObjects = iStore.query(sq);

		for (int i = 0; i < iObjects.size(); i++)
		{	
			StringBuilder rowData = new StringBuilder();

			IInfoObject iobjects = (IInfoObject)iObjects.get(i);
			
			rowData.append(iobjects.getTitle());
			rowData.append("|");

			rowData.append(iobjects.getID());
			rowData.append("|");

			IProperties prop = iobjects.properties();
			IProperty getProp = prop.getProperty("SI_PARENTID");
			String FolderID = getProp.toString();
			IInfoObjects folder = iStore.query("select si_id,si_CUID,si_name,si_parentid,si_path from ci_infoobjects where si_id=" + FolderID);
			IInfoObject ifolder = (IInfoObject)folder.get(0);
			if (ifolder.getKind().equals("Folder")) {
				IFolder iifolder = (IFolder)ifolder;
				String finalFolderPath = "";
				if (iifolder.getPath() != null) {
					String[] path = iifolder.getPath();
					for (int fi = 0; fi < path.length; fi++) {
						finalFolderPath = path[fi] + "/" + finalFolderPath;
					}
					finalFolderPath = finalFolderPath + iifolder.getTitle();
				} else {
					finalFolderPath = finalFolderPath + iifolder.getTitle();
				}

				rowData.append(finalFolderPath);
			} else if (ifolder.getKind().equals("FavoritesFolder")) {
				rowData.append("FavoritesFolder ::  ");
				rowData.append(ifolder.getTitle());
				rowData.append("|");
			} else if (ifolder.getKind().equals("Inbox")) {
				rowData.append(" Inbox ::  ");
				rowData.append(ifolder.getTitle());
				rowData.append("|");
			} else if (ifolder.getKind().equals("ObjectPackage")) {
				IProperties prop1 = ifolder.properties();
				IProperty getProp1 = prop1.getProperty("SI_PARENTID");
				String FolderID1 = getProp1.toString();
				IInfoObjects folder1 = iStore.query("select * from ci_infoobjects where si_id=" +
														FolderID1);
				IInfoObject ifolder1 = (IInfoObject)folder1.get(0);
				if (ifolder1.getKind().equals("Folder")) {
					IFolder iifolder1 = (IFolder)ifolder1;
					String finalFolderPath1 = "";
					if (iifolder1.getPath() != null) {
						String[] path = iifolder1.getPath();
						for (int j = 0; j < path.length; j++) {
							finalFolderPath1 = path[j] + "/" + finalFolderPath1;
						}
						finalFolderPath1 = 

								finalFolderPath1 + iifolder1.getTitle() + "/" + ifolder.getTitle();
					} else {
						finalFolderPath1 = 

								finalFolderPath1 + iifolder1.getTitle() + "/" + ifolder.getTitle();
					}
					rowData.append(finalFolderPath1);
					rowData.append("|");
				}
			}


			IProperties props = iobjects.properties();

			IProperty unvID = props.getProperty("SI_UNIVERSE");

			props = iobjects.properties();
			IProperty unxID = props.getProperty("SI_DSL_UNIVERSE");

			props = iobjects.properties();
			IProperty fhsqlconID = props.getProperty("SI_FHSQL_RELATIONAL_CONNECTION");
			
			props = iobjects.properties();
			IProperty CUID = props.getProperty("SI_ID");
			IInfoObjects report = iStore.query("select si_id,si_CUID,si_name,si_parentid,si_path from ci_infoobjects where si_id=" + CUID);
			IInfoObject ireport = (IInfoObject)report.get(0);
			
			reportList.add(CUID.toString());
//			IProperties repProps = ireport.properties();
//			IProperty repCUID = repProps.getProperty("SI_CUID");
//			System.out.println(repCUID.getValue().toString());
			
			if (!unvID.toString().contains("="))
			{
				unvID = null;
			}
			else {
				breakTokenU(unvID);

				rowData.append("|");
				rowData.append(this.objName);
				rowData.append("|");

				rowData.append(this.objKind);
				rowData.append("|");

				rowData.append(this.objPath);
				rowData.append("|");
			}


			if (!unxID.toString().contains("="))
			{
				unxID = null;
			}
			else
			{
				breakTokenU(unxID);

				rowData.append("|");
				rowData.append(this.objName);
				rowData.append("|");

				rowData.append(this.objKind);
				rowData.append("|");

				rowData.append(this.objPath);
				rowData.append("|");
			}


			if ((!fhsqlconID.toString().contains("=")) || (fhsqlconID == null))
			{
				fhsqlconID = null;
			}
			else
			{
				breakTokenU(fhsqlconID);

				rowData.append("|");
				rowData.append(this.objName);
				rowData.append("|");

				rowData.append(this.objKind);
				rowData.append("|");

				rowData.append(this.objPath);
				rowData.append("|");
			}


			//System.out.println(rowData.toString());
			addRow(rowData.toString());
		}
	}


	private void breakTokenU(IProperty unvID)
			throws SDKException
	{
		String temp = "";


		StringTokenizer eq = new StringTokenizer(unvID.toString(), ",", false);

		while (eq.hasMoreTokens()) {
			temp = eq.nextToken();
			if (temp.contains("=")) {
				int index = temp.indexOf("=");
				temp = temp.substring(index + 1, temp.length());

				getunvDetails(temp);
			}
		}
	}


	private void getunvDetails(String objID)
			throws SDKException
	{
		String sq = "SELECT TOP 50000 SI_NAME, SI_KIND from CI_INFOOBJECTS, CI_SYSTEMOBJECTS, CI_APPOBJECTS WHERE SI_KIND IN ('CCIS.DataConnection', 'DSL.MetaDataFile', 'Universe') AND SI_ID=" + objID;
		IInfoObjects iObjects = iStore.query(sq);

		for (int i = 0; i < iObjects.size(); i++)
		{

			IInfoObject iobjects = (IInfoObject)iObjects.get(i);

			this.objName = iobjects.getTitle();

			IProperties prop = iobjects.properties();
			IProperty getProp = prop.getProperty("SI_KIND");
			this.objKind = getProp.toString();

			this.objPath = objectPath(iobjects.getID());
		}
	}



	private String objectPath(int objID)
			throws SDKException
	{
		String folderpath = null;
		String sq = "SELECT TOP 50000 SI_NAME, SI_PARENTID, SI_FILES, SI_PARENT_FOLDER, SI_PATH from CI_INFOOBJECTS, CI_SYSTEMOBJECTS, CI_APPOBJECTS where SI_ID=" + objID;

		IInfoObjects iObjects = iStore.query(sq);

		for (int i = 0; i < iObjects.size(); i++)
		{
			IInfoObject iObject = (IInfoObject)iObjects.get(i);

			IProperties prop = iObject.properties();
			IProperty getProp = prop.getProperty("SI_PARENTID");

			String FolderID = getProp.toString();

			IInfoObjects folder = iStore.query("select top 50000 si_id,si_CUID,si_name,si_parentid,si_path from  ci_infoobjects,ci_systemobjects, ci_appobjects where si_id=" + FolderID);

			IInfoObject ifolder = (IInfoObject)folder.get(0);
			if (ifolder.getKind().equals("Folder")) {
				IFolder iifolder = (IFolder)ifolder;
				String finalFolderPath = "";
				if (iifolder.getPath() != null) {
					String[] path = iifolder.getPath();
					for (int fi = 0; fi < path.length; fi++) {
						finalFolderPath = path[fi] + "/" + finalFolderPath;
					}
					finalFolderPath = finalFolderPath + iifolder.getTitle();
				} else {
					finalFolderPath = finalFolderPath + iifolder.getTitle();
				}

				folderpath = finalFolderPath;
			}
			else {
				folderpath = "";
			}
		}

		if (folderpath == null) {
			folderpath = "";
		} else {
			return folderpath;
		}
		return folderpath;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getFinalfilename() {
		return finalfilename;
	}

	public void setFinalfilename(String finalfilename) {
		this.finalfilename = finalfilename;
	}
	
	
}

