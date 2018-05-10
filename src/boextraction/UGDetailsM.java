package boextraction;

import com.crystaldecisions.sdk.exception.SDKException;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;
import com.crystaldecisions.sdk.occa.infostore.IInfoObjects;
import com.crystaldecisions.sdk.occa.infostore.IInfoStore;
import com.crystaldecisions.sdk.plugin.desktop.internal.UserGroupAssociates;
import com.crystaldecisions.sdk.plugin.desktop.user.IUser;
import com.crystaldecisions.sdk.plugin.desktop.user.IUserAliases;
import com.crystaldecisions.sdk.properties.IProperties;
import com.crystaldecisions.sdk.properties.IProperty;

import java.util.Map;
import java.util.StringTokenizer;

public class UGDetailsM extends InitCmsExcel {
	UserGroupAssociates userGroup;
	IUser iUser;

	public static void main(String[] args) throws Exception {
		try {
			UGDetailsM UserGD = new UGDetailsM();

			initcmsexcel.setCMS(args[0]);
			initcmsexcel.setUserId(args[1]);
			initcmsexcel.setPassword(args[2]);

			initcmsexcel.initCMSConnection();
			System.out.println("After Initiating CMS Connection.....");
			initcmsexcel.initExcel();
			System.out.println("After Initiating Excel.....");
			initcmsexcel.addRow(
					"User ID|Full Name|Email Address|Status|Last Logon Time|Creation Time|Last Password Change|Last Modify Date|Owner|License Type|Group Details");
			UserGD.generateExcel();

			initcmsexcel.saveExcel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generateExcel() throws SDKException {
		String sq = "Select TOP 50000 SI_NAME, SI_USERFULLNAME,SI_EMAIL_ADDRESS,SI_ALIASES,SI_LASTLOGONTIME,SI_CREATION_TIME,SI_LAST_PASSWORD_CHANGE_TIME, SI_UPDATE_TS, SI_OWNER,SI_NAMEDUSER,SI_USERGROUPS FROM CI_SYSTEMOBJECTS WHERE SI_KIND = 'USER'";
		IInfoObjects iObjects = InitCmsExcel.iStore.query(sq);

		for (int i = 0; i < iObjects.size(); i++) {
			StringBuilder rowData = new StringBuilder();

			this.iUser = ((IUser) iObjects.get(i));

			rowData.append(this.iUser.getTitle());
			rowData.append("|");

			rowData.append(this.iUser.getFullName().equals("") ? " " : this.iUser.getFullName());
			rowData.append("|");

			rowData.append(this.iUser.getEmailAddress().equals("") ? " " : this.iUser.getEmailAddress());
			rowData.append("|");

			rowData.append(getUserStatus(this.iUser.getAliases()));
			rowData.append("|");

			rowData.append(getLastLogonTime().equals("") ? " " : getLastLogonTime());
			rowData.append("|");

			IProperties props = this.iUser.properties();
			IProperty createTime = props.getProperty("SI_CREATION_TIME");
			rowData.append(createTime.getValue().toString().equals("") ? " " : createTime);
			rowData.append("|");
			// here adding info for Romania
			
			System.out.println(this.iUser.getFullName());

			//Password
			try {
				IProperty last_PasswordChangeTime = props.getProperty("SI_LAST_PASSWORD_CHANGE_TIME");
				rowData.append(last_PasswordChangeTime.getValue().toString().equals("") ? " " : last_PasswordChangeTime);
				rowData.append("|");
			} catch (Exception e) {
				rowData.append(" "); //For Guest
				rowData.append("|");
			}


			//Modify
			IProperty last_ModifyTime = props.getProperty("SI_UPDATE_TS");
			rowData.append(last_ModifyTime.getValue().toString().equals("") ? " " : last_ModifyTime);
			rowData.append("|");
			
			//Owner
			rowData.append(this.iUser.getOwner()); 
			rowData.append("|");
// end			
			IProperty licenseType = props.getProperty("SI_NAMEDUSER");

			if (licenseType.getValue().toString() == "true") {
				rowData.append("Named User License");
				rowData.append("|");
			} else {
				rowData.append("Concurrent Access License");
				rowData.append("|");
			}

			rowData.append(getGroupsDetails((UserGroupAssociates) this.iUser.getGroups()));
			rowData.append("|");

			initcmsexcel.addRow(rowData.toString());
		}
	}

	private String getGroupsDetails(UserGroupAssociates userGroup) throws SDKException {
		String temp = "";

		String finalgrouplist = "";
		IInfoObject iObject = null;
		StringBuilder groupname1 = new StringBuilder();
		StringTokenizer st1 = new StringTokenizer(userGroup.toString(), "[", false);

		while (st1.hasMoreTokens()) {
			temp = st1.nextToken();
		}
		temp = temp.substring(0, temp.length() - 1);
		String[] tempgids = temp.split(",");
		for (int i = 0; i < tempgids.length; i++) {
			String groupid = tempgids[i].toString().trim();

			String groupName = "Select TOP 50000 SI_NAME FROM CI_SYSTEMOBJECTS WHERE SI_KIND = 'UserGroup' AND SI_ID="
					+ groupid;
			IInfoObjects iObjects = iStore.query(groupName);

			for (int x = 0; x < iObjects.size(); x++) {
				iObject = (IInfoObject) iObjects.get(x);

				IProperties prop = iObject.properties();
				IProperty getGroupName = prop.getProperty("SI_NAME");

				groupname1.append(getGroupName.getValue().toString());
				groupname1.append(", ");
			}
		}

		finalgrouplist = groupname1.toString();
		if (finalgrouplist.endsWith(" ")) {
			String finalgrouplist1 = finalgrouplist.substring(0, finalgrouplist.length() - 2);
			return finalgrouplist = finalgrouplist1;
		}

		return finalgrouplist;
	}

	private String getLastLogonTime() {
		IProperties props = this.iUser.properties();
		IProperty lastLogon = props.getProperty("SI_LASTLOGONTIME");

		if (lastLogon != null) {
			return lastLogon.getValue().toString();
		}

		return "User never logged into the server";
	}

	private String getUserStatus(IUserAliases aliases) {
		String temp = "";
		StringTokenizer st = new StringTokenizer(aliases.toString(), ",", false);
		while (st.hasMoreTokens())
			temp = st.nextToken();
		StringTokenizer st1 = new StringTokenizer(temp, "=", false);
		while (st1.hasMoreTokens())
			temp = st1.nextToken();
		temp = temp.substring(0, temp.length() - 1);
		if (temp.contains("true")) {
			return "Disabled";
		}

		return "Enabled";
	}
}
