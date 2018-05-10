package boextraction;

import com.crystaldecisions.sdk.exception.SDKException;
import com.crystaldecisions.sdk.framework.CrystalEnterprise;
import com.crystaldecisions.sdk.framework.IEnterpriseSession;
import com.crystaldecisions.sdk.framework.ISessionMgr;
import com.crystaldecisions.sdk.occa.infostore.IInfoStore;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
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

public class InitCmsExcel {
	IEnterpriseSession es;
	protected static IInfoStore iStore;
	XSSFWorkbook workbook = null;

	Cell cell;
	XSSFFont font;
	XSSFCellStyle my_style_1;
	XSSFCellStyle my_style_2;
	XSSFSheet sheet;
	XSSFSheet sheetnotes;
	XSSFCellStyle my_style_0;
	int rownum;
	int rownumn;
	private String CMS;
	private String userId;
	private String password;
	private String filepath;
	public String folderid;
	protected static InitCmsExcel initcmsexcel = new InitCmsExcel();

	public void initCMSConnection() throws SDKException {
		System.out.println("======================================================================");
		System.out.println("           // Authored By Panagis and Nick //             ");
		System.out.println("======================================================================");
		System.out.println("Initiating CMS Connection.....");
		ISessionMgr sm = CrystalEnterprise.getSessionMgr();
		System.out.println("Creating CMS Session.....");
		this.es = sm.logon(this.userId, this.password, this.CMS, "secEnterprise");
		System.out.println("Getting InfoStore Info.....");
		iStore = (IInfoStore) this.es.getService("", "InfoStore");
	}

	public void setPassword(String s1) {
		this.password = s1;
	}

	public void setUserId(String s1) {
		this.userId = s1;
	}

	public void setCMS(String s1) {
		this.CMS = s1;
	}

	public void initExcel() {
		System.out.println("Initiating Excel.....");

		this.workbook = new XSSFWorkbook();
		this.sheet = this.workbook.createSheet("UserDetails");
		this.sheetnotes = this.workbook.createSheet("Notes");
		this.my_style_1 = this.workbook.createCellStyle();
		this.my_style_2 = this.workbook.createCellStyle();
		XSSFFont font = this.workbook.createFont();
		font.setFontHeightInPoints((short) 10);
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
		addNRow("           // Authored By Panagis and Nick //             ");
		addNRow("======================================================================");
		System.out.println("Fetching the User Details from the CMS database.....");
	}

	public void saveExcel() throws Exception {
		try {
			this.filepath = System.getProperty("user.dir");
			String finalpath = this.filepath + "\\Details.xlsx";
			FileOutputStream out = new FileOutputStream(new File(finalpath));
			this.workbook.write(out);
			out.close();
			System.out.println("Excel written successfully At: " + finalpath);
		} catch (Exception e) {
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
			if (row.getRowNum() == 0) {
				this.sheet.autoSizeColumn(cellnum);
				Cell cell = row.createCell(cellnum++);
				cell.setCellStyle(this.my_style_2);
				this.sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H1"));
				cell.setCellValue(st.nextToken());
			} else {
				this.sheet.autoSizeColumn(cellnum);
				Cell cell = row.createCell(cellnum++);
				cell.setCellStyle(this.my_style_1);
				cell.setCellValue(st.nextToken());
			}
		}
	}
}
