package utils;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import boextraction.DProvider;
import boextraction.XMLReader;

public class FreeHandSQLExport extends BaseAPIFunctionSet {
	
	//
	// !! Fill the sample parameters in the Config class !!
	//
	
	List<String> sql = new ArrayList<String>();

	@BeforeClass
	public static void setUp() throws Exception {
		// CMS logon
		logon();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		// CMS logoff
		logoff();
	}
	
	/**
	 * Contains all steps to use the free-hand SQL data provider according to the
	 * standard workflow.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "nls" })
	@Test
	
	
	public List<DProvider> ExportFHSQL(String docId) throws Exception {
		
		List<DProvider> dpList = new ArrayList<>(0);
		XMLReader xmlr = new  XMLReader();
		List<String> docConProviders = FreeHandSQLExport.getDataProvidersIDs(docId);
		
		for (String i : docConProviders) {
			String dataProvider = FreeHandSQLExport.getDataProvider(docId, i);	
			String sql = xmlr.readXmlSQL(xmlr.parseXmlDocument(dataProvider));
			String connId = xmlr.readXmlConnID(xmlr.parseXmlDocument(dataProvider));
			String dpName = xmlr.readXmlName(xmlr.parseXmlDocument(dataProvider));			
			String connName = FreeHandSQLExport.getNameOfConnection(connId);
			DProvider dp = new DProvider(docId,i,dpName, connName, sql);
			dpList.add(dp);
		}
		
		return dpList;		
	}


}
