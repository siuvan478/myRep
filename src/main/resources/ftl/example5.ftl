<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>

<body>

	<div style="padding: 10px;">
		
		<table style="border-color:#ddd; border-collapse:collapse; font-size: 14px; text-align: inherit; font-family: inherit; line-height: 1.66667;font-weight: 600;
		color: #333;pointer-events: auto;padding: 0; width: 100%;" border="1" >
			<tr style="font-size: 20px;">
				<td colspan=15 style="padding: 10px;">客户交易入账通知</td>
			</tr>
			<tr>
				<td style="padding: 8px; background: #ccc;">交易号：</td>
				<td style="padding: 8px; color: #666; " colspan=3>${tradeNo}</td>
			</tr>
			<tr>
				<td style="padding: 8px; background: #ccc;">收据上传时间：</td>
				<td style="padding: 8px; color: #666;">${uploadDate}</td>
				<td style="padding: 8px; background: #ccc;">确认日期:</td>
				<td style="padding: 8px; color: #666;">${finPayTranDate}</td>
			</tr>
			<tr>
				<td style="padding: 8px; background: #ccc;">币种：</td>
				<td style="padding: 8px; color: #666;">${currency}</td>
				<td style="padding: 8px; background: #ccc;">收据总计（RMB）：</td>
				<td style="padding: 8px; color: #666; text-align: right;">${totalAmountInRMB}</td>
			</tr>
			<tr>
				<td style="padding: 8px; background: #ccc;">交易备注：</td>
				<td style="padding: 8px; color: #666;" colspan=3>${paymentRemark}</td>
			</tr>
			<tr style="word-break: keep-all;white-space:nowrap; background: #ccc;" >
				  <td style="padding: 5px;">用户名</td>
				  <td style="padding: 5px;">交易方式</td>
				  <td style="padding: 5px;">金额</td>
				  <td style="padding: 5px;">金额（RMB）</td>
			</tr>
			
			<#list details as detail>
			<tr style="word-break: keep-all;white-space:nowrap; color: #666;">
				  <td style="padding: 8px;">${detail.bdUserName}</td>
				  <td style="padding: 8px;">${detail.payCode}</td>
				  <td style="padding: 8px; text-align: right;">${detail.amount}</td>
				  <td style="padding: 8px; text-align: right;">${detail.amountInRMB}</td>
			</tr>
			</#list>
			
		</table>
	
	</div>

</body>
</html>