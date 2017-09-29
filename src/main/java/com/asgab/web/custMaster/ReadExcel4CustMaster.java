package com.asgab.web.custMaster;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.asgab.entity.CustMaster;

public class ReadExcel4CustMaster {

	public static List<CustMaster> read(XSSFWorkbook workbook, String userId) throws Exception {

		List<CustMaster> lists = new ArrayList<CustMaster>();

		if (workbook != null) {

			XSSFSheet sheet = workbook.getSheetAt(0);
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {

				XSSFRow row = sheet.getRow(i);
				CustMaster tempCustMaster = new CustMaster();
				tempCustMaster.setCreateBy(userId);

				// 財務加款的端口
				tempCustMaster.setCustPort(getCellValue(row.getCell(0)));
				// 客戶用户名
				tempCustMaster.setCustUsername(getCellValue(row.getCell(1)));
				// 查賬財務郵箱地址
				tempCustMaster.setFin_email(getCellValue(row.getCell(2)));
				// 销售联系人
				tempCustMaster.setSales_contact(getCellValue(row.getCell(3)));
				// AM联系人
				tempCustMaster.setAm_contact(getCellValue(row.getCell(4)));
				// 操作联系人
				tempCustMaster.setOps_contact(getCellValue(row.getCell(5)));
				// 销售郵箱地址
				tempCustMaster.setSales_email(getCellValue(row.getCell(6)));
				// AM郵箱地址
				tempCustMaster.setAm_email(getCellValue(row.getCell(7)));
				// OP郵箱地址
				System.err.println(tempCustMaster.getCustUsername());
				tempCustMaster.setOps_email(getCellValue(row.getCell(8)));
				
				// 客户
				tempCustMaster.setCustName(getCellValue(row.getCell(9)));
				// 网站名称
				tempCustMaster.setWebName(getCellValue(row.getCell(10)));
				// 广告主
				tempCustMaster.setAdvertiser(getCellValue(row.getCell(11)));

				XSSFCell acctCreateDateCell = row.getCell(12);
				try {
					if (DateUtil.isCellDateFormatted(acctCreateDateCell)) {
						tempCustMaster.setAcctCreateDate(acctCreateDateCell.getDateCellValue());
					}

				} catch (Exception e) {
					System.err.println("Excel value error, for " + i + " row " + acctCreateDateCell);
				}

				XSSFCell annualSvcFeeDateCell = row.getCell(13);
				try {
					if (DateUtil.isCellDateFormatted(annualSvcFeeDateCell)) {
						tempCustMaster.setAnnualSvcFeeDate(annualSvcFeeDateCell.getDateCellValue());
					} else if (annualSvcFeeDateCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
						double d = annualSvcFeeDateCell.getNumericCellValue();
						Date date = DateUtil.getJavaDate(d);
						tempCustMaster.setAnnualSvcFeeDate(date);
					}

				} catch (Exception e) {
					System.err.println("Excel value error, for " + i + " row, value >> " + annualSvcFeeDateCell);
				}

				// 收取年服务费
				tempCustMaster.setAnnualSvcFee(getCellValue(row.getCell(14)));
				// 续费返点率（%）
				tempCustMaster.setRewardsPercent(getCellValue(row.getCell(15)));
				// 管理费率（%）
				tempCustMaster.setMgtFeePercent(getCellValue(row.getCell(16)));
				// 备注
				tempCustMaster.setRemark1(getCellValue(row.getCell(17)));
				// 已消费
				tempCustMaster.setConsumption(getCellValue(row.getCell(18)));

				lists.add(tempCustMaster);
			}
		}

		return lists;
	}

	public static String getCellValue(XSSFCell cell) {
		String cellValue = "";
		switch (cell.getCellType()) {
		case XSSFCell.CELL_TYPE_STRING:
			cellValue = String.valueOf(cell.getStringCellValue());
			break;
		case XSSFCell.CELL_TYPE_NUMERIC:
			cellValue = String.valueOf(cell.getNumericCellValue());
			break;
		case XSSFCell.CELL_TYPE_FORMULA:
			try {
				cellValue = String.valueOf(cell.getStringCellValue());
				System.err.println(">>>>>>" + cellValue);
			} catch (IllegalStateException e) {
				cellValue = "";
				//cellValue = String.valueOf(cell.getNumericCellValue());
			}
			break;
		default:
			break;
		}

		return cellValue;
	}

}
