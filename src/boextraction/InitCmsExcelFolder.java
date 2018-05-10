/*     */ package boextraction;
/*     */ 
/*     */ import com.crystaldecisions.sdk.exception.SDKException;
/*     */ import com.crystaldecisions.sdk.framework.CrystalEnterprise;
/*     */ import com.crystaldecisions.sdk.framework.IEnterpriseSession;
/*     */ import com.crystaldecisions.sdk.framework.ISessionMgr;
/*     */ import com.crystaldecisions.sdk.occa.infostore.IInfoStore;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.poi.ss.usermodel.BorderStyle;
/*     */ import org.apache.poi.ss.usermodel.Cell;
/*     */ import org.apache.poi.ss.usermodel.FillPatternType;
/*     */ import org.apache.poi.ss.usermodel.HorizontalAlignment;
/*     */ import org.apache.poi.ss.usermodel.IndexedColors;
/*     */ import org.apache.poi.ss.usermodel.Row;
/*     */ import org.apache.poi.ss.usermodel.VerticalAlignment;
/*     */ import org.apache.poi.ss.util.CellRangeAddress;
/*     */ import org.apache.poi.xssf.usermodel.XSSFCellStyle;
/*     */ import org.apache.poi.xssf.usermodel.XSSFFont;
/*     */ import org.apache.poi.xssf.usermodel.XSSFSheet;
/*     */ import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/*     */ 
/*     */ public class InitCmsExcelFolder
/*     */ {
/*     */   IEnterpriseSession es;
/*     */   protected static IInfoStore iStore;
/*  34 */   XSSFWorkbook workbook = null;
/*     */   
/*     */   Cell cell;
/*     */   XSSFFont font;
/*     */   XSSFCellStyle my_style_1;
/*     */   XSSFCellStyle my_style_2;
/*     */   XSSFSheet sheet;
/*     */   XSSFSheet sheetnotes;
/*     */   XSSFCellStyle my_style_0;
/*     */   int rownum;
/*     */   int rownumn;
/*     */   private String CMS;
/*     */   private String userId;
/*     */   private String password;
/*     */   private String filepath;
/*     */   public String folderid;
/*  50 */   protected static InitCmsExcelFolder initcmsfolder = new InitCmsExcelFolder();
/*     */   
/*     */   public void initCMSConnection() throws SDKException {
/*  53 */     System.out.println("======================================================================");
/*  54 */     System.out.println("           // Authored By Panagis and Nick //             ");
/*  55 */     System.out.println("======================================================================");
/*  56 */     System.out.println("Initiating CMS Connection.....");
/*  57 */     ISessionMgr sm = CrystalEnterprise.getSessionMgr();
/*  58 */     this.es = sm.logon(this.userId, this.password, this.CMS, "secEnterprise");
/*  59 */     iStore = (IInfoStore)this.es.getService("", "InfoStore");
/*     */   }
/*     */   
/*     */   public void setPassword(String s1) {
/*  63 */     this.password = s1;
/*     */   }
/*     */   
/*     */   public void setUserId(String s1) {
/*  67 */     this.userId = s1;
/*     */   }
/*     */   
/*     */   public void setCMS(String s1) {
/*  71 */     this.CMS = s1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFolderID(String s1)
/*     */   {
/*  79 */     this.folderid = s1;
/*     */   }
/*     */   
/*     */   public void initExcel() {
/*  83 */     System.out.println("Initiating Excel.....");
/*     */     
/*  85 */     this.workbook = new XSSFWorkbook();
/*  86 */     this.sheetnotes = this.workbook.createSheet("Notes");
/*  87 */     this.sheet = this.workbook.createSheet("ReportDetails");
/*  88 */     this.my_style_1 = this.workbook.createCellStyle();
/*  89 */     this.my_style_2 = this.workbook.createCellStyle();
/*  90 */     XSSFFont font = this.workbook.createFont();
/*  91 */     font.setFontHeightInPoints((short)10);
/*  92 */     font.setFontName("Arial");
/*  93 */     font.setColor(IndexedColors.WHITE.getIndex());
/*  94 */     font.setBold(true);
/*  95 */     font.setItalic(false);
/*     */     
/*  97 */     this.my_style_1.setBorderBottom(BorderStyle.THIN);
/*  98 */     this.my_style_1.setBorderTop(BorderStyle.THIN);
/*  99 */     this.my_style_1.setBorderRight(BorderStyle.THIN);
/* 100 */     this.my_style_1.setBorderLeft(BorderStyle.THIN);
/*     */     
/* 102 */     this.my_style_1.setAlignment(HorizontalAlignment.CENTER);
/* 103 */     this.my_style_1.setVerticalAlignment(VerticalAlignment.CENTER);
/*     */     
/* 105 */     this.my_style_2.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
/* 106 */     this.my_style_2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
/* 107 */     this.my_style_2.setFont(font);
/*     */     
/* 109 */     this.my_style_2.setBorderBottom(BorderStyle.THIN);
/* 110 */     this.my_style_2.setBorderTop(BorderStyle.THIN);
/* 111 */     this.my_style_2.setBorderRight(BorderStyle.THIN);
/* 112 */     this.my_style_2.setBorderLeft(BorderStyle.THIN);
/*     */     
/* 114 */     this.my_style_2.setAlignment(HorizontalAlignment.CENTER);
/* 115 */     this.my_style_2.setVerticalAlignment(VerticalAlignment.CENTER);
/* 116 */     addNRow("======================================================================");
/* 117 */     addNRow("           // Authored By Panagis and Nick //             ");
/* 118 */     addNRow("======================================================================");
/* 119 */     System.out.println("Fetching the Report and Folder Details from the CMS database.....");
/*     */   }
/*     */   
/*     */   public void saveExcel() throws Exception {
/*     */     try {
/* 124 */       this.filepath = System.getProperty("user.dir");
/* 125 */       String finalpath = this.filepath + "\\Details.xlsx";
/* 126 */       FileOutputStream out = new FileOutputStream(new File(finalpath));
/* 127 */       this.workbook.write(out);
/* 128 */       out.close();
/* 129 */       System.out.println("Excel written successfully At: " + finalpath);
/*     */     }
/*     */     catch (Exception e) {
/* 132 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public void addNRow(String rowData) {
/* 137 */     int cellnum = 0;
/* 138 */     Row rown = this.sheetnotes.createRow(this.rownumn++);
/* 139 */     StringTokenizer st = new StringTokenizer(rowData, ":");
/* 140 */     while (st.hasMoreTokens()) {
/* 141 */       this.sheetnotes.autoSizeColumn(cellnum);
/* 142 */       Cell cell = rown.createCell(cellnum++);
/* 143 */       cell.setCellStyle(this.my_style_2);
/* 144 */       cell.setCellValue(st.nextToken());
/*     */     }
/*     */   }
/*     */   
/*     */   public void addRow(String rowData) {
/* 149 */     int cellnum = 0;
/* 150 */     Row row = this.sheet.createRow(this.rownum++);
/* 151 */     StringTokenizer st = new StringTokenizer(rowData, "|");
/* 152 */     while (st.hasMoreTokens()) {
/* 153 */       if (row.getRowNum() == 0)
/*     */       {
/* 155 */         this.sheet.autoSizeColumn(cellnum);
/* 156 */         Cell cell = row.createCell(cellnum++);
/* 157 */         cell.setCellStyle(this.my_style_2);
/* 158 */         this.sheet.setAutoFilter(CellRangeAddress.valueOf("A1:C1"));
/* 159 */         cell.setCellValue(st.nextToken());
/*     */       }
/*     */       else
/*     */       {
/* 163 */         this.sheet.autoSizeColumn(cellnum);
/* 164 */         Cell cell = row.createCell(cellnum++);
/* 165 */         cell.setCellStyle(this.my_style_1);
/* 166 */         cell.setCellValue(st.nextToken());
/*     */       }
/*     */     }
/*     */   }
/*     */ }
