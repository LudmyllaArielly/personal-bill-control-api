package com.ludmylla.personal.bill.config;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ludmylla.personal.bill.model.Bill;
import com.ludmylla.personal.bill.useful.Useful;

public class ExcelExporter {
	
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	private List<Bill> listBill;

	public ExcelExporter(List<Bill> listBill) {
		this.listBill = listBill;
		workbook = new XSSFWorkbook();
	}
	
	private void createCell(Row row, int dataLineNumber, Object value, CellStyle style) {

	 		sheet.autoSizeColumn(dataLineNumber);
			Cell cell = row.createCell(dataLineNumber);
			
			if(value instanceof Long) {
				cell.setCellValue((Long) value);
			}else if(value instanceof Integer) {
				cell.setCellValue((Integer) value);
			}else if(value instanceof Boolean) {
				cell.setCellValue((Boolean) value);
			}else if(value instanceof Date) {
				cell.setCellValue((Date) value);
			}else {
				cell.setCellValue((String) value);  
			}
			
		cell.setCellStyle(style);
		}
	
	
	private void writeHeaderLine() {
		sheet = workbook.createSheet("Bill");
		
		Row row = sheet.createRow(0);
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(20);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		createCell(row, 0 , "Bill Information", style);
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,4));
		font.setFontHeightInPoints((short)(10));
		
		row=sheet.createRow(1);
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		
		addinAttributesToTheHeaderLine(row, style);
			
	}
	
	private void addinAttributesToTheHeaderLine(Row row, CellStyle style) {
		createCell(row, 0, "Description", style);
		createCell(row, 1, "Price total", style);
		createCell(row, 2, "Quantity payment installments", style);
		createCell(row, 3, "purchaseDate", style);
		createCell(row, 4 , "Category", style);
		createCell(row, 5 , "Pay", style);
		createCell(row, 6 , "Account Type", style);
		createCell(row, 7 , "Value Type", style);	
		
		for(int i =0; i<listBill.size(); i++) {
			if(listBill.get(i).getPaymentInstallments().size() > 0) {
				int headerLineNumber = 8;
				for(int j =0; j<listBill.get(i).getPaymentInstallments().size(); j++) {
					createCell(row, headerLineNumber++, "Installment Number", style);
					createCell(row, headerLineNumber++, "Installment Price", style);
					createCell(row, headerLineNumber++, "Installment Date", style);
				}
			}
		}
	}

	private void writeDataLines() {
		int rowCount = 2;
		
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);
		
		addingDataToTheLine(style, rowCount);
		
	}	
	private void addingDataToTheLine(CellStyle style, int rowCount) {

		for(Bill bill : listBill) {
			Row row = sheet.createRow(rowCount++);
			int dataLineNumber = 0;
			createCell(row, dataLineNumber++, bill.getDescription(), style);
			createCell(row, dataLineNumber++, bill.getPriceTotal().toPlainString(), style);
			createCell(row, dataLineNumber++, bill.getQuantityPaymentInstallments().toPlainString(), style);
			createCell(row, dataLineNumber++, Useful.formatterDate( bill.getPurchaseDate()), style);
			createCell(row, dataLineNumber++, bill.getCategory().getName().toString() ,style);
			createCell(row, dataLineNumber++, bill.getPay().getDescription().toString() ,style);
			createCell(row, dataLineNumber++, bill.getAccountType().toString() ,style);
			createCell(row, dataLineNumber++, bill.getValueType().toString() ,style);
	
				for(int i =0; i<bill.getPaymentInstallments().size(); i++) {
					createCell(row, dataLineNumber++, bill.getPaymentInstallments().get(i).getInstallmentNumber(),style);
					createCell(row, dataLineNumber++, bill.getPaymentInstallments().get(i).getInstallmentPrice().toPlainString(),style);
					createCell(row, dataLineNumber++, Useful.formatterDate(bill.getPaymentInstallments().get(i).getInstallmentDate()),style);
				}	
			}
	}
	
	public void export(HttpServletResponse response) throws IOException{
		writeHeaderLine();
		writeDataLines();
		
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
		
	}

}
